package com.joi.school.fitness.core.sport.uploadexercise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.user.UserEngine;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import es.dmoral.toasty.Toasty;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/7 0007 17:14
 */
public class UploadExerciseActivity extends BaseActivity {

    private List<Sport> mSportList;
    private ExerciseListAdapter mAdapter;
    private RecyclerView mExerciseRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_excercise);

        mExerciseRecyclerView = findViewById(R.id.rv_exercise_list);

        mSportList = new ArrayList<>();
        mAdapter = new ExerciseListAdapter(mSportList);
        mAdapter.setOnItemClickListener(new ExerciseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Sport sport) {
                showLoadingDialog();
                HeatRecord heatRecord = new HeatRecord();
                heatRecord.setUser(UserEngine.getInstance().getCurrentUser());
                heatRecord.setHeatChange(-sport.getCalories());
                heatRecord.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        dismissLoadingDialog();
                        if (e == null) {
                            Toasty.normal(getApplicationContext(), R.string.hint_upload_completed_exercise_complete).show();
                            finish();
                        } else {
                            AndroidUtils.showUnknownErrorToast();
                        }
                    }
                });
            }
        });

        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExerciseRecyclerView.setAdapter(mAdapter);

        showLoadingDialog();
        BmobQuery<Sport> query = new BmobQuery<>();
        query.findObjects(new FindListener<Sport>() {
            @Override
            public void done(List<Sport> list, BmobException e) {
                dismissLoadingDialog();
                if (e == null) {
                    showExerciseList(list);
                } else {
                }
            }
        });
    }

    private void showExerciseList(List<Sport> data) {
        mSportList.clear();
        mSportList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
