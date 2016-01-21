package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.holamed.meddapp.adapter.DealsArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class HealthCheckUp extends AppCompatActivity {
    public static ProgressDialog pDialog;
    static private ArrayList<DealsItem> dealsList;
    static private ListView listView;
    private ImageView back;
    private ImageView home;
    static Dialog alertDialog;
    static private DealsArrayAdapter adapter;
    public static TextView noResultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        setContentView(R.layout.activity_health_check_up);
        TextView title_name= (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("HEALTH PACKAGES");
        home=(ImageView)findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HealthCheckUp.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back=(ImageView)findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent(Registration.this,Detailed_result.class);
                //startActivity(i);
                finish();
            }
        });



        noResultView=(TextView)findViewById(R.id.no_result);

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

            alertDialog = new Dialog(HealthCheckUp.this);

            //setting custom layout to dialog
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.custom_alert_dialog);

        Button one = (Button) alertDialog.findViewById(R.id.back);
        one.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                onBackPressed();
                alertDialog.dismiss();
            }
        });

        Button two=(Button)alertDialog.findViewById(R.id.try_again);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);


        AppControllerSearchTests.fetchDeals();

        dealsList= new ArrayList<DealsItem>();





        listView = (ListView) findViewById(R.id.deals_listview);
        adapter = new DealsArrayAdapter(this,R.layout.healthcheckuprow,dealsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPEDEAL);
                DealsItem tmp=AppControllerSearchTests.dealsfecthed.get(position);
                // AppControllerSearchTests.setSelectedDeal(tmp);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                // AppControllerSearchTests.setSelectedDeal(tmp);
                AppControllerSearchTests.setHomecollection(tmp.getDetails().isHomecollection());
                AppControllerSearchTests.setSelectedLab(tmp.getDetails());

                Intent intent = new Intent(HealthCheckUp.this, Registration.class);
                startActivity(intent);


            }
        });
    }


    static public void loadView(){

        dealsList=AppControllerSearchTests.dealsfecthed;
        Log.d("Dcheck length", String.valueOf(dealsList.size()));
        adapter.clear();
     adapter.addAll(dealsList);
        adapter.notifyDataSetChanged();
        Log.d("Dcheck length", String.valueOf(adapter.getCount()));
        listView.setAdapter(adapter);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        // testsListView.invalidate();
        // testsListView.invalidateViews();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_check_up, menu);
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
    public static void showAlertDialog()
    {

        alertDialog.show();
    }

    public void onBackPressed()
    {   Log.d("Back", "Pressed");
        Intent i=new Intent(HealthCheckUp.this,SecondActivityMain.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

}