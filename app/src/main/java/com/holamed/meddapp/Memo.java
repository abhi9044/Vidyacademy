package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Memo extends AppCompatActivity {
    private String couponCode;
    private String uid;
TextView radiolabname;
    LinearLayout homecollectionlayout;
    TextView pathomode;
    TextView pathocharge;
    TextView radiotests;
    LinearLayout pathlayout;
    LinearLayout radlayout;
    TextView patholabnae;
    TextView pathotests;
    TextView pathototal;
    Button booknow;
    TextView radiodiscount;
    TextView radiototal;
    TextView pathodiscount;
    TextView totaldis;
    TextView totalamt;
    ResultItem selectedLabPatho;
    ResultItem selectedLabRadio;
    private Double priceMrprad=0.0,priceMeddrad=0.0,priceUserrad=0.0, priceMrppat=0.0,priceMeddpat=0.0,priceUserpat=0.0;
    private Double amount;
    Double disrad=0.0;
    Double dispat=0.0;
    private String testsrad;
    private static int MY_SOCKET_TIMEOUT_MS_T=50000;
    private ImageView back;
    private ImageView home;
    private SharedPreferences pref;

    static ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        //one time rating shared pref
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("BILLING");
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

        setContentView(R.layout.memo);
booknow=(Button)findViewById(R.id.bbooknow);
        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Memo.this, PastPatients.class);
                i.putExtra("goto","Registration");
                startActivity(i);
            }
        });
        booknow.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    booknow.setBackgroundColor(getResources().getColor(R.color.primary));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    booknow.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });

        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Memo.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Memo.this, CategorySearchPatho.class);
                startActivity(i);

            }
        });


        homecollectionlayout=(LinearLayout)findViewById(R.id.laylabcharges);
        pathomode=(TextView)findViewById(R.id.pathomode);
        pathocharge=(TextView)findViewById(R.id.pathocharge);
        pathlayout= (LinearLayout) findViewById(R.id.patholayout);
        radlayout= (LinearLayout) findViewById(R.id.radiolayout);
        radiolabname=(TextView)findViewById(R.id.radiolabname);
        radiotests=(TextView)findViewById(R.id.radiotests);
        patholabnae=(TextView)findViewById(R.id.patholabname);
        pathotests=(TextView)findViewById(R.id.pathotests);
        pathototal=(TextView)findViewById(R.id.pathototal);
        radiodiscount=(TextView)findViewById(R.id.radiodis);
        radiototal=(TextView)findViewById(R.id.radioamt);
        pathodiscount=(TextView)findViewById(R.id.pathodiscount);
         totaldis=(TextView)findViewById(R.id.totaldis);
        ResultItem selectedLabPatho = AppControllerSearchTests.getSelectedLabPatho();
        ResultItem selectedLabRadio = AppControllerSearchTests.getSelectedLabRadio();


  if(selectedLabPatho==null)
      pathlayout.setVisibility(View.GONE);
        else {
if(AppControllerSearchTests.getHomecollection())
{
homecollectionlayout.setVisibility(View.VISIBLE);
    pathomode.setText("Home Collection");
    pathocharge.setText(getApplicationContext().getString(R.string.Rs) + AppControllerSearchTests.getPriceHome());

}
      String labNamepat = selectedLabPatho.getLabName();
      priceUserpat = (Double) selectedLabPatho.getPriceUser();
      priceMeddpat = (Double) selectedLabPatho.getPriceMedd();
      priceMrppat = (Double) selectedLabPatho.getPriceMrp();
      String testspat = selectedLabPatho.getSelectedTestName();
     dispat=priceMrppat-priceMeddpat;
      patholabnae.setText(labNamepat);
      pathotests.setText(testspat);
      pathototal.setText(getApplicationContext().getString(R.string.Rs)+priceMeddpat.toString());

      pathodiscount.setText(getApplicationContext().getString(R.string.Rs)+dispat.toString());

  }
        if(selectedLabRadio==null)
            radlayout.setVisibility(View.GONE);
        else {
            String labNamerad = selectedLabRadio.getLabName();
            priceUserrad = (Double) selectedLabRadio.getPriceUser();
            priceMeddrad = (Double) selectedLabRadio.getPriceMedd();
            priceMrprad = (Double) selectedLabRadio.getPriceMrp();
         Log.d("priceUserwa",priceUserrad.toString());
            Log.d("priceMeddwa",priceMeddrad.toString());
            Log.d("priceMrpwa",priceMrprad.toString());
            disrad=priceMrprad-priceUserrad;
            String testsrad = selectedLabRadio.getSelectedTestName();

            radiolabname.setText(labNamerad);
            radiotests.setText(testsrad);

            radiototal.setText(getApplicationContext().getString(R.string.Rs)+priceUserrad.toString());

            radiodiscount.setText(getApplicationContext().getString(R.string.Rs)+disrad.toString());

        }
        double totald=dispat+disrad;

        totaldis.setText("TOTAL DISCOUNT  : "+getApplicationContext().getString(R.string.Rs)+totald);
        Log.d("some discount", String.valueOf(totaldis.getText()));














    }

    public void onBackPressed(){
        Intent i = new Intent(Memo.this, SecondActivityMain.class);
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