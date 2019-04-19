package com.joi.school.fitness.forum;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/18 0018 20:17
 */
class SlidesPagerAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;
    private final List<Fragment> mFragmentList;

    public SlidesPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragmentList) {
        super(fm);
        this.mTitles = titles;
        this.mFragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}
