package com.holamed.meddapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.holamed.meddapp.adapter.FaqAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by prabhat on 7/8/2015.
 */
public class logoutFragment extends Fragment implements View.OnClickListener,Observer {

    boolean mSignInClicked;

    public logoutFragment(){}
    private ListView listView;
    private FaqAdapter faqadapter;
    Boolean val;
    Animation slide_down;
    private String platform;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    private GoogleConnection googleConnection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        LayoutInflater inflater1 = (LayoutInflater)((AppCompatActivity)getActivity()).getSupportActionBar()
                .getThemedContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater1.inflate(R.layout.actionbar_basic, null);
        AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
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

        TextView title_name= (TextView) customActionBarView.findViewById(R.id.fragmentName);
        title_name.setText("Logout");
        View rootView = inflater.inflate(R.layout.activity_logout_fragment, container, false);
        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        loginEditor = loginPrefs.edit();
        Log.d("google",loginPrefs.getString("googleClient","none"));

       /* googleConnection = GoogleConnection.getInstance(getActivity());
        googleConnection.addObserver(this);
       */
        /*platform=loginPrefs.getString("platform","none");
        if(platform.equals("facebook"))
        */
       /* else if(platform.equals("google"))
        {   Log.d("google","logging out");
               googleConnection.onSignOut();
        }
*/
        loginEditor.clear();

        Log.d("logout","2");
        loginEditor.commit();

        Intent i = new Intent(getActivity(),SecondActivityMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        getActivity().startActivity(i);


        //request handler for end of back ground process
      /*  final Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String ss = (String) msg.obj;
                if (ss.equals("right")) {
                    Log.d("logout","1");
                    loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                    loginEditor = loginPrefs.edit();
                    loginEditor.clear();
                    Log.d("logout","2");
                    loginEditor.commit();
                    Intent i = new Intent(getActivity(),LogIn.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    getActivity().finish();
                } else if (ss.equals("wrong")) {
                    Log.d("logout","3");
                    loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                    loginEditor = loginPrefs.edit();
                    Log.d("logout","4");
                    loginEditor.clear();
                    loginEditor.commit();
                    Log.d("logout","5");
                    Intent i = new Intent(getActivity(),LogIn.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    getActivity().finish();
                } else if (ss.equals("error")) {
                    Log.d("logout","6");
                    loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                    loginEditor = loginPrefs.edit();
                    Log.d("logout","7");
                    loginEditor.clear();
                    loginEditor.commit();
                    Log.d("logout","8");
                    Intent i = new Intent(getActivity(),LogIn.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    getActivity().finish();



                } else {

                    loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                    loginEditor = loginPrefs.edit();
                    Log.d("logout","9");
                    loginEditor.clear();
                    loginEditor.commit();
                    Log.d("logout","10");
                    Intent i = new Intent(getActivity(),LogIn.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    getActivity().finish();

                }

            }

        };


        new Thread(new Runnable() {
            public void run() {
                String result = "";
                InputStream isr = null;

                try {


                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(AppControllerSearchTests.serverUrl+"/api/users/logout");
                    Log.d("logout","11");
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("csrf", ""));
                    Log.d("logout","12");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    Log.d("logout","13");
                    HttpEntity entity = response.getEntity();
                    isr = entity.getContent();

                } catch (Exception e) {
                    Log.d("logout","14");
                }


                //convert response to string
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    isr.close();
                    result = sb.toString();
                    Log.d("result signup", result);

                } catch (Exception e) {

                }
                //parse json data
                try {

                    String LogedInId = "";
                    //JSONArray jArray = new JSONArray(result);
                    JSONObject j = new JSONObject(result);
                    val = j.getBoolean("status");
                    Log.d("logout","15");
                    Log.d("result1", val + "");



                            /*for(int i=0; i<jArray.length();i++){
                                JSONObject json = jArray.getJSONObject(i);




                            }*/

        /*            if (val == true) {
                        Message msg = myHandler.obtainMessage();
                        msg.obj = "right";
                        myHandler.sendMessage(msg);
                        Log.d("logout","16");

                    } else {

                        Message msg = myHandler.obtainMessage();
                        msg.obj = "right";
                        myHandler.sendMessage(msg);
                        Log.d("logout","17");

                    }


                } catch (Exception e) {
                    // TODO: handle exception

                    Message msg = myHandler.obtainMessage();
                    msg.obj = "error";
                    myHandler.sendMessage(msg);
                    Log.d("logout","18");
                }
            }
        }).start();*/
        return rootView;

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
