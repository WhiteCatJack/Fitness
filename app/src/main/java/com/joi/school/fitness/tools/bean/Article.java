package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * @author Joi
 * createAt 2019/3/19 0019 14:05
 */
public class Article extends SyncBmobObject {
    private String coverImageUrl;
    private String title;
    private String brief;
    private String contentHtml;

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public Article setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBrief() {
        return brief;
    }

    public Article setBrief(String brief) {
        this.brief = brief;
        return this;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public Article setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
        return this;
    }
}
