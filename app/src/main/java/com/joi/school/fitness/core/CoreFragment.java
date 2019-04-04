package com.joi.school.fitness.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.core.sport.SportRecommendActivity;
import com.joi.school.fitness.user.UserEngine;
import com.joi.school.fitness.util.Navigation;

public class CoreFragment extends Fragment {

    private View mFunctionLayout;
    private View mSportRecommendView;
    private View mMealRecommendView;
    private View mAssessmentView;

    private View mNoPhysicalStatisticLayout;
    private View mSetPhysicalStatisticLink;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_core, container, false);

        mFunctionLayout = layout.findViewById(R.id.sv_function);
        mSportRecommendView = layout.findViewById(R.id.cv_sport_recommend);
        mMealRecommendView = layout.findViewById(R.id.cv_meal_recommend);
        mAssessmentView = layout.findViewById(R.id.cv_assessment);
        mNoPhysicalStatisticLayout = layout.findViewById(R.id.fl_no_physical_statistic);
        mSetPhysicalStatisticLink = layout.findViewById(R.id.link_physical_statistic);

        mSportRecommendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SportRecommendActivity.class));
            }
        });
        mMealRecommendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAssessmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    }

    private void checkPhysicalStatistic() {
        boolean hasStatistic = UserEngine.hasPysicalStatistic();
        if (hasStatistic) {
            mFunctionLayout.setVisibility(View.VISIBLE);
            mNoPhysicalStatisticLayout.setVisibility(View.GONE);
        } else {
            mFunctionLayout.setVisibility(View.GONE);
            mNoPhysicalStatisticLayout.setVisibility(View.VISIBLE);
        }
    }
}
