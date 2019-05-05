package com.joi.school.fitness.core.sport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.transform.SportRecommendWrapper;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/17 0017 21:21
 */
class RecommendedTaskListAdapter extends RecyclerView.Adapter<RecommendedTaskViewHolder> {

    private List<SportRecommendWrapper> mDataList;
    private OnItemClickListener<SportRecommendWrapper> mOnItemClickListener;

    public RecommendedTaskListAdapter(List<SportRecommendWrapper> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public RecommendedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecommendedTaskViewHolder viewHolder = new RecommendedTaskViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommended_task, viewGroup, false)
        );
        viewHolder.setOnItemClickListener(mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedTaskViewHolder recommendedTaskViewHolder, int i) {
        SportRecommendWrapper exerciseTask = mDataList.get(i);
        recommendedTaskViewHolder.build(exerciseTask);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener<SportRecommendWrapper> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
