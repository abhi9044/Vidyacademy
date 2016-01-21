package com.holamed.meddapp;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by prabhat on 6/20/2015.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.holamed.meddapp.AppControllerSearchTests;
import com.holamed.meddapp.CategorySearch;
import com.holamed.meddapp.PastOrdersDB;
import com.holamed.meddapp.PastOrdersObject;
import com.holamed.meddapp.R;
import com.holamed.meddapp.SearchTests;
import com.holamed.meddapp.TestsDB;
import com.holamed.meddapp.TestsTableSqlite;
import com.holamed.meddapp.adapter.swipeListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreviousFragment extends Fragment {


    //for swipe list
    swipeListAdapter adapter;
    ListView swipeList;
    String[] web ;
    String[] idweb;

    String[] webzero = {
            "No Suggestions To Show!!"
    };

    String[] idwebzero = {
            "nothing"
    };

    private PastOrdersDB swipeDB;
    private TestsDB db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_previous_fragment, container, false);
        db = new TestsDB(getActivity());
        db.open();
        List<TestsTableSqlite> tests = db.getAllTests();

        //geting swipe data
        swipeDB = new PastOrdersDB(getActivity());
        swipeDB.open();
        ArrayList<PastOrdersObject> pastorderslist=swipeDB.getAll();
        ArrayList<String> pastlist = new ArrayList<>() ;
        ArrayList<String> pastid = new ArrayList<>();
        int length = pastorderslist.size();
        String nameTest;
        String idTest;
        String type;
        String name;
        for(int i=0; i<length; i++){
            type = pastorderslist.get(i).getChoice();
            //Toast.makeText(SearchTests.this, type, Toast.LENGTH_LONG).show();
            if(type.equals("testslab")){
                pastlist.add(pastorderslist.get(i).getTests());
                for (TestsTableSqlite cn : tests) {
                    name = cn.getName();
                    if(name.equals(pastorderslist.get(i).getTests())){
                        pastid.add(cn.getKey());
                    }

                    //testKey.add(cn.getKey());
                }

            }
        }
        swipeDB.close();



        length = pastlist.size();
        String[] webtemp = new String[length];
        String[] idwebtemp = new String[length];
        for(int i=0; i <length; i++){
            try {
                webtemp[i] = pastlist.get(i);
                idwebtemp[i] = pastid.get(i);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        Set<String> webqq = new HashSet<String>(Arrays.asList(webtemp));
        Set<String> idwebqq = new HashSet<String>(Arrays.asList(idwebtemp));
        web = webqq.toArray(new String[webqq.size()]);
        idweb = new String[web.length];

        for(int i=0; i<web.length ; i++){
            for(int j=0;j<webtemp.length;j++){
                if(web[i].equals(webtemp[j])){
                    try {
                        idweb[i] = idwebtemp[j];
                        j = 10000000;
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }


        //swipe list
        swipeList = (ListView) rootView.findViewById(R.id.previous_swipe_list);
        if( web.length > 0) {
            adapter = new
                    swipeListAdapter(getActivity(), web);
        }
        else{

            adapter = new
                    swipeListAdapter(getActivity(), webzero);
        }


        //swipeList.setVisibility(View.INVISIBLE);
        swipeList.setAdapter(adapter);
        swipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(SearchTests.this, "You Clicked at " + idweb[+position], Toast.LENGTH_SHORT).show();
                if (web.length > 0) {
                    AppControllerSearchTests.setSelectedtest(web[+position]);
                    Intent swipeCategory = new Intent(getActivity(), CategorySearch.class);
                    startActivity(swipeCategory);
                }
            }
        });


        db.close();





        return rootView;
    }
}