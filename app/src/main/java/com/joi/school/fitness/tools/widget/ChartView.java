package com.joi.school.fitness.tools.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/19 0019 20:55
 */
public class ChartView extends View {

    private List<Double> mValues;

    public ChartView(Context context) {
        super(context);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mValues == null) {
            return;
        }
        // draw axis
        // draw line
    }

    public void setValues(List<Double> values) {
        this.mValues = values;
    }
}
