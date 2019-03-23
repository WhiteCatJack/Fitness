package com.joi.school.fitness.forum;

import com.joi.school.fitness.forum.datasource.Post;
import com.joi.school.fitness.forum.datasource.PostDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author 泽乾
 * createAt 2019/3/23 0023 15:44
 */
public class ForumPresenter implements IForumContract.Presenter {

    private IForumContract.View mView;

    public ForumPresenter(IForumContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getAll() {
        PostDataSource.getImpl().getAll(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null) {
                    mView.showPostList(list);
                } else {

                }
            }
        });
    }
}
