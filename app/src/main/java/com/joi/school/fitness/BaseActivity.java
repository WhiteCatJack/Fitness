package com.joi.school.fitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.joi.school.fitness.sign.SignInActivity;

import cn.bmob.v3.BmobUser;
import es.dmoral.toasty.Toasty;

/**
 * @author 泽乾
 * createAt 2019/3/22 0022 17:24
 */
public class BaseActivity extends AppCompatActivity {

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

    /**
     * 判断是否需要检查登录态
     * 默认需要，可以通过重写来控制
     */
    protected boolean needCheckLogin() {
        return true;
    }
}
