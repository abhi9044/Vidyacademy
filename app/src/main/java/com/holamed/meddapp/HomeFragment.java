package com.holamed.meddapp;

/**
 * Created by Era on 5/18/2015.
 */

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.holamed.meddapp.adapter.CustomAutoCompleteAdapter;
import com.holamed.meddapp.adapter.Dialog_selectedtest_ArrayAdapter;
import com.holamed.meddapp.adapter.NavDrawerListAdapter;
import com.holamed.meddapp.adapter.TestsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private Dialog dialog;
    private static final int AUTOCOMPLETE_DELAY = 500;
    private static final int MESSAGE_TEXT_CHANGED = 0;
    private TestsListAdapter a;
    private TestsListAdapter b;
    private Bundle selected_test_bundle;
    Animation animSideDown;
    private CustomAutoCompleteAdapter adapterl;
    private ImageView img;
    private ListView list_tests;
    private Filter filter;
    public static final String TEST_SELECTED="test_selected";
    ArrayList<String> testName = new ArrayList<String>();
    ArrayList<String> testKey = new ArrayList<String>();
    AutoCompleteTextView auto=null;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    public final static String SAVED_KEY = "saved";
    public String isSaved = "f";
    // private ArrayAdapter<String> adapter;
    private TestsDB db;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    //making auto complete edit text
    private TestsListAdapter displayadapter;
    private ListView dialog_list;
    private Dialog_selectedtest_ArrayAdapter dialog_adapter;
    private ArrayList<Dialog_testItem>  dialoglist;
    ListView list;
    private LinearLayout tutorial;
    private boolean flag;
    private int count=0;
    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        selected_test_bundle=new Bundle();
        tutorial=(LinearLayout)rootView.findViewById(R.id.tutorial_hidden);
       // setContentView(R.layout.activity_search_tests);
        // ActionBar actionBar = getSupportActionBar();
        // show the action bar
        //actionBar.show();
        dialoglist=new ArrayList<>();
        db = new TestsDB(getActivity().getApplicationContext());
       // ActionBar actionBar=getSupportActionBar();
        //actionBar.show();

        final  ArrayList<HashMap<String, String>> testsToDisplay = new ArrayList<HashMap<String, String>>();



        db.open();
        List<TestsTableSqlite> tests = db.getAllTests();

        for (TestsTableSqlite cn : tests) {
            testName.add(cn.getName());
            testKey.add(cn.getKey());
        }
        db.close();
        //auto complete
        auto = (AutoCompleteTextView) rootView.findViewById(R.id.searchEditText);
        //  auto.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_monitor_26, 0, 0, 0);
        //  adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, testName);
        adapterl = new CustomAutoCompleteAdapter(getActivity(), testName);
        auto.setThreshold(1);
         final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_TEXT_CHANGED) {
                    String enteredText = (String)msg.obj;
                    adapterl.getFilter().filter(enteredText);

                }
            }
        };

        //Set adapter to AutoCompleteTextView
        //  auto.setAdapter(adapter);
     /*  auto.setAdapter(adapterl);
       auto.setDropDownHeight(500);
                animSideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        // set animation listener
        animSideDown.setAnimationListener(this);*/
        final ArrayList<SelectedTests> parcel_selectedtests = new ArrayList<SelectedTests>();
        //final

        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String enteredText = auto.getText().toString();
                mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
                final Message msg = Message.obtain(mHandler, MESSAGE_TEXT_CHANGED, enteredText);
                mHandler.sendMessageDelayed(msg, AUTOCOMPLETE_DELAY);
                //        runThread();
                //adapterl.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        auto.setAdapter(adapterl);
        auto.setDropDownHeight(400);
        animSideDown = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_down);

        // set animation listener
        animSideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //  auto.setAdapter(adapterl);

        auto.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3)
            {
                tutorial.setVisibility(View.GONE);
                auto.setHint("Book another test");
                flag=true;
                count++;

                Log.d("mSuggestionssize", String.valueOf(adapterl.getCount()));


                for(int i=0;i<testsToDisplay.size();i++) {
                    if (testsToDisplay.get(i).get(TEST_SELECTED).equals(auto.getText().toString()))
                        flag = false;
                }
                if(flag)
                {
                    HashMap<String, String> selected_tests = new HashMap<>();
                    SelectedTests t = new SelectedTests();
                    //t.setSelected_test_name(auto.getText().toString());
                    //t.setSelected_test_name(adapterl.ReturnText(arg2));
                    t.setSelected_test_name(auto.getText().toString());

                    parcel_selectedtests.add(t);
                    selected_tests.put(TEST_SELECTED, auto.getText().toString());
                    //    selected_tests.put(TEST_SELECTED, String.valueOf(arg0.getItemAtPosition(arg2)));
                    //selected_tests.put(TEST_SELECTED, adapterl.ReturnText(arg2));
                    adapterl.addToClickedString(auto.getText().toString());
                    testsToDisplay.add(selected_tests);
                    //selected_test_bundle.putString("selectedtest", adapterl.ReturnText(arg2));
                    Dialog_testItem testfordialog=new Dialog_testItem();
                    //testfordialog.setTestname(adapterl.ReturnText(arg2));
                    testfordialog.setTestname(auto.getText().toString());
                    testfordialog.setTestdescription("I am MRI Brain! I would eat your brains!Zombie is my name!Eating thy is my ticket to Zombie fame");
                    dialoglist.add(testfordialog);

                }
                list_tests=(ListView)rootView.findViewById(R.id.List_tests);
                // Getting adapter by passing xml data ArrayList

                auto.setText("");
                displayadapter=new TestsListAdapter(getActivity(), testsToDisplay,dialoglist,adapterl);
                list_tests.setAdapter(displayadapter);
            }
        });

        // load the animation
        Spinner dropdown = (Spinner)rootView.findViewById(R.id.spinner_location);
        List<String> items = new ArrayList<>();
        items.add("Indore");
     //   items.add("bhopal");
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapterdropdown_location = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.location_spinner_item, items);
       // adapterdropdown_location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapterdropdown_location);

        Button booktest=(Button) rootView.findViewById(R.id.bookhealth);
        booktest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(testsToDisplay.isEmpty()) {

                    Dialog di = new Dialog(getActivity());
                    LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View vi = li.inflate(R.layout.none_selected_dialog, null, false);
                    di.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    di.setContentView(vi);
                    di.setCanceledOnTouchOutside(true);
                    di.show();
                }
                            else {

                                Intent category = new Intent(getActivity(), CategorySearch.class);

                                //selected_test_bundle.putString("selectedtest", dialoglist.get(0).getTestname());
                               // category.putExtras(selected_test_bundle);
                                AppControllerSearchTests.setSelectedtest(testsToDisplay.get(0).get(TEST_SELECTED));
                                startActivity(category);
                            }
                        }
                    });
                    //dialog.setTitle("Selected Tests");





        return rootView;
    }

    public void stringdeletedintestlist(String a,CustomAutoCompleteAdapter adp)
    {
        adp.removeFromClickedString(a);
        adapterl=adp;
        runThread();
        // adp.notifyDataSetChanged();
    }
    public void positiondeletedindialog(int position,TestsListAdapter ab,String a,CustomAutoCompleteAdapter b)
    {
        Log.d("position", String.valueOf(position));
        Log.d("check ab out", String.valueOf(ab));
        b.removeFromClickedString(a);
        b.notifyDataSetChanged();
        ab.customremove(position);
        ab.notifyDataSetChanged();
    }
    private void runThread()
    {
       getActivity().runOnUiThread(new Runnable() {
           public void run() {
               adapterl.notifyDataSetChanged();
           }
       });

    }

}
