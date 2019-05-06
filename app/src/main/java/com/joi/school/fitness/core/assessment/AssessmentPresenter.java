package com.joi.school.fitness.core.assessment;

import com.joi.school.fitness.tools.bean.HeatRecord;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/5/4 0004 17:44
 */
public class AssessmentPresenter implements IAssessmentContract.Presenter {
    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    private AssessmentFragment mView;

    public AssessmentPresenter(AssessmentFragment mView) {
        this.mView = mView;
    }

    @Override
    public void getData(final String userId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SyncBmobQuery<HeatRecord> query = new SyncBmobQuery<>(HeatRecord.class);
                query.addWhereEqualTo("user", userId);

                Calendar thirtyDaysBefore = Calendar.getInstance();
                thirtyDaysBefore.set(Calendar.HOUR, 0);
                thirtyDaysBefore.set(Calendar.MINUTE, 0);
                thirtyDaysBefore.set(Calendar.SECOND, 0);
                thirtyDaysBefore.add(Calendar.DATE, -30);
                query.addWhereGreaterThanOrEqualTo("time", new BmobDate(thirtyDaysBefore.getTime()));

                query.groupby(new String[]{"time"});
                query.order("time");
                try {
                    final List<HeatRecord> heatRecordList = query.syncFindObjects();
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showOutcome(transform(getOutcomeData(heatRecordList)));
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView.getActivity(), e);
                }
            }
        });
    }

    private List<HeatRecord> getOutcomeData(List<HeatRecord> heatRecordList) {
        List<HeatRecord> ret = new ArrayList<>();
        for (HeatRecord heatRecord : heatRecordList) {
            if (heatRecord == null) {
                continue;
            }
            if (heatRecord.getType() == HeatRecord.Type.TYPE_EXERCISE) {
                ret.add(heatRecord);
            }
        }
        return ret;
    }

    private double[] transform(List<HeatRecord> heatRecordList) {
        List<Double> doubleList = new ArrayList<>();
        for (HeatRecord record : heatRecordList) {
            doubleList.add(record.getHeatChange());
        }
        double[] ret = new double[doubleList.size()];
        for (int i = 0; i < doubleList.size(); i++) {
            ret[i] = doubleList.get(i);
        }
        return ret;
    }

}
