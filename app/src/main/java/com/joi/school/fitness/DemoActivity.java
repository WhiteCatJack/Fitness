package com.joi.school.fitness;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.ClientMailbox;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.bean.ServerMailbox;
import com.joi.school.fitness.tools.bean.Sport;
import com.joi.school.fitness.tools.bmobsync.SyncBmobQuery;
import com.joi.school.fitness.tools.constant.MailboxConstants;
import com.joi.school.fitness.tools.user.UserEngine;
import com.joi.school.fitness.tools.util.AndroidUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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

    private ExecutorService mExecutor = Executors.newFixedThreadPool(1);

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

                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ClientMailbox mailbox = new ClientMailbox();
                            mailbox.setUser(UserEngine.getInstance().getCurrentUser());
                            mailbox.setType(MailboxConstants.TYPE_SPORT_RECOMMEND);
                            String clientMailId = mailbox.syncSave();

                            Thread.sleep(10000);

                            SyncBmobQuery<ServerMailbox> query = new SyncBmobQuery<>(ServerMailbox.class);
                            query.addWhereEqualTo("clientMail", clientMailId);
                            query.addWhereEqualTo("user", UserEngine.getInstance().getCurrentUser().getObjectId());
                            List<ServerMailbox> temp = query.syncFindObjects();
                            if (temp.size() < 1) {
                                throw new BmobException("Server not replied!");
                            }
                            ServerMailbox serverMail = temp.get(0);
                            serverMail.setValid(false);
                            serverMail.syncUpdate(serverMail.getObjectId());
                            serverMail.syncDelete();

                            JSONObject jsonObject = new JSONObject(serverMail.getObj());
                            String recommendedSportId = jsonObject.getString("sportId");
                            SyncBmobQuery<Sport> sportQuery = new SyncBmobQuery<>(Sport.class);
                            sportQuery.addWhereEqualTo("objectId", recommendedSportId);
                            List<Sport> sportList = sportQuery.syncFindObjects();
                            if (sportList.size() < 1) {
                                throw new BmobException("Sport not retrieved!");
                            }
                            final Sport targetSport = sportList.get(0);
                            DemoActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissLoadingDialog();
                                    buildRecommendDialog(targetSport);
                                }
                            });
                        } catch (Exception e) {
                            AndroidUtils.showErrorMainThread(DemoActivity.this, e);
                        }
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

        ImageView mealImageView = layout.findViewById(R.id.iv_image);
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

        Glide.with(layout.getContext()).load(meal.getMealImageUrl())
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .placeholder(R.drawable.ui_placeholder)
                .into(mealImageView);
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

        ImageView sportImageView = layout.findViewById(R.id.iv_image);
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


        Glide.with(layout.getContext()).load(sport.getSportImageUrl())
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .placeholder(R.drawable.ui_placeholder)
                .into(sportImageView);
        sportNameView.setText(sport.getSportKind());

        dialog.show();
    }
}
