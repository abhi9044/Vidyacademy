package com.holamed.meddapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OTPReceiveActivity extends AppCompatActivity {
    ImageView back;
    ImageView home;
    Button buttonDone;
    Button bslogin;
    Button btlogin;
    String gotoClass;
    EditText editText;
    TextView textViewHeader;
    TextView textviewSubText;
    View relativeLayout;
    View progressBar;
    String phoneNumber;
    String otp;
    String nextActivity;
    SharedPreferences loginPrefs;
    SharedPreferences.Editor loginEditor;
    boolean firstClick = true;
    private PastOrdersDB dbpast;
    final String requestOTPurl = AppControllerSearchTests.serverUrl + "/api/newUsers/createOtp/?phone=";
    final String verifyOTPurl = AppControllerSearchTests.serverUrl + "/api/newUsers/verifyOtp/?phone=";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("shreyDebug", "oncreate OTPReceiveActivity");
        setContentView(R.layout.activity_otpverify);
        nextActivity = getIntent().getExtras().getString("goto");

        //setup actionbar
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbar_otp, null);
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

        //actionbar button click listeners
        home = (ImageView) findViewById(R.id.homeOTP);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OTPReceiveActivity.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.backOTP);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClick) {
                    if (nextActivity.equals("SecondActivityMain")) {
                        startActivity(new Intent(OTPReceiveActivity.this, SecondActivityMain.class));
                        Log.d("shreyDebug", "starting intent SecondActivityMain" + nextActivity);
                    } else {
                        finish();
                        Log.d("shreyDebug", nextActivity);
                    }
                } else {
                    textViewHeader.setText("Enter phone number for OTP verification");
                    editText.setText(phoneNumber);
                    editText.setHint("Enter Number Here");
                    firstClick = true;
                }
            }
        });
bslogin=(Button)findViewById(R.id.bslogin);
        bslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OTPReceiveActivity.this,Slogin.class);
                startActivity(i);
            }
        });
btlogin=(Button)(findViewById(R.id.bapplytutor));

        dbpast = new PastOrdersDB(this.getApplicationContext());
        buttonDone = (Button) findViewById(R.id.bSubmittutor);
        editText = (EditText) findViewById(R.id.phoneEditText);
         relativeLayout = findViewById(R.id.relativeLayoutOTP);
        progressBar = findViewById(R.id.progressBarOTP);

        loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        loginEditor=loginPrefs.edit();
btlogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i =new Intent(getApplicationContext(),TutorLogin.class);
        startActivity(i);

    }
});
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstClick) {
                    Log.d("shreyDebug", "first click");
                    phoneNumber = editText.getText().toString();
                    if (phoneNumber.matches("^\\d{10}$")) {

                        Log.d("shreyDebug", "phoneNumber=" + phoneNumber);
                        relativeLayout.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        createOTP();
                    } else {
                        editText.setError("Please enter a 10 digit number");
                    }
                } else {
                    Log.d("shreyDebug", "second click");
                    otp = editText.getText().toString();
                    if (otp.matches("^\\d{5}$")) {
                        //TODO:add button to change number
                        relativeLayout.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        verifyOTP();
                    } else {
                        editText.setError("Please enter a 5 digit OTP");
                    }
                }

            }
        });

    }

    private void createOTP() {
        Log.d("shreyDebug", "Inside createOTP");
        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.GET,
                requestOTPurl + phoneNumber, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("shreyDebug", "inside try");
                    String status = response.getString("status");
                    if (status != null && status.equals("true")) {
                        Log.d("shreyDebug", "createOTP status true:" + response.getInt("data"));
                        textViewHeader.setText("Please enter the One Time Password received via SMS on your mobile number " + phoneNumber);
                        textviewSubText.setVisibility(View.GONE);
                        editText.setHint("Enter the OTP");
                        editText.setText("");
                        firstClick = false;
                    } else {
                        Log.d("shreyDebug", "verify OTP status: " + status);
                        throw new JSONException("Status: " + status);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("shreyDebug", "inside catch");
                    Toast.makeText(getApplicationContext(),
                            "Network Error \n Retry Later",
                            Toast.LENGTH_LONG).show();
                    firstClick = true;
                    Log.d("shreyDebug", "JSONException error: " + e.getMessage());
                } finally {
                    relativeLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                relativeLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("shreyDebug", "inside onErrorResponse");
                Toast.makeText(getApplicationContext(),
                        "Network Error \n Retry Later", Toast.LENGTH_SHORT).show();
                firstClick = true;
                Log.d("shreyDebug", "OnErrorResponse error: " + error.getMessage());

            }
        });
        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq1);
    }

    private void verifyOTP() {
        Log.d("shreyDebug","inside verifyOTP");
        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(Request.Method.GET,
                verifyOTPurl + phoneNumber + "&oneTimePassword=" + otp, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("shreyDebug", "inside onResponse");
                    String status = response.getString("status");
                    if (status != null && status.equals("true")) {
                        Log.d("shreyDebug", "verify OTP status true");
                        Object data = response.get("data");
                        Log.d("shreyDebug", "this is a " + data.getClass().getName());
                        if (data instanceof JSONArray) {
                            JSONObject jsonData = response.getJSONArray("data").getJSONObject(0);
                            loginEditor.putString("UserId", jsonData.getString("_id"));
                            loginEditor.commit();

                        }
                        Log.d("shreyDebug", "user id is " + response.getJSONArray("data").getJSONObject(0).getString("_id"));
                        loginEditor.putBoolean("LoggedIn", true);
                        loginEditor.putString("PhoneNumber", phoneNumber);

                        loginEditor.commit();

                        final String PREFS_NAME = "MyPrefsFile";

                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                        if (settings.getBoolean("my_first_time", true)) {
                            //the app is being launched for first time, do something
                            Log.d("Comments", "First time");
                            String userid = loginPrefs.getString("UserId", "null");
                            Log.d("check userid", userid);
                            String phone = loginPrefs.getString("phone", "null");
                            dbpast.open();
                            ArrayList<PastOrdersObject> pastorderslist = dbpast.getAll();
                            if (pastorderslist.size() > 0)
                                AppControllerSearchTests.performTransfer(pastorderslist, userid, phone);
                            settings.edit().putBoolean("my_first_time", false).commit();
                        }

                        Toast.makeText(OTPReceiveActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        try {
                            Bundle bundle = getIntent().getExtras();
                            gotoClass = bundle.getString("goto");

                        } catch (Exception e) {
                            Toast.makeText(OTPReceiveActivity.this, "Error! Try Again", Toast.LENGTH_LONG).show();

                        }
                        if (gotoClass.equals("Registration")) {
                            Intent i = new Intent(OTPReceiveActivity.this, PastPatients.class);
                            i.putExtra("goto", "Registration");
                            startActivity(i);

                        } else if (gotoClass.equals("RegistrationHealth")) {
                            Intent i = new Intent(OTPReceiveActivity.this, PastPatients.class);
                            i.putExtra("goto", "RegistrationHealth");
                            startActivity(i);


                        } else if (gotoClass.equals("RegistrationEvents")) {
                            Intent i = new Intent(OTPReceiveActivity.this, PastPatients.class);
                            i.putExtra("goto", "RegistrationEvents");
                            startActivity(i);

                        } else if (nextActivity.equals("SecondActivityMain")) {
                            startActivity(new Intent(OTPReceiveActivity.this, SecondActivityMain.class));
                            Log.d("shreyDebug", "starting intent SecondActivityMain" + nextActivity);
                        }
                        finish();

                        /*if(nextActivity.equals("SecondMainActivity")){
                            Toast.makeText(OTPReceiveActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(OTPReceiveActivity.this,SecondActivityMain.class);
                            startActivity(intent);
                        }else if(nextActivity.equals("Registration")){
                            Toast.makeText(OTPReceiveActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                            *//*Intent intent=new Intent(OTPReceiveActivity.this,Registration.class);
                            startActivity(intent);*//*
                        }else if(nextActivity.equals("RegistrationHealth")){
                            Toast.makeText(OTPReceiveActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(OTPReceiveActivity.this,RegistrationHealth.class);
                            startActivity(intent);
                        }*/
                    } else {
                        Toast.makeText(OTPReceiveActivity.this, "Incorrect OTP Login Unsuccessfull", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OTPReceiveActivity.this, SecondActivityMain.class);
                        startActivity(intent);
                    }
                    relativeLayout.setVisibility(View.VISIBLE);
                    ((View) editText).setVisibility(View.INVISIBLE);
                    ((View) buttonDone).setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    relativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),
                            "Network Error \n Retry Later", Toast.LENGTH_SHORT).show();
                    Log.d("shreyDebug", "JSONException error: " + e.getMessage());
                } finally {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Network Error \n Retry Later", Toast.LENGTH_SHORT).show();
                Log.d("shreyDebug", "OnErrorResponse error: " + error.getMessage());
                relativeLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        // Adding request to request queue
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq2);
    }
}

