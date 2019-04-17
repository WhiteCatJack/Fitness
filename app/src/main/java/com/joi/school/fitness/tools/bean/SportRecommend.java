package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class SportRecommend extends SyncBmobObject {
    private FitnessUser targetUser;
    private List<Sport> recommendedSports;
    private double calories;

    public FitnessUser getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(FitnessUser targetUser) {
        this.targetUser = targetUser;
    }

    public List<Sport> getRecommendedSports() {
        return recommendedSports;
    }

    public void setRecommendedSports(List<Sport> recommendedSports) {
        this.recommendedSports = recommendedSports;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
