package com.joi.school.fitness.tools.datasource.article;

/**
 * @author Joi
 * createAt 2019/3/22 0022 10:47
 */
public abstract class ArticleDataSource implements IArticleDataSource {
    public static ArticleDataSource getImpl() {
        return ArticleDataSourceImpl.getInstance();
    }
}
