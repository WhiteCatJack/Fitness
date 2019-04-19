package com.joi.school.fitness.forum.postlist.postdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.constant.IntentConstants;
import com.joi.school.fitness.tools.util.Navigation;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 14:30
 */
public class PostActivity extends BaseActivity {

    private Post mPost;

    private ImageView mAvatarImageView;
    private TextView mNicknameTextView;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private View mCommentButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if (!getDataFromIntent()) {
            finish();
            return;
        }
        initView();
        setPostData();
    }

    private void setPostData() {
        Glide.with(this)
                .load(mPost.getAuthor().getAvatarUrl())
                .placeholder(R.drawable.ui_placeholder)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mAvatarImageView);
        mNicknameTextView.setText(mPost.getAuthor().getNick());
        mTitleTextView.setText(mPost.getTitle());
        mContentTextView.setText(mPost.getContent());
    }

    private void initView() {
        mAvatarImageView = findViewById(R.id.iv_avatar);
        mNicknameTextView = findViewById(R.id.tv_nick);
        mTitleTextView = findViewById(R.id.tv_title);
        mContentTextView = findViewById(R.id.tv_content);
        mCommentButton = findViewById(R.id.bt_comment);

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToCommentActivity(PostActivity.this, mPost);
            }
        });
    }

    private boolean getDataFromIntent() {
        Intent intent = getIntent();
        mPost = (Post) intent.getSerializableExtra(IntentConstants.INTENT_KEY_POST);
        return mPost != null;
    }
}
