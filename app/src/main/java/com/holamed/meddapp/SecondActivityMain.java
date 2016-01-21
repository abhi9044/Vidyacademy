package com.holamed.meddapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.holamed.meddapp.adapter.NavDrawerListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;


public class SecondActivityMain extends AppCompatActivity implements Animation.AnimationListener, AdapterView.OnItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private LinearLayout drawerLinearLayout;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle mDrawerToggle;
    public SharedPreferences loginPrefs;

    private CharSequence navDrawerTitleCharSequence;
    private CharSequence actionBarTitleCharSequence;

    //navDrawer items
    private String[] navDrawerTitles;
    private String[] navDrawerIcons;

    private ArrayList<NavDrawerItem> drawerItemArraylist;
    private NavDrawerListAdapter navDrawerListAdapter;
    Toolbar toolbar;

    private GoogleApiClient googleApiClient;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity_main);

        drawerLinearLayout = (LinearLayout) findViewById(R.id.left_drawer);

        loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
        buildGoogleApiClient();
        googleApiClient.connect();
        actionBarTitleCharSequence = navDrawerTitleCharSequence = getTitle();
        setupNavDrawer();

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    private void setupNavDrawer() {
        navDrawerTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navDrawerIcons = getResources().getStringArray(R.array.nav_drawer_icons);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.list_slidermenu);

        drawerItemArraylist = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[0], navDrawerIcons[0]));
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[1], navDrawerIcons[1]));
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[2], navDrawerIcons[2]));
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[3], navDrawerIcons[3]));
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[4], navDrawerIcons[4]));
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[5], navDrawerIcons[5]));
        drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[6], navDrawerIcons[6]));
        Log.d("myid",loginPrefs.getString("UserId","no"));
        if (loginPrefs.getBoolean("LoggedIn", false)) {
            drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[7], navDrawerIcons[7]));
        } else {
            drawerItemArraylist.add(new NavDrawerItem("Login", navDrawerIcons[7]));
        }

        navDrawerListAdapter = new NavDrawerListAdapter(getApplicationContext(), drawerItemArraylist);
        drawerListView.setAdapter(navDrawerListAdapter);
        drawerListView.setOnItemClickListener(new navDrawerOnClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(actionBarTitleCharSequence);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(navDrawerTitleCharSequence);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loginPrefs.getBoolean("LoggedIn", false)) {
            drawerItemArraylist.remove(7);
            drawerItemArraylist.add(new NavDrawerItem(navDrawerTitles[7], navDrawerIcons[7]));
        } else {
            drawerItemArraylist.remove(7);
            drawerItemArraylist.add(new NavDrawerItem("Login", navDrawerIcons[7]));
        }
        navDrawerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Slide menu item click listener
     */

    private class navDrawerOnClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            if (position == 0 || position == 1 || position == 2 || position == 6) {
                navDrawerListAdapter.setDrawerItemSelected(position);
                navDrawerListAdapter.notifyDataSetChanged();
            }
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_tests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerLinearLayout);
        //  menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:

                fragment = new MainFragment();
                break;
            case 1:
                fragment = new TutorialFragment();
                break;

            case 2:

                fragment = new FAQFragment();
                break;

            case 3:
                //         fragment = new ContactUsFragment();
                final Dialog dialog4 = new Dialog(this);
                dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog4.setContentView(R.layout.contact_us_dialog);
                ImageButton call = (ImageButton) dialog4.findViewById(R.id.ibcall);
                ImageButton mail = (ImageButton) dialog4.findViewById(R.id.ibmail);

                ImageView close = (ImageView) dialog4.findViewById(R.id.cclose);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.dismiss();
                    }
                });
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.dismiss();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel: 88793-99793"));
                        startActivity(callIntent);


                    }
                });
                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog4.dismiss();
                        String[] TO = {"support@medd.in"};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail using"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(SecondActivityMain.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog4.show();


                break;
            case 4:
                //    fragment = new ShareFragment();

                break;

            case 5:
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.rateus_dialog);

                ImageView rclose = (ImageView) dialog.findViewById(R.id.rclose);

                ImageButton yes = (ImageButton) dialog.findViewById(R.id.imgbok);
                ImageButton no = (ImageButton) dialog.findViewById(R.id.imgbgood);
                rclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final Dialog dialog2 = new Dialog(SecondActivityMain.this);
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.rateus_five_layout);
                        Button brat = (Button) dialog2.findViewById(R.id.bfivestar);
                        ImageView rate5close = (ImageView) dialog2.findViewById(R.id.rate5close);
                        rate5close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog2.dismiss();
                            }
                        });
                        brat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    Toast.makeText(SecondActivityMain.this, "Kindly Swipe Up to Rate..", Toast.LENGTH_LONG).show();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }

                            }
                        });

                        dialog2.show();

                    }

                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final Dialog dialog3 = new Dialog(SecondActivityMain.this);
                        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog3.setContentView(R.layout.feedback_dialog);
                        Button bfeed = (Button) dialog3.findViewById(R.id.bgetfeedback);
                        ImageView hclose = (ImageView) dialog3.findViewById(R.id.hclose);
                        hclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog3.dismiss();
                            }
                        });
                        bfeed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog3.dismiss();
                                Intent intent = new Intent(SecondActivityMain.this, Feedback.class);
                                startActivity(intent);


                            }
                        });

                        dialog3.show();


                    }
                });
                dialog.show();


                break;
            case 6:

                fragment = new About();

                break;


            case 7:
                fragment = null;
                if (loginPrefs.getBoolean("LoggedIn", false)) {
                    sharedPreferencesEditor = loginPrefs.edit();
                    sharedPreferencesEditor.putBoolean("LoggedIn", false);
                    sharedPreferencesEditor.putString("UserId", "");
                    sharedPreferencesEditor.putString("PhoneNumber", "");
                    sharedPreferencesEditor.commit();
                    drawerItemArraylist.remove(7);
                    drawerItemArraylist.add(new NavDrawerItem("Login", navDrawerIcons[7]));
                    drawerLayout.closeDrawer(drawerLinearLayout);
                    Toast.makeText(SecondActivityMain.this, "Logout Successful", Toast.LENGTH_LONG).show();
                    navDrawerListAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(SecondActivityMain.this, SecondActivityMain.class);
                    startActivity(intent);
                } else {
                    drawerLayout.closeDrawer(drawerLinearLayout);
                    Intent intent = new Intent(SecondActivityMain.this, OTPReceiveActivity.class);
                    intent.putExtra("goto", "SecondActivityMain");
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
            setTitle(navDrawerTitles[position]);
            drawerLayout.closeDrawer(drawerLinearLayout);
        } else if (position == 4) {

            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.shareimage);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("*/*");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                    b, "Title", null);
            Uri imageUri = Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            share.putExtra(Intent.EXTRA_TEXT, "Medd helps me book all my medical tests through my smartphone, and get my reports in one place!.Download Android app now and get great discounts: bit.ly/medd-app");
            startActivity(Intent.createChooser(share, "Select"));
            fragment = new MainFragment();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        actionBarTitleCharSequence = title;
        getSupportActionBar().setTitle(actionBarTitleCharSequence);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onBackPressed() {
       /*
        */

        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Location S", "play service connected");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (mLastLocation != null) {
            AppControllerSearchTests.setLatitude(mLastLocation.getLatitude());
            AppControllerSearchTests.setLongitude(mLastLocation.getLongitude());
            Log.d("Location set", String.valueOf(AppControllerSearchTests.getLatitude()) + " " + String.valueOf(AppControllerSearchTests.getLongitude()));
            AppControllerSearchTests.locSet = true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Location S", "play srvices sus");
        AppControllerSearchTests.setLatitude(1.0);
        AppControllerSearchTests.setLongitude(1.0);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Location S", "play srvices failed");
        AppControllerSearchTests.setLatitude(1.0);
        AppControllerSearchTests.setLongitude(1.0);

    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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

    @Override
    public void onStart() {
        super.onStart();
        final ArrayList<String> pathology = new ArrayList<String>();
        final ArrayList<String> radiology = new ArrayList<String>();

        Branch branch = Branch.getInstance();
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked before showing up
                    Log.i("BranchConfigTest", "deep link data: " + referringParams.toString());

                    try {
                        String type = referringParams.getString("deeplink_type");
                        if (type.equals("Test")) {
                            String test_type = referringParams.getString("test_type");
                            if (test_type.equals("pathology")) {
                                String id = referringParams.getString("test_id");
                                pathology.add(id);
                            } else {
                                String id = referringParams.getString("test_id");
                                radiology.add(id);
                            }
                            AppControllerSearchTests.setSelectedPatho(pathology);
                            AppControllerSearchTests.setSelectedRadio(radiology);
                            Intent category = new Intent(SecondActivityMain.this, CategorySearch.class);
                            startActivity(category);

                        } else {
                            Intent category = new Intent(SecondActivityMain.this, HealthCheckUpNew.class);
                            startActivity(category);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

}