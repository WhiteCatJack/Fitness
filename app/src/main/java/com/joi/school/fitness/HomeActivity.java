package com.joi.school.fitness;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.joi.school.fitness.core.CoreFragment;
import com.joi.school.fitness.forum.ForumFragment;
import com.joi.school.fitness.home.NewsFragment;
import com.joi.school.fitness.mine.MineFragment;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.bean.Meal;
import com.joi.school.fitness.tools.util.AndroidUtils;
import com.joi.school.fitness.tools.util.DataManipulator;

public class HomeActivity extends BaseActivity implements IHomeContract.View {

    private static final String TAG = "HomeActivity";

    public static final String FRAGMENT_TAG_CORE = "fragment_tag_core";
    public static final String FRAGMENT_TAG_NEWS = "fragment_tag_news";
    public static final String FRAGMENT_TAG_FORUM = "fragment_tag_forum";
    public static final String FRAGMENT_TAG_MINE = "fragment_tag_mine";

    private static final int MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE = 1;
    private static final int MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE = 2;

    private IHomeContract.Presenter mPresenter;
    private int mRecongnitionType;

    @Override
    public void mealRecognitionDone(final Meal meal, final int type) {
        dismissLoadingDialog();
        if (meal == null) {
            AndroidUtils.showUnknownErrorToast();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("识别成功!识别的菜品是:" + meal.getMealName() + ", 请问正确吗?");
        builder.setPositiveButton("正确", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.uploadMealHeatRecord(meal, type);
            }
        }).setNegativeButton("不", null);
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE:
                case MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE:
                    Bitmap bitmap = AndroidUtils.readPhotoFromIntent(this, data);
                    if (bitmap != null) {
                        showLoadingDialog();
                        mPresenter.doMealRecognition(bitmap, mRecongnitionType);
                    } else {
                        AndroidUtils.showUnknownErrorToast();
                    }
                    break;
            }
        }
    }

    @StringDef({FRAGMENT_TAG_CORE, FRAGMENT_TAG_NEWS, FRAGMENT_TAG_FORUM, FRAGMENT_TAG_MINE})
    private @interface FragmentTag {
    }

    private CoreFragment mCoreFragment;
    private NewsFragment mNewsFragment;
    private ForumFragment mForumFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new HomePresenter(this);
        setContentView(R.layout.activity_main);

        setBottomNavigationBar();
        switchFragment(FRAGMENT_TAG_CORE);
        checkPermission();
//        manipulateData();
        showSlidesIfNeeded();
    }

    private void showSlidesIfNeeded() {
//        SharedPreferences...
        startActivity(new Intent(this, SlidesActivity.class));
    }

    private void manipulateData() {
        DataManipulator.HeatRecordDataManipulator.createHeatRecordTable();
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                },
                0
        );
    }

    private void setBottomNavigationBar() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchFragment(FRAGMENT_TAG_NEWS);
                        return true;
                    case R.id.navigation_core:
                        switchFragment(FRAGMENT_TAG_CORE);
                        return true;
                    case R.id.navigation_forum:
                        switchFragment(FRAGMENT_TAG_FORUM);
                        return true;
                    case R.id.navigation_mine:
                        switchFragment(FRAGMENT_TAG_MINE);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_bar_meal_recognize) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setItems(new String[]{"早餐", "中餐", "晚餐"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mRecongnitionType = which + 1;
                    AndroidUtils.showPhotoChoiceDialog(HomeActivity.this,
                                                       MEAL_SCAN_CAMERA_PHOTO_REQUEST_CODE, MEAL_SCAN_FILE_SYSTEM_PHOTO_REQUEST_CODE
                    );
                }
            });
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchFragment(@FragmentTag String fragmentTag) {
        Fragment targetFragment;
        switch (fragmentTag) {
            case FRAGMENT_TAG_CORE:
                if (mCoreFragment == null) {
                    mCoreFragment = new CoreFragment();
                }
                 targetFragment = mCoreFragment;
                break;
            case FRAGMENT_TAG_FORUM:
                if (mForumFragment == null) {
                    mForumFragment = new ForumFragment();
                }
                targetFragment = mForumFragment;
                break;
            case FRAGMENT_TAG_MINE:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                }
                targetFragment = mMineFragment;
                break;
            case FRAGMENT_TAG_NEWS:
            default:
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                }
                targetFragment = mNewsFragment;
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.add(R.id.fl_fragment_container, targetFragment);
        } else {
            transaction
                    .hide(mCurrentFragment)
                    .show(targetFragment);
        }
        mCurrentFragment = targetFragment;
        transaction.commit();
    }

}
