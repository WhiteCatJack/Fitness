package com.joi.school.fitness.tools.transform;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/5/5 0005 14:02
 */
public class DoingExerciseTaskWrapper {
    private DoingExerciseTask task;
    private ExerciseTaskWrapper exerciseTaskWrapper;

    public DoingExerciseTask getTask() {
        return task;
    }

    public DoingExerciseTaskWrapper setTask(DoingExerciseTask task) {
        this.task = task;
        return this;
    }

    public ExerciseTaskWrapper getExerciseTaskWrapper() {
        return exerciseTaskWrapper;
    }

    public DoingExerciseTaskWrapper setExerciseTaskWrapper(ExerciseTaskWrapper exerciseTaskWrapper) {
        this.exerciseTaskWrapper = exerciseTaskWrapper;
        return this;
    }
}
