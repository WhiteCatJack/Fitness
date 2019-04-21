package com.joi.school.fitness.forum;

import com.joi.school.fitness.tools.bean.PostTag;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.util.AndroidUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/18 0018 20:30
 */
public class ForumPresenter implements IForumContract.Presenter {

    private ForumFragment mView;

    private ExecutorService mExecutor = new ScheduledThreadPoolExecutor(1);

    public ForumPresenter(ForumFragment mView) {
        this.mView = mView;
    }

    @Override
    public void getPostTags() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SyncBmobQuery<PostTag> query = new SyncBmobQuery<>(PostTag.class);
                    query.order("-updatedAt");
                    final List<PostTag> tagList = query.syncFindObjects();
                    mView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.doneGetPostTags(tagList);
                        }
                    });
                } catch (BmobException e) {
                    AndroidUtils.showErrorMainThread(mView.getActivity(), e);
                }
            }
        });
    }
}
