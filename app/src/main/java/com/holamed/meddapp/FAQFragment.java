package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.holamed.meddapp.adapter.FaqAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Era on 5/18/2015.
 */
public class FAQFragment extends Fragment{
    private ArrayList<FaqObject> faqobjlist;
    public FAQFragment(){}
    private ListView listView;
    private FaqAdapter faqadapter;
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
        title_name.setText("FAQ");

        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        faqobjlist=new ArrayList<>();
        FaqObject a[]=new FaqObject[10];
        for(int i=0;i<10;i++)
            a[i]=new FaqObject();

            a[0].setQuestion("What is Medd?");
            a[0].setAnswer("Medd is an platform which is your personal diagnostics companion. You can avail upto 50% discounts on all diagnostic tests, at the best labs and hospitals around you.");
            faqobjlist.add(a[0]);

            a[1].setQuestion("Why should I book my tests through Medd?");
            a[1].setAnswer("When you book through Medd, you get huge discounts on the booked tests. Also, you can conveniently compare different options and select the best one for you through your smartphone!\n");
            faqobjlist.add(a[1]);
            a[2].setQuestion("How can Medd give such amazing discounts?");
            a[2].setAnswer("We have partnerships with all the labs and hospitals listed on our platform, and we are together able to give discount by utilizing the concept of bulk buying. So the more patients use our app, the higher will the discounts be.");
            faqobjlist.add(a[2]);
            a[3].setQuestion("My doctor told me to go to a particular lab. What if (s)he doesn’t accept the report?\n");
            a[3].setAnswer("We list only the best labs and hospitals on Medd. Also, you can see the ratings given by our users and decide for yourself.");
            faqobjlist.add(a[3]);
            a[4].setQuestion("Can I book a test through my computer?");
            a[4].setAnswer("Yes, we have a website Medd.in which offers the entire functionality of the app.");
            faqobjlist.add(a[4]);
            a[5].setQuestion("Where can I find my previous bookings?");
            a[5].setAnswer("You can find all of your previous bookings by logging in through the website or the mobile app, and navigating to Past Orders.");
            faqobjlist.add(a[5]);
            a[6].setQuestion("I cannot find a test I need. Is there a solution?");
            a[6].setAnswer("Please give us a call at 88793-99793 or send us a mail at hello@medd.in, and we will get back to you as soon as possible!");
            faqobjlist.add(a[6]);
        listView=(ListView)rootView.findViewById(R.id.list_faq);
        faqadapter=new FaqAdapter(getActivity(),faqobjlist);
        listView.setAdapter(faqadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.findViewById(R.id.answer).getVisibility() == View.VISIBLE) {
                    view.findViewById(R.id.answer).setVisibility(View.GONE);
                    ((TextView) view.findViewById(R.id.question)).setTypeface(null, Typeface.NORMAL);
                } else {
                    Log.d("not visible", String.valueOf(faqadapter.getCount()));
                    for (int j = 0; j < faqadapter.getCount(); j++) {
                        Log.d("inside loop", String.valueOf(j));
                        try {
                            listView.getChildAt(j).findViewById(R.id.answer).setVisibility(View.GONE);
                            ((TextView)listView.getChildAt(j).findViewById(R.id.question)).setTypeface(null, Typeface.NORMAL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                   TextView ans = (TextView) view.findViewById(R.id.answer);
                    ans.setVisibility(View.VISIBLE);
                    ans.startAnimation(slide_down);

                    ((TextView) view.findViewById(R.id.question)).setTypeface(null, Typeface.BOLD);



                }

            }
        });
        return rootView;

    }
    public static void loadView()
    {

    }
}
