package com.joi.school.fitness.forum;

import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.datasource.PostDataSource;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.exception.BmobException;

/**
 * @author Joi
 * createAt 2019/3/23 0023 15:44
 */
public class ForumPresenter implements IForumContract.Presenter {

    private ForumFragment mView;

    public ForumPresenter(ForumFragment mView) {
        this.mView = mView;
    }

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

    @Override
    public void getAll() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Post> postList = PostDataSource.getImpl().getAll();
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showPostList(postList);
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
