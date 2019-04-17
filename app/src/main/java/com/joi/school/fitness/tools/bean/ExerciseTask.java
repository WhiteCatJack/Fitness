package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class ExerciseTask extends SyncBmobObject {
    private FitnessUser targetUser;
    private BmobRelation recommendedSports;

    public FitnessUser getTargetUser() {
        return targetUser;
    }

    public ExerciseTask setTargetUser(FitnessUser targetUser) {
        this.targetUser = targetUser;
        return this;
    }

    public BmobRelation getRecommendedSports() {
        return recommendedSports;
    }

    public ExerciseTask setRecommendedSports(BmobRelation recommendedMeals) {
        this.recommendedSports = recommendedMeals;
        return this;
    }
}
