package com.joi.school.fitness.home;

import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.datasource.article.ArticleDataSource;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/14 0014 17:47
 */
public class HomePresenter implements IHomeContract.Presenter{

    private HomeFragment mView;

    public HomePresenter(HomeFragment mView) {
        this.mView = mView;
    }

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

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
}
