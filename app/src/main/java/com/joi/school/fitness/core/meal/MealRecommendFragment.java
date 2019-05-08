package com.joi.school.fitness.core.meal;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.BaseFragment;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.bean.MealRecommend;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.transform.MealRecommendWrapper;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class MealRecommendFragment extends BaseFragment {

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    private LinearLayout mBreakfastLayout;
    private LinearLayout mLunchLayout;
    private LinearLayout mDinnerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_meal_recommend, container, false);

        mBreakfastLayout = layout.findViewById(R.id.ll_breakfast_layout);
        mLunchLayout = layout.findViewById(R.id.ll_lunch_layout);
        mDinnerLayout = layout.findViewById(R.id.ll_dinner_layout);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMealList();
    }

    private void getMealList() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date date = calendar.getTime();
                BmobDate today00 = new BmobDate(date);

                try {
                    SyncBmobQuery<MealRecommend> mealRecommendQuery =
                            new SyncBmobQuery<>(MealRecommend.class);
                    mealRecommendQuery.addWhereEqualTo(
                            "targetUser", UserEngine.getInstance().getCurrentUser().getObjectId());
                    mealRecommendQuery.addWhereGreaterThanOrEqualTo("time", today00);

                    SyncBmobQuery<Meal> mealQuery = new SyncBmobQuery<>(Meal.class);
                    List<Meal> mealList = mealQuery.syncFindObjects();

                    final List<MealRecommend> mealRecommendList = mealRecommendQuery.syncFindObjects();
                    if (mealRecommendList.size() > 0) {
                        MealRecommend mealRecommend = mealRecommendList.get(0);
                        final MealRecommendWrapper wrapper = new MealRecommendWrapper(mealRecommend);
                        List<Meal> mealObjectList = new ArrayList<>();
                        List<String> idList = wrapper.getBreakfastMealIdList();
                        for (String id : idList) {
                            for (Meal meal : mealList) {
                                if (meal.getObjectId().equals(id)) {
                                    mealObjectList.add(meal);
                                }
                            }
                        }
                        wrapper.setBreakfastMealList(mealObjectList);
                        mealObjectList = new ArrayList<>();
                        idList = wrapper.getLunchMealIdList();
                        for (String id : idList) {
                            for (Meal meal : mealList) {
                                if (meal.getObjectId().equals(id)) {
                                    mealObjectList.add(meal);
                                }
                            }
                        }
                        wrapper.setLunchMealList(mealObjectList);
                        mealObjectList = new ArrayList<>();
                        idList = wrapper.getDinnerMealIdList();
                        for (String id : idList) {
                            for (Meal meal : mealList) {
                                if (meal.getObjectId().equals(id)) {
                                    mealObjectList.add(meal);
                                }
                            }
                        }
                        wrapper.setDinnerMealList(mealObjectList);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMealList(wrapper);
                            }
                        });
                    } else {
                        throw new BmobException("No recommend.");
                    }
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(getActivity(), e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @MainThread
    private void showMealList(MealRecommendWrapper mealRecommendWrapper) {
        for (Meal meal : mealRecommendWrapper.getBreakfastMealList()) {
            mBreakfastLayout.addView(createItemView(meal));
        }
        for (Meal meal : mealRecommendWrapper.getLunchMealList()) {
            mLunchLayout.addView(createItemView(meal));
        }
        for (Meal meal : mealRecommendWrapper.getDinnerMealList()) {
            mDinnerLayout.addView(createItemView(meal));
        }
    }

    private View createItemView(Meal meal) {
        View view = getLayoutInflater().inflate(R.layout.item_meal_recommend, null, false);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.topMargin = AndroidUtils.toPx(4);
        layoutParams.leftMargin = AndroidUtils.toPx(4);
        layoutParams.rightMargin = AndroidUtils.toPx(4);
        layoutParams.bottomMargin = AndroidUtils.toPx(4);
        view.setLayoutParams(layoutParams);
        TextView tv = view.findViewById(R.id.tv_content);
        tv.setText(meal.getMealName());
        return view;
    }
}
