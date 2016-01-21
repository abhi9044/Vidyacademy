package com.holamed.meddapp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import com.nineoldandroids.view.ViewHelper;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static android.content.Intent.getIntent;

//10 april
@SuppressLint("NewApi")
public class CategorySearchPatho extends AppCompatActivity implements

        LocationListener, ObservableScrollViewCallbacks {
    private View mHeaderView;
    ArrayList<String> test_picked_patho;


    int i = 0;
    private int mBaseTranslationY;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private ActionBar actionBar;
    private View mToolbarView;
    static ProgressDialog pDialog;
    public static Dialog alertDialog;
    String cityprefs;
    private String test_picked;
    private TestsDB db;
    SharedPreferences loginPrefs;
    private ImageView home;
    private ImageView back;
    public static TextView noResultView;
    public static LocationManager locationManager;
    public static Boolean isAdapterSet = false;
    // Tab titles
    private String[] tabs = {"Home Collection", "Lab Visit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this,"Swipe left to see labs near you ->>",Toast.LENGTH_LONG).show();
        loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        cityprefs = loginPrefs.getString("citySelected", "Indore");
        AppControllerSearchTests.NeedUpdate = true;
        setContentView(R.layout.activity_search);
        db = new TestsDB(this);
        try {
            test_picked_patho = AppControllerSearchTests.getSelectedPatho();
        } catch (Exception io) {
            Toast.makeText(this, "Not available please try again", Toast.LENGTH_SHORT).show();
        }


        mHeaderView = findViewById(R.id.header);
        mHeaderView.setBackgroundColor(getResources().getColor(R.color.primary));
        mToolbarView = findViewById(R.id.toolbar);
        TextView title_name = (TextView) mToolbarView.findViewById(R.id.custom_title);
        try {
                title_name.setText("STEP-2(PATHOLOGY LABS)");
              } catch (Exception io) {
            Toast.makeText(this, "Not available please try again.", Toast.LENGTH_SHORT).show();
        }
        title_name.setGravity(Gravity.CENTER);
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategorySearchPatho.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CategorySearchPatho.this, CategorySearchRadio.class);
                startActivity(i);
            }
        });
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabs, 2);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        //viewPagerTab.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        viewPagerTab.setSelectedIndicatorColors(getResources().getColor(R.color.white));
        viewPagerTab.setDefaultTabTextColor(getResources().getColor(R.color.white));
        viewPagerTab.setDistributeEvenly(true);
            viewPagerTab.setViewPager(viewPager);


        // When the page is selected, other fragments' scrollY should be adjusted
        // according to the toolbar status(shown/hidden)

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                propagateToolbarState(toolbarIsShown());
                    switch (i) {
                        case 0:
                            Log.d("Top rated selected", "yes");

                            TopRatedPatho.loadView();
                            break;
                        case 1:
                            Log.d("Closest selected", "yes");

                            //         if (!AppControllerSearchTests.locSet) {
                            //                locWork();
                            //          }
                                    ClosestFragmentPatho.loadView();
                            break;
                    }

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        forceTabs();
        isAdapterSet = false;

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        propagateToolbarState(toolbarIsShown());

        noResultView = (TextView) findViewById(R.id.no_result);


        if (AppControllerSearchTests.NeedUpdate) {

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            alertDialog = new Dialog(CategorySearchPatho.this);
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


            try {
                AppControllerSearchTests.fetchdatapatho(test_picked_patho, cityprefs)//fetches data and stores them in fetched[] Array
                ;
                // pDialog.hide();
                //Log.d("Dialog closed", "yes");
            } catch (JSONException e) {
                Log.d("failed Dialog closed", "yes");


                pDialog.hide();
                e.printStackTrace();

                new AlertDialog.Builder(this)
                        .setTitle("Hang On !!")
                        .setMessage("A Error Occured.\nPlease try again Later")
                        .setPositiveButton(R.string.Try_Again, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                recreate();
                            }
                        })
                        .setNegativeButton(R.string.Back, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // back
                                // Intent i = new Intent(CategorySearch.this, SearchTests.class);
                                //startActivity(i);
                                finish();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false)
                        .show();
            }

        }


    }

    protected void onViewCreated(View view, Bundle savedInstanceState) {

    }


    public static void showAlertDialog() {

        alertDialog.show();
    }

    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        // Location location = ClosestFragment.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        AppControllerSearchTests.setLatitude(location.getLatitude());
        AppControllerSearchTests.setLongitude(location.getLongitude());
        if (!AppControllerSearchTests.locSet) {
            AppControllerSearchTests.locSet = true;
            ClosestFragmentPatho.loadDistance();
            ClosestFragmentPatho.loadView();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();

        ClosestFragmentPatho.listView.removeHeaderView(ClosestFragmentPatho.header);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            AppControllerSearchTests.setLatitude(location.getLatitude());
            AppControllerSearchTests.setLongitude(location.getLongitude());
            AppControllerSearchTests.locSet = true;

            ClosestFragmentPatho.loadDistance();
            ClosestFragmentPatho.loadView();

        }
    }


    @Override
    public void onProviderDisabled(String provider) {

    }

    public void locWork() {


        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (!isAdapterSet) {
                ClosestFragmentPatho.listView.addHeaderView(ClosestFragmentPatho.header);
                // ClosestFragment.testsListView.setAdapter(ClosestFragment.closestAdapter);
                isAdapterSet = true;
            }
        } else {
            if (!isAdapterSet)// ClosestFragment.testsListView.setAdapter(ClosestFragment.closestAdapter);
                isAdapterSet = true;

        }
        ClosestFragmentPatho.listView.setAdapter(ClosestFragmentPatho.closestAdapter);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);

            Log.d("Network", "Network");
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Log.d("Got Location", "yes");
                    AppControllerSearchTests.setLatitude(location.getLatitude());
                    AppControllerSearchTests.setLongitude(location.getLongitude());
                    AppControllerSearchTests.locSet = true;
                    ClosestFragmentPatho.loadDistance();
                    ClosestFragmentPatho.loadView();
                    return;
                } else {
                    Log.d("Network", "Null");
                }
            }

        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
            Log.d("Gps", "Gps");
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Log.d("Got Location", "yes");
                    AppControllerSearchTests.setLatitude(location.getLatitude());
                    AppControllerSearchTests.setLongitude(location.getLongitude());
                    AppControllerSearchTests.locSet = true;
                    ClosestFragmentPatho.loadDistance();
                    ClosestFragmentPatho.loadView();
                    return;
                } else {
                    Log.d("Gps", "Null");

                }
            }

        }


        //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);


        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            Log.d("Got Location", "yes");
            AppControllerSearchTests.setLatitude(location.getLatitude());
            AppControllerSearchTests.setLongitude(location.getLongitude());
            AppControllerSearchTests.locSet = true;
            ClosestFragmentPatho.loadDistance();
            ClosestFragmentPatho.loadView();
            return;

        }

        return;
    }

    public void onBackPressed() {
        Intent i = new Intent(CategorySearchPatho.this, SearchTests.class);
        startActivity(i);
        finish();
        super.onBackPressed();

    }

    public void forceTabs() {
        try {
            final ActionBar actionBar = getSupportActionBar();
            final Method setHasEmbeddedTabsMethod = actionBar.getClass()
                    .getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.setAccessible(true);
            setHasEmbeddedTabsMethod.invoke(actionBar, false);
        } catch (final Exception e) {
            // Handle issues as needed: log, warn user, fallback etc
            // This error is safe to ignore, standard tabs will appear.
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

/*        if (dragging) {
            //Log.d("Cat","Scrolled called");
            int toolbarHeight = mToolbarView.getHeight();
            //  int toolbarHeight =0;
            float currentHeaderTranslationY = ViewHelper.getTranslationY(mHeaderView);
            if (firstScroll) {
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            com.nineoldandroids.view.ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewHelper.setTranslationY(mHeaderView, headerTranslationY);
  */
        //   }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;
        Log.d("up cancel", "scroll");

  /*
        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            Log.d("up cancel","scroll fragment null");
            return;
        }
        View view = fragment.getView();
        if (view == null) {
            Log.d("up cancel","scroll view null");
            return;
        }
*/
        int toolbarHeight = mToolbarView.getHeight();
        ObservableListView listView;
        if (viewPager.getCurrentItem() == 0) {
            listView = (ObservableListView) findViewById(R.id.scroll_tr);
        } else if (viewPager.getCurrentItem() == 1)
            listView = (ObservableListView) findViewById(R.id.scroll_ch);
        else listView = (ObservableListView) findViewById(R.id.scroll_cl);

        if (listView == null) {
            Log.d("up cancel", "scroll Listview null");
            return;
        }


        int scrollY = listView.getCurrentScrollY();
        if (scrollState == ScrollState.DOWN) {
            showToolbar();
            Log.d("scroll", "down");
        } else if (scrollState == ScrollState.UP) {
            if (toolbarHeight <= scrollY) {
                hideToolbar();
                Log.d("scroll", "up hide");
            } else {
                //showToolbar();
                Log.d("scroll", "up not show");
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (toolbarIsShown() || toolbarIsHidden()) {
                // Toolbar is completely moved, so just keep its state
                // and propagate it to other pages
                propagateToolbarState(toolbarIsShown());
                Log.d("scroll", "prop");
            } else {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar();
                Log.d("scroll", "none show");
            }
        }
    }


    private void propagateToolbarState(boolean isShown) {
        int toolbarHeight = mToolbarView.getHeight();
        //int toolbarHeight =0;
        // Set scrollY for the fragments that are not created yet
        mAdapter.setScrollY(isShown ? 0 : toolbarHeight);

        // Set scrollY for the active fragments
        for (int i = 0; i < mAdapter.getCount(); i++) {
            // Skip current item
            Log.d("scroll", "i= " + String.valueOf(i));
            if (i == viewPager.getCurrentItem())
                continue;


            ObservableListView listView;

            if (i == 2) {
                listView = (ObservableListView) findViewById(R.id.scroll_cl);
            } else if (i == 1)
                listView = (ObservableListView) findViewById(R.id.scroll_ch);
            else listView = (ObservableListView) findViewById(R.id.scroll_tr);

            if (listView == null) {
                Log.d("scroll", " prop list null " + String.valueOf(viewPager.getCurrentItem() + " " + String.valueOf(i)));
                return;
            }
            if (isShown) {
                // Scroll up
                Log.d("scroll", "try prop isshown " + String.valueOf(viewPager.getCurrentItem() + " " + String.valueOf(i)));

                // if (0 < testsListView.getCurrentScrollY()) {
                listView.setSelection(0);
                Log.d("scroll", " prop isshown " + String.valueOf(viewPager.getCurrentItem() + " " + String.valueOf(i)));
                // }
            } else {
                // Scroll down (to hide padding)
                Log.d("scroll", "try prop is not shown " + String.valueOf(viewPager.getCurrentItem() + " " + String.valueOf(i)));

                //  if (testsListView.getCurrentScrollY() < toolbarHeight) {
                Log.d("scroll", " prop is not shown " + String.valueOf(viewPager.getCurrentItem() + " " + String.valueOf(i)));
                listView.setSelection(1);
                // }
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mHeaderView) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mHeaderView) == -mToolbarView.getHeight();
        // return ViewHelper.getTranslationY(mHeaderView) ==0;
    }

    private void showToolbar() {
        Log.d("tootlbar", "scroll shown");
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        if (headerTranslationY != 0) {
            com.nineoldandroids.view.ViewPropertyAnimator.animate(mHeaderView).cancel();
            com.nineoldandroids.view.ViewPropertyAnimator.animate(mHeaderView).translationY(0).setDuration(200).start();
        }
        propagateToolbarState(true);
    }

    private void hideToolbar() {
        Log.d("tootlbar", "scroll hidden");
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        int toolbarHeight = mToolbarView.getHeight();
        //int toolbarHeight =0;
        if (headerTranslationY != -toolbarHeight) {
            com.nineoldandroids.view.ViewPropertyAnimator.animate(mHeaderView).cancel();
            com.nineoldandroids.view.ViewPropertyAnimator.animate(mHeaderView).translationY(-toolbarHeight).setDuration(200).start();
        }
        propagateToolbarState(false);
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private int mScrollY = 0;

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        CharSequence Titles[] = {"Lab Visit", "Home-Collection"};
        int NumbOfTabs = 2; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {
                if (position == 0) // if the position is 0 we are returning the First tab
                {
                    return new TopRatedPatho();
                } else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
                {

                    return new ClosestFragmentPatho();

                }



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
}