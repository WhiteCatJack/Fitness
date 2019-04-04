package com.joi.school.fitness.base;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 17:47
 */
public interface BiCallback<T> {
    void done(T data);
    void error(String message);
}
