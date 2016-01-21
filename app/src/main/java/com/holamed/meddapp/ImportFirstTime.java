package com.holamed.meddapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class ImportFirstTime extends AppCompatActivity {

    private String urlJsonObj =AppControllerSearchTests.serverUrl+"/api/testgroups/getall";
    //private ProgressDialog pDialog;
    TestsDB db ;
    private SharedPreferences loginPrefs;
    private SharedPreferences.Editor loginEditor;
    public final static String SAVED_KEY = "saved";
    public String isSaved = "f";
    static Dialog alertDialog;
    private static int MY_SOCKET_TIMEOUT_MS_I=50000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_first_time);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        db= new TestsDB(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray tests = response.getJSONArray("data");
                    String jsonResponse = "check" + tests.length();
                    for (int i = 0; i < tests.length(); i++) {
                        JSONObject test = tests.getJSONObject(i);
                        String name = test.getString("name");
                        String id=test.getString("_id");
                       String type=test.getString("type");
                        TestsTableSqlite new_test=new TestsTableSqlite();
                        new_test.setKey(id);
                        new_test.setName(name);
                        new_test.setType(type);
                        db.open();
                        db.addTests(new_test);

                        loginPrefs = getSharedPreferences("Test4LessData", Context.MODE_PRIVATE);
                        loginEditor = loginPrefs.edit();
                        loginEditor.putString(SAVED_KEY, "t");
                        loginEditor.commit();
                        Intent searchtest = new Intent(ImportFirstTime.this,SearchTests.class);
                        startActivity(searchtest);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Check", "Error: " + error.getMessage());
                showAlertDialog();
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS_I,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_import_first_time, menu);
        return true;
    }
  public void showAlertDialog() {
      alertDialog = new Dialog(ImportFirstTime.this);
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
              alertDialog.show();
  }
}

