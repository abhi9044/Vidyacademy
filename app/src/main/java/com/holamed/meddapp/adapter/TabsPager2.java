package com.holamed.meddapp.adapter;

/**
 * Created by Abhishek on 9/14/2015.
 */

import com.holamed.meddapp.OrderTabsFragment;
import com.holamed.meddapp.HistoryTabsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.holamed.meddapp.HistoryTabsFragment;
import com.holamed.meddapp.OrderTabsFragment;

public class TabsPager2 extends FragmentPagerAdapter {

    public TabsPager2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new OrderTabsFragment();
            case 1:
                // Games fragment activity
                return new HistoryTabsFragment();
            }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}