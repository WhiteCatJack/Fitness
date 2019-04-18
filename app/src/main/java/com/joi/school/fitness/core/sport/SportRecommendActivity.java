package com.joi.school.fitness.core.sport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class SportRecommendActivity extends BaseActivity implements ISportRecommendContract.View {

    private RecyclerView mTaskListRecyclerView;
    private View mCompleteTaskLayout;

    private List<ExerciseTask> mExerciseTaskList = new ArrayList<>();
    private ExerciseTaskListAdapter mAdapter;

    private ISportRecommendContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SportRecommendPresenter(this);
        setContentView(R.layout.activity_sport_recommend);

        mTaskListRecyclerView = findViewById(R.id.rv_list);
        mCompleteTaskLayout = findViewById(R.id.fl_complete_task);

        mAdapter = new ExerciseTaskListAdapter(mExerciseTaskList);
        mAdapter.setOnItemClickListener(new OnItemClickListener<ExerciseTask>() {
            @Override
            public void onItemClick(final ExerciseTask data) {
                // 提示开始执行此运动task
                new AlertDialog.Builder(SportRecommendActivity.this)
                        .setMessage("确定要以此为今天的运动目标吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.chooseTask(data);
                            }
                        })
                        .setNegativeButton("否", null)
                        .create().show();
            }
        });
        mTaskListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTaskListRecyclerView.setAdapter(mAdapter);

        mPresenter.getTaskList();
    }

    @Override
    public void hasDoingTask(DoingExerciseTask task) {
        Navigation.goToDoingExerciseTaskActivity(this, task);
        finish();
    }

    @Override
    public void showTaskList(List<ExerciseTask> taskList) {
        mCompleteTaskLayout.setVisibility(View.GONE);
        mTaskListRecyclerView.setVisibility(View.VISIBLE);

        mExerciseTaskList.clear();
        mExerciseTaskList.addAll(taskList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void completeChooseTask(DoingExerciseTask task) {
        Navigation.goToDoingExerciseTaskActivity(this, task);
        finish();
    }

    @Override
    public void todayTaskDone() {
        mCompleteTaskLayout.setVisibility(View.VISIBLE);
        mTaskListRecyclerView.setVisibility(View.GONE);
    }
}
