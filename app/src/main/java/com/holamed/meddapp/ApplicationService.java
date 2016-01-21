package com.holamed.meddapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by Abhishek on 4/5/2015.
 */
public class ApplicationService extends Service implements LocationListener {
    protected LocationManager locationManager;
    Location location;
    private  static final long MIN_DISTANCE_FOR_UPDATE=10;
    private static final  long mintime=1000*60*2;
    public ApplicationService(Context context)
    {
        locationManager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
        }

public Location getLocation(String provider)
{
    if(locationManager.isProviderEnabled(provider))
    {
        locationManager.requestLocationUpdates(provider,mintime,MIN_DISTANCE_FOR_UPDATE,this);
        if(locationManager!=null)
        {
         location=locationManager.getLastKnownLocation(provider);
            return location;}


        }
return  null;
    }




    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
