package com.holamed.meddapp;

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
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.holamed.meddapp.adapter.ehrAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HistoryTabsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static View rootView;
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    static Animation SlideDown;
    private static ListView listView;
    private static ehrAdapter adapter;
    public static ProgressDialog pDialog;
    private static SwipeRefreshLayout swipeRefreshLayout;
    static Dialog alertDialog;
    private PastOrdersDB pastOrdersDB;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;
    private String name;
    private String email;
    private ImageView profileImageView;
    private String picPath;
    private static ArrayList<TransactionObject> mtransactionObjectArrayList;
    public static TextView noResultView;
    Toast toast;

    @Override
    public void onResume() {
        super.onResume();
        if (loginPrefs.getString("UserID", null) != null) {
            pDialog.show();
        } else {
            Log.d("shreyDebug", "user not logged in");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initializing animations
        SlideDown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);

        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

        rootView = inflater.inflate(R.layout.fragment_history_tabs, container, false);
        loadProfile();

        pastOrdersDB = new PastOrdersDB(getActivity());
        pastOrdersDB.open();
        noResultView = (TextView) rootView.findViewById(R.id.no_result_e);

        //display progress dialog. dismissed inside appcontrollerSearchTests
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        //setting up alert dialog for no internet connection
        alertDialog = new Dialog(getActivity());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.custom_alert_dialog);
        Button backButton = (Button) alertDialog.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                onBackPressed();
                alertDialog.dismiss();
            }
        });
        Button retryButton = (Button) alertDialog.findViewById(R.id.try_again);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().recreate();
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mtransactionObjectArrayList = new ArrayList<TransactionObject>();
        listView = (ListView) rootView.findViewById(R.id.ehr_list);
        adapter = new ehrAdapter(getActivity(), R.layout.ehr_listrow, mtransactionObjectArrayList);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                                        if (loginPrefs.getString("UserId", null) != null) {
                                            AppControllerSearchTests.fetchEHR(loginPrefs.getString("UserId", "null"));
                                        } else {
                                            Log.d("shreyDebug", "user not logged in");
                                        }
                                    }
                                }
        );


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AppControllerSearchTests.getTransactionObject(position).getHashMap()==null) {
                    if(toast!=null)
                        toast.cancel();

                    toast = Toast.makeText(getActivity(),"Results Unavailable",Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(getActivity(), EHRDetails.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            if (!loginPrefs.getBoolean("LoggedIn", false)) {
                Intent intent = new Intent(getActivity(), OTPReceiveActivity.class);
                intent.putExtra("goto", "SecondActivityMain");
                startActivity(intent);
            } else
                Log.d("MyFragment", "Fragment is not visible.");
    }

    /*private static ArrayList<TransactionObject> sortByDate(ArrayList<TransactionObject> mtransactionObjectArrayList){
        String[] datesArray=new String[mtransactionObjectArrayList.size()];
        for(int i=0;i<mtransactionObjectArrayList.size();i++){
            datesArray[i]=mtransactionObjectArrayList.get(i).getDate();
        }
        Arrays.sort(datesArray);
        for(int i=0;i<mtransactionObjectArrayList.size();i++){
            mtransactionObjectArrayList.get(i).setDate(datesArray[i]);
        }
        return mtransactionObjectArrayList;
    }*/

    public static void loadView() {

        mtransactionObjectArrayList = AppControllerSearchTests.transactionObjectArrayList;
        if (mtransactionObjectArrayList != null)
            adapter.clear();

        Collections.sort(mtransactionObjectArrayList, new Comparator<TransactionObject>() {
            @Override
            public int compare(TransactionObject lhs, TransactionObject rhs) {
                return -lhs.getDate().compareTo(rhs.getDate());
            }
        });
        adapter.addAll(mtransactionObjectArrayList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    public static void loadNoResultsView() {
        noResultView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void loadProfile() {
        boolean isSaved = loginPrefs.getBoolean("saved", false);
        Log.d("check isSaved", String.valueOf(isSaved));
        name = loginPrefs.getString("name", "none");
        email = loginPrefs.getString("email", "none");
        picPath = loginPrefs.getString("picPath", "none");

//        LinearLayout loginNow=(LinearLayout)rootView.findViewById(R.id.loginNow);
        ScrollView loggedin = (ScrollView) rootView.findViewById(R.id.loggedin);
        LinearLayout profileLayout = (LinearLayout) rootView.findViewById(R.id.profileLayout);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment fragpro = new ProfileFragment();
                if (fragpro != null) {
                    FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                    fragmentManager2.beginTransaction()
                            .replace(R.id.frame_container, fragpro).commit();
                }
            }
        });
        TextView nameTextView = (TextView) rootView.findViewById(R.id.profile_name);

//        TextView goodDayTextView=(TextView)rootView.findViewById(R.id.profile_goodDay);
        TextView emailTextView = (TextView) rootView.findViewById(R.id.profile_email);
        profileImageView = (ImageView) rootView.findViewById(R.id.profileImage);
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

            loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);

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
        Intent i = new Intent(getActivity(), SecondActivityMain.class);
        startActivity(i);
        //finish();
        super.getActivity().onBackPressed();
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


    @Override
    public void onRefresh() {
        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        if (loginPrefs.getString("UserId", null) != null) {
            AppControllerSearchTests.fetchEHR(loginPrefs.getString("UserId", "null"));
        } else {
            Log.d("shreyDebug", "user not logged in");
        }

    }
}
