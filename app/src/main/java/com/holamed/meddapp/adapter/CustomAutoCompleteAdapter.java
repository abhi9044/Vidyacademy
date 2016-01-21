package com.holamed.meddapp.adapter;

/**
 * Created by Era on 5/15/2015.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holamed.meddapp.R;


public class CustomAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private Activity activity;
    private ArrayList<String> data;
    private ArrayList<Integer> clicked;
    private ArrayList<String> clickedString;
    private static LayoutInflater inflater=null;
    ArrayList<String> mData;
    ArrayList<String> mSuggestions;
    private TestFilter tfilter;
    private boolean isEnabledBoolean;

    public CustomAutoCompleteAdapter(Activity a, ArrayList<String> d) {
        activity = a;
        data=d;
        isEnabledBoolean=true;
        clicked=new ArrayList<Integer>();
        clickedString=new ArrayList<String>();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         mData = new ArrayList<>(data);
         mSuggestions = new ArrayList<>(d);

        // imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return mSuggestions.size();
    }

    public String getItem(int position) {
        return mSuggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.autocomplete_custom, null);

        TextView name = (TextView)vi.findViewById(R.id.fieldname); // title
        ImageView i=(ImageView)vi.findViewById(R.id.already_added);
        String a;
        try {
             a = mSuggestions.get(position);

        // Setting all values in listview
        name.setText(a);
        Log.d("checkA", a);

        name.setTextColor(Color.BLACK);
        i.setImageResource(R.drawable.greentick_24);
         if(clickedString.contains(mSuggestions.get(position)))
         {
             //i.setVisibility(View.VISIBLE);
              i.setImageResource(R.drawable.greentick_24);
             name.setTextColor(Color.GRAY);

         } else {
            // i.setVisibility(View.INVISIBLE);
             i.setImageResource(R.drawable.small_plus_blue_24);
             name.setTextColor(Color.BLACK);

         }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
     /*      if (clicked.contains(position)) {
            i.setVisibility(View.VISIBLE);
            name.setTextColor(Color.GRAY);

        } else {
            i.setVisibility(View.INVISIBLE);
            name.setTextColor(Color.BLACK);

        }*/
        // imageLoader.DisplayImage(account.get(CustomizedListView.KEY_THUMB_URL), thumb_image);

        return vi;
    }
  /*  public boolean areAllItemsEnabled()
    {
        return true;
    }*/
    /*public String ReturnText(int position)
    {
       return mSuggestions.get(position);
    }*/
    public void addToClickedString(String a)
    {
        clickedString.add(a);
    }
    public void resetClicked(ArrayList<Integer> resetList)
    {
        clicked=resetList;
    }
    public void removeAllClickedString()
    {
        clickedString=new ArrayList<String>();
    }
    public void removeAllClicked()
    {
        clicked=new ArrayList<Integer>();
    }
    public void addToClicked(int position)
    {
        clicked.add(position);
    }
    public void removeFromClicked(int position)
    {   Log.d("removefromclickedcalled","yes");
        clicked.remove(position);
    }
    public void removeFromClickedString(String r)
    {
        clickedString.remove(r);
    }
    public void setIsEnabledBoolean(boolean value)
    {
        isEnabledBoolean=value;
    }
/*    @Override
    public boolean isEnabled (int position)
    {    Log.d("inisenabledfor", String.valueOf(position));


          if (clickedString.contains(data.get(position))) {

              Log.d("value for position", String.valueOf(position) + String.valueOf(false));

              return false;
          } else {
              Log.d("value for position", String.valueOf(position) + String.valueOf(true));

              return true;
          }


    }
  */
    @Override
    public Filter getFilter() {
        if(tfilter==null)
             tfilter=new TestFilter();
        return tfilter;
    }
    private class TestFilter extends Filter {
        private ArrayList<String> newValues;
        FilterResults filterResults;
       /* @Override
        public String convertResultToString(Object resultValue) {
            return (resultValue.toString());
        }*/
       public void filtra(final CharSequence constraint) {

           activity.runOnUiThread(new Runnable() {
               public void run() {
                   publishResults(constraint, filterfunction(constraint));
               }});
       }
        @Override
       protected FilterResults performFiltering(CharSequence constraint) {
            return filterfunction(constraint);
        }
        private FilterResults filterfunction(CharSequence constraint)
        {filterResults=new FilterResults();

            if (constraint != null && constraint.toString().length() > 0) {

                mSuggestions.clear();

                //Check for similarities in data from constraint
                ArrayList<String> newValues=new ArrayList<>();
                for (String value : mData) {
                    if (value.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        newValues.add(value);
                    //    Toast.makeText(activity, value + " contains " + constraint.toString(), Toast.LENGTH_LONG).show();
                    } else {
                       // mSuggestions.remove(value);
                      //  Toast.makeText(activity, value + " does not " + constraint.toString(), Toast.LENGTH_LONG).show();

                    }
                }

                filterResults.values = newValues;
                filterResults.count = newValues.size();
                Log.d("checkfiltercount", String.valueOf(mSuggestions.size()));
                Log.d("constraint", String.valueOf(constraint));
             }
            else
            {   synchronized(newValues) {
                filterResults.values = mData;
                filterResults.count = mData.size();
            }
            }
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d("inpublish","yes");
            System.out.println("inpublish");
         /*   if (results != null && results.count > 0) {
                mSuggestions = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }*/

            if (results.count > 0) {
                mSuggestions = (ArrayList<String>) results.values;
                try {
                    notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } else {

                notifyDataSetInvalidated();
            }
          /* if(results.values!=null){
               mSuggestions = (ArrayList<String>) results.values;
            }
            else
           {
               mSuggestions=(ArrayList<String>)data;

           }*/
           // if (results.count > 0) {
              //  notifyDataSetChanged();

           /* }
            else {
                mSuggestions=(ArrayList<String>)mData;
                notifyDataSetChanged();
            }*/

        }
    }
    }
