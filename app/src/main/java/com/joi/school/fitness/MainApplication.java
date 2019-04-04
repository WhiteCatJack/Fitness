package com.joi.school.fitness;

import android.app.Application;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.joi.school.fitness.constant.Constants;
import com.joi.school.fitness.util.MealRecognitionUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author Joi
 * createAt 2019/3/22 0022 16:21
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initBmob();
        initFresco();
        initBaiduPicRecognition();

        generateFakeData();
    }

    private void initBaiduPicRecognition() {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(Constants.BAIDU_APP_ID, Constants.BAIDU_APP_KEY, Constants.BAIDU_SECRET_KEY);
        MealRecognitionUtils.setsInstance(client);
    }

    private void generateFakeData() {
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId(Constants.BMOB_KEY)
                .setConnectTimeout(Constants.BMOB_NETWORK_LAG_TIME_LIMIT_IN_SECONDS)
                .build();
        Bmob.initialize(config);
    }
}
