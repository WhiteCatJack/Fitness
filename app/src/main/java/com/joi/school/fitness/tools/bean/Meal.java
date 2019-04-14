package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class Meal extends SyncBmobObject {
    private String mealName;
    private double calories;
    private String mealImageUrl;

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getMealImageUrl() {
        return mealImageUrl;
    }

    public void setMealImageUrl(String mealImageUrl) {
        this.mealImageUrl = mealImageUrl;
    }
}
