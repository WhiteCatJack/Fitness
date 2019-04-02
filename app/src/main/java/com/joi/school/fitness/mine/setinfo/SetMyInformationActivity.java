package com.joi.school.fitness.mine.setinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.joi.school.fitness.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.user.UserEngine;
import com.joi.school.fitness.view.NormalCellView;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/1 0001 20:00
 */
public class SetMyInformationActivity extends BaseActivity {

    private NormalCellView mSetAvatarCellView;
    private View mSetNicknameCellView;
    private View mSetPersonalizedSignatureCellView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_my_information);

        initView();
    }

    private void initView() {
        mSetAvatarCellView = findViewById(R.id.cell_set_my_avatar);
        mSetNicknameCellView = findViewById(R.id.cell_set_my_nickname);
        mSetPersonalizedSignatureCellView = findViewById(R.id.cell_set_my_personalized_signature);

        mSetAvatarCellView.setIconUrl(UserEngine.getCurrentUser().getAvatarUrl());

        mSetAvatarCellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSetNicknameCellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSetPersonalizedSignatureCellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
