package com.holamed.meddapp;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class PastPatients extends AppCompatActivity {
    final static String serverURL = AppControllerSearchTests.serverUrl + "/api/v1/patients/getAll/?user_id=";
    String URL;
    HttpClient client;
    String code;
    Button bproceed;
    String gotoClass;
    ListView list;
    ProgressDialog dialog;
    private ImageView home;
    private ImageView back;
    SharedPreferences loginPrefs;
    ArrayList<String> id_list = new ArrayList<>();
    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> phone_list = new ArrayList<>();
    ArrayList<String> address_list = new ArrayList<>();

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!loginPrefs.getBoolean("LoggedIn", false)) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        try {
            Bundle bundle = getIntent().getExtras();
            gotoClass = bundle.getString("goto");

        } catch (Exception e) {
            Toast.makeText(PastPatients.this, "Error! Try Again", Toast.LENGTH_LONG).show();

        }

        loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        if (!loginPrefs.getBoolean("LoggedIn", false)) {
            Intent intent = new Intent(this, OTPReceiveActivity.class);
            if (gotoClass.equals("Registration"))
                intent.putExtra("goto", "Registration");
            else if (gotoClass.equals("RegistrationHealth"))
                intent.putExtra("goto", "RegistrationHealth");
            else if (gotoClass.equals("RegistrationEvents"))
                intent.putExtra("goto", "RegistrationEvents");


            startActivity(intent);
        }

        setContentView(R.layout.past_patients_layout);


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
        Toolbar parent = (Toolbar) customActionBarView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        list = (ListView) findViewById(R.id.list_view);
        title_name.setText("Select User");
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PastPatients.this, SecondActivityMain.class);
                startActivity(i);

            }
        });

        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PastPatients.this, SecondActivityMain.class);
                startActivity(i);
                // finish();
            }
        });

        bproceed = (Button) findViewById(R.id.bproceed);
        bproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gotoClass.equals("Registration")) {
                    AppControllerSearchTests.setPatientId(null);
                    Intent i = new Intent(PastPatients.this, Registration.class);
                    startActivity(i);
                    finish();

                } else if (gotoClass.equals("RegistrationHealth")) {
                    AppControllerSearchTests.setPatientId(null);
                    Intent i = new Intent(PastPatients.this, RegistrationHealth.class);
                    startActivity(i);
                    finish();

                } else if (gotoClass.equals("RegistrationEvents")) {
                    AppControllerSearchTests.setPatientId(null);
                    Intent i = new Intent(PastPatients.this, Registration_Events.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        client = new DefaultHttpClient();
        if (!isConnected()) {
            showAlertDialog(PastPatients.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        } else {
            new Read().execute("name");
        }


    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
 try {
     if (networkInfo != null && networkInfo.isConnected())
         return true;
     else
         return false;
 }
 catch (Exception io) {
     Toast.makeText(this, "Error Ocurred!Please try again", Toast.LENGTH_SHORT).show();
 return  false;
 }

    }

    public class Read extends AsyncTask<String, Integer, String> {

        public void onPreExecute() {


            dialog = ProgressDialog.show(PastPatients.this, "Loading", "", true);

        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
            URL = serverURL + loginPrefs.getString("UserId", "null");
            // URL=serverURL+ "5667c8f55eb25ae64bd834f0";
            StringBuilder url = new StringBuilder(URL);
            int j;

            HttpGet get = new HttpGet(url.toString());
            HttpResponse r = null;
            try {
                r = client.execute(get);

            } catch (IOException e) {
                    Toast.makeText(PastPatients.this, "Error Occured!Please try again", Toast.LENGTH_SHORT).show();
finish();
                e.printStackTrace();
            }
            int status = r.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity e = r.getEntity();
                String data = null;
                try {
                    data = EntityUtils.toString(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    JSONObject main = new JSONObject(data);
                    JSONArray main2 = main.getJSONArray("data");
                    for (int k = 0; k < main2.length(); k++) {
                        JSONArray main3 = main2.getJSONArray(k);
                        JSONObject getfirst = main3.getJSONObject(0);
                        String _id = getfirst.getString("_id");
                        String name = getfirst.getString("name");
                        String phone = getfirst.getString("phone");
                        try {
                            String address = getfirst.getString("address");
                            address_list.add(address);
                        } catch (Exception e2) {
                            address_list.add("null");
                        }
                        Log.d("pastnamewa", name);
                        id_list.add(_id);
                        name_list.add(name);
                        phone_list.add(phone);

                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


            }

            return code;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            abhishekAdapter vish = new abhishekAdapter(PastPatients.this, id_list, name_list, phone_list, address_list);
            list.setAdapter(vish);
            if (id_list.isEmpty() && (loginPrefs.getBoolean("LoggedIn", false))) {
                if (gotoClass.equals("Registration")) {
                    AppControllerSearchTests.setPatientId(null);
                    Intent i = new Intent(PastPatients.this, Registration.class);
                    startActivity(i);
                    finish();

                } else if (gotoClass.equals("RegistrationHealth")) {
                    AppControllerSearchTests.setPatientId(null);
                    Intent i = new Intent(PastPatients.this, RegistrationHealth.class);
                    startActivity(i);
                    finish();

                } else if (gotoClass.equals("RegistrationEvents")) {
                    AppControllerSearchTests.setPatientId(null);
                    Intent i = new Intent(PastPatients.this, Registration_Events.class);
                    startActivity(i);
                    finish();
                }

            }

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView tv_id = (TextView) view.findViewById(R.id.patient_id);
                    TextView tv_name = (TextView) view.findViewById(R.id.patient_name);
                    TextView tv_address = (TextView) view.findViewById(R.id.patient_address);
                    TextView tv_phone = (TextView) view.findViewById(R.id.patient_number);
                    AppControllerSearchTests.setPatientId(tv_id.getText().toString());
                    AppControllerSearchTests.setPatientName(tv_name.getText().toString());
                    AppControllerSearchTests.setPatientAddressline1(tv_address.getText().toString());
                    AppControllerSearchTests.setPatientPhone(tv_phone.getText().toString());


                    if (gotoClass.equals("Registration")) {
                        Intent newIntent = new Intent(PastPatients.this, Registration.class);
                        newIntent.putExtra("id", tv_id.getText());
                        newIntent.putExtra("name", tv_name.getText());
                        newIntent.putExtra("address", tv_address.getText());
                        newIntent.putExtra("phone", tv_phone.getText());
                        startActivity(newIntent);

                    } else if (gotoClass.equals("RegistrationHealth")) {
                        Intent newIntent = new Intent(PastPatients.this, RegistrationHealth.class);
                        newIntent.putExtra("id", tv_id.getText());
                        newIntent.putExtra("name", tv_name.getText());
                        newIntent.putExtra("address", tv_address.getText());
                        newIntent.putExtra("phone", tv_phone.getText());
                        startActivity(newIntent);


                    } else if (gotoClass.equals("RegistrationEvents")) {
                        Intent newIntent = new Intent(PastPatients.this, Registration_Events.class);
                        newIntent.putExtra("id", tv_id.getText());
                        newIntent.putExtra("name", tv_name.getText());
                        newIntent.putExtra("address", tv_address.getText());
                        newIntent.putExtra("phone", tv_phone.getText());
                        startActivity(newIntent);

                    }

                }
            });
            dialog.dismiss();


        }

    }

    class abhishekAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> idlist;
        ArrayList<String> namelist;
        ArrayList<String> phonelist;
        ArrayList<String> addresslist;

        abhishekAdapter(Context c, ArrayList<String> idgot, ArrayList<String> namegot, ArrayList<String> phonegot, ArrayList<String> address) {
            super(c, R.layout.list_past_patients, R.id.patient_name, namegot);
            this.context = c;
            this.namelist = namegot;
            this.idlist = idgot;
            this.phonelist = phonegot;
            this.addresslist = address;
            ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // TODO
            // Auto-generated
            // method
            // stub
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_past_patients, parent, false);
            TextView tv_id = (TextView) row.findViewById(R.id.patient_id);
            TextView tv_name = (TextView) row.findViewById(R.id.patient_name);
            TextView tv_address = (TextView) row.findViewById(R.id.patient_address);
            TextView tv_phone = (TextView) row.findViewById(R.id.patient_number);
            tv_id.setText(idlist.get(position));
            tv_name.setText(name_list.get(position));
            tv_address.setText(addresslist.get(position));
            tv_phone.setText(phonelist.get(position));


            return row;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiablmaeIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
