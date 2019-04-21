package com.joi.school.fitness.tools.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.Navigation;

/**
 * @author Joi
 * createAt 2019/3/22 0022 17:24
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private Context mContext;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    private void checkLogin() {
        if (needCheckLogin() && !UserEngine.getInstance().isSignedIn(this)) {
            AndroidUtils.showWarning(AndroidUtils.getApplicationContext().getResources().getString(R.string.warning_not_sign_in));
            Navigation.goToSignInActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        if (mContext != null) {
            mContext = null;
        }
        super.onDestroy();
    }

    /**
     * 判断是否需要检查登录态
     * 默认需要，可以通过重写来控制开关
     */
    protected boolean needCheckLogin() {
        return true;
    }

    protected Context getContext() {
        return mContext;
    }

    private void checkAndInitLoadingDialog() {
        if (mProgressDialog != null) {
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress, null, false);
        mProgressDialog = new AlertDialog.Builder(this).setView(view).create();
    }

    protected void showLoadingDialog() {
        checkAndInitLoadingDialog();
        mProgressDialog.show();
    }

    protected void dismissLoadingDialog() {
        checkAndInitLoadingDialog();
        mProgressDialog.dismiss();
    }
}
