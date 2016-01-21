package com.holamed.meddapp.adapter;

/**
 * Created by Era on 5/13/2015.
 */import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.holamed.meddapp.R;


public class ReviewsAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public ReviewsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.review_list_roww, null);

        TextView name = (TextView)vi.findViewById(R.id.reviewer_name);
        TextView date = (TextView)vi.findViewById(R.id.review_date);
        TextView review = (TextView)vi.findViewById(R.id.review);
        TextView rating=(TextView)vi.findViewById(R.id.rating_r);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.img_reviewer); // thumb image

        HashMap<String, String> account = new HashMap<String, String>();
        account = data.get(position);

        // Setting all values in listview

        name.setText("Mundi");
        date.setText("15 May");
        rating.setText("6");
        review.setText("I am awesome but the lab lemme think! Lab is super cool! medd rocks! Daredevils yoooohooooo");
        thumb_image.setImageResource(R.drawable.compact_camera_64);
        // imageLoader.DisplayImage(account.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}
