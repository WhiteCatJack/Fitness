package com.joi.school.fitness.core.sport.doing;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.transform.DoingExerciseTaskWrapper;
import com.joi.school.fitness.tools.transform.ExerciseTaskWrapper;
import com.joi.school.fitness.tools.user.UserEngine;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
class DoingExercisePresenter implements IDoingExerciseContract.Presenter {

    private DoingExerciseActivity mView;

    public DoingExercisePresenter(DoingExerciseActivity activity) {
        this.mView = activity;
    }

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void getSportList(final DoingExerciseTask doingExerciseTask) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ExerciseTaskWrapper exerciseTaskWrapper = new ExerciseTaskWrapper(doingExerciseTask.getTask());

                    List<BmobQuery<Sport>> queries = new ArrayList<>();
                    for (String id : exerciseTaskWrapper.getSportIdList()) {
                        BmobQuery<Sport> eq = new BmobQuery<>();
                        eq.addWhereEqualTo("objectId", id);
                        queries.add(eq);
                    }

                    SyncBmobQuery<Sport> query = new SyncBmobQuery<>(Sport.class);
                    query.or(queries);
                    final List<Sport> sportList = query.syncFindObjects();

                    List<Sport> taskSportList = new ArrayList<>();
                    List<String> sportIdList = exerciseTaskWrapper.getSportIdList();
                    for (String id : sportIdList) {
                        for (Sport sport : sportList) {
                            if (sport.getObjectId().equals(id)) {
                                taskSportList.add(sport);
                            }
                        }
                    }
                    exerciseTaskWrapper.setSportList(taskSportList);

                    final DoingExerciseTaskWrapper doingExerciseTaskWrapper = new DoingExerciseTaskWrapper();
                    doingExerciseTaskWrapper.setTask(doingExerciseTask);
                    doingExerciseTaskWrapper.setExerciseTaskWrapper(exerciseTaskWrapper);
                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.doneBuildWrapper(doingExerciseTaskWrapper);
                            mView.showSportList(sportList);
                        }
                    });
                } catch (BmobException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void completeTask(final DoingExerciseTaskWrapper taskWrapper) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DoingExerciseTask doingExerciseTask = taskWrapper.getTask();
                    doingExerciseTask.setComplete(true);
                    doingExerciseTask.syncUpdate();

                    ExerciseTaskWrapper exerciseTaskWrapper = taskWrapper.getExerciseTaskWrapper();

                    // get heat sum
                    double sumHeatChange = 0;
                    for (int i = 0; i < exerciseTaskWrapper.getSportList().size(); i++) {
                        Sport sport = exerciseTaskWrapper.getSportList().get(i);
                        int timeInMinutes = exerciseTaskWrapper.getTimeInMinuteList().get(i);
                        double caloriesPerUnit = sport.getCaloriesPerUnit();
                        double realCalories = -caloriesPerUnit * (timeInMinutes / 60.0);
                        sumHeatChange += realCalories;
                    }

                    new HeatRecord()
                            .setUser(UserEngine.getInstance().getCurrentUser())
                            .setType(HeatRecord.Type.TYPE_EXERCISE)
                            .setTime(new BmobDate(Calendar.getInstance().getTime()))
                            .setHeatChange(sumHeatChange)
                            .syncSave();

                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.doneComplete();
                        }
                    });
                } catch (BmobException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
