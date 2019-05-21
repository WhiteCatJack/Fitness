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

    private TextView mChartTitle;
    private LineChartView mChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_assesment, container, false);

        mChartTitle = layout.findViewById(R.id.tv_chart_title);
        mChart = layout.findViewById(R.id.chart);
        mChart.setInteractive(false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new AssessmentPresenter(this);
        mPresenter.getData(UserEngine.getInstance().getCurrentUser().getObjectId());
    }

    @Override
    public void showGraph(double[] incomeHeatRecordList, double[] outcomeHeatRecordList) {
        for (int i = 0; i < outcomeHeatRecordList.length; i++) {
            if (outcomeHeatRecordList[i] < 0) {
                outcomeHeatRecordList[i] = -outcomeHeatRecordList[i];
            }
        }

        setChartData(mChart, incomeHeatRecordList, outcomeHeatRecordList);
        mChart.setVisibility(View.VISIBLE);
        mChartTitle.setVisibility(View.VISIBLE);
    }

    private void setChartData(LineChartView chart, double[] income, double[] outCome) {
        int minLength = Math.min(income.length, outCome.length);

        List<PointValue> values = new ArrayList<>();
        for (int i = 1; i < minLength; i++) {
            values.add(new PointValue(i, (float) outCome[i]));
        }

        Line lineOutCome = new Line(values).setColor(
                AndroidUtils.getApplicationContext().getResources().getColor(R.color.colorPrimaryDark)
        ).setCubic(false);
        lineOutCome.setStrokeWidth(3);

        values = new ArrayList<>();
        for (int i = 1; i < minLength; i++) {
            values.add(new PointValue(i, (float) income[i]));
        }

        Line lineInCome = new Line(values).setColor(
                AndroidUtils.getApplicationContext().getResources().getColor(R.color.colorAccent)
        ).setCubic(false);
        lineInCome.setStrokeWidth(3);

        List<Line> lines = new ArrayList<>();
        lines.add(lineOutCome);
        lines.add(lineInCome);

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
