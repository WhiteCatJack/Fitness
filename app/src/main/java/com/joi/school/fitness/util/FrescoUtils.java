package com.joi.school.fitness.util;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/3/23 0023 20:11
 */
public class FrescoUtils {
    public static void setImageUrl(SimpleDraweeView draweeView, @Nullable String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            draweeView.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
