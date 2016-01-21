package com.holamed.meddapp;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Era on 6/9/2015.
 */
public class DealsItem extends Application {
    String labsnames;
    String labsids;
    String lati;
    String longi;
    String deal_desc;
    int deal_total;
    int deal_medd;
    int deal_list;
    String test_id;
    String lab_id;
    String deal_id;
    Boolean homecoll = false;
    Boolean labvisit = false;
    String test_name;
    String lab_name;
    String deal_name;
    String type;
    String id;
    Boolean toShow = false;
    public ResultItem details;
    public Boolean fetchedMoreDetails = false;
    String main_lab;
    String num_test;
String addresslab;
    public String getAddress_lab() {
        return addresslab;
    }

    public void setAddressLab(String addresslab) {
        this.addresslab = addresslab;
    }
    public void setHealthLati(String lati) {
        this.lati = lati;
    }
    public void setHealthLongi(String longi) {
        this.longi = longi;
    }
    public String getHealthLongi() {
        return longi;
    }
    public String getHealthLati() {
        return lati;
    }
    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getNum_test() {
        return num_test;
    }

    public void setNum_Test(String num_test) {
        this.num_test = num_test;
    }

    public void setLab_main(String main_lab) {
        this.main_lab = main_lab;
    }

    public String getLab_main() {
        return main_lab;
    }

    public void setLabsnames(String lab_name) {
        this.labsnames = lab_name;
    }

    public String getLabsnames() {
        return labsnames;
    }

    public void setLabIds(String lab_id) {
        this.labsids = lab_id;
    }

    public String getLabsIds() {
        return labsids;
    }

    public String getLab_id() {
        return lab_id;
    }

    public Integer getDeal_medd() {
        return deal_medd;
    }

    public Integer getDeal_list() {
        return deal_list;
    }


    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }


    public String getDeal_desc() {
        return deal_desc;
    }

    public void setDeal_desc(String deal_desc) {
        this.deal_desc = deal_desc;
    }

    public Integer getDeal_total() {
        return deal_total;
    }

    public void setDeal_total(Integer deal_total) {
        this.deal_total = deal_total;
    }

    public void setDeal_medd(Integer deal_medd) {
        this.deal_medd = deal_medd;
    }

    public void setDeal_list(Integer deal_list) {
        this.deal_list = deal_list;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setHomecoll(Boolean homecoll) {
        this.homecoll = homecoll;
    }


    public Boolean getHomecoll() {
        return homecoll;
    }


    public Boolean getLabvisit() {
        return labvisit;
    }


    public void setLabVisit(Boolean labvisit) {
        this.labvisit = labvisit;
    }


    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResultItem getDetails() {
        return details;
    }

    public void setDetails(ResultItem details) {
        this.details = details;
    }

    public void fetchMoreDetails() {
        Log.d("Indetails", "indetails");
        final String url = AppControllerSearchTests.serverUrl + "/api/labs/getbyid?id=" + this.getLab_id();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("volleyissues", url);
                Log.d("data fecthed", response.toString());

                try {
                    ResultItem detail = AppControllerSearchTests.responseToResulltItem(response, getTest_id());
                    if (detail != null) toShow = true;
                    setDetails(detail);
                    Log.d("deals item", "home collection " + detail.isHomecollection());
                    //AppControllerSearchTests.setHomecollection(detail.isHomecollection());
                    //AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);

                    AppControllerSearchTests.incrementDealsCount();
                    //AppControllerSearchTests.setSelectedLab(detail);
                    //  if(pDialog.isShowing())pDialog.dismiss();
                    //recreate();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("error fetch deal", String.valueOf(e));

                    AppControllerSearchTests.incrementDealsCount();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Dealfetch", "Error: " + error.getMessage());
                // if(pDialog.isShowing())pDialog.dismiss();
                // showAlertDialog();
                AppControllerSearchTests.incrementDealsCount();

            }


        });
        Log.d("Indee", this.getLab_id());
        AppControllerSearchTests.getInstance().addToRequestQueue(jsonObjReq);
    }
}
