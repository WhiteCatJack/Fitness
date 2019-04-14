package com.joi.school.fitness.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.forum.newpost.NewPostActivity;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment implements IForumContract.View {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mPostListView;
    protected FloatingActionButton mNewPostButton;

    private IForumContract.Presenter mPresenter;

    private PostListAdapter mPostListAdapter;
    private List<Post> mPostDataList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_forum, container, false);
        mSwipeRefreshLayout = layout.findViewById(R.id.srl);
        mPostListView = layout.findViewById(R.id.rv_list);
        mNewPostButton = layout.findViewById(R.id.fab_new_post);

        mNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewPostActivity.class));
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = new ForumPresenter(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getAll();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getAll();

        mPostListAdapter = new PostListAdapter(mPostDataList);
        mPostListAdapter.setOnItemClickListener(new OnItemClickListener<Post>() {
            @Override
            public void onItemClick(Post post) {
                Navigation.goToPostActivity(getContext(), post);
            }
        });
        mPostListView.setAdapter(mPostListAdapter);
    }

    @Override
    public void showPostList(List<Post> data) {
        mSwipeRefreshLayout.setRefreshing(false);
        mPostDataList.clear();
        mPostDataList.addAll(data);
        mPostListAdapter.notifyDataSetChanged();
    }
}
