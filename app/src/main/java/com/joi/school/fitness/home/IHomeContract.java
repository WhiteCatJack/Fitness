package com.joi.school.fitness.home;

import com.joi.school.fitness.tools.bean.Article;

import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/14 0014 17:47
 */
public interface IHomeContract {
    interface View {
        void showArticles(List<Article> articleList);
    }

    interface Presenter {
        void getAllArticles();
    }
}
