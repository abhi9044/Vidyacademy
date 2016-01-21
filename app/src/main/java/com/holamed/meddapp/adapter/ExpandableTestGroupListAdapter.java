package com.holamed.meddapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.holamed.meddapp.R;
import com.holamed.meddapp.TestGroupObject;
import com.holamed.meddapp.TestResultObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shreyans on 12/16/2015.
 */
public class ExpandableTestGroupListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<TestResultObject>> _listDataChild;

    public ExpandableTestGroupListAdapter(Context context, ArrayList<String> listDataHeader,
                                 HashMap<String, ArrayList<TestResultObject>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public TestResultObject getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon-1);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.testgroup_child_expandablelistview, null);
        }

        if(childPosition==0){
            TextView testName, testResult, testRange,infoIcon;
            testName = (TextView) convertView.findViewById(R.id.testName);
            testResult = (TextView) convertView.findViewById(R.id.testResult);
            testRange = (TextView) convertView.findViewById(R.id.testRange);
            infoIcon=(TextView)convertView.findViewById(R.id.infoIcon);
            infoIcon.setVisibility(View.GONE);

            testName.setText("Test Name");
            testName.setTypeface(null, Typeface.BOLD);
            testResult.setText("Result");
            testResult.setTypeface(null, Typeface.BOLD);
            testRange.setText("Range");
            testRange.setTypeface(null, Typeface.BOLD);
        }
        else {

            final TestResultObject testResultObject = getChild(groupPosition, childPosition);

            TextView testName, testResult, testRange,infoIcon;

            testName = (TextView) convertView.findViewById(R.id.testName);
            testResult = (TextView) convertView.findViewById(R.id.testResult);
            testRange = (TextView) convertView.findViewById(R.id.testRange);
            infoIcon=(TextView) convertView.findViewById(R.id.infoIcon);

            Typeface font=Typeface.createFromAsset(_context.getAssets(),"fontawesome-webfont.ttf");
            infoIcon.setTypeface(font);
            infoIcon.setVisibility(View.VISIBLE);

            testName.setText(testResultObject.getName()+"  ");
            testResult.setText(testResultObject.getRange());
            testRange.setText(testResultObject.getValue());
            testName.setTypeface(null, Typeface.NORMAL);
            testResult.setTypeface(null, Typeface.NORMAL);
            testRange.setTypeface(null, Typeface.NORMAL);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size()+1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.testgroup_parent_expandablelistview, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.testgrouptext);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
