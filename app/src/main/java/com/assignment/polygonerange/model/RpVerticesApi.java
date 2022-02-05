package com.assignment.polygonerange.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.ToString;

@ToString
public class RpVerticesApi {
    String id;

    @SerializedName("Charging_stations")
    private List<List<List<Double>>> chargingStations = null;
    @SerializedName("Charging_stations_available")
    private String chargingStationsAvailable;
    @SerializedName("Distance_to_nearest_charging_station_(km):")
    private Double distanceToNearestChargingStationKm;
    @SerializedName("Distance_to_vertices_(km)")
    private List<Double> distanceToVerticesKm = null;
    @SerializedName("Nearest_charging_station")
    private List<List<Double>> nearestChargingStation = null;
    @SerializedName("RP_execution_time_(sec)")
    private Double rPExecutionTimeSec;
    @SerializedName("RP_start_time")
    private String rPStartTime;
    @SerializedName("Vehicle_id")
    private String vehicleId;
    @SerializedName("Vertices")
    private List<List<Double>> vertices = null;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<List<Double>>> getChargingStations() {
        return chargingStations;
    }

    public void setChargingStations(List<List<List<Double>>> chargingStations) {
        this.chargingStations = chargingStations;
    }

    public String getChargingStationsAvailable() {
        return chargingStationsAvailable;
    }

    public void setChargingStationsAvailable(String chargingStationsAvailable) {
        this.chargingStationsAvailable = chargingStationsAvailable;
    }

    public Double getDistanceToNearestChargingStationKm() {
        return distanceToNearestChargingStationKm;
    }

    public void setDistanceToNearestChargingStationKm(Double distanceToNearestChargingStationKm) {
        this.distanceToNearestChargingStationKm = distanceToNearestChargingStationKm;
    }

    public List<Double> getDistanceToVerticesKm() {
        return distanceToVerticesKm;
    }

    public void setDistanceToVerticesKm(List<Double> distanceToVerticesKm) {
        this.distanceToVerticesKm = distanceToVerticesKm;
    }

    public List<List<Double>> getNearestChargingStation() {
        return nearestChargingStation;
    }

    public void setNearestChargingStation(List<List<Double>> nearestChargingStation) {
        this.nearestChargingStation = nearestChargingStation;
    }

    public Double getrPExecutionTimeSec() {
        return rPExecutionTimeSec;
    }

    public void setrPExecutionTimeSec(Double rPExecutionTimeSec) {
        this.rPExecutionTimeSec = rPExecutionTimeSec;
    }

    public String getrPStartTime() {
        return rPStartTime;
    }

    public void setrPStartTime(String rPStartTime) {
        this.rPStartTime = rPStartTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public List<List<Double>> getVertices() {
        return vertices;
    }

    public void setVertices(List<List<Double>> vertices) {
        this.vertices = vertices;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
