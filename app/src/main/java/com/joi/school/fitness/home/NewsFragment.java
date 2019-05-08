package com.joi.school.fitness.home;

import android.content.Intent;
import android.net.Uri;
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
import com.joi.school.fitness.tools.util.Navigation;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class NewsFragment extends Fragment implements IHomeContract.View {

    private HomePresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Banner mBannerView;
    private RecyclerView mRecyclerView;

    private List<Article> mArticleList = new ArrayList<>();
    private ArticleListAdapter mArticleListAdapter;

    private CountDownLatch mCountDown;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRefreshLayout = layout.findViewById(R.id.srl);
        mBannerView = layout.findViewById(R.id.b_banner);
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
                refresh();
            }
        });
        refresh();
    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getAllAdvertisement();
        mPresenter.getAllArticles();
    }

    @Override
    public void showAdvertisement(final List<Advertisement> advertisementList) {
        mSwipeRefreshLayout.setRefreshing(false);

        List<String> imageUrlList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (Advertisement advertisement : advertisementList) {
            if (advertisement == null) {
                continue;
            }
            imageUrlList.add(advertisement.getCoverImageUrl());
            titleList.add(advertisement.getTitle());
        }

        Banner banner = mBannerView;
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageUrlList);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titleList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(Constants.BANNER_AUTO_SCROLL_TIME_IN_MILLIS);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
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
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String data = advertisement.getLinkUrl();
        intent.setData(Uri.parse(data));
        startActivity(intent);
    }
}
