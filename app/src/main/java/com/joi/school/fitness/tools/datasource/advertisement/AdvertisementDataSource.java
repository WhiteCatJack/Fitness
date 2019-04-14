package com.joi.school.fitness.tools.datasource.advertisement;

/**
 * @author Joi
 * createAt 2019/3/22 0022 10:47
 */
public abstract class AdvertisementDataSource implements IAdertisementDataSource {
    public static AdvertisementDataSource getImpl() {
        return AdvertisementDataSourceImpl.getInstance();
    }
}
