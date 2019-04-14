package com.joi.school.fitness.home;

import com.joi.school.fitness.tools.bean.Advertisement;
import com.joi.school.fitness.tools.bean.Article;

import java.util.List;

import me.wangyuwei.banner.BannerEntity;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/14 0014 17:47
 */
public interface IHomeContract {
    interface View {
        void showAdvertisement(List<BannerEntity> bannerEntityList);

        void showArticles(List<Article> articleList);

        void reactClickBannerItem(Advertisement advertisement);
    }

    interface Presenter {
        void getAllAdvertisement();

        void getAllArticles();

        void clickBannerItem(int i);
    }
}
