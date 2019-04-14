package com.joi.school.fitness.tools.datasource;

import android.support.annotation.IntRange;

import com.joi.school.fitness.tools.bean.Post;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * @author Joi
 * createAt 2019/3/22 0022 10:47
 */
public interface IPostDataSource {
    /**
     * 查询所有
     */
    List<Post> getAll() throws BmobException;

    List<Post> getAll(@IntRange(from = 0) int start, @IntRange(from = 0) int amount) throws BmobException;

    /**
     * 新建
     */
    String newPost(Post post) throws BmobException;
}
