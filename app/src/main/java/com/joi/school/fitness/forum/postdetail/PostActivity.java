package com.joi.school.fitness.forum.postdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.constant.Constants;
import com.joi.school.fitness.forum.datasource.Post;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/1 0001 14:30
 */
public class PostActivity extends BaseActivity {

    private Post mPost;

    private SimpleDraweeView avatarImageView;
    private TextView titleTextView;
    private TextView contentTextView;

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
        titleTextView.setText(mPost.getTitle());
        contentTextView.setText(mPost.getContent());
    }

    private void initView() {
        avatarImageView = findViewById(R.id.iv_avatar);
        titleTextView = findViewById(R.id.tv_title);
        contentTextView = findViewById(R.id.tv_content);
    }

    private boolean getDataFromIntent() {
        Intent intent = getIntent();
        mPost = (Post) intent.getSerializableExtra(Constants.INTENT_KEY_POST);
        if (mPost == null) {
            return false;
        }
        return true;
    }
}
