package com.joi.school.fitness.tools.bean;

import cn.bmob.v3.BmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/22 0022 17:19
 */
public class PhysicalStatistic extends BmobObject {
    private FitnessUser user;
    private double heartRate;
    private double height;
    private double weight;
    private double vitalCapacity;

    public FitnessUser getUser() {
        return user;
    }

    public void setUser(FitnessUser user) {
        this.user = user;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVitalCapacity() {
        return vitalCapacity;
    }

    public void setLungCapacity(double vitalCapacity) {
        this.vitalCapacity = vitalCapacity;
    }
}
