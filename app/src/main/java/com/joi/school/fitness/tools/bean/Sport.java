package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class Sport extends SyncBmobObject {
    private String sportKind;
    private int difficulty;
    private String sportImageUrl;
    private double calories;

    public String getSportKind() {
        return sportKind;
    }

    public Sport setSportKind(String sportKind) {
        this.sportKind = sportKind;
        return this;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Sport setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public String getSportImageUrl() {
        return sportImageUrl;
    }

    public Sport setSportImageUrl(String sportImageUrl) {
        this.sportImageUrl = sportImageUrl;
        return this;
    }

    public double getCalories() {
        return calories;
    }

    public Sport setCalories(double calories) {
        this.calories = calories;
        return this;
    }
}
