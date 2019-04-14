package com.joi.school.fitness.core.meal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.BaiduAuth;
import com.joi.school.fitness.tools.util.HttpUtil;
import com.joi.school.fitness.tools.util.MealRecognitionUtils;

import java.net.URLEncoder;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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

    private TextView mTestScanButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_recommend);

        mTestScanButton = findViewById(R.id.bt_test_meal_scan);

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
            switch (requestCode) {
                case MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE:
                case MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE:
                    Bitmap bitmap = AndroidUtils.readPhotoFromIntent(this, data);
                    if (bitmap != null) {
                        doMealRecognition(AndroidUtils.convertBitmapToBase64(bitmap));
                    } else {
                        AndroidUtils.showUnknownErrorToast();
                    }
                    break;
            }
        }
    }

    private void mealRecognitionComplete(String resultJson) {
        dismissLoadingDialog();

        Meal meal = MealRecognitionUtils.getResult(resultJson);
        if (meal == null) {
            AndroidUtils.showUnknownErrorToast();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MealRecommendActivity.this);
        builder.setMessage(resultJson);
        builder.create().show();

        HeatRecord heatRecord = new HeatRecord();
        heatRecord.setUser(UserEngine.getInstance().getCurrentUser());
        heatRecord.setHeatChange(meal.getCalories());
        heatRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                dismissLoadingDialog();
                if (e == null) {
                    Toasty.normal(getApplicationContext(), R.string.hint_upload_meal_complete).show();
                } else {
                    AndroidUtils.showUnknownErrorToast();
                }
            }
        });
    }

    private void doMealRecognition(final String base64) {
        showLoadingDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    final String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
                    String imgParam = URLEncoder.encode(base64, "UTF-8");
                    final String param = "image=" + imgParam + "&top_num=" + 5 + "&baike_num=" + 1;
                    String accessToken = BaiduAuth.getAuth();
                    result = HttpUtil.post(url, accessToken, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String finalResult = result;
                MealRecommendActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mealRecognitionComplete(finalResult);
                    }
                });
            }
        }).start();
    }

}
