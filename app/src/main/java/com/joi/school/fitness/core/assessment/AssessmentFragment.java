package com.joi.school.fitness.core.assessment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseFragment;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class AssessmentFragment extends BaseFragment implements IAssessmentContract.View {

    private IAssessmentContract.Presenter mPresenter;

    private LineChartView mOriginalChart;
    private LineChartView mIncomeChart;
    private TextView mOutcomeTitle;
    private LineChartView mOutcomeChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_assesment, container, false);

        mOriginalChart = layout.findViewById(R.id.chart_original);
        mIncomeChart = layout.findViewById(R.id.chart_income);
        mOutcomeTitle = layout.findViewById(R.id.tv_title_outcome);
        mOutcomeChart = layout.findViewById(R.id.chart_outcome);

        mOriginalChart.setInteractive(false);
        mIncomeChart.setInteractive(false);
        mOutcomeChart.setInteractive(false);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new AssessmentPresenter(this);
        mPresenter.getData(UserEngine.getInstance().getCurrentUser().getObjectId());
    }

    @Override
    public void showOriginalData(double[] heatRecordList) {

    }

    @Override
    public void showIncome(double[] heatRecordList) {
        setChartData(mIncomeChart, heatRecordList);
        mIncomeChart.setVisibility(View.VISIBLE);
        mIncomeChart.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOutcome(double[] heatRecordList) {
        for (int i = 0; i < heatRecordList.length; i++) {
            if (heatRecordList[i] < 0) {
                heatRecordList[i] = -heatRecordList[i];
            }
        }
        setChartData(mOutcomeChart, heatRecordList);
        mOutcomeChart.setVisibility(View.VISIBLE);
        mOutcomeTitle.setVisibility(View.VISIBLE);
    }

    private void setChartData(LineChartView chart, double[] array) {
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            values.add(new PointValue(i, (float) array[i]));
        }

        Line line = new Line(values).setColor(
                AndroidUtils.getApplicationContext().getResources().getColor(R.color.colorPrimaryDark)
        ).setCubic(true);
        line.setStrokeWidth(3);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axis = new Axis();
        axis.setLineColor(AndroidUtils.getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        axis.setTextColor(AndroidUtils.getApplicationContext().getResources().getColor(R.color.text_level1_color));
        axis.setMaxLabelChars(4);
        data.setAxisYLeft(axis);

        chart.setLineChartData(data);
    }
}
