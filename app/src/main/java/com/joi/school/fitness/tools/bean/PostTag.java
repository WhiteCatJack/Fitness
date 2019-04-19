package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/18 0018 20:23
 */
public class PostTag extends SyncBmobObject {
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public PostTag setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }
}
