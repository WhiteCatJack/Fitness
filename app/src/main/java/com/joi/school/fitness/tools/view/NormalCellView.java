package com.joi.school.fitness.tools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.R;

/**
 * @author Joi
 * createAt 2019/4/2 0002 15:13
 */
public class NormalCellView extends FrameLayout {

    private ImageView iconView;
    private TextView labelView;
    private View arrowView;
    private View bottomDividerView;

    public NormalCellView(@NonNull Context context) {
        super(context);
        initView(null);
    }

    public NormalCellView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public NormalCellView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.widget_cell_view, this, true);
        iconView = layout.findViewById(R.id.iv_ic);
        labelView = layout.findViewById(R.id.tv_label);
        arrowView = layout.findViewById(R.id.iv_arrow);
        bottomDividerView = layout.findViewById(R.id.v_divider);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NormalCellView);
        String labelText = a.getString(R.styleable.NormalCellView_labelText);
        boolean arrowVisible = a.getBoolean(R.styleable.NormalCellView_arrowVisible, true);
        boolean dividerVisible = a.getBoolean(R.styleable.NormalCellView_dividerVisible, true);
        a.recycle();

        setLabelText(labelText);
        setArrowVisibility(arrowVisible);
        setDividerVisibility(dividerVisible);
    }

    public void setIconUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            iconView.setVisibility(GONE);
            return;
        }
        Glide.with(iconView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.ui_placeholder)
                .into(iconView);
        iconView.setVisibility(VISIBLE);
    }

    public void setLabelText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        labelView.setText(text);
    }

    public void setArrowVisibility(boolean visible) {
        if (visible) {
            arrowView.setVisibility(VISIBLE);
        } else {
            arrowView.setVisibility(GONE);
        }
    }

    public void setDividerVisibility(boolean visible) {
        if (visible) {
            bottomDividerView.setVisibility(VISIBLE);
        } else {
            bottomDividerView.setVisibility(GONE);
        }
    }
}
