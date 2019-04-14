package com.joi.school.fitness.forum.newpost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.FitnessUser;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
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

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

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
                final Post post = getPost();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            post.syncSave();
                            NewPostActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(getApplicationContext(), getString(R.string.toast_new_post_success), Toast.LENGTH_SHORT, true).show();
                                    finish();
                                }
                            });
                        } catch (BmobException e) {
                            AndroidUtils.showErrorMainThread(NewPostActivity.this, e);
                        }
                    }
                };
                mExecutor.execute(runnable);
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
