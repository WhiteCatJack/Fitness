package com.joi.school.fitness.forum.postdetail.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joi.school.fitness.R;
import com.joi.school.fitness.constant.IntentConstants;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.Comment;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 21:51
 */
public class CommentActivity extends BaseActivity {
    private Post mPost;

    private SwipeRefreshLayout swipeRefreshLayout;
    protected FloatingActionButton mNewCommentButton;
    private List<Comment> mCommentList;
    private RecyclerView mCommentRecyclerView;
    private CommentListAdapter mCommentListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        if (!getDataFromIntent()) {
            finish();
            return;
        }
        initView();
        getCommentList();
    }

    private void getCommentList() {
        BmobQuery<Comment> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(mPost.getObjectId());
        query.order("-createdAt");
        query.addWhereEqualTo("post", new BmobPointer(post));
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> objects, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                if (e == null) {
                    mCommentList.clear();
                    mCommentList.addAll(objects);
                    mCommentListAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.srl);
        mCommentRecyclerView = findViewById(R.id.rv_comment_list);
        mNewCommentButton = findViewById(R.id.fab_new_comment);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getCommentList();
            }
        });
        mNewCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToNewCommentActivity(CommentActivity.this, mPost);
            }
        });

        mCommentList = new ArrayList<>();
        mCommentListAdapter = new CommentListAdapter(mCommentList);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentRecyclerView.setAdapter(mCommentListAdapter);
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

