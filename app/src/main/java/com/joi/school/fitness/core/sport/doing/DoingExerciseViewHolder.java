package com.joi.school.fitness.core.sport.doing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.util.GlideUtils;

/**
 * Description.
 *
 * @author Joi
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
        titleTextView = itemView.findViewById(R.id.tv_content);
        coverImageView = itemView.findViewById(R.id.iv_cover_img);
    }

    public void build(Sport sport, int timeInMinute) {
        String content = sport.getSportName() + " (" + String.valueOf(timeInMinute) + "min)";
        titleTextView.setText(content);
        GlideUtils.setImageUrl(coverImageView, sport.getSportImageUrl(), true);
    }
}
