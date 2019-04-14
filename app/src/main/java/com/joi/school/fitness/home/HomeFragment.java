package com.joi.school.fitness.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joi.school.fitness.R;
import com.joi.school.fitness.home.articlelist.ArticleListAdapter;
import com.joi.school.fitness.tools.base.OnItemClickListener;
import com.joi.school.fitness.tools.bean.Advertisement;
import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.constant.Constants;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

import me.wangyuwei.banner.BannerEntity;
import me.wangyuwei.banner.BannerView;
import me.wangyuwei.banner.OnBannerClickListener;

public class HomeFragment extends Fragment implements IHomeContract.View {

    private HomePresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BannerView mBannerView;
    private RecyclerView mRecyclerView;

    private List<Article> mArticleList = new ArrayList<>();
    private ArticleListAdapter mArticleListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRefreshLayout = layout.findViewById(R.id.srl);
        mBannerView = layout.findViewById(R.id.bv_banner);
        mRecyclerView = layout.findViewById(R.id.rv_list);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new HomePresenter(this);

        mArticleListAdapter = new ArticleListAdapter(mArticleList);
        mArticleListAdapter.setOnItemClickListener(new OnItemClickListener<Article>() {
            @Override
            public void onItemClick(Article article) {
                Navigation.goToArticleActivity(getContext(), article);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mArticleListAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.getAllAdvertisement();
                mPresenter.getAllArticles();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getAllAdvertisement();
        mPresenter.getAllArticles();
    }

    @Override
    public void showAdvertisement(final List<BannerEntity> bannerEntityList) {
        mBannerView.setEntities(bannerEntityList);
        mBannerView.setAutoScroll(Constants.BANNER_AUTO_SCROLL_TIME_IN_SECONDS);
        mBannerView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                mPresenter.clickBannerItem(position);
            }
        });
    }

    @Override
    public void showArticles(List<Article> articleList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mArticleList.clear();
        mArticleList.addAll(articleList);
        mArticleListAdapter.notifyDataSetChanged();
    }

    @Override
    public void reactClickBannerItem(Advertisement advertisement) {
        AndroidUtils.showToast(advertisement.getLinkUrl());
    }
}
