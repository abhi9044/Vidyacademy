package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.DealsItem;
import com.holamed.meddapp.HealthCheckUp_Date_Select;
import com.holamed.meddapp.R;

import java.util.ArrayList;

/**
 * Created by Era on 6/9/2015.
 */
public class DealsArrayAdapter extends ArrayAdapter<DealsItem> {
    Context context;
    public ArrayList<DealsItem> data;
    int layoutResourceId;
    // ResultItem  data[] = null;

    public DealsArrayAdapter(Context context, int layoutResourceId, ArrayList<DealsItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;
        Log.d("here?ppop", String.valueOf(row));

        if (row == null) {
            Log.d("here?", String.valueOf(row));
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResultHolder();
            holder.test_name = (TextView) row.findViewById(R.id.deal_name);
            holder.deal_desc = (TextView) row.findViewById(R.id.description);
            holder.book = (Button) row.findViewById(R.id.bookhealth);
            holder.details = (TextView) row.findViewById(R.id.packageDetails);
            holder.num_tests = (TextView) row.findViewById(R.id.tvnumtests);
            holder.main_lab = (TextView) row.findViewById(R.id.main_lab);
            holder.deal_name = (TextView) row.findViewById(R.id.deal_name);
            holder.price = (TextView) row.findViewById(R.id.cost);
            holder.mrp = (TextView) row.findViewById(R.id.mrp);
            holder.saving = (TextView) row.findViewById(R.id.saving);
            //       holder.lab_name= (TextView)row.findViewById(R.id.lab_name);
            row.setTag(holder);
        } else {
            Log.d("here?dffjid", String.valueOf(row));

            holder = (ResultHolder) row.getTag();
        }
    /*    LinearLayout linearLayout=(LinearLayout)row.findViewById(R.id.healthbox);
        if((position+1)%2==0)
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.primary));
        else if((position+1)%3==0)
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.lightBlue));
        else
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
*/
        final DealsItem tmp = data.get(position);
        Log.d("tmp", String.valueOf(tmp.getDetails()));

        holder.test_name.setText(tmp.getDeal_name());
        holder.deal_desc.setText(tmp.getDeal_desc());
        holder.main_lab.setText("at " + tmp.getLab_main());
        holder.num_tests.setText("Number of tests - " + tmp.getNum_test());
        // Log.d("Correct", ""+tmp.getDetails().getSaving());
        double a = tmp.getDeal_list();
        double b = tmp.getDeal_medd();
        double dis = (a - b) / a * 100;
        int disc = (int) dis;
        holder.saving.setText(" " + disc + "% OFF");
        holder.mrp.setText(" " + context.getString(R.string.Rs) + " " + String.valueOf(tmp.getDeal_list()));
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price.setText(context.getString(R.string.Rs) + " " + String.valueOf(tmp.getDeal_medd()));

        // holder.deal_name.setText(tmp.getDeal_name());
        //  holder.lab_name.setText(tmp.getLab_name());
        final View finalRow = row;
        final ResultHolder finalHolder = holder;
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tv = (TextView) finalRow.findViewById(R.id.description);

                if (tv.getVisibility() == View.VISIBLE) {
                    // Its visible
                    tv.setVisibility(View.GONE);
                    finalHolder.details.setText("+Click here for details");

                } else {
                    // Either gone or invisible
                    tv.setVisibility(View.VISIBLE);
                    finalHolder.details.setText("-Click here for details");


                }


            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tv = (TextView) finalRow.findViewById(R.id.description);

                if (tv.getVisibility() == View.VISIBLE) {
                    // Its visible
                    tv.setVisibility(View.GONE);
                    finalHolder.details.setText("+Click here for details");

                } else {
                    // Either gone or invisible
                    tv.setVisibility(View.VISIBLE);
                    finalHolder.details.setText("-Click here for details");


                }
            }
        });
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, HealthCheckUp_Date_Select.class);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                Log.d("Homewa", "" + tmp.getHomecoll());
                Log.d("Homewa", "" + tmp.getLabvisit());
                AppControllerSearchTests.setHomecollHealth(tmp.getHomecoll());
                AppControllerSearchTests.setLabVisit(tmp.getLabvisit());
                AppControllerSearchTests.setLabsname(tmp.getLabsnames());
                AppControllerSearchTests.setLabIds(tmp.getLabsIds());
                AppControllerSearchTests.setAddresslab(tmp.getAddress_lab());
                AppControllerSearchTests.setPriceMeddHealth(String.valueOf(tmp.getDeal_medd()));
                AppControllerSearchTests.setPriceListHealth(String.valueOf(tmp.getDeal_list()));
                AppControllerSearchTests.setPriceTotalHealth(String.valueOf(tmp.getDeal_total()));
                AppControllerSearchTests.setHealthLati(tmp.getHealthLati());
                AppControllerSearchTests.setHealthLongi(tmp.getHealthLongi());
                AppControllerSearchTests.setHealth_package_name(tmp.getDeal_name());
                // AppControllerSearchTests.setSelectedDeal(tmp);
//                AppControllerSearchTests.setHomecollection(tmp.getDetails().isHomecollection());
                //              AppControllerSearchTests.setSelectedLab(tmp.getDetails());


                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        return row;
    }

    static class ResultHolder {
        TextView test_name, deal_desc, lab_name, deal_name, price, mrp, saving, details, main_lab, num_tests;
        Button book;
    }

    public void updateAdapter(ArrayList<DealsItem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data = newlist;
        this.notifyDataSetChanged();
    }
}

