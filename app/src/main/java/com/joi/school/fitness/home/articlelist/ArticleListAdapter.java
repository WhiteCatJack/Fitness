package com.joi.school.fitness.home.articlelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Article;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/14 0014 17:44
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private List<Article> mDataList;
    private OnItemClickListener<Article> mOnItemClickListener;

    public ArticleListAdapter(List<Article> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListViewHolder viewHolder = new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
        viewHolder.setOnItemClickListener(mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        Article article = mDataList.get(i);
        listViewHolder.build(article);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener<Article> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
