package com.joi.school.fitness.user;

import cn.bmob.v3.BmobUser;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/3/22 0022 17:19
 */
public class FitnessUser extends BmobUser {
    private String avatarUrl;
    private String nick;
    // 个性签名
    private String personalizedSignature;
    // 最新身体数据
    private PhysicalStatistic latestPhysicalStatistic;

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

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public PhysicalStatistic getLatestPhysicalStatistic() {
        return latestPhysicalStatistic;
    }

    public void setLatestPhysicalStatistic(PhysicalStatistic latestPhysicalStatistic) {
        this.latestPhysicalStatistic = latestPhysicalStatistic;
    }
}
