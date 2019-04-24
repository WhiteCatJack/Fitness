package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class Sport extends SyncBmobObject {
    private String sportName;
    private int difficulty;
    private String sportImageUrl;
    private String calorieUnit;
    private double caloriesPerUnit;

    public String getSportName() {
        return sportName;
    }

    public Sport setSportName(String sportName) {
        this.sportName = sportName;
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

    public String getCalorieUnit() {
        return calorieUnit;
    }

    public Sport setCalorieUnit(String calorieUnit) {
        this.calorieUnit = calorieUnit;
        return this;
    }

    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public Sport setCaloriesPerUnit(double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
        return this;
    }
}
