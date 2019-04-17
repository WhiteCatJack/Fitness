package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/8 0008 14:44
 */
public class ServerMailbox extends SyncBmobObject {
    private FitnessUser user;
    private ClientMailbox clientMail;
    private boolean valid = true;
    private String obj;

    public FitnessUser getUser() {
        return user;
    }

    public ServerMailbox setUser(FitnessUser user) {
        this.user = user;
        return this;
    }

    public ClientMailbox getClientMail() {
        return clientMail;
    }

    public ServerMailbox setClientMail(ClientMailbox clientMail) {
        this.clientMail = clientMail;
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public ServerMailbox setValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public String getObj() {
        return obj;
    }

    public ServerMailbox setObj(String obj) {
        this.obj = obj;
        return this;
    }
}
