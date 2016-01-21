package com.holamed.meddapp;

import android.accounts.AccountManager;
import android.support.v7.app.ActionBar;
import android.app.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import java.util.jar.Attributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;


public class Registration extends AppCompatActivity implements OnItemSelectedListener {
    private static final String TAG = "Email list";
    private static final int REQUEST_CODE_EMAIL = 1;

    private MixpanelAPI mixpanel;
    private String projectToken = "39504217e737bb956ceb8c20ca0a34ee";
    private EditText NameEditText;
    private EditText emailEditText;
    private EditText refferalText;
    private EditText mobileEditText;
    private TextView addnote;
    String filepath;

    String signal;
    String namegot="none";
    String phonegot="none";
    String addressgot="none";
    private EditText ageEditText;
    private EditText line1EditText;
    private EditText line2EditText;
    private EditText pincodeEditText;
    private RadioGroup radioGroup;
    private PersonDB personDB;
    private String gender = "male";
    private Bundle bundleToRegistration;
    private boolean addressswitchstatus;
    private Switch addressSwitch;
    private SharedPreferences preferences;
    private PersonForm newUser;
    private int address_flag;
    private ImageView back;
    private ImageView home;

    private LinearLayout homecollectionset;
    static ProgressDialog pDialog;
    static Dialog alertDialog;
    private PersonForm person;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    private boolean loggedinUser;
    private int switchflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        final SharedPreferences settings = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);//PreferenceManager.getDefaultSharedPreferences(this);
        loginEditor = settings.edit();
        //loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_registration);
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration.this, SecondActivityMain.class);
                startActivity(i);

            }
        });
        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i=new Intent(Registration.this,Detailed_result.class);
                //startActivity(i);
                finish();
            }
        });


        try {
            Intent i = getIntent();
         namegot=i.getStringExtra("name");
            phonegot=i.getStringExtra("phone");
            addressgot=i.getStringExtra("address");
        } catch (Exception e) {

            namegot="none";
            phonegot="none";
            addressgot="none";

        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bundleToRegistration = getIntent().getExtras();
        mixpanel = MixpanelAPI.getInstance(this, projectToken);

        personDB = new PersonDB(this);
        personDB.open();
        person = personDB.getPhoneUser();
        Log.d("person", String.valueOf(person));
       /* Log.d("person",person.getName());
        Log.d("person",person.getEmail());
        Log.d("person",person.getAddress());
        Log.d("person", String.valueOf(person.getAge()));
        Log.d("person", String.valueOf(person.getPhone()));*/
//        Log.d("person",person.getPincode().toString());
       addnote = (TextView) findViewById(R.id.addnote);
        NameEditText = (EditText) findViewById(R.id.firstName_field);
        emailEditText = (EditText) findViewById(R.id.email_field);
        mobileEditText = (EditText) findViewById(R.id.mobile_field);
        ageEditText = (EditText) findViewById(R.id.age_R);
        line1EditText = (EditText) findViewById(R.id.line1);
        line2EditText = (EditText) findViewById(R.id.line2);
        pincodeEditText = (EditText) findViewById(R.id.pincode);
        refferalText = (EditText) findViewById(R.id.refferal);
try {
    if (!(namegot.equals("none")))
    {
        NameEditText.setText(namegot);
        NameEditText.setKeyListener(null);
    }
        if (!(phonegot.equals("none")))
        mobileEditText.setText(phonegot);
    if (!(addressgot.equals("none")))
        line1EditText.setText(addressgot);
}
catch (Exception e)
{
   e.printStackTrace();
}
        //     homecollectionset=(LinearLayout)findViewById(R.id.homecollectionset);
        if (AppControllerSearchTests.getSearchType().equals(AppControllerSearchTests.TYPELAB)) {
            if (!AppControllerSearchTests.getHomecollection())
            //      homecollectionset.setVisibility(View.GONE);
            {
                line1EditText.setVisibility(View.GONE);
                line2EditText.setVisibility(View.GONE);
                pincodeEditText.setVisibility(View.GONE);
                addnote.setVisibility(View.GONE);


            }

        } else {
            if (!AppControllerSearchTests.getHomedelivery()) {
            }
//                homecollectionset.setVisibility(View.GONE);
            else {
                //         TextView hdreq=(TextView)findViewById(R.id.hcreq);
                //       hdreq.setText("Home Delivery");
                //     TextView a=(TextView)findViewById(R.id.moneyforhc);
                //   a.setText("Free Home delivery of medicines");
            }
        }
        if (AppControllerSearchTests.getSearchType().equals(AppControllerSearchTests.TYPEPHARMACY))

        {
            //TextView hdreq=(TextView)findViewById(R.id.hcreq);
            // hdreq.setText("Home Delivery");
            //TextView a=(TextView)findViewById(R.id.moneyforhc);
            //a.setText("Free Home delivery of medicines");
        }
        if (AppControllerSearchTests.getSearchType().equals(AppControllerSearchTests.TYPEEVENT)) {
            //homecollectionset.setVisibility(View.GONE);
        }
        newUser = new PersonForm();
        //personDB = new PersonDB(this, null, null, 1);
        personDB = new PersonDB(this);


        //bundleToRegistration=getIntent().getExtras();


//        addressSwitch=(Switch) findViewById(R.id.addresschoice);

        radioGroup = (RadioGroup) findViewById(R.id.gender);
        radioGroup.check(R.id.male);
        boolean isSaved = settings.getBoolean("saved", false);
/*
        if (isSaved) {
            loggedinUser = true;
            if (checkbox.isChecked()) {
                NameEditText.setText(settings.getString("name", "none"));
                emailEditText.setText(settings.getString("email", ""));
                ageEditText.setText(settings.getString("age", ""));
                mobileEditText.setText(settings.getString("phone", ""));

                line1EditText.setText(settings.getString("address", ""));
                pincodeEditText.setText(settings.getString("pincode", ""));
                gender = settings.getString("gender", "");
                if (gender.equalsIgnoreCase("male")) {
                    radioGroup.check(R.id.male);

                } else {
                    radioGroup.check(R.id.female);
                }
            }
//        Log.d("personr", String.valueOf(person.getAge()));

            try {
                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            loggedinUser = true;
                            NameEditText.setText(settings.getString("name", ""));
                            emailEditText.setText(settings.getString("email", ""));
                            line1EditText.setText(settings.getString("address", ""));
                            pincodeEditText.setText(settings.getString("pincode", ""));
                            ageEditText.setText(settings.getString("age", ""));
                            mobileEditText.setText(settings.getString("phone", ""));
                            gender = settings.getString("gender", "");
                            if (gender.equalsIgnoreCase("male")) {
                                radioGroup.check(R.id.male);

                            } else {
                                radioGroup.check(R.id.female);
                            }
                            if (gender.equalsIgnoreCase("male")) {
                                radioGroup.check(R.id.male);

                            } else {
                                radioGroup.check(R.id.female);
                            }


                        } else {
                            loggedinUser = false;
                            NameEditText.setText("");
                            emailEditText.setText("");
                            line1EditText.setText("");
                            mobileEditText.setText("");
                            pincodeEditText.setText("");
                            ageEditText.setText("");
                            mobileEditText.setText("");
                            radioGroup.check(R.id.male);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkbox.setVisibility(View.GONE);
            loggedinUser = false;
        }
*/
        //      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        final SharedPreferences.Editor editor = preferences.edit();

        address_flag = 2;


        NameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_fname = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    NameEditText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    //TextKeyListener.clear((firstName).getText());
                    flag_fname = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_fname == 2) {
                        Log.d("has no focus", "flag 2");

                        NameEditText.setBackgroundResource(R.drawable.edittextborder);
                        if (NameEditText.getText().toString().length() == 0 || !isValidName(NameEditText.getText().toString().trim())) {
                            NameEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            NameEditText.setError("First name should only contain characters spaces and '.'");

                        } else {
                            NameEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkmark_24, 0);

                        }

                    }

                }

            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_lname = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());
                    emailEditText.setBackgroundResource(R.drawable.edittextborderonfocus);

                    flag_lname = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_lname == 2) {

                        emailEditText.setBackgroundResource(R.drawable.edittextborder);
                        Log.d("has no focus", "flag 2");
                        if (emailEditText.getText().toString().length() == 0 || !isValidMail(emailEditText.getText().toString().trim())) {
                            emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            emailEditText.setError("Invalid email. Ex: 'abc@xyz.com'");

                        } else {
                            emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkmark_24, 0);

                        }

                    }

                }

            }
        });
        mobileEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_phone = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());

                    mobileEditText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    flag_phone = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_phone == 2) {

                        mobileEditText.setBackgroundResource(R.drawable.edittextborder);
                        Log.d("has no focus", "flag 2");
                        if (mobileEditText.getText().toString().length() == 0 || !isValidPhoneNumber(mobileEditText.getText().toString().trim())) {
                            mobileEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            mobileEditText.setError("Must be a 10 digit long number");
                        } else {
                            mobileEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkmark_24, 0);

                        }

                    }

                }

            }
        });
        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_phone = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());

                    ageEditText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    flag_phone = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_phone == 2) {

                        ageEditText.setBackgroundResource(R.drawable.edittextborder);
                        Log.d("has no focus", "flag 2");
                        if (ageEditText.getText().toString().length() == 0 || !isValidAge(ageEditText.getText().toString().trim())) {
                            ageEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            ageEditText.setError("Must be a number");
                        } else {
                            ageEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkmark_24, 0);

                        }

                    }

                }

            }
        });
        line1EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_phone = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());

                    line1EditText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    flag_phone = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_phone == 2) {
                        if (line1EditText.getText().toString().trim().length() == 0) {
                            line1EditText.setBackgroundResource(R.drawable.edittextborder);
                            Log.d("has no focus", "flag 2");
                            line1EditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            line1EditText.setError("It cannot be empty");
                        } else {
                            ageEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkmark_24, 0);

                        }
                    }

                }

            }
        });

        line2EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_phone = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());

                    line2EditText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    flag_phone = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_phone == 2) {

                        line2EditText.setBackgroundResource(R.drawable.edittextborder);
                        Log.d("has no focus", "flag 2");


                    }

                }

            }
        });

        pincodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_phone = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());

                    pincodeEditText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    flag_phone = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_phone == 2) {

                        pincodeEditText.setBackgroundResource(R.drawable.edittextborder);
                        Log.d("has no focus", "flag 2");
                        pincodeEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkmark_24, 0);


                    }

                }
            }
        });

        refferalText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            int flag_phone = 1;

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    //TextKeyListener.clear((firstName).getText());

                    refferalText.setBackgroundResource(R.drawable.edittextborderonfocus);
                    flag_phone = 2;
                    Log.d("has focus", "flag 2");

                } else {
                    if (flag_phone == 2) {

                        refferalText.setBackgroundResource(R.drawable.edittextborder);
                        Log.d("has no focus", "flag 2");


                    }

                }

            }
        });


        Log.d("uidcheck", String.valueOf(AppControllerSearchTests.getUserId()));
        String uid = settings.getString("user_id", null);
        personDB.open();
        PersonForm person1 = personDB.getPhoneUser();

//        Log.d("uidcheck1",uid);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.female) {
                    gender = "female";
                } else if (checkedId == R.id.male) {
                    gender = "male";
                }

            }
        });
        /*addressSwitch.setChecked(false);
        if(AppControllerSearchTests.getSearchType().equals(AppControllerSearchTests.TYPEPHARMACY))
        {   Log.d("issue1","checked");
            addressSwitch.setChecked(true);
            addressSwitch.setEnabled(false);
            addressswitchstatus=true;
            line1EditText.setVisibility(View.VISIBLE);
            line2EditText.setVisibility(View.VISIBLE);
            pincodeEditText.setVisibility(View.VISIBLE);

        }
        */
        //attach a listener to check for changes in state
     /*
        addressSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                  addressswitchstatus=true;
                    line1EditText.setVisibility(View.VISIBLE);
                    line2EditText.setVisibility(View.VISIBLE);
                    pincodeEditText.setVisibility(View.VISIBLE);
                }else{

                    line1EditText.setVisibility(View.INVISIBLE);
                    line2EditText.setVisibility(View.INVISIBLE);
                    pincodeEditText.setVisibility(View.INVISIBLE);
                    addressswitchstatus=false;
                 }

            }
        });
*/
        final Button Register = (Button) findViewById(R.id.confirmation);


        //on touch listener to change color of button
        boolean check = false;
        Register.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    Register.setBackgroundColor(getResources().getColor(R.color.primary));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    Register.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = getIntent();

                try {
                    signal = i.getStringExtra("is");
                    filepath = i.getStringExtra("filePath");
                    isImage = i.getBooleanExtra("isImage", true);
                    Log.d("phase2", filepath);
                } catch (Exception e) {

                    signal = "no prescription";

                }
              */  NameEditText = (EditText) findViewById(R.id.firstName_field);
                emailEditText = (EditText) findViewById(R.id.email_field);
                mobileEditText = (EditText) findViewById(R.id.mobile_field);
                if (NameEditText.getText().toString().length() == 0) {
                    NameEditText.setError("Please Fill your first name");
                } else if (!isValidName(NameEditText.getText().toString().trim())) {
                    NameEditText.setError("First name should only contain characters spaces and '.'");
                } else if (!isValidMail(emailEditText.getText().toString().trim())) {
                    emailEditText.setError("Invalid email. Ex: 'abc@xyz.com' ");
                } else if (!isValidPhoneNumber(mobileEditText.getText().toString().trim())) {
                    mobileEditText.setError("Invalid mobile. Should be 10 digits long. Ex:1234567890");
                } else if (ageEditText.getText().toString().trim().length() == 0) {
                    ageEditText.setError("Invalid Age.Should be a number");
                } else if (!isValidAge(ageEditText.getText().toString())) {
                    ageEditText.setError("Invalid Age.Should be a number");
                } else if (!addressCheck()) {

                } else {
                    newUser.setName(NameEditText.getText().toString().trim());
                    newUser.setEmail(emailEditText.getText().toString().trim());
                    newUser.setPhone(Long.parseLong(mobileEditText.getText().toString().trim()));
                    newUser.setAge(Integer.parseInt(ageEditText.getText().toString().trim()));
                    if (line1EditText.getText().toString().length() != 0) {
                        if (line2EditText.getText().toString().length() != 0) {
                            newUser.setAddress(line1EditText.getText().toString() + " " + line2EditText.getText().toString() + " " + pincodeEditText.getText().toString());
                            AppControllerSearchTests.setPatientAddressline1(line1EditText.getText().toString());
                            AppControllerSearchTests.setPatientAddressline2(line2EditText.getText().toString());
                            AppControllerSearchTests.setPatientAddresspincode(pincodeEditText.getText().toString());
                            Log.d("address is", newUser.getAddress());

                        } else {
                            newUser.setAddress(line1EditText.getText().toString() + " " + pincodeEditText.getText().toString());
                            AppControllerSearchTests.setPatientAddressline1(line1EditText.getText().toString());
                            AppControllerSearchTests.setPatientAddresspincode(pincodeEditText.getText().toString());

                        }
                    }
                    newUser.setGender(gender);
                    String uid = settings.getString("user_id", "fuck");

                    if (person == null)

                    {
                        //Log.d("uidcheck",AppControllerSearchTests.getUserId());
                        Log.d("uidcheck1", uid);
                        newUser.setUser_id(uid);
                        personDB.open();
                        personDB.addPerson(newUser);
                    }
                        AppControllerSearchTests.setUid(null);

                  /*  if (loggedinUser) {

                        loginEditor.putString("name", NameEditText.getText().toString());
                        loginEditor.putString("phone", mobileEditText.getText().toString());
                        if (line2EditText.getText().toString() != null || line2EditText.getText().toString().length() != 0)
                            loginEditor.putString("address", line1EditText.getText().toString() + " " + line2EditText.getText().toString());
                        else
                            loginEditor.putString("address", line1EditText.getText().toString());

                        loginEditor.putString("pincode", pincodeEditText.getText().toString());
                        loginEditor.putString("age", ageEditText.getText().toString());
                        loginEditor.putString("email", emailEditText.getText().toString());
                        Log.d("getAge", settings.getString("age", "none"));
                        loginEditor.commit();
                        Log.d("getAge", settings.getString("age", "none"));

                    }*/
                    JSONObject props = new JSONObject();
                    try {
                        props.put("User email", AppControllerSearchTests.getCurrentEmail());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mixpanel.track("Registration Event", props);

/*
                    if (signal.equals("phar")) {
                      */
/*  Intent upload = new Intent(Registration.this, UploadActivity.class);
                        upload.putExtra("name", NameEditText.getText().toString());
                        upload.putExtra("age", ageEditText.getText().toString());
                        upload.putExtra("email", emailEditText.getText().toString());
                        upload.putExtra("mobile", mobileEditText.getText().toString());
                        upload.putExtra("gender", gender);
                        upload.putExtra("line1", line1EditText.getText().toString());
                        upload.putExtra("line2", line2EditText.getText().toString());
                        upload.putExtra("pin", pincodeEditText.getText().toString());
                        upload.putExtra("filepath", filepath);
                        upload.putExtra("isImage", isImage);
                        startActivity(upload);
                        finish();*/
                        Intent intent;
                        ResultItem labpat = AppControllerSearchTests.getSelectedLabPatho();
                        ResultItem labrad = AppControllerSearchTests.getSelectedLabRadio();
                        if ((labpat != null) || ((labrad != null))) {
                            intent = new Intent(v.getContext(), ConfirmationRadpath.class);
                        } else {
                            intent = new Intent(v.getContext(), Confirmation.class);
                        }
                        AppControllerSearchTests.setPatientName(NameEditText.getText().toString());
                        AppControllerSearchTests.setPatientAge(ageEditText.getText().toString());
                        AppControllerSearchTests.setPatientEmail(emailEditText.getText().toString());
                        AppControllerSearchTests.setPatientPhone(mobileEditText.getText().toString());
                        AppControllerSearchTests.setPatientGender(gender);
                        if (refferalText.getText().toString().length() > 0)
                            AppControllerSearchTests.setRefferalCode(refferalText.getText().toString());
                        else
                            AppControllerSearchTests.setRefferalCode("none");

                        AppControllerSearchTests.setPatientAddressline1(line1EditText.getText().toString());
                        AppControllerSearchTests.setPatientAddressline2(line2EditText.getText().toString());
                        AppControllerSearchTests.setPatientAddresspincode(pincodeEditText.getText().toString());

                        startActivity(intent);
                    }


                }


        });

        Intent newi = getIntent();
        String s = newi.getStringExtra("is");
        try {
            if (s.equals("phar")) {
                homecollectionset.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            // email.setText(accountName);
        }
    }

    private boolean isValidAge(String age) {
        Pattern pattern;
        Matcher matcher;
        String number_regex = "^[0-9]*$";
        pattern = Pattern.compile(number_regex);
        matcher = pattern.matcher(age);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern;
        Matcher matcher;
        String number_regex = "^[0-9]*$";
        pattern = Pattern.compile(number_regex);
        matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            if (phoneNumber.length() == 10) {
                if (phoneNumber.charAt(0) != '0')

                    return true;
            }
        }
        return false;
    }

    private boolean isValidName(String name) {
        Pattern pattern;
        Matcher matcher;

        String name_regex = "^[\\p{L} .'-]+$";

        pattern = Pattern.compile(name_regex);

        matcher = pattern.matcher(name);


        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private boolean isValidMail(String email) {
        Pattern pattern;
        Matcher matcher;

        String email_regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(email_regex);

        matcher = pattern.matcher(email);


        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        mixpanel.flush();
        super.onDestroy();
    }

    private boolean addressCheck() {
        if (addressswitchstatus) {
            Log.d("checkingaddressswitch", "yes");
            if (line1EditText.getText().toString().trim().length() == 0) {
                line1EditText.setError("Address cannot empty for home pick up");
                return false;
            }


        }
        return true;
    }

    public static void showAlertDialog() {

        alertDialog.show();
    }
}