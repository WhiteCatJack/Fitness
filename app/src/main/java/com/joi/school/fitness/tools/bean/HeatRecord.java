package com.joi.school.fitness.tools.bean;

import com.joi.school.fitness.tools.bmobsync.SyncBmobObject;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/7 0007 17:41
 */
public class HeatRecord extends SyncBmobObject {
    private FitnessUser user;
    private double      heatChange;
    private BmobDate    time;

    public static class Type {
        private static final int TYPE_UNKNOWN   = 0;
        public static final  int TYPE_BREAKFAST = 1;
        public static final  int TYPE_LUNCH     = 2;
        public static final  int TYPE_DINNER    = 3;
        public static final  int TYPE_EXERCISE  = 4;
    }

    // 1早餐2午餐3晚餐4运动消耗, 0不正常数值
    private int type = Type.TYPE_UNKNOWN;

    @Override
    public String syncSave() throws BmobException {
        if (type == Type.TYPE_UNKNOWN){
            return null;
        }
        return super.syncSave();
    }

    public FitnessUser getUser() {
        return user;
    }

    public HeatRecord setUser(FitnessUser user) {
        this.user = user;
        return this;
    }

    public double getHeatChange() {
        return heatChange;
    }

    public HeatRecord setHeatChange(double heatChange) {
        this.heatChange = heatChange;
        return this;
    }

    public BmobDate getTime() {
        return time;
    }

    public HeatRecord setTime(BmobDate time) {
        this.time = time;
        return this;
    }

    public int getType() {
        return type;
    }

    public HeatRecord setType(int type) {
        this.type = type;
        return this;
    }
}
