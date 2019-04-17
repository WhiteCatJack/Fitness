package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/3/22 0022 17:19
 */
public class PhysicalStatistic extends SyncBmobObject {
    private FitnessUser user;
    private double heartRate;
    private double height;
    private double weight;
    private double vitalCapacity;

    public FitnessUser getUser() {
        return user;
    }

    public PhysicalStatistic setUser(FitnessUser user) {
        this.user = user;
        return this;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public PhysicalStatistic setHeartRate(double heartRate) {
        this.heartRate = heartRate;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public PhysicalStatistic setHeight(double height) {
        this.height = height;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public PhysicalStatistic setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public double getVitalCapacity() {
        return vitalCapacity;
    }

    public PhysicalStatistic setVitalCapacity(double vitalCapacity) {
        this.vitalCapacity = vitalCapacity;
        return this;
    }
}
