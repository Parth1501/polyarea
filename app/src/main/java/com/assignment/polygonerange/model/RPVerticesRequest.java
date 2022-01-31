package com.assignment.polygonerange.model;


public class RPVerticesRequest {
    private double latitude;
    private double longitude;
    private int soc;

    public RPVerticesRequest(double latitude, double longitude, int soc) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.soc = soc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }
}
