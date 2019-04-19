package com.joi.school.fitness.forum.postlist;

import android.support.annotation.NonNull;

import com.joi.school.fitness.tools.bean.Post;
import com.joi.school.fitness.tools.bean.PostTag;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/23 0023 15:44
 */
public interface IPostListContract {
    interface View {
        void showPostList(List<Post> data);
    }

    interface Presenter {
        void getAll(@NonNull PostTag postTag);
    }
}
