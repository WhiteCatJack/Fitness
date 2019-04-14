package com.joi.school.fitness.tools.util;

import com.joi.school.fitness.tools.base.RpcCallback;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/4 0004 20:17
 */
public class BmobUtils {
    public static void uploadFile(String filePath, final RpcCallback<String> callback) {
        if (callback == null) {
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(filePath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onSuccess(bmobFile.getFileUrl());
                } else {
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer ignore) {
            }
        });
    }
}
