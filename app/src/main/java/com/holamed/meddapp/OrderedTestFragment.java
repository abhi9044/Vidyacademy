package com.holamed.meddapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.holamed.meddapp.adapter.FaqAdapter;
import com.holamed.meddapp.adapter.PastOrdersAdapter;

import java.util.ArrayList;

/**
 * Created by Era on 5/18/2015.
 */
public class OrderedTestFragment extends Fragment {

    private ListView listView;
    private PastOrdersAdapter pastordersadapter;
    public OrderedTestFragment(){}
    private PastOrdersDB db;
    public static ProgressDialog pDialog;

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
        title_name.setText("Past Orders");
        View rootView = inflater.inflate(R.layout.fragment_orderedtest, container, false);
        listView=(ListView)rootView.findViewById(R.id.list_pastorders);
        db = new PastOrdersDB(getActivity().getApplicationContext());
        db.open();
        ArrayList<PastOrdersObject> pastorderslist=db.getAll();
        if(pastorderslist.isEmpty())
        {
            LinearLayout a=(LinearLayout)rootView.findViewById(R.id.no_order);
            a.setVisibility(View.VISIBLE);
            final Button test_button=(Button)rootView.findViewById(R.id.go_test_but_f);
            test_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test_button.setBackgroundColor(getResources().getColor(R.color.onclickcolor));

                    Intent intent = new Intent(getActivity(), SearchTests.class);
                    getActivity().startActivity(intent);
                }
            });
            final Button pharmacy_button=(Button)rootView.findViewById(R.id.go_pharmacy_but_f);
            pharmacy_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("initiate", "here");
                    pharmacy_button.setBackgroundColor(getResources().getColor(R.color.onclickcolor));
                    Intent intent = new Intent(getActivity(), SecondActivityMain.class);
                    getActivity().startActivity(intent);
                }
            });

        }
        pastordersadapter=new PastOrdersAdapter(getActivity(),R.layout.pastorders_listrow,pastorderslist);
        listView.setAdapter(pastordersadapter);
        if (listView.getCount()<1){
            LinearLayout noResult=(LinearLayout)rootView.findViewById(R.id.no_order);
            noResult.setVisibility(View.VISIBLE);

            Button test=( Button)rootView.findViewById(R.id.go_test_but_f);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),SearchTests.class);
                    startActivity(i);
                }
            });
            Button pharmacy=( Button)rootView.findViewById(R.id.go_pharmacy_but_f);
            pharmacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("initiate", "here1");
                    Intent i = new Intent(getActivity(), SecondActivityMain.class);
                    startActivity(i);
                }
            });
        }

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        return rootView;
    }
    public static void bookClicked(final PastOrdersObject object,final Context ctx){
        final Dialog alertDialog;

        alertDialog = new Dialog(AppControllerSearchTests.getInstance());

        //setting custom layout to dialog
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.custom_dialog);

        Button one = (Button) alertDialog.findViewById(R.id.first);
        one.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AppControllerSearchTests.setSelectedtest(object.getTests());
              //  Intent swipeCategory = new Intent(ctx, CategorySearch.class);
               // ctx.startActivity(swipeCategory);
                alertDialog.dismiss();
            }
        });

        Button two=(Button)alertDialog.findViewById(R.id.second);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //pDialog.show();
            //AppControllerSearchTests.
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static void loadView()
    {

    }

}
