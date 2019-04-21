package com.joi.school.fitness.tools.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.widget.Toast;

import com.joi.school.fitness.R;

import java.io.ByteArrayOutputStream;

import cn.bmob.v3.Bmob;
import es.dmoral.toasty.Toasty;

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

    public static int toPx(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int toDp(int pxValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) ((float) pxValue / scale + 0.5f);
    }

    public static void showPhotoChoiceDialog(@NonNull final Activity activity,
                                             final int requestCameraCode, final int requestFileSystemCode) {
        String[] choices = {
                activity.getString(R.string.from_camera),
                activity.getString(R.string.from_file_system)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        AndroidUtils.requestCameraPhoto(activity, requestCameraCode);
                        break;
                    case 1:
                        AndroidUtils.requestFileSystemPhoto(activity, requestFileSystemCode);
                        break;
                }
            }
        });
        builder.create().show();
    }

    public static void showPhotoChoiceDialog(@NonNull final Fragment fragment,
                                             final int requestCameraCode, final int requestFileSystemCode) {
        String[] choices = {
                getApplicationContext().getResources().getString(R.string.from_camera),
                getApplicationContext().getResources().getString(R.string.from_file_system)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        AndroidUtils.requestCameraPhoto(fragment, requestCameraCode);
                        break;
                    case 1:
                        AndroidUtils.requestFileSystemPhoto(fragment, requestFileSystemCode);
                        break;
                }
            }
        });
        builder.create().show();
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

    public static void requestCameraPhoto(Fragment fragment, int requestCode) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            fragment.startActivityForResult(takePhotoIntent, requestCode);
        }
    }

    public static void requestFileSystemPhoto(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Nullable
    public static Uri getFileUriFromIntent(Intent intent) {
        return intent.getData();
    }

    @Nullable
    public static String getRealFilePath(@Nullable Uri uri) {
        String data = null;
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = Bmob.getApplicationContext().getContentResolver()
                    .query(uri, new String[]{
                            MediaStore.Images.ImageColumns.DATA
                    }, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static Bitmap readPhotoFromIntent(final Activity activity, final Intent data) {
        Uri imageUri = getFileUriFromIntent(data);
        Bitmap bitmap = null;
        if (imageUri != null) {
            try {
                bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap convertBase64ToBitmap(String base64) {
        byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static String getString(@StringRes int id) {
        return getApplicationContext().getResources().getString(id);
    }

    public static void showUnknownErrorToast() {
        showError(R.string.unknown_error);
    }

    public static void showToast(String content) {
        Toasty.normal(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int contentId) {
        showToast(getString(contentId));
    }

    public static void showWarning(String content) {
        Toasty.warning(getApplicationContext(), content, Toast.LENGTH_SHORT, true).show();
    }

    public static void showWarning(@StringRes int contentId) {
        showWarning(getString(contentId));
    }

    public static void showError(String content) {
        Toasty.error(getApplicationContext(), content, Toast.LENGTH_SHORT, true).show();
    }

    public static void showError(@StringRes int contentId) {
        showError(getString(contentId));
    }

    public static void showAlert(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content);
        builder.create().show();
    }

    public static void showAlert(Context context, @StringRes int contentId) {
        showAlert(context, getString(contentId));
    }

    public static void showErrorMainThread(Activity activity, final Exception e) {
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtils.showError(e.getMessage());
            }
        });
    }
}
