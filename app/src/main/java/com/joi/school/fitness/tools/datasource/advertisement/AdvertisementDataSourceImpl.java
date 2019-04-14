package com.joi.school.fitness.tools.datasource.advertisement;

import com.joi.school.fitness.tools.bean.Advertisement;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.constant.Constants;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 帖子数据.
 *
 * @author Joi
 * createAt 2019/3/22 0022 10:42
 */
class AdvertisementDataSourceImpl extends AdvertisementDataSource {

    private static volatile AdvertisementDataSourceImpl sInstance;

    private AdvertisementDataSourceImpl() {
    }

    static AdvertisementDataSourceImpl getInstance() {
        if (sInstance == null) {
            synchronized (AdvertisementDataSourceImpl.class) {
                if (sInstance == null) {
                    sInstance = new AdvertisementDataSourceImpl();
                }
            }
        }
        return sInstance;
    }

    @Override
    public List<Advertisement> getAll() throws BmobException {
        return getAll(0, Constants.AMOUNT_IN_ONE_PAGE);
    }

    @Override
    public List<Advertisement> getAll(int start, int amount) throws BmobException {
        final SyncBmobQuery<Advertisement> query = new SyncBmobQuery<>(Advertisement.class);
        query.setSkip(start == 0 ? 0 : start - 1)
                .setLimit(amount)
                .order("-createdAt")
                .include("author");

        return query.syncFindObjects();
    }
}
