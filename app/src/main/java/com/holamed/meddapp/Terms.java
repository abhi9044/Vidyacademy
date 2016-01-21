package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;


public class Terms extends AppCompatActivity {
    final static String URL = "http://api.medd.in/api/others/getall";
private View mHeaderView;
    private View mToolbarView;
    HttpClient client;
    String code;
    ProgressDialog dialog;
    private ImageView home;
    private ImageView back;
    TextView tvtandc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms);

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
        title_name.setText("About Us");
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Terms.this, SecondActivityMain.class);
                startActivity(i);

            }
        });

        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Terms.this, SecondActivityMain.class);
                startActivity(i);
                // finish();
            }
        });


        client = new DefaultHttpClient();

        tvtandc=(TextView)findViewById(R.id.tvvtandc);
        if(!isConnected()) {
            showAlertDialog(Terms.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
        else {
            new Read().execute("name");
        }


    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public class Read extends AsyncTask<String, Integer, String> {

        public void onPreExecute() {



                dialog = ProgressDialog.show(Terms.this, "Loading", "", true);

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
            int status = r.getStatusLine().getStatusCode();
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
                    JSONArray main2=main.getJSONArray("data");
JSONObject jkkd=main2.getJSONObject(0);
                    JSONObject iop=jkkd.getJSONObject("tnc");
                   code=iop.getString("content");


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


            }

                return code;
          }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub


            super.onPostExecute(result);
tvtandc.setText(android.text.Html.fromHtml(result));


            tvtandc.setTextColor(Color.parseColor("#000000"));
            dialog.dismiss();


        }

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
}
