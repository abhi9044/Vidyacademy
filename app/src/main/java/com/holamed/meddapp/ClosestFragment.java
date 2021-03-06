package com.holamed.meddapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.holamed.meddapp.adapter.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClosestFragment extends BaseFragment {
    static ObservableListView listView;
    static ResultItemArrayAdapter closestAdapter;
    static ArrayList<ResultItem> ClosesortedFetched;
    static TextView tvno2;
    static View header;
    static Boolean isViewset = false;
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("shreyDebug", "onCreateView TopRatedFragment");

        View rootView = inflater.inflate(R.layout.fragment_closest, container, false);
        tvno2 = (TextView) rootView.findViewById(R.id.no_resultclo);
        Activity parentActivity = getActivity();
        listView = (ObservableListView) rootView.findViewById(R.id.scroll_cl);
        setDummyDataWithHeader(listView, inflater.inflate(R.layout.padding, listView, false));

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


        ClosesortedFetched = AppControllerSearchTests.fetched;
        closest_sort();
        // array = new ResultItem[AppControllerSearchTests.fetched.size()];
        //AppControllerSearchTests.fetched.toArray(array);
        closestAdapter = new ResultItemArrayAdapter(getActivity(), R.layout.listview_row_item_lab, ClosesortedFetched, "closest");
        // testsListView.addHeaderView(header);
        listView.setAdapter(closestAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView pricePerTest=(TextView) view.findViewById(R.id.pricePerTest);
                if (pricePerTest.getVisibility()==View.GONE)
                    pricePerTest.setVisibility(View.VISIBLE);
                else
                    pricePerTest.setVisibility(View.GONE);

                /*Log.d("in onItemClick", "yes");

                if (AppControllerSearchTests.imageDBhelper.isOpen())
                    AppControllerSearchTests.imageDBhelper.close();
                Log.d("header", String.valueOf(listView.getHeaderViewsCount()));
                Log.d("headerP", String.valueOf(position));
//                Log.d("headerV", String.valueOf(ClosesortedFetched.get(position)));
                //       if(position<testsListView.getHeaderViewsCount())
                //         return;

                ResultItem tmp = ClosesortedFetched.get(position - listView.getHeaderViewsCount());
                Log.d("in onItemClick", "yes");
                Intent intent = new Intent(getActivity(), Detailed_result.class);
                //intent.putExtra("expandoption",tmp);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                AppControllerSearchTests.setHomecollection(tmp.isHomecollection());

                AppControllerSearchTests.setSelectedLab(tmp);
                startActivity(intent);*/
            }
        });

        //if(!AppControllerSearchTests.locSet) {
        header = (View) getActivity().getLayoutInflater().inflate(R.layout.listview_header_row, null);
        Log.d("header", String.valueOf(header));
        if (!AppControllerSearchTests.locSet) {
            Log.d("header", "if");
            listView.addHeaderView(header);
        }
        //  testsListView.addHeaderView(header);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView) getActivity().findViewById((R.id.txtHeader));

                if (text.getText().toString() == "Getting your location ...") return;
                text.setText("Getting your location ...");
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                callGPSSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppControllerSearchTests.getInstance().startActivity(callGPSSettingIntent);
                listView.removeHeaderView(header);


            }
        });


        //  closest_sort();


        Log.d("closest ", String.valueOf(AppControllerSearchTests.fetched.size()));


        return rootView;
    }

    public static void closest_sort() {

        Collections.sort(ClosesortedFetched, new Comparator<ResultItem>() {
                    public int compare(ResultItem x, ResultItem y) {


                        if (x.getDistanceValue().compareTo(y.getDistanceValue()) != 0)
                            return x.getDistanceValue().compareTo(y.getDistanceValue());

                        else
                            return y.getRating().compareTo(x.getRating());


                    }
                }
        );

        //sort fetched in closest order
    }

    public static void loadDistance() {

        for (int j = 0; j < AppControllerSearchTests.fetched.size(); j++) {
            Log.d(" load distance", String.valueOf(AppControllerSearchTests.fetched.get(j).getLatitude()));
            AppControllerSearchTests.fetched.get(j).setLatLong(AppControllerSearchTests.fetched.get(j).getLatitude(), AppControllerSearchTests.fetched.get(j).getLongitude());
        }

    }

    static void upd() {
        tvno2.setVisibility(View.VISIBLE);
    }

    static public void loadView() throws NullPointerException {


        Log.d("in closest", "loadview");
        if (AppControllerSearchTests.fetched.isEmpty()) {
            Log.d("in closest", "loadview empty");
            return;
        }

        if (!AppControllerSearchTests.locSet) {
            try {


                Log.d("fetched len", String.valueOf(ClosesortedFetched.isEmpty()));

                if (listView.getAdapter() == null) listView.setAdapter(closestAdapter);
                closestAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                listView.removeHeaderView(header);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ClosesortedFetched = (ArrayList) AppControllerSearchTests.fetched;
            closest_sort();
            // topRatedAdapter.clear();
            // topRatedAdapter.addAll(sortedFetched);
            closestAdapter.notifyDataSetChanged();
            if (listView.getAdapter() == null) listView.setAdapter(closestAdapter);
            Log.d("Dcheck length", String.valueOf(closestAdapter.getCount()));
            Log.d("close adap", String.valueOf(listView.getAdapter().getCount()));


            AppControllerSearchTests.NeedUpdate = false;

        }
    }


}