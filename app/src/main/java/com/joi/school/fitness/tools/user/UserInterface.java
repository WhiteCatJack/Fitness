package com.joi.school.fitness.tools.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.joi.school.fitness.tools.bean.FitnessUser;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户操作.
 *
 * @author Joi
 * createAt 2019/4/12 0012 10:41
 */
interface UserInterface {

    /**
     * 检查登录态
     * 如果登陆态不正确，直接跳转至登录页面
     */
    boolean isSignedIn(@NonNull Context context);

    /**
     * 获取当前登录用户
     */
    FitnessUser getCurrentUser();

    /**
     * 是否有上传过身体信息
     */
    boolean hasPhysicalStatistic();

    /**
     * 登录
     */
    void signIn(@NonNull FitnessUser user, SaveListener<FitnessUser> listener);

    /**
     * 注册
     */
    void signUp(@NonNull FitnessUser user, SaveListener<FitnessUser> listener);

    /**
     * 登出
     */
    void signOut();

    /**
     * 更新
     */
    void update(@NonNull FitnessUser user, UpdateListener listener);
}
