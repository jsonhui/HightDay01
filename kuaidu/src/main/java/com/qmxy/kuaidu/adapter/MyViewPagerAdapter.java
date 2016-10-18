package com.qmxy.kuaidu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：Json on 2016/6/29 19:26
 * 邮箱：320175912@qq.com
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> mTitleDatas;

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitleDatas) {
        super(fm);
        this.fragments = fragments;
        this.mTitleDatas = mTitleDatas;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleDatas.get(position);
    }
}
