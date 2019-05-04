package com.joi.school.fitness.core.sport;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.transform.ExerciseTaskWrapper;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/17 0017 20:48
 */
interface ISportRecommendContract {
    interface View {
        void hasDoingTask(DoingExerciseTask doingExerciseTask);

        void showTaskList(List<ExerciseTaskWrapper> taskList);

        void completeChooseTask(DoingExerciseTask task);

        void todayTaskDone();
    }

    interface Presenter {
        void getTaskList();

        void chooseTask(ExerciseTaskWrapper task);
    }
}
