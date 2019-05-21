package com.joi.school.fitness.core.assessment;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/5/4 0004 17:44
 */
public interface IAssessmentContract {
    interface View {
        void showGraph(double[] incomeHeatRecordList, double[] outcomeHeatRecordList);
    }

    interface Presenter {
        void getData(String userId);
    }
}
