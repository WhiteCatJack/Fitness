package com.joi.school.fitness.core.sport;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
class SportRecommendPresenter implements ISportRecommendContract.Presenter {

    private SportRecommendFragment mView;

    public SportRecommendPresenter(SportRecommendFragment activity) {
        this.mView = activity;
    }

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void getTaskList() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, 17);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 1);
                Date date = calendar.getTime();
                BmobDate today00 = new BmobDate(date);

                try {
                    SyncBmobQuery<DoingExerciseTask> doingExerciseTaskSyncBmobQuery =
                            new SyncBmobQuery<>(DoingExerciseTask.class);
                    doingExerciseTaskSyncBmobQuery.addWhereEqualTo(
                            "user", UserEngine.getInstance().getCurrentUser().getObjectId());
                    doingExerciseTaskSyncBmobQuery.addWhereGreaterThanOrEqualTo("createdAt", today00);
                    final List<DoingExerciseTask> doingExerciseTaskList = doingExerciseTaskSyncBmobQuery.syncFindObjects();
                    if (doingExerciseTaskList.size() > 0) {
                        final DoingExerciseTask doingExerciseTask = doingExerciseTaskList.get(0);
                        mView.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (doingExerciseTask.getComplete()){
                                    mView.todayTaskDone();
                                } else {
                                    mView.hasDoingTask(doingExerciseTask);
                                }
                            }
                        });
                        return;
                    }

                    SyncBmobQuery<ExerciseTask> taskListQuery = new SyncBmobQuery<>(ExerciseTask.class);
                    taskListQuery.addWhereEqualTo("targetUser", UserEngine.getInstance().getCurrentUser().getObjectId());
                    taskListQuery.addWhereGreaterThanOrEqualTo("createdAt", today00);

                    final List<ExerciseTask> result = taskListQuery.syncFindObjects();
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showTaskList(result);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView.getActivity(), e);
                }
            }
        });
    }

    @Override
    public void chooseTask(final ExerciseTask task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final DoingExerciseTask doingExerciseTask = new DoingExerciseTask()
                            .setUser(UserEngine.getInstance().getCurrentUser())
                            .setTask(task);
                    doingExerciseTask.syncSave();
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.completeChooseTask(doingExerciseTask);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView.getActivity(), e);
                }
            }
        });
    }
}
