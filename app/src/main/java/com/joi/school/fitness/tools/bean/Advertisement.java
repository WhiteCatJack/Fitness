package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * @author Joi
 * createAt 2019/3/19 0019 14:05
 */
public class Advertisement extends SyncBmobObject {
    private String title;
    private String coverImageUrl;
    private String linkUrl;

    public String getTitle() {
        return title;
    }

    public Advertisement setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public Advertisement setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
        return this;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public Advertisement setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        return this;
    }
}
