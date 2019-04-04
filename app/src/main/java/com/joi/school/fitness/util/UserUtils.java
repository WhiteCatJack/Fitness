package com.joi.school.fitness.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.joi.school.fitness.constant.BroadcastConstants;
import com.joi.school.fitness.sign.SignInActivity;

import cn.bmob.v3.BmobUser;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/23 0023 21:43
 */
public class UserUtils {

    public static void signOut(@NonNull Context context) {
        if (context == null) {
            return;
        }
        BmobUser.logOut();

        context.startActivity(new Intent(context, SignInActivity.class));

        Intent intent = new Intent();
        intent.setAction(BroadcastConstants.BROADCAST_USER_SIGN_OUT);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
