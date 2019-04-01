package com.joi.school.fitness.forum;

import com.joi.school.fitness.forum.datasource.Post;

import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/3/23 0023 15:44
 */
public interface IForumContract {
    interface View {
        void showPostList(List<Post> data);
    }

    interface Presenter {
        void getAll();
    }
}