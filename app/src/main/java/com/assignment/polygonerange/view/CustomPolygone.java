package com.assignment.polygonerange.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.assignment.polygonerange.R;
import com.assignment.polygonerange.model.RPVertices;
import com.assignment.polygonerange.util.AppContext;
import com.assignment.polygonerange.util.Constants;
import com.assignment.polygonerange.util.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomPolygone {
    private Polygon polygon;
    private Marker car_marker,charging_marker;
    private boolean haveToShow=true;


    public void removePolyline(){
        if(polygon !=null)
            polygon.remove();
    }

    private void fillPolygoneColor(int strokeColor, int bgColor) {
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(bgColor);
    }
    public void addChargingMarker(GoogleMap googleMap, double lat, double lon) {
        if(charging_marker!=null)
            charging_marker.remove();
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.title("Nearest Charging point");
        markerOptions.position(new LatLng(lat,lon));
        markerOptions.icon(bitmapDescriptorFromVector(AppContext.context,R.drawable.ic_baseline_charging_station_24));
        charging_marker=googleMap.addMarker(markerOptions);

    }

    public void addCarMarker(GoogleMap googleMap, double lat, double lon) {
        if(car_marker!=null)
            car_marker.remove();
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.title("You are here");
        markerOptions.position(new LatLng(lat,lon));
        markerOptions.icon(bitmapDescriptorFromVector(AppContext.context,R.drawable.electric_car));
        car_marker=googleMap.addMarker(markerOptions);

    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public void drawPolygon(GoogleMap mMap,RPVertices vertices) {
        if(vertices.getSoc()<=15)
            haveToShow=true;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        PolygonOptions polylineOptions = new PolygonOptions()
                .clickable(true);
        LatLng prev_latlng = null, first_latlng = null;
        List<List<Double>> coordinates=vertices.getRp_vertices();
        List<LatLng> mid_latlng = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {

            Double x = coordinates.get(i).get(0);
            Double y = coordinates.get(i).get(1);
            Log.e(TAG, "drawPolygon: {} {}"+x+" "+y );
            LatLng latLng = new LatLng(x, y);
            builder.include(latLng);
            polylineOptions.add(latLng);
            if (prev_latlng == null) {
                prev_latlng = latLng;
                first_latlng = latLng;
            } else {
                mid_latlng.add(Utils.getMidLatLng(latLng, prev_latlng));
                prev_latlng = latLng;
            }
        }
        assert first_latlng != null;
        polylineOptions.add(first_latlng);
        mid_latlng.add(Utils.getMidLatLng(prev_latlng, first_latlng));
        polylineOptions.strokeColor(Color.RED);
        polygon = mMap.addPolygon(polylineOptions);
        Log.i(TAG, "drawPolygon: "+vertices.getSoc());

        if(vertices.getSoc()<=Constants.LOW_BATTERY_WARNING)
            fillPolygoneColor(Color.parseColor("#8B0000"),Color.argb(Constants.ALPHA_VALUE,255,0,0));
        else
            fillPolygoneColor(Color.parseColor("#006400"),Color.argb(Constants.ALPHA_VALUE,0,255,0));


        int padding = 50;
        /**create the bounds from latlngBuilder to set into map camera*/
        LatLngBounds bounds = builder.build();
        /**create the camera with bounds and padding to set into map*/
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        addCarMarker(mMap,vertices.getLatitude(),vertices.getLongitude());
        addChargingMarker(mMap,vertices.getNearest_lat(),vertices.getNearest_lon());

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /**set animated zoom camera into map*/
                mMap.animateCamera(cu);
                if(vertices.getSoc()<=Constants.LOW_BATTER_ALERT){
                    showAlert();
                }
            }
        });

        Log.e(TAG, "drawPolygon: Success");

    }

    private void showAlert() {

        if(!haveToShow) {
            return;
        }
        haveToShow=false;
        new AlertDialog.Builder(AppContext.context)
                .setTitle("WARNING!!!")
                .setMessage("Battery percentage is below 15%")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void drawPolygonLocal(GoogleMap mMap,int json_index) throws JSONException {
        JSONArray coordinates = new JSONArray();
        try {
            JSONObject obj = new JSONObject(Objects.requireNonNull(Utils.loadJSONFromAsset(AppContext.context, Constants.FILE_NAME)));
            coordinates = obj.getJSONArray("coordinates");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true);
        LatLng prev_latlng = null, first_latlng = null;
        List<LatLng> mid_latlng = new ArrayList<>();
        JSONArray coordinate=coordinates.getJSONArray(json_index);
        for (int i = 0; i < coordinate.length(); i++) {
            try {
                Double x = Double.parseDouble(coordinate.getJSONArray(i).get(0).toString());
                Double y = Double.parseDouble(coordinate.getJSONArray(i).get(1).toString());
                LatLng latLng = new LatLng(x, y);
                polylineOptions.add(latLng);
                if (prev_latlng == null) {
                    prev_latlng = latLng;
                    first_latlng = latLng;
                } else {
                    mid_latlng.add(Utils.getMidLatLng(latLng, prev_latlng));
                    prev_latlng = latLng;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        polylineOptions.add(first_latlng);
        mid_latlng.add(Utils.getMidLatLng(prev_latlng, first_latlng));
//        polygon = mMap.addPolyline(polylineOptions);
//        polygon.setEndCap(new RoundCap());
//        polygon.setJointType(JointType.ROUND);
//        polygon.setWidth(Constants.POLYLINE_STROKE_WIDTH_PX);
//        polygon.setColor(Constants.COLOR_BLACK_ARGB);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Utils.getMidPoint(mid_latlng), Constants.ZOOM_VALUE));


    }

    public void drawPolygon(GoogleMap mMap) throws JSONException {
        JSONArray coordinates = new JSONArray();
        try {
            JSONObject obj = new JSONObject(Objects.requireNonNull(Utils.loadJSONFromAsset(AppContext.context, Constants.FILE_NAME)));
            coordinates = obj.getJSONArray("coordinates");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true);
        LatLng prev_latlng = null, first_latlng = null;
        List<LatLng> mid_latlng = new ArrayList<>();
        JSONArray coordinate=coordinates.getJSONArray(4);
        for (int i = 0; i < coordinate.length(); i++) {
            try {
                Double x = Double.parseDouble(coordinate.getJSONArray(i).get(0).toString());
                Double y = Double.parseDouble(coordinate.getJSONArray(i).get(1).toString());
                LatLng latLng = new LatLng(x, y);
                polylineOptions.add(latLng);
                if (prev_latlng == null) {
                    prev_latlng = latLng;
                    first_latlng = latLng;
                } else {
                    mid_latlng.add(Utils.getMidLatLng(latLng, prev_latlng));
                    prev_latlng = latLng;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        polylineOptions.add(first_latlng);
        mid_latlng.add(Utils.getMidLatLng(prev_latlng, first_latlng));
        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setEndCap(new RoundCap());
        polyline.setJointType(JointType.ROUND);
        polyline.setWidth(Constants.POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(Constants.COLOR_BLACK_ARGB);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Utils.getMidPoint(mid_latlng), Constants.ZOOM_VALUE));


    }

}
