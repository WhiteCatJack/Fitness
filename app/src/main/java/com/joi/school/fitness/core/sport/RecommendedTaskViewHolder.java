package com.joi.school.fitness.core.sport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.transform.SportRecommendWrapper;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/5/4 0004 21:23
 */
class RecommendedTaskViewHolder extends RecyclerView.ViewHolder {

    private View layout;
    private TextView contentTextView;
    private OnItemClickListener<SportRecommendWrapper> mOnItemClickListener;

    public RecommendedTaskViewHolder(@NonNull View itemView) {
        super(itemView);

        bindLayout();
    }

    private void bindLayout() {
        layout = itemView.findViewById(R.id.cv_layout);
        contentTextView = layout.findViewById(R.id.tv_content);
    }

    public void build(final SportRecommendWrapper task) {
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
                builder.append(" + ");
            }
        }
        contentTextView.setText(builder.toString());
    }

    public void setOnItemClickListener(OnItemClickListener<SportRecommendWrapper> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
