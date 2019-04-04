package com.joi.school.fitness.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;

import com.joi.school.fitness.base.BiCallback;

import java.io.ByteArrayOutputStream;

import cn.bmob.v3.Bmob;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 20:06
 */
public class AndroidUtils {
    public static Context getApplicationContext() {
        return Bmob.getApplicationContext();
    }

    public static void requestCameraPhoto(Activity activity, int requestCode) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePhotoIntent, requestCode);
        }
    }

    public static void requestFileSystemPhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void readCameraPhoto(final Intent data, final BiCallback<String> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle extras = data.getExtras();
                try {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    callback.done(convertBitmapToBase64(bitmap));
                } catch (Exception e) {
                    callback.error(e.getMessage());
                }
            }
        }).start();
    }

    public static void readGalleryPhoto(final Activity activity, final Intent data, final BiCallback<String> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
                        callback.done(convertBitmapToBase64(bitmap));
                    } catch (Exception e) {
                        callback.error(e.getMessage());
                    }
                }
            }
        }).start();
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
