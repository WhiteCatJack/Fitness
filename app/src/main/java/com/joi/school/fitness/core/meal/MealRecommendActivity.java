package com.joi.school.fitness.core.meal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.base.BiCallback;
import com.joi.school.fitness.util.AndroidUtils;
import com.joi.school.fitness.util.BaiduAuth;
import com.joi.school.fitness.util.FrescoUtils;
import com.joi.school.fitness.util.HttpUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import es.dmoral.toasty.Toasty;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class MealRecommendActivity extends BaseActivity {

    private static final int MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE = 1;
    private static final int MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE = 2;

    private List<Meal> likeList = new ArrayList<>();
    private List<Meal> unlikeList = new ArrayList<>();

    private TextView mTestButton;
    private TextView mTestScanButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_recommend);

        mTestButton = findViewById(R.id.bt_test_meal_recommend);
        mTestScanButton = findViewById(R.id.bt_test_meal_scan);

        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Meal> query = new BmobQuery<>();
                query.findObjects(new FindListener<Meal>() {
                    @Override
                    public void done(List<Meal> list, BmobException e) {
                        dismissLoadingDialog();
                        if (e == null) {
                            buildRecommendDialog(getMealRandomly(preDo(list)));
                        } else {

                        }
                    }
                });
            }
        });
        mTestScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.showPhotoChoiceDialog(MealRecommendActivity.this,
                        MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE, MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            BiCallback<Bitmap> callback = new BiCallback<Bitmap>() {
                @Override
                public void done(Bitmap bitmap) {
                    doMealRecognition(AndroidUtils.convertBitmapToBase64(bitmap));
                }

                @Override
                public void error(String message) {
                    Toasty.warning(getApplicationContext(), message, Toast.LENGTH_SHORT, true).show();
                }
            };
            switch (requestCode) {
                case MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE:
                case MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE:
                    AndroidUtils.readPhotoFromIntent(this, data, callback);
                    break;
            }
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE:
                    dismissLoadingDialog();
                    String result = (String) msg.obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MealRecommendActivity.this);
                    builder.setMessage(result);
                    builder.create().show();
                    break;
                default:
            }
        }
    };

    private void doMealRecognition(final String base64) {
        showLoadingDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    final String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
                    String imgParam = URLEncoder.encode(base64, "UTF-8");
                    final String param = "image=" + imgParam + "&top_num=" + 5;
                    String accessToken = BaiduAuth.getAuth();
                    result = HttpUtil.post(url, accessToken, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    private List<Meal> preDo(List<Meal> netData) {
        for (Meal unlike : unlikeList) {
            for (int i = 0; i < netData.size(); i++) {
                if (unlike.getObjectId().equals(netData.get(i).getObjectId())) {
                    netData.remove(i);
                }
            }
        }
        netData.addAll(likeList);
        return netData;
    }

    @Deprecated
    private Meal getMealRandomly(@NonNull List<Meal> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        int randomIndex = (int) (Math.random() * size);
        return list.get(randomIndex);
    }

    private void buildRecommendDialog(@NonNull final Meal meal) {
        if (meal == null) {
            return;
        }

        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_make_recommend, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        SimpleDraweeView mealImageView = layout.findViewById(R.id.iv_image);
        TextView mealNameView = layout.findViewById(R.id.tv_label);
        View yesView = layout.findViewById(R.id.tv_yes);
        View noView = layout.findViewById(R.id.tv_no);

        yesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeList.add(meal);
                dialog.dismiss();
            }
        });
        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlikeList.add(meal);
                dialog.dismiss();
            }
        });

        FrescoUtils.setImageUrl(mealImageView, meal.getMealImageUrl());
        mealNameView.setText(meal.getMealName());

        dialog.show();
    }
}
