package com.assignment.polygonerange.util;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Utils {
    public static String json;
    public static String loadJSONFromAsset(Context context,String filename) {
        if(json != null)
            return json;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static LatLng getMidLatLng(LatLng l1,LatLng l2) {
        double mid_x=(l1.latitude+l2.latitude)/2;
        double mid_y=(l1.longitude+l2.longitude)/2;
        return new LatLng(mid_x,mid_y);
    }
    public static LatLng getMidPoint(List<LatLng> list) {
        double sum_x=0,sum_y=0;
        int n=list.size();
        for(LatLng latLng:list) {
            sum_x+=latLng.latitude;
            sum_y+=latLng.longitude;
        }
        return new LatLng(sum_x/n,sum_y/n);
    }

    public static void toast(String str) {
        Toast.makeText(AppContext.context,str,Toast.LENGTH_LONG).show();
    }
}
