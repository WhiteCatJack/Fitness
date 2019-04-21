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
import com.joi.school.fitness.tools.bean.PostTag;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.widget.TagGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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

    private TagGroup mTagGroupView;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private FloatingActionButton mCompleteButton;

    private List<PostTag> mTagList;
    private List<String> mTagStringList;
    private PostTag mChosenPostTag;

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initViews();
        getTagList();
    }

    private void initViews() {
        mTagGroupView = findViewById(R.id.tag_group);
        mTitleEditText = findViewById(R.id.tv_title);
        mContentEditText = findViewById(R.id.tv_content);
        mCompleteButton = findViewById(R.id.fab_complete);

        mTagGroupView.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                selectTag(mTagStringList.indexOf(tag));
            }
        });
        mCompleteButton.setOnClickListener(new View.OnClickListener() {
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
        post.setTag(mChosenPostTag);
        return post;
    }

    private void selectTag(int index) {
        mChosenPostTag = mTagList.get(index);
    }

    private void showTagList(List<PostTag> tagList) {
        mTagList = tagList;
        mTagStringList = new ArrayList<>(tagList.size());
        String[] tags = new String[tagList.size()];
        for (int i = 0; i < tagList.size(); i++) {
            PostTag tag = tagList.get(i);
            mTagStringList.add(tag.getTagName());
            tags[i] = tag.getTagName();
        }
        mTagGroupView.setTags(tags);
    }

    private void getTagList() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SyncBmobQuery<PostTag> query = new SyncBmobQuery<>(PostTag.class);
                query.order("-updatedAt");
                try {
                    final List<PostTag> tagList = query.syncFindObjects();
                    NewPostActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showTagList(tagList);
                        }
                    });
                } catch (BmobException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
