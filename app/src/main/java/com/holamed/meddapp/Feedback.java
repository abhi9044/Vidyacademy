package com.holamed.meddapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Feedback extends AppCompatActivity {

    private ImageView back;
    private ImageView home;
    TextView sugg;
    public SharedPreferences loginPrefs;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
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

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("Help us improve");
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Feedback.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Feedback.this, SecondActivityMain.class);
                startActivity(i);
                // finish();
            }
        });

        loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        email = loginPrefs.getString("email", null);
        if (email != null) {
        email = loginPrefs.getString("email", "null");

        } else {
            Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");

            for (Account account : accounts) {
                if (account.name != null) {
                    email = account.name;
                    break;
                }
            }
        }
        Button send = (Button) findViewById(R.id.bsfeed);
        sugg = (TextView) findViewById(R.id.etfeedback);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] params = {email, sugg.getText().toString()};

                new FetchTestsTask().execute(params);

            }
        });


    }

    private class FetchTestsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... userID) {

            InputStream inputStream = null;
            String result = "";
            try {

                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost("http://api.medd.in/api/feedbacks/create");


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("user", userID[0]));
                nameValuePairs.add(new BasicNameValuePair("feedback", userID[1]));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpclient.execute(httpPost);


            } catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String user) {
            Log.d("fetch tests completed", "yes");
            super.onPostExecute(user);
            Toast.makeText(Feedback.this, "Thank you for your valuable feedback!!", Toast.LENGTH_LONG).show();
            Intent in = new Intent(Feedback.this, SecondActivityMain.class);
            startActivity(in);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
    }


}
