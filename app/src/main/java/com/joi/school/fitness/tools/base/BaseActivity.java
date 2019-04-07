package com.joi.school.fitness.tools.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.joi.school.fitness.R;
import com.joi.school.fitness.sign.SignInActivity;

import cn.bmob.v3.BmobUser;
import es.dmoral.toasty.Toasty;

/**
 * @author Joi
 * createAt 2019/3/22 0022 17:24
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (needCheckLogin() && !BmobUser.isLogin()) {
                // 提示请先登录，回到sign in
                Toasty.warning(getApplicationContext(), R.string.warning_not_sign_in, Toast.LENGTH_SHORT, true).show();
                startActivity(new Intent(this, SignInActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }

    /**
     * 判断是否需要检查登录态
     * 默认需要，可以通过重写来控制
     */
    protected boolean needCheckLogin() {
        return true;
    }

    protected void showLoadingDialog(){
        progressDialog.show();
    }
    protected void dismissLoadingDialog(){
        progressDialog.dismiss();
    }
}
