package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/7 0007 17:41
 */
public class HeatRecord extends SyncBmobObject {
    private FitnessUser user;
    private double heatChange;

    public FitnessUser getUser() {
        return user;
    }

    public void setUser(FitnessUser user) {
        this.user = user;
    }

    public double getHeatChange() {
        return heatChange;
    }

    public void setHeatChange(double heatChange) {
        this.heatChange = heatChange;
    }
}
