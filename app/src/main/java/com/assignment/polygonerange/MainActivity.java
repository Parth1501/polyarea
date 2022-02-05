package com.assignment.polygonerange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.assignment.polygonerange.api.LocalDataApi;
import com.assignment.polygonerange.api.RetrofitClient;
import com.assignment.polygonerange.datamapper.DataPopulate;
import com.assignment.polygonerange.model.RPVertices;
import com.assignment.polygonerange.model.RPVerticesRequest;
import com.assignment.polygonerange.model.RpVerticesApi;
import com.assignment.polygonerange.util.AppContext;
import com.assignment.polygonerange.util.MyLocation;
import com.assignment.polygonerange.util.Permissions;
import com.assignment.polygonerange.view.CustomPolygone;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private static final float ZOOM_VALUE = 6.5F;
    private static final double ARRAY_SIZE = 6;
    private GoogleMap mMap;
    private LocationManager locationManager;

    private Permissions permissions;
    private CustomPolygone customPolygone;
    private MyLocation myLocation;
    private Handler handler;
    private DataPopulate dummy_data;
    private TextView tv;
    private MyLocation location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        permissions.getPermission(getApplicationContext(),this);

    }

    private void init() {
        AppContext.context=this;
        permissions=new Permissions();
        locationManager=(LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        myLocation=new MyLocation();
        myLocation.setLocationManager(locationManager,this);
        customPolygone=new CustomPolygone();
        handler = new Handler();
        dummy_data=new DataPopulate();
        tv=findViewById(R.id.tv);
        location=new MyLocation(locationManager);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        System.out.println("called");
        mMap = googleMap;

        oneMinuteIntervalThreadCall();
    }

    private void getVertices(double latitude, double longitude, int soc) {
        Call<RpVerticesApi> call= RetrofitClient.getInstance().getMyApi().getVertices(latitude,longitude,soc);
        call.enqueue(new Callback<RpVerticesApi>() {
            @Override
            public void onResponse(Call<RpVerticesApi> call, Response<RpVerticesApi> response) {
                System.out.println("Response from api:"+response.body());
                RpVerticesApi body=response.body();

                makeDrawPolyGoneCall(makeRpVerticesObject(body,latitude,longitude,soc));

            }

            @Override
            public void onFailure(Call<RpVerticesApi> call, Throwable t) {

            }
        });
    }

    private void makeDrawPolyGoneCall(RPVertices rpVertices) {
        customPolygone.drawPolygon(mMap,rpVertices);
        if(rpVertices.getSoc()>15) {
//                tv.setText("SOC: " + rpVertices.getSoc());
            tv.setTextColor(Color.GREEN);
        }
        else {

            tv.setTextColor(Color.RED);
        }
        String str= null;
        try {
            str = location.getLocation(rpVertices.getNearest_lat(),rpVertices.getNearest_lon());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.setText("SOC: " + rpVertices.getSoc()+"\nAddress: "+str);
    }

    private RPVertices makeRpVerticesObject(RpVerticesApi api,double lat,double lon,int soc) {
        RPVertices vertices=new RPVertices();
        vertices.setRp_vertices(api.getVertices());
        vertices.setLatitude(lat);
        vertices.setLongitude(lon);
        if(api.getNearestChargingStation()!=null) {
            vertices.setNearest_lat(api.getNearestChargingStation().get(0).get(0));
            vertices.setNearest_lon(api.getNearestChargingStation().get(0).get(1));
        }
        vertices.setSoc(soc);
        return vertices;
    }


    private void makePolyGoneCall() {
        RPVerticesRequest[] req=dummy_data.populateRPVerticesArray();
        int random_index= (int) ((Math.random()*10)% ARRAY_SIZE);
        customPolygone.removePolyline();
        getVertices(req[random_index].getLatitude(),req[random_index].getLongitude(),req[random_index].getSoc());
//            RPVertices rpVertices=LocalDataApi.GetRPVertices(req[random_index].getLatitude(),req[random_index].getLongitude(),req[random_index].getSoc());
//            customPolygone.drawPolygon(mMap,rpVertices);
//            if(rpVertices.getSoc()>15) {
////                tv.setText("SOC: " + rpVertices.getSoc());
//                tv.setTextColor(Color.GREEN);
//            }
//            else {
//                tv.setTextColor(Color.RED);
//            }
//            String str=location.getLocation(rpVertices.getNearest_lat(),rpVertices.getNearest_lon());
//            tv.setText("SOC: " + rpVertices.getSoc()+"\nAddress: "+str);


    }

    private void oneMinuteIntervalThreadCall() {


        final int delay = 1000*5; // 1000 milliseconds == 1 second
        makePolyGoneCall();
        handler.postDelayed(new Runnable() {
            int i=0;

            public void run() {

                    makePolyGoneCall();
                    handler.postDelayed(this, delay);

            }
        }, delay);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}