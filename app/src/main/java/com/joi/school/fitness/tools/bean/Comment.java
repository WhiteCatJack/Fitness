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

    public void setPost(Post post) {
        this.post = post;
    }

    public FitnessUser getAuthor() {
        return author;
    }

    public void setAuthor(FitnessUser author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
