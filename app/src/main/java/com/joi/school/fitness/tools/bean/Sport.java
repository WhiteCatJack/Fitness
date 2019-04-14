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

    public void setSportKind(String sportKind) {
        this.sportKind = sportKind;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getSportImageUrl() {
        return sportImageUrl;
    }

    public void setSportImageUrl(String sportImageUrl) {
        this.sportImageUrl = sportImageUrl;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
