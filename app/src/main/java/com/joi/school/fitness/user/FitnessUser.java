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
}
