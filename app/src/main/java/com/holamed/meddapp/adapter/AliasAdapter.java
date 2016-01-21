package com.holamed.meddapp.adapter;

/**
 * Created by Abhishek on 9/27/2015.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.holamed.meddapp.Alias;
import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.CategorySearch;
import com.holamed.meddapp.R;
import com.holamed.meddapp.SearchTests;
import com.holamed.meddapp.SelectedTests;

import org.w3c.dom.Text;

public class AliasAdapter extends BaseAdapter {
    View finalView;

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    public List<Alias> alialist = null;
    public ArrayList<Alias> arraylist;

    public AliasAdapter(Context context, List<Alias> alialist) {
        mContext = context;
        this.alialist = alialist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Alias>();
        this.arraylist.addAll(alialist);


    }

    public class ViewHolder {
        TextView name;
        TextView aliases;
        TextView type;
        TextView id;
        CheckBox multi;
    }

    @Override
    public int getCount() {
        return alialist.size();
    }

    @Override
    public Alias getItem(int position) {
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
        view = inflater.inflate(R.layout.list_search_common, null);
        // Locate the TextViews in listview_item.xml
        holder.multi = (CheckBox) view.findViewById(R.id.checkBoxmulti);
        holder.name = (TextView) view.findViewById(R.id.product_name);
        holder.aliases = (TextView) view.findViewById(R.id.tvalias);
        holder.id = (TextView) view.findViewById(R.id.idalias);
        holder.type = (TextView) view.findViewById(R.id.typealias);

        view.setTag(holder);
        holder.multi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view2) {

                CheckBox cb = (CheckBox) view2;
                Alias checkdetails = (Alias) cb.getTag();
                checkdetails.setSelected(cb.isChecked());
//if (checkdetails.isSelected())
//    ((SearchTests)mContext).notigy(cb);
                ((SearchTests) mContext).UpdateCartValue(arraylist);

                StringBuffer responseText = new StringBuffer();
                responseText.append("Test added to cart");
                int z = 0;

                for (int i = 0; i < alialist.size(); i++) {
                    Alias country = alialist.get(i);
                    if (country.isSelected()) {
                        z++;
                        //           responseText.append("\n" + country.getName());
                    }
                }
                if (z > 0)
                    Toast.makeText(mContext,
                            responseText, Toast.LENGTH_SHORT).show();
            }
        });


        //} else {
        //  holder = (ViewHolder) view.getTag();
        //}
        // Set the results into TextViews
        holder.multi.setChecked(alialist.get(position).isSelected());
        holder.name.setText(alialist.get(position).getName());
        int len1 = alialist.get(position).getName().length();
        int len2 = alialist.get(position).getAlias().length();
        if (len1 != len2)
            holder.aliases.setText("AKA-" + alialist.get(position).getAlias().substring(len1 + 1));
        else
            holder.aliases.setText("");
        holder.id.setText(alialist.get(position).getId());
        holder.type.setText(alialist.get(position).getType());
        holder.multi.setTag(alialist.get(position));
        // Listen for ListView Item Click
        final View finalView1 = view;
        final View finalView2 = view;
        final View finalView3 = view;
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });
        if(holder.aliases.length()!=0){
            holder.aliases.setVisibility(View.VISIBLE);
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        alialist.clear();
        if (charText.length() == 0) {
            alialist.addAll(arraylist);
        } else {
            for (Alias wp : arraylist) {
                if (wp.getAlias().toLowerCase(Locale.getDefault()).contains(charText)) {
                    alialist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}