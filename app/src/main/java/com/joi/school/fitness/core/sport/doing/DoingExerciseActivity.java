package com.joi.school.fitness.core.sport.doing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.constant.IntentConstants;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class DoingExerciseActivity extends BaseActivity implements IDoingExerciseContract.View {

    private RecyclerView mSportListRecyclerView;
    private View mCompleteButton;

    private List<Sport> mSportTaskList = new ArrayList<>();
    private DoingExerciseListAdapter mAdapter;
    private DoingExerciseTask mDoingExerciseTask;

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

    private IDoingExerciseContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_exercise);

        if (!getDataFromIntent()) {
            finish();
            return;
        }
        mPresenter = new DoingExercisePresenter(this);

        mSportListRecyclerView = findViewById(R.id.rv_list);
        mCompleteButton = findViewById(R.id.bt_complete_task);

        mAdapter = new DoingExerciseListAdapter(mSportTaskList);
        mSportListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSportListRecyclerView.setAdapter(mAdapter);

        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.completeTask(mDoingExerciseTask);
            }
        });

        mPresenter.getSportList(mDoingExerciseTask.getTask());
    }

    @Override
    public void showSportList(List<Sport> sportList){
        mSportTaskList.clear();
        mSportTaskList.addAll(sportList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doneComplete() {
        AndroidUtils.showToast("你完成了今天的任务！");
        finish();
    }

    private boolean getDataFromIntent() {
        mDoingExerciseTask = (DoingExerciseTask) getIntent().getSerializableExtra(IntentConstants.INTENT_KEY_DOING_EXERCISE_TASK);
        return mDoingExerciseTask != null;
    }
}
