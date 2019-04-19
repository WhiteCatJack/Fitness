package com.joi.school.fitness.core;

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
import com.joi.school.fitness.core.assessment.AssessmentFragment;
import com.joi.school.fitness.core.meal.MealRecommendFragment;
import com.joi.school.fitness.core.sport.SportRecommendFragment;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;

public class CoreFragment extends Fragment {

    private View mSlidesLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            AndroidUtils.getApplicationContext().getResources().getString(R.string.title_core_sport_recommend),
            AndroidUtils.getApplicationContext().getResources().getString(R.string.title_core_meal_recommend),
            AndroidUtils.getApplicationContext().getResources().getString(R.string.title_core_assessment)
    };
    private SlidesPagerAdapter mAdapter;
    private SlidingTabLayout mSlidesTabLayout;
    private ViewPager mSlidesViewPager;

    private View mNoPhysicalStatisticLayout;
    private View mSetPhysicalStatisticLink;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_core, container, false);

        mSlidesLayout = layout.findViewById(R.id.ll_slides);
        mSlidesTabLayout = layout.findViewById(R.id.stl_tab);
        mSlidesViewPager = layout.findViewById(R.id.vp);

        mNoPhysicalStatisticLayout = layout.findViewById(R.id.fl_no_physical_statistic);
        mSetPhysicalStatisticLink = layout.findViewById(R.id.link_physical_statistic);

        mSetPhysicalStatisticLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToSetPhysicalStatisticActivity(getContext());
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkPhysicalStatistic();

        mFragments.add(new SportRecommendFragment());
        mFragments.add(new MealRecommendFragment());
        mFragments.add(new AssessmentFragment());
        mSlidesViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mAdapter = new SlidesPagerAdapter(getChildFragmentManager(), mTitles, mFragments);
        mSlidesViewPager.setAdapter(mAdapter);
        mSlidesTabLayout.setViewPager(mSlidesViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPhysicalStatistic();
    }

    private void checkPhysicalStatistic() {
        boolean hasStatistic = UserEngine.getInstance().hasPhysicalStatistic();
        if (hasStatistic) {
            mSlidesLayout.setVisibility(View.VISIBLE);
            mNoPhysicalStatisticLayout.setVisibility(View.GONE);
        } else {
            mSlidesLayout.setVisibility(View.GONE);
            mNoPhysicalStatisticLayout.setVisibility(View.VISIBLE);
        }
    }
}
