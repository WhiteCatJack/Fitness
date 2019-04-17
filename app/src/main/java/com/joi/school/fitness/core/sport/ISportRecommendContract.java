package com.joi.school.fitness.core.sport;

import com.joi.school.fitness.tools.bean.ExerciseTask;

import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/17 0017 20:48
 */
public interface ISportRecommendContract {
    interface View {
        void showTaskList(List<ExerciseTask> taskList);
    }

    interface Presenter {
        void getTaskList();
    }
}
