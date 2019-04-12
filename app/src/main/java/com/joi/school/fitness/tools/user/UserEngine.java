package com.joi.school.fitness.tools.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.FitnessUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 16:20
 */
public class UserEngine implements UserInterface {

    private static volatile UserEngine sInstance;

    private UserEngine() {
    }

    public static UserEngine getInstance() {
        if (sInstance == null) {
            synchronized (UserEngine.class) {
                if (sInstance == null) {

                    sInstance = new UserEngine();
                }
            }
        }
        return sInstance;
    }

    /**
     * 通过{@link BaseActivity#onResume()}默认检查登录态，如果没有登录会强制跳转到登录页面
     */
    @NonNull
    @Override
    public FitnessUser getCurrentUser() {
        return BmobUser.getCurrentUser(FitnessUser.class);
    }

    @Override
    public boolean hasPhysicalStatistic() {
        return getCurrentUser().getLatestStatistic() != null;
    }

    @Override
    public boolean isSignedIn(@NonNull Context context) {
        return getCurrentUser() != null;
    }

    @Override
    public void signIn(@NonNull FitnessUser user, SaveListener<FitnessUser> listener) {
        if (user == null || listener == null) {
            return;
        }
        user.login(listener);
    }

    @Override
    public void signUp(@NonNull FitnessUser user, SaveListener<FitnessUser> listener) {
        if (user == null || listener == null) {
            return;
        }
        user.signUp(listener);
    }

    @Override
    public void signOut() {
        if (getCurrentUser() != null) {
            BmobUser.logOut();
        }
    }

    @Override
    public void update(@NonNull FitnessUser user, UpdateListener listener) {
        if (user == null || listener == null) {
            return;
        }
        user.update(user.getObjectId(), listener);
    }
}
