package com.jeff.shareapp.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 张武 on 2016/9/1.
 */
public class MyPageViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragList;

    public MyPageViewAdapter(List<Fragment> fragList, FragmentManager fm) {
        super(fm);
        this.fragList = fragList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

}
