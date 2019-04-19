package com.joi.school.fitness.forum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.joi.school.fitness.R;
import com.joi.school.fitness.forum.newpost.NewPostActivity;
import com.joi.school.fitness.forum.postlist.PostListFragment;
import com.joi.school.fitness.tools.bean.PostTag;
import com.joi.school.fitness.tools.constant.IntentConstants;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment implements IForumContract.View {

    private IForumContract.Presenter mPresenter;

    private View mSlidesLayout;
    private List<PostTag> mTagList;
    private SlidingTabLayout mSlidesTabLayout;
    private ViewPager mSlidesViewPager;

    private View mNewPostButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_forum, container, false);

        mSlidesLayout = layout.findViewById(R.id.ll_slides);
        mSlidesTabLayout = layout.findViewById(R.id.stl_tab);
        mSlidesViewPager = layout.findViewById(R.id.vp);

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
        mPresenter.getPostTags();
    }

    private void initPostListFragmentViewPager(List<PostTag> tagList) {
        String[] titles = new String[tagList.size()];
        List<Fragment> fragments = new ArrayList<>(tagList.size());

        for (int i = 0; i < tagList.size(); i++) {
            PostTag tag = tagList.get(i);
            titles[i] = tag.getTagName();

            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentConstants.INTENT_KEY_POST_TAG, tag);
            Fragment fragment = new PostListFragment();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        SlidesPagerAdapter mAdapter = new SlidesPagerAdapter(getChildFragmentManager(), titles, fragments);
        mSlidesViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mSlidesViewPager.setAdapter(mAdapter);
        mSlidesTabLayout.setViewPager(mSlidesViewPager);
    }

    @Override
    public void doneGetPostTags(List<PostTag> tagList) {
        this.mTagList = tagList;

        initPostListFragmentViewPager(tagList);
    }
}
