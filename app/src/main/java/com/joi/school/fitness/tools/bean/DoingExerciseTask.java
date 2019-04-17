package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class DoingExerciseTask extends SyncBmobObject {
    private FitnessUser user;
    private ExerciseTask task;
    private Boolean complete = false;

    public FitnessUser getUser() {
        return user;
    }

    public DoingExerciseTask setUser(FitnessUser user) {
        this.user = user;
        return this;
    }

    public ExerciseTask getTask() {
        return task;
    }

    public DoingExerciseTask setTask(ExerciseTask task) {
        this.task = task;
        return this;
    }

    public Boolean getComplete() {
        return complete;
    }

    public DoingExerciseTask setComplete(Boolean complete) {
        this.complete = complete;
        return this;
    }
}
