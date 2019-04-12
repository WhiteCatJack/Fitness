package com.joi.school.fitness.sign;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于在完成登录流程之后同时将登陆流程中的Activity全部释放
 * 设计机制：
 * 启动Activity时将其注册到此处，当Activity正常自己结束时取消注册
 * 完成登录流程时调用{@link #clearCache() clearCache}方法将当前注册的Activity全部释放
 *
 * @author Joi
 * createAt 2019/3/23 0023 15:26
 */
class SignProcessActivityCache {

    private static List<Activity> sSignActivityList;

    static void registerActivity(@NonNull Activity activity) {
        if (activity == null) {
            return;
        }
        if (sSignActivityList == null) {
            sSignActivityList = new ArrayList<>();
        }
        if (!sSignActivityList.contains(activity)) {
            sSignActivityList.add(activity);
        }
    }

    static void unregisterActivity(@NonNull Activity activity) {
        if (activity == null || sSignActivityList == null || sSignActivityList.isEmpty()) {
            return;
        }
        sSignActivityList.remove(activity);
    }

    static void clearCache() {
        if (sSignActivityList == null) {
            return;
        }
        for (Activity activity : sSignActivityList) {
            if (activity == null) {
                continue;
            }
            activity.finish();
        }
        sSignActivityList.clear();
        sSignActivityList = null;
    }

}
