package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.EventsItem;
import com.holamed.meddapp.PastPatients;
import com.holamed.meddapp.R;
import com.holamed.meddapp.Registration;
import com.holamed.meddapp.Registration_Events;

import java.util.ArrayList;

/**
 * Created by Era on 8/11/2015.
 */
public class EventsArrayAdapter extends ArrayAdapter<EventsItem> {
    Context context;
    public ArrayList<EventsItem> data;
    int layoutResourceId;
    // ResultItem  data[] = null;

    public EventsArrayAdapter(Context context, int layoutResourceId, ArrayList<EventsItem> data) {
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

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ResultHolder();
            holder.eventName = (TextView)row.findViewById(R.id.eventName);
            holder.eventDetails=(TextView)row.findViewById(R.id.event_details);
            holder.eventDescription= (TextView)row.findViewById(R.id.eventDescription);
            holder.book=(Button)row.findViewById(R.id.bookhealth);
            holder.eventAddress = (TextView)row.findViewById(R.id.eventAddress);
            holder.eventDate= (TextView)row.findViewById(R.id.eventDate);
            holder.eventPrice= (TextView)row.findViewById(R.id.eventPrice);
            row.setTag(holder);
        }
        else
        {
            holder = (ResultHolder)row.getTag();
        }
    /*    LinearLayout linearLayout=(LinearLayout)row.findViewById(R.id.healthbox);
        if((position+1)%2==0)
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.primary));
        else if((position+1)%3==0)
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.lightBlue));
        else
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
*/
        final EventsItem tmp = data.get(position);

        holder.eventName.setText(tmp.getEventName());

        holder.eventDescription.setText(tmp.getEventDescription());
        if(tmp.getEventPrice().equals("Free"))
            holder.eventPrice.setText(tmp.getEventPrice());
        else
            holder.eventPrice.setText(context.getString(R.string.Rs) +tmp.getEventPrice());
        holder.eventDate.setText(tmp.getEventDate());
        holder.eventAddress.setText(tmp.getEventAddress());
        final ResultHolder finalHolder = holder;
        final View finalRow = row;
        holder.eventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) finalRow.findViewById(R.id.eventDescription);

                if (tv.getVisibility() == View.VISIBLE) {
                    // Its visible
                    tv.setVisibility(View.GONE);
                    finalHolder.eventDetails.setText("+Click here for details");

                } else {
                    // Either gone or invisible
                    tv.setVisibility(View.VISIBLE);
                    finalHolder.eventDetails.setText("-Click here for details");


                }
            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) finalRow.findViewById(R.id.eventDescription);

                if (tv.getVisibility() == View.VISIBLE) {
                    // Its visible
                    tv.setVisibility(View.GONE);
                    finalHolder.eventDetails.setText("+Click here for details");

                } else {
                    // Either gone or invisible
                    tv.setVisibility(View.VISIBLE);
                    finalHolder.eventDetails.setText("-Click here for details");


                }

            }
        });
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,PastPatients.class);
                i.putExtra("goto","RegistrationEvents");
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPEEVENT);
                AppControllerSearchTests.setSelectedEvent(tmp);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        return row;
    }

    static class ResultHolder
    {
        TextView eventName,eventAddress,eventDate,eventDescription,eventPrice,eventDetails;
        Button book;
    }

    public void updateAdapter(ArrayList<EventsItem> newlist) {
        //data.clear();
        //data.addAll(newlist);
        data =newlist;
        this.notifyDataSetChanged();
    }
}


