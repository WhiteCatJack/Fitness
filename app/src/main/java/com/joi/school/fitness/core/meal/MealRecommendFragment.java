package com.joi.school.fitness.core.meal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseFragment;
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

import static android.app.Activity.RESULT_OK;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class MealRecommendFragment extends BaseFragment {

    private static final int MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE = 1;
    private static final int MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE = 2;

    private TextView mTestScanButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_meal_recommend, container, false);
        mTestScanButton = layout.findViewById(R.id.bt_test_meal_scan);

        mTestScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.showPhotoChoiceDialog(MealRecommendFragment.this,
                        MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE, MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE);
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE:
                case MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE:
                    Bitmap bitmap = AndroidUtils.readPhotoFromIntent(getActivity(), data);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                    AndroidUtils.showToast(R.string.hint_upload_meal_complete);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mealRecognitionComplete(finalResult);
                    }
                });
            }
        }).start();
    }

}
