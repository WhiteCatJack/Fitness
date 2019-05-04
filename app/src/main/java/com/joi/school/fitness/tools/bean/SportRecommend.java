package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 10:35
 */
public class SportRecommend extends SyncBmobObject {
    private FitnessUser targetUser;
    private String data;

    public FitnessUser getTargetUser() {
        return targetUser;
    }

    public SportRecommend setTargetUser(FitnessUser targetUser) {
        this.targetUser = targetUser;
        return this;
    }

    public String getData() {
        return data;
    }

    public SportRecommend setData(String data) {
        this.data = data;
        return this;
    }
}
