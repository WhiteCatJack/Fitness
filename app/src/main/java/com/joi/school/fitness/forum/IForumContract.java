package com.joi.school.fitness.forum;

import com.joi.school.fitness.tools.bean.PostTag;

import java.util.List;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/18 0018 20:30
 */
interface IForumContract {
    interface View {
        void doneGetPostTags(List<PostTag> tagList);
    }

    interface Presenter {
        void getPostTags();
    }
}
