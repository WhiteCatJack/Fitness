package com.joi.school.fitness.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.joi.school.fitness.constant.BroadcastConstants;

import cn.bmob.v3.BmobUser;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/3/23 0023 21:43
 */
public class UserUtils {

    public static void signOut(@NonNull Context applicationContext) {
        if (applicationContext == null) {
            return;
        }
        BmobUser.logOut();
        Intent intent = new Intent();
        intent.setAction(BroadcastConstants.BROADCAST_USER_SIGN_OUT);
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);
    }
}
