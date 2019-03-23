package com.joi.school.fitness.forum;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.R;
import com.joi.school.fitness.forum.datasource.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/3/23 0023 15:53
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private List<Post> mData = new ArrayList<>();

    public PostListAdapter(@NonNull List<Post> data) {
        if (data != null) {
            this.mData = data;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mData.get(position);
        holder.build(post);
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
        SimpleDraweeView avatarImageView;
        TextView titleTextView;
        TextView contentTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView) {
            avatarImageView = itemView.findViewById(R.id.iv_avatar);
            titleTextView = itemView.findViewById(R.id.tv_title);
            contentTextView = itemView.findViewById(R.id.tv_content);
        }

        public void build(Post post) {
            Uri uri = Uri.parse(post.getAuthor().getAvatarUrl());
            avatarImageView.setImageURI(uri);
            titleTextView.setText(post.getTitle());
            contentTextView.setText(post.getContent());
        }
    }
}
