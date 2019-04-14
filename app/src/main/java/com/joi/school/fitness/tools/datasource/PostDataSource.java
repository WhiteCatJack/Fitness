package com.joi.school.fitness.tools.datasource;

/**
 * @author Joi
 * createAt 2019/3/22 0022 10:47
 */
public abstract class PostDataSource implements IPostDataSource {
    public static PostDataSource getImpl() {
        return PostDataSourceImpl.getInstance();
    }
}
