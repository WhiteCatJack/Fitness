package com.joi.school.fitness.core.sport;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.R;
import com.joi.school.fitness.constant.MailboxConstants;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.ClientMailbox;
import com.joi.school.fitness.tools.bean.ServerMailbox;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.util.FrescoUtils;
import com.joi.school.fitness.tools.util.Navigation;
import com.joi.school.fitness.user.UserEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/1 0001 15:28
 */
public class SportRecommendActivity extends BaseActivity {

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

                ClientMailbox mailbox = new ClientMailbox();
                mailbox.setUser(UserEngine.getCurrentUser());
                mailbox.setType(MailboxConstants.TYPE_SPORT_RECOMMEND);
                mailbox.save(new SaveListener<String>() {
                    @Override
                    public void done(final String clientMailId, BmobException e) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException ignore) {
                                }
                                BmobQuery<ServerMailbox> query = new BmobQuery<>();
                                query.addWhereEqualTo("clientMail", clientMailId);
                                query.addWhereEqualTo("user", UserEngine.getCurrentUser().getObjectId());
                                query.findObjects(new FindListener<ServerMailbox>() {
                                    @Override
                                    public void done(List<ServerMailbox> list, final BmobException e) {
                                        if (e == null && list != null && list.size() > 0) {
                                            ServerMailbox mail = list.get(0);
                                            try {
                                                JSONObject jsonObject = new JSONObject(mail.getObj());
                                                String recommendedSportId = jsonObject.getString("sportId");
                                                BmobQuery<Sport> sportQuery = new BmobQuery<>();
                                                sportQuery.addWhereEqualTo("objectId", recommendedSportId);
                                                sportQuery.findObjects(new FindListener<Sport>() {
                                                    @Override
                                                    public void done(final List<Sport> list, final BmobException e) {
                                                        SportRecommendActivity.this.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                dismissLoadingDialog();
                                                                if (e == null && list != null && list.size() > 0) {
                                                                    buildRecommendDialog(list.get(0));
                                                                } else {
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            } catch (JSONException ignore) {
                                            }
                                            mail.setValid(false);
                                            mail.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                }
                                            });
                                        } else {
                                        }
                                    }
                                });
                            }
                        }).start();
                    }
                });
            }
        });
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
                dialog.dismiss();
            }
        });
        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FrescoUtils.setImageUrl(sportImageView, sport.getSportImageUrl());
        sportNameView.setText(sport.getSportKind());

        dialog.show();
    }
}
