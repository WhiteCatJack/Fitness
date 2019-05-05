package com.joi.school.fitness.core.sport.task;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.transform.ExerciseTaskWrapper;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/17 0017 21:21
 */
class ExerciseTaskViewHolder extends RecyclerView.ViewHolder {

    private View layout;
    private TextView contentTextView;

    private OnItemClickListener<ExerciseTaskWrapper> mOnItemClickListener;

    public ExerciseTaskViewHolder(@NonNull View itemView) {
        super(itemView);

        bindLayout();
    }

    private void bindLayout() {
        layout = itemView.findViewById(R.id.cv_layout);
        contentTextView = itemView.findViewById(R.id.tv_content);
    }

    public void build(final ExerciseTaskWrapper task) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(task);
                }
            }
        });
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < task.getSportList().size(); i++) {
            Sport sport = task.getSportList().get(i);
            int timeInMinutes = task.getTimeInMinuteList().get(i);
            builder.append(sport.getSportName());
            builder.append("(").append(timeInMinutes).append("min)");
            if (i != task.getSportList().size() - 1) {
                builder.append("\n");
            }
        }
        contentTextView.setText(builder.toString());
    }

    public ExerciseTaskViewHolder setOnItemClickListener(OnItemClickListener<ExerciseTaskWrapper> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }
}
