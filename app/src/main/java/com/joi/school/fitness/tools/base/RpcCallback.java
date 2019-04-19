package com.joi.school.fitness.tools.base;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 17:47
 */
public interface RpcCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}
