package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holamed.meddapp.FAQFragment;
import com.holamed.meddapp.FaqObject;
import com.holamed.meddapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Era on 5/18/2015.
 */
public class FaqAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<FaqObject> data;
    private static LayoutInflater inflater = null;
    //public ImageLoader imageLoader;

    public FaqAdapter(Activity a, ArrayList<FaqObject> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.faq_row, null);

        TextView question = (TextView) vi.findViewById(R.id.question); // title
        TextView answer = (TextView) vi.findViewById(R.id.answer); // title


        FaqObject faq=new FaqObject();
        faq = data.get(position);
        question.setText(faq.getQuestion());
        answer.setText(faq.getAnswer());
        answer.setVisibility(View.GONE);
        return vi;
    }
}