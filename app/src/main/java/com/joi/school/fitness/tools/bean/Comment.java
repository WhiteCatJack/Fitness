package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Post JavaBean.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:37
 */
public class Comment extends SyncBmobObject {
    private Post post;
    private FitnessUser author;
    private String content;

    public Post getPost() {
        return post;
    }

    public Comment setPost(Post post) {
        this.post = post;
        return this;
    }

    public FitnessUser getAuthor() {
        return author;
    }

    public Comment setAuthor(FitnessUser author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }
}
