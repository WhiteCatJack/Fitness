package com.joi.school.fitness.core.sport.doing;

import com.joi.school.fitness.tools.bean.DoingExerciseTask;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.datatype.BmobPointer;
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

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

    @Override
    public void getSportList(final ExerciseTask task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SyncBmobQuery<Sport> query = new SyncBmobQuery<>(Sport.class);
                    query.addWhereRelatedTo("recommendedSports", new BmobPointer(task));
                    final List<Sport> sportList = query.syncFindObjects();
                    mView.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showSportList(sportList);
                        }
                    });
                } catch (BmobException e) {
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
