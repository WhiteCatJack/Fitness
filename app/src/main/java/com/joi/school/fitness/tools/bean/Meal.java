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

    public Meal setMealName(String mealName) {
        this.mealName = mealName;
        return this;
    }

    public double getCalories() {
        return calories;
    }

    public Meal setCalories(double calories) {
        this.calories = calories;
        return this;
    }

    public String getMealImageUrl() {
        return mealImageUrl;
    }

    public Meal setMealImageUrl(String mealImageUrl) {
        this.mealImageUrl = mealImageUrl;
        return this;
    }
}
