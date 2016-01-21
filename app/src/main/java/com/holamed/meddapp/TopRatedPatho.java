package com.holamed.meddapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.holamed.meddapp.adapter.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopRatedPatho extends BaseFragment {
    static TextView tvno;
    static ObservableListView listView;
    static com.holamed.meddapp.adapter.ResultItemArrayAdapterPatho topRatedAdapter;
    static ArrayList<ResultItem> sortedFetched;
    static ProgressDialog pDialog;
    private static Activity act;
    private static View rootView;
    private static View header;
    private static Context c;
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    static Animation SlideDown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        act=getActivity();
        //initializing animations
        SlideDown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);



        rootView = inflater.inflate(R.layout.fragment_top_rated_patho, container, false);
        Activity parentActivity = getActivity();
        listView = (ObservableListView) rootView.findViewById(R.id.scroll_tr);
        setDummyDataWithHeader(listView, inflater.inflate(R.layout.padding, listView, false));
        tvno=(TextView)rootView.findViewById(R.id.no_resulttop);
        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            // Scroll to the specified position after layout
            Bundle args = getArguments();
            if (args != null && args.containsKey(ARG_INITIAL_POSITION)) {
                final int initialPosition = args.getInt(ARG_INITIAL_POSITION, 0);
                ScrollUtils.addOnGlobalLayoutListener(listView, new Runnable() {
                    @Override
                    public void run() {
                        // scrollTo() doesn't work, should use setSelection()
                        listView.setSelection(initialPosition);
                    }
                });
            }

            // TouchInterceptionViewGroup should be a parent view other than ViewPager.
            // This is a workaround for the issue #117:
            // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
            listView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.root));

            listView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }

        final Button bcheckout = (Button) rootView.findViewById(R.id.bcheckout);
        bcheckout.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    bcheckout.setBackgroundColor(getResources().getColor(R.color.primary));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    bcheckout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });

        bcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultItem selectedLabp=AppControllerSearchTests.getSelectedLabPatho();
                ResultItem selectedlabr=AppControllerSearchTests.getSelectedLabRadio();
                if((selectedlabr==null))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Book atleast one Lab to Checkout",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent i=new Intent(getActivity().getApplicationContext(),Memo.class);
                    startActivity(i);
                }

            }
        });
        act=getActivity();

        sortedFetched = AppControllerSearchTests.fetchedhome;
        topRated_sort();
        // array = new ResultItem[AppControllerSearchTests.fetched.size()];
        //AppControllerSearchTests.fetched.toArray(array);
        topRatedAdapter = new com.holamed.meddapp.adapter.ResultItemArrayAdapterPatho(getActivity(), R.layout.listview_row_item_lab, sortedFetched,"toprated");
        // testsListView.addHeaderView(header);
        listView.setAdapter(topRatedAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("in onItemClick", "yes");
                Log.d("listview ",String.valueOf(position));
                if(position<listView.getHeaderViewsCount())
                    return;

                if(AppControllerSearchTests.imageDBhelper.isOpen())
                    AppControllerSearchTests.imageDBhelper.close();
                ResultItem tmp = sortedFetched.get(position-listView.getHeaderViewsCount());

                Log.d("in onItemClick", "yes");
                Intent intent = new Intent(getActivity(), Detailed_result_patho.class);
                //intent.putExtra("expandoption",tmp);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                AppControllerSearchTests.setHomecollection(tmp.isHomecollection());

                AppControllerSearchTests.setSelectedLabPatho(tmp);
                startActivity(intent);
            }
        });
        header = (View) getActivity().getLayoutInflater().inflate(R.layout.listview_header, null);

        //if(!AppControllerSearchTests.locSet) {
        Log.d("header", String.valueOf(header));
        if(!AppControllerSearchTests.locSet) {
            Log.d("header", "if");
            listView.addHeaderView(header);

        }
        return rootView;
    }


    static void loadView() {
        Log.d("in top rated", "loadview");
        sortedFetched=(ArrayList)AppControllerSearchTests.fetchedhome;
        topRated_sort();
        // topRatedAdapter.clear();
        // topRatedAdapter.addAll(sortedFetched);
        topRatedAdapter.notifyDataSetChanged();
        Log.d("Dcheck length", String.valueOf(topRatedAdapter.getCount()));

        topRatedAdapter.updateAdapter(sortedFetched);
        listView.invalidate();
        listView.invalidateViews();
        listView.setVisibility(View.VISIBLE);
        //testsListView.startAnimation(SlideDown);
        //this was for animation

        AppControllerSearchTests.NeedUpdate = false;

        CategorySearchPatho.pDialog.hide();
        // topRatedAdapter.notifyDataSetChanged();
    }
    static void upd()
    {
        tvno.setVisibility(View.VISIBLE);
    }


    private static void topRated_sort() {

        //sort sortedFetched in toprated order

        Collections.sort(sortedFetched, new Comparator<ResultItem>() {
                    public int compare(ResultItem x, ResultItem y) {


                        if (x.getRating().compareTo(y.getRating()) != 0)
                            return y.getRating().compareTo(x.getRating());
                        else
                            return ((Double) x.getPriceUser()).compareTo((Double) y.getPriceUser());


                    }
                }
        );


    }
}