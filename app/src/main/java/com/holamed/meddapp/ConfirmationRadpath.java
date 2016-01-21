package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfirmationRadpath extends AppCompatActivity {
    private String couponCode;
    private String couponCoderad;

    private String uid;
SharedPreferences loginprefs;
    private MixpanelAPI mixpanel;
    private String projectToken = "39504217e737bb956ceb8c20ca0a34ee";
    private String patientID, patientName, patientAge, patientEmail, patientPhone, patientGender, patientAddress, userID, userMail;
    private String patientIDrad,testsIDrad,timeStamprad,labIDrad,labNamerad,labAddrad,labPhonerad,testNamerad,labEmailrad,userIdrad;
    String mrpSinglerad;
    String meddSinglerad;
    String useringlerad;

    private String testsID;
    private String timeStamp;
    private String labID;
    private String labName;
    private String labAdd;
    private String labPhone;
    private String testName;
    private String labEmail;
    private String pharmacy_name;
    private String pharmacy_address;
    private Double priceMrp, priceMedd, priceUser;
    private Double priceMrprad, priceMeddrad, priceUserrad;

    private Double amount;
    private Double amountrad;
    private String tests;
    private String testsrad;
    private static int MY_SOCKET_TIMEOUT_MS_T = 50000;
    private String eventName;
    private String eventAddress;
    private String eventPrice;
    private String eventDescription;
    public SharedPreferences Rated;
    public SharedPreferences.Editor RatedEditor;
    String mrpSingle;
    String meddSingle;
    String useringle;

    private String bookeddate;
    private Boolean status = Boolean.FALSE;
    private Boolean statusrad = Boolean.FALSE;
   /* private PastOrdersObject a;
    private PastOrdersObject brad;
    private PastOrdersDB db;
   */
   private Button moretests;
    private ImageView back;
    private ImageView home;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    java.util.List<String> testsNamemulti;
    java.util.List<String> testsIdmulti;
    java.util.List<String> testsNamemultirad;
    java.util.List<String> testsIdmultirad;
    java.util.List<String> multimrp;  java.util.List<String> multimedd;  java.util.List<String> multiuser;
    java.util.List<String> multimrprad;  java.util.List<String> multimeddrad;  java.util.List<String> multiuserrad;

    private LinearLayout hintbox;
    private String PROGRESSDIALOGMESSAGE1 = "Registering..";
    private String PROGRESSDIALOGMESSAGE2 = "Generating Code..";
    private boolean homeCollection = false;
    private ResultItem selectedLab;
    private ResultItem selectedLabrad;
    //private Pharmacydetails selectedPhar;
    private ImageButton direction;
    private JSONObject patient;
    private JSONObject patientrad;
    private JSONObject patientFull;

    private JSONObject patientFullrad;
    //private PersonDB personDB;
    private EventsItem event;

    static ProgressDialog pDialog;
    private String refferalcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        //one time rating shared pref
        Rated = this.getSharedPreferences(
                "rated_medd", Context.MODE_PRIVATE);
        RatedEditor = Rated.edit();
        //personDB=new PersonDB(this);
        uid = AppControllerSearchTests.getUid();
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("CONFIRMATION");
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

        setContentView(R.layout.activity_confirmation_radpath);

        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmationRadpath.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmationRadpath.this, SecondActivityMain.class);
                startActivity(i);

            }
        });

        loginprefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        showPdialog();
        hintbox = (LinearLayout) findViewById(R.id.hintbox);
        pref = getSharedPreferences("confirmation", MODE_PRIVATE);
        editor = pref.edit();
        final String getStatus = pref.getString("confirm", "nil");
        Log.d("getstatuscheck", getStatus);
        if (getStatus.equals("true"))
            hintbox.setVisibility(View.GONE);

        else {
            hintbox.setVisibility(View.VISIBLE);
            editor.putString("confirm", "true");
            editor.commit();
        }
        patientName = AppControllerSearchTests.getPatientName();
        patientAge = AppControllerSearchTests.getPatientAge();
        patientEmail = AppControllerSearchTests.getPatientEmail();
        patientPhone = AppControllerSearchTests.getPatientPhone();
        patientGender = AppControllerSearchTests.getPatientGender();
        patientID=AppControllerSearchTests.getPatientId();
        if(AppControllerSearchTests.getPatientAddressline1()!=null) {
            patientAddress = AppControllerSearchTests.getPatientAddressline1();
            if (AppControllerSearchTests.getPatientAddressline2() != null)
                patientAddress = patientAddress + "  " + AppControllerSearchTests.getPatientAddressline2();

            if (AppControllerSearchTests.getPatientAddresspincode() != null)
                patientAddress = patientAddress + "  " + AppControllerSearchTests.getPatientAddresspincode();

        }
        else
        {
            patientAddress="";
        }
        event = AppControllerSearchTests.getSelectedEvent();
        refferalcode = AppControllerSearchTests.getRefferalcode();


        if (AppControllerSearchTests.getSearchType().equals(AppControllerSearchTests.TYPEEVENT)) {
            eventName = event.getEventName();
            eventAddress = event.getEventAddress();
            eventPrice = event.getEventPrice();
            eventDescription = event.getEventDescription();
            amount = Double.valueOf(eventPrice);
        }
        ///
        //try{
        if (AppControllerSearchTests.getSearchType().equals(AppControllerSearchTests.TYPELAB)) {
            selectedLab = AppControllerSearchTests.getSelectedLabPatho();
           if(selectedLab!=null) {
               labName = selectedLab.getLabName();
               labAdd = selectedLab.getLabAdd();
               labID = selectedLab.getLabID();
               labEmail = selectedLab.getLabEmail();
               priceUser = (Double) selectedLab.getPriceUser();
               priceMedd = (Double) selectedLab.getPriceMedd();
               priceMrp = (Double) selectedLab.getPriceMrp();
               meddSingle = selectedLab.getPriceMeddSingle();
               mrpSingle = selectedLab.getPriceMrpSingle();
               useringle = selectedLab.getPriceUserSingle();
               labPhone = selectedLab.getLabPhone();
               testsID = selectedLab.getSelectedTestID();
               testName = selectedLab.getSelectedTestName();
               Log.d("testName", testName);
               Log.d("testName", testsID);
               tests = selectedLab.getSelectedTestName();
               testsNamemulti = Arrays.asList(testName.split("~"));
               testsIdmulti = Arrays.asList(testsID.split("~"));
               multimedd = Arrays.asList(meddSingle.split("~"));
               multimrp = Arrays.asList(mrpSingle.split("~"));
               multiuser = Arrays.asList(useringle.split("~"));
           }
            selectedLabrad = AppControllerSearchTests.getSelectedLabRadio();

            if(selectedLabrad!=null) {
                labNamerad = selectedLabrad.getLabName();
                labAddrad = selectedLabrad.getLabAdd();
                labIDrad = selectedLabrad.getLabID();
                meddSinglerad = selectedLabrad.getPriceMeddSingle();
                mrpSinglerad = selectedLabrad.getPriceMrpSingle();
                useringlerad = selectedLabrad.getPriceUserSingle();
                labEmailrad = selectedLabrad.getLabEmail();
                priceUserrad = (Double) selectedLabrad.getPriceUser();
                priceMeddrad = (Double) selectedLabrad.getPriceMedd();
                priceMrprad = (Double) selectedLabrad.getPriceMrp();
                labPhonerad = selectedLabrad.getLabPhone();
                testsIDrad = selectedLabrad.getSelectedTestID();
                testNamerad = selectedLabrad.getSelectedTestName();

                testsrad = selectedLabrad.getSelectedTestName();
                testsNamemultirad = Arrays.asList(testNamerad.split("~"));
                testsIdmultirad = Arrays.asList(testsIDrad.split("~"));
                multimeddrad = Arrays.asList(meddSinglerad.split("~"));
                multimrprad = Arrays.asList(mrpSinglerad.split("~"));
                multiuserrad = Arrays.asList(useringlerad.split("~"));

            }
        }
      /*      selectedPhar=AppControllerSearchTests.getSelectedPharamcy();
            if(selectedPhar!=null) {
                pharmacy_name = selectedPhar.getPharmacyname();
                pharmacy_address = selectedPhar.getPharmacyaddress();
            }
        }catch (Exception e){
            selectedPhar=AppControllerSearchTests.getSelectedPharamcy();
            if(selectedPhar!=null) {
                pharmacy_name = selectedPhar.getPharmacyname();
                pharmacy_address = selectedPhar.getPharmacyaddress();
            }
            e.printStackTrace();
        }*/


        moretests = (Button) findViewById(R.id.moretest);
        final Button moretestspressed = (Button) findViewById(R.id.moretest);
        //on touch listener to change color of button
        boolean check = false;
        moretests.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    moretestspressed.setBackgroundColor(getResources().getColor(R.color.primary));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    moretestspressed.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });

        moretests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moretests.setBackgroundColor(getResources().getColor(R.color.onclickcolor));
                Intent i = new Intent(ConfirmationRadpath.this, SecondActivityMain.class);
                startActivity(i);
                finish();
            }
        });
        if(selectedLab!=null)
        dbadd();
        if(selectedLabrad!=null)
        dbaddrad();
    }
    public void dbadd()
    {
       /* db = new PastOrdersDB(this);
        a = new PastOrdersObject();
       */ Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        timeStamp = sdf2.format(date);

        /*a.setPatientName(patientName);
        a.setAmount(this.getString(R.string.Rs) + priceUser);
        a.setTests(tests);
        a.setDateString(sdf.format(date));
        */Log.d("checklabplz", AppControllerSearchTests.getSearchType());
        if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPELAB) {
          /*  Log.d("checklabplz", "yes");
            a.setAddress(labAdd);
            a.setCenter(labName);

            a.setChoice(AppControllerSearchTests.getSearchType());
          */  bookeddate = sdf.format(date);

            try {
                getCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPEEVENT) {
            Log.d("typeEvent", "ineleseif");
            patient = new JSONObject();
            String urlJsonObj = AppControllerSearchTests.serverUrl + "/api/v1/transactions/create";
            HashMap<String, String> jsonParams = new HashMap<>();
            JSONObject eventobj = new JSONObject();
            try {

                eventobj.put("_id", event.getEventId());
                eventobj.put("name", event.getEventName()); //extra
                eventobj.put("description", event.getEventDescription());
                eventobj.put("address", event.getEventAddress());

                patient.put("name", patientName);
                // patient.put("age", patientAge); //extra
                patient.put("email", patientEmail);
                patient.put("phone", patientPhone);
                if(patientID!=null)
                    patient.put("_id",patientID);
                // patient.put("gender", patientGender);//extra
                Log.d("typeEvent", patient.toString());
                Log.d("typeEvent", eventobj.toString());
                JSONObject transaction = new JSONObject();
                transaction.put("patient", patient);
                transaction.put("event", eventobj);
                Log.d("typeEvent", transaction.toString());
                jsonParams.put("transaction", transaction.toString());
                Log.d("typeEvent jsonParams", String.valueOf(jsonParams));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    urlJsonObj, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {


                    try {

                        Log.d("typeEvent response", response.toString());
                        couponCode = response.getString("coupon");


                        showViews();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //showAlertDialog();
                    }
                    hidePdialog();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Check", "Error: " + error.getMessage());
                    Log.d("volleyError", error.getMessage());
                    hidePdialog();
                    showAlertDialog();
                }
            });
            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
            //a.setChoice(AppControllerSearchTests.getSearchType());
            bookeddate = sdf.format(date);

        }


    }
    public void dbaddrad()
    {
      /*  db = new PastOrdersDB(this);
        brad = new PastOrdersObject();
      */  Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        timeStamp = sdf2.format(date);

        /*brad.setPatientName(patientName);
        brad.setAmount(this.getString(R.string.Rs) + priceUserrad);
        brad.setTests(testsrad);
        brad.setDateString(sdf.format(date));
        */Log.d("checklabplz", AppControllerSearchTests.getSearchType());
        if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPELAB) {
            Log.d("checklabplz", "yes");
          /*  brad.setAddress(labAddrad);
            brad.setCenter(labNamerad);

            brad.setChoice(AppControllerSearchTests.getSearchType());
          */  bookeddate = sdf.format(date);

            try {
                getCoderad();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPEEVENT) {
            Log.d("typeEvent", "ineleseif");
            patient = new JSONObject();
            String urlJsonObj = AppControllerSearchTests.serverUrl + "/api/v1/transactions/create";
            HashMap<String, String> jsonParams = new HashMap<>();
            JSONObject eventobj = new JSONObject();
            try {

                eventobj.put("_id", event.getEventId());
                eventobj.put("name", event.getEventName()); //extra
                eventobj.put("description", event.getEventDescription());
                eventobj.put("address", event.getEventAddress());

                patient.put("name", patientName);
                // patient.put("age", patientAge); //extra
                patient.put("email", patientEmail);
                patient.put("phone", patientPhone);
                if(patientID!=null)
                    patient.put("_id",patientID);
                // patient.put("gender", patientGender);//extra
                Log.d("typeEvent", patient.toString());
                Log.d("typeEvent", eventobj.toString());
                JSONObject transaction = new JSONObject();
                transaction.put("patient", patient);
                transaction.put("event", eventobj);
                Log.d("typeEvent", transaction.toString());
                jsonParams.put("transaction", transaction.toString());
                Log.d("typeEvent jsonParams", String.valueOf(jsonParams));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    urlJsonObj, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {


                    try {

                        Log.d("typeEvent response", response.toString());
                        couponCode = response.getString("coupon");


                        showViewsrad();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //showAlertDialog();
                    }
                    hidePdialog();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Check", "Error: " + error.getMessage());
                    Log.d("volleyError", error.getMessage());
                    hidePdialog();
                    showAlertDialog();
                }
            });
            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
           // a.setChoice(AppControllerSearchTests.getSearchType());
            bookeddate = sdf.format(date);

        }


    }





    private void showPdialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(PROGRESSDIALOGMESSAGE1);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirmation, menu);
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

   /* private void RegisterPersonRad() throws JSONException {
        patientIDrad = "0";


        patientrad = new JSONObject();
        patientrad.put("name", patientName);
        patientrad.put("age", patientAge);
        patientrad.put("email", patientEmail);
        patientrad.put("phone", patientPhone);
        patientrad.put("gender", patientGender);


        //create new order to get new patient id

        String urlJsonObjrad = AppControllerSearchTests.serverUrl + "/api/patients/create";

        String pararad;
        if (AppControllerSearchTests.getPatientAddressline1().length() > 0) homeCollection = true;
        patientAddress = AppControllerSearchTests.getPatientAddressline1() + ", " + AppControllerSearchTests.getPatientAddressline2() + ", " + AppControllerSearchTests.getPatientAddresspincode();
        try {
            if (homeCollection) {
                patientrad.put("address", patientAddress);
                Log.d("address", "there");

            } else {
                patientrad.put("address", "");

            }
        } catch (NullPointerException e) {
            Log.d("address", "not there");
        }

        pararad = patientrad.toString();
        Log.d("confo user req", "Para String: " + pararad);

        Map<String, String> jsonParamsrad = new HashMap<String, String>();
        jsonParamsrad.put("patient", pararad);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                urlJsonObjrad, new JSONObject(jsonParamsrad), new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {

                try {

                    Log.d("Confirmation add user", "response:" + response.toString());
                    if (response.getBoolean("status")) {
                        statusrad = Boolean.TRUE;
                        statusrad = Boolean.TRUE;
                        JSONObject abc = response.getJSONObject("patient");
                        patientIDrad = abc.getString("_id");
                        Log.d("patientidcheck", patientIDrad);
                        // now request for code
                        getCoderad();
                        String rValue = Rated.getString("done", "no");


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Check", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidePdialog();
                showAlertDialog();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void RegisterPerson() throws JSONException {
        patientID = "0";


        patient = new JSONObject();
        patient.put("name", patientName);
        patient.put("age", patientAge);
        patient.put("email", patientEmail);
        patient.put("phone", patientPhone);
        patient.put("gender", patientGender);


        //create new order to get new patient id

        String urlJsonObj = AppControllerSearchTests.serverUrl + "/api/patients/create";

        String para;
        if (AppControllerSearchTests.getPatientAddressline1().length() > 0) homeCollection = true;
        patientAddress = AppControllerSearchTests.getPatientAddressline1() + ", " + AppControllerSearchTests.getPatientAddressline2() + ", " + AppControllerSearchTests.getPatientAddresspincode();
        try {
            if (homeCollection) {
                patient.put("address", patientAddress);
                Log.d("address", "there");

            } else {
                patient.put("address", "");

            }
        } catch (NullPointerException e) {
            Log.d("address", "not there");
        }

        para = patient.toString();
        Log.d("confo user req", "Para String: " + para);

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("patient", para);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                urlJsonObj, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {

                try {

                    Log.d("Confirmation add user", "response:" + response.toString());
                    if (response.getBoolean("status")) {
                        status = Boolean.TRUE;
                        JSONObject abc = response.getJSONObject("patient");
                        patientID = abc.getString("_id");
                        Log.d("patientidcheck", patientID);
                        // now request for code
                        getCode();
                        String rValue = Rated.getString("done", "no");


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Check", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidePdialog();
                showAlertDialog();
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }
*/
    public void showViewsrad(){
        Log.d("check", "updating views");
        LinearLayout radlay=(LinearLayout)findViewById(R.id.radlay);
        radlay.setVisibility(View.VISIBLE);
        radlay.setVisibility(View.VISIBLE);
        radlay.setVisibility(View.VISIBLE);
        radlay.setVisibility(View.VISIBLE);
        radlay.setVisibility(View.VISIBLE);
        radlay.setVisibility(View.VISIBLE);
        TextView couponViewrad=(TextView)findViewById(R.id.confirmation_coupon_code_path);
        couponViewrad.setText(couponCoderad);
        TextView patientNameViewrad=(TextView)findViewById(R.id.confirmation_patient_name_path);
        patientNameViewrad.setText(patientName);

        TextView bookingConfirmed=(TextView)findViewById(R.id.textView_couponCodeIspath);
        TextView pay_dis=(TextView)findViewById(R.id.textView_due_amount_path);
        TextView testsView=(TextView)findViewById(R.id.confirmation_test_names_path);
        TextView labAddView=(TextView)findViewById(R.id.confirmation_centre_address_path);
        TextView labNameView=(TextView)findViewById(R.id.confirmation_centre_name_path);
        TextView amountView=(TextView)findViewById(R.id.confirmation_due_amount_path);
        TextView bookingdate=(TextView)findViewById(R.id.book_date_path);
        direction=(ImageButton)findViewById(R.id.direction_conf_path);
        bookingdate.setText(bookeddate);
        testsView.setText(testsrad);
        LinearLayout shareLayout=(LinearLayout)findViewById(R.id.shareLayout);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.shareimage);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("*/*");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                        b, "Title", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.putExtra(Intent.EXTRA_TEXT, "Medd helps me book all my medical tests through my smartphone, and get my reports in one place!.Download Android app now and get great discounts: bit.ly/medd-app");
                startActivity(Intent.createChooser(share, "Select"));

            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriparse;
                try {

                    uriparse = "http://maps.google.com/maps?daddr=" + String.valueOf(selectedLabrad.getLatitude()) + "," + String.valueOf(selectedLabrad.getLongitude());

                } catch (NullPointerException e) {

                    e.printStackTrace();
                    try {
                        uriparse = "http://maps.google.com/maps?daddr=" + String.valueOf(selectedLabrad.getLabAdd());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }


                Log.d("parseuri", uriparse);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uriparse));
                startActivity(intent);
            }
        });
        TextView opt=(TextView)findViewById(R.id.textView_your_option_path);
        if(AppControllerSearchTests.getSearchType()==AppControllerSearchTests.TYPELAB)
        {  opt.setText("LAB");
            labAddView.setText(labAddrad);
            labNameView.setText(labNamerad);
            amountView.setText(this.getString(R.string.Rs)+amountrad);
        }
        else if(AppControllerSearchTests.getSearchType()==AppControllerSearchTests.TYPEEVENT)
        {   opt.setText("EVENT");
            LinearLayout testblock=(LinearLayout)findViewById(R.id.testblock);
            testblock.setVisibility(View.GONE);
            pay_dis.setText("DESCRIPTION");
            labAddView.setText(eventAddress);
            labNameView.setText(eventName);
            amountView.setText(eventDescription);
            direction.setVisibility(View.GONE);
            bookingConfirmed.setText("Registration Number");
            // amountView.setText(AppControllerSearchTests.getSelectedPharamcy().getDiscount());
        }
    }



    public void showViews() {
        Log.d("check", "updating views");
        LinearLayout pathlay=(LinearLayout)findViewById(R.id.pathlay);
       pathlay.setVisibility(View.VISIBLE);
        TextView couponView = (TextView) findViewById(R.id.confirmation_coupon_code);
        couponView.setText(couponCode);
        TextView patientNameView = (TextView) findViewById(R.id.confirmation_patient_name);
        patientNameView.setText(patientName);
        LinearLayout laylab=(LinearLayout)findViewById(R.id.layoutlabchargespatho);
        TextView labmode=(TextView)findViewById(R.id.labmodepatho);
        TextView labcharges=(TextView)findViewById(R.id.tvlabchargespatho);

        TextView bookingConfirmed = (TextView) findViewById(R.id.textView_couponCodeIs);
        TextView pay_dis = (TextView) findViewById(R.id.textView_due_amount);
        TextView testsView = (TextView) findViewById(R.id.confirmation_test_names);
        TextView labAddView = (TextView) findViewById(R.id.confirmation_centre_address);
        TextView labNameView = (TextView) findViewById(R.id.confirmation_centre_name);
        TextView amountView = (TextView) findViewById(R.id.confirmation_due_amount);
        TextView bookingdate = (TextView) findViewById(R.id.book_date);
        direction = (ImageButton) findViewById(R.id.direction_conf);
        bookingdate.setText(bookeddate);
        testsView.setText(tests);
        if(AppControllerSearchTests.getHomecollection())
        {
            laylab.setVisibility(View.VISIBLE);
            labmode.setText("Home Collection");
           labcharges.setText(getApplicationContext().getString(R.string.Rs)+AppControllerSearchTests.getPriceHome());

        }

        LinearLayout shareLayout = (LinearLayout) findViewById(R.id.shareLayout);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.shareimage);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("*/*");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                        b, "Title", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.putExtra(Intent.EXTRA_TEXT, "Medd helps me book all my medical tests through my smartphone, and get my reports in one place!.Download Android app now and get great discounts: bit.ly/medd-app");
                startActivity(Intent.createChooser(share, "Select"));

            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriparse;
                try {

                    uriparse = "http://maps.google.com/maps?daddr=" + String.valueOf(selectedLab.getLatitude()) + "," + String.valueOf(selectedLab.getLongitude());

                } catch (NullPointerException e) {

                    e.printStackTrace();
                    try {
                        uriparse = "http://maps.google.com/maps?daddr=" + String.valueOf(selectedLab.getLabAdd());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }


                Log.d("parseuri", uriparse);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uriparse));
                startActivity(intent);
            }
        });
        TextView opt = (TextView) findViewById(R.id.textView_your_option);
        if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPELAB) {
            opt.setText("LAB");
            labAddView.setText(labAdd);
            labNameView.setText(labName);
            amountView.setText(this.getString(R.string.Rs) + amount);
        } else if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPEEVENT) {
            opt.setText("EVENT");
            LinearLayout testblock = (LinearLayout) findViewById(R.id.testblock);
            testblock.setVisibility(View.GONE);
            pay_dis.setText("DESCRIPTION");
            labAddView.setText(eventAddress);
            labNameView.setText(eventName);
            amountView.setText(eventDescription);
            direction.setVisibility(View.GONE);
            bookingConfirmed.setText("Registration Number");
            // amountView.setText(AppControllerSearchTests.getSelectedPharamcy().getDiscount());
        }
    }

    void getCoderad() throws JSONException {

        //make transaction to get coupon code

       /* if (statusrad) {
       */     Log.d("check", "good going");

            setDialogText(PROGRESSDIALOGMESSAGE2);
            // initialising parameters
            userIdrad = loginprefs.getString("UserId","null");//for now
            userMail = patientEmail;//for now
            patientFullrad=null;
            patientFullrad = new JSONObject();
            if(patientID!=null)
            patientFullrad.put("_id", patientID);

            patientFullrad.put("name", patientName);
            patientFullrad.put("age", patientAge);
            patientFullrad.put("email", patientEmail);
            patientFullrad.put("phone", patientPhone);
            patientFullrad.put("gender", patientGender);

        if (AppControllerSearchTests.getPatientAddressline1()!=null && AppControllerSearchTests.getPatientAddressline1().length()>0)
            homeCollection = true;
        try {
            if (homeCollection) {
                patientFullrad.put("address", patientAddress);
                Log.d("address", "there");

            } else {
                patientFullrad.put("address", "");

            }
        }catch (NullPointerException e) {
            Log.d("address", "not there");
        }

            String urlJsonObjForTxn = AppControllerSearchTests.serverUrl + "/api/v1/transactions/create";
            String type = "diagnostics";
            Map<String, String> jsonParams2rad = new HashMap<String, String>();
            String para2rad;
            if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPEPHARMACY) {
                type = "pharmacy";
            /*    Pharmacydetails tmp=AppControllerSearchTests.getSelectedPharamcy();
                String PharName= tmp.getPharmacyname();
                String PharID=tmp.getPharmacyID();
                String PharPhone=tmp.getPhone();
                String PharAdd=tmp.getPharmacyaddress();
                Integer PharDisc=tmp.getDiscountVal();
                String PharEmail=tmp.getPharmacyemail();
            */
                JSONObject txn = new JSONObject();
                JSONArray tests = new JSONArray();
                JSONObject timeStmp = new JSONObject();
                timeStmp.put("booking", timeStamp);

                JSONObject txnJ = new JSONObject();
                txnJ.put("_id", "");
                txnJ.put("coupon", "");

                JSONObject pharJ = new JSONObject();
              /*  pharJ.put("_id",PharID);
                pharJ.put("name",PharName);
                pharJ.put("address",PharAdd);
                pharJ.put("phone",PharPhone);
                pharJ.put("discount",PharDisc);
                pharJ.put("email",PharEmail);
*/


                JSONObject email = new JSONObject();
                email.put("to", patientEmail);
                email.put("txn", txnJ);
                email.put("tests", tests);
                email.put("patient", patient);
                email.put("lab", null);
                email.put("pharmacy", pharJ);
                email.put("subjet", "");


                JSONObject homecollection = new JSONObject();
                homecollection.put("required", homeCollection);

                JSONObject pharmacy = new JSONObject();
  /*              pharmacy.put("_id",PharID);
                pharmacy.put("discount",PharDisc);
*/

                JSONObject source = new JSONObject();
                if (AppControllerSearchTests.locSet)
                    source.put("geolocation", String.valueOf(AppControllerSearchTests.getLatitude()) + "," + String.valueOf(AppControllerSearchTests.getLongitude()));
                else
                    source.put("geolocation", "");

                source.put("type", "android");
                source.put("_id", Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID));

                txn.put("type", "pharmacy");
                txn.put("source", source);
                txn.put("user", userIdrad);
                txn.put("patient", patientFull);
                if (refferalcode.equals("none"))
                    txn.put("affiliate", "");
                else
                    txn.put("affiliate", refferalcode);
                txn.put("timestamp", timeStmp);
                txn.put("diagnostics", null);
                txn.put("pharmacy", pharJ);
                txn.put("email", email);
                txn.put("app_version", 13);
                txn.put("user_id", userIdrad);


                if (homeCollection)
                    homecollection.put("address", patientAddress);

                else
                    homecollection.put("address", "");


                txn.put("home_service", homecollection);
                para2rad = txn.toString();
            } else {
                Log.d("settingprice", "hello");
                JSONObject txnrad = new JSONObject();
                JSONObject pricerad = new JSONObject();
                pricerad.put("mrp", priceMrprad);
                pricerad.put("medd", priceMeddrad);
                pricerad.put("user", priceUserrad);


                JSONArray testObjArrayrad = new JSONArray();

                for (int i = 0; i < testsNamemultirad.size(); i++) {
                    JSONObject testObjrad = null;
                    testObjrad=new JSONObject();
                    testObjrad.put("mrp", multimrprad.get(i));
                    testObjrad.put("medd", multimeddrad.get(i));
                    testObjrad.put("user", multiuserrad.get(i));
                    testObjrad.put("_id", testsIdmultirad.get(i));
                    testObjrad.put("name", testsNamemultirad.get(i));
                    testObjArrayrad.put(testObjrad);

                }


                JSONObject timeStmprad = new JSONObject();
                timeStmprad.put("booking", timeStamprad);

                JSONObject labJrad = new JSONObject();
                labJrad.put("_id", labIDrad);
                labJrad.put("name", labNamerad);
                labJrad.put("address", labAddrad);
                labJrad.put("phone", labPhonerad);

                JSONObject txnJrad = new JSONObject();
                txnJrad.put("_id", "");
                txnJrad.put("coupon", "");


                JSONObject homecollectionrad= new JSONObject();
                homecollectionrad.put("required", homeCollection);

                JSONObject diagrad = new JSONObject();
                diagrad.put("_id", labIDrad);
                diagrad.put("tests", testObjArrayrad);
                diagrad.put("price", pricerad);

                diagrad.put("name", labNamerad);
                diagrad.put("email", labEmailrad);
                diagrad.put("address", labAddrad);
                diagrad.put("phone", labPhonerad);
                JSONObject sourcerad = new JSONObject();
                if (AppControllerSearchTests.locSet)
                    sourcerad.put("geolocation", String.valueOf(AppControllerSearchTests.getLatitude()) + "," + String.valueOf(AppControllerSearchTests.getLongitude()));
                else
                    sourcerad.put("geolocation", "");

                sourcerad.put("type", "android");
                sourcerad.put("_id", Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID));

                txnrad.put("type", "diagnostics");
                txnrad.put("source", sourcerad);
                txnrad.put("user", userIdrad);
                txnrad.put("patient", patientFullrad);
                txnrad.put("timestamp", timeStmprad);
                txnrad.put("diagnostics", diagrad);
                txnrad.put("pharmacy", null);
                txnrad.put("email", null);
                txnrad.put("app_version", 13);
                txnrad.put("user_id", userIdrad);


                if (homeCollection)
                    homecollectionrad.put("address", patientAddress);
                else
                    homecollectionrad.put("address", "");


                txnrad.put("home_service", homecollectionrad);

                para2rad = txnrad.toString();
            }


            Log.d("Check para2", para2rad);

            jsonParams2rad.put("transaction", para2rad);
            JsonObjectRequest jsonObjReq2rad = new JsonObjectRequest(Request.Method.POST,
                    urlJsonObjForTxn, new JSONObject(jsonParams2rad), new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {


                    try {

                        Log.d("data fecthed", response.toString());

                        if (response.getBoolean("status")) {
                            Log.d("Confirmation coupon", "response:" + response.toString());
                            //JSONObject txn = response.getJSONObject("transaction");
                            couponCoderad = response.getString("coupon");
                            if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPELAB) {
                                amountrad = priceUserrad;
                                amountrad = priceUserrad;
                            }
                            showViewsrad();
                            couponCoderad = response.getString("coupon");
             /*               brad.setCoupon_code(couponCoderad);
                            brad.setPatient_id(patientIDrad);
             */               ResultItem alpharad = AppControllerSearchTests.getSelectedLabRadio();
                            String testidalpharad = alpharad.getSelectedTestID();
                            Log.d("alpha check", String.valueOf(patientIDrad));
                            Log.d("alpha check", testidalpharad);
               /*             brad.setTestgroup_id(testidalpharad);
                            db.open();
                            db.addOder(brad);
               */         }
                        //showViews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Error json", e.getMessage().toString());
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    hidePdialog();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Check", "Error: " + error.getMessage());

                    hidePdialog();
                    showAlertDialog();
                }
            });
            // Adding request to request queue
            jsonObjReq2rad.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS_T,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq2rad);

        /*} else {
            Log.d("check", "not going");
        }
*/    }

    void getCode() throws JSONException {

        //make transaction to get coupon code

        /*if (status) {
        */    Log.d("check", "good going");

            setDialogText(PROGRESSDIALOGMESSAGE2);
            // initialising parameters
            userID = loginprefs.getString("UserId","null");//for now
            userMail = patientEmail;//for now

            patientFull = new JSONObject();
            if(patientID!=null)
            patientFull.put("_id", patientID);

            patientFull.put("name", patientName);
            patientFull.put("age", patientAge);
            patientFull.put("email", patientEmail);
            patientFull.put("phone", patientPhone);
            patientFull.put("gender", patientGender);

        if (AppControllerSearchTests.getPatientAddressline1()!=null && AppControllerSearchTests.getPatientAddressline1().length()>0)
            homeCollection = true;
        try {
            if (homeCollection) {
                patientFull.put("address", patientAddress);
                Log.d("address", "there");

            } else {
                patientFull.put("address", "");

            }
        }catch (NullPointerException e) {
            Log.d("address", "not there");
        }

            String urlJsonObjForTxn = AppControllerSearchTests.serverUrl + "/api/v1/transactions/create";
            String type = "diagnostics";
            Map<String, String> jsonParams2 = new HashMap<String, String>();
            String para2;
            if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPEPHARMACY) {
                type = "pharmacy";
            /*    Pharmacydetails tmp=AppControllerSearchTests.getSelectedPharamcy();
                String PharName= tmp.getPharmacyname();
                String PharID=tmp.getPharmacyID();
                String PharPhone=tmp.getPhone();
                String PharAdd=tmp.getPharmacyaddress();
                Integer PharDisc=tmp.getDiscountVal();
                String PharEmail=tmp.getPharmacyemail();
            */
                JSONObject txn = new JSONObject();
                JSONArray tests = new JSONArray();
                JSONObject timeStmp = new JSONObject();
                timeStmp.put("booking", timeStamp);

                JSONObject txnJ = new JSONObject();
                txnJ.put("_id", "");
                txnJ.put("coupon", "");

                JSONObject pharJ = new JSONObject();
              /*  pharJ.put("_id",PharID);
                pharJ.put("name",PharName);
                pharJ.put("address",PharAdd);
                pharJ.put("phone",PharPhone);
                pharJ.put("discount",PharDisc);
                pharJ.put("email",PharEmail);
*/


                JSONObject email = new JSONObject();
                email.put("to", patientEmail);
                email.put("txn", txnJ);
                email.put("tests", tests);
                email.put("patient", patient);
                email.put("lab", null);
                email.put("pharmacy", pharJ);
                email.put("subjet", "");


                JSONObject homecollection = new JSONObject();
                homecollection.put("required", homeCollection);

                JSONObject pharmacy = new JSONObject();
  /*              pharmacy.put("_id",PharID);
                pharmacy.put("discount",PharDisc);
*/
                JSONObject source = new JSONObject();
                if (AppControllerSearchTests.locSet)
                    source.put("geolocation", String.valueOf(AppControllerSearchTests.getLatitude()) + "," + String.valueOf(AppControllerSearchTests.getLongitude()));
                else
                    source.put("geolocation", "");

                source.put("type", "android");
                source.put("_id", Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID));

                txn.put("type", "pharmacy");
                txn.put("source", source);
                txn.put("user", userID);
                txn.put("patient", patientFull);

                txn.put("timestamp", timeStmp);
                txn.put("diagnostics", null);
                txn.put("pharmacy", pharJ);
                txn.put("email", email);
                txn.put("app_version", 9);
                txn.put("user_id", userID);


                if (homeCollection)
                    homecollection.put("address", patientAddress);

                else
                    homecollection.put("address", "");


                txn.put("home_service", homecollection);
                para2 = txn.toString();
            } else {
                Log.d("settingprice", "hello");
                JSONObject txn = new JSONObject();
                JSONObject price = new JSONObject();
                price.put("mrp", priceMrp);
                price.put("medd", priceMedd);
                price.put("user", priceUser);


                JSONArray testObjArray = new JSONArray();

                for (int i = 0; i < testsNamemulti.size(); i++) {
                    JSONObject testObj = null;
                    testObj=new JSONObject();
                    testObj.put("mrp", multimrp.get(i));
                    testObj.put("medd", multimedd.get(i));
                    testObj.put("user", multiuser.get(i));
                    testObj.put("_id", testsIdmulti.get(i));
                    Log.d("testwa", testsIdmulti.get(i));
                    testObj.put("name", testsNamemulti.get(i));
                    testObjArray.put(testObj);

                }

                Log.d("testwa2",""+tests);


                JSONObject timeStmp = new JSONObject();
                timeStmp.put("booking", timeStamp);

                JSONObject labJ = new JSONObject();
                labJ.put("_id", labID);
                labJ.put("name", labName);
                labJ.put("address", labAdd);
                labJ.put("phone", labPhone);

                JSONObject txnJ = new JSONObject();
                txnJ.put("_id", "");
                txnJ.put("coupon", "");

                JSONObject homecollection = new JSONObject();
                homecollection.put("required", homeCollection);

                JSONObject diag = new JSONObject();
                diag.put("_id", labID);
                diag.put("tests", testObjArray);
                diag.put("price", price);
                diag.put("name", labName);
                diag.put("email", labEmail);
                diag.put("address", labAdd);
                diag.put("phone", labPhone);
                JSONObject source = new JSONObject();
                if (AppControllerSearchTests.locSet)
                    source.put("geolocation", String.valueOf(AppControllerSearchTests.getLatitude()) + "," + String.valueOf(AppControllerSearchTests.getLongitude()));
                else
                    source.put("geolocation", "");

                source.put("type", "android");
                source.put("_id", Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID));

                txn.put("type", "diagnostics");
                txn.put("source", source);
                txn.put("user", userID);
                txn.put("patient", patientFull);
                txn.put("timestamp", timeStmp);
                txn.put("diagnostics", diag);
                txn.put("pharmacy", null);
                txn.put("app_version", 10);
                txn.put("user_id", userID);


                if (homeCollection)
                    homecollection.put("address", patientAddress);
                else
                    homecollection.put("address", "");


                txn.put("home_service", homecollection);

                para2 = txn.toString();
            }


            Log.d("Check para2", para2);

            jsonParams2.put("transaction", para2);
            JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.POST,
                    urlJsonObjForTxn, new JSONObject(jsonParams2), new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {


                    try {

                        Log.d("data fecthed", response.toString());

                        if (response.getBoolean("status")) {
                            Log.d("Confirmation coupon", "response:" + response.toString());
                            //JSONObject txn = response.getJSONObject("transaction");
                            couponCode = response.getString("coupon");
                            if (AppControllerSearchTests.getSearchType() == AppControllerSearchTests.TYPELAB) {
                                amount = priceUser;
                                amount = priceUser;
                            }
                            showViews();
                            couponCode = response.getString("coupon");
             /*               a.setCoupon_code(couponCode);
                            a.setPatient_id(patientID);
             */               ResultItem alpha = AppControllerSearchTests.getSelectedLabPatho();
                            String testidalpha = alpha.getSelectedTestID();
                            Log.d("alpha check", patientID);
                            Log.d("alpha check", testidalpha);
               /*             a.setTestgroup_id(testidalpha);
                            db.open();
                            db.addOder(a);
               */         }
                        //showViews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Error json", e.getMessage().toString());
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    hidePdialog();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Check", "Error: " + error.getMessage());

                    hidePdialog();
                    showAlertDialog();
                }
            });
            // Adding request to request queue
            jsonObjReq2.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS_T,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq2);

        /*} else {
            Log.d("check", "not going");
        }*/
    }

    public void showAlertDialog() {

        AlertDialog.Builder alertDialog;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout2 = inflater2.inflate(R.layout.custom_alert_dialog, null);
        Button one = (Button) layout2.findViewById(R.id.back);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button two = (Button) layout2.findViewById(R.id.try_again);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        alertDialog = new AlertDialog.Builder(this)
                .setView(layout2).setCancelable(false);
        alertDialog.show();
    }

    private void hidePdialog() {
        if (pDialog.isShowing()) pDialog.hide();
        pDialog.dismiss();
    }

    private void setDialogText(String in) {
        if (pDialog.isShowing()) pDialog.setMessage(in);
    }

    public void onBackPressed() {
        Intent i = new Intent(ConfirmationRadpath.this, SecondActivityMain.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}