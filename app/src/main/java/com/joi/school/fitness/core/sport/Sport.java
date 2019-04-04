package com.joi.school.fitness.core.sport;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class Sport {
    private String sportKind;
    private int difficulty;
    private String sportImageUrl;

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
}
