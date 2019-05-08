package com.joi.school.fitness;

import android.graphics.Bitmap;

import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.BaiduAuth;
import com.joi.school.fitness.tools.util.HttpUtil;
import com.joi.school.fitness.tools.util.MealRecognitionUtils;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/5/4 0004 17:29
 */
public class HomePresenter implements IHomeContract.Presenter {

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    private HomeActivity mView;

    public HomePresenter(HomeActivity mView) {
        this.mView = mView;
    }

    @Override
    public void doMealRecognition(Bitmap bitmap, final int type) {
        final String base64 = AndroidUtils.convertBitmapToBase64(bitmap);
        mExecutor.execute(new Runnable() {
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
                final Meal meal = MealRecognitionUtils.getResult(result);

                mView.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.mealRecognitionDone(meal, type);
                    }
                });
            }
        });
    }

    @Override
    public void uploadMealHeatRecord(Meal meal, int type) {
        HeatRecord heatRecord = new HeatRecord();
        heatRecord.setUser(UserEngine.getInstance().getCurrentUser());
        heatRecord.setType(type);
        heatRecord.setHeatChange(meal.getCalories());
        heatRecord.setTime(new BmobDate(Calendar.getInstance().getTime()));
        heatRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    AndroidUtils.showToast(R.string.hint_upload_meal_complete);
                } else {
                    AndroidUtils.showUnknownErrorToast();
                }
            }
        });
    }
}
