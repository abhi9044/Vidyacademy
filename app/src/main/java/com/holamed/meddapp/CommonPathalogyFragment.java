package com.holamed.meddapp;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by prabhat on 6/20/2015.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.CategorySearch;
import com.holamed.meddapp.R;
import com.holamed.meddapp.adapter.swipeListAdapter;

public class CommonPathalogyFragment extends Fragment {

    String[] names = {
            "Total Body Profile",
            "CBC",
            "SONOGRAPHY LOWER ABDOMEN WITH FILM",
            "Random Plasma Glucose",
            "MRI BRAIN PLAIN",
            "URINE CREATININE",
            "THYROID FUNCTION TEST (T3, T4, TSH)",
            "MRI CHEST PLAIN",
            "CT SCAN CHEST PLAIN"

    };

    swipeListAdapter adapter;
    ListView swipeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_common_pathalogy_fragment, container, false);

        //swipe list
        swipeList = (ListView) rootView.findViewById(R.id.pathalogy_swipe);
        adapter = new
                swipeListAdapter(getActivity(), names);

        //swipeList.setVisibility(View.INVISIBLE);
        swipeList.setAdapter(adapter);
        swipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(SearchTests.this, "You Clicked at " + idweb[+position], Toast.LENGTH_SHORT).show();
                if (names.length > 0) {
                    AppControllerSearchTests.setSelectedtest(names[+position]);
                    Intent swipeCategory = new Intent(getActivity(), CategorySearch.class);
                    startActivity(swipeCategory);
                }
            }
        });

        return rootView;
    }
}