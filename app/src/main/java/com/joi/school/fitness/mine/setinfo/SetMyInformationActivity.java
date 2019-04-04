package com.joi.school.fitness.mine.setinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.joi.school.fitness.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.base.BiCallback;
import com.joi.school.fitness.user.FitnessUser;
import com.joi.school.fitness.user.UserEngine;
import com.joi.school.fitness.util.AndroidUtils;
import com.joi.school.fitness.util.BmobUtils;
import com.joi.school.fitness.util.UserUtils;
import com.joi.school.fitness.view.NormalCellView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 20:00
 */
public class SetMyInformationActivity extends BaseActivity {

    private static final int SET_AVATAR_FILE_SYSTEM_PHOTO_REQUEST_CODE = 1;
    private static final int SET_AVATAR_CAMERA_PHOTO_REQUEST_CODE = 2;

    private NormalCellView mSetAvatarCellView;
    private EditText mSetNicknameEditText;
    private View mSetNicknameButton;
    private EditText mSetPersonalizedSignatureEditText;
    private View mSetPersonalizedSignatureButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_my_information);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SET_AVATAR_FILE_SYSTEM_PHOTO_REQUEST_CODE:
                case SET_AVATAR_CAMERA_PHOTO_REQUEST_CODE:
                    String filePath = AndroidUtils.getRealFilePath(AndroidUtils.getFileUriFromIntent(data));
                    if (!TextUtils.isEmpty(filePath)) {
                        showLoadingDialog();
                        BmobUtils.uploadFile(filePath, new BiCallback<String>() {
                            @Override
                            public void done(String url) {
                                FitnessUser fitnessUser = new FitnessUser();
                                fitnessUser.setAvatarUrl(url);
                                changeUserInfo(fitnessUser);
                            }

                            @Override
                            public void error(String message) {
                                AndroidUtils.showUnknownErrorToast();
                            }
                        });
                    } else {
                        AndroidUtils.showUnknownErrorToast();
                    }
                    break;
            }
        }
    }

    private void initView() {
        mSetAvatarCellView = findViewById(R.id.cell_set_my_avatar);
        mSetNicknameEditText = findViewById(R.id.et_nick);
        mSetNicknameButton = findViewById(R.id.bt_change_nick);
        mSetPersonalizedSignatureEditText = findViewById(R.id.et_personalized_signature);
        mSetPersonalizedSignatureButton = findViewById(R.id.bt_change_personalized_signature);

        setUserInfo();

        mSetAvatarCellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.showPhotoChoiceDialog(SetMyInformationActivity.this,
                        SET_AVATAR_CAMERA_PHOTO_REQUEST_CODE, SET_AVATAR_FILE_SYSTEM_PHOTO_REQUEST_CODE);
            }
        });
        mSetNicknameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FitnessUser fitnessUser = new FitnessUser();
                fitnessUser.setNick(mSetNicknameEditText.getText().toString());
                changeUserInfo(fitnessUser);
            }
        });
        mSetPersonalizedSignatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FitnessUser fitnessUser = new FitnessUser();
                fitnessUser.setSignature(mSetPersonalizedSignatureEditText.getText().toString());
                changeUserInfo(fitnessUser);
            }
        });
    }

    private void changeUserInfo(@NonNull FitnessUser fitnessUser) {
        if (fitnessUser == null) {
            return;
        }
        showLoadingDialog();
        fitnessUser.update(UserEngine.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dismissLoadingDialog();
                if (e == null) {
                    setUserInfo();
                    UserUtils.infoChanged(getApplicationContext());
                } else {
                    AndroidUtils.showUnknownErrorToast();
                }
            }
        });
    }

    private void setUserInfo() {
        mSetAvatarCellView.setIconUrl(UserEngine.getCurrentUser().getAvatarUrl());
    }
}
