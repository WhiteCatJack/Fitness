package com.joi.school.fitness.core.sport.doing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.util.AndroidUtils;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/17 0017 23:13
 */
class DoingExerciseViewHolder extends RecyclerView.ViewHolder {

    private ImageView coverImageView;
    private TextView titleTextView;

    public DoingExerciseViewHolder(@NonNull View itemView) {
        super(itemView);

        bindLayout();
    }

    private void bindLayout() {
        coverImageView = itemView.findViewById(R.id.iv_cover);
        titleTextView = itemView.findViewById(R.id.tv_title);
    }

    public void build(final Sport sport) {
    }
}
