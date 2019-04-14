package com.joi.school.fitness;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.ClientMailbox;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.bean.ServerMailbox;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.constant.MailboxConstants;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.FrescoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Description.
 *
 * @author 泽乾
 * createAt 2019/4/14 0014 21:49
 */
public class DemoActivity extends BaseActivity {

    private List<Meal> likeList = new ArrayList<>();
    private List<Meal> unlikeList = new ArrayList<>();

    private View mSportRecommendButton;
    private View mMealRecommendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mSportRecommendButton = findViewById(R.id.bt_sport_recommend);
        mMealRecommendButton = findViewById(R.id.bt_meal_recommend);

        mSportRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();

                ClientMailbox mailbox = new ClientMailbox();
                mailbox.setUser(UserEngine.getInstance().getCurrentUser());
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
                                query.addWhereEqualTo("user", UserEngine.getInstance().getCurrentUser().getObjectId());
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
                                                        DemoActivity.this.runOnUiThread(new Runnable() {
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

        mMealRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Meal> query = new BmobQuery<>();
                query.findObjects(new FindListener<Meal>() {
                    @Override
                    public void done(List<Meal> list, BmobException e) {
                        dismissLoadingDialog();
                        if (e == null) {
                            buildRecommendDialog(getMealRandomly(preDo(list)));
                        } else {

                        }
                    }
                });
            }
        });
    }


    private List<Meal> preDo(List<Meal> netData) {
        for (Meal unlike : unlikeList) {
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
    private Meal getMealRandomly(@NonNull List<Meal> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        int randomIndex = (int) (Math.random() * size);
        return list.get(randomIndex);
    }

    private void buildRecommendDialog(@NonNull final Meal meal) {
        if (meal == null) {
            return;
        }

        View layout = LayoutInflater.from(this).inflate(R.layout.dialog_make_recommend, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();

        SimpleDraweeView mealImageView = layout.findViewById(R.id.iv_image);
        TextView mealNameView = layout.findViewById(R.id.tv_label);
        View yesView = layout.findViewById(R.id.tv_yes);
        View noView = layout.findViewById(R.id.tv_no);

        yesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeList.add(meal);
                dialog.dismiss();
            }
        });
        noView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlikeList.add(meal);
                dialog.dismiss();
            }
        });

        FrescoUtils.setImageUrl(mealImageView, meal.getMealImageUrl());
        mealNameView.setText(meal.getMealName());

        dialog.show();
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
