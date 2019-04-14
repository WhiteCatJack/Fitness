package com.joi.school.fitness.home.articlelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.util.FrescoUtils;

/**
 * @author Joi
 * createAt 2019/3/19 0019 14:05
 */
class ListViewHolder extends RecyclerView.ViewHolder{
    private View layout;
    private SimpleDraweeView coverImageView;
    private TextView titleTextView;
    private TextView briefTextView;

    private OnItemClickListener<Article> mOnItemClickListener;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    private void bindViews(View itemView) {
        layout = itemView.findViewById(R.id.cv_layout);
        coverImageView = itemView.findViewById(R.id.iv_cover_img);
        titleTextView = itemView.findViewById(R.id.tv_title);
        briefTextView = itemView.findViewById(R.id.tv_brief);
    }

    void setOnItemClickListener(OnItemClickListener<Article> onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    void build(final Article article) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(article);
                }
            }
        });
        FrescoUtils.setImageUrl(coverImageView, article.getCoverImageUrl());
        titleTextView.setText(article.getTitle());
        briefTextView.setText(article.getBrief());
    }
}
