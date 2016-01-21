package com.holamed.meddapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.holamed.meddapp.Dialog_testItem;
import com.holamed.meddapp.R;
import com.holamed.meddapp.SearchTests;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Era on 4/30/2015.
 */
public class TestsListAdapter extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private static ArrayList<Dialog_testItem> item_dialog;
    private ArrayList<Integer> autofill;
    private CustomAutoCompleteAdapter autocomplete_adapt;
    private SearchTests obj;
    //public ImageLoader imageLoader;

    public TestsListAdapter(Activity a, ArrayList<HashMap<String, String>> d,ArrayList<Dialog_testItem> t,CustomAutoCompleteAdapter autocomplete_adapt) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.autocomplete_adapt=autocomplete_adapt;
        item_dialog=t;
        obj=new SearchTests();
        Log.d("getfecthedlist", String.valueOf(t.get(0)));
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.test_list_row, null);
        TextView name = (TextView)vi.findViewById(R.id.test_name_clicked); // title
        ImageView img=(ImageView)vi.findViewById(R.id.remove_test);

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                  Integer index = (Integer) v.getTag();
                //items.remove(index.intValue());
                 obj.stringdeletedintestlist(data.get(position).get(SearchTests.TEST_SELECTED),autocomplete_adapt);
        //        autocomplete_adapt.removeFromClickedString(data.get(position).get(SearchTests.TEST_SELECTED));
                data.remove(position);
                item_dialog.remove(position);
               int rem=removedpos(position);
                notifyDataSetChanged();
            }
        });
        System.out.println("in adapt");
       Log.d("inadap","YES");
       // ImageView thumb_image=(ImageView)vi.findViewById(R.id.test_remove_image); // thumb image

        HashMap<String, String> tests = new HashMap<String, String>();
        tests = data.get(position);

        // Setting all values in listview

        name.setText(tests.get(SearchTests.TEST_SELECTED));
        System.out.println("check name in dapt"+name.getText().toString());
        Log.d("inadapchecktextVname", name.getText().toString());
     //   thumb_image.setImageResource(R.drawable.account_image);
        //imageLoader.DisplayImage(account.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
    public ArrayList<Dialog_testItem> getDialogItemList()
    {
        return item_dialog;
    }
    public void clearData() {
        // clear the data
        Log.d("clear data adapter","yes");
        data.clear();

    }
    public void customremove(int position)
    {
        data.remove(position);
        notifyDataSetChanged();
    }
   public int removedpos(int position)
   {
       return position;
   }
    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    // A class that queries a web API, parses the data and returns an ArrayList<Style>
                    /*StyleFetcher fetcher = new StyleFetcher();
                    try {
                        mData = fetcher.retrieveResults(constraint.toString());
                    }
                    catch(Exception e) {
                        Log.e("myException", e.getMessage());
                    }*/
                    // Now assign the values and count to the FilterResults object
                    filterResults.values = data;
                    filterResults.count =data.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

}

