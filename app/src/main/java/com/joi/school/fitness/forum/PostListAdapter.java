package com.joi.school.fitness.forum;

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
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/23 0023 15:53
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private List<Post> mData = new ArrayList<>();
    private OnItemClickListener<Post> mOnItemClickListener;

    public PostListAdapter(@NonNull List<Post> data) {
        this.mData = data;
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
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        ImageView avatarImageView;
        TextView nicknameTextView;
        TextView titleTextView;
        TextView contentTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView) {
            layout = itemView.findViewById(R.id.cv_layout);
            avatarImageView = itemView.findViewById(R.id.iv_avatar);
            nicknameTextView = itemView.findViewById(R.id.tv_nick);
            titleTextView = itemView.findViewById(R.id.tv_title);
            contentTextView = itemView.findViewById(R.id.tv_content);
        }

        void build(final Post post) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(post);
                    }
                }
            });
            Glide.with(layout.getContext())
                    .load(post.getAuthor().getAvatarUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .placeholder(R.drawable.ui_placeholder)
                    .into(avatarImageView);
            nicknameTextView.setText(post.getAuthor().getNick());
            titleTextView.setText(post.getTitle());
            contentTextView.setText(post.getContent());
        }
    }

    void setOnItemClickListener(OnItemClickListener<Post> listener) {
        mOnItemClickListener = listener;
    }
}
