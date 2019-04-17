package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class MealRecommend extends SyncBmobObject {
    private FitnessUser targetUser;
    private BmobRelation recommendedMeals;

    public FitnessUser getTargetUser() {
        return targetUser;
    }

    public MealRecommend setTargetUser(FitnessUser targetUser) {
        this.targetUser = targetUser;
        return this;
    }

    public BmobRelation getRecommendedMeals() {
        return recommendedMeals;
    }

    public MealRecommend setRecommendedMeals(BmobRelation recommendedMeals) {
        this.recommendedMeals = recommendedMeals;
        return this;
    }
}
