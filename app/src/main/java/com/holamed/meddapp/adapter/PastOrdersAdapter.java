package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.OrderedTestFragment;
import com.holamed.meddapp.PastOrdersObject;
import com.holamed.meddapp.R;

import java.util.ArrayList;

/**
 * Created by Era on 5/19/2015.
 */
public class PastOrdersAdapter extends ArrayAdapter<PastOrdersObject> {
    Context context;
    public ArrayList<PastOrdersObject> data;
    int layoutResourceId;

    public PastOrdersAdapter(Context context, int layoutResourceId, ArrayList<PastOrdersObject> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        final LinearLayout container = (LinearLayout) row;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ResultHolder();
            holder.call = (ImageButton) row.findViewById(R.id.ibcallpast);
            holder.centertext = (TextView) row.findViewById(R.id.textView_center);
            holder.patientname = (TextView) row.findViewById(R.id.confirmation_patient_name_r);
            holder.center_name = (TextView) row.findViewById(R.id.confirmation_centre_name_r);
            holder.center_address = (TextView) row.findViewById(R.id.confirmation_centre_address_r);
            holder.amount = (TextView) row.findViewById(R.id.amount_r);
            holder.tests = (TextView) row.findViewById(R.id.confirmation_test_names_r);
            holder.date = (TextView) row.findViewById(R.id.book_date_r);
            holder.coupon_code = (TextView) row.findViewById(R.id.confirmation_coupon_code_r);
            holder.ptl = (LinearLayout) row.findViewById(R.id.pasttestslayout);
            holder.am_ps = (TextView) row.findViewById(R.id.textView_due_amount);
            holder.opt = (TextView) row.findViewById(R.id.textView_your_option_r);
            holder.book = (Button) row.findViewById(R.id.bookhealth);


            row.setTag(holder);
        } else {
            holder = (ResultHolder) row.getTag();
        }

        final PastOrdersObject tmp = data.get(position);
        //TestResultItem tmp=data.get(position);
        holder.call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: 88793-99793"));
                        context.startActivity(callIntent);

            }
        });
        holder.patientname.setText(tmp.getPatientName());
        holder.center_name.setText(tmp.getCenter());
        holder.center_address.setText(tmp.getAddress());
        if (tmp.getChoice().equals(AppControllerSearchTests.TYPELAB)) {
            Log.d("checklabplz", "typelabyo");

            holder.amount.setText(String.valueOf(tmp.getAmount()) + "(at center)");
            holder.tests.setText(tmp.getTests());
            holder.opt.setText("LAB");
        } else {
            holder.opt.setText("EVENT");
            holder.book.setVisibility(View.GONE);
            holder.centertext.setVisibility(View.GONE)
            ;
            holder.center_name.setVisibility(View.GONE);
            holder.ptl.setVisibility(View.GONE);
            holder.am_ps.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
        }
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tmp.getChoice().equals(AppControllerSearchTests.TYPELAB))
                    OrderedTestFragment.bookClicked(tmp, getContext());

            }
        });
        holder.date.setText(String.valueOf(tmp.getDateString()));
        holder.coupon_code.setText(tmp.getCoupon_code());

        return row;
    }

    static class ResultHolder {
        TextView center_name, center_address, amount, tests, coupon_code, date, patientname, am_ps, opt, centertext;
        LinearLayout ptl;
        Button book;
        ImageButton call;
    }

    public void updateAdapter(ArrayList<PastOrdersObject> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data = newlist;
        this.notifyDataSetChanged();
    }
}

