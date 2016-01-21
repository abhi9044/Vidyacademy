package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.holamed.meddapp.adapter.ImageAdapterTutorial;
import com.viewpagerindicator.CirclePageIndicator;


public class TutorialActivityMedd extends AppCompatActivity {
    private SharedPreferences pref;
    private static Button b;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_tutorial_activity_medd);
        pref = getSharedPreferences("testapp", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("doneonce", "true");
        editor.commit();
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_t);
        ImageAdapterTutorial adapter = new ImageAdapterTutorial(TutorialActivityMedd.this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("onscrolled", String.valueOf(position));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        CirclePageIndicator mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        //  mIndicator.setFades(false);
        mIndicator.setViewPager(viewPager);
        b=(Button)findViewById(R.id.watched);
        //on touch listener to change color of button
        boolean check=false;
        b.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });
      //  b.setVisibility(View.GONE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TutorialActivityMedd.this,SecondActivityMain.class);
                startActivity(i);
            }
        });

        mIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("mindicator", String.valueOf(position));
                if(position==3)
                {       //Log.d("waddup","visible");
                    b.setVisibility(View.VISIBLE);
            }
            else
            {
                //Log.d("waddup","invisible");
             //   b.setVisibility(View.GONE);
            }
            }
        });

    }

   /*public static void ButtonVisibility(int position)
   {    //Log.d("position check lulu", String.valueOf(position));

       if (position<3) {
           //Log.d("waddup","visible");
           b.setVisibility(View.GONE);
       }
       else
       {
           //Log.d("waddup","invisible");
           b.setVisibility(View.VISIBLE);
       }
   }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial_activity_medd, menu);
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
}
