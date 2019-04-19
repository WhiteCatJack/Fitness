package com.joi.school.fitness.core.sport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.ExerciseTask;
import com.joi.school.fitness.tools.util.AndroidUtils;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/17 0017 21:21
 */
class ExerciseTaskViewHolder extends RecyclerView.ViewHolder {

    private View layout;
    private ImageView coverImageView;
    private TextView titleTextView;

    private OnItemClickListener<ExerciseTask> mOnItemClickListener;

    public ExerciseTaskViewHolder(@NonNull View itemView) {
        super(itemView);

        bindLayout();
    }

    private void bindLayout() {
        layout = itemView.findViewById(R.id.cv_layout);
        coverImageView = itemView.findViewById(R.id.iv_cover);
        titleTextView = itemView.findViewById(R.id.tv_title);
    }

    public void build(final ExerciseTask task) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(task);
                }
            }
        });
        coverImageView.setBackground(AndroidUtils.getApplicationContext().getResources().getDrawable(R.drawable.ui_placeholder));
        titleTextView.setText("运动任务");
    }

    public ExerciseTaskViewHolder setOnItemClickListener(OnItemClickListener<ExerciseTask> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }
}
