package com.joi.school.fitness.tools.bean;

import cn.bmob.v3.BmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/7 0007 17:41
 */
public class PunchRecord extends BmobObject {
    private FitnessUser user;
    private Sport completeExercise;

    public FitnessUser getUser() {
        return user;
    }

    public void setUser(FitnessUser user) {
        this.user = user;
    }

    public Sport getCompleteExercise() {
        return completeExercise;
    }

    public void setCompleteExercise(Sport completeExercise) {
        this.completeExercise = completeExercise;
    }
}
