package com.joi.school.fitness.core.sport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.ExerciseTask;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class SportRecommendActivity extends BaseActivity implements ISportRecommendContract.View {

    private RecyclerView mTaskListRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_recommend);


    }

    @Override
    public void showTaskList(List<ExerciseTask> taskList) {

    }
}
