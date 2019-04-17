package com.joi.school.fitness.core.sport.doing;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bean.Sport;

import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/17 0017 20:48
 */
interface IDoingExerciseContract {
    interface View {
        void showSportList(List<Sport> taskList);
        void doneComplete();
    }

    interface Presenter {
        void getSportList(ExerciseTask task);
        void completeTask(DoingExerciseTask task);
    }
}
