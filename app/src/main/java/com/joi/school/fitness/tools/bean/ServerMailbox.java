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

    public void setUser(FitnessUser user) {
        this.user = user;
    }

    public ClientMailbox getClientMail() {
        return clientMail;
    }

    public void setClientMail(ClientMailbox clientMail) {
        this.clientMail = clientMail;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
