package com.joi.school.fitness.tools.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/22 0022 17:19
 */
public class FitnessUser extends BmobUser {
    private String            avatarUrl;
    private String            nick;
    // 个性签名
    private String            signature;
    // 最新身体数据
    private PhysicalStatistic latestStatistic;
    // 0男1女
    private int               gender;
    private BmobDate          birthday;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public FitnessUser setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getNick() {
        return nick;
    }

    public FitnessUser setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public FitnessUser setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public PhysicalStatistic getLatestStatistic() {
        return latestStatistic;
    }

    public FitnessUser setLatestStatistic(PhysicalStatistic latestStatistic) {
        this.latestStatistic = latestStatistic;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public FitnessUser setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public BmobDate getBirthday() {
        return birthday;
    }

    public FitnessUser setBirthday(BmobDate birthday) {
        this.birthday = birthday;
        return this;
    }
}
