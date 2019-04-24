package com.joi.school.fitness.core.sport.doing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Sport;

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
        coverImageView = itemView.findViewById(R.id.iv_cover);
        titleTextView = itemView.findViewById(R.id.tv_title);
    }

    public void build(final Sport sport) {
        Glide.with(coverImageView.getContext()).load(sport.getSportImageUrl())
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .placeholder(R.drawable.ui_placeholder)
                .into(coverImageView);
        titleTextView.setText(sport.getSportName());
    }
}
