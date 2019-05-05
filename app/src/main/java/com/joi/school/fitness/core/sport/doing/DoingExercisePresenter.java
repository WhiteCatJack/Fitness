package com.joi.school.fitness.core.sport.doing;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.transform.ExerciseTaskWrapper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.BmobQuery;
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
    public void getSportList(final ExerciseTask task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ExerciseTaskWrapper exerciseTaskWrapper = new ExerciseTaskWrapper(task);

                    List<BmobQuery<Sport>> queries = new ArrayList<>();
                    for (String id : exerciseTaskWrapper.getSportIdList()) {
                        BmobQuery<Sport> eq = new BmobQuery<>();
                        eq.addWhereEqualTo("objectId", id);
                        queries.add(eq);
                    }

                    SyncBmobQuery<Sport> query = new SyncBmobQuery<>(Sport.class);
                    query.or(queries);
                    final List<Sport> sportList = query.syncFindObjects();
                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
    public void completeTask(final DoingExerciseTask task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    task.setComplete(true);
                    task.syncUpdate();
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
