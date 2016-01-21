package com.holamed.meddapp;

import android.annotation.TargetApi;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.holamed.meddapp.adapter.ReviewsAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class Detailed_result_radio extends AppCompatActivity {
    private ImageView ac,homecollection,ambulance,cc_accept;
    private Button selectlab;
    private TextView lab_address_d,cost_d,mrp_d,saving_d,rating_d,lab_name_d,lab_phone_d,lab_email_d;
    private ResultItem selected_option;
    private ListView list;
    private ReviewsAdapter adapter;
    private ImageView back;
    private ImageView home;
    private ImageButton direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);
        TextView title_name= (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("LAB DETAILS");
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

        setContentView(R.layout.activity_detailed_result);
        home=(ImageView)findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Detailed_result_radio.this,SecondActivityMain.class);
                startActivity(i);

            }
        });
        back=(ImageView)findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Detailed_result.this, CategorySearch.class);
                //startActivity(i);
                finish();

            }
        });



        selected_option=AppControllerSearchTests.getSelectedLabRadio();
        Log.d("selected_option", String.valueOf(selected_option));



        final RelativeLayout forImage=(RelativeLayout)findViewById(R.id.lay_for_labImage);
        setBackGround(forImage, getResources().getDrawable(R.drawable.lab_image_bg));

        if(!AppControllerSearchTests.imageDBhelper.isOpen())
            AppControllerSearchTests.imageDBhelper.open();

        ImageDBElement element = AppControllerSearchTests.imageDBhelper.retriveElement(selected_option.getLabID(),AppControllerSearchTests.TYPELAB);
        AppControllerSearchTests.imageDBhelper.close();

        try {
            setBackGround(forImage, new BitmapDrawable(element.getImage()));
            Log.d("img","frm db");

        }
        catch (NullPointerException e) {
            Log.d("Image", "not from db");
            e.printStackTrace();

            ImageRequest request = new ImageRequest(selected_option.getImageURL(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                            setBackGround(forImage, new BitmapDrawable(bitmap));
                            if(!AppControllerSearchTests.imageDBhelper.isOpen())
                                AppControllerSearchTests.imageDBhelper.open();
                            try {
                                AppControllerSearchTests.imageDBhelper.insertElement(new ImageDBElement(bitmap, selected_option.getLabName(), selected_option.getLabID(),AppControllerSearchTests.TYPELAB));

                                AppControllerSearchTests.imageDBhelper.close();
                            } catch (Exception e) {
                                if(AppControllerSearchTests.imageDBhelper.isOpen())
                                    AppControllerSearchTests.imageDBhelper.close();
                                e.printStackTrace();
                            }

                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            // mImageView.setImageResource(R.drawable.image_load_error);
                            error.printStackTrace();
                        }
                    });
            // Access the RequestQueue through your singleton class.
            AppControllerSearchTests.getInstance().addToRequestQueue(request);
        }


        //  AppControllerSearchTests.imageDBhelper.close();

        /////////////////////////////////////////////////
        lab_name_d = (TextView)findViewById(R.id.lab_name_d);
        lab_address_d= (TextView)findViewById(R.id.lab_address_d);
        lab_phone_d=(TextView)findViewById(R.id.lab_phone_d);
        lab_email_d=(TextView)findViewById(R.id.lab_email_d);
        mrp_d=(TextView)findViewById(R.id.mrp_d);
        cost_d=(TextView)findViewById(R.id.cost_d);
        saving_d=(TextView)findViewById(R.id.saving_d);
        rating_d=(TextView)findViewById(R.id.rating_d);
        ac=(ImageView)findViewById(R.id.ac_boolean_icon);
        ambulance=(ImageView)findViewById(R.id.ambulance_boolean_icon);
        homecollection=(ImageView)findViewById(R.id.homecollection_boolean_icon);
        cc_accept=(ImageView)findViewById(R.id.cc_accept_boolean_icon);
        selectlab=(Button)findViewById(R.id.bookLab);
        //  direction=(ImageButton)findViewById(R.id.direction);


        //selected_option=(ResultItem)getIntent().getSerializableExtra("expandoption");


        lab_name_d.setText(selected_option.getLabName());
        lab_address_d.setText(selected_option.getLabAdd());
        cost_d.setText(this.getString(R.string.Rs)+String.valueOf(selected_option.getPriceUser()));
        saving_d.setText(this.getString(R.string.Rs)+String.valueOf(selected_option.getSaving()));
        mrp_d.setText(String.valueOf(selected_option.getMrp())+")");
        mrp_d.setPaintFlags(mrp_d.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        rating_d.setText(String.valueOf(selected_option.getRating()));
        lab_phone_d.setText(selected_option.getLabPhone());
        lab_email_d.setText(selected_option.getLabEmail());
        if(selected_option.isAc())
            ac.setImageResource(R.drawable.greentick_24);
        else
            ac.setImageResource(R.drawable.orange_multiply_filled_25);
        if(selected_option.isParking())
            ambulance.setImageResource(R.drawable.greentick_24);
        else
            ambulance.setImageResource(R.drawable.orange_multiply_filled_25);

        if(selected_option.isCc_accept())
            cc_accept.setImageResource(R.drawable.greentick_24);
        else
            cc_accept.setImageResource(R.drawable.orange_multiply_filled_25);
        if(selected_option.isHomecollection())
            homecollection.setImageResource(R.drawable.greentick_24);
        else
            homecollection.setImageResource(R.drawable.orange_multiply_filled_25);

        list=(ListView)findViewById(R.id.list_reviews);
        // Getting adapter by passing xml data ArrayList
        ArrayList<HashMap<String, String>> accountsListToDisplay = new ArrayList<HashMap<String, String>>();
        ArrayList<PersonForm> accountslist=new ArrayList<PersonForm>();
        final String RsSymbol=this.getString(R.string.Rs);
        adapter=new ReviewsAdapter(this, accountsListToDisplay);
        list.setAdapter(adapter);


    /*    direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriparse;
                try {

                    uriparse = "http://maps.google.com/maps?daddr=" + String.valueOf(selected_option.getLatitude()) + "," + String.valueOf(selected_option.getLongitude());

                } catch (NullPointerException e) {

                    e.printStackTrace();
                    try {
                        uriparse = "http://maps.google.com/maps?daddr=" + String.valueOf(selected_option.getLabAdd());
                    }
                    catch (Exception e2){
                        e2.printStackTrace();
                        return;
                    }
                }


                Log.d("parseuri",uriparse);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uriparse));
                startActivity(intent);
            }
        });*/

        final Button booktestPressed = (Button) findViewById(R.id.bookhealth);

        //on touch listener to change color of button
        boolean check=false;
        selectlab.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    selectlab.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    selectlab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });


        selectlab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      Intent i=new Intent(Detailed_result_radio.this,Registration.class);
                finish();
                //i.putExtra("tests","MRI Brain");
                //i.putExtra("labName",lab_name_d.getText().toString());
                //i.putExtra("labAdd",lab_address_d.getText().toString());
                //  AppControllerSearchTests.imageDBhelper.close();

      //          AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
        //        AppControllerSearchTests.setSelectedLabRadio(selected_option);


                /*
                AppControllerSearchTests.setLabPhone(selected_option.getLabPhone());
                Log.d("check labPhn", selected_option.getLabPhone());

                AppControllerSearchTests.setLabId(selected_option.getLabID());
                Log.d("check labId", selected_option.getLabID());

                AppControllerSearchTests.setPriceMedd(String.valueOf(selected_option.getSelectedTestPriceMedd()));
                Log.d("check priceMedd", String.valueOf(selected_option.getSelectedTestPriceMedd()));

                AppControllerSearchTests.setPriceMrp(String.valueOf(selected_option.getMrp()));
                Log.d("check Mrp", String.valueOf(selected_option.getMrp()));

                AppControllerSearchTests.setPriceUser(String.valueOf(selected_option.getPriceUser()));
                Log.d("check UserPrice", String.valueOf(selected_option.getPriceUser()));
                AppControllerSearchTests.setTestsID(selected_option.getSelectedTestID());
                //Log.d("check Tests", String.valueOf(selected_option.getCost_offered()));
                AppControllerSearchTests.setTestsName(selected_option.getSelectedTestName());
                AppControllerSearchTests.setLabAdd(selected_option.getLabAdd());
                AppControllerSearchTests.setLabName(selected_option.getLabName());
                //i.putExtra("priceUser",RsSymbol+String.valueOf(selected_option.getCost_offered()));
                //i.putExtra("labID",selected_option.getLabID());
                //i.putExtra("priceMedd",selected_option.getCost_offered());
                //i.putExtra("priceMrp",Double.toString(selected_option.getMrp()));
                //i.putExtra("labPhone",selected_option.getLabPhone());
                */
              //  startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_result, menu);
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


    private void setBackGround(View v,Drawable d){
        if (android.os.Build.VERSION.SDK_INT >= 16){
            setBackgroundV16Plus(v, d);
        }
        else{
            setBackgroundV16Minus(v, d);
        }

    }
    @TargetApi(16)
    private void setBackgroundV16Plus(View view, Drawable d) {
        view.setBackground(d);

    }

    @SuppressWarnings("deprecation")
    private void setBackgroundV16Minus(View view, Drawable d)  {
        view.setBackgroundDrawable(d);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
