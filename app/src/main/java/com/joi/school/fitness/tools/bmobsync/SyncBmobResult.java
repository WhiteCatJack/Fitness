package com.joi.school.fitness.tools.bmobsync;

import cn.bmob.v3.exception.BmobException;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/13 0013 14:56
 */
class SyncBmobResult<T> {

    T data;
    BmobException exception;

    void setUp(T data, BmobException exception) {
        this.data = data;
        this.exception = exception;
    }
}
