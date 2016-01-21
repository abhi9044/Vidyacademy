package com.holamed.meddapp;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.holamed.meddapp.adapter.ImageAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * Created by Era on 5/18/2015.
 */
public class TutorialFragment extends Fragment {
    public TutorialFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        title_name.setText("How it works");
        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext());
        viewPager.setAdapter(adapter);
       CirclePageIndicator mIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);
      //  mIndicator.setFades(false);
        mIndicator.setViewPager(viewPager);
        return rootView;
    }
    public static void loadView()
    {

    }
}
