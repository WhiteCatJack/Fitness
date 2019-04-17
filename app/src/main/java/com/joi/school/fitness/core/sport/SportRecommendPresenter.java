package com.joi.school.fitness.core.sport;

import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.user.UserEngine;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class SportRecommendPresenter implements ISportRecommendContract.Presenter {

    private SportRecommendActivity mView;

    public SportRecommendPresenter(SportRecommendActivity activity) {
        this.mView = activity;
    }

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

    @Override
    public void getTaskList() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 1);
                Date date = calendar.getTime();
                BmobDate today00 = new BmobDate(date);

                SyncBmobQuery<ExerciseTask> taskListQuery = new SyncBmobQuery<>(ExerciseTask.class);
                taskListQuery.addWhereEqualTo("targetUser", UserEngine.getInstance().getCurrentUser().getObjectId());
                taskListQuery.addWhereGreaterThanOrEqualTo("createdAt", today00);

                try {
                    final List<ExerciseTask> result = taskListQuery.syncFindObjects();
                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showTaskList(result);
                        }
                    });
                } catch (BmobException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
