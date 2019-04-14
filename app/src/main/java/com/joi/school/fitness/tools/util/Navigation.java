package com.joi.school.fitness.tools.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.joi.school.fitness.HomeActivity;
import com.joi.school.fitness.core.sport.uploadexercise.UploadExerciseActivity;
import com.joi.school.fitness.forum.postdetail.PostActivity;
import com.joi.school.fitness.forum.postdetail.comment.CommentActivity;
import com.joi.school.fitness.forum.postdetail.comment.newcomment.NewCommentActivity;
import com.joi.school.fitness.mine.body.SetPhysicalStatisticActivity;
import com.joi.school.fitness.mine.setinfo.SetMyInformationActivity;
import com.joi.school.fitness.sign.SignInActivity;
import com.joi.school.fitness.sign.SignUpActivity;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.constant.IntentConstants;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 14:37
 */
public class Navigation {
    private static void noExtraNavigation(Context context, Class target) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, target));
    }

    public static void goToPostActivity(@NonNull Context context, @NonNull Post post) {
        if (context == null || post == null) {
            return;
        }
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(IntentConstants.INTENT_KEY_POST, post);
        context.startActivity(intent);
    }

    public static void goToSignInActivity(@NonNull Context context) {
        noExtraNavigation(context, SignInActivity.class);
    }

    public static void goToHomeActivity(@NonNull Context context) {
        noExtraNavigation(context, HomeActivity.class);
    }

    public static void goToSignUpActivity(@NonNull Context context) {
        noExtraNavigation(context, SignUpActivity.class);
    }

    public static void goToSetPhysicalStatisticActivity(@NonNull Context context) {
        noExtraNavigation(context, SetPhysicalStatisticActivity.class);
    }

    public static void goToSetMyInformationActivity(@NonNull Context context) {
        noExtraNavigation(context, SetMyInformationActivity.class);
    }

    public static void goToUploadExerciseActivity(@NonNull Context context) {
        noExtraNavigation(context, UploadExerciseActivity.class);
    }

    public static void goToCommentActivity(@NonNull Context context, Post post) {
        if (context == null || post == null || TextUtils.isEmpty(post.getObjectId())) {
            return;
        }
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(IntentConstants.INTENT_KEY_POST, post);
        context.startActivity(intent);
    }

    public static void goToNewCommentActivity(@NonNull Context context, Post post) {
        if (context == null || post == null || TextUtils.isEmpty(post.getObjectId())) {
            return;
        }
        Intent intent = new Intent(context, NewCommentActivity.class);
        intent.putExtra(IntentConstants.INTENT_KEY_POST, post);
        context.startActivity(intent);
    }
}