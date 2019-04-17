package com.joi.school.fitness.tools.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.R;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/17 0017 19:01
 */
public class GlideUtils {

    // 1级Api
    public static void setImageUrl(
            @NonNull ImageView imageView,
            @NonNull String imageUrl) {
        setImageUrl(imageView, imageUrl, false);
    }

    // 2级Api
    public static void setImageUrl(
            @NonNull ImageView imageView,
            @NonNull String imageUrl, boolean useDefaultPlaceHolder) {
        Drawable placeHolder = null;
        if (useDefaultPlaceHolder) {
            placeHolder = AndroidUtils.getApplicationContext().getResources().getDrawable(R.drawable.ui_placeholder);
        }
        setImageUrl(imageView, imageUrl, null, placeHolder);
    }

    public static void setImageUrl(
            @NonNull ImageView imageView,
            @NonNull String imageUrl,
            @Nullable BitmapTransformation transformation) {
        setImageUrl(imageView, imageUrl, transformation, null);
    }

    // 3级Api
    public static void setImageUrl(
            @NonNull ImageView imageView,
            @NonNull String imageUrl,
            @Nullable BitmapTransformation transformation,
            @Nullable Drawable placeHolder) {
        if (imageView == null || imageView.getContext() == null || imageUrl == null) {
            return;
        }
        RequestBuilder<Drawable> requestBuilder = Glide.with(imageView.getContext()).load(imageUrl);
        if (transformation != null) {
            requestBuilder = requestBuilder.apply(RequestOptions.bitmapTransform(transformation));
        }
        if (placeHolder != null) {
            requestBuilder = requestBuilder.placeholder(placeHolder);
        }
        requestBuilder.into(imageView);
    }
}
