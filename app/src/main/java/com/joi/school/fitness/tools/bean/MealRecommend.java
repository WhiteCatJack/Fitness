package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class MealRecommend extends SyncBmobObject {
    private FitnessUser targetUser;
    private List<Meal> recommendedMeals;

    public FitnessUser getTargetUser() {
        return targetUser;
    }

    public MealRecommend setTargetUser(FitnessUser targetUser) {
        this.targetUser = targetUser;
        return this;
    }

    public List<Meal> getRecommendedMeals() {
        return recommendedMeals;
    }

    public MealRecommend setRecommendedMeals(List<Meal> recommendedMeals) {
        this.recommendedMeals = recommendedMeals;
        return this;
    }
}
