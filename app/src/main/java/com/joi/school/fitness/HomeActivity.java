package com.joi.school.fitness;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.joi.school.fitness.core.CoreFragment;
import com.joi.school.fitness.forum.ForumFragment;
import com.joi.school.fitness.home.NewsFragment;
import com.joi.school.fitness.mine.MineFragment;
import com.joi.school.fitness.tools.base.BaseActivity;
import com.joi.school.fitness.tools.util.DataManipulator;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    public static final String FRAGMENT_TAG_CORE = "fragment_tag_core";
    public static final String FRAGMENT_TAG_NEWS = "fragment_tag_news";
    public static final String FRAGMENT_TAG_FORUM = "fragment_tag_forum";
    public static final String FRAGMENT_TAG_MINE = "fragment_tag_mine";

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
        setContentView(R.layout.activity_main);

        setBottomNavigationBar();
        switchFragment(FRAGMENT_TAG_CORE);
        checkPermission();
        manipulateData();
    }

    private void manipulateData() {
        DataManipulator.HeatRecordDataManipulator.createHeatRecordTable();
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(this,
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
