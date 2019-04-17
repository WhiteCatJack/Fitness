package com.joi.school.fitness.forum.postdetail.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/23 0023 15:53
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Comment> mData = new ArrayList<>();

    public CommentListAdapter(@NonNull List<Comment> data) {
        if (data != null) {
            this.mData = data;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mData.get(position);
        holder.build(comment);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView nicknameTextView;
        TextView contentTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView) {
            avatarImageView = itemView.findViewById(R.id.iv_avatar);
            nicknameTextView = itemView.findViewById(R.id.tv_nick);
            contentTextView = itemView.findViewById(R.id.tv_content);
        }

        void build(final Comment comment) {
            if (comment.getAuthor() != null) {
                Glide.with(itemView.getContext())
                        .load(comment.getAuthor().getAvatarUrl())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .placeholder(R.drawable.ui_placeholder)
                        .into(avatarImageView);
                nicknameTextView.setText(comment.getAuthor().getNick());
            }
            contentTextView.setText(comment.getContent());
        }
    }
}
