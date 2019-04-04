package com.joi.school.fitness.user;

import cn.bmob.v3.BmobUser;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/22 0022 17:19
 */
public class FitnessUser extends BmobUser {
    private String avatarUrl;
    private String nick;
    // 个性签名
    private String signature;
    // 最新身体数据
    private PhysicalStatistic latestStatistic;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public PhysicalStatistic getLatestStatistic() {
        return latestStatistic;
    }

    public void setLatestStatistic(PhysicalStatistic latestStatistic) {
        this.latestStatistic = latestStatistic;
    }
}
