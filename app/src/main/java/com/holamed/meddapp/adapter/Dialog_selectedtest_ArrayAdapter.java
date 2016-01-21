package com.holamed.meddapp.adapter;

/**
 * Created by Era on 5/14/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holamed.meddapp.Dialog_testItem;
import com.holamed.meddapp.R;
import com.holamed.meddapp.ResultItem;

import java.util.ArrayList;

/**
 * Created by Era on 5/13/2015.
 */
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.holamed.meddapp.R;
import com.holamed.meddapp.ResultItem;
import com.holamed.meddapp.SearchTests;

import java.util.ArrayList;

import static android.content.Intent.getIntent;


public class Dialog_selectedtest_ArrayAdapter extends ArrayAdapter<Dialog_testItem> {
    Context context;
    public ArrayList<Dialog_testItem> data;
    int layoutResourceId;
    SearchTests o;
    TestsListAdapter a;
    private CustomAutoCompleteAdapter autocomplete_adapt;

    // ResultItem  data[] = null;

    public Dialog_selectedtest_ArrayAdapter(Context context, int layoutResourceId, ArrayList<Dialog_testItem> data,TestsListAdapter a,CustomAutoCompleteAdapter autocomplete_adapt) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.a=a;
        this.autocomplete_adapt=autocomplete_adapt;
        o=new SearchTests();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DetailedResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DetailedResultHolder();
            holder.test_name = (TextView)row.findViewById(R.id.selected_test_name);
           // holder.test_description= (TextView)row.findViewById(R.id.test_information);
            holder.reomve=(ImageView)row.findViewById(R.id.remove_dialog_test);
            holder.test_icon=(ImageView)row.findViewById(R.id.list_image_dialog);
            row.setTag(holder);
        }
        else
        {
            holder = (DetailedResultHolder)row.getTag();
        }

        final Dialog_testItem tmp = data.get(position);
        //TestResultItem tmp=data.get(position);
        holder.test_name.setText(tmp.getTestname());
       // holder.test_description.setText(tmp.getTestdescription());

        holder.reomve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle the lab details
                Integer index = (Integer) v.getTag();
                //items.remove(index.intValue());
                Log.d("positioninadapter", String.valueOf(position));
                o.positiondeletedindialog(position, a,data.get(position).getTestname(),autocomplete_adapt);

                //autocomplete_adapt.removeFromClickedString(data.get(position).getTestname());
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return row;
    }
    public void clearData() {
        // clear the data
        Log.d("clear data adapter","yes");
        data.clear();

    }
    static class DetailedResultHolder
    {
        ImageView reomve,test_icon;
        TextView test_name,test_description;
    }

    public void updateAdapter(ArrayList<Dialog_testItem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data =newlist;
        this.notifyDataSetChanged();
    }
}

