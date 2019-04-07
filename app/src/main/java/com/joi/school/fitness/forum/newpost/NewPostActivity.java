package com.joi.school.fitness.forum.newpost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.datasource.PostDataSource;
import com.joi.school.fitness.tools.bean.FitnessUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import es.dmoral.toasty.Toasty;

/**
 * 发帖UI.
 *
 * @author Joi
 * createAt 2019/3/22 0022 11:25
 */
public class NewPostActivity extends BaseActivity {

    private EditText mTitleEditText;
    private EditText mContentEditText;
    private FloatingActionButton mComleteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initViews();
    }

    private void initViews() {
        mTitleEditText = findViewById(R.id.tv_title);
        mContentEditText = findViewById(R.id.tv_content);
        mComleteButton = findViewById(R.id.fab_complete);

        mComleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Post post = getPost();
                PostDataSource.getImpl().newPost(post, new SaveListener<String>() {
                    @Override
                    public void done(String postId, BmobException e) {
                       dismissLoadingDialog();
                        if (e == null) {
                            Toasty.success(getApplicationContext(), getString(R.string.toast_new_post_success), Toast.LENGTH_SHORT, true).show();
                            finish();
                        } else {
                            Toasty.error(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
            }
        });
    }

    private Post getPost() {
        Post post = new Post();
        post.setAuthor(BmobUser.getCurrentUser(FitnessUser.class));
        post.setTitle(mTitleEditText.getText().toString());
        post.setContent(mContentEditText.getText().toString());
        return post;
    }
}
