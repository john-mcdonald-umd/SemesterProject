package edu.umd.cs.semesterproject.service.impl;
// http://stackoverflow.com/questions/8352420/get-current-device-location-after-specific-interval

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class ServiceLocation extends Service {
    private LocationManager locMan;
    private Boolean locationChanged;
    private Handler handler = new Handler();

    public static Location curLocation;
    public static boolean isService = true;

    LocationListener gpsListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged");
            if (curLocation == null) {
                curLocation = location;
                locationChanged = true;
            }else if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude()){
                locationChanged = false;
                return;
            }else
                locationChanged = true;

            curLocation = location;

            if (locationChanged)
                locMan.removeUpdates(gpsListener);

        }
        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status,Bundle extras) {
            if (status == 0)// UnAvailable
            {
            } else if (status == 1)// Trying to Connect
            {
            } else if (status == 2) {// Available
            }
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();

        curLocation = getBestLocation();

        if (curLocation == null)
            Toast.makeText(getBaseContext(),"Unable to get your location", Toast.LENGTH_SHORT).show();
        else{
            //Toast.makeText(getBaseContext(), curLocation.toString(), Toast.LENGTH_LONG).show();
        }

        isService =  true;
    }
    final String TAG="LocationService";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onStart(Intent i, int startId){
        handler.postDelayed(GpsFinder,1);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(GpsFinder);
        handler = null;
        Toast.makeText(this, "Stop services", Toast.LENGTH_SHORT).show();
        isService = false;
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public Runnable GpsFinder = new Runnable(){
        public void run(){

            Location tempLoc = getBestLocation();
            if(tempLoc!=null)
                curLocation = tempLoc;
            tempLoc = null;
            handler.postDelayed(GpsFinder,1000);// register again to start after 1 seconds...
        }
    };

    private Location getBestLocation() {
        Location gpslocation = null;
        Location networkLocation = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission good");
        }
        else {
            Log.d(TAG, "Permission bad");
        }

        if(locMan==null){
            locMan = (LocationManager) getApplicationContext() .getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            if(locMan.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 1, gpsListener);// here you can set the 2nd argument time interval also that after how much time it will get the gps location
                gpslocation = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            }
            if(locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 1, gpsListener);
                networkLocation = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        } catch (IllegalArgumentException e) {
            //Log.e(ErrorCode.ILLEGALARGUMENTERROR, e.toString());
            Log.e("error", e.toString());
        }
        if(gpslocation==null && networkLocation==null)
            return null;

        if(gpslocation!=null && networkLocation!=null){
            if(gpslocation.getTime() < networkLocation.getTime()){
                gpslocation = null;
                return networkLocation;
            }else{
                networkLocation = null;
                return gpslocation;
            }
        }
        if (gpslocation == null) {
            return networkLocation;
        }
        if (networkLocation == null) {
            return gpslocation;
        }
        return null;
    }
}