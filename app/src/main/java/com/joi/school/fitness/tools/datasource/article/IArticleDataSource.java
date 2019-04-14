package com.joi.school.fitness.tools.datasource.article;

import android.support.annotation.IntRange;

import com.joi.school.fitness.tools.bean.Article;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * @author Joi
 * createAt 2019/3/22 0022 10:47
 */
public interface IArticleDataSource {
    /**
     * 查询所有
     */
    List<Article> getAll() throws BmobException;

    List<Article> getAll(@IntRange(from = 0) int start, @IntRange(from = 0) int amount) throws BmobException;
}
