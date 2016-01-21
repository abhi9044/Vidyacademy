package com.holamed.meddapp;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class All_Blogs extends AppCompatActivity {
    final static String URL = "http://medd.in/apples/wp-json/wp/v2/posts/";
    private View mHeaderView;
    private View mToolbarView;
    HttpClient client;
    String code;
    ListView list;
    ProgressBar blogbar;
    ArrayList<String> nameblog;
    ArrayList<String> dateblog;
    ArrayList<String> contentblog;
    ArrayList<String> image_url;
    ArrayList<String> blog_link;

    ProgressDialog dialog;
    private ImageView home;
    private ImageView back;
    TextView tvtandc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_all);
        client = new DefaultHttpClient();
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
                .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);

        ActionBar actionBar = getSupportActionBar();
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

        TextView title_name = (TextView) customActionBarView.findViewById(R.id.custom_title);
        list = (ListView)findViewById(R.id.listView_blog);
        nameblog = new ArrayList<>();
        contentblog = new ArrayList<>();
        dateblog = new ArrayList<>();
        blog_link = new ArrayList<>();
        image_url = new ArrayList<>();
        blogbar = (ProgressBar)findViewById(R.id.blog_progressbar);

        title_name.setText("Medd Apples");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent blogdetail = new Intent(All_Blogs.this, Detailed_Blogs.class);
                blogdetail.putExtra("name", nameblog.get(i));
                blogdetail.putExtra("date", dateblog.get(i));
                blogdetail.putExtra("content", contentblog.get(i));
                blogdetail.putExtra("imagelink", image_url.get(i));
                blogdetail.putExtra("bloglink", blog_link.get(i));

                startActivity(blogdetail);

            }
        });
        home = (ImageView) findViewById(R.id.home_pressed);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(All_Blogs.this, SecondActivityMain.class);
                startActivity(i);

            }
        });

        back = (ImageView) findViewById(R.id.back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(All_Blogs.this, SecondActivityMain.class);
                startActivity(i);
                // finish();
            }
        });

        new Read().execute("blog");

    }
    public void fetchjson() throws ClientProtocolException,
            IOException, JSONException {
        StringBuilder url = new StringBuilder(URL);
        int j;

        HttpGet get = new HttpGet(url.toString());
        HttpResponse r = client.execute(get);
        int status = r.getStatusLine().getStatusCode();
        if (status == 200) {
            HttpEntity e = r.getEntity();
            String data = EntityUtils.toString(e);
            JSONArray main = new JSONArray(data);
            for (int i = 0; i < main.length(); i++) {
                JSONObject a = main.getJSONObject(i);
                JSONObject b = a.getJSONObject("title");
                JSONObject c = a.getJSONObject("content");
                try {
                    JSONObject z = a.getJSONObject("better_featured_image");
                    String imgurl = z.getString("source_url");
                    image_url.add(imgurl);
                } catch (Exception ex) {
                    image_url.add("noimage");
                }
                String link = a.getString("link");
                blog_link.add(link);

                nameblog.add(b.getString("rendered"));
                contentblog.add(c.getString("rendered"));
                String date = a.getString("date").substring(8, 10);
                String month = a.getString("date").substring(5, 7);
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
            Toast t = Toast.makeText(this, "Error",
                    Toast.LENGTH_LONG);
            t.show();


        }

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
            abhishekAdapter vish = new abhishekAdapter(All_Blogs.this, nameblog, dateblog, contentblog,image_url,blog_link);
            list.setAdapter(vish);
            blogbar.setVisibility(View.GONE);


        }

    }
    class abhishekAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> namelist;
        ArrayList<String> datelist;
        ArrayList<String> contentlist;
        ArrayList<String> imagelist;
        ArrayList<String> bloglist;


        abhishekAdapter(Context c, ArrayList<String> namegot, ArrayList<String> dateblog, ArrayList<String> contentblog,ArrayList<String> imageurl,ArrayList<String> bloglink) {
            super(c, R.layout.listview_blog, R.id.nameblog, namegot);
            this.context = c;
            this.namelist = namegot;
            this.datelist = dateblog;
            this.contentlist = contentblog;
            this.imagelist=imageurl;
            this.bloglist=bloglink;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // TODO
            // Auto-generated
            // method
            // stub
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.listview_blog, parent, false);
            TextView na = (TextView) row.findViewById(R.id.nameblog);
            TextView date = (TextView) row.findViewById(R.id.blogdate);
            TextView cont = (TextView) row.findViewById(R.id.contentblog);
            ImageView imgV=(ImageView)row.findViewById(R.id.imageblog);
            na.setText(namelist.get(position));
            date.setText(datelist.get(position));
            cont.setTextAppearance(getApplicationContext(), R.style.normalText);
            cont.setText(android.text.Html.fromHtml(contentlist.get(position).substring(0, 121) + "..."));
            if(!(imagelist.get(position).equals("noimage"))) {
                imgLoader.DisplayImage(imagelist.get(position), R.drawable.blog1, imgV);
            }
            Log.d("Imagewa", imagelist.get(position));
            Log.d("Imagewa", bloglist.get(position));
            return row;
        }
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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
