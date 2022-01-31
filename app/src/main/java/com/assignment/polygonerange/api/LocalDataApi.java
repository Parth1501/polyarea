package com.assignment.polygonerange.api;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.assignment.polygonerange.model.RPVertices;
import com.assignment.polygonerange.util.AppContext;
import com.assignment.polygonerange.util.Constants;
import com.assignment.polygonerange.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocalDataApi {
    public static final double LAT_1=18.9316;
    public static final double LON_1=72.8326;
    public static final int SOC_1=35;

    public static final double LAT_2=18.650005;
    public static final double LON_2=73.8139;
    public static final int SOC_2=75;

    public static final double LAT_3=18.783967;
    public static final double LON_3=73.335064;
    public static final int SOC_3=55;

    public static final double LAT_4=28.5562;
    public static final double LON_4=77.1000;
    public static final int SOC_4=42;

    public static final double LAT_5=8.4834;
    public static final double LON_5=76.9198;
    public static final int SOC_5=65;

    public static final double LAT_6=22.6531;
    public static final double LON_6=88.4449;
    public static final int SOC_6=15;





    public static RPVertices GetRPVertices(double latitude,double longitude,int soc ) throws JSONException {
        String json= Utils.loadJSONFromAsset(AppContext.context, Constants.FILE_NAME);
        JSONObject obj=new JSONObject(Objects.requireNonNull(json));
        JSONArray jsonArray=obj.getJSONArray("coordinates");
        System.out.println(jsonArray);
        JSONArray result=jsonArray.getJSONArray(getCase(latitude,longitude,soc));
        Log.e(TAG, "GetRPVertices: "+result );
        List<List<Double>> vertices=new ArrayList<>();

        for(int i=0;i<result.length();i++) {
            Log.e(TAG, "GetRPVertices: " + result.get(i));
            List<Double> intermediate=new ArrayList();
            double x = result.getJSONArray(i).getDouble(0);
            double y = result.getJSONArray(i).getDouble(1);
            intermediate.add(x);
            intermediate.add(y);
            vertices.add(intermediate);
        }
        double nearest_lat=getNearestLat(latitude,longitude,soc);
        double nearest_lon= getNearestLongitude(latitude,longitude,soc);
        return new RPVertices(vertices,latitude,longitude,nearest_lat,nearest_lon,soc);

    }

    private static  int getCase(double latitude, double longitude, int soc) {

        if(latitude==LAT_1&&longitude==LON_1&&soc==SOC_1)
            return 0;
        else if(latitude==LAT_2&&longitude==LON_2&&soc==SOC_2)
            return 1;
        else if(latitude==LAT_3&&longitude==LON_3&&soc==SOC_3)
            return 2;
        else if(latitude==LAT_4&&longitude==LON_4&&soc==SOC_4)
            return 3;
        else if(latitude==LAT_5&&longitude==LON_5&&soc==SOC_5)
            return 4;
        else if(latitude==LAT_6&&longitude==LON_6&&soc==SOC_6)
            return 5;
        else
            return 0;
    }
    private static  double getNearestLat(double latitude, double longitude, int soc) {

        if(latitude==LAT_1&&longitude==LON_1&&soc==SOC_1)
            return 18.994249;
        else if(latitude==LAT_2&&longitude==LON_2&&soc==SOC_2)
            return 18.662691;
        else if(latitude==LAT_3&&longitude==LON_3&&soc==SOC_3)
            return 18.75906;
        else if(latitude==LAT_4&&longitude==LON_4&&soc==SOC_4)
            return 28.524933;
        else if(latitude==LAT_5&&longitude==LON_5&&soc==SOC_5)
            return -1;
        else if(latitude==LAT_6&&longitude==LON_6&&soc==SOC_6)
            return 22.6319888039941;
        else
            return 0;
    }
    private static  double getNearestLongitude(double latitude, double longitude, int soc) {

        if(latitude==LAT_1&&longitude==LON_1&&soc==SOC_1)
            return 72.824228;
        else if(latitude==LAT_2&&longitude==LON_2&&soc==SOC_2)
            return 73.812068;
        else if(latitude==LAT_3&&longitude==LON_3&&soc==SOC_3)
            return 73.37059;
        else if(latitude==LAT_4&&longitude==LON_4&&soc==SOC_4)
            return 77.044606;
        else if(latitude==LAT_5&&longitude==LON_5&&soc==SOC_5)
            return -1;
        else if(latitude==LAT_6&&longitude==LON_6&&soc==SOC_6)
            return 88.4214050986271;
        else
            return 0;
    }
}
