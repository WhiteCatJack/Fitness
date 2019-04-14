package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Post JavaBean.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:37
 */
public class Article extends SyncBmobObject {
    private String title;
    private String content;

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
}
