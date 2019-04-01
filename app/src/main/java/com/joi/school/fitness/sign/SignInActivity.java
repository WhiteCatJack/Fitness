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
public class SignInActivity extends BaseActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mSignInButton;
    private TextView mSignUpLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignProcessActivityCache.registerActivity(this);

        setContentView(R.layout.activity_sign_in);
        initView();
    }

    @Override
    protected void onDestroy() {
        SignProcessActivityCache.unregisterActivity(this);
        super.onDestroy();
    }

    private void initView() {
        mUsernameEditText = findViewById(R.id.et_username);
        mPasswordEditText = findViewById(R.id.et_password);
        mSignInButton = findViewById(R.id.bt_sign_in);
        mSignUpLink = findViewById(R.id.link_sign_up);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FitnessUser user = new FitnessUser();
                user.setUsername(mUsernameEditText.getText().toString().trim());
                user.setPassword(mPasswordEditText.getText().toString().trim());
                user.login(new SaveListener<FitnessUser>() {
                    @Override
                    public void done(FitnessUser fitnessUser, BmobException e) {
                        if (e == null) {
                            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
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
        mSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    @Override
    protected boolean needCheckLogin() {
        return false;
    }
}
