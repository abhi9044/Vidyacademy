package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.holamed.meddapp.adapter.FaqAdapter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;


public class ProfileFragment extends Fragment {
    // private ArrayList<FaqObject> faqobjlist;
    public ProfileFragment() {
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


        //intializing animaion

        LayoutInflater inflater1 = (LayoutInflater) ((AppCompatActivity)getActivity()).getSupportActionBar()
                .getThemedContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater1.inflate(R.layout.actionbar_basic, null);
        AppControllerSearchTests.setSearchType(AppControllerSearchTests.TYPELAB);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
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

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.fragmentName);
        title_name.setText("Profile");

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button ok = (Button) rootView.findViewById(R.id.bmobile);


        loginPrefs = getActivity().getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        loginEditor = loginPrefs.edit();
        boolean isSaved = loginPrefs.getBoolean("saved", false);
        name = loginPrefs.getString("name", "none");
        age = loginPrefs.getString("age", "none");
        gender = loginPrefs.getString("gender", "none");
        email = loginPrefs.getString("email", "none");
        phone = loginPrefs.getString("phone", "none");
        userId = loginPrefs.getString("userId", "none");
        picPath = loginPrefs.getString("picPath", "none");
        Log.d("userId CHECK", String.valueOf(userId));
//        LinearLayout loginNow=(LinearLayout)rootView.findViewById(R.id.loginNow);
        ScrollView loggedin = (ScrollView) rootView.findViewById(R.id.loggedin);
        final EditText nameTextView = (EditText) rootView.findViewById(R.id.profile_name);
        TextView phoneStaticTextView = (TextView) rootView.findViewById(R.id.phoneStaticText);
        TextView goodDayTextView = (TextView) rootView.findViewById(R.id.profile_goodDay);
        TextView genderTextView = (TextView) rootView.findViewById(R.id.profile_gender);
        final EditText emailTextView = (EditText) rootView.findViewById(R.id.profile_email);
        TextView ageTextView = (TextView) rootView.findViewById(R.id.profile_age);
        final EditText phoneEditText = (EditText) rootView.findViewById(R.id.profile_phone);
        profileImageView = (ImageView) rootView.findViewById(R.id.profileImage);

       /* Log.d("bitmap", String.valueOf(bitmap));
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);


        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);

        profileImageView.setImageBitmap(circleBitmap);
       */
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Please wait!", Toast.LENGTH_SHORT).show();
                loginEditor.putString("phone", phoneEditText.getText().toString());
                loginEditor.putString("name", nameTextView.getText().toString());
                loginEditor.putString("email", emailTextView.getText().toString());

                loginEditor.commit();
                Intent k = new Intent(getActivity(), SecondActivityMain.class);
                startActivity(k);
            }
        });
        if (isSaved) {
            Bitmap bitmap = null;
            if (!picPath.equalsIgnoreCase("none")) {
                File sd = Environment.getExternalStorageDirectory();
                /*File image = new File(sd+picPath, imageName);*/
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeFile(picPath/*image.getAbsolutePath()*/, bmOptions);
                Log.d("bitmap", String.valueOf(bitmap));
                Log.d("picPath", picPath);

                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                bitmap = getResizedBitmap(bitmap, 500, 500);
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                Canvas c = new Canvas(circleBitmap);
                Log.d("bitmap width half", String.valueOf(bitmap.getHeight() / 2));
                Log.d("bitmap width half", String.valueOf(bitmap.getWidth() / 2));

                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                profileImageView.setImageBitmap(circleBitmap);

//                profileImageView.setImageBitmap(bitmap);
            }

            //          loginNow.setVisibility(View.GONE);
            if (name.equalsIgnoreCase("none"))
                nameTextView.setVisibility(View.GONE);
            else
                nameTextView.setText(name);
            if (name.contains(" ")) {
                goodDayString = name.substring(0, name.indexOf(" ")) + "!";
            } else
                goodDayString = name + "!";
            if (phone.equalsIgnoreCase("none"))
                phoneEditText.setText("");
            else
                phoneEditText.setText(phone);

            phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        loginEditor.putString("phone", phoneEditText.getText().toString());
                    loginEditor.commit();
                }
            });
            if (age.equalsIgnoreCase("none"))
                ageTextView.setVisibility(View.GONE);
            else

                ageTextView.setText(" , " + age);
            if (email.equalsIgnoreCase("none"))
                emailTextView.setVisibility(View.GONE);
            else
                emailTextView.setText(email);
            if (gender.equalsIgnoreCase("none"))
                genderTextView.setVisibility(View.GONE);
            else
                genderTextView.setText(gender);


        }

        //else {
          //  Intent i = new Intent(getActivity(), FacebookCheckActivity.class);
            //startActivity(i);
       // }

        return rootView;
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
}

