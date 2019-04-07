package com.joi.school.fitness.core.sport;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.R;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.util.FrescoUtils;
import com.joi.school.fitness.tools.util.Navigation;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class SportRecommendActivity extends BaseActivity {

    private List<Sport> likeList = new ArrayList<>();
    private List<Sport> unlikeList = new ArrayList<>();

    private TextView mUploadExerciseButton;
    private TextView mSportRecommendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_recommend);

        mUploadExerciseButton = findViewById(R.id.bt_upload_completed_exercise);
        mSportRecommendButton = findViewById(R.id.bt_sport_recommend);

        mUploadExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToUploadExerciseActivity(SportRecommendActivity.this);
            }
        });
        mSportRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                BmobQuery<Sport> query = new BmobQuery<>();
                query.findObjects(new FindListener<Sport>() {
                    @Override
                    public void done(List<Sport> list, BmobException e) {
                        dismissLoadingDialog();
                        if (e == null) {
                            buildRecommendDialog(getSportRandomly(preDo(list)));
                        } else {

                        }
                    }
                });
            }
        });
    }

    private List<Sport> preDo(List<Sport> netData) {
        for (Sport unlike : unlikeList) {
            for (int i = 0; i < netData.size(); i++) {
                if (unlike.getObjectId().equals(netData.get(i).getObjectId())) {
                    netData.remove(i);
                }
            }
        }
        netData.addAll(likeList);
        return netData;
    }

    @Deprecated
    private Sport getSportRandomly(@NonNull List<Sport> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        int randomIndex = (int) (Math.random() * size);
        return list.get(randomIndex);
    }

    private void buildRecommendDialog(@NonNull final Sport sport) {
        if (sport == null) {
            return;
        }

        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_make_recommend, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        SimpleDraweeView sportImageView = layout.findViewById(R.id.iv_image);
        TextView sportNameView = layout.findViewById(R.id.tv_label);
        View yesView = layout.findViewById(R.id.tv_yes);
        View noView = layout.findViewById(R.id.tv_no);

        yesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeList.add(sport);
                dialog.dismiss();
            }
        });
        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlikeList.add(sport);
                dialog.dismiss();
            }
        });

        FrescoUtils.setImageUrl(sportImageView, sport.getSportImageUrl());
        sportNameView.setText(sport.getSportKind());

        dialog.show();
    }
}
