package com.joi.school.fitness.forum.postdetail.comment.newcomment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joi.school.fitness.R;
import com.joi.school.fitness.constant.IntentConstants;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.Comment;
import com.joi.school.fitness.tools.bean.FitnessUser;
import com.joi.school.fitness.tools.bean.Post;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import es.dmoral.toasty.Toasty;

/**
 * @author Joi
 * createAt 2019/3/22 0022 11:25
 */
public class NewCommentActivity extends BaseActivity {
    private Post mPost;

    private EditText mContentEditText;
    private FloatingActionButton mComleteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        if (!getDataFromIntent()) {
            finish();
            return;
        }

        initViews();
    }

    private boolean getDataFromIntent() {
        Intent intent = getIntent();
        mPost = (Post) intent.getSerializableExtra(IntentConstants.INTENT_KEY_POST);
        if (mPost == null) {
            return false;
        }
        return true;
    }

    private void initViews() {
        mContentEditText = findViewById(R.id.tv_content);
        mComleteButton = findViewById(R.id.fab_complete);

        mComleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Comment comment = getComment();
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String object, BmobException e) {
                        if (e == null) {
                            Toasty.success(getApplicationContext(), getString(R.string.toast_new_comment_success), Toast.LENGTH_SHORT, true).show();
                            finish();
                        } else {
                        }
                    }
                });
            }
        });
    }

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setPost(mPost);
        comment.setAuthor(BmobUser.getCurrentUser(FitnessUser.class));
        comment.setContent(mContentEditText.getText().toString());
        return comment;
    }
}
