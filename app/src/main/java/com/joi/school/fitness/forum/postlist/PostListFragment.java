package com.joi.school.fitness.forum.postlist;

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
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.bean.PostTag;
import com.joi.school.fitness.tools.constant.IntentConstants;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

public class PostListFragment extends Fragment implements IPostListContract.View {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mPostListView;
    protected FloatingActionButton mNewPostButton;

    private IPostListContract.Presenter mPresenter;

    private PostListAdapter mPostListAdapter;
    private List<Post> mPostDataList = new ArrayList<>();

    private PostTag mPostTag;

    public PostListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_forum_post, container, false);
        mSwipeRefreshLayout = layout.findViewById(R.id.srl);
        mPostListView = layout.findViewById(R.id.rv_list);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!getDataFromArguments()) {
            throw new IllegalStateException("No post tag!");
        }

        mPresenter = new PostListPresenter(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getAll(mPostTag);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getAll(mPostTag);
        mPostListAdapter = new PostListAdapter(mPostDataList);
        mPostListAdapter.setOnItemClickListener(new OnItemClickListener<Post>() {
            @Override
            public void onItemClick(Post post) {
                Navigation.goToPostActivity(getContext(), post);
            }
        });
        mPostListView.setAdapter(mPostListAdapter);
    }

    private boolean getDataFromArguments() {
        if (getArguments() == null) {
            return false;
        }
        mPostTag = (PostTag) getArguments().getSerializable(IntentConstants.INTENT_KEY_POST_TAG);
        return mPostTag != null;
    }

    @Override
    public void showPostList(List<Post> data) {
        mSwipeRefreshLayout.setRefreshing(false);
        mPostDataList.clear();
        mPostDataList.addAll(data);
        mPostListAdapter.notifyDataSetChanged();
    }
}
