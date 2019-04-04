package com.joi.school.fitness.user;

import android.support.annotation.Nullable;

import cn.bmob.v3.BmobUser;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 16:20
 */
public class UserEngine {

    @Nullable
    public static FitnessUser getCurrentUser() {
        return BmobUser.getCurrentUser(FitnessUser.class);
    }

    public static boolean hasPysicalStatistic() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getLatestStatistic() != null;
        }
        return false;
    }
}
