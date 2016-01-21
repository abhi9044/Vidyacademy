package com.holamed.meddapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.holamed.meddapp.adapter.BaseFragment;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public class LoginFragment extends Fragment  {
    CallbackManager callbackManager;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    private String name;
    private String email;
    private String gender;
    private String userId;
    private MixpanelAPI mixpanel;
    private String projectToken = "39504217e737bb956ceb8c20ca0a34ee";
    private String PROGRESSDIALOGMESSAGE1 = "Registring..";
    private GoogleApiClient googleApiClient;
    static ProgressDialog pDialog;

    private JSONObject patient;
    private ImageView back;
    private ImageView home;
    private static final int RC_SIGN_IN = 0;
    /* private GoogleApiClient mGoogleApiClient;
     private boolean mIntentInProgress;
     private boolean signedInUser;
     private ConnectionResult mConnectionResult;
    */ private SignInButton signinButton;
    private GoogleConnection googleConnection;

    private static Activity act;
    private static View rootView;
    private static View header;
    private static LinearLayout orderTab;
    private static LinearLayout labTest;
    private static LinearLayout healthCheckUp;
    private static Context c;
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    static Animation SlideDown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        act = this.getActivity();
        FacebookSdk.sdkInitialize(act.getApplicationContext());

        //initializing animations
        SlideDown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);


        rootView = inflater.inflate(R.layout.activity_facebook_check, container, false);
     return rootView;

    }




    static void loadView() {
        Log.d("LV","OT");

     }

   }