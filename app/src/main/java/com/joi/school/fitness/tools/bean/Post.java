package com.joi.school.fitness.tools.bean;

import android.support.annotation.NonNull;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

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
}
