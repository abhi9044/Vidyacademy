package com.holamed.meddapp;

/**
 * Created by Era on 4/25/2015.
 */

import android.accounts.Account;
import android.accounts.AccountManager;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.holamed.meddapp.adapter.EventLab;
import com.parse.Parse;
import com.parse.ParseInstallation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import io.branch.referral.Branch;

/**
 * Created by Era on 4/6/2015.
 */
public class AppControllerSearchTests extends MultiDexApplication {
    // public static final String serverUrl = "http://52.27.88.80";
    public static SharedPreferences loginPrefs;
    static String cityprefs;
    public static final String serverUrl = "http://52.27.88.80";
    public static final String TAG = AppController.class.getSimpleName(); //
    public static final String TYPELAB = "testslab";
    public static final String TYPEPHARMACY = "pharmacy";
    public static final String TYPEDEAL = "deal";
    public static final String TYPEEVENT = "event";
    private static boolean gpsstatus;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public static int sharedPrefVersion = 3;
    private static String currentEmail;
    private static String health_package_name;
    private static String subEventType;
    private static String refferalcode;
    private static PersonForm person;
    //=====================string constants for json object=====================
    private static String PHARMACYNAME = "name";

    private static String PHARMACYADDRESS = "address";
    private static String PHARMACYRATING = "rating";
    private static String PHARMACYDISCOUNT = "discount";
    private static String PHARMACYPHONE = "phone";
    private static String PHARMACYEMAILL = "email";
    private static String FULLDAY = "fullday";
    private static String HOMEDELIVERY = "home_delivery";
    private static String PHARMACYID = "_id";
    private static String HOMECOLLEC = "home_collection";
    private static String LABVISIT = "lab_visit";

    private static String DATA = "data";

    private static String LABID = "_id";
    private static String LABEMAIL = "email";
    private static String LABPHONE = "phone";
    private static String LABNAME = "name";
    private static String LABADDRESS = "address";
    private static String RATINGAVG = "rating";


    private static String TESTGROUPID = "_id";
    private static String TESTNAME = "name";
    private static String PRICEMRP = "mrp";
    private static String PRICEMEDD = "medd";
    private static String PRICEUSER = "user";
    private static String AVAILABLE = "available";


    private static String FACILITY = "facilities";
    private static String PARKING = "parking";
    private static String AC = "ac";
    private static String INSURANCE = "insurance";
    private static String CREDITCARD = "credit_card";
    private static String HOMECOLLECTION = "home_collection";

    private static String CITY = "city";
    private static int MY_SOCKET_TIMEOUT_MS = 50000;
    private static String TIMING = "timing";
    private static String TIME = "time";
    private static String To = "to";
    private static String FROM = "from";

    private static String DAYS = "days";
    private static String MONDAY = "mon";
    private static String TUESDAY = "tue";
    private static String WEDNESDAY = "wed";
    private static String THURSDAY = "thu";
    private static String FRIDAY = "fri";
    private static String SATURDAY = "sat";
    private static String SUNDAY = "sun";

    private static String COVER = "cover";
    private static String COVERMOB = "mobile";


    private static String LONGITUDE = "longitude";
    private static String LATITUDE = "latitude";


    private static String GEOLOC = "geolocation";
    private static String PUBLISH = "publish";
    //====================================================================

    public static Boolean locSet = false;


    public static Boolean homecoll = false;
    public static Boolean labvisit = false;

    public static Boolean NeedUpdate = Boolean.TRUE;
    public static ArrayList<ResultItem> fetched;

    public static ArrayList<ResultItem> fetchedhome;
    public static ArrayList<DealsItem> dealsfecthed;
    public static ArrayList<DealsItem> dealsfecthedcat1;
    public static ArrayList<DealsItem> dealsfecthedcat2;
    public static ArrayList<DealsItem> dealsfecthedcat3;
    public static ArrayList<DealsItem> dealsfecthedcat4;
    public static ArrayList<DealsItem> dealsfecthedcat5;
    public static ArrayList<DealsItem> dealsfecthedcat6;
    public static ArrayList<EventsItem> eventsfecthed;

    public static ArrayList<TransactionObject> transactionObjectArrayList;
    public static ArrayList<TestResultObject> ehrtests;
    // public static ArrayList<Pharmacydetails> pharmcies;
    private static AppControllerSearchTests mInstance;
    private static String UserId;
    private static String DateHealth;
    private static String pricelisthealth;
    private static String pricemeddhealth;
    private static String pricetotalhealth;

    private static boolean userInfoStatus;
    public static int DealsCount = 0;
    public static int currentDealsCount = 0;


    //storage and there access functions===============================
    private static boolean homedelivery;
    private static boolean homecollection;

    private static boolean allergy;
    private static boolean women;
    private static boolean fitness;
    private static boolean senior_citizen;
    private static boolean heart;
    private static boolean common;
    private static String patientName, patientAge, patientEmail, patientPhone, patientGender, patientAddressline1, patientAddressline2, patientAddresspincode;
    private static String selectedtest;
    private static String lati;
    private static String longi;

    private static String selectedlabsnames;
    private static String selectedlabsids;
    private static String addresslab;
    private static ArrayList<String> selectedmultites;
    private static ArrayList<String> selectedmultirad;
    private static ArrayList<String> selectedmultipat;
    private static ArrayList<String> selectedmultitesid;
    private static ArrayList<String> selectedmultiradid;
    private static ArrayList<String> selectedmultipatid;
    private static ArrayList<String> searchtesname;
    private static ArrayList<String> searchtesalias;
    private static ArrayList<String> searchteskey;
    private static ArrayList<String> searchtestype;

    private static ArrayList<String> selectedid;
    private static ArrayList<String> selectedtype;
    private static ResultItem selectedLab;
    private static ResultItem selectedLabPatho;

    private static ResultItem selectedLabRadio;

    private static TransactionObject transactionObject;

    //   private static Pharmacydetails selectedPharamcy;
    private static DealsItem selectedDeal;
    private static EventsItem selectedEvent;

    public static void setGpsstatus(boolean input) {
        gpsstatus = input;
    }

    public static boolean getGpsstatus() {
        return gpsstatus;
    }

    private static Integer distanceCountNo;
    private static Integer resultLength;
    private static Double latitude, longitude;
    private static String searchType;
    private static String priceHome;
    private static String uid;
    private static String patientId;


    public static boolean getHomedelivery() {
        return homedelivery;
    }

    public static void setHomedelivery(boolean input) {
        homedelivery = input;
    }

    public static String getCurrentEmail() {
        return currentEmail;
    }

    public static String getSubEventType() {
        return subEventType;
    }

    public static String getAddresslab() {
        return addresslab;
    }

    public static void setAddresslab(String input) {
        addresslab = input;
    }

    public static void setSubEventType(String input) {
        subEventType = input;
    }

    public static void setHealth_package_name(String input) {
        health_package_name = input;
    }

    public static String getHealth_package_name() {
        return health_package_name;
    }

    public static void setPatientId(String input) {
        patientId = input;
    }

    public static String getPatientId() {
        return patientId;
    }

    public static void setHealthLati(String input) {
        lati = input;
    }

    public static void setHealthLongi(String input) {
        longi = input;
    }

    public static void setPriceHome(String input) {
        priceHome = input;
    }

    public static String getPriceHome() {
        return priceHome;
    }

    public static void setDateHealth(String input) {
        DateHealth = input;
    }

    public static String getHealthLati() {
        return lati;
    }

    public static String getHealthLongi() {
        return longi;
    }

    public static void setPriceMeddHealth(String input) {
        pricemeddhealth = input;
    }

    public static void setPriceListHealth(String input) {
        pricelisthealth = input;
    }

    public static void setPriceTotalHealth(String input) {
        pricetotalhealth = input;
    }

    public static String getPriceMeddHealth() {
        return pricemeddhealth;
    }

    public static String getPriceListHealth() {
        return pricelisthealth;
    }

    public static String getPriceTotalHealth() {
        return pricetotalhealth;
    }

    public static String getDateHealth() {
        return DateHealth;
    }


    public static void setCurrentEmail(String input) {
        currentEmail = input;
    }

    public static void setRefferalCode(String input) {
        refferalcode = input;
    }

    public static boolean getHomecollection() {
        return homecollection;
    }

    public static boolean getHomecollHealth() {
        return homecoll;
    }

    public static boolean getLabVisit() {
        return labvisit;
    }

    public static void setHomecollHealth(boolean homeco) {
        homecoll = homeco;
    }

    public static void setHomecollection(boolean input) {
        homecollection = input;
    }

    public static void setLabsname(String lab_name) {
        selectedlabsnames = lab_name;
    }

    public static String getLabsname() {
        return selectedlabsnames;
    }

    public static void setLabIds(String lab_id) {
        selectedlabsids = lab_id;
    }

    public static String getLabsIds() {
        return selectedlabsids;
    }


    public static void setLabVisit(boolean lab) {
        labvisit = lab;
    }

    public static void setPersonForm(PersonForm p) {
        person = p;
    }

    public static PersonForm getPersonForm() {
        return person;
    }

    public static String getUid() {
        return uid;
    }

    public static String getRefferalcode() {
        return refferalcode;
    }


    public static void setUid(String in) {
        uid = in;
    }

    public static boolean getUserInfoStatus() {
        return userInfoStatus;
    }

    public static void setUserInfoStatus(boolean in) {
        userInfoStatus = in;
    }

    //   public static Pharmacydetails getSelectedPharamcy(){return sele
    //
    // ctedPharamcy;}
    public static String getPatientAddressline1() {
        return patientAddressline1;
    }

    public static String getPatientAddressline2() {
        return patientAddressline2;
    }

    public static String getPatientAddresspincode() {
        return patientAddresspincode;
    }

    public static ResultItem getSelectedLab() {
        return selectedLab;
    }

    public static ResultItem getSelectedLabPatho() {
        return selectedLabPatho;
    }

    public static ResultItem getSelectedLabRadio() {
        return selectedLabRadio;
    }

    public static String getSelectedtest() {
        return selectedtest;
    }

    public static void setSearchtesname(ArrayList<String> input) {
        searchtesname = input;
    }

    public static void setSearchtestype
            (ArrayList<String> input) {
        searchtestype = input;
    }

    public static void setSearchtesalias(ArrayList<String> input) {
        searchtesalias = input;
    }

    public static void setSearchteskey(ArrayList<String> input) {
        searchteskey = input;
    }

    public static ArrayList<String> getSelectedPatho() {
        return selectedid;
    }

    public static ArrayList<String> getSearchtesname() {
        return searchtesname;
    }


    public static ArrayList<String> getSearchteskey() {
        return searchteskey;
    }

    public static ArrayList<String> getSearchtestype() {
        return searchtestype;
    }

    public static ArrayList<String> getSearchtesalias() {
        return searchtesalias;
    }

    public static ArrayList<String> getSelectedRadio() {
        return selectedtype;
    }

    public
    static DealsItem getSelectedDeal() {
        return selectedDeal;
    }


    public static String getPatientName() {
        return patientName;
    }

    public static String getPatientAge() {
        return patientAge;
    }

    public static String getPatientEmail() {
        return patientEmail;
    }

    public static String getPatientPhone() {
        return patientPhone;
    }

    public static String getPatientGender() {
        return patientGender;
    }

    public static Double getLongitude() {
        return longitude;
    }

    public static Double getLatitude() {
        return latitude;
    }

    public static String getSearchType() {
        return searchType;
    }

    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String in) {
        UserId = in;
    }

    //  public static void setSelectedPharmacy(Pharmacydetails input){selectedPharamcy=input;}
    public static void setSelectedLab(ResultItem input) {
        selectedLab = input;
    }


    public static void setSelectedLabPatho(ResultItem input) {
        selectedLabPatho = input;
    }

    public static void setSelectedLabRadio(ResultItem input) {
        selectedLabRadio = input;
    }

    public static void setSelectedtest(String input) {
        selectedtest = input;
    }

    public static void setSelectedPatho(ArrayList<String> input) {
        //remove duplicates : ArrayList->Set->Arraylist => no duplicates but fucks up the order
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(input);
        ArrayList<String> tempArrayList = new ArrayList<>();
        tempArrayList.clear();
        tempArrayList.addAll(hashSet);
        selectedid = tempArrayList;
    }


    public static void setSelectedRadio(ArrayList<String> input) {
        //remove duplicates : ArrayList->Set->Arraylist => no duplicates but fucks up the order
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(input);
        ArrayList<String> tempArrayList = new ArrayList<>();
        tempArrayList.clear();
        tempArrayList.addAll(hashSet);
        selectedtype = tempArrayList;
    }

    public static void setSelectedDeal(DealsItem in) {
        selectedDeal = in;
    }

    public static void setSelectedEvent(EventsItem in) {
        selectedEvent = in;
    }

    public static EventsItem getSelectedEvent() {
        return selectedEvent;
    }

    public static void setPatientName(String input) {
        patientName = input;
    }

    public static void setPatientAge(String input) {
        patientAge = input;
    }

    public static void setPatientEmail(String input) {
        patientEmail = input;
    }

    public static void setPatientPhone(String input) {
        patientPhone = input;
    }

    public static void setPatientGender(String input) {
        patientGender = input;
    }

    public static void setPatientAddressline1(String input) {
        patientAddressline1 = input;
    }

    public static void setPatientAddressline2(String input) {
        patientAddressline2 = input;
    }

    public static void setPatientAddresspincode(String input) {
        patientAddresspincode = input;
    }

    public static void setLatitude(Double in) {
        latitude = in;
    }

    public static void setLongitude(Double in) {
        longitude = in;
    }

    public static void setSearchType(String in) {
        searchType = in;
    }

    public static void setEhrtests(ArrayList<TestResultObject> in) {
        ehrtests = in;
    }

    public static ArrayList<TestResultObject> getEhrtests() {
        return ehrtests;
    }

    public static void setTransactionObject(TransactionObject e) {
        transactionObject = e;
    }

    public static TransactionObject getTransactionObject(int position) {
        return transactionObjectArrayList.get(position);
    }
    ////////=================================================================

    public static void resetDealsCount() {
        DealsCount = 0;
        currentDealsCount = 0;
    }

    public static void setDealsCount(int n) {
        DealsCount = n;
    }

    public static void incrementDealsCount() {
        currentDealsCount++;
        if (currentDealsCount >= DealsCount) {
            filterDeals();

            try {
                HealthCheckUpNew.pDialog.dismiss();
                if (dealsfecthed.isEmpty())
                    Fragment_HealthCheckUp_All.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_All.loadView();

                if (dealsfecthedcat1.isEmpty())
                    Fragment_HealthCheckUp_Cat1.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_Cat1.loadView();

                if (dealsfecthedcat2.isEmpty())
                    Fragment_HealthCheckUp_Cat2.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_Cat2.loadView();

                if (dealsfecthedcat3.isEmpty())
                    Fragment_HealthCheckUp_Cat3.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_Cat3.loadView();

                if (dealsfecthedcat4.isEmpty())
                    Fragment_HealthCheckUp_Cat4.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_Cat4.loadView();

                if (dealsfecthedcat5.isEmpty())
                    Fragment_HealthCheckUp_Cat5.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_Cat5.loadView();

                if (dealsfecthedcat6.isEmpty())
                    Fragment_HealthCheckUp_Cat6.noResultView.setVisibility(View.VISIBLE);
                else
                    Fragment_HealthCheckUp_Cat6.loadView();

            } catch (Exception e) {

                try {
                    e.printStackTrace();
                    Fragment_HealthCheckUp_All.noResultView.setVisibility(View.VISIBLE);
                    Fragment_HealthCheckUp_Cat2.noResultView.setVisibility(View.VISIBLE);
                    Fragment_HealthCheckUp_Cat1.noResultView.setVisibility(View.VISIBLE);
                    Fragment_HealthCheckUp_Cat3.noResultView.setVisibility(View.VISIBLE);
                    Fragment_HealthCheckUp_Cat4.noResultView.setVisibility(View.VISIBLE);
                    Fragment_HealthCheckUp_Cat5.noResultView.setVisibility(View.VISIBLE);
                    Fragment_HealthCheckUp_Cat6.noResultView.setVisibility(View.VISIBLE);

                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }


    public static ImageDBhelper imageDBhelper;


    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(this);
        loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        cityprefs = loginPrefs.getString("citySelected", "Indore");
        Parse.initialize(this, "CHwGEC23gi8YbDtwilQBZB6eMZivZfVcdKF0ubjy", "9Ri4YsomBE9yOdvcmTQU6zdeMSJk0QMQOQwtmX51");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    /*    FontsOverride.setDefaultFont(this, "DEFAULT", "OpenSans-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset2.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");*/
        mInstance = this;
    }

    public static synchronized AppControllerSearchTests getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public static void fetchDeals() {


        cityprefs = loginPrefs.getString("citySelected", "Indore");
        String temp = cityprefs.substring(0, 1).toLowerCase() + cityprefs.substring(1);
        Log.d("cityprefs", cityprefs);
        dealsfecthed = new ArrayList<>();
        dealsfecthedcat1 = new ArrayList<>();
        dealsfecthedcat2 = new ArrayList<>();
        dealsfecthedcat3 = new ArrayList<>();
        dealsfecthedcat4 = new ArrayList<>();
        dealsfecthedcat5 = new ArrayList<>();
        dealsfecthedcat6 = new ArrayList<>();

        String urlJsonObj = serverUrl + "/api/healthPackages/get?publish=true&city=" + temp;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("data fecthed ", response.toString());

                try {
                    JSONArray deals = response.getJSONArray("data");
                    Log.d("Dcheck", String.valueOf(deals.length()));

                    resetDealsCount();
                    setDealsCount(deals.length());

                    if (deals.length() < 1) {
                        Fragment_HealthCheckUp_All.noResultView.setVisibility(View.VISIBLE);
                        Fragment_HealthCheckUp_Cat1.noResultView.setVisibility(View.VISIBLE);
                        Fragment_HealthCheckUp_Cat2.noResultView.setVisibility(View.VISIBLE);
                        Fragment_HealthCheckUp_Cat3.noResultView.setVisibility(View.VISIBLE);
                        Fragment_HealthCheckUp_Cat4.noResultView.setVisibility(View.VISIBLE);
                        Fragment_HealthCheckUp_Cat5.noResultView.setVisibility(View.VISIBLE);
                        Fragment_HealthCheckUp_Cat6.noResultView.setVisibility(View.VISIBLE);

                    }
                    for (int i = 0; i < deals.length(); i++) {
                        try {
                            StringBuilder labsnames = new StringBuilder();
                            StringBuilder labsids = new StringBuilder();
                            StringBuilder addresslab = new StringBuilder();
                            StringBuilder longi = new StringBuilder();
                            StringBuilder lati = new StringBuilder();

                            JSONObject deal = deals.getJSONObject(i);
                           /* if (!deal.getBoolean(PUBLISH)) {
                                incrementDealsCount();
                                continue;
                            }*/
                            String id = deal.getString("_id");
                            String deal_name = deal.getString("name");
                            String deal_desc = deal.getString("description");
                            String main_lab = deal.getString("main_lab");
                            Log.d("MainLabwa", main_lab);
                            int num_test = deal.getInt("num_tests");
                            homecoll = deal.getBoolean("home_collection");
                            labvisit = deal.getBoolean("lab_visit");
                            Log.d("home", "" + homecoll);
                            Log.d("home", "" + labvisit);

                            JSONObject price = deal.getJSONObject("price");
                            int pmedd = price.getInt("medd");
                            int plist = price.getInt("list");
                            int ptotal = price.getInt("total");
                            JSONObject category = deal.getJSONObject("category");
                            heart = category.getBoolean("heart");
                            allergy = category.getBoolean("allergy");
                            women = category.getBoolean("women");
                            fitness = category.getBoolean("fitness");
                            senior_citizen = category.getBoolean("senior_citizen");
                            common = category.getBoolean("common");
                            JSONArray labs = deal.getJSONArray("labs");
                            for (int m = 0; m < labs.length(); m++) {
                                JSONObject j = labs.getJSONObject(m);
                                JSONObject k = j.getJSONObject("geolocation");
                                if (lati.length() > 0) {
                                    lati.append("~");
                                    lati.append(String.valueOf(k.getDouble("latitude")));
                                    longi.append("~");
                                    longi.append(String.valueOf(k.getDouble("longitude")));
                                } else {
                                    lati.append(String.valueOf(k.getDouble("latitude")));
                                    longi.append(String.valueOf(k.getDouble("longitude")));
                                }

                                if (labsnames.length() > 0) {
                                    labsnames.append("~");
                                    labsnames.append(j.getString("name"));
                                } else {
                                    labsnames.append(j.getString(TESTNAME));
                                }
                                if (labsids.length() > 0) {
                                    labsids.append("~");
                                    labsids.append(j.getString("_id"));
                                } else {
                                    labsids.append(j.getString("_id"));
                                }

                                if (addresslab.length() > 0) {
                                    addresslab.append("~");
                                    addresslab.append(j.getString("address"));
                                } else {
                                    addresslab.append(j.getString("address"));
                                }
                            }
                            if (labs.length() > 0) {
                                labsnames.append("~");
                                labsids.append("~");
                                addresslab.append("~");
                                longi.append("~");
                                lati.append("~");
                            } else {
                                labsnames.append("null");
                                labsids.append("null");
                                addresslab.append("null");
                                longi.append("null");
                                lati.append("null");

                            }

                            DealsItem d = new DealsItem();
                            d.setDeal_name("Book1");
                            d.setId(id);
                            d.setAddressLab(addresslab.toString());
                            d.setNum_Test(String.valueOf(num_test));
                            d.setLab_main(main_lab);
                            d.setDeal_desc("Book Description");
                            d.setDeal_total(ptotal);
                            d.setDeal_medd(pmedd);
                            d.setDeal_list(plist);
                            d.setHomecoll(homecoll);
                            d.setLabVisit(labvisit);
                            d.setLabsnames(labsnames.toString());
                            d.setLabIds(labsids.toString());
                            d.setHealthLati(lati.toString());
                            d.setHealthLongi(longi.toString());
                            Log.d("Before", labsnames.toString());
                            Log.d("After", labsids.toString());

//
                            dealsfecthed.add(d);
                            if (heart)
                                dealsfecthedcat1.add(d);
                            if (senior_citizen)
                                dealsfecthedcat2.add(d);
                            if (fitness)
                                dealsfecthedcat3.add(d);
                            if (women)
                                dealsfecthedcat4.add(d);
                            if (allergy)
                                dealsfecthedcat5.add(d);
                            if (common)
                                dealsfecthedcat6.add(d);

                        } catch (Exception e) {
                            e.printStackTrace();
                            incrementDealsCount();
                        }
                    }

                    Log.d("inappcontroller", "fetched");

                    if (dealsfecthed.isEmpty())
                        Fragment_HealthCheckUp_All.noResultView.setVisibility(View.VISIBLE);
                    else
                        Fragment_HealthCheckUp_All.loadView();
                    if (dealsfecthedcat1.isEmpty())
                        Fragment_HealthCheckUp_Cat1.noResultView.setVisibility(View.VISIBLE);
                    else
                        Fragment_HealthCheckUp_Cat1.loadView();
                    if (dealsfecthedcat4.isEmpty())
                        Fragment_HealthCheckUp_Cat4.noResultView.setVisibility(View.VISIBLE);
                    else
                        Fragment_HealthCheckUp_Cat4.loadView();
                    if (dealsfecthedcat6.isEmpty())
                        Fragment_HealthCheckUp_Cat6.noResultView.setVisibility(View.VISIBLE);
                    else
                        Fragment_HealthCheckUp_Cat6.loadView();

                    if (HealthCheckUpNew.pDialog.isShowing()) HealthCheckUpNew.pDialog.dismiss();


                } catch (JSONException e) {
                    if (HealthCheckUpNew.pDialog.isShowing()) HealthCheckUpNew.pDialog.dismiss();

                    if (dealsfecthed.isEmpty())
                        Fragment_HealthCheckUp_All.noResultView.setVisibility(View.VISIBLE);

                    if (dealsfecthedcat1.isEmpty())
                        Fragment_HealthCheckUp_Cat1.noResultView.setVisibility(View.VISIBLE);
                    if (dealsfecthedcat4.isEmpty())
                        Fragment_HealthCheckUp_Cat4.noResultView.setVisibility(View.VISIBLE);
                    if (dealsfecthedcat6.isEmpty())
                        Fragment_HealthCheckUp_Cat6.noResultView.setVisibility(View.VISIBLE);


                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("errorlog", error.getMessage());
                HealthCheckUpNew.pDialog.hide();
                HealthCheckUpNew.showAlertDialog();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);

    }

    public static void fetchEvents(String city) {
        eventsfecthed = new ArrayList<>();
        final String tempcity = city.substring(0, 1).toLowerCase() + city.substring(1);
        String urlJsonObj = serverUrl + "/api/events/getall";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("data fetched ", response.toString());

                try {
                    JSONArray events = response.getJSONArray("data");
                    Log.d("events size", String.valueOf(events.length()));
                    if (events.length() < 1)
                        EventsActivity.noResultView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < events.length(); i++) {
                        try {
                            Log.d("eventi", String.valueOf(i));
                            JSONObject event = events.getJSONObject(i);
                            if ((event.getBoolean(PUBLISH))) {
                                if (event.getString("city").equals(tempcity)) {
                                    String id = event.getString("_id");
                                    String name = "Teaching camp at Mahalunge Village";
                                    String type = event.getString("type");
                                    Log.d("eventName", name);
                                    String price = event.getString("price");
                                    if (price.equals("null")) {
                                        price = "Free";

                                    } else {
                                        price = event.getString("price");

                                    }
                                    String description = event.getString("description");
                                    String date = event.getString("date");
                                    if (date.contains("T")) {
                                        date = date.substring(0, date.indexOf("T"));
                                    }
                                    String timeTo = event.getString("timeto");
                                    String timeFrom = event.getString("timefrom");
                                    String address = event.getString("address");
                                    JSONObject labInput = event.getJSONObject("lab");
                                    JSONArray testgroups = event.getJSONArray("testgroups");
                                    List<EventTestgroup> testgroupsList = new ArrayList<>();
                                    String testname, testId = null;
                                    Double user, mrp, medd = 0.0;
                                    for (int k = 0; k < testgroups.length(); k++) {
                                        try {
                                            testname = testgroups.getJSONObject(k).getString("name");
                                        } catch (Exception io) {
                                            testname = "";
                                        }
                                        try {
                                            testId = testgroups.getJSONObject(k).getString("_id");
                                        } catch (Exception io) {
                                            testId = "";
                                        }
                                        user = Double.parseDouble(testgroups.getJSONObject(k).getString("user"));
                                        mrp = Double.parseDouble(testgroups.getJSONObject(k).getString("mrp"));
                                        medd = Double.parseDouble(testgroups.getJSONObject(k).getString("medd"));

                                        EventTestgroup test = new EventTestgroup();
                                        test.setName("Teaching camp at Mahalunge Village");
                                        test.setId(testId);
                                        test.setMedd(medd);
                                        test.setMrp(mrp);
                                        test.setUser(user);
                                        testgroupsList.add(test);
                                    }
                                    EventLab lab = new EventLab();
                                    lab.setName(labInput.getString("name"));
                                    try {
                                        lab.setId(labInput.getString("_id"));
                                    } catch (Exception e) {
                                        lab.setId("");
                                    }
                                    lab.setPhone(labInput.getString("phone"));
                                    lab.setAddress(labInput.getString("address"));
                                    lab.setEmail(labInput.getString("email"));

                                    EventsItem newEvent = new EventsItem();
                                    newEvent.setEventId(id);
                                    Log.d("event id", newEvent.getEventId());
                                    newEvent.setEventName(name);
                                    newEvent.setEventAddress(address);
                                   if(!(date.equals("null"))) {
                                       newEvent.setEventDate(date + " " + timeFrom + " to " + timeTo);
                                   }
                                    else
                                   {
                                       newEvent.setEventDate(" " + timeFrom + " to " + timeTo);

                                   }
                                       newEvent.setEventDescription(description);

                                    newEvent.setEventPrice(price);
                                    newEvent.setEventLab(lab);
                                    newEvent.setEventType(type);
                                    newEvent.setEventTestgroup(testgroupsList);
                                    eventsfecthed.add(newEvent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Log.d("inappcontroller", "fetched");

                    if (EventsActivity.pDialog.isShowing()) EventsActivity.pDialog.dismiss();
                    if (eventsfecthed.isEmpty())
                        EventsActivity.noResultView.setVisibility(View.VISIBLE);
                    else {
                        EventsActivity.loadView();
                        EventsActivity.noResultView.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    if (EventsActivity.pDialog.isShowing()) EventsActivity.pDialog.dismiss();
                    if (eventsfecthed.isEmpty())
                        EventsActivity.noResultView.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Log.d("errorlog",error.getMessage());
                EventsActivity.showAlertDialog();
                EventsActivity.pDialog.hide();

                EventsActivity.showAlertDialog();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }


    /* public static void fetchpharmacies()
     {
         pharmcies=new ArrayList<>();
         pharmcies.clear();
         imageDBhelper = new ImageDBhelper(getInstance());
         resetDistCountNo();
         Pharmacydetails p=new Pharmacydetails();

         String url=serverUrl+"/api/pharmacies/getall";
         Log.d("inappcontrol", "yesyes");
         JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>()
         {
             @Override
             public void onResponse(JSONObject response) {
                 Log.d("volleyissues","not yet");
                 Log.d("data fecthed", response.toString());
                 try {

                     JSONArray pharmacygot = response.getJSONArray("data");
                     Log.d("check array length", String.valueOf(pharmacygot.length()));

                     for (int i = 0; i < pharmacygot.length(); i++) {
                         try {
                             JSONObject current = pharmacygot.getJSONObject(i);
                             if (!current.getBoolean(PUBLISH)) {
                                 Log.d("publish", "false " + current.getString(PHARMACYNAME));
                                 continue;
                             }

                             Pharmacydetails tempItem = new Pharmacydetails();

                             ///Pharmacy Data
                             tempItem.setPharmacyname(current.getString(PHARMACYNAME));
                             tempItem.setPharmacyaddress(current.getString(PHARMACYADDRESS));
                             try {
                                 tempItem.setRating(String.valueOf(current.getDouble(RATINGAVG)));
                             } catch (Exception e) {
                                 tempItem.setRating(null);
                             }
                             tempItem.setPharmacyID(current.getString(PHARMACYID));
                             tempItem.setDiscount(current.getInt(PHARMACYDISCOUNT));
                             tempItem.setPharmacyphone(current.getString(PHARMACYPHONE));
                             tempItem.setPharmacyemail(current.getString(PHARMACYEMAILL));

                             JSONObject timing = current.getJSONObject(TIMING);
                             //time
                             JSONObject time = timing.getJSONObject(TIME);

                             tempItem.setTimeTo(time.getString(To));
                             tempItem.setTimeFrom(time.getString(FROM));

                             //days
                             JSONObject days = timing.getJSONObject(DAYS);
                             tempItem.setMon(days.getBoolean(MONDAY));
                             tempItem.setTue(days.getBoolean(TUESDAY));
                             tempItem.setWed(days.getBoolean(WEDNESDAY));
                             tempItem.setThurs(days.getBoolean(THURSDAY));
                             tempItem.setFri(days.getBoolean(FRIDAY));
                             tempItem.setSat(days.getBoolean(SATURDAY));
                             tempItem.setSun(days.getBoolean(SUNDAY));

                             ///////////////image request//////////////////////

                             JSONObject coverImg = current.getJSONObject(COVER);
                             tempItem.setImgURL(coverImg.getString(COVERMOB));

                             imageWork(tempItem);
                             //fetches the image if not already fetched in past and stores in DB

                             /////////////////////////////////////////////////

                             //facilities
                             JSONObject facilities = current.getJSONObject(FACILITY);
                             tempItem.setParking(facilities.getBoolean(PARKING));
                             tempItem.setCc(facilities.getBoolean(CREDITCARD));
                             tempItem.setFullday(facilities.getBoolean(FULLDAY));
                             tempItem.setHomedelivery(facilities.getBoolean(HOMEDELIVERY));


                             //////////Location work///////////////////
                             try {
                                 JSONObject geolocation = current.getJSONObject(GEOLOC);


                                 tempItem.setLatLong(geolocation.getDouble(LATITUDE), geolocation.getDouble(LONGITUDE));
                             } catch (JSONException e) {
                                 tempItem.setDistanceValue(Double.MAX_VALUE);
                             }
                             //////////////////////////////////////////////

                             Log.d("jsonfetched", "true");
                             pharmcies.add(tempItem);
                         }
                         catch (Exception e){
                             e.printStackTrace();
                         }
                     }


                     Log.d("inappcontroller", "fetched");
                     PharmacySearch.pDialog.setCanceledOnTouchOutside(true);
                     if(PharmacySearch.pDialog.isShowing())PharmacySearch.pDialog.dismiss();

                     Log.d("Dialog","closed after success");
                     //loading list in all the 3 lists
                     if(pharmcies.isEmpty())PharmacySearch.noResultViewp.setVisibility(View.VISIBLE);
                    else  {
                         ClosestPharmacyFragment.loadView();
                         HighestDiscountFragment.loadView();
                         TopRatedPharmacyFragment.loadView();
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                     if(PharmacySearch.pDialog.isShowing())PharmacySearch.pDialog.dismiss();
                     if(pharmcies.isEmpty())PharmacySearch.noResultViewp.setVisibility(View.VISIBLE);

                    *//* Toast.makeText(AppControllerSearchTests.getInstance(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                            *//*
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("errorlog",error.getMessage());
                PharmacySearch.pDialog.hide();
                PharmacySearch.showAlertDialog();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("testgroups", "[\"552687a71f0bb8121ee730f3\"]");

                Log.d("check params before req",params.get("testgroups"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;

            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }
*/
    public static void fetchdata(final ArrayList<String> test_picked_patho, final String citySelected, final ArrayList<String> test_picked_radio) throws JSONException {
        final ArrayList<String> testgrps = new ArrayList<>();
        testgrps.addAll(test_picked_patho);
        testgrps.addAll(test_picked_radio);
        String pat;
        String rad;
        String tes;
        if (test_picked_patho.isEmpty())
            pat = "[]";
        else {
            String[] patho = test_picked_patho.toArray(new String[test_picked_patho.size()]);

            StringBuilder builder = new StringBuilder();

            for (String string : patho) {
                if (builder.length() > 0) {
                    builder.append(",");
                } else
                    builder.append("[");

                builder.append(string);
            }
            builder.append("]");
            pat = builder.toString();
        }
        if (test_picked_radio.isEmpty())
            rad = "[]";
        else {
            String[] radio = test_picked_radio.toArray(new String[test_picked_radio.size()]);
            StringBuilder builder = new StringBuilder();

            for (String string : radio) {
                if (builder.length() > 0) {
                    builder.append(",");
                } else
                    builder.append("[");

                builder.append(string);
            }
            builder.append("]");
            rad = builder.toString();


        }
        if (testgrps.isEmpty())
            tes = "[]";
        else {
            String[] teststringgrps = testgrps.toArray(new String[testgrps.size()]);
            StringBuilder builder = new StringBuilder();

            for (String string : teststringgrps) {
                if (builder.length() > 0) {
                    builder.append(",");
                } else
                    builder.append("[");

                builder.append(string);
            }
            builder.append("]");
            tes = builder.toString();
        }


        //fetch data from server and store it in fetched


        imageDBhelper = new ImageDBhelper(getInstance());
        resultLength = 0;


        fetched = new ArrayList<ResultItem>();
        fetched.clear();
        fetchedhome = new ArrayList<ResultItem>();
        fetchedhome.clear();
        //"552687a71f0bb8121ee730f6";

        ////////////volley work//////////////

        // Tag used to cancel the request
        //String url1= "[\"552687a71f0bb8121ee730f3\"]";
        final String temp = citySelected.substring(0, 1).toLowerCase() + citySelected.substring(1);
        String url = serverUrl + "/api/labs/getbytestgroups?testgroups=" + tes + "&city=" + temp + "&pathology=" + pat + "&radiology=" + rad + "&publish=true";
        Log.d("shreyURL", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            Boolean testFound;
            JSONArray labs_testgroup;

            @Override
            public void onResponse(JSONObject response) {
                Log.d("volleyissues in lab", "not yet");
                Log.d("data fecthed st", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    JSONObject obj = response.getJSONObject("data");
                    Log.d("Objectwa", obj.toString());
                    if (!test_picked_patho.isEmpty()) {
                        labs_testgroup = obj.getJSONArray("pathology");
                    } else {
                        labs_testgroup = obj.getJSONArray("radiology");
                    }
                    Log.d("check array length st", String.valueOf(labs_testgroup.length()));

                    //ResultItem tempItem = new ResultItem();
                    resultLength = labs_testgroup.length();
                    String jsonResponse = "check" + labs_testgroup.length();

                    for (int i = 0; i < labs_testgroup.length(); i++) {


                        try {
                            JSONObject current = labs_testgroup.getJSONObject(i);
                            Log.d("reading", String.valueOf(i) + " " + current.getString(LABNAME));
                            if (!current.getBoolean(PUBLISH)) {
                                Log.d("publish", "false " + current.getString(LABNAME));
                                continue;
                            }
                            ResultItem tempItem = new ResultItem();
///lab Data)

                            tempItem.setLabID(current.getString(LABID));
                            tempItem.setLabName(current.getString(LABNAME));
                            tempItem.setLabEmail(current.getString(LABEMAIL));
                            tempItem.setLabPhone(current.getString(LABPHONE));
                            tempItem.setLabAdd(current.getString(LABADDRESS));


                            try {
                                tempItem.setRating(current.getDouble(RATINGAVG));
                            } catch (Exception e) {
                                tempItem.setRating(-1);
                            }

                            tempItem.setCity(current.getString(CITY));
                            homecoll = current.getBoolean(HOMECOLLEC);
                            labvisit = current.getBoolean(LABVISIT);

                            ///////////////image request//////////////////////

                            JSONObject coverImg = current.getJSONObject(COVER);
                            tempItem.setImageURL(coverImg.getString(COVERMOB));

                            imageWork(tempItem);
                            //fetches the image if not already fetched in past and stores in DB

                            /////////////////////////////////////////////////

                            JSONObject timing = current.getJSONObject(TIMING);
                            //time
                            JSONObject time = timing.getJSONObject(TIME);

                            tempItem.setTimeTo(time.getString(To));
                            tempItem.setTimeFrom(time.getString(FROM));

                            //days
                            JSONObject days = timing.getJSONObject(DAYS);
                            tempItem.setMon(days.getBoolean(MONDAY));
                            tempItem.setTue(days.getBoolean(TUESDAY));
                            tempItem.setWed(days.getBoolean(WEDNESDAY));
                            tempItem.setThu(days.getBoolean(THURSDAY));
                            tempItem.setFri(days.getBoolean(FRIDAY));
                            tempItem.setSat(days.getBoolean(SATURDAY));
                            tempItem.setSun(days.getBoolean(SUNDAY));


                            //facilities
                            JSONObject facilities = current.getJSONObject(FACILITY);
                            tempItem.setParking(facilities.getBoolean(PARKING));
                            tempItem.setAc(facilities.getBoolean(AC));
                            tempItem.setInsurance(facilities.getBoolean(INSURANCE));
                            tempItem.setCc_accept(facilities.getBoolean(CREDITCARD));
                            tempItem.setHomeCollection(facilities.getBoolean(HOMECOLLECTION));

                            tempItem.setNabl(facilities.getBoolean("nabl"));
                            Log.d("nabl", String.valueOf(tempItem.isNabl()));

                            //////////Location work///////////////////
                            try {
                                JSONObject geolocation = current.getJSONObject(GEOLOC);

                                Log.d("geoloc check", "m" + String.valueOf(geolocation));
                                Log.d("lat double check", "m" + String.valueOf(geolocation.getDouble(LATITUDE)));
                                Log.d("long double check", "m" + String.valueOf(geolocation.getDouble(LONGITUDE)));

                                Log.d("lat string check", "m" + geolocation.getString(LATITUDE));
                                Log.d("long string check", "m" + geolocation.getString(LONGITUDE));

                                tempItem.setLatLong(geolocation.getDouble(LATITUDE), geolocation.getDouble(LONGITUDE));
                            } catch (JSONException e) {
                                tempItem.setDistanceValue(Double.MAX_VALUE);
                            }
                            //////////////////////////////////////////////

                            JSONArray testgroup = current.getJSONArray("testgroups");
                            Log.d("check testgroup length", String.valueOf(testgroup.length()));
                            Double mrp = 0.0, medd = 0.0, user = 0.0, discount = 0.0;
                            StringBuilder testnam = new StringBuilder();
                            StringBuilder mrpSingle = new StringBuilder();
                            StringBuilder userSingle = new StringBuilder();
                            StringBuilder meddSingle = new StringBuilder();
                            StringBuilder testi = new StringBuilder();
                            StringBuilder meddhcoll = new StringBuilder();
                            String pricePerTest = "";

                            testFound = false;
                            if (!test_picked_patho.isEmpty()) {
                                for (int j = 0; j < testgroup.length(); j++) {

                                    JSONObject testgroupobj = testgroup.getJSONObject(j);
                                    String stringefy = "\"" + testgroupobj.getString(TESTGROUPID) + "\"";

                                    for (int z = 0; z < test_picked_patho.size(); z++) {
                                        if (stringefy.equals(test_picked_patho.get(z))) {
                                            Log.d("inside if", "yes");
                                            //test detail
                                            if (!testgroupobj.getBoolean(AVAILABLE)) break;
                                            user = user + testgroupobj.getDouble(PRICEUSER);
                                            medd = medd + testgroupobj.getDouble(PRICEMEDD);
                                            mrp = mrp + testgroupobj.getDouble(PRICEMRP);

                                            pricePerTest += testgroupobj.getString("url_name") + "-" + String.valueOf(testgroupobj.getDouble(PRICEUSER)) + "\n";

                                            if (mrpSingle.length() > 0) {
                                                mrpSingle.append("~");
                                                mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                                userSingle.append("~");
                                                userSingle.append(testgroupobj.getString(PRICEUSER));
                                                meddSingle.append("~");
                                                meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                                meddhcoll.append("~");
                                                meddhcoll.append("100");


                                            } else {
                                                mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                                userSingle.append(testgroupobj.getString(PRICEUSER));
                                                meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                                meddhcoll.append("100");

                                            }

                                            if (testnam.length() > 0) {
                                                testnam.append("~");
                                                testnam.append(testgroupobj.getString(TESTNAME));
                                            } else {
                                                testnam.append(testgroupobj.getString(TESTNAME));
                                            }
                                            if (testi.length() > 0) {
                                                testi.append("~");
                                                testi.append(testgroupobj.getString(TESTGROUPID));
                                            } else
                                                testi.append(testgroupobj.getString(TESTGROUPID));

                                            //selectedmultites.add(testgroupobj.getString(TESTNAME));
                                            //selectedmultitesid.add(testgroupobj.getString(TESTGROUPID));
                                            //tempItem.setPriceUser(testgroupobj.getDouble(PRICEUSER));
                                            //tempItem.setPriceMedd(testgroupobj.getDouble(PRICEMEDD));
                                            //tempItem.setPriceMrp(testgroupobj.getDouble(PRICEMRP));
                                            //tempItem.setSelectedTestName(testgroupobj.getString(TESTNAME));
                                            //tempItem.setSelectedTestID(testgroupobj.getString(TESTGROUPID));
                                            //tempItem.setSaving(testgroupobj.getDouble(PRICEMRP) - testgroupobj.getDouble(PRICEUSER));
                                            testFound = true;
                                        }

                                    }


                                }
                                tempItem.setPricePerTest(pricePerTest);
                                tempItem.setPriceUser(user);
                                tempItem.setPriceMedd(medd);
                                tempItem.setPriceMrp(mrp);
                                tempItem.setPriceMeddSingle(meddSingle.toString());
                                tempItem.setPriceMrpSingle(mrpSingle.toString());
                                tempItem.setPriceUserSingle(userSingle.toString());
                                tempItem.setSelectedmultites(selectedmultites);
                                tempItem.setSelectedmultitesid(selectedmultitesid);
                                tempItem.setSelectedTestName(testnam.toString());
                                tempItem.setSelectedTestID(testi.toString());
                                tempItem.setSaving(mrp - user);
                                tempItem.setPriceHome(current.getString("home_collection_price"));

                            } else {
                                for (int j = 0; j < testgroup.length(); j++) {

                                    JSONObject testgroupobj = testgroup.getJSONObject(j);
                                    String stringefy = "\"" + testgroupobj.getString(TESTGROUPID) + "\"";

                                    for (int z = 0; z < test_picked_radio.size(); z++) {
                                        if (stringefy.equals(test_picked_radio.get(z))) {
                                            Log.d("inside if", "yes");
                                            //test detail
                                            if (!testgroupobj.getBoolean(AVAILABLE)) break;
                                            user = user + testgroupobj.getDouble(PRICEUSER);
                                            medd = medd + testgroupobj.getDouble(PRICEMEDD);
                                            mrp = mrp + testgroupobj.getDouble(PRICEMRP);
                                            if (mrpSingle.length() > 0) {
                                                mrpSingle.append("~");
                                                mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                                userSingle.append("~");
                                                userSingle.append(testgroupobj.getString(PRICEUSER));
                                                meddSingle.append("~");
                                                meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                                meddhcoll.append("~");
                                                meddhcoll.append("100");

                                            } else {
                                                mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                                userSingle.append(testgroupobj.getString(PRICEUSER));
                                                meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                                meddhcoll.append("100");

                                            }
                                            if (testnam.length() > 0) {
                                                testnam.append("~");
                                                testnam.append(testgroupobj.getString(TESTNAME));
                                            } else {
                                                testnam.append(testgroupobj.getString(TESTNAME));
                                            }
                                            if (testi.length() > 0) {
                                                testi.append("~");
                                                testi.append(testgroupobj.getString(TESTGROUPID));
                                            } else
                                                testi.append(testgroupobj.getString(TESTGROUPID));

                                            //     selectedmultites.add(testgroupobj.getString(TESTNAME));
                                            //                 selectedmultites.add(testgroupobj.getString(TESTGROUPID));

                                            //     tempItem.setPriceUser(testgroupobj.getDouble(PRICEUSER));
                                            //   tempItem.setPriceMedd(testgroupobj.getDouble(PRICEMEDD));
                                            // tempItem.setPriceMrp(testgroupobj.getDouble(PRICEMRP));
                                            //tempItem.setSelectedTestName(testgroupobj.getString(TESTNAME));
                                            //tempItem.setSelectedTestID(testgroupobj.getString(TESTGROUPID));
                                            //tempItem.setSaving(testgroupobj.getDouble(PRICEMRP) - testgroupobj.getDouble(PRICEUSER));
                                            testFound = true;
                                        }

                                    }


                                }
                                tempItem.setPriceUser(user);
                                tempItem.setPriceMedd(medd);
                                tempItem.setPriceMrp(mrp);
                                tempItem.setPriceUserSingle(userSingle.toString());
                                tempItem.setPriceMrpSingle(mrpSingle.toString());
                                tempItem.setPriceMeddSingle(meddSingle.toString());
                                tempItem.setSelectedTestName(testnam.toString());
                                tempItem.setSelectedTestID(testi.toString());
                                //           tempItem.setSelectedmultites(selectedmultites);
                                //         tempItem.setSelectedmultitesid(selectedmultitesid);
                                tempItem.setPriceHome(meddhcoll.toString());
                                tempItem.setSaving(mrp - user);


                            }
                            if (!testFound) continue;
                            Log.d("jsonfetched", "true");
                            if (homecoll) {
                                tempItem.setHomeCollectionAvailable(true);
                                fetchedhome.add(tempItem);
                            }

                            if (labvisit)
                                fetched.add(tempItem);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    Log.d("inappcontroller", "fetched");
                    if (CategorySearch.pDialog.isShowing()) CategorySearch.pDialog.dismiss();
                    if (!test_picked_patho.isEmpty()) {
                        if (fetched.isEmpty()) ClosestFragment.upd();
                        else {
                            Log.d("Dialog", "closed after success");
                            //loading list in all the 3 lists
                            ClosestFragment.loadView();
                        }
                        if (fetchedhome.isEmpty()) TopRatedFragment.upd();
                        else {
                            Log.d("Dialog", "closed after success");
                            //loading list in all the 3 lists
                            TopRatedFragment.loadView();
                        }

                    } else {
                        if (fetched.isEmpty()) ClosestFragment.upd();
                        else {
                            Log.d("Dialog", "closed after success");
                            //loading list in all the 3 lists
                            ClosestFragment.loadView();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error fetch tests", String.valueOf(e));
                    CategorySearch.pDialog.dismiss();
                    if (fetched.isEmpty()) CategorySearch.noResultView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("errorlogst", error.toString());
                CategorySearch.pDialog.hide();

                CategorySearch.showAlertDialog();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //    params.put("testgroups", "[\"552687a71f0bb8121ee730f3\"]");

                Log.d("check params before req", params.get("testgroups"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;


            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));

        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);


    }

    private static Response.Listener<String> createMyReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", "thumbs up");
                //setTvResultText(response);
            }
        };
    }


    private static Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());
            }
        };
    }


    private void setTvResultText(String str) {
        //  mTvResult.setText(String.format(getString(R.string.act__params__sum), str));
    }

    private static void imageWork(final ResultItem item) {


        if (!imageDBhelper.isOpen()) {
            imageDBhelper.open();
        }

        if (imageDBhelper.isInDb(item.getLabID(), TYPELAB)) {

            Log.d("imgDB", "already in DB " + item.getLabName());
            return;
        }


        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(item.getImageURL(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        if (!imageDBhelper.isOpen())
                            imageDBhelper.open();
                        Log.d("ImageResponse", bitmap.toString() + item.getLabName() + item.getLabID());
                        try {
                            imageDBhelper.insertElement(new ImageDBElement(bitmap, item.getLabName(), item.getLabID(), TYPELAB));
                            imageDBhelper.close();
                        } catch (android.database.sqlite.SQLiteConstraintException e) {

                            if (imageDBhelper.isOpen())
                                imageDBhelper.close();
                            Log.d("DB error", " in AppContrl");
                            e.printStackTrace();
                        }
                        //imageDBhelper.close();
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                });
        // Access the RequestQueue through your singleton class.
        AppControllerSearchTests.getInstance().addToRequestQueue(request);

        if (imageDBhelper.isOpen()) imageDBhelper.close();
    }

    /*private static void imageWork(final Pharmacydetails item)
    {

        try {
            if (!imageDBhelper.isOpen()) {
                imageDBhelper.open();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            imageDBhelper.open();
        }

        if(imageDBhelper.isInDb(item.getPharmacyID(),TYPEPHARMACY)){

            Log.d("imgDB","already in DB "+item.getPharmacyname());
            imageDBhelper.close();
            return ;
        }


        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(item.getImgURL(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        if (!imageDBhelper.isOpen())
                            imageDBhelper.open();
                        Log.d("ImageResponse",bitmap.toString()+item.getPharmacyname()+item.getPharmacyID());
                        try {
                            imageDBhelper.insertElement(new ImageDBElement(bitmap, item.getPharmacyname(), item.getPharmacyID(),TYPEPHARMACY));
                            imageDBhelper.close();
                        }
                        catch (android.database.sqlite.SQLiteConstraintException e){

                            if(imageDBhelper.isOpen())
                                imageDBhelper.close();
                            Log.d("DB error"," in AppContrl");
                            e.printStackTrace();
                        }
                        //imageDBhelper.close();
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                });

        // Access the RequestQueue through your singleton class.
        AppControllerSearchTests.getInstance().addToRequestQueue(request);


if (imageDBhelper.isOpen())imageDBhelper.close();
    }*/

    public static int incrementDistCountNo() {
        try {
            distanceCountNo++;
        } catch (Exception io) {
            // Toast.makeText(getApplicationContext(), "Erroor Ocurred! Please try again", Toast.LENGTH_SHORT).show();
        }

        return distanceCountNo;
    }

    public static int getResultLength() {
        return resultLength;
    }


    static public ResultItem responseToResulltItem(JSONObject response, String testsID) {
        try {

            JSONArray labs_testgroup = response.getJSONArray("data");
            Log.d("check array length", String.valueOf(labs_testgroup.length()));
            ResultItem tempItem = null;

            for (int i = 0; i < 1; i++) {
                JSONObject current = labs_testgroup.getJSONObject(i);
                tempItem = new ResultItem();

                ///lab Data

                tempItem.setLabID(current.getString(LABID));
                tempItem.setLabName(current.getString(LABNAME));
                tempItem.setLabEmail(current.getString(LABEMAIL));
                tempItem.setLabPhone(current.getString(LABPHONE));
                tempItem.setLabAdd(current.getString(LABADDRESS));
                tempItem.setRating(current.getDouble(RATINGAVG));
                tempItem.setCity(current.getString(CITY));
                //tempItem.setImageURL(current.getString(IMAGEURL));

                //tempItem.setImageURL("https://www.google.co.in/images/srpr/logo11w.png");
                ///////////////image request//////////////////////


                JSONObject coverImg = current.getJSONObject(COVER);
                tempItem.setImageURL(coverImg.getString(COVERMOB));

                //   imageWork(tempItem);
                //fetches the image if not already fetched in past and stores in DB

                /////////////////////////////////////////////////

                JSONObject timing = current.getJSONObject(TIMING);
                //time
                JSONObject time = timing.getJSONObject(TIME);

                tempItem.setTimeTo(time.getString(To));
                tempItem.setTimeFrom(time.getString(FROM));

                //days
                JSONObject days = timing.getJSONObject(DAYS);
                tempItem.setMon(days.getBoolean(MONDAY));
                tempItem.setTue(days.getBoolean(TUESDAY));
                tempItem.setWed(days.getBoolean(WEDNESDAY));
                tempItem.setThu(days.getBoolean(THURSDAY));
                tempItem.setFri(days.getBoolean(FRIDAY));
                tempItem.setSat(days.getBoolean(SATURDAY));
                tempItem.setSun(days.getBoolean(SUNDAY));
                //facilities
                JSONObject facilities = current.getJSONObject(FACILITY);
                tempItem.setParking(facilities.getBoolean(PARKING));
                tempItem.setAc(facilities.getBoolean(AC));
                tempItem.setInsurance(facilities.getBoolean(INSURANCE));
                tempItem.setCc_accept(facilities.getBoolean(CREDITCARD));
                tempItem.setHomeCollection(facilities.getBoolean(HOMECOLLECTION));

                //////////Location work///////////////////
                try {
                    JSONObject geolocation = current.getJSONObject(GEOLOC);


                    tempItem.setLatLong(geolocation.getDouble(LATITUDE), geolocation.getDouble(LONGITUDE));
                } catch (JSONException e) {
                    tempItem.setDistanceValue(Double.MAX_VALUE);
                }
                //////////////////////////////////////////////

                JSONArray testgroup = current.getJSONArray("testgroups");
                Log.d("check testgroup length", String.valueOf(testgroup.length()));

                Boolean testFound = false;
                for (int j = 0; j < testgroup.length(); j++) {
                    JSONObject testgroupobj = testgroup.getJSONObject(j);

                    if (testgroupobj.getString(TESTGROUPID).equals(testsID)) {
                        //    if(!testgroupobj.getBoolean(AVAILABLE))break;   ignoring this if listed in deals
                        //test detail

                        tempItem.setPriceUser(testgroupobj.getDouble(PRICEUSER));
                        tempItem.setPriceMedd(testgroupobj.getDouble(PRICEMEDD));
                        tempItem.setPriceMrp(testgroupobj.getDouble(PRICEMRP));
                        tempItem.setSelectedTestName(testgroupobj.getString(TESTNAME));
                        tempItem.setSelectedTestID(testgroupobj.getString(TESTGROUPID));
                        tempItem.setSaving(testgroupobj.getDouble(PRICEMRP) - testgroupobj.getDouble(PRICEUSER));
                        testFound = true;
                        break;
                    }

                }
                if (!testFound) return null;


                Log.d("jsonfetched detail", "true");


            }
            return tempItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void fetchEHR(String user_Id) {
        String url = AppControllerSearchTests.serverUrl + "/api/v1/ehr/getAll/?user_id=" + user_Id;
        Log.d("shreyDebug", "the url is :" + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                transactionObjectArrayList = new ArrayList<>();
                transactionObjectArrayList.clear();
                try {
                    JSONArray transactionObjectJSONArray = response.getJSONArray("data");
                    for (int i = 0; i < transactionObjectJSONArray.length(); i++) {
                        TransactionObject transactionObject = new TransactionObject();

                        JSONObject singleTransactionJSONObject = transactionObjectJSONArray.getJSONArray(i).getJSONObject(0);

                        transactionObject.lab_name = singleTransactionJSONObject.getJSONObject("diagnostics").getString("name");
                        transactionObject.date = singleTransactionJSONObject.getJSONObject("timestamp").getString("booking").substring(0, 10);
                        transactionObject.patient_name = singleTransactionJSONObject.getJSONObject("patient").getString("name");

                        JSONArray testGroupsBookedJSONArray = singleTransactionJSONObject.getJSONObject("diagnostics").getJSONArray("tests");
                        StringBuilder AllTestGroups = new StringBuilder();
                        for (int k = 0; k < testGroupsBookedJSONArray.length(); k++) {
                            JSONObject temp = testGroupsBookedJSONArray.getJSONObject(k);
                            if (AllTestGroups.length() > 0) {
                                AllTestGroups.append("\n");
                                AllTestGroups.append(temp.getString("name"));

                            } else
                                AllTestGroups.append(temp.getString("name"));
                        }
                        transactionObject.test_groups = AllTestGroups.toString();

                        JSONArray pathologyJSONArray = singleTransactionJSONObject.getJSONObject("ehr").getJSONArray("pathology");
                        if (pathologyJSONArray.length() != 0) {

                            ArrayList<String> testGroupNameArrayList = new ArrayList<>();
                            HashMap<String, ArrayList<TestResultObject>> hashMap = new HashMap<>();

                            for (int k = 0; k < pathologyJSONArray.length(); k++) {

                                JSONArray testGroupsJSONArray = pathologyJSONArray.getJSONObject(k).getJSONArray("testgroups");

                                for (int j = 0; j < testGroupsJSONArray.length(); j++) {
                                    JSONObject currentTestGroupJSONObject = testGroupsJSONArray.getJSONObject(j);
                                    testGroupNameArrayList.add(currentTestGroupJSONObject.getString("name"));
                                    JSONArray allTestsWithinThisTestGroupJSONArray = currentTestGroupJSONObject.getJSONArray("tests");
                                    ArrayList<TestResultObject> testResultObjectArrayList = new ArrayList<>();
                                    for (int l = 0; l < allTestsWithinThisTestGroupJSONArray.length(); l++) {
                                        TestResultObject testResultObject = new TestResultObject();
                                        testResultObject.setName(allTestsWithinThisTestGroupJSONArray.getJSONObject(l).getString("name"));
                                        testResultObject.setRange(String.valueOf(allTestsWithinThisTestGroupJSONArray.getJSONObject(l).getInt("value")));
                                        testResultObject.setValue(String.valueOf(allTestsWithinThisTestGroupJSONArray.getJSONObject(l).getJSONObject("range").getInt("min")) + "-" + String.valueOf(allTestsWithinThisTestGroupJSONArray.getJSONObject(l).getJSONObject("range").getInt("max")));
                                        testResultObjectArrayList.add(testResultObject);
                                    }
                                    hashMap.put(currentTestGroupJSONObject.getString("name"), testResultObjectArrayList);
                                }

                            }
                            transactionObject.hashMap = hashMap;
                            transactionObject.testGroupNameArrayList = testGroupNameArrayList;
                        }
                        transactionObjectArrayList.add(transactionObject);
                    }


                    // Parsing json object response
                    // response will be a json object
                    updateEhrList("finished");

                } catch (
                        Exception e
                        )

                {
                    Log.d("ehrexception", String.valueOf(e));
                    e.printStackTrace();
                    updateEhrList("error");
                }

                if (transactionObjectArrayList == null)

                {
                    if (HistoryTabsFragment.pDialog.isShowing())
                        HistoryTabsFragment.pDialog.dismiss();
                    HistoryTabsFragment.loadNoResultsView();
                    // EHR.loadProfile();
                } else

                {
                    if (HistoryTabsFragment.pDialog.isShowing())
                        HistoryTabsFragment.pDialog.dismiss();
                    HistoryTabsFragment.loadView();
                }
            }
        }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("shreyDebug", "OnErrorResponse error" + error.toString());
                HistoryTabsFragment.pDialog.hide();

                HistoryTabsFragment.showAlertDialog();
            }

        }

        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //    params.put("testgroups", "[\"552687a71f0bb8121ee730f3\"]");

                // Log.d("check params before req",params.get("testgroups"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;


            }

        };


        jsonObjReq.setRetryPolicy(new

                        DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        );
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));
        AppControllerSearchTests.getInstance().

                addToRequestQueue(jsonObjReq);


    }

    public static void performTransfer(List<PastOrdersObject> pastOrderslist, String userId, String phone) {
        Log.d("tranfer ", "in function");
        String urlJsonObj = AppControllerSearchTests.serverUrl + "/api/v1/ehr/transferPastOrders";
        HashMap<String, String> jsonParams = new HashMap<>();

        JSONArray patients = new JSONArray();
        JSONObject transfer = new JSONObject();
        try {

            for (int i = 0; i < pastOrderslist.size(); i++) {
                JSONObject patientobj = new JSONObject();
                String name = pastOrderslist.get(i).getPatientName();
                String id = pastOrderslist.get(i).getPatient_id();
                //  String phone=pastOrderslist.get(i).getPhone();
                // Log.d("transfer",phone);
                String address = pastOrderslist.get(i).getAddress();
                patientobj.put("name", name);
                patientobj.put("_id", id);
                //patientobj.put("phone",phone);
                patientobj.put("address", address);
                patients.put(patientobj);
            }
            // patient.put("gender", patientGender);//extra
            Log.d("transfer", patients.toString());
            ;
            transfer.put("user_id", userId);
            transfer.put("phone", phone);
            transfer.put("patients", patients);
            jsonParams.put("transfer", transfer.toString());

            Log.d("transfer", String.valueOf(jsonParams));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                urlJsonObj, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject response) {


                try {

                    Log.d("transfer response", response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    //showAlertDialog();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Check", "Error: " + error.getMessage());
                Log.d("volleyError", error.getMessage());
            }
        });
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);

    }

    public static void sendAnalytics() {
        SharedPreferences pref;
        pref = getInstance().getSharedPreferences("testapp", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        if (pref.getBoolean("sentToServer", false)) return;

        final String tmDevice, tmSerial, androidId, deviceId, timeStamp, androidVersion;
        tmDevice = pref.getString("tmDevice", "nil");
        tmSerial = pref.getString("tmSerial", "nil");
        androidId = pref.getString("androidId", "nil");
        deviceId = pref.getString("deviceId", "nil");
        timeStamp = pref.getString("timeStamp", "nil");
        androidVersion = pref.getString("version", "nil");
        JSONArray emails = new JSONArray();

        Account[] accounts = AccountManager.get(getInstance()).getAccounts();
        // String[] array;
        ArrayList<String> array = new ArrayList<>();

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                // emails.put(account.name);
                array.add(account.name);
                Log.d("possible email", account.name);
            }
        }

        Set<String> stringSet = new HashSet<>(array);// removing duplicate entries
        String[] filteredArray = stringSet.toArray(new String[0]);

        for (int i = 0; i < filteredArray.length; i++) {
            emails.put(filteredArray[i]);
        }


        JSONArray imei = new JSONArray();
        imei.put(tmDevice);

        JSONObject analyticsData = new JSONObject();
        try {
            analyticsData.put("type", "android");
            analyticsData.put("imei", imei);
            analyticsData.put("serial", tmSerial);
            analyticsData.put("os_id", androidId);
            analyticsData.put("os_version", androidVersion);
            analyticsData.put("device_id", deviceId);
            analyticsData.put("emails", emails);
            analyticsData.put("timestamp", timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String urlJsonObj = serverUrl + "/api/mobiledevices/create";

        String para;
        try {


            para = analyticsData.toString();

            Log.d("analytics", "Para String: " + para);

            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("mobiledevice", para);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    urlJsonObj, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {

                    try {

                        Log.d("analytics", "response:" + response.toString());
                        if (response.getBoolean("status")) {

                            editor.putBoolean("sentToServer", true);
                            editor.putString("IdFromServer", response.getString("_id"));
                            editor.commit();
                            Log.d("analytics", "sentToServer");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("analytics", "json error");
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Check", "Error: " + error.getMessage());
                    Log.d("analytics", "volley error");

                }
            });

            // Adding request to request queue
            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isAnalyticsSent() {
        SharedPreferences pref;

        pref = getInstance().getSharedPreferences("testapp", MODE_PRIVATE);
        Log.d("analytics", "checking if sent " + String.valueOf(pref.getBoolean("sentToServer", false)));
        return pref.getBoolean("sentToServer", false);
    }

    public static void saveAnalytics() {

        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getInstance().getSharedPreferences("testapp", MODE_PRIVATE);
        editor = pref.edit();


        final TelephonyManager tm = (TelephonyManager) getInstance().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId, androidVersion;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getInstance().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        androidVersion = Build.VERSION.RELEASE;
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        Log.d("deviceId", deviceId);

        Date date = new Date();

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String timeStamp = sdf2.format(date);
        Log.d("timestamp", timeStamp);
        SharedPreferences.Editor prefEditor = pref.edit();
        editor.putString("deviceId", deviceId);
        editor.putString("tmDevice", tmDevice);
        editor.putString("tmSerial", tmSerial);
        editor.putString("androidId", androidId);
        editor.putString("timeStamp", timeStamp);
        editor.putString("version", androidVersion);
        editor.putInt("PrefVersion", sharedPrefVersion);
        editor.putBoolean("sentToServer", false);
        editor.putBoolean("savedAnalytics", true);
        editor.commit();

        Log.d("analytics", "saved to sharedpref " + pref.getBoolean("savedAnalytics", false));
        AppControllerSearchTests.sendAnalytics();


    }

    public static void filterDeals() {//removes the deals whose details cant be fetched


        for (int i = 0; i < dealsfecthed.size(); i++) {
            if (!dealsfecthed.get(i).toShow) {
                dealsfecthed.remove(i);
                i--;
            }

        }
    }

    public static void sendAnalyticsUpdate() {
        SharedPreferences pref;

        pref = getInstance().getSharedPreferences("testapp", MODE_PRIVATE);


        String idFromServer = pref.getString("IdFromServer", "nil");
        if (idFromServer.equals("nil")) {
            // if id was not recieved last time then it sends full new analytics data to get _id
            sendAnalytics();
            return;
        }
        Date date = new Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String timeStamp = sdf2.format(date);

        JSONObject analyticsData = new JSONObject();
        try {

            analyticsData.put("_id", idFromServer);
            analyticsData.put("timestamp", timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String urlJsonObj = serverUrl + "/api/mobiledevices/reopen";

        String para;
        try {


            para = analyticsData.toString();

            Log.d("analytics update", "Para String: " + para);

            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("mobiledevice", para);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    urlJsonObj, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

                @Override

                public void onResponse(JSONObject response) {

                    try {

                        Log.d("analytics", "response:" + response.toString());
                        if (response.getBoolean("status")) {


                            Log.d("analytics update", "sentToServer");

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("analytics update", "json error");
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Check", "Error: " + error.getMessage());
                    Log.d("analytics", "volley error");

                }
            });

            // Adding request to request queue
            AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // public static void fetchSpecificLab
    public static void updateEhrList(String msg) {
        HistoryTabsFragment.loadView();
    }


    public static void fetchdataradio(String citypr, final ArrayList<String> test_picked_radio) throws JSONException {
        String rad;
        if (test_picked_radio.isEmpty())
            rad = "[]";
        else {
            String[] radio = test_picked_radio.toArray(new String[test_picked_radio.size()]);
            StringBuilder builder = new StringBuilder();

            for (String string : radio) {
                if (builder.length() > 0) {
                    builder.append(",");
                } else
                    builder.append("[");

                builder.append(string);
            }
            builder.append("]");
            rad = builder.toString();


        }
        //fetch data from server and store it in fetched
        imageDBhelper = new ImageDBhelper(getInstance());
        resultLength = 0;


        fetched = new ArrayList<ResultItem>();
        fetched.clear();
        fetchedhome = new ArrayList<ResultItem>();
        fetchedhome.clear();
        //"552687a71f0bb8121ee730f6";

        ////////////volley work//////////////

        // Tag used to cancel the request
        //String url1= "[\"552687a71f0bb8121ee730f3\"]";
        String temp = citypr.substring(0, 1).toLowerCase() + citypr.substring(1);
        String url = serverUrl + "/api/labs/getbytestgroups?testgroups=" + rad + "&city=" + temp + "&pathology=[]" + "&radiology=" + rad + "&publish=true";
        Log.d("shreyURL", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            Boolean testFound;
            JSONArray labs_testgroup;

            @Override
            public void onResponse(JSONObject response) {
                Log.d("volleyissues in lab", "not yet");
                Log.d("data fecthed st", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    JSONObject obj = response.getJSONObject("data");
                    Log.d("Objectwa", obj.toString());
                    labs_testgroup = obj.getJSONArray("radiology");

                    Log.d("check array length st", String.valueOf(labs_testgroup.length()));

                    //ResultItem tempItem = new ResultItem();
                    resultLength = labs_testgroup.length();
                    String jsonResponse = "check" + labs_testgroup.length();

                    for (int i = 0; i < labs_testgroup.length(); i++) {


                        try {
                            JSONObject current = labs_testgroup.getJSONObject(i);
                            Log.d("reading", String.valueOf(i) + " " + current.getString(LABNAME));
                            if (!current.getBoolean(PUBLISH)) {
                                Log.d("publish", "false " + current.getString(LABNAME));
                                continue;
                            }
                            ResultItem tempItem = new ResultItem();
///lab Data)

                            tempItem.setLabID(current.getString(LABID));
                            tempItem.setLabName(current.getString(LABNAME));
                            tempItem.setLabEmail(current.getString(LABEMAIL));
                            tempItem.setLabPhone(current.getString(LABPHONE));
                            tempItem.setLabAdd(current.getString(LABADDRESS));
                            try {
                                tempItem.setRating(current.getDouble(RATINGAVG));
                            } catch (Exception e) {
                                tempItem.setRating(-1);
                            }

                            tempItem.setCity(current.getString(CITY));
                            homecoll = current.getBoolean(HOMECOLLEC);
                            labvisit = current.getBoolean(LABVISIT);
                            ///////////////image request//////////////////////

                            JSONObject coverImg = current.getJSONObject(COVER);
                            tempItem.setImageURL(coverImg.getString(COVERMOB));

                            imageWork(tempItem);
                            //fetches the image if not already fetched in past and stores in DB

                            /////////////////////////////////////////////////

                            JSONObject timing = current.getJSONObject(TIMING);
                            //time
                            JSONObject time = timing.getJSONObject(TIME);

                            tempItem.setTimeTo(time.getString(To));
                            tempItem.setTimeFrom(time.getString(FROM));

                            //days
                            JSONObject days = timing.getJSONObject(DAYS);
                            tempItem.setMon(days.getBoolean(MONDAY));
                            tempItem.setTue(days.getBoolean(TUESDAY));
                            tempItem.setWed(days.getBoolean(WEDNESDAY));
                            tempItem.setThu(days.getBoolean(THURSDAY));
                            tempItem.setFri(days.getBoolean(FRIDAY));
                            tempItem.setSat(days.getBoolean(SATURDAY));
                            tempItem.setSun(days.getBoolean(SUNDAY));


                            //facilities
                            JSONObject facilities = current.getJSONObject(FACILITY);
                            tempItem.setParking(facilities.getBoolean(PARKING));
                            tempItem.setAc(facilities.getBoolean(AC));
                            tempItem.setInsurance(facilities.getBoolean(INSURANCE));
                            tempItem.setCc_accept(facilities.getBoolean(CREDITCARD));
                            tempItem.setHomeCollection(facilities.getBoolean(HOMECOLLECTION));

                            tempItem.setNabl(facilities.getBoolean("nabl"));
                            Log.d("nabl", String.valueOf(tempItem.isNabl()));

                            //////////Location work///////////////////
                            try {
                                JSONObject geolocation = current.getJSONObject(GEOLOC);

                                Log.d("geoloc check", "m" + String.valueOf(geolocation));
                                Log.d("lat double check", "m" + String.valueOf(geolocation.getDouble(LATITUDE)));
                                Log.d("long double check", "m" + String.valueOf(geolocation.getDouble(LONGITUDE)));

                                Log.d("lat string check", "m" + geolocation.getString(LATITUDE));
                                Log.d("long string check", "m" + geolocation.getString(LONGITUDE));

                                tempItem.setLatLong(geolocation.getDouble(LATITUDE), geolocation.getDouble(LONGITUDE));
                            } catch (JSONException e) {
                                tempItem.setDistanceValue(Double.MAX_VALUE);
                            }
                            //////////////////////////////////////////////

                            JSONArray testgroup = current.getJSONArray("testgroups");
                            Log.d("check testgroup length", String.valueOf(testgroup.length()));
                            Double mrp = 0.0, medd = 0.0, user = 0.0, discount = 0.0;
                            StringBuilder testnam = new StringBuilder();
                            StringBuilder testi = new StringBuilder();
                            StringBuilder mrpSingle = new StringBuilder();
                            StringBuilder userSingle = new StringBuilder();
                            StringBuilder meddSingle = new StringBuilder();
                            StringBuilder meddhcoll = new StringBuilder();

                            testFound = false;
                            for (int j = 0; j < testgroup.length(); j++) {

                                JSONObject testgroupobj = testgroup.getJSONObject(j);
                                String stringefy = "\"" + testgroupobj.getString(TESTGROUPID) + "\"";

                                for (int z = 0; z < test_picked_radio.size(); z++) {
                                    if (stringefy.equals(test_picked_radio.get(z))) {
                                        Log.d("inside if", "yes");
                                        //test detail
                                        if (!testgroupobj.getBoolean(AVAILABLE)) break;
                                        user = user + testgroupobj.getDouble(PRICEUSER);
                                        medd = medd + testgroupobj.getDouble(PRICEMEDD);
                                        mrp = mrp + testgroupobj.getDouble(PRICEMRP);

                                        if (mrpSingle.length() > 0) {
                                            mrpSingle.append("~");
                                            mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                            userSingle.append("~");
                                            userSingle.append(testgroupobj.getString(PRICEUSER));
                                            meddSingle.append("~");
                                            meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                            meddhcoll.append("~");
                                            meddhcoll.append("100");
                                        } else {
                                            mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                            userSingle.append(testgroupobj.getString(PRICEUSER));
                                            meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                            meddhcoll.append("100");

                                        }
                                        if (testnam.length() > 0) {
                                            testi.append("~");
                                            testnam.append(testgroupobj.getString(TESTNAME));
                                        } else {
                                            testnam.append(testgroupobj.getString(TESTNAME));
                                        }
                                        if (testi.length() > 0) {
                                            testi.append("~");

                                            testi.append(testgroupobj.getString(TESTGROUPID));
                                        } else
                                            testi.append(testgroupobj.getString(TESTGROUPID));

                                        //     tempItem.setPriceUser(testgroupobj.getDouble(PRICEUSER));
                                        //   tempItem.setPriceMedd(testgroupobj.getDouble(PRICEMEDD));
                                        // tempItem.setPriceMrp(testgroupobj.getDouble(PRICEMRP));
                                        //tempItem.setSelectedTestName(testgroupobj.getString(TESTNAME));
                                        //tempItem.setSelectedTestID(testgroupobj.getString(TESTGROUPID));
                                        //tempItem.setSaving(testgroupobj.getDouble(PRICEMRP) - testgroupobj.getDouble(PRICEUSER));
                                        testFound = true;
                                    }

                                }


                            }
                            tempItem.setPriceUser(user);
                            tempItem.setPriceMedd(medd);
                            tempItem.setPriceUserSingle(userSingle.toString());
                            tempItem.setPriceMrpSingle(mrpSingle.toString());
                            tempItem.setPriceMeddSingle(meddSingle.toString());
                            tempItem.setPriceMrp(mrp);
                            tempItem.setSelectedTestName(testnam.toString());
                            tempItem.setSelectedTestID(testi.toString());
                            tempItem.setSaving(mrp - user);
                            tempItem.setPriceHome(meddhcoll.toString());


                            if (!testFound) continue;
                            Log.d("jsonfetched", "true");
                            if (homecoll)
                                fetchedhome.add(tempItem);
                            if (labvisit)
                                fetched.add(tempItem);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    Log.d("inappcontroller", "fetched");
                    if (CategorySearchRadio.pDialog.isShowing())
                        CategorySearchRadio.pDialog.dismiss();

                    if (fetched.isEmpty()) ClosestFragmentRadio.upd();
                    else {
                        Log.d("Dialog", "closed after success");
                        //loading list in all the 3 lists
                        ClosestFragmentRadio.loadView();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error fetch tests", String.valueOf(e));
                    if (!(CategorySearch.pDialog == null)) {
                        CategorySearch.pDialog.dismiss();
                        if (fetched.isEmpty())
                            CategorySearch.noResultView.setVisibility(View.VISIBLE);
                    }
                    if (fetched.isEmpty()) CategorySearch.noResultView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("errorlogst", error.toString());
                CategorySearch.pDialog.hide();

                CategorySearch.showAlertDialog();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //    params.put("testgroups", "[\"552687a71f0bb8121ee730f3\"]");

                Log.d("check params before req", params.get("testgroups"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;


            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));

        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);


    }

    public static void fetchdatapatho(final ArrayList<String> test_picked_patho, String citypr) throws JSONException {
        String rad;
        if (test_picked_patho.isEmpty())
            rad = "[]";
        else {
            String[] radio = test_picked_patho.toArray(new String[test_picked_patho.size()]);
            StringBuilder builder = new StringBuilder();

            for (String string : radio) {
                if (builder.length() > 0) {
                    builder.append(",");
                } else
                    builder.append("[");

                builder.append(string);
            }
            builder.append("]");
            rad = builder.toString();


        }
        //fetch data from server and store it in fetched
        imageDBhelper = new ImageDBhelper(getInstance());
        resultLength = 0;


        fetched = new ArrayList<ResultItem>();
        fetched.clear();
        fetchedhome = new ArrayList<ResultItem>();
        fetchedhome.clear();
        //"552687a71f0bb8121ee730f6";

        ////////////volley work//////////////

        // Tag used to cancel the request
        //String url1= "[\"552687a71f0bb8121ee730f3\"]";
        String temp = citypr.substring(0, 1).toLowerCase() + citypr.substring(1);
        String url = serverUrl + "/api/labs/getbytestgroups?testgroups=" + rad + "&city=" + temp + "&radiology=[]" + "&pathology=" + rad + "&publish=true";
        Log.d("shreyURL", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            Boolean testFound;
            JSONArray labs_testgroup;

            @Override
            public void onResponse(JSONObject response) {
                Log.d("volleyissues in lab", "not yet");
                Log.d("data fecthed st", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    JSONObject obj = response.getJSONObject("data");
                    Log.d("Objectwa", obj.toString());
                    labs_testgroup = obj.getJSONArray("pathology");

                    Log.d("check array length st", String.valueOf(labs_testgroup.length()));

                    //ResultItem tempItem = new ResultItem();
                    resultLength = labs_testgroup.length();
                    String jsonResponse = "check" + labs_testgroup.length();

                    for (int i = 0; i < labs_testgroup.length(); i++) {


                        try {
                            JSONObject current = labs_testgroup.getJSONObject(i);
                            Log.d("reading", String.valueOf(i) + " " + current.getString(LABNAME));
                            if (!current.getBoolean(PUBLISH)) {
                                Log.d("publish", "false " + current.getString(LABNAME));
                                continue;
                            }
                            ResultItem tempItem = new ResultItem();
///lab Data)

                            tempItem.setLabID(current.getString(LABID));
                            tempItem.setLabName(current.getString(LABNAME));
                            tempItem.setLabEmail(current.getString(LABEMAIL));
                            tempItem.setLabPhone(current.getString(LABPHONE));
                            tempItem.setLabAdd(current.getString(LABADDRESS));
                            try {
                                tempItem.setRating(current.getDouble(RATINGAVG));
                            } catch (Exception e) {
                                tempItem.setRating(-1);
                            }

                            tempItem.setCity(current.getString(CITY));
                            homecoll = current.getBoolean(HOMECOLLEC);
                            labvisit = current.getBoolean(LABVISIT);
                            ///////////////image request//////////////////////

                            JSONObject coverImg = current.getJSONObject(COVER);
                            tempItem.setImageURL(coverImg.getString(COVERMOB));

                            imageWork(tempItem);
                            //fetches the image if not already fetched in past and stores in DB

                            /////////////////////////////////////////////////

                            JSONObject timing = current.getJSONObject(TIMING);
                            //time
                            JSONObject time = timing.getJSONObject(TIME);

                            tempItem.setTimeTo(time.getString(To));
                            tempItem.setTimeFrom(time.getString(FROM));

                            //days
                            JSONObject days = timing.getJSONObject(DAYS);
                            tempItem.setMon(days.getBoolean(MONDAY));
                            tempItem.setTue(days.getBoolean(TUESDAY));
                            tempItem.setWed(days.getBoolean(WEDNESDAY));
                            tempItem.setThu(days.getBoolean(THURSDAY));
                            tempItem.setFri(days.getBoolean(FRIDAY));
                            tempItem.setSat(days.getBoolean(SATURDAY));
                            tempItem.setSun(days.getBoolean(SUNDAY));


                            //facilities
                            JSONObject facilities = current.getJSONObject(FACILITY);
                            tempItem.setParking(facilities.getBoolean(PARKING));
                            tempItem.setAc(facilities.getBoolean(AC));
                            tempItem.setInsurance(facilities.getBoolean(INSURANCE));
                            tempItem.setCc_accept(facilities.getBoolean(CREDITCARD));
                            tempItem.setHomeCollection(facilities.getBoolean(HOMECOLLECTION));

                            tempItem.setNabl(facilities.getBoolean("nabl"));
                            Log.d("nabl", String.valueOf(tempItem.isNabl()));

                            //////////Location work///////////////////
                            try {
                                JSONObject geolocation = current.getJSONObject(GEOLOC);

                                Log.d("geoloc check", "m" + String.valueOf(geolocation));
                                Log.d("lat double check", "m" + String.valueOf(geolocation.getDouble(LATITUDE)));
                                Log.d("long double check", "m" + String.valueOf(geolocation.getDouble(LONGITUDE)));

                                Log.d("lat string check", "m" + geolocation.getString(LATITUDE));
                                Log.d("long string check", "m" + geolocation.getString(LONGITUDE));

                                tempItem.setLatLong(geolocation.getDouble(LATITUDE), geolocation.getDouble(LONGITUDE));
                            } catch (JSONException e) {
                                tempItem.setDistanceValue(Double.MAX_VALUE);
                            }
                            //////////////////////////////////////////////

                            JSONArray testgroup = current.getJSONArray("testgroups");
                            Log.d("check testgroup length", String.valueOf(testgroup.length()));
                            Double mrp = 0.0, medd = 0.0, user = 0.0, discount = 0.0;
                            StringBuilder testnam = new StringBuilder();
                            StringBuilder testi = new StringBuilder();
                            StringBuilder userSingle = new StringBuilder();
                            StringBuilder mrpSingle = new StringBuilder();
                            StringBuilder meddSingle = new StringBuilder();
                            StringBuilder meddhcoll = new StringBuilder();
                            testFound = false;
                            for (int j = 0; j < testgroup.length(); j++) {

                                JSONObject testgroupobj = testgroup.getJSONObject(j);
                                String stringefy = "\"" + testgroupobj.getString(TESTGROUPID) + "\"";

                                for (int z = 0; z < test_picked_patho.size(); z++) {
                                    if (stringefy.equals(test_picked_patho.get(z))) {
                                        Log.d("inside if", "yes");
                                        //test detail
                                        if (!testgroupobj.getBoolean(AVAILABLE)) break;
                                        user = user + testgroupobj.getDouble(PRICEUSER);
                                        medd = medd + testgroupobj.getDouble(PRICEMEDD);
                                        mrp = mrp + testgroupobj.getDouble(PRICEMRP);
                                        if (mrpSingle.length() > 0) {
                                            mrpSingle.append("~");
                                            mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                            userSingle.append("~");
                                            userSingle.append(testgroupobj.getString(PRICEUSER));
                                            meddSingle.append("~");
                                            meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                            meddhcoll.append("~");
                                            meddhcoll.append("100");

                                        } else {
                                            mrpSingle.append(testgroupobj.getString(PRICEMRP));
                                            userSingle.append(testgroupobj.getString(PRICEUSER));
                                            meddSingle.append(testgroupobj.getString(PRICEMEDD));
                                            meddhcoll.append("100");

                                        }

                                        if (testnam.length() > 0) {
                                            testnam.append("~");
                                            testnam.append(testgroupobj.getString(TESTNAME));
                                        } else {
                                            testnam.append(testgroupobj.getString(TESTNAME));
                                        }
                                        if (testi.length() > 0) {
                                            testi.append("~");
                                            testi.append(testgroupobj.getString(TESTGROUPID));
                                        } else
                                            testi.append(testgroupobj.getString(TESTGROUPID));

                                        //     tempItem.setPriceUser(testgroupobj.getDouble(PRICEUSER));
                                        //   tempItem.setPriceMedd(testgroupobj.getDouble(PRICEMEDD));
                                        // tempItem.setPriceMrp(testgroupobj.getDouble(PRICEMRP));
                                        //tempItem.setSelectedTestName(testgroupobj.getString(TESTNAME));
                                        //tempItem.setSelectedTestID(testgroupobj.getString(TESTGROUPID));
                                        //tempItem.setSaving(testgroupobj.getDouble(PRICEMRP) - testgroupobj.getDouble(PRICEUSER));
                                        testFound = true;
                                    }

                                }


                            }
                            tempItem.setPriceUser(user);
                            tempItem.setPriceMedd(medd);
                            tempItem.setPriceMrp(mrp);
                            tempItem.setPriceMeddSingle(meddSingle.toString());
                            tempItem.setPriceUserSingle(userSingle.toString());
                            tempItem.setPriceMrpSingle(mrpSingle.toString());
                            tempItem.setSelectedTestName(testnam.toString());
                            tempItem.setSelectedTestID(testi.toString());
                            tempItem.setSaving(mrp - user);
                            tempItem.setPriceHome(current.getString("home_collection_price"));

                            if (!testFound) continue;
                            Log.d("jsonfetched", "true");
                            if (homecoll)
                                fetchedhome.add(tempItem);
                            if (labvisit)
                                fetched.add(tempItem);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    Log.d("inappcontroller", "fetched");
                    if (CategorySearchRadio.pDialog.isShowing())
                        CategorySearchRadio.pDialog.dismiss();

                    if (fetched.isEmpty()) {
                        ClosestFragmentPatho.upd();
                        ClosestFragmentPatho.updatev();
                    } else {
                        Log.d("Dialog", "closed after success");
                        //loading list in all the 3 lists
                        ClosestFragmentPatho.loadView();
                        ClosestFragmentPatho.updatev();
                    }
                    if (fetchedhome.isEmpty()) TopRatedPatho.upd();
                    else {
                        Log.d("Dialog", "closed after success");
                        //loading list in all the 3 lists
                        TopRatedPatho.loadView();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error fetch tests", String.valueOf(e));
                    CategorySearch.pDialog.dismiss();
                    if (fetched.isEmpty()) CategorySearch.noResultView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("errorlogst", error.toString());
                CategorySearch.pDialog.hide();

                CategorySearch.showAlertDialog();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //    params.put("testgroups", "[\"552687a71f0bb8121ee730f3\"]");

                Log.d("check params before req", params.get("testgroups"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;


            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d("time st", String.valueOf(MY_SOCKET_TIMEOUT_MS));

        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);


    }
}


