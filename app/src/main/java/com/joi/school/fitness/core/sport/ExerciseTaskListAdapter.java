package com.joi.school.fitness.core.sport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.ExerciseTask;

import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/17 0017 21:21
 */
class ExerciseTaskListAdapter extends RecyclerView.Adapter<ExerciseTaskViewHolder> {

    private List<ExerciseTask> mDataList;
    private OnItemClickListener<ExerciseTask> mOnItemClickListener;

    public ExerciseTaskListAdapter(List<ExerciseTask> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ExerciseTaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExerciseTaskViewHolder viewHolder = new ExerciseTaskViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_exercise_task, viewGroup, false)
        );
        viewHolder.setOnItemClickListener(mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseTaskViewHolder exerciseTaskViewHolder, int i) {
        ExerciseTask exerciseTask = mDataList.get(i);
        exerciseTaskViewHolder.build(exerciseTask);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener<ExerciseTask> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
