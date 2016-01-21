package com.holamed.meddapp;

/**
 * Created by prabhat on 6/20/2015.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipePageAdapter extends FragmentPagerAdapter {

    public SwipePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new CommonPathalogyFragment();

                // previos tests fragmen
                 case 1:
                // common pathology fragment
                     return new PreviousFragment();


        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
