package com.holamed.meddapp;

import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pradeep on 10-04-2015.
 */

public class ResultItem implements Serializable {

    public ResultItem() {
        super();
    }

    private boolean homeCollectionAvailable;

    public boolean isHomeCollectionAvailable() {
        return homeCollectionAvailable;
    }

    public void setHomeCollectionAvailable(boolean homeCollectionAvailable) {
        this.homeCollectionAvailable = homeCollectionAvailable;
    }

    private boolean nabl;
    private String labID;
    private String labName;
    private String labEmail;
    private String labPhone;
    private String labAdd;
    private double rating;
    private static ArrayList<String> selectedmultites;
    private static ArrayList<String> selectedmultirad;
    private static ArrayList<String> selectedmultipat;
    private static ArrayList<String> selectedmultitesid;
    private static ArrayList<String> selectedmultiradid;
    private static ArrayList<String> selectedmultipatid;

    //private int num_rating;


    private double priceMrp;
    private double priceUser;
    private double priceMedd;
    private String priceHome;
    private String mrpSingle;
    private String userSingle;
    private String meddSingle;
    private double saving;
    private String selectedTestID;
    private String selectedTestName;


    private String timeFrom;
    private String timeTo;

    private Boolean Mon, Tue, Wed, Thu, Fri, Sat, Sun;


    private boolean ac;
    private boolean parking;
    private boolean homeCollection;
    private boolean ambulance;
    private boolean cc_accept;
    private boolean insurance;

    private String city;


    private String imageURL;
    //contacts,employees and reviews are not there till now

    private Double latitude, longitude;

    private Boolean isDistReady = false;
    private String distanceText;
    private Double distanceValue;

//    private Double selectedTestPriceMedd;

    //added by shreyans
    private String pricePerTest;

    public String getPricePerTest() {
        return pricePerTest;
    }

    public void setPricePerTest(String pricePerTest) {
        this.pricePerTest = pricePerTest;
    }

    public String getLabID() {
        return labID;
    }


    public boolean isNabl() {
        return nabl;
    }

    public void setNabl(boolean nabl) {
        this.nabl = nabl;
    }

    public void setLabID(String labID) {
        this.labID = labID;
    }

    public String getLabPhone() {
        return labPhone;
    }

    public void setLabPhone(String labPhone) {
        this.labPhone = labPhone;
    }

    public String getLabEmail() {
        return labEmail;
    }

    public void setLabEmail(String input) {
        labEmail = input;
    }


    public void setSelectedmultites(ArrayList<String> input) {
        this.selectedmultites = input;
    }

    public void setSelectedmultirad(ArrayList<String> input) {
        this.selectedmultirad = input;
    }

    public void setSelectedmultipat(ArrayList<String> input) {
        this.selectedmultipat = input;
    }


    public void setSelectedmultitesid(ArrayList<String> input) {
        this.selectedmultitesid = input;
    }

    public void setSelectedmultiradid(ArrayList<String> input) {
        this.selectedmultiradid = input;
    }

    public void setSelectedmultipatid(ArrayList<String> input) {
        this.selectedmultipatid = input;
    }

    public ArrayList getSelectedmultites() {
        return selectedmultites;
    }

    public ArrayList getSelectedmultipat() {
        return selectedmultipat;
    }

    public ArrayList getSelectedmultirad() {
        return selectedmultirad;
    }


    public ArrayList getSelectedmultitesid() {
        return selectedmultitesid;
    }

    public ArrayList getSelectedmultipatid() {
        return selectedmultipatid;
    }

    public ArrayList getSelectedmultiradid() {
        return selectedmultiradid;
    }

    public String getSelectedTestID() {
        return selectedTestID;
    }

    public void setSelectedTestID(String selectedTestID) {
        this.selectedTestID = selectedTestID;
    }

    public void setSelectedTestName(String name) {
        selectedTestName = name;
    }

    public String getSelectedTestName() {
        return selectedTestName;
    }

    public Double getSelectedTestPriceMedd() {
        return priceMedd;
    }

    public void setSelectedTestPriceMedd(Double selectedTestPriceMedd) {
        this.priceMedd = selectedTestPriceMedd;
    }

    public Double getRating() {
        return rating;
    }


    public void setRating(Double input) {
        this.rating = input;
    }

    /*public int getNum_rating() {
        return num_rating;
    }*/

   /* public void setNum_rating(int num_rating) {
        this.num_rating = num_ratiAng;
    }*/


    public double getPriceMedd() {
        return priceMedd;
    }

    public void setPriceMedd(double input) {
        priceMedd = input;
    }
    public void setPriceHome(String input) {
        priceHome = input;
    }
    public String getPriceHome() {
        return priceHome;
    }


    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public double getMrp() {
        return priceMrp;
    }

    public void setMrp(double mrp) {
        this.priceMrp = mrp;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
    }


    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String input) {
        timeFrom = input;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String input) {
        timeTo = input;
    }


    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isHomecollection() {
        return homeCollection;
    }


    public boolean isAmbulance() {
        return ambulance;
    }

    public void setAmbulance(boolean ambulance) {
        this.ambulance = ambulance;
    }

    public boolean isCc_accept() {
        return cc_accept;
    }

    public void setCc_accept(boolean cc_accept) {
        this.cc_accept = cc_accept;
    }

    public Boolean getFri() {
        return Fri;
    }

    public Boolean getMon() {
        return Mon;
    }

    public Boolean getSat() {
        return Sat;
    }

    public Boolean getSun() {
        return Sun;
    }

    public Boolean getThu() {
        return Thu;
    }

    public Boolean getTue() {
        return Tue;
    }

    public Boolean getWed() {
        return Wed;
    }

    public boolean isHomeCollection() {
        return homeCollection;
    }

    public boolean isParking() {
        return parking;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFri(Boolean fri) {
        Fri = fri;
    }

    public void setHomeCollection(boolean homeCollection) {
        this.homeCollection = homeCollection;
    }

    public void setLabAdd(String labAdd) {
        this.labAdd = labAdd;
    }

    public double getPriceMrp() {
        return priceMrp;
    }

    public void setPriceMrpSingle(String mrpSingle) {
        this.mrpSingle = mrpSingle;
    }

    public String getPriceMrpSingle() {
        return mrpSingle;
    }


    public void setPriceMeddSingle(String meddSingle) {
        this.meddSingle = meddSingle;
    }

    public String getPriceMeddSingle() {
        return meddSingle;
    }

    public void setPriceUserSingle(String userSingle) {
        this.userSingle = userSingle;
    }

    public String getPriceUserSingle() {
        return userSingle;
    }


    public double getPriceUser() {
        Log.d("Price user", "called" + Double.toString(priceUser));
        return priceUser;
    }

    public String getLabAdd() {
        return labAdd;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public void setMon(Boolean mon) {
        Mon = mon;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public void setPriceMrp(double priceMrp) {
        this.priceMrp = priceMrp;
    }

    public void setPriceUser(double priceUser) {
        this.priceUser = priceUser;
        Log.d("Price user", "called" + Double.toString(priceUser));
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setSat(Boolean sat) {
        Sat = sat;
    }

    public void setSun(Boolean sun) {
        Sun = sun;
    }

    public void setThu(Boolean thu) {
        Thu = thu;
    }

    public void setTue(Boolean tue) {
        Tue = tue;
    }

    public void setWed(Boolean wed) {
        Wed = wed;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDistanceValue(Double distanceValue) {
        this.distanceValue = distanceValue;
    }

    public Double getDistanceValue() {
        return distanceValue;
    }


    public void setLatLong(Double lat, Double longi) {
        setLatitude(lat);
        setLongitude(longi);
        if (!AppControllerSearchTests.locSet) return;
        calculateDistance();

        //////////API CALL/////////


        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + String.valueOf(AppControllerSearchTests.getLatitude()) + "," + String.valueOf(AppControllerSearchTests.getLongitude()) + "&destinations=" + String.valueOf(getLatitude() + "," + String.valueOf(getLongitude()));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String tempDistance = "";
                            Double tempDistanceValue = Double.MAX_VALUE;

                            if (response.getString("status").equals("OK")) {
                                JSONArray rows = response.getJSONArray("rows");
                                JSONObject reqsted = rows.getJSONObject(0);
                                JSONArray elements = reqsted.getJSONArray("elements");

                                if (rows.length() > 0) {
                                    JSONObject temp, distance;
                                    ////getting min distance
                                    for (int j = 0; j < elements.length(); j++) {
                                        temp = elements.getJSONObject(j);
                                        if (temp.getString("status").equals("OK")) {
                                            distance = temp.getJSONObject("distance");

                                            if (tempDistanceValue > distance.getDouble("value")) {
                                                tempDistanceValue = distance.getDouble("value");
                                                tempDistance = distance.getString("text");
                                            }

                                            distanceValue = tempDistanceValue;
                                            distanceText = tempDistance;
                                            Log.d("distance API", getLabName() + " " + String.valueOf(distanceValue));
                                            //                   ClosestFragment.loadView();


                                        }
                                    }
                                } else updateDistCountNo();


                            } else updateDistCountNo();


                        } catch
                                (Exception e) {
                            e.printStackTrace();
                            updateDistCountNo();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        updateDistCountNo();

                    }
                });

// Access the RequestQueue through your singleton class.
        AppControllerSearchTests.getInstance().addToRequestQueue(jsObjRequest);

    }

    private void calculateDistance() {
        Location selected_location = new Location("locationA");
        try {
            selected_location.setLatitude(getLatitude());
            selected_location.setLongitude(getLongitude());
            Location near_locations = new Location("locationB");
            near_locations.setLatitude(AppControllerSearchTests.getLatitude());
            near_locations.setLongitude(AppControllerSearchTests.getLongitude());

            setDistanceValue((double) selected_location.distanceTo(near_locations));
            Log.d("distance Calc", getLabName() + " " + String.valueOf(getDistanceValue()));
        } catch (Exception e) {
            e.printStackTrace();
            setDistanceValue(Double.MAX_VALUE);
        }
    }

    private void updateDistCountNo() {
        if (AppControllerSearchTests.getResultLength() <= AppControllerSearchTests.incrementDistCountNo())
            ;
        ClosestFragment.loadView();

    }

}