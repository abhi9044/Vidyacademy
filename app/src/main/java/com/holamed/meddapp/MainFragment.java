package com.holamed.meddapp;

import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    public SharedPreferences loginPrefs;
    ArrayList<String> a=new ArrayList<String>();

    public SharedPreferences.Editor loginEditor;
    private View mHeaderView;
    CharSequence[] citysel=null;
    private int mBaseTranslationY;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private ActionBar actionBar;
    private View mToolbarView;
    static ProgressDialog pDialog;
    public static Dialog alertDialog;
    private boolean isLoggedIn;
    private String test_picked;
    private TestsDB db;
    private ImageView home;
    private ImageView back;
    public static TextView noResultView;
    public static LocationManager locationManager;
    public static Boolean isAdapterSet=false;
DatabaseHandler db2;

    private String city;
    private List<String> categories;
    // Tab titles
    private String[] tabs = { "Order", "History"};
Button citySelect;
    int position=0;
    private MixpanelAPI mixpanel;
    private  String projectToken = "39504217e737bb956ceb8c20ca0a34ee";

    @Override
    public void onResume() {
        super.onResume();
        if(!loginPrefs.contains("citySelected")){
            citySelect.performClick();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db2=new DatabaseHandler(getActivity());
        LayoutInflater inflater1 = (LayoutInflater)((AppCompatActivity)getActivity()).getSupportActionBar()
                .getThemedContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater1.inflate(R.layout.actionbarcustomdropdown, null);
        AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
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

        citySelect=(Button) customActionBarView.findViewById(R.id.bcityselectMainFragment);
        final List<Contact> contacts = db2.getAllContacts();
        for (Contact cn : contacts) {

            String log = cn.getName();
            // Writing Contacts to log
a.add(log);
            a.indexOf(log);
            Log.d("Name: ", ""+a.indexOf(log));
            }
       citysel= a.toArray(new CharSequence[a.size()]);

        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        city=loginPrefs.getString("citySelected","Indore");
        citySelect.setText(city);

/*
        mHeaderView = rootView.findViewById(R.id.header);
        mHeaderView.setBackgroundColor(getResources().getColor(R.color.primary));
        mToolbarView=rootView.findViewById(R.id.toolbar);
        TextView title_name= (TextView) mToolbarView.findViewById(R.id.custom_title);
        title_name.setText("SELECT LAB");
        title_name.setGravity(Gravity.CENTER);
*/
      /*  TextView title_name= (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("SEARCH TESTS");
      */


//Spinner replaced by button

      citySelect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              city=loginPrefs.getString("citySelected","Indore");
              int pos =a.indexOf(city);
Log.d("positionwa",""+pos);
              AlertDialog.Builder builder2=new AlertDialog.Builder(getActivity())
                      .setTitle("Select your city")
                      .setSingleChoiceItems(citysel, pos, new DialogInterface.OnClickListener() {

                          @Override
                          public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub

                              loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                              loginEditor = loginPrefs.edit();
        loginEditor.putString("citySelected",citysel[which].toString());
        loginEditor.commit();

                              city=loginPrefs.getString("citySelected","Indore");
citySelect.setText(city);
                              Toast.makeText(getActivity(),
                                      city+" selected", Toast.LENGTH_LONG).show();

//dismissing the dialog when the user makes a selection.
                              dialog.dismiss();
                          }
                      });
              AlertDialog alertdialog2=builder2.create();
           alertdialog2.show();
          }



      });

        //Old spinner code by era
   /*
        Spinner spinner = (Spinner) customActionBarView.findViewById(R.id.citySpinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        categories = new ArrayList<>();
        categories.add("Mumbai");
        categories.add("Indore");
*/

  //Old code by era
        // Creating adapter for spinner
      /*
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(categories.indexOf(city));
*/
        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //floating action button
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(getActivity(),SearchTests.class));
            }
        });

        mAdapter= new ViewPagerAdapter(getActivity().getSupportFragmentManager(),tabs,2);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);

        /*actionBar = getActivity().getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener((ActionBar.TabListener) getActivity()));
        }*/
        SmartTabLayout viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        //viewPagerTab.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
        viewPagerTab.setSelectedIndicatorColors(getResources().getColor(R.color.primary));
        viewPagerTab.setDefaultTabTextColor(getResources().getColor(R.color.primary));
        viewPagerTab.setDistributeEvenly(true);
        viewPagerTab.setViewPager(viewPager);
        isLoggedIn=loginPrefs.getBoolean("saved",false);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                switch(i){
                    case 0:Log.d("LV", "OT");

                        OrderTabsFragment.loadView();
                        break;
                    case 1:Log.d("LV", "HT");
                        Log.d("LVisSaved", String.valueOf(isLoggedIn));
                    if(isLoggedIn)
                             HistoryTabsFragment.loadView();
                   //     else
                     //   {

                       //     Intent intent = new Intent(getActivity(), FacebookCheckActivity.class);
                         //   getActivity().startActivity(intent);
                        //}

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        forceTabs();
        isAdapterSet=false;



        return rootView;
    }
    public void forceTabs() {
        try {
            final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            final Method setHasEmbeddedTabsMethod = actionBar.getClass()
                    .getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.setAccessible(true);
            setHasEmbeddedTabsMethod.invoke(actionBar, false);
        }
        catch(final Exception e) {
            // Handle issues as needed: log, warn user, fallback etc
            // This error is safe to ignore, standard tabs will appear.
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        city=categories.get(position);
        Log.d("city",city);
        loginEditor.putString("citySelected",city);
        loginEditor.commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private int mScrollY=0;
        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }
        CharSequence Titles[]={ "Order", "History"};
        int NumbOfTabs=2; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {

            if(position == 0) // if the position is 0 we are returning the First tab
            {
                return new OrderTabsFragment();
            }
            else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
            {
//       if(isLoggedIn)
                return new HistoryTabsFragment();
//else
  //                  return new LoginFragment();
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