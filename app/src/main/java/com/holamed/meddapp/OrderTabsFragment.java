package com.holamed.meddapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.holamed.meddapp.adapter.BaseFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class OrderTabsFragment extends Fragment {
    private static Activity act;
    ListView list;
    ProgressBar blogbar;
    ArrayList<String> nameblog;
    ArrayList<String> dateblog;
    ArrayList<String> contentblog;
    ArrayList<String> image_url;
    ArrayList<String> blog_link;


    Button bviewall;
    private static View rootView;
    HttpClient client;
    final static String URL = "http://medd.in/apples/wp-json/wp/v2/posts/";
    private static View header;
    private static LinearLayout orderTab;
    private static LinearLayout labTest;
    private static LinearLayout healthCheckUp;
    private static LinearLayout events_layout;
    private static Context c;
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    static Animation SlideDown;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        act = getActivity();
        //initializing animations
        client = new DefaultHttpClient();
        SlideDown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);

        nameblog = new ArrayList<>();
        contentblog = new ArrayList<>();
        dateblog = new ArrayList<>();
        blog_link = new ArrayList<>();
        image_url = new ArrayList<>();

        rootView = inflater.inflate(R.layout.fragment_order_tabs, container, false);
        Activity parentActivity = getActivity();

        act = getActivity();
        list = (ListView) rootView.findViewById(R.id.listView_blog);
        bviewall = (Button) rootView.findViewById(R.id.bviewall);
        orderTab = (LinearLayout) rootView.findViewById(R.id.orderTab);
        blogbar = (ProgressBar) rootView.findViewById(R.id.blog_progressbar);
        healthCheckUp = (LinearLayout) rootView.findViewById(R.id.healthCheckUp);
        events_layout = (LinearLayout) rootView.findViewById(R.id.events_layout);
        labTest = (LinearLayout) rootView.findViewById(R.id.labTest);
        bviewall.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //perform your animation when button is touched and held
                    bviewall.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //perform your animation when button is released
                    bviewall.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }


                return false;
            }
        });
        bviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), All_Blogs.class);
                startActivity(i);
            }
        });

        labTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Findtutor.class);
                getActivity().startActivity(intent);
            }
        });
        events_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        healthCheckUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), HealthCheckUpNew.class);
               getActivity().startActivity(intent);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent blogdetail = new Intent(getActivity(), Detailed_Blogs.class);
                blogdetail.putExtra("name", nameblog.get(i));
                blogdetail.putExtra("date", dateblog.get(i));
                blogdetail.putExtra("content", contentblog.get(i));
                blogdetail.putExtra("imagelink", image_url.get(i));
                blogdetail.putExtra("bloglink", blog_link.get(i));
                startActivity(blogdetail);

            }
        });


        new Read().execute("blog");

        return rootView;
    }

    public class Read extends AsyncTask<String, Integer, String> {

        public void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            try {
                fetchjson();


            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            abhishekAdapter vish = new abhishekAdapter(getActivity(), nameblog, dateblog, contentblog, image_url, blog_link);
            list.setAdapter(vish);
            blogbar.setVisibility(View.GONE);
            bviewall.setVisibility(View.VISIBLE);

        }

    }

    class abhishekAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> namelist;
        ArrayList<String> datelist;
        ArrayList<String> contentlist;
        ArrayList<String> imagelist;
        ArrayList<String> bloglist;


        abhishekAdapter(Context c, ArrayList<String> namegot, ArrayList<String> dateblog, ArrayList<String> contentblog, ArrayList<String> imageurl, ArrayList<String> bloglink) {
            super(c, R.layout.listview_blog, R.id.nameblog, namegot);
            this.context = c;
            this.namelist = namegot;
            this.datelist = dateblog;
            this.contentlist = contentblog;
            this.imagelist = imageurl;
            this.bloglist = bloglink;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // TODO
            // Auto-generated
            // method
            // stub
            ImageLoader imgLoader = new ImageLoader(getActivity().getApplicationContext());
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listview_blog, parent, false);
            TextView na = (TextView) row.findViewById(R.id.nameblog);
            TextView date = (TextView) row.findViewById(R.id.blogdate);
            TextView cont = (TextView) row.findViewById(R.id.contentblog);
            ImageView imgV = (ImageView) row.findViewById(R.id.imageblog);
            na.setText(namelist.get(position));
            date.setText(datelist.get(position));
            cont.setText(contentlist.get(position) + "...");
            if (!(imagelist.get(position).equals("noimage"))) {
                imgLoader.DisplayImage(imagelist.get(position), R.drawable.blog1, imgV);
            }

            return row;
        }
    }

    public void fetchjson() throws ClientProtocolException,
            IOException, JSONException {
        StringBuilder url = new StringBuilder(URL);
        int j;

        HttpGet get = new HttpGet(url.toString());
        HttpResponse r = client.execute(get);
        int status = r.getStatusLine().getStatusCode();
        if (status == 200) {
            for (int i = 0; i < 1; i++) {
                    image_url.add("noimage");

                nameblog.add("Name of the blog");
                contentblog.add("This is the content");
                String date = "21";
                String month = "01";
                String link = "";
                blog_link.add(link);
                String monthgot = null;
                if (month.equals("01"))
                    monthgot = "Jan";
                else if (month.equals("02"))
                    monthgot = "Feb";
                else if (month.equals("03"))
                    monthgot = "Mar";
                else if (month.equals("04"))
                    monthgot = "Apr";
                else if (month.equals("05"))
                    monthgot = "May";
                else if (month.equals("06"))
                    monthgot = "June";
                else if (month.equals("07"))
                    monthgot = "July";
                else if (month.equals("08"))
                    monthgot = "August";
                else if (month.equals("09"))
                    monthgot = "Sept";
                else if (month.equals("10"))
                    monthgot = "Oct";
                else if (month.equals("11"))
                    monthgot = "Nov";
                else if (month.equals("12"))
                    monthgot = "Dec";

                dateblog.add(date + " " + monthgot);
            }

        } else {
            Toast t = Toast.makeText(getActivity(), "Error",
                    Toast.LENGTH_LONG);
            t.show();


        }

    }

    static void loadView() {
        Log.d("LV", "OT");
        orderTab.setVisibility(View.VISIBLE);
    }
}