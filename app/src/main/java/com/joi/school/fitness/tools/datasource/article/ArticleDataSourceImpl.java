package com.joi.school.fitness.tools.datasource.article;

import com.joi.school.fitness.tools.bean.Article;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.constant.Constants;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 帖子数据.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:42
 */
class ArticleDataSourceImpl extends ArticleDataSource {

    private static volatile ArticleDataSourceImpl sInstance;

    private ArticleDataSourceImpl() {
    }

    static ArticleDataSourceImpl getInstance() {
        if (sInstance == null) {
            synchronized (ArticleDataSourceImpl.class) {
                if (sInstance == null) {
                    sInstance = new ArticleDataSourceImpl();
                }
            }
        }
        return sInstance;
    }

    @Override
    public List<Article> getAll() throws BmobException {
        return getAll(0, Constants.AMOUNT_IN_ONE_PAGE);
    }

    @Override
    public List<Article> getAll(int start, int amount) throws BmobException {
        final SyncBmobQuery<Article> query = new SyncBmobQuery<>(Article.class);
        query.setSkip(start == 0 ? 0 : start - 1)
                .setLimit(amount)
                .order("-createdAt")
                .include("author");

        return query.syncFindObjects();
    }
}
