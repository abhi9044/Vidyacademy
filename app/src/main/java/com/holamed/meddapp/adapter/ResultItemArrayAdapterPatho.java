package com.holamed.meddapp.adapter;

/**
 * Created by Pradeep on 10-04-2015.
 */

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.Memo;
import com.holamed.meddapp.R;
import com.holamed.meddapp.ResultItem;

import java.util.ArrayList;

import static android.content.Intent.getIntent;


public class ResultItemArrayAdapterPatho extends ArrayAdapter<ResultItem> {
    Context context;
    public ArrayList<ResultItem> data;
    int layoutResourceId;
    String frag;
    // ResultItem  data[] = null;
    public ResultItemArrayAdapterPatho(Context context, int layoutResourceId, ArrayList<ResultItem> data, String frag) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.frag=frag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResultHolder();
            //      holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.lab_add=(TextView)row.findViewById(R.id.lab_add);
            holder.lab_address= (TextView)row.findViewById(R.id.lab_address);
            holder.tvcharges=(TextView)row.findViewById(R.id.tvhcharges);

            if(frag.equals("toprated")) {
                holder.lab_add.setVisibility(View.GONE);
                holder.lab_address.setVisibility(View.GONE);
                holder.tvcharges.setVisibility(View.VISIBLE);
            }
            holder.lab_name = (TextView)row.findViewById(R.id.lab_name);
            holder.mrp=(TextView)row.findViewById(R.id.mrp);
            holder.cost=(TextView)row.findViewById(R.id.cost);
            holder.saving=(TextView)row.findViewById(R.id.saving);
            holder.rating=(TextView)row.findViewById(R.id.rating);
            holder.circle=(LinearLayout)row.findViewById(R.id.rating_circle);
            holder.select=(Button)row.findViewById(R.id.selectlab);
            holder.nabl=(TextView)row.findViewById(R.id.nabl);

            // holder.expand=(ImageView)row.findViewById(R.id.expand_newfeatures);
            // holder.num_rating=(TextView)row.findViewById(R.id.number_rating);

            holder.ac=(ImageView)row.findViewById(R.id.ac_boolean_icon);
            holder.ambulance=(ImageView)row.findViewById(R.id.ambulance_boolean_icon);
            holder.homecollection=(ImageView)row.findViewById(R.id.homecollection_boolean_icon);
            holder.cc_accept=(ImageView)row.findViewById(R.id.cc_accept_boolean_icon);
            holder.ac_tv=(TextView)row.findViewById(R.id.airconditioning);
            holder.ambulance_tv=(TextView)row.findViewById(R.id.ambulance);
            //    holder.homecollection_tv=(TextView)row.findViewById(R.id.homecollection);
            holder.cc_accept_tv=(TextView)row.findViewById(R.id.creditcardsaccepted);
            //      holder.homecollectionava=(TextView)row.findViewById(R.id.homecollectionava);
//            holder.direction=(ImageButton)row.findViewById(R.id.direction_list);


        /*
            holder.ambulance.setVisibility(container.GONE);
            holder.ac.setVisibility(container.GONE);
            holder.homecollection.setVisibility(container.GONE);
            holder.cc_accept.setVisibility(container.GONE);

            holder.ambulance_tv.setVisibility(container.GONE);
            holder.ac_tv.setVisibility(container.GONE);
            holder.homecollection_tv.setVisibility(container.GONE);
            holder.cc_accept_tv.setVisibility(container.GONE);
            */
            row.setTag(holder);


        }
        else
        {
            holder = (ResultHolder)row.getTag();
        }

        final ResultItem tmp = data.get(position);
        //TestResultItem tmp=data.get(position);
        double a=tmp.getPriceMrp();
        double b=tmp.getPriceUser();
        double disc=(a-b)/a*100;
        int dis=(int)disc;
        holder.lab_name.setText(tmp.getLabName());
        holder.lab_address.setText(tmp.getLabAdd());
        holder.tvcharges.setText("Home Collection Charge:"+context.getString(R.string.Rs)+tmp.getPriceHome());


        holder.cost.setText(context.getString(R.string.Rs) + String.valueOf(tmp.getPriceUser()));
        holder.saving.setText(dis+"% OFF");
        holder.mrp.setText(context.getString(R.string.Rs) + String.valueOf(tmp.getPriceMrp()));
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if(tmp.isHomecollection())
        { AppControllerSearchTests.setHomecollection(true);
            //  holder.homecollectionava.setText("Home collection available");

        }
        else
        {  AppControllerSearchTests.setHomecollection(false);
            //holder.homecollectionava.setText("Home collection not available");
        }
        if(tmp.isNabl())
        {
            holder.nabl.setText("NABL Accredited");
        }
        else
        {
            holder.nabl.setVisibility(View.GONE);
        }
        final ResultHolder finalHolder = holder;
        if(tmp.getRating()>-1)
            holder.rating.setText(String.valueOf(tmp.getRating()));
        else holder.circle.setVisibility(View.INVISIBLE);
        final String lab_id=tmp.getLabID();
        String phone=tmp.getLabPhone();
        final ResultHolder finalHolder1 = holder;
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle the lab details

                Intent i=new Intent(context,Memo.class);
                AppControllerSearchTests.setSelectedLabPatho(tmp);
                if(frag.equals("toprated")) {
                    AppControllerSearchTests.setHomecollection(true);
                    AppControllerSearchTests.setPriceHome(tmp.getPriceHome());

                }else
                    AppControllerSearchTests.setHomecollection(false);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                ResultItem selectedLab = AppControllerSearchTests.getSelectedLabPatho();
            //    Toast.makeText(context,selectedLab.getLabName()+" Selected",Toast.LENGTH_SHORT).show();

                      context.startActivity(i);

            }
        });
        return row;
    }

    static class ResultHolder
    {
        ImageView ac,homecollection,ambulance,cc_accept,expand;
        TextView homecollectionava,tvcharges,lab_add,lab_name,lab_address,cost,mrp,saving,rating,ac_tv,homecollection_tv,ambulance_tv,cc_accept_tv,nabl;//,num_rating;
        Button select;
        ImageButton direction;
        LinearLayout circle;
    }

    public void updateAdapter(ArrayList<ResultItem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data =newlist;
        this.notifyDataSetChanged();
    }
}
