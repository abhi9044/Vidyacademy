package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holamed.meddapp.R;
import com.holamed.meddapp.TestResultObject;

import java.util.ArrayList;

/**
 * Created by Era on 7/15/2015.
 */
public class ehrdetail_Adapter extends ArrayAdapter<TestResultObject>{

    Context context;
    public ArrayList<TestResultObject> data;
    int layoutResourceId;
    // ResultItem  data[] = null;

    public ehrdetail_Adapter(Context context, int layoutResourceId, ArrayList<TestResultObject> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultHolder holder = null;
        final RelativeLayout container = (RelativeLayout) row;


        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResultHolder();
            holder.test = (TextView)row.findViewById(R.id.ehr_test_name_list);
            holder.value= (TextView)row.findViewById(R.id.ehr_value_list);
            holder.range= (TextView)row.findViewById(R.id.ehr_reference_list);
            row.setTag(holder);
        }
        else
        {    //Log.d("here?dffjid", String.valueOf(row));

            holder = (ResultHolder)row.getTag();
        }

        final TestResultObject tmp = data.get(position);
        //Log.d("tmp", tmp.getDeal_name());

        holder.test.setText(tmp.getName());
        holder.value.setText(tmp.getValue());
        holder.range.setText(tmp.getRange());

        return row;
    }

    static class ResultHolder
    {
        TextView test,value,range;

    }

    public void updateAdapter(ArrayList<TestResultObject> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data =newlist;
        this.notifyDataSetChanged();
    }
}



