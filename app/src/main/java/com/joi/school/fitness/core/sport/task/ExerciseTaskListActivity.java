package com.joi.school.fitness.core.sport.task;

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
import com.joi.school.fitness.tools.transform.ExerciseTaskWrapper;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class ExerciseTaskListActivity extends BaseActivity implements IExerciseTaskListContract.View {

    private RecyclerView mTaskListRecyclerView;
    private View mCompleteTaskLayout;

    private List<ExerciseTaskWrapper> mExerciseTaskList = new ArrayList<>();
    private ExerciseTaskListAdapter mAdapter;

    private IExerciseTaskListContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_task);

        mTaskListRecyclerView = findViewById(R.id.rv_list);
        mCompleteTaskLayout = findViewById(R.id.fl_complete_task);

        mPresenter = new ExerciseTaskListPresenter(this);

        mAdapter = new ExerciseTaskListAdapter(mExerciseTaskList);
        mAdapter.setOnItemClickListener(new OnItemClickListener<ExerciseTaskWrapper>() {
            @Override
            public void onItemClick(final ExerciseTaskWrapper data) {
                // 提示开始执行此运动task
                new AlertDialog.Builder(getContext())
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
        mTaskListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTaskListRecyclerView.setAdapter(mAdapter);

        mPresenter.getTaskList();
    }

    @Override
    public void hasDoingTask(DoingExerciseTask task) {
        Navigation.goToDoingExerciseTaskActivity(getContext(), task);
        finish();
    }

    @Override
    public void showTaskList(List<ExerciseTaskWrapper> taskList) {
        mCompleteTaskLayout.setVisibility(View.GONE);
        mTaskListRecyclerView.setVisibility(View.VISIBLE);

        mExerciseTaskList.clear();
        mExerciseTaskList.addAll(taskList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void completeChooseTask(DoingExerciseTask task) {
        Navigation.goToDoingExerciseTaskActivity(getContext(), task);
        finish();
    }

    @Override
    public void todayTaskDone() {
        mCompleteTaskLayout.setVisibility(View.VISIBLE);
        mTaskListRecyclerView.setVisibility(View.GONE);
    }
}
