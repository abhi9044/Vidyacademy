package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * Created by Abhishek on 10/20/2015.
 */
public class HealthCheckUpNew extends AppCompatActivity {
    private ViewPager viewPagernew;
    private View mHeaderView;
    public static ProgressDialog pDialog;
    static Dialog alertDialog;
    private ViewPagerAdapter mAdapternew;
    private ActionBar actionBar;
    private View mToolbarViewnew;

    private String[] tabs = {"ALL", "Heart", "Women","Common Tests"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checkup_new);
        AppControllerSearchTests.setRefferalCode(null);
        AppControllerSearchTests.setHomecollHealth(false);
        AppControllerSearchTests.setLabVisit(false);
        AppControllerSearchTests.setLabIds(null);
        AppControllerSearchTests.setLabsname(null);
        AppControllerSearchTests.setAddresslab(null);
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        Toolbar parent=(Toolbar) customActionBarView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("Health Checkups");
        ImageView home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HealthCheckUpNew.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        AppControllerSearchTests.fetchDeals();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        alertDialog = new Dialog(this);

        //setting custom layout to dialog
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.custom_alert_dialog);
        Button one = (Button) alertDialog.findViewById(R.id.back);
        one.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                onBackPressed();
                alertDialog.dismiss();
            }
        });

        Button two = (Button) alertDialog.findViewById(R.id.try_again);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);


        ImageView back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HealthCheckUpNew.this, SecondActivityMain.class);
                startActivity(i);
                // finish();
            }
        });

        mAdapternew = new ViewPagerAdapter(getSupportFragmentManager(), tabs, 4);
        viewPagernew = (ViewPager) findViewById(R.id.pagernew);
        viewPagernew.setAdapter(mAdapternew);
        viewPagernew.setOffscreenPageLimit(3);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertabnew);
        //viewPagerTab.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        viewPagerTab.setSelectedIndicatorColors(getResources().getColor(R.color.primary));
        viewPagerTab.setDefaultTabTextColor(getResources().getColor(R.color.primary));
        viewPagerTab.setDistributeEvenly(true);
        viewPagerTab.setViewPager(viewPagernew);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private int mScrollY = 0;

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        CharSequence Titles[] = {"ALL", "Heart", "Women", "Common Tests"};
        int NumbOfTabs = 4; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new Fragment_HealthCheckUp_All();
            else if (position == 1)
                return new Fragment_HealthCheckUp_Cat1();
            else if (position == 2)
                return new Fragment_HealthCheckUp_Cat4();
            else if (position == 3)
                return new Fragment_HealthCheckUp_Cat6();
            else
                return new Fragment_HealthCheckUp_Cat1();


        }


        // This method return the titles for the Tabs in the Tab Strip

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }

        // This method return the Number of tabs for the tabs Strip

        @Override
        public int getCount() {
            return NumbOfTabs;
        }
    }

    public static void showAlertDialog() {

        alertDialog.show();
    }

    public void onBackPressed() {
        Log.d("Back", "Pressed");
        Intent i = new Intent(HealthCheckUpNew.this, SecondActivityMain.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
