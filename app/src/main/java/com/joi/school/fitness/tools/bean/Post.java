package com.joi.school.fitness.tools.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Post JavaBean.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:37
 */
public class Post extends BmobObject {
    private FitnessUser author;
    private String title;
    private String content;
    private List<Comment> commentList;

    public FitnessUser getAuthor() {
        return author;
    }

    public void setAuthor(FitnessUser author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
