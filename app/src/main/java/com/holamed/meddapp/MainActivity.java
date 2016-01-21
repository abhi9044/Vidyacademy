package com.holamed.meddapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.appsflyer.AppsFlyerLib;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class MainActivity extends ActionBarActivity {
    final static String URL = "http://api.medd.in/api/cities/getall";
    HttpClient client;
    DatabaseHandler db2;

    String city = null;
    //private MixpanelAPI mixpanel;
    ListView list;
    int v;
    Boolean updateResult = true;
    PackageInfo pInfo = null;
    String version;
    Context ye;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    AlertDialog alert;
    private String urlJsonObj = AppControllerSearchTests.serverUrl + "/api/testgroups/getall";
    //private ProgressDialog pDialog;
    TestsDB db;
    private SharedPreferences loginPrefs;
    private SharedPreferences.Editor loginEditor;
    public final static String SAVED_KEY = "saved";
    public String isSaved = "f";
    static Dialog alertDialog;
    private static int MY_SOCKET_TIMEOUT_MS_I = 50000;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        AppsFlyerLib.setAppsFlyerKey("[wwQySi8ELjzyuHgUswXdHo]");
        AppsFlyerLib.sendTracking(getApplicationContext());
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        db2 = new DatabaseHandler(MainActivity.this);
        db2.deleteContact();
        setContentView(R.layout.activity_main);
        client = new DefaultHttpClient();
        loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        loginEditor = loginPrefs.edit();
        if (isConnected()) {
            new Reada().execute("name");
        } else {
            db2.addContact(new Contact("Indore"));
            db2.addContact(new Contact("Mumbai"));
            db2.addContact(new Contact("Jaipur"));
        }

        ye = this;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        v = pInfo.versionCode;
        version = v + "";
        Log.d("okok", version);

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                AppControllerSearchTests.setCurrentEmail(possibleEmail);
                break;
            }
        }
        db = new TestsDB(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Update Medd");
        builder.setMessage("Update Medd from Google Play Store For Better Performance");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });

        Log.d("okok", "its fine till here");
        alert = builder.create();
        pref = getSharedPreferences("MeddLoginDetails", MODE_PRIVATE);
        editor = pref.edit();
        final String getStatus = pref.getString("doneonce", "nil");
        final boolean eraseOriginalLogin = pref.getBoolean("erased", false);
        if (!eraseOriginalLogin) {
            Log.d("erasing previous login", "yes");
            editor.clear();
            editor.putBoolean("erased", true);
            editor.commit();
        }
        Log.d("jumping in main", "yesy");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (!getStatus.equals("true")) {
            Log.d("firstrun", "intut");
            // analytics

            Log.d("fetchtestscalled", "yes");
            //new FetchTestsTask().execute("fetch");
            AppControllerSearchTests.saveAnalytics();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i;
                    if(loginPrefs.getBoolean("firstTime",true)){
                        loginEditor.putBoolean("firstTime",false);
                        loginEditor.commit();
                        i = new Intent(MainActivity.this, TutorialActivityMedd.class);
                        Log.d("shreyDebug","condition true");
                    }else{
                        i = new Intent(MainActivity.this, SecondActivityMain.class);
                        Log.d("shreyDebug","condition false ");
                    }
                    startActivity(i);
                }
            }, 2000);
        } else {

            if (pref.getInt("PrefVersion", 0) < AppControllerSearchTests.sharedPrefVersion)
                AppControllerSearchTests.saveAnalytics();
            else if (!pref.getBoolean("savedAnalytics", false))
                AppControllerSearchTests.saveAnalytics();
            else if (!AppControllerSearchTests.isAnalyticsSent()) {
                AppControllerSearchTests.sendAnalytics();
            } else AppControllerSearchTests.sendAnalyticsUpdate();

            checkUpdate();
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public class Reada extends AsyncTask<String, Integer, String> {

        public void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            StringBuilder url = new StringBuilder(URL);
            int j;

            HttpGet get = new HttpGet(url.toString());
            HttpResponse r = null;
            try {
                r = client.execute(get);

            } catch (IOException e) {
                e.printStackTrace();
            }
            int status = 0;
            try {
                status = r.getStatusLine().getStatusCode();
            } catch (Exception e) {
                e.printStackTrace();
                //TODO:dunno whats happening here but getting error from ehr detailed view - cannot display toast  inside "do in background"
                //Toast.makeText(MainActivity.this, "Please check your Internet Connection ", Toast.LENGTH_SHORT).show();
                finish();
            }

            if (status == 200) {
                HttpEntity e = r.getEntity();
                String data = null;
                try {
                    data = EntityUtils.toString(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    JSONObject main = new JSONObject(data);
                    JSONArray main2 = main.getJSONArray("data");
                    for (int i = 0; i < main2.length(); i++) {
                        JSONObject jk = main2.getJSONObject(i);
                        city = jk.getString("name");
                        String c = city.substring(0, 1).toUpperCase() + city.substring(1);
                        db2.addContact(new Contact(c));
                        Log.d("Added", c);


                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


            }

            return city;

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub


            super.onPostExecute(result);


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_tests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        //    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //  menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void checkUpdate() {

        updateResult = true;
        Log.d("checkforupdate", "yes");

        final Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String ss = (String) msg.obj;
                if (ss.equals("update")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Updade Medd");
                    builder.setMessage("Upade Medd from Google Play Store For Better Performance");
                    builder.setInverseBackgroundForced(true);
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            });

                    Log.d("okok", "its fine till here");

                    alert = builder.create();
                    alert.show();


                } else if (ss.equals("nothing")) {

                    Log.d("fetchtestscalled", "yes");
                    new FetchTestsTask().execute("fetch");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i = new Intent(MainActivity.this, SecondActivityMain.class);
                            startActivity(i);
                            finish();
                        }


                    }, 1000);
                }

            }

        };


        new Thread(new Runnable() {
            public void run() {


                Log.d("okok", "process started");
                String result = "";
                String go = "";
                HttpResponse response;
                InputStream isr = null;


                try {


                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(AppControllerSearchTests.serverUrl + "/api/versions/isandroidupdated?version=" + version));
                    response = client.execute(request);
                    Log.d("okok", response + "");

                    HttpEntity httpEntity = response.getEntity();

                    if (httpEntity != null) {


                        InputStream inputStream = httpEntity.getContent();

                        //Lecture du retour au format JSON
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder stringBuilder = new StringBuilder();

                        String ligneLue = bufferedReader.readLine();
                        while (ligneLue != null) {
                            stringBuilder.append(ligneLue + " \n");
                            ligneLue = bufferedReader.readLine();
                        }
                        bufferedReader.close();

                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        updateResult = jsonObject.getBoolean("data");

                        Log.i("okok", stringBuilder.toString());


//               int nombreDeResultatsTotal = jsonResultSet.getInt("nb");
//               Log.i(LOG_TAG, "Resultats retourne" + nombreDeResultatsTotal);

                    }// <-- end IF
                } catch (IOException e) {

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                //convert response to string

                //parse json data
                try {
                    String datapass = "";
                    String LogedInId = "";


                    Log.d("okok result", updateResult + "");
                    if (updateResult) {


                        Message msg = myHandler.obtainMessage();
                        msg.obj = "nothing";
                        myHandler.sendMessage(msg);


                    } else {
                        Log.d("okok alert", "alert");

                        Message msg = myHandler.obtainMessage();
                        msg.obj = "update";
                        myHandler.sendMessage(msg);

                    }


                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("log_tag", "Error Parsing Data " + e.toString());

                }

            }
        }).start();


    }



    /*@Override
    protected void onDestroy() {
        mixpanel.flush();
        super.onDestroy();
    }*/

    private class FetchTestsTask extends AsyncTask<String, Void, String> {


        @Override


        protected String doInBackground(String... userID) {
            Log.d("fetch tests task", "yes");
            InputStream isr = null;
            loginPrefs = getSharedPreferences("Test4LessData", Context.MODE_PRIVATE);
            loginEditor = loginPrefs.edit();

            if (loginPrefs.getString(SAVED_KEY, "f").equals("f")) {
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        urlJsonObj, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray tests = response.getJSONArray("data");
                            String jsonResponse = "check" + tests.length();
                            for (int i = 0; i < tests.length(); i++) {
                                JSONObject test = tests.getJSONObject(i);
                                String name = test.getString("name");
                                String id = test.getString("_id");
                                String type = test.getString("type");
                                int frequency = test.getInt("frequency");
                                JSONArray alias = test.getJSONArray("aliases");
                                String temp = name;
                                for (int z = 0; z < alias.length(); z++) {
                                    temp = temp + ", " + alias.getString(z);
                                    Log.d("GetString", alias.getString(z));

                                }
                                Log.d("Aliases", temp);

                                TestsTableSqlite new_test = new TestsTableSqlite();
                                new_test.setKey(id);
                                new_test.setName(name);
                                new_test.setType(type);
                                new_test.setfre(frequency);
                                new_test.setaliases(temp);
                                db.open();
                                db.addTests(new_test);
                                Log.d("FETCH", String.valueOf(tests.length()));
                                loginEditor.putString(SAVED_KEY, "t");
                                loginEditor.putInt("testsLength", tests.length());
                                loginEditor.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Check", "Error: " + error.getMessage());
                    }
                });
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS_I,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
            }
            return String.valueOf(userID);
        }

        @Override
        protected void onPostExecute(String user) {
            Log.d("fetch tests completed", "yes");
            super.onPostExecute(user);

        }
    }


}
