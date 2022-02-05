package com.assignment.polygonerange.api;

import com.assignment.polygonerange.model.RpVerticesApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {
        String BASE_URL = "https://intense-plains-09400.herokuapp.com";
        @GET("/GetRPVertices/?Veh_ID=1&Ign_on=true&alt=170.1&accy_lat=0.9&accy_long=0.9&accy_alt=0.9&veh_spd=3.1&dis=4000.5&str_ang=1.1&acc_pdl=1.1&brk_prs=true&frnt_lft=100.1&frnt_ryt=100.1&rear_lft=100.1&rear_ryt=100.1&ev_gear=1&hv_volt=320.2&hv_temp=32.2&out_temp=25.5&intr_temp=25.06&mtr_cur=100.1&mtr_volt=100.1&mtr_trq=100.1&mtr_spd=100.1&mtr_temp=100.1&batt_cur=100.1&Amp_hr=100.1&Amp_hr_regen=100.1&Batt_kwh=100.1&DCDC_vlt=100.1&DCDC_cur=100.1&batt_vlt_12=100.1&ener_drv=100.1&ener_aux=100.1&ener_cool=100.1&regen_eff=100.1")
        Call<RpVerticesApi> getVertices(@Query("lat") double lat,
                                           @Query("long") double lon,
                                           @Query("Soc") int soc);

}
