package com.joi.school.fitness.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.FitnessUser;
import com.joi.school.fitness.tools.constant.BroadcastConstants;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.FrescoUtils;
import com.joi.school.fitness.tools.util.Navigation;
import com.joi.school.fitness.tools.util.UserUtils;

public class MineFragment extends Fragment {

    private SimpleDraweeView mMyAvatarView;
    private TextView mMyNickNameTextView;
    private TextView mMyPersonalizedSignatureTextView;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mine, container, false);
        View myInformationCard = layout.findViewById(R.id.cv_my_information);
        mMyAvatarView = myInformationCard.findViewById(R.id.iv_avatar);
        mMyNickNameTextView = myInformationCard.findViewById(R.id.tv_nickname);
        mMyPersonalizedSignatureTextView = myInformationCard.findViewById(R.id.tv_personalized_signature);

        myInformationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToSetMyInformationActivity(getContext());
            }
        });
        layout.findViewById(R.id.bt_sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtils.signOut(getContext());
            }
        });
        layout.findViewById(R.id.bt_physical_statistic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToSetPhysicalStatisticActivity(getContext());
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUserInfo();
        registerBroadcast();
    }

    public void setUserInfo() {
        FitnessUser user = UserEngine.getInstance().getCurrentUser();
        FrescoUtils.setImageUrl(mMyAvatarView, user.getAvatarUrl());
        mMyNickNameTextView.setText(user.getNick());
        mMyPersonalizedSignatureTextView.setText(user.getSignature());
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        if (mBroadcastReceiver != null) {
            mBroadcastReceiver = null;
        }
        super.onDestroy();
    }

    private void registerBroadcast() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BroadcastConstants.BROADCAST_USER_INFO_CHANGED.equals(action)
                        || BroadcastConstants.BROADCAST_USER_SIGN_OUT.equals(action)) {
                    setUserInfo();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConstants.BROADCAST_USER_INFO_CHANGED);
        filter.addAction(BroadcastConstants.BROADCAST_USER_SIGN_OUT);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver, filter);
    }
}
