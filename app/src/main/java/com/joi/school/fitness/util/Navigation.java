package com.joi.school.fitness.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.joi.school.fitness.constant.Constants;
import com.joi.school.fitness.forum.datasource.Post;
import com.joi.school.fitness.forum.postdetail.PostActivity;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/1 0001 14:37
 */
public class Navigation {
    public static void goToPostActivity(@NonNull Context context, @NonNull Post post) {
        if (context == null || post == null) {
            return;
        }
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(Constants.INTENT_KEY_POST, post);
        context.startActivity(intent);
    }
}
