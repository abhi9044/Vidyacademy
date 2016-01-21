package com.holamed.meddapp;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Era on 5/18/2015.
 */
public class ContactUsFragment extends Fragment {
    public ContactUsFragment(){}
ImageButton call;
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
        title_name.setText("Contact Us");
        View rootView = inflater.inflate(R.layout.fragment_contactus, container, false);
call=(ImageButton)rootView.findViewById(R.id.imcall);
     call.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent callIntent = new Intent(Intent.ACTION_CALL);
             callIntent.setData(Uri.parse("tel: 88793-99793"));
             getActivity().startActivity(callIntent);
         }
     });
        return rootView;
    }
}
