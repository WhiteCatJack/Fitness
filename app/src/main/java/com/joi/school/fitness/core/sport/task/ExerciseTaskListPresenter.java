package com.joi.school.fitness.core.sport.task;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.transform.ExerciseTaskWrapper;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;

import org.json.JSONException;

import java.util.ArrayList;
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
class ExerciseTaskListPresenter implements IExerciseTaskListContract.Presenter {

    private ExerciseTaskListActivity mView;

    public ExerciseTaskListPresenter(ExerciseTaskListActivity activity) {
        this.mView = activity;
    }

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void getTaskList() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<ExerciseTaskWrapper> resultList = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date date = calendar.getTime();
                BmobDate today00 = new BmobDate(date);

                try {
                    SyncBmobQuery<DoingExerciseTask> doingExerciseTaskSyncBmobQuery =
                            new SyncBmobQuery<>(DoingExerciseTask.class);
                    doingExerciseTaskSyncBmobQuery.addWhereEqualTo(
                            "user", UserEngine.getInstance().getCurrentUser().getObjectId());
                    doingExerciseTaskSyncBmobQuery.addWhereGreaterThanOrEqualTo("createdAt", today00);
                    doingExerciseTaskSyncBmobQuery.include("task");
                    final List<DoingExerciseTask> doingExerciseTaskList = doingExerciseTaskSyncBmobQuery.syncFindObjects();
                    if (doingExerciseTaskList.size() > 0) {
                        final DoingExerciseTask doingExerciseTask = doingExerciseTaskList.get(0);
                        mView.runOnUiThread(new Runnable() {
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
                    taskListQuery.addWhereGreaterThanOrEqualTo("time", today00);

                    SyncBmobQuery<Sport> sportQuery = new SyncBmobQuery<>(Sport.class);
                    List<Sport> sportList = sportQuery.syncFindObjects();

                    final List<ExerciseTask> result = taskListQuery.syncFindObjects();

                    for (ExerciseTask exerciseTask : result) {
                        ExerciseTaskWrapper wrapper = new ExerciseTaskWrapper(exerciseTask);
                        List<Sport> taskSportList = new ArrayList<>();
                        List<String> sportIdList = wrapper.getSportIdList();
                        for (String id : sportIdList) {
                            for (Sport sport : sportList) {
                                if (sport.getObjectId().equals(id)) {
                                    taskSportList.add(sport);
                                }
                            }
                        }
                        wrapper.setSportList(taskSportList);
                        resultList.add(wrapper);
                    }

                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showTaskList(resultList);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView, e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void chooseTask(final ExerciseTaskWrapper taskWrapper) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final DoingExerciseTask doingExerciseTask = new DoingExerciseTask()
                            .setUser(UserEngine.getInstance().getCurrentUser())
                            .setTask(taskWrapper.getTask());
                    doingExerciseTask.syncSave();
                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.completeChooseTask(doingExerciseTask);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView, e);
                }
            }
        });
    }
}
