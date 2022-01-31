package com.assignment.polygonerange.model;

import java.util.List;

public class RPVertices {
    List<List<Double>> rp_vertices;
    double latitude;
    double longitude;
    double nearest_lat;
    double nearest_lon;
    int soc;

    public RPVertices(List<List<Double>> rp_vertices, double latitude, double longitude, double nearest_lat, double nearest_lon, int soc) {
        this.rp_vertices = rp_vertices;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nearest_lat = nearest_lat;
        this.nearest_lon = nearest_lon;
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

    public double getNearest_lat() {
        return nearest_lat;
    }

    public void setNearest_lat(double nearest_lat) {
        this.nearest_lat = nearest_lat;
    }

    public double getNearest_lon() {
        return nearest_lon;
    }

    public void setNearest_lon(double nearest_lon) {
        this.nearest_lon = nearest_lon;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    public RPVertices(List<List<Double>> rp_vertices, double nearest_lat, double nearest_lon, int soc) {
        this.rp_vertices = rp_vertices;
        this.nearest_lat = nearest_lat;
        this.nearest_lon = nearest_lon;
        this.soc = soc;
    }

    public RPVertices(List<List<Double>> rp_vertices) {
        this.rp_vertices = rp_vertices;
    }

    public RPVertices() {
    }

    public List<List<Double>> getRp_vertices() {
        return rp_vertices;
    }

    public void setRp_vertices(List<List<Double>> rp_vertices) {
        this.rp_vertices = rp_vertices;
    }
}
