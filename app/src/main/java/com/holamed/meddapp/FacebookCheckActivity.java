package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public class FacebookCheckActivity extends AppCompatActivity implements Observer {
    CallbackManager callbackManager;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    private String name;
    private String email;
    private String gender;
    private String userId;
    private MixpanelAPI mixpanel;
    private  String projectToken = "39504217e737bb956ceb8c20ca0a34ee";
    private String PROGRESSDIALOGMESSAGE1="Registring..";
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
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        View parent=(View) customActionBarView.getParent();
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        Toolbar toolbarParent=(Toolbar) customActionBarView.getParent();
        toolbarParent.setContentInsetsAbsolute(0, 0);

        TextView title_name= (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("LOGIN");
        setContentView(R.layout.activity_facebook_check);

        pDialog=new ProgressDialog(this);
        home=(ImageView)findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacebookCheckActivity.this,SecondActivityMain.class);
                startActivity(i);

            }
        });
        back=(ImageView)findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacebookCheckActivity.this,SecondActivityMain.class);
                startActivity(i);
                // finish();
            }
        });

     /*   googleConnection = GoogleConnection.getInstance(this);
        googleConnection.addObserver(this);
     */   loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        loginEditor = loginPrefs.edit();
/*
        signinButton=(SignInButton)findViewById(R.id.sign_in_button);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleConnection.connect();
                googleApiClient= googleConnection.onSignIn();
                Log.d("google name",googleConnection.getPerson());
                Log.d("google email", googleConnection.getAccountName());

            }
        });
*/
        //  mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
findViewById(R.id.backfb).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i= new Intent(FacebookCheckActivity.this,SecondActivityMain.class);
       startActivity(i);
    }
});
        callbackManager = CallbackManager.Factory.create();
        mixpanel = MixpanelAPI.getInstance(this, projectToken);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Toast toast = Toast.makeText(FacebookCheckActivity.this, "OnSuccess:" + loginResult.getAccessToken().toString(), Toast.LENGTH_LONG);
                //toast.show();
                Log.d("Access Token", String.valueOf(loginResult.getAccessToken()));
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.v("FbLoginActivity", response.toString());
                                Log.d("object", String.valueOf(object));
                                Log.d("getJSON", String.valueOf(response.getJSONObject()));
                                try {
                                    JSONObject props = new JSONObject();
                                    props.put("User email", AppControllerSearchTests.getCurrentEmail());
                                    mixpanel.track("Facebook Event", props);
                                    name=object.getString("name");
                                    email=object.getString("email");
                                    gender=object.getString("gender");
                                    if(object.getString("gender").equalsIgnoreCase("female"))
                                        gender="Female";
                                    if(object.getString("gender").equalsIgnoreCase("male"))
                                        gender="Male";
                                    userId=object.getString("id");
                                    Log.d("fblogin",name);
                                    Log.d("fblogin",email);
                                    Log.d("fblogin",gender);
                                    Log.d("fblogin",userId);
                                    loginEditor.putString("gender",gender);
                                    loginEditor.putString("name",name);
                                    loginEditor.putString("email",email);
                                    loginEditor.putString("userId",userId);
                                    loginEditor.putBoolean("saved", true);
                                    loginEditor.putString("platform","facebook");
                                    Log.d("checkfb name", String.valueOf(loginPrefs.getString("name", "none")));
                                    loginEditor.commit();
                                    Log.d("checkfb name 2", String.valueOf(loginPrefs.getString("name", "none")));
                                    AsyncTask<String, Void, Bitmap> downloadImageTask=new DownloadImageTask().execute(userId);
                                    new RegisterTask().execute(name);
                                    int i=0;
                                    Log.d("status", String.valueOf(downloadImageTask.getStatus()));
                                    /*while(downloadImageTask.getStatus()== AsyncTask.Status.PENDING ||downloadImageTask.getStatus()== AsyncTask.Status.RUNNING) {
                                        pDialog.show();
                                        i++;
                                        Log.d("in while", String.valueOf(i));

                                    }
                                    if(downloadImageTask.getStatus()== AsyncTask.Status.FINISHED)
                                    {   pDialog.dismiss();
                                    */    Log.d("status","finished" );
                                    Intent ehrgo = new Intent(FacebookCheckActivity.this, SecondActivityMain.class);
                                    startActivity(ehrgo);
                                    // }

                                } catch (JSONException e) {
                                    showAlertDialog();
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
                //graphresponse.getRawResponse();
                Profile profile=Profile.getCurrentProfile();
                Log.d("profile", String.valueOf(profile));
                if(profile!=null)
                {
                    Log.d("profile",profile.getName());

                    //Log.d("profile",profile.asMap().get("email").toString());

                }


            }

            @Override
            public void onCancel() {
                showAlertDialog();
            }

            @Override
            public void onError(FacebookException e) {
                showAlertDialog();
            }
        });
    }


    public void onBackPressed() {
        Intent i=new Intent(FacebookCheckActivity.this,SecondActivityMain.class);
        startActivity(i);
    }
    public void showAlertDialog()
    {

        AlertDialog.Builder alertDialog;
        LayoutInflater inflater2 = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View  layout2 = inflater2.inflate(R.layout.custom_alert_dialog,null);
        Button one=(Button)layout2.findViewById(R.id.back);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button two=(Button)layout2.findViewById(R.id.try_again);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        alertDialog= new AlertDialog.Builder(this)
                .setView(layout2).setCancelable(false);
        alertDialog.show();
    }

    private void hidePdialog(){
        if(pDialog.isShowing())pDialog.hide();
        pDialog.dismiss();
    }

    private void setDialogText(String in){
        if(pDialog.isShowing())pDialog.setMessage(in);
    }
    private void showPdialog() {
        pDialog.setMessage(PROGRESSDIALOGMESSAGE1);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    @Override
    public void update(Observable observable, Object data) {

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... userID) {
            Bitmap bitmap=null;
            try {
                URL imageURL = new URL("https://graph.facebook.com/"+userId+"/picture?width=9999"/*"https://graph.facebook.com/" + userID + "/picture?type=large"*/);
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

            } catch (MalformedURLException e) {

                e.printStackTrace();
                showAlertDialog();
            } catch (IOException e) {

                e.printStackTrace();
                showAlertDialog();
            }

            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);



            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            OutputStream outStream = null;
            loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
            String filename=loginPrefs.getString("userId","MeddUser");

            File file = new File(extStorageDirectory, filename + ".png");
            if (file.exists()) {
                file.delete();
                file = new File(extStorageDirectory, filename + ".png");
                Log.e("file exist", "" + file + ",Bitmap= " + filename);
            }
            try {
                // make a new bitmap from your file
                // Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

                outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
                loginEditor = loginPrefs.edit();
                loginEditor.putString("picPath", file.getAbsolutePath());
                loginEditor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("file", "" + file);
        }
    }
    private class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... userID) {
            String user="done";
            String result = "";
            InputStream isr = null;
            Log.d("regiter", "init");
            try {
                Random random = new Random();

                String phone = "1221" + String.valueOf((100000 + random.nextInt(900000)));
                Log.d("Signupcheck", "yes1");
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(AppControllerSearchTests.serverUrl + "/api/users/signup");
                Log.d("Signupcheck", "yes2");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("phone", phone));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("password", "null"));
                nameValuePairs.add(new BasicNameValuePair("app_version", "7"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                isr = entity.getContent();
                Log.d("checkisr", isr.toString());

            } catch (Exception e) {
                Log.d("errorcome", e.toString());
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

                Log.d("result signup", String.valueOf(result));


            } catch (Exception e) {
                Log.d("result exception1",String.valueOf(e));
            }
            return user;
        }
        @Override
        protected void onPostExecute(String user) {
            super.onPostExecute(user);



        }
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
       /* switch (requestCode) {
            case RC_SIGN_IN:
                if (GoogleConnection.REQUEST_CODE == requestCode) {
                    googleConnection.onActivityResult(requestCode);
                }

                break;
        }*/
//        callbackManager.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,responseCode,intent);
    }

    private void getProfileInformation(String name) {
        Log.d("google","profileInfo");
        /*try {
            if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleApiClient);
                String personName = currentPerson.getDisplayName();
                Log.d("google",personName);
                int personGender=currentPerson.getGender();
                Log.d("google", String.valueOf(personGender));
                String personPhotoUrl = currentPerson.getImage().getUrl();
                Log.d("google",personPhotoUrl);
                String personEmail = Plus.AccountApi.getAccountName(googleApiClient);
                Log.d("google",personEmail);
        */        loginEditor.putString("gender",name);
                loginEditor.putString("name",name);
                loginEditor.putString("email",name);
                loginEditor.putString("userId","none");
                loginEditor.putBoolean("saved", true);
                loginEditor.putString("platform","google");
                Log.d("checkfb name", String.valueOf(loginPrefs.getString("name", "none")));
                loginEditor.commit();

               /* username.setText(personName);
                emailLabel.setText(email);
                new LoadProfileImage(image).execute(personPhotoUrl);
               */ // update profile frame with new info about Google Account
                // profile

          /*  }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("googlecatch", String.valueOf(e));
        }*/
    }
    @Override
    protected void onStart() {
        super.onStart();
 /*       Log.d("google", "onStart()");
        googleConnection.connect();
 */   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("google", "onDestroy()");
   /*     googleConnection.deleteObserver(this);
        googleConnection.disconnect();
   */     mixpanel.flush();
    }
}