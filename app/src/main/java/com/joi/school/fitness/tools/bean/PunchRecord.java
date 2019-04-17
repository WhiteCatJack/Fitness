package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/7 0007 17:41
 */
public class PunchRecord extends SyncBmobObject {
    private FitnessUser user;
    private Sport completeExercise;

    public FitnessUser getUser() {
        return user;
    }

    public PunchRecord setUser(FitnessUser user) {
        this.user = user;
        return this;
    }

    public Sport getCompleteExercise() {
        return completeExercise;
    }

    public PunchRecord setCompleteExercise(Sport completeExercise) {
        this.completeExercise = completeExercise;
        return this;
    }
}
