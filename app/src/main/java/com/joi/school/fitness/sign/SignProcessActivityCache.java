package com.joi.school.fitness.sign;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/*
 * @author 泽乾
 * createAt 2019/3/23 0023 15:26
 */
public class SignProcessActivityCache {

    private static List<Activity> mSignActivityList = new ArrayList<>();

    public static void registerActivity(@NonNull Activity activity) {
        if (activity == null) {
            return;
        }
        if (!mSignActivityList.contains(activity)) {
            mSignActivityList.add(activity);
        }
    }

    public static void unregisterActivity(@NonNull Activity activity) {
        if (activity == null) {
            return;
        }
        mSignActivityList.remove(activity);
    }

    public static void clearCache() {
        for (Activity activity : mSignActivityList) {
            if (activity == null) {
                continue;
            }
            activity.finish();
        }
        mSignActivityList.clear();
    }

}
