package com.joi.school.fitness.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.R;
import com.joi.school.fitness.core.meal.MealRecommendActivity;
import com.joi.school.fitness.core.sport.SportRecommendActivity;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.Navigation;

public class CoreFragment extends Fragment {

    private View mFunctionLayout;
    private View mSportRecommendView;
    private View mMealRecommendView;
    private View mAssessmentView;
    private ImageView mSportRecommendCoverView;
    private ImageView mMealRecommendCoverView;
    private ImageView mAssessmentCoverView;

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

        mSportRecommendCoverView = layout.findViewById(R.id.iv_sport_cover);
        mMealRecommendCoverView = layout.findViewById(R.id.iv_meal_cover);
        mAssessmentCoverView = layout.findViewById(R.id.iv_assessment_cover);

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
                startActivity(new Intent(getContext(), MealRecommendActivity.class));
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
        setCovers();
    }

    private void setCovers() {
        setGlideDrawableId(mSportRecommendCoverView, R.drawable.sport_theme);
        setGlideDrawableId(mMealRecommendCoverView, R.drawable.meal_theme);
        setGlideDrawableId(mAssessmentCoverView, R.drawable.assessment_theme);
    }

    private void setGlideDrawableId(ImageView view, int drawableId) {
        Glide.with(this).load(drawableId)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(AndroidUtils.toDp(3))))
                .placeholder(getResources().getDrawable(R.drawable.ui_placeholder))
                .into(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPhysicalStatistic();
    }

    private void checkPhysicalStatistic() {
        boolean hasStatistic = UserEngine.getInstance().hasPhysicalStatistic();
        if (hasStatistic) {
            mFunctionLayout.setVisibility(View.VISIBLE);
            mNoPhysicalStatisticLayout.setVisibility(View.GONE);
        } else {
            mFunctionLayout.setVisibility(View.GONE);
            mNoPhysicalStatisticLayout.setVisibility(View.VISIBLE);
        }
    }
}
