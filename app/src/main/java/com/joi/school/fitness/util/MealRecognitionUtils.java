package com.joi.school.fitness.util;

import com.baidu.aip.imageclassify.AipImageClassify;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/4 0004 12:52
 */
public class MealRecognitionUtils {
    private static AipImageClassify sInstance;

    public static AipImageClassify getsInstance() {
        return sInstance;
    }

    public static void setsInstance(AipImageClassify sInstance) {
        MealRecognitionUtils.sInstance = sInstance;
    }
}
