package com.joi.school.fitness.tools.bean;

import android.support.annotation.NonNull;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;
import com.joi.school.fitness.tools.constant.Constants;

/**
 * Post JavaBean.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:37
 */
public class Post extends SyncBmobObject {
    private FitnessUser author;
    private String title;
    private String content;
    private PostTag tag;

    public Post() {
        PostTag tag = new PostTag();
        tag.setObjectId(Constants.POST_TAG_DEFAULT);
        this.tag = tag;
    }

    @NonNull
    public FitnessUser getAuthor() {
        return author;
    }

    public Post setAuthor(FitnessUser author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public PostTag getTag() {
        return tag;
    }

    public Post setTag(PostTag tag) {
        if (tag == null) {
            return this;
        }
        this.tag = tag;
        return this;
    }
}
