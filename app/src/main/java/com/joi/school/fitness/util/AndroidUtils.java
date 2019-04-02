package com.joi.school.fitness.util;

import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/1 0001 20:06
 */
public class AndroidUtils {
    public static Context getApplicationContext(){
        return Bmob.getApplicationContext();
    }
}
