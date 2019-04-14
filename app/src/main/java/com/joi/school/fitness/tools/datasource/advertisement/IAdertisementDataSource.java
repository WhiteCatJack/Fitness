package com.joi.school.fitness.tools.datasource.advertisement;

import android.support.annotation.IntRange;

import com.joi.school.fitness.tools.bean.Advertisement;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * @author Joi
 * createAt 2019/3/22 0022 10:47
 */
public interface IAdertisementDataSource {
    /**
     * 查询所有
     */
    List<Advertisement> getAll() throws BmobException;

    List<Advertisement> getAll(@IntRange(from = 0) int start, @IntRange(from = 0) int amount) throws BmobException;
}
