package com.holamed.meddapp;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.holamed.meddapp.adapter.ExpandableTestGroupListAdapter;
import com.holamed.meddapp.adapter.ehrdetail_Adapter;

import java.util.ArrayList;


public class EHRDetails extends AppCompatActivity {
    private TextView labname, labIcon;
    private ImageView back;
    private ImageView home;
    //private TextView testname;
    private TextView patientname, patientIcon;
    private TextView date, dateIcon;
    private TextView testsIcon;
    private ExpandableListView testgroupExpandablelistView;
    private ExpandableTestGroupListAdapter expandableTestGroupListAdapter;
    private TextView downIcon;
    private int position;
    private int lastExpandedPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position=getIntent().getIntExtra("position",-1);

        //setup actionbar
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        Toolbar parent=(Toolbar) customActionBarView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        setContentView(R.layout.activity_ehrdetails);

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("Detailed History");
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EHRDetails.this, SecondActivityMain.class);
                startActivity(i);
            }
        });

        back = (ImageView) findViewById(R.id.back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<TransactionObject> transactionObjectArrayList = AppControllerSearchTests.transactionObjectArrayList;

        patientname = (TextView) findViewById(R.id.personname_ehr_d);
        labname = (TextView) findViewById(R.id.lab_name_ehr_d);
        date = (TextView) findViewById(R.id.ehr_date_d);

        labIcon=(TextView)findViewById(R.id.labIcon);
        testsIcon=(TextView)findViewById(R.id.testIcon);
        patientIcon=(TextView)findViewById(R.id.profileIcon);
        dateIcon=(TextView)findViewById(R.id.dateIcon);
        downIcon=(TextView)findViewById(R.id.downIcon);
        //testname=(TextView)findViewById(R.id.test_name_ehr_d);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");
        testsIcon.setTypeface(font);
        patientIcon.setTypeface(font);
        dateIcon.setTypeface(font);
        labIcon.setTypeface(font);
        downIcon.setTypeface(font);


        labname.setText(transactionObjectArrayList.get(position).getLab_name());
        //testname.setText(e.getTest_groups());
        date.setText(transactionObjectArrayList.get(position).getDate());
        patientname.setText(transactionObjectArrayList.get(position).getPatient_name());

        testgroupExpandablelistView = (ExpandableListView) findViewById(R.id.ehr_detail_list);
        if (transactionObjectArrayList.get(position).testGroupNameArrayList==null) {
            //TextView no=(TextView)findViewById(R.id.no_result_e);
            //no.setVisibility(View.VISIBLE);
            testgroupExpandablelistView.setVisibility(View.INVISIBLE);
        } else {
            expandableTestGroupListAdapter = new ExpandableTestGroupListAdapter(this,transactionObjectArrayList.get(position).getTestGroupNameArrayList(),transactionObjectArrayList.get(position).getHashMap() );
            testgroupExpandablelistView.setAdapter(expandableTestGroupListAdapter);
        }


        testgroupExpandablelistView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPosition!=-1&&lastExpandedPosition!=groupPosition){
                    testgroupExpandablelistView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition=groupPosition;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ehrdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
