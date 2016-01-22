package com.holamed.meddapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.holamed.meddapp.adapter.AliasAdapter;
import com.holamed.meddapp.adapter.CustomAutoCompleteAdapter;

import com.holamed.meddapp.adapter.TestsListAdapter;
import com.holamed.meddapp.adapter.swipeListAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SearchTests extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Animation.AnimationListener, AdapterView.OnItemSelectedListener, ActionBar.TabListener {

    private Dialog dialog;
    final static String URL = "http://api.medd.in/api/testgroups/getall";
    ListView testsListView;
    HttpClient client;
    boolean checkedvalues[];
    ArrayList<String> testName = new ArrayList<String>();
    ArrayList<String> testKey = new ArrayList<String>();
    ArrayList<String> testType = new ArrayList<String>();
    ArrayList<String> testAliases = new ArrayList<String>();
    ArrayList<Alias> arralias = new ArrayList<Alias>();

    private GoogleApiClient googleApiClient;
    private CustomAutoCompleteAdapter adapterl;
    private ImageView homeActionButton;
    private ImageView cartButton;

    ProgressDialog dialogprog;
    AliasAdapter adapterlist;
    Button continueButton;

    ArrayList<String> cityArrayList = new ArrayList<String>();
    ArrayList<String> checkname = new ArrayList<String>();
    CharSequence[] cityChar = null;


    public static final String TEST_SELECTED = "test_selected";
    TextView Cartnumber;
    EditText auto;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    public String isSaved = "f";

    // slide menu items
    Button citySelectButton;
    ListView list;
    String currentCityString;
    ArrayList itemsSelected;

    // Tab titles
    private String[] tabs = {"Common Tests", "Previous Searches"};

    swipeListAdapter adapter;
    DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tests);
        Log.d("shreyDebug", "onCreate SearchTests");
        //TODO: convert action bar to toolbar
        //setup actionbar
        LayoutInflater inflater1 = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater1.inflate(R.layout.actionbar_search_tests, null);
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
        parent.setContentInsetsAbsolute(0,0);

        AppControllerSearchTests.setSelectedLab(null);
        AppControllerSearchTests.setSelectedLabPatho(null);
        AppControllerSearchTests.setSelectedLabRadio(null);
        AppControllerSearchTests.setRefferalCode(null);
        AppControllerSearchTests.setHomecollection(false);
        AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);

        if (getIntent().getData() != null) {
            try {
                Uri data = getIntent().getData();
                String name = data.toString();
                ArrayList<String> pathology = new ArrayList<String>();
                ArrayList<String> radiology = new ArrayList<String>();
                String rightside = name.replaceAll("\\+", " +");
                java.util.List<String> ele = Arrays.asList(rightside.split(" +"));
                String temp1 = ele.get(0) + ",";
                java.util.List<String> ele2 = Arrays.asList(temp1.split("="));
                String radio = ele2.get(1);
                String patho = ele.get(1).substring(3) + ",";
                java.util.List<String> ele3 = Arrays.asList(patho.split(","));
                java.util.List<String> ele4 = Arrays.asList(radio.split(","));
                if (!(patho.equals(","))) {
                    for (int i = 0; i < ele3.size(); i++) {
                        String newstr = "\"" + ele3.get(i) + "\"";
                        pathology.add(newstr);
                    }
                }
                if (!(radio.equals(","))) {
                    for (int i = 0; i < ele4.size(); i++) {

                        String newstr = "\"" + ele4.get(i) + "\"";
                        radiology.add(newstr);
                    }
                }
                AppControllerSearchTests.setSelectedPatho(pathology);
                AppControllerSearchTests.setSelectedRadio(radiology);
                if (pathology.isEmpty() && radiology.isEmpty())
                    Toast.makeText(SearchTests.this, "Please select atleast one test", Toast.LENGTH_SHORT).show();
                else if ((!(pathology.isEmpty())) && !((radiology.isEmpty()))) {
                    Intent category = new Intent(SearchTests.this, CategorySearchRadio.class);
                    startActivity(category);
                } else {
                    Intent category = new Intent(SearchTests.this, CategorySearch.class);
                    startActivity(category);
                }
            } catch (Exception e) {
                Toast.makeText(SearchTests.this, "Book Tests and avail Upto 50% discounts", Toast.LENGTH_LONG).show();
            }
        }

        //variable definitions
        databaseHandler = new DatabaseHandler(this);
        itemsSelected = new ArrayList();
        client = new DefaultHttpClient();
        final List<Contact> cityList = databaseHandler.getAllContacts();

        //layout element references
        citySelectButton = (Button) customActionBarView.findViewById(R.id.bcityselect);
        cartButton = (ImageView) findViewById(R.id.cart);
        homeActionButton = (ImageView) findViewById(R.id.back_pressed);
        testsListView = (ListView) findViewById(R.id.list_view);

        //get all cities and save in CharSequence cityChar
        for (Contact cn : cityList) {
            String city = cn.getName();
            // Writing Contacts to log
            cityArrayList.add(city);
            cityArrayList.indexOf(city);
            Log.d("Name: ", "" + cityArrayList.indexOf(city));
        }
        cityChar = cityArrayList.toArray(new CharSequence[cityArrayList.size()]);

        //get current city from shared preferences and setText on button
        loginPrefs = SearchTests.this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        currentCityString = loginPrefs.getString("citySelected", "Indore");
        citySelectButton.setText(currentCityString);

        //onClickListener for City Select Button on actionbar
        citySelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCitySelectButton(view);
            }


        });

        //onClickListener for Home button on ActionBar
        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchTests.this, SecondActivityMain.class);
                startActivity(i);
                finish();
            }
        });

        //onscrolllistener for listview
        testsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //hide KB
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        buildGoogleApiClient();
        googleApiClient.connect();

        //auto complete
        currentCityString = loginPrefs.getString("citySelected", "Indore");

        //if(responses saved inside AppControllerSearchTests){get those} -> basically works as a cache
                if (isConnected())
                    new FetchTestsTask().execute(currentCityString);
                else
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();

        // Locate the EditText in listview_main.xml
        auto = (EditText) findViewById(R.id.searchEditText);

        // Capture Text in EditText
        auto.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = auto.getText().toString().toLowerCase(Locale.getDefault());
                adapterlist.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void onClickCitySelectButton(View view) {
        loginPrefs = SearchTests.this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);


        currentCityString = loginPrefs.getString("citySelected", "Indore");
        int pos = cityArrayList.indexOf(currentCityString);

        AlertDialog.Builder builder2 = new AlertDialog.Builder(SearchTests.this)
                .setTitle("Select your city")
                .setSingleChoiceItems(cityChar, pos, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        loginPrefs = SearchTests.this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                        loginEditor = loginPrefs.edit();
                        loginEditor.putString("citySelected", cityChar[which].toString());
                        loginEditor.commit();

                        currentCityString = loginPrefs.getString("citySelected", "Indore");
                        citySelectButton.setText(currentCityString);
                        Toast.makeText(SearchTests.this,
                                currentCityString + " selected", Toast.LENGTH_LONG).show();

                        //dismissing the dialog when the user makes a selection.
                        dialog.dismiss();
                        if (isConnected())
                            new FetchTestsTask().execute(currentCityString);
                        else
                            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();

                    }
                });
        AlertDialog alertdialog2 = builder2.create();
        alertdialog2.show();
    }

    private class FetchTestsTask extends AsyncTask<String, Void, ArrayList<Alias>> {

        public void onPreExecute() {


            dialogprog = ProgressDialog.show(SearchTests.this, "Loading..." +
                    "", "", true);

        }

        @Override
        protected ArrayList<Alias> doInBackground(String... userID) {
            doInBackgroundFetchTestTask(userID);
            return arralias;
        }

        @Override
        protected void onPostExecute(ArrayList<Alias> user) {
            super.onPostExecute(user);
            onPostExecuteFetchTestTask(user);
            dialogprog.dismiss();
        }
    }

    private void doInBackgroundFetchTestTask(String... userID) {
        String gotcity = userID[0].substring(0, 1).toLowerCase() + userID[0].substring(1);
        StringBuilder url = new StringBuilder(URL + "?city=" + gotcity);
        arralias.clear();
            Alias setalia = new Alias("Maths", "Elementary", "", "", false);
            arralias.add(setalia);
        setalia = new Alias("Maths", "Elementary", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("Maths", "Middle School", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("Maths", "High School", "", "", false);
        arralias.add(setalia);
      setalia = new Alias("Maths", "Intermediate", "", "", false);
        arralias.add(setalia);
       setalia = new Alias("Maths", "Advance", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("Social Science", "Elementary", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("English", "Elementary", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("English", "Advance", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("Computers", "Elementary", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("PCM", "Advance", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("PCM", "Elementary", "", "", false);
        arralias.add(setalia);
        setalia = new Alias("Maths", "Kids", "", "", false);
        arralias.add(setalia);
    }

    private void setStringToArrayList(String data) {
        try {
            JSONObject main = new JSONObject(data);
            JSONArray tests = main.getJSONArray("data");
            JSONArray tests2 = sortJsonArray(tests);
            String jsonResponse = "check" + tests.length();
            testName.clear();
            testAliases.clear();
            testKey.clear();
            testType.clear();
            for (int i = 0; i < tests2.length(); i++) {
                JSONObject test = tests2.getJSONObject(i);
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
                testName.add(name);
                testAliases.add(temp);
                testKey.add(id);
                testType.add(type);
                Log.d("FETCH", String.valueOf(tests.length()));
            }

            AppControllerSearchTests.setSearchtesname(testName);
            AppControllerSearchTests.setSearchtesalias(testAliases);
            AppControllerSearchTests.setSearchteskey(testKey);
            AppControllerSearchTests.setSearchtestype(testType);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    private void onPostExecuteFetchTestTask(ArrayList<Alias> user) {
        adapterlist = new AliasAdapter(SearchTests.this, user);


        // Binds the Adapter to the ListView
        testsListView.setAdapter(null);
        testsListView.setAdapter(adapterlist);

        continueButton = (Button) findViewById(R.id.bproceed);
        continueButton.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    continueButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    continueButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchTests.this,Tutor.class);
                startActivity(i);
            }
        });


        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsSelected.clear();
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Alias> testList = adapterlist.arraylist;
                for (int i = 0; i < testList.size(); i++) {
                    Alias tests = testList.get(i);
                    if (tests.isSelected()) {
                        checkname.add(tests.getName());
                        itemsSelected.add(tests.getName());
                        responseText.append("\n" + tests.getName());
                    }
                    checkedvalues = new boolean[checkname.size()];
                    for (int k = 0; k < checkname.size(); k++)
                        checkedvalues[k] = true;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchTests.this);
                final CharSequence[] cs = checkname.toArray(new CharSequence[checkname.size()]);
                checkname.clear();
                builder.setTitle("Selected Tests");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(cs, checkedvalues,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            ArrayList<Alias> testList = (ArrayList<Alias>) adapterlist.alialist;

                            @Override
                            public void onClick(DialogInterface dialog, int selectedItemId,
                                                boolean isSelected) {
                                if (isSelected) {
                                    itemsSelected.add(cs[selectedItemId]);

                                } else if (itemsSelected.contains(cs[selectedItemId])) {
                                    itemsSelected.remove(cs[selectedItemId]);
                                } else if (!isSelected) {
                                    //                            Toast.makeText(SearchTests.this, testName.get(0), Toast.LENGTH_SHORT).show();
                                    itemsSelected.remove(cs[selectedItemId]);

                                }


                            }
                        })
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Your logic when OK button is clicked
                                UpdateCheck(itemsSelected, testName, testKey, testAliases, testType);

                                ArrayList<String> checkedtests = new ArrayList<String>();
                                ArrayList<String> pathology = new ArrayList<String>();
                                ArrayList<String> radiology = new ArrayList<String>();

                                for (int i = 0; i < testName.size(); i++) {
                                    for (int l = 0; l < itemsSelected.size(); l++) {
                                        if (testName.get(i).equals(itemsSelected.get(l))) {
                                            if (testType.get(i).equals("pathology"))
                                                pathology.add("\"" + testKey.get(i) + "\"");
                                            else
                                                radiology.add("\"" + testKey.get(i) + "\"");

                                        }
                                    }
                                }
                                AppControllerSearchTests.setSelectedPatho(pathology);
                                AppControllerSearchTests.setSelectedRadio(radiology);
                                if (pathology.isEmpty() && radiology.isEmpty())
                                    Toast.makeText(SearchTests.this, "Please select atleast one test", Toast.LENGTH_SHORT).show();
                                else if ((!(pathology.isEmpty())) && !((radiology.isEmpty()))) {
                                    Intent category = new Intent(SearchTests.this, CategorySearchRadio.class);
                                    startActivity(category);
                                } else {
                                    Intent category = new Intent(SearchTests.this, CategorySearch.class);
                                    startActivity(category);
                                }
                            }
                        })
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                UpdateCheck(itemsSelected, testName, testKey, testAliases, testType);


                            }
                        });
                dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void UpdateCheck(ArrayList c, ArrayList<String> testName, ArrayList<String> testKey, ArrayList<String> testAliases, ArrayList<String> testType) {
        ArrayList<Alias> arralias2 = new ArrayList<Alias>();
        Alias setalia = null;
        boolean present = false;
        for (int i = 0; i < testName.size(); i++) {
            present = false;
            for (int l = 0; l < c.size(); l++) {
                if (testName.get(i).equals(c.get(l)))
                    present = true;
            }
            if (present)
                setalia = new Alias(testName.get(i), testAliases.get(i), testKey.get(i), testType.get(i), true);
            else
                setalia = new Alias(testName.get(i), testAliases.get(i), testKey.get(i), testType.get(i), false);
            arralias2.add(setalia);
            Log.d("Settingwa", testName.get(i).toString());

            // Binds the Adapter to the ListView

        }
        adapterlist = new AliasAdapter(SearchTests.this, arralias2);
        testsListView.setAdapter(adapterlist);
        UpdateCartValue(adapterlist.arraylist);
        dialog.dismiss();

    }

    public void UpdateCartValue(ArrayList<Alias> alialist) {

        ArrayList<Alias> countryList = alialist;
        int z = 0;
        for (int i = 0; i < countryList.size(); i++) {
            Alias country = countryList.get(i);
            if (country.isSelected()) {
                z++;
            }

        }
        Cartnumber = (TextView) findViewById(R.id.tvcart);
        Cartnumber.setText(" " + z + " ");
        Animation myAnimation;
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.myanimation);
        Cartnumber.startAnimation(myAnimation);
    }

    public void stringdeletedintestlist(String a, CustomAutoCompleteAdapter adp) {
        adp.removeFromClickedString(a);
        adapterl = adp;
        runThread();
        // adp.notifyDataSetChanged();
    }

    public void positiondeletedindialog(int position, TestsListAdapter ab, String a, CustomAutoCompleteAdapter b) {
        Log.d("position", String.valueOf(position));
        Log.d("check ab out", String.valueOf(ab));
        b.removeFromClickedString(a);
        b.notifyDataSetChanged();
        ab.customremove(position);
        ab.notifyDataSetChanged();
    }

    private void runThread() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                adapterl.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("shreyDebug", "onResume SearchTests");

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Location S", "play service connected");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        Log.d("getlastlocation", String.valueOf(mLastLocation));
        if (mLastLocation == null)
            AppControllerSearchTests.setGpsstatus(false);
        if (mLastLocation != null) {
            AppControllerSearchTests.setGpsstatus(true);
            AppControllerSearchTests.setLatitude(mLastLocation.getLatitude());
            AppControllerSearchTests.setLongitude(mLastLocation.getLongitude());
            AppControllerSearchTests.locSet = true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Location S", "play srvices sus");
        AppControllerSearchTests.setLatitude(1.0);
        AppControllerSearchTests.setLongitude(1.0);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Location S", "play srvices failed");
        AppControllerSearchTests.setLatitude(1.0);
        AppControllerSearchTests.setLongitude(1.0);

    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void onBackPressed() {
        Log.d("Back", "Pressed");
        Intent i = new Intent(SearchTests.this, SecondActivityMain.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public static JSONArray sortJsonArray(JSONArray array) {
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            try {
                jsons.add(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            Integer lid,
                    rid;

            @Override

            public int compare(JSONObject lhs, JSONObject rhs) {
                try {
                    lid = lhs.getInt("frequency");
                    rid = rhs.getInt("frequency");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Here you could parse string id to integer and then compare.
                return lid.compareTo(rid);
            }
        });
        return new JSONArray(jsons);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

}