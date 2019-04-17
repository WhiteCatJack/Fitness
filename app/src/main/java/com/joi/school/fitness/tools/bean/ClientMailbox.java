package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/8 0008 14:44
 */
public class ClientMailbox extends SyncBmobObject {
    private FitnessUser user;
    private boolean valid = true;
    private int type;

    public FitnessUser getUser() {
        return user;
    }

    public ClientMailbox setUser(FitnessUser user) {
        this.user = user;
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public ClientMailbox setValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public int getType() {
        return type;
    }

    public ClientMailbox setType(int type) {
        this.type = type;
        return this;
    }
}
