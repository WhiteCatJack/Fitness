package com.joi.school.fitness.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.joi.school.fitness.BaseActivity;
import com.joi.school.fitness.HomeActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.user.FitnessUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author 泽乾
 * createAt 2019/3/22 0022 17:22
 */
public class SignUpActivity extends BaseActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mSignUpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignProcessActivityCache.registerActivity(this);
        setContentView(R.layout.activity_sign_up);

        initView();
    }

    @Override
    protected void onDestroy() {
        SignProcessActivityCache.registerActivity(this);
        super.onDestroy();
    }

    private void initView() {
        mUsernameEditText = findViewById(R.id.et_username);
        mPasswordEditText = findViewById(R.id.et_password);
        mSignUpButton = findViewById(R.id.bt_sign_up);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FitnessUser user = new FitnessUser();
                user.setUsername(mUsernameEditText.getText().toString().trim());
                user.setPassword(mPasswordEditText.getText().toString().trim());
                user.signUp(new SaveListener<FitnessUser>() {
                    @Override
                    public void done(FitnessUser fitnessUser, BmobException e) {
                        if (e == null) {
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            SignProcessActivityCache.clearCache();
                        } else {
                            /*
                                弹出警告
                             */
                        }
                    }
                });
            }
        });
    }

    @Override
    protected boolean needCheckLogin() {
        return false;
    }
}
