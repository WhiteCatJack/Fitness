package com.joi.school.fitness.tools.datasource.post;

import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.bean.PostTag;
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
    public List<Post> getAll(PostTag tag) throws BmobException {
        return getAll(tag, 0, Constants.AMOUNT_IN_ONE_PAGE);
    }

    @Override
    public List<Post> getAll(PostTag tag, int start, int amount) throws BmobException {
        final SyncBmobQuery<Post> query = new SyncBmobQuery<>(Post.class);
        query.setSkip(start == 0 ? 0 : start - 1)
                .setLimit(amount)
                .order("-createdAt")
                .addWhereEqualTo("tag", tag.getObjectId())
                .include("author");

        return query.syncFindObjects();
    }

    @Override
    public String newPost(Post post) throws BmobException {
        return post.syncSave();
    }
}
