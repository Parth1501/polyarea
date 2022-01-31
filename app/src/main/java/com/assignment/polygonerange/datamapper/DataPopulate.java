package com.assignment.polygonerange.datamapper;

import com.assignment.polygonerange.api.LocalDataApi;
import com.assignment.polygonerange.model.RPVerticesRequest;

public class DataPopulate {
    public RPVerticesRequest[] populateRPVerticesArray() {
        RPVerticesRequest[] array=new RPVerticesRequest[6];
        array[0]=new RPVerticesRequest(LocalDataApi.LAT_1,LocalDataApi.LON_1,LocalDataApi.SOC_1);
        array[1]=new RPVerticesRequest(LocalDataApi.LAT_2,LocalDataApi.LON_2,LocalDataApi.SOC_2);
        array[2]=new RPVerticesRequest(LocalDataApi.LAT_3,LocalDataApi.LON_3,LocalDataApi.SOC_3);
        array[3]=new RPVerticesRequest(LocalDataApi.LAT_4,LocalDataApi.LON_4,LocalDataApi.SOC_4);
        array[4]=new RPVerticesRequest(LocalDataApi.LAT_5,LocalDataApi.LON_5,LocalDataApi.SOC_5);
        array[5]=new RPVerticesRequest(LocalDataApi.LAT_6,LocalDataApi.LON_6,LocalDataApi.SOC_6);
        return array;
    }
}
