package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holamed.meddapp.FaqObject;
import com.holamed.meddapp.R;

import java.util.ArrayList;

/**
 * Created by prabhat on 6/15/2015.
 */


import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class swipeListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    public swipeListAdapter(Activity context,
                            String[] web) {
        super(context, R.layout.swipe_list_single, web);
        this.context = context;
        this.web = web;


    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.swipe_list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt67);


        txtTitle.setText(web[position]);


        return rowView;
    }
}