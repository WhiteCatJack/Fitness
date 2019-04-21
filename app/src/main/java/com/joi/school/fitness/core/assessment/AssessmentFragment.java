package com.joi.school.fitness.core.assessment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseFragment;
import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class AssessmentFragment extends BaseFragment {
    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_assesment, container, false);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getHeatRecord();
    }

    private void getHeatRecord() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SyncBmobQuery<HeatRecord> query = new SyncBmobQuery<>(HeatRecord.class);
                query.addWhereEqualTo("user", UserEngine.getInstance().getCurrentUser().getObjectId());
                try {
                    final List<HeatRecord> heatRecordList = query.syncFindObjects();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(getActivity(), e);
                }
            }
        });
    }

}
