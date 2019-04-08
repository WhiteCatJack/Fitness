package com.joi.school.fitness.forum.postdetail.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.constant.IntentConstants;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.Post;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 21:51
 */
public class CommentActivity extends BaseActivity {
    private Post mPost;

    private RecyclerView mCommentRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        if (!getDataFromIntent()) {
            finish();
            return;
        }
        initView();
    }

    private void initView() {
        mCommentRecyclerView = findViewById(R.id.rv_comment_list);
    }

    private boolean getDataFromIntent() {
        Intent intent = getIntent();
        mPost = (Post) intent.getSerializableExtra(IntentConstants.INTENT_KEY_POST);
        if (mPost == null) {
            return false;
        }
        return true;
    }
}

