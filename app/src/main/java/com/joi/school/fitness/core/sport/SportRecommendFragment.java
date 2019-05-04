package com.joi.school.fitness.core.sport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseFragment;
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
public class SportRecommendFragment extends BaseFragment implements ISportRecommendContract.View {

    private RecyclerView mTaskListRecyclerView;
    private View mCompleteTaskLayout;

    private List<ExerciseTaskWrapper> mExerciseTaskList = new ArrayList<>();
    private ExerciseTaskListAdapter mAdapter;

    private ISportRecommendContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_sport_recommend, container, false);

        mTaskListRecyclerView = layout.findViewById(R.id.rv_list);
        mCompleteTaskLayout = layout.findViewById(R.id.fl_complete_task);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = new SportRecommendPresenter(this);

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
    }

    @Override
    public void todayTaskDone() {
        mCompleteTaskLayout.setVisibility(View.VISIBLE);
        mTaskListRecyclerView.setVisibility(View.GONE);
    }
}
