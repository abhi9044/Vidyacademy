package com.holamed.meddapp.adapter;

import org.apache.commons.lang3.text.WordUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holamed.meddapp.R;
import com.holamed.meddapp.TransactionObject;

import java.util.ArrayList;

/**
 * Created by Era on 7/15/2015.
 */
public class ehrAdapter extends ArrayAdapter<TransactionObject> {

    Context context;
    public ArrayList<TransactionObject> data;
    int layoutResourceId;
    // ResultItem  data[] = null;

    public ehrAdapter(Context context, int layoutResourceId, ArrayList<TransactionObject> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResultHolder();
            holder.patientname = (TextView) row.findViewById(R.id.personname_ehr);
            holder.test_name = (TextView) row.findViewById(R.id.test_name_ehr);
            holder.lab_name = (TextView) row.findViewById(R.id.lab_name_ehr);
            holder.date = (TextView) row.findViewById(R.id.ehr_date);
            holder.profileicon = (TextView) row.findViewById(R.id.profileIcon);
            holder.testIcon = (TextView) row.findViewById(R.id.testIcon);
            holder.labIcon = (TextView) row.findViewById(R.id.labIcon);
            holder.dateIcon = (TextView) row.findViewById(R.id.dateIcon);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
            holder.profileicon.setTypeface(font);
            holder.labIcon.setTypeface(font);
            holder.dateIcon.setTypeface(font);
            holder.testIcon.setTypeface(font);
            row.setTag(holder);
        } else {
            holder = (ResultHolder) row.getTag();
        }

        final TransactionObject tmp = data.get(position);

        String testname = tmp.getTest_groups().replace('#', '\n');
        holder.test_name.setText(testname);
        holder.lab_name.setText(tmp.getLab_name());
        holder.date.setText(tmp.getDate());
        holder.patientname.setText(WordUtils.capitalizeFully(tmp.getPatient_name()));

        return row;
    }

    static class ResultHolder {
        TextView test_name, lab_name, date, patientname, profileicon, testIcon, dateIcon, labIcon;
    }

    public void updateAdapter(ArrayList<TransactionObject> newlist) {
        data = newlist;
        this.notifyDataSetChanged();
    }
}


