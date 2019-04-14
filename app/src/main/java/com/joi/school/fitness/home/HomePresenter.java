package com.joi.school.fitness.home;

import com.joi.school.fitness.tools.bean.Advertisement;
import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.datasource.advertisement.AdvertisementDataSource;
import com.joi.school.fitness.tools.datasource.article.ArticleDataSource;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.exception.BmobException;
import me.wangyuwei.banner.BannerEntity;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/14 0014 17:47
 */
public class HomePresenter implements IHomeContract.Presenter {

    private HomeFragment mView;

    public HomePresenter(HomeFragment mView) {
        this.mView = mView;
    }

    private List<Advertisement> mAdvertisementList;

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

    @Override
    public void getAllAdvertisement() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Advertisement> advertisementList = AdvertisementDataSource.getImpl().getAll();
                    final List<BannerEntity> bannerEntityList = new ArrayList<>();
                    for (Advertisement advertisement : advertisementList) {
                        if (advertisement == null) {
                            continue;
                        }
                        BannerEntity entity = new BannerEntity();
                        entity.imageUrl = advertisement.getCoverImageUrl();
                        entity.title = advertisement.getTitle();
                        bannerEntityList.add(entity);
                    }
                    mAdvertisementList = advertisementList;
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showAdvertisement(bannerEntityList);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView.getActivity(), e);
                }
            }
        };
        mExecutor.execute(task);
    }

    @Override
    public void getAllArticles() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Article> postList = ArticleDataSource.getImpl().getAll();
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showArticles(postList);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView.getActivity(), e);
                }
            }
        };
        mExecutor.execute(task);
    }

    @Override
    public void clickBannerItem(int i) {
        mView.reactClickBannerItem(mAdvertisementList.get(i));
    }
}
