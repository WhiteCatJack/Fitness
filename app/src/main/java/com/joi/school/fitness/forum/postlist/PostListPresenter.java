package com.joi.school.fitness.forum.postlist;

import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.bean.PostTag;
import com.joi.school.fitness.tools.datasource.post.PostDataSource;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.exception.BmobException;

/**
 * @author Joi
 * createAt 2019/3/23 0023 15:44
 */
public class PostListPresenter implements IPostListContract.Presenter {

    private PostListFragment mView;

    public PostListPresenter(PostListFragment mView) {
        this.mView = mView;
    }

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void getAll(final PostTag postTag) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Post> postList = PostDataSource.getImpl().getAll(postTag);
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
