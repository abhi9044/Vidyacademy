package com.holamed.meddapp;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.holamed.meddapp.adapter.BaseFragment;
import com.holamed.meddapp.adapter.DealsArrayAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Fragment_HealthCheckUp_Cat6 extends BaseFragment {
    public static ProgressDialog pDialog;
    static private ArrayList<DealsItem> dealsList;
    static private ListView listView;

    static Dialog alertDialog;
    static private DealsArrayAdapter adapternew;
    public static TextView noResultView;
    private static Activity act;
    private static View rootView;
    private static Context c;
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    static Animation SlideDown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        act = getActivity();
        //initializing animations
        SlideDown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);


        rootView = inflater.inflate(R.layout.fragment_fragment_healthcheckup, container, false);
        final Activity parentActivity = this.getActivity();
        noResultView = (TextView) rootView.findViewById(R.id.no_result);

        dealsList = new ArrayList<DealsItem>();
        listView = (ListView) rootView.findViewById(R.id.deals_listviewnew);
        adapternew = new DealsArrayAdapter(this.getActivity(), R.layout.healthcheckuprow, dealsList);
        listView.setAdapter(adapternew);

     /*
        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DealsItem tmp = AppControllerSearchTests.dealsfecthed.get(position);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                AppControllerSearchTests.setHomecollection(tmp.getDetails().isHomecollection());
                AppControllerSearchTests.setSelectedLab(tmp.getDetails());

                Intent intent = new Intent(parentActivity, Registration.class);
                startActivity(intent);


            }
        });
       */
        return rootView;
    }


    static public void loadView() {
        dealsList = AppControllerSearchTests.dealsfecthedcat6;
        Log.d("Dcheck length", String.valueOf(dealsList.size()));
        adapternew.clear();
        adapternew.addAll(dealsList);
        adapternew.notifyDataSetChanged();
        Log.d("Dcheck length", String.valueOf(adapternew.getCount()));
        listView.setAdapter(adapternew);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        // testsListView.invalidate();

        // testsListView.invalidateViews();

    }


}




