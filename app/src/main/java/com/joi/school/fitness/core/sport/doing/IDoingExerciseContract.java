package com.joi.school.fitness.core.sport.doing;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.transform.DoingExerciseTaskWrapper;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/17 0017 20:48
 */
interface IDoingExerciseContract {
    interface View {
        void doneBuildWrapper(DoingExerciseTaskWrapper taskWrapper);
        void showSportList(List<Sport> taskList, List<Integer> timeList);
        void doneComplete();
    }

    interface Presenter {
        void getSportList(DoingExerciseTask doingExerciseTask);
        void completeTask(DoingExerciseTaskWrapper taskWrapper);
    }
}
