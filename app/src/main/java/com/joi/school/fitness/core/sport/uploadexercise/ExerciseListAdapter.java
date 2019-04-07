package com.joi.school.fitness.core.sport.uploadexercise;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Sport;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/23 0023 15:53
 */
public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<Sport> mData = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public ExerciseListAdapter(@NonNull List<Sport> data) {
        if (data != null) {
            this.mData = data;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sport sport = mData.get(position);
        holder.build(sport);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView exerciseNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView) {
            layout = itemView.findViewById(R.id.cv_layout);
            exerciseNameTextView = itemView.findViewById(R.id.tv_exercise_name);
        }

        void build(final Sport sport) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(sport);
                    }
                }
            });
            exerciseNameTextView.setText(sport.getSportKind());
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Sport sport);
    }
}
