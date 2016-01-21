package com.holamed.meddapp.adapter;

/**
 * Created by Abhishek on 9/27/2015.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.holamed.meddapp.EventsObject;
import com.holamed.meddapp.R;

public class SubEventAdapter extends BaseAdapter {
    View finalView;

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    public List<EventsObject> alialist = null;
    public ArrayList<EventsObject> arraylist;

    public SubEventAdapter(Context context, List<EventsObject> alialist) {
        mContext = context;
        this.alialist = alialist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<EventsObject>();
        this.arraylist.addAll(alialist);


    }

    public class ViewHolder {
        TextView name;
        TextView price;
        TextView id;
        CheckBox multi;
    }

    @Override
    public int getCount() {
        return alialist.size();
    }

    @Override
    public EventsObject getItem(int position) {
        return alialist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, final ViewGroup parent) {
        final ViewHolder holder;
        //  if (view == null) {
        holder = new ViewHolder();
        view = inflater.inflate(R.layout.list_sub_event, null);
        // Locate the TextViews in listview_item.xml
        holder.multi = (CheckBox) view.findViewById(R.id.checkBoxmulti);
        holder.name = (TextView) view.findViewById(R.id.patient_name);
        holder.price = (TextView) view.findViewById(R.id.subeventprice);
        holder.id = (TextView) view.findViewById(R.id.subevent_id);


        view.setTag(holder);
        holder.multi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view2) {

                CheckBox cb = (CheckBox) view2;
                EventsObject checkdetails = (EventsObject) cb.getTag();
                checkdetails.setSelected(cb.isChecked());
            }
        });
//if (checkdetails.isSelected())
//    ((SearchTests)mContext).notigy(cb);

        holder.multi.setChecked(alialist.get(position).isSelected());
        holder.name.setText(alialist.get(position).getName());
        holder.id.setText(alialist.get(position).getId());
        holder.price.setText(mContext.getString(R.string.Rs)+alialist.get(position).getPrice());
        holder.multi.setTag(alialist.get(position));
        // Listen for ListView Item Click


        return view;
    }


}