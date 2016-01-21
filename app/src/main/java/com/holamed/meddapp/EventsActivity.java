package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.games.event.Events;
import com.holamed.meddapp.adapter.DealsArrayAdapter;
import com.holamed.meddapp.adapter.EventsArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class EventsActivity extends AppCompatActivity {
    public SharedPreferences.Editor loginEditor;
    public SharedPreferences loginPrefs;
    private String city;
    ArrayList<String> a = new ArrayList<String>();
    public static ProgressDialog pDialog;
    CharSequence[] citysel = null;
    static private ArrayList<EventsItem> eventsList;
    Button cityselect;
    static private ListView listView;
    private ImageView back;
    private ImageView home;
    DatabaseHandler db2;
    static Dialog alertDialog;


    static private EventsArrayAdapter adapter;
    public static TextView noResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbardropdown2, null);

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

        setContentView(R.layout.activity_events);
        //TextView title_name= (TextView) customActionBarView.findViewById(R.id.custom_title);
        db2 = new DatabaseHandler(this);
        cityselect = (Button) customActionBarView.findViewById(R.id.bcityselect);
        final List<Contact> contacts = db2.getAllContacts();
        for (Contact cn : contacts) {

            String log = cn.getName();
            // Writing Contacts to log
            a.add(log);
            a.indexOf(log);
            Log.d("Name: ", "" + a.indexOf(log));
        }
        citysel = a.toArray(new CharSequence[a.size()]);


        loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        city = loginPrefs.getString("citySelected", "Indore");
        cityselect.setText(city);
        cityselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city = loginPrefs.getString("citySelected", "Indore");
                int pos = a.indexOf(city);
                Log.d("positionwa", "" + pos);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(EventsActivity.this)
                        .setTitle("Select your city")
                        .setSingleChoiceItems(citysel, pos, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub

                                loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                                loginEditor = loginPrefs.edit();
                                loginEditor.putString("citySelected", citysel[which].toString());
                                loginEditor.commit();

                                city = loginPrefs.getString("citySelected", "Indore");
                                cityselect.setText(city);
                                Toast.makeText(EventsActivity.this,
                                        city + " selected", Toast.LENGTH_LONG).show();
                                fetchEvents(city);

//dismissing the dialog when the user makes a selection.
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertdialog2 = builder2.create();
                alertdialog2.show();
            }


        });


        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventsActivity.this, SecondActivityMain.class);
                startActivity(i);
                //finish();
            }
        });

        noResultView = (TextView) findViewById(R.id.no_result);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        alertDialog = new Dialog(EventsActivity.this);

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

        Button two = (Button) alertDialog.findViewById(R.id.try_again);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        fetchEvents(city);


/*
        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               */
/* DealsItem tmp=AppControllerSearchTests.dealsfecthed.get(position);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
                AppControllerSearchTests.setHomecollection(tmp.getDetails().isHomecollection());
                AppControllerSearchTests.setSelectedLab(tmp.getDetails());
*//*
//CHANGE THIS FOR EVENTS
                EventsItem tmp = AppControllerSearchTests.eventsfecthed.get(position);
                AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPEEVENT);
                AppControllerSearchTests.setSelectedEvent(tmp);
                Intent intent = new Intent(EventsActivity.this, Registration.class);
                startActivity(intent);


            }
        });
*/
    }

    void fetchEvents(String city) {

        AppControllerSearchTests.fetchEvents(city);

        eventsList = new ArrayList<EventsItem>();
        listView = (ListView) findViewById(R.id.eventsListview);
        adapter = new EventsArrayAdapter(this, R.layout.eventrow, eventsList);
        listView.setAdapter(adapter);
    }


    static public void loadView() {

        eventsList = AppControllerSearchTests.eventsfecthed;
        Log.d("Dcheck length", String.valueOf(eventsList.size()));
        adapter.clear();
        adapter.addAll(eventsList);
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

    public static void showAlertDialog() {

        alertDialog.show();
    }

    public void onBackPressed() {
        Log.d("Back", "Pressed");
        Intent i = new Intent(EventsActivity.this, SecondActivityMain.class);
        startActivity(i);
        finish();
        super.onBackPressed();

    }
}