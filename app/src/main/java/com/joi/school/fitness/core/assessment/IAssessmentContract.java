package com.joi.school.fitness.core.assessment;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/5/4 0004 17:44
 */
public interface IAssessmentContract {
    interface View {
        void showOriginalData(double[] heatRecordList);

        void showIncome(double[] heatRecordList);

        void showOutcome(double[] heatRecordList);
    }

    interface Presenter {
        void getData(String userId);
    }
}
