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

public class TutorLogin extends AppCompatActivity {
    ImageView back;
    ImageView home;
    Button bsubmittutor;
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
        setContentView(R.layout.activity_tutor_login);

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
                Intent i = new Intent(TutorLogin.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.backOTP);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        finish();
            }
        });

bsubmittutor=(Button)findViewById(R.id.bSubmittutor);
        bsubmittutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent (TutorLogin.this,TutorProfile.class);
                startActivity(i);
            }
        });
    }
}

