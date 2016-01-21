package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.holamed.meddapp.adapter.FaqAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Era on 5/18/2015.
 */
public class About extends Fragment{
    private ArrayList<FaqObject> faqobjlist;
    PackageInfo pinfo=null;
    private ListView listView;
    private FaqAdapter faqadapter;
Button btc;
    Animation slide_down;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        //intializing animaion
        slide_down = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);
        LayoutInflater inflater1 = (LayoutInflater)((AppCompatActivity)getActivity()).getSupportActionBar()
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

        TextView title_name= (TextView) customActionBarView.findViewById(R.id.fragmentName);

        title_name.setText("About Us");

        View rootView = inflater.inflate(R.layout.layout_aboutus, container, false);
        TextView tvver= (TextView) rootView.findViewById(R.id.tvvers);
        try {
            pinfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int v = pinfo.versionCode;
 //      tvver.setText("v"+v);

        String fontPath = "fonts/Roboto-black.ttf";

        // text view label
        TextView txtGhost
                = (TextView) rootView.findViewById(R.id.tvbout);

        // Loading Font Face
       // Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);

        // Applying font
         btc=(Button)rootView.findViewById(R.id.btc);
        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        /*        Uri uri = Uri.parse("http://medd.in/privacy");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
        */
                Intent intent = new Intent(getActivity(),Terms.class);
                startActivity(intent);

            }
        });
        return rootView;

    }
    public static void loadView()
    {

    }
}
