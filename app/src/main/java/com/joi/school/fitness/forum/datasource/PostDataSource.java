package com.joi.school.fitness.forum.datasource;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author 泽乾
 * createAt 2019/3/22 0022 10:47
 */
public abstract class PostDataSource {
    public static PostDataSource getImpl() {
        return PostDataSourceImpl.getInstance();
    }

    /**
     * 查询所有
     */
    public void getAll(@Nullable FindListener<Post> listener) {
    }

    public void getAll(@IntRange(from = 0) int start, @IntRange(from = 0) int amount, @Nullable FindListener<Post> listener) {
    }

    /**
     * 新建
     */
    public void newPost(Post post, @Nullable SaveListener<String> listener) {
    }
}
