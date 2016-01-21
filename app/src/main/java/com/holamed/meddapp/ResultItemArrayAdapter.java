package com.holamed.meddapp;

/**
 * Created by Pradeep on 10-04-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultItemArrayAdapter extends ArrayAdapter<ResultItem> {
    Context context;
    public ArrayList<ResultItem> resultItemArrayList;
    int layoutResourceId;
    String frag;

    // ResultItem  data[] = null;
    public ResultItemArrayAdapter(Context context, int layoutResourceId, ArrayList<ResultItem> data, String frag) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.resultItemArrayList = data;
        this.frag = frag;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ResultHolder();

            holder.pricePerTest=(TextView) row.findViewById(R.id.pricePerTest);
            holder.infoIcon=(TextView) row.findViewById(R.id.labInfoIcon);
            Typeface font=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
            holder.infoIcon.setTypeface(font);



            holder.lab_add = (TextView) row.findViewById(R.id.lab_add);
            holder.lab_address = (TextView) row.findViewById(R.id.lab_address);
            holder.tvhcharges = (TextView) row.findViewById(R.id.tvhcharges);
            if (frag.equals("toprated")) {
                holder.lab_add.setVisibility(View.GONE);
                holder.lab_address.setVisibility(View.GONE);
                holder.tvhcharges.setVisibility(View.VISIBLE);
            }
            holder.lab_name = (TextView) row.findViewById(R.id.lab_name);
            holder.mrp = (TextView) row.findViewById(R.id.mrp);
            holder.cost = (TextView) row.findViewById(R.id.cost);
            holder.saving = (TextView) row.findViewById(R.id.saving);
            holder.rating = (TextView) row.findViewById(R.id.rating);
            holder.circle = (LinearLayout) row.findViewById(R.id.rating_circle);
            holder.select = (Button) row.findViewById(R.id.selectlab);
            holder.nabl = (TextView) row.findViewById(R.id.nabl);
            holder.homeCollectionPrice = (TextView) row.findViewById(R.id.homeCollectionPrice);

            // holder.expand=(ImageView)row.findViewById(R.id.expand_newfeatures);
            // holder.num_rating=(TextView)row.findViewById(R.id.number_rating);
            holder.tvhcharges = (TextView) row.findViewById(R.id.tvhcharges);
            holder.ac = (ImageView) row.findViewById(R.id.ac_boolean_icon);
            holder.ambulance = (ImageView) row.findViewById(R.id.ambulance_boolean_icon);
            holder.homecollection = (ImageView) row.findViewById(R.id.homecollection_boolean_icon);
            holder.cc_accept = (ImageView) row.findViewById(R.id.cc_accept_boolean_icon);
            holder.ac_tv = (TextView) row.findViewById(R.id.airconditioning);
            holder.ambulance_tv = (TextView) row.findViewById(R.id.ambulance);
            //    holder.homecollection_tv=(TextView)row.findViewById(R.id.homecollection);
            holder.cc_accept_tv = (TextView) row.findViewById(R.id.creditcardsaccepted);
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


        } else {
            holder = (ResultHolder) row.getTag();
        }

        final ResultItem currentResultItem = resultItemArrayList.get(position);
        double a = currentResultItem.getPriceMrp();
        double b = currentResultItem.getPriceUser();
        double disc = (a - b) / a * 100;
        int dis = (int) disc;

        holder.pricePerTest.setText(currentResultItem.getPricePerTest());

        //TestResultItem tmp=data.get(position);
        holder.lab_name.setText(currentResultItem.getLabName());
        holder.lab_address.setText(currentResultItem.getLabAdd());
        holder.tvhcharges.setText("Home Collection Charge:" + context.getString(R.string.Rs) + currentResultItem.getPriceHome());
        //holder.tvhcharges.setText("Extra home collection charges may apply");

        holder.cost.setText(context.getString(R.string.Rs) + String.valueOf(currentResultItem.getPriceUser()));

        holder.saving.setText(dis + "% OFF");

        holder.mrp.setText(context.getString(R.string.Rs) + String.valueOf(currentResultItem.getPriceMrp()));
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if(currentResultItem.isHomeCollectionAvailable()){
            Log.d("shreyDebug","home collection price is - "+currentResultItem.getPriceHome());
            if(currentResultItem.getPriceHome()=="0"){
                holder.homeCollectionPrice.setVisibility(View.VISIBLE);
            }
            else {
                holder.homeCollectionPrice.setVisibility(View.VISIBLE);
                holder.homeCollectionPrice.setText("Home collection price: "+currentResultItem.getPriceHome());
            }
        }else{
            holder.homeCollectionPrice.setVisibility(View.GONE);
        }

        if (currentResultItem.isHomecollection()) {
            AppControllerSearchTests.setHomecollection(true);
            //  holder.homecollectionava.setText("Home collection available");

        } else {
            AppControllerSearchTests.setHomecollection(false);
            //holder.homecollectionava.setText("Home collection not available");
        }
        if (currentResultItem.isNabl()) {
            holder.nabl.setText("NABL Accredited");
        } else {
            holder.nabl.setVisibility(View.GONE);
        }
        final ResultHolder finalHolder = holder;
        if (currentResultItem.getRating() > -1)
            holder.rating.setText(String.valueOf(currentResultItem.getRating()));
        else holder.circle.setVisibility(View.INVISIBLE);
        final String lab_id = currentResultItem.getLabID();
        String phone = currentResultItem.getLabPhone();
        final ResultHolder finalHolder1 = holder;
        final ResultHolder finalHolder2 = holder;
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle the lab details
                Intent i = new Intent(context, PastPatients.class);
                i.putExtra("goto", "Registration");
                AppControllerSearchTests.setSelectedLab(currentResultItem);
                if (frag.equals("toprated")) {
                    AppControllerSearchTests.setHomecollection(true);
                    AppControllerSearchTests.setPriceHome(currentResultItem.getPriceHome());
                } else
                    AppControllerSearchTests.setHomecollection(false);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

        holder.infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("in onItemClick", "yes");

                if (AppControllerSearchTests.imageDBhelper.isOpen())
                    AppControllerSearchTests.imageDBhelper.close();

                ResultItem tmp = currentResultItem;
                Intent intent = new Intent(context, Detailed_result.class);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                AppControllerSearchTests.setHomecollection(tmp.isHomecollection());
                AppControllerSearchTests.setSelectedLab(tmp);
                context.startActivity(intent);
            }
        });
        return row;
    }

    static class ResultHolder {
        ImageView ac, homecollection, ambulance, cc_accept, expand;
        TextView homeCollectionPrice, tvhcharges, lab_add, lab_name, lab_address, cost, mrp, saving, rating, ac_tv, homecollection_tv, ambulance_tv, cc_accept_tv, nabl, pricePerTest, infoIcon;//,num_rating;
        Button select;
        ImageButton direction;
        LinearLayout circle;
    }

    public void updateAdapter(ArrayList<ResultItem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        resultItemArrayList = newlist;
        this.notifyDataSetChanged();
    }
}
