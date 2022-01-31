package com.assignment.polygonerange.util;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocation {

    private Marker marker;
    private MarkerOptions markerOptions;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public MyLocation() {
        markerOptions=new MarkerOptions();
    }

    public MyLocation(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void setLocationManager(LocationManager locationManager, LocationListener locationListener) {
        this.locationManager = locationManager;
        this.locationListener=locationListener;
    }

    public Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }



    @SuppressLint("MissingPermission")
    public String getLocation(Double lat,Double lon) throws IOException {
        List<Address> list = null;
        Location locationGPS = getLastKnownLocation();
        Log.e(TAG, "getLocation: {}"+locationGPS );
        Geocoder geocoder = new Geocoder(AppContext.context, Locale.getDefault());
        list=geocoder.getFromLocation(lat,lon,10);
        if(list.size()>0)
         return list.get(0).getAddressLine(0);
        return "Address not found";


    }

}
