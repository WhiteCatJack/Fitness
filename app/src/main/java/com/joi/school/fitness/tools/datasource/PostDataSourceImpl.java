package com.joi.school.fitness.tools.datasource;

import android.support.annotation.Nullable;

import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.constant.Constants;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 帖子数据.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:42
 */
class PostDataSourceImpl extends PostDataSource {

    private static volatile PostDataSourceImpl sInstance;

    private PostDataSourceImpl() {
    }

    static PostDataSourceImpl getInstance() {
        if (sInstance == null) {
            synchronized (PostDataSourceImpl.class) {
                if (sInstance == null) {
                    sInstance = new PostDataSourceImpl();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getAll(@Nullable FindListener<Post> listener) {
        getAll(0, Constants.AMOUNT_IN_ONE_PAGE, listener);
    }

    @Override
    public void getAll(int start, int amount, FindListener<Post> listener) {
        // 如果没有回调需求，则不执行获取操作
        if (listener == null) {
            return;
        }
        // 获取数据
        new BmobQuery<Post>()
                .setSkip(start == 0 ? 0 : start - 1)
                .setLimit(amount)
                .order("-createdAt")
                .include("author")
                .findObjects(listener);
    }

    @Override
    public void newPost(Post post, @Nullable SaveListener<String> listener) {
        post.save(listener);
    }
}
