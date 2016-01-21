package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HealthCheckUp_Date_Select extends AppCompatActivity {
    TextView tvmode1, tvavailable;
    Switch tvmode2;
    TextView tvmode3, tvmode4;
    Spinner spinnerlab;
    TextView tvlabadd;
    Boolean homecol;
    Boolean labvi;
    EditText bdate;
    Button bproceeddate;
    java.util.List<String> categorieslabs;
    java.util.List<String> categoriesids;
    java.util.List<String> categoriesaddress;
    java.util.List<String> categorieslat;
    java.util.List<String> categorieslong;

    private RadioGroup radioGroup;
    private RadioButton radioButton;


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker = new DatePickerDialog((AppCompatActivity)getActivity(), this, year, month, day);
            Date today = new Date();
            Calendar d = Calendar.getInstance();
            d.setTime(today);
            d.add(Calendar.DAY_OF_MONTH, +6);
            long maxDate = d.getTime().getTime();
            Calendar e = Calendar.getInstance();
            e.setTime(today);
            e.add(Calendar.DAY_OF_MONTH, 0);

            long minDate = c.getTime().getTime();// Twice!
            mDatePicker.getDatePicker().setMaxDate(maxDate);
            mDatePicker.getDatePicker().setMinDate(minDate);
            // Create a new instance of DatePickerDialog and return it
            return mDatePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Toast.makeText(getActivity(), "Selected Date: " + day + "-" + (month + 1) + "-" + year, Toast.LENGTH_SHORT).show();
            String date = day + "-" + (month + 1) + "-" + year;
            EditText bdate = (EditText) ((AppCompatActivity)getActivity()).findViewById(R.id.bdate);
            bdate.setText(date);
            AppControllerSearchTests.setDateHealth(date);
        }
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        //one time rating shared pref
        //personDB=new PersonDB(this);
        homecol = AppControllerSearchTests.getHomecollHealth();
        labvi = AppControllerSearchTests.getLabVisit();
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("Health Packages");
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

        setContentView(R.layout.activity_health_date_pick);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        ImageView home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HealthCheckUp_Date_Select.this, SecondActivityMain.class);
                startActivity(i);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HealthCheckUp_Date_Select.this, HealthCheckUpNew.class);
                startActivity(i);
            }
        });


        bproceeddate = (Button) findViewById(R.id.bproceeddate);
        bproceeddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bdate.getText().length() > 0) {
                    Intent i = new Intent(HealthCheckUp_Date_Select.this, PastPatients.class);
                    i.putExtra("goto","RegistrationHealth");
                    startActivity(i);
                } else {
                    Toast.makeText(HealthCheckUp_Date_Select.this, "Please select a date", Toast.LENGTH_SHORT).show();

                }

            }
        });
        bproceeddate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    bproceeddate.setBackgroundColor(getResources().getColor(R.color.primary));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    bproceeddate.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });

        tvmode1 = (TextView) findViewById(R.id.tvmode1);
        tvlabadd = (TextView) findViewById(R.id.tvlabadd);
        bdate = (EditText) findViewById(R.id.bdate);
        tvavailable = (TextView) findViewById(R.id.tvavailable);
        spinnerlab = (Spinner) findViewById(R.id.spinnerlab);
        spinnerlab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                tvlabadd.setText("Lab Address:" + categoriesaddress.get(i));
                AppControllerSearchTests.setAddresslab(categoriesaddress.get(i));
                AppControllerSearchTests.setLabsname(categorieslabs.get(i));
                AppControllerSearchTests.setLabIds(categoriesids.get(i));
                AppControllerSearchTests.setHealthLati(categorieslat.get(i));
                AppControllerSearchTests.setHealthLongi(categorieslong.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    if (checkedRadioButton.getText().equals("Home Collection")) {
                        AppControllerSearchTests.setHomecollHealth(true);
                        tvavailable.setVisibility(View.GONE);
                        spinnerlab.setVisibility(View.GONE);
                        tvlabadd.setVisibility(View.GONE);
                    } else {
                        AppControllerSearchTests.setHomecollHealth(false);

                        tvavailable.setVisibility(View.VISIBLE);
                        spinnerlab.setVisibility(View.VISIBLE);
                        tvlabadd.setVisibility(View.VISIBLE);
                    }
                }


            }
        });

        List<String> listlab = new ArrayList<String>();
        List<String> listid = new ArrayList<String>();
        List<String> listaddress = new ArrayList<String>();
        List<String> latitude = new ArrayList<String>();
        List<String> longitude = new ArrayList<String>();

        String labsnames = AppControllerSearchTests.getLabsname();
        String labsids = AppControllerSearchTests.getLabsIds();
        String addresslab = AppControllerSearchTests.getAddresslab();
        String lati = AppControllerSearchTests.getHealthLati();
        String longi = AppControllerSearchTests.getHealthLongi();
        categorieslabs = Arrays.asList(labsnames.split("~"));
        categoriesids = Arrays.asList(labsids.split("~"));
        categoriesaddress = Arrays.asList(addresslab.split("~"));
        categorieslat = Arrays.asList(lati.split("~"));
        categorieslong = Arrays.asList(longi.split("~"));
        for (int i = 0; i < categorieslabs.size(); i++) {
            listlab.add(categorieslabs.get(i));
            listid.add(categoriesids.get(i));
            listaddress.add(categoriesaddress.get(i));
        }
        tvlabadd.setText("Lab Address:" + categoriesaddress.get(0));
        AppControllerSearchTests.setAddresslab(categoriesaddress.get(0));
        AppControllerSearchTests.setLabsname(categorieslabs.get(0));
        AppControllerSearchTests.setLabIds(categoriesids.get(0));
        AppControllerSearchTests.setHealthLati(categorieslat.get(0));
        AppControllerSearchTests.setHealthLongi(categorieslong.get(0));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listlab);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerlab.setAdapter(dataAdapter);
        spinnerlab.setSelection(0);


        if ((labvi) && (homecol)) {
            tvavailable.setVisibility(View.GONE);
            spinnerlab.setVisibility(View.GONE);
            tvlabadd.setVisibility(View.GONE);
            Toast.makeText(HealthCheckUp_Date_Select.this, "Home Collection and Lab Visit both facilities are available", Toast.LENGTH_LONG).show();
        } else if (labvi) {
            tvmode1.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            Toast.makeText(HealthCheckUp_Date_Select.this, "Only Lab Visit facility is available", Toast.LENGTH_LONG).show();

        } else {
            tvavailable.setVisibility(View.GONE);
            spinnerlab.setVisibility(View.GONE);
            tvmode1.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            tvlabadd.setVisibility(View.GONE);
            Toast.makeText(HealthCheckUp_Date_Select.this, "Only Home Collection facility is available", Toast.LENGTH_LONG).show();

        }
  /*
        tvmode2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    Toast.makeText(HealthCheckUp_Date_Select.this, "Lab Visit Selected", Toast.LENGTH_SHORT).show();
                    tvavailable.setVisibility(View.VISIBLE);
                    spinnerlab.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(HealthCheckUp_Date_Select.this, "Home Collection Selected", Toast.LENGTH_SHORT).show();
                    tvavailable.setVisibility(View.GONE);
                    spinnerlab.setVisibility(View.GONE);

                }

            }
        });

*/
    }


    /*
        public void onBackPressed() {
            Intent i = new Intent(HealthCheckUp_Date_Select.this, SecondActivityMain.class);
            startActivity(i);
            finish();
            super.onBackPressed();
        }
    */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


}