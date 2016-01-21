package com.holamed.meddapp.adapter;

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

import java.util.ArrayList;

import static android.content.Intent.getIntent;


public class DetailedResultItemArrayAdapter extends ArrayAdapter<ResultItem> {
    Context context;
    public ArrayList<ResultItem> data;
    int layoutResourceId;
    // ResultItem  data[] = null;

    public DetailedResultItemArrayAdapter(Context context, int layoutResourceId, ArrayList<ResultItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DetailedResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DetailedResultHolder();
            holder.lab_name_d = (TextView)row.findViewById(R.id.lab_name_d);
            holder.lab_address_d= (TextView)row.findViewById(R.id.lab_address_d);
            holder.mrp_d=(TextView)row.findViewById(R.id.mrp_d);
            holder.cost_d=(TextView)row.findViewById(R.id.cost_d);
            holder.saving_d=(TextView)row.findViewById(R.id.saving_d);
         //   holder.rating=(TextView)row.findViewById(R.id.rating_d);
            holder.ac=(ImageView)row.findViewById(R.id.ac_boolean_icon);
            holder.ambulance=(ImageView)row.findViewById(R.id.ambulance_boolean_icon);
            holder.homecollection=(ImageView)row.findViewById(R.id.homecollection_boolean_icon);
            holder.cc_accept=(ImageView)row.findViewById(R.id.cc_accept_boolean_icon);
            holder.ac_tv=(TextView)row.findViewById(R.id.airconditioning);
            holder.ambulance_tv=(TextView)row.findViewById(R.id.ambulance);
            holder.homecollection_tv=(TextView)row.findViewById(R.id.homecollection);
            holder.cc_accept_tv=(TextView)row.findViewById(R.id.creditcardsaccepted);
            holder.selectlab=(Button)row.findViewById(R.id.bookLab);
            row.setTag(holder);
        }
        else
        {
            holder = (DetailedResultHolder)row.getTag();
        }

        final ResultItem tmp = data.get(position);
        //TestResultItem tmp=data.get(position);
        holder.lab_name_d.setText(tmp.getLabName());
        holder.lab_address_d.setText(tmp.getLabAdd());
        holder.cost_d.setText(context.getString(R.string.Rs)+String.valueOf(tmp.getPriceUser()));
        holder.saving_d.setText("You save:"+context.getString(R.string.Rs)+String.valueOf(tmp.getSaving()));
        holder.mrp_d.setText("MRP:"+context.getString(R.string.Rs)+String.valueOf(tmp.getMrp()));
        holder.mrp_d.setPaintFlags(holder.mrp_d.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if(tmp.isAc())
            holder.ac.setImageResource(R.drawable.checkmark_24);
        else
            holder.ac.setImageResource(R.drawable.orange_multiply_filled_25);
        if(tmp.isAmbulance())
            holder.ambulance.setImageResource(R.drawable.checkmark_24);
        else
            holder.ambulance.setImageResource(R.drawable.orange_multiply_filled_25);

        if(tmp.isCc_accept())
            holder.cc_accept.setImageResource(R.drawable.checkmark_24);
        else
            holder.cc_accept.setImageResource(R.drawable.orange_multiply_filled_25);
        if(tmp.isHomecollection())
            holder.homecollection.setImageResource(R.drawable.checkmark_24);
        else
            holder.homecollection.setImageResource(R.drawable.orange_multiply_filled_25);
        //  final ImageView i=(ImageView)row.findViewById(R.id.expand_newfeatures);
        // final LinearLayout[] l = {(LinearLayout) row.findViewById(R.id.expand_check)};

        Log.d("check value container", String.valueOf(container));
      /*  if(tmp.isAc())
        holder.ac.setImageResource(R.drawable.checkmark_24);
        else
            holder.ac.setImageResource(R.drawable.orange_multiply_filled_25);
        if(tmp.isAmbulance())
            holder.ambulance.setImageResource(R.drawable.checkmark_24);
        else
            holder.ambulance.setImageResource(R.drawable.orange_multiply_filled_25);

        if(tmp.isCc_accept())
            holder.cc_accept.setImageResource(R.drawable.checkmark_24);
        else
            holder.cc_accept.setImageResource(R.drawable.orange_multiply_filled_25);
        if(tmp.isHomecollection())
            holder.homecollection.setImageResource(R.drawable.checkmark_24);
        else
            holder.homecollection.setImageResource(R.drawable.orange_multiply_filled_25);*/
        // holder.num_rating.setText(String.valueOf(tmp.getNum_rating()));
       // holder.rating.setText("  "+String.valueOf(tmp.getRating()));
/*        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle the lab details
                Intent i=new Intent(context,AccountsList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });*/
        return row;
    }

    static class DetailedResultHolder
    {
        ImageView ac,homecollection,ambulance,cc_accept,expand;
        Button selectlab;
        TextView lab_name_d,lab_address_d,cost_d,mrp_d,saving_d,rating_d,ac_tv,homecollection_tv,ambulance_tv,cc_accept_tv;//,num_rating;
    }

    public void updateAdapter(ArrayList<ResultItem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data =newlist;
        this.notifyDataSetChanged();
    }
}
