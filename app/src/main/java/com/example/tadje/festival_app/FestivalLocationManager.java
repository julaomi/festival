package com.example.tadje.festival_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by tadje on 13.07.2018.
 */

public class FestivalLocationManager implements LocationListener {

    private static FestivalLocationManager instance = null;

    private Context mContext;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location;
    private double latitude;
    private double longitude;
    // The minimum distance to change updates in metters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    // The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5;
    private LocationManager locationManager;
    private List<ILocationListener> listeners = new ArrayList<>();



    public FestivalLocationManager() {
    }


    public static FestivalLocationManager getInstance() {
        if (instance == null) {
            instance = new FestivalLocationManager();
        }

        return instance;
    }

    // initialize for get the locations
    public Location locationInitialize(Context context) {

        mContext = context;
        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        checkForPermissions();

        return location;
    }


    public void checkForPermissions() {

        if (isGPSEnabled || isNetworkEnabled) {

            this.canGetLocation = true;


            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                    .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                            .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                locationManager.requestLocationUpdates(LocationManager
                                .GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        (LocationListener) this);
                locationManager.requestLocationUpdates(LocationManager
                                .NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        (LocationListener) this);

                lastLocationFromProvider();
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void lastLocationFromProvider() {

        if (locationManager != null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager
                        .NETWORK_PROVIDER);
            }

            initializeLocationVariablen(location);
        }
    }

    public void initializeLocationVariablen(Location location) {

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            FestivalManager.getInstance().setLatitude(latitude);
            FestivalManager.getInstance().setLongitude(longitude);


            putLocationToDatabase();
        }
    }


    private void putLocationToDatabase() {
        }



    //stop the location updates
    @SuppressLint("NewApi")
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager
                            .PERMISSION_GRANTED && mContext.checkSelfPermission(Manifest.permission
                    .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates((LocationListener) this);
            //locationManager = null;
        }
    }

    // if an automatic location update happends then start the methode
    // initializeLocationVariablen with the new location and notifyLocationChangedListener
    @Override
    public void onLocationChanged(Location location) {

        initializeLocationVariablen(location);
        notifyLocationChangedListener(location);

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

    //register Listener to the listener List
    public void registerLocationChangedListener(ILocationListener locationChangedListener) {
        if (!listeners.contains(locationChangedListener)) {
            listeners.add(locationChangedListener);
        }
    }

    //remove the listener from the listener list
    public void unregisterLocationChangedListener(ILocationListener
                                                          locationChangedListener) {
        if (listeners.contains(locationChangedListener)) {
            listeners.remove(locationChangedListener);
        }
    }

    //inform the registered listener about the new location
    public void notifyLocationChangedListener(Location location) {
        for (ILocationListener listener : listeners) {
            listener.onLocationChanged(location);
        }
    }


}

