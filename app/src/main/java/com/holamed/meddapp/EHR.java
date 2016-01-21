package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.holamed.meddapp.adapter.ehrAdapter;

import java.io.File;
import java.util.ArrayList;


public class EHR extends AppCompatActivity {
    private ImageView back;
    private ImageView home;
    private static ListView listView;
    private static ehrAdapter adapter;
    public static ProgressDialog pDialog;
    static Dialog alertDialog;
    private PastOrdersDB db;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    private String name;
    private String age;
    private String gender;
    private String email;
    private String phone;
    private String userId;
    private String goodDayString;
    private ImageView profileImageView;
    private String picPath;
    private static ArrayList<TransactionObject> ehrList;
    public static TextView noResultView;

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

        setContentView(R.layout.activity_ehr


        );

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        title_name.setText("History");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //hideSoftKeyboard();
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EHR.this, SecondActivityMain.class);
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
        loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        loginEditor = loginPrefs.edit();

        loadProfile();

        db = new PastOrdersDB(this);
        db.open();
        ArrayList<PastOrdersObject> pastorderslist1 = db.getAll();
        ArrayList<PastOrdersObject> pastorderslist = new ArrayList<>();
        for (int i = 0; i < pastorderslist1.size(); i++) {
            if (pastorderslist1.get(i).getChoice().equals(AppControllerSearchTests.TYPELAB))
                pastorderslist.add(pastorderslist1.get(i));
        }


        noResultView = (TextView) findViewById(R.id.no_result_e);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        alertDialog = new Dialog(EHR.this);

        //setting custom layout to dialog
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.custom_alert_dialog);

        Button one = (Button) alertDialog.findViewById(R.id.back);
        one.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                onBackPressed();
                alertDialog.dismiss();
            }
        });

        Button two = (Button) alertDialog.findViewById(R.id.try_again);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);


        ehrList = new ArrayList<TransactionObject>();

        listView = (ListView) findViewById(R.id.ehr_list);
        adapter = new ehrAdapter(this, R.layout.ehr_listrow, ehrList);
        listView.setAdapter(adapter);
        AppControllerSearchTests.fetchEHR(loginPrefs.getString("UserId", "null"));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionObject tmp = ehrList.get(position);
                Intent intent = new Intent(EHR.this, EHRDetails.class);
                //intent.putExtra("expandoption",tmp);
                //AppControllerSearchTests.setEhrtests(tmp.getTestGroupObjectArrayList());
                //AppControllerSearchTests.setTransactionObject(tmp);
                startActivity(intent);
            }
        });

    }


    public static void loadView() {

        ehrList = AppControllerSearchTests.transactionObjectArrayList;
        adapter.clear();
        adapter.addAll(ehrList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        // testsListView.invalidate();
        // testsListView.invalidateViews();
    }

    public void loadProfile() {
        boolean isSaved = loginPrefs.getBoolean("saved", false);
        name = loginPrefs.getString("name", "none");
        age = loginPrefs.getString("age", "none");
        gender = loginPrefs.getString("gender", "none");
        email = loginPrefs.getString("email", "none");
        phone = loginPrefs.getString("phone", "none");
        userId = loginPrefs.getString("userId", "none");
        picPath = loginPrefs.getString("picPath", "none");

//        LinearLayout loginNow=(LinearLayout)rootView.findViewById(R.id.loginNow);
        ScrollView loggedin = (ScrollView) findViewById(R.id.loggedin);
        LinearLayout profileLayout = (LinearLayout) findViewById(R.id.profileLayout);
        TextView nameTextView = (TextView) findViewById(R.id.profile_name);
        TextView phoneStaticTextView = (TextView) findViewById(R.id.phoneStaticText);

        TextView emailTextView = (TextView) findViewById(R.id.profile_email);
        if (isSaved) {

            //new DownloadImageTask().execute(userId);
            Bitmap bitmap = null;
            //          loginNow.setVisibility(View.GONE);
            if (!picPath.equalsIgnoreCase("none")) {
                File sd = Environment.getExternalStorageDirectory();
                /*File image = new File(sd+picPath, imageName);*/
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(picPath/*image.getAbsolutePath()*/, bmOptions);
                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                bitmap = getResizedBitmap(bitmap, 500, 500);
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                profileImageView.setImageBitmap(circleBitmap);

//                profileImageView.setImageBitmap(bitmap);
            } else {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();

                bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.defaultimage);
                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                bitmap = getResizedBitmap(bitmap, 500, 500);
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                profileImageView.setImageBitmap(circleBitmap);
            }
            if (name.equalsIgnoreCase("none")) {
                nameTextView.setVisibility(View.GONE);

            } else
                nameTextView.setText(name);
            loginPrefs = getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
            loginEditor = loginPrefs.edit();


            if (email.equalsIgnoreCase("none"))
                emailTextView.setVisibility(View.GONE);
            else
                emailTextView.setText(email);

        }
        /*else
           profileLayout.setVisibility(View.GONE);
*/
    }

    public static void showAlertDialog() {

        alertDialog.show();
    }

    public void onBackPressed() {
        Intent i = new Intent(EHR.this, SecondActivityMain.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ehr, menu);
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

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
