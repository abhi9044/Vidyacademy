package com.holamed.meddapp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pradeep on 10-04-2015.
 */
public class Backend extends android.app.Application {
    //////// JSON object element Names///////////
    static String  NAME="name";
    static String PLACE="place";
    static String FEATURES="features";
    static String RATINGNO="ratingNo";
    static String RATINGAVG="ratingAVG";
    static String PRICEOFFERED="priceOffered";
    static String PRICEACTUAL="priceActual";
    static String IMAGEURL="imgURL";
    static String LONGITUDE="PostionLong";
    static String LATITUDE="PostitionLat";


    /////////////////////////end/////////////////
    public static Boolean NeedUpdate=Boolean.TRUE;


    public static ArrayList<ResultItem> fetched;

    public static final String TAG = Backend.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static Backend mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized Backend getInstance() {
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

    public static void fetchdata()throws JSONException{
        //fetch data from server and store it in fetched
        fetched=new ArrayList<ResultItem>();

        ////////////volley work//////////////

        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = "http://192.168.173.1/json.php";
/*
        final ProgressDialog pDialog = new ProgressDialog(getInstance());
        pDialog.setMessage("Loading...");
        pDialog.show();
*/
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        Log.d(TAG, response.toString());
                        ///////////////Parsing//////////////////////////

                        try {
                            // Parsing json array response
                            // loop through each json object
                            //jsonResponse = "";

                            ResultItem tempItem= new ResultItem();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject item= (JSONObject) response
                                        .get(i);
/*
                                tempItem.setLab_name(item.getString(NAME));
                                tempItem.setLab_address(item.getString(PLACE));
                                tempItem.setRating(item.getString(RATINGNO));
                                tempItem.setAc(true);
                                tempItem.setHomecollection(true);
                                tempItem.setAmbulance(true);
                                tempItem.setCc_accept(true);
                                tempItem.setCost_offered(1000);
                                tempItem.setMrp(2000);
                                tempItem.setSaving(1000);
                                tempItem.setNum_rating(34);
                                fetched.add(tempItem);*/
                            }



                           // TopRatedFragment.loadView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Backend.getInstance(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                        ///////end parsing ///////////////////




                       // pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                 CategorySearch.pDialog.hide();
            }


        }){
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("place", "indore");
            params.put("latitude", "1478");
            params.put("Longitude", "14785");
            params.put("testName", "Xray");

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

// Adding request to request queue
        Backend.getInstance().addToRequestQueue(req, tag_json_arry);


        /////////////////////////////////////////////////////////////////////////////////




               //sample input
        /*ResultItem temp;

        temp= new ResultItem(R.drawable.ic_launcher, "ABC","place",9000,700,40, 3.8,0.0,0.0,"abcd\nefgh\njkl");





        fetched.add(temp);
        temp= new ResultItem(R.drawable.ic_launcher, "Patha2","place2",3000,900,60,  4.5,0,0,"abcllld\nefglkjh\njkl");
        fetched.add(temp);
        temp= new ResultItem(R.drawable.ic_launcher, "Path3","place3",8000,5000,20, (float) 2.8,0,0,"efgh\njkl");
        fetched.add(temp);
        temp=new ResultItem(R.drawable.ic_launcher, "Path4","place4",8000,2000,50, (float) 4.8,0,0,"abcd\nefgh\njkl");
        fetched.add(temp);
            */
    }

}
