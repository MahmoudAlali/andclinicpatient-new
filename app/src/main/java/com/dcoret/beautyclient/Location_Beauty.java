package com.dcoret.beautyclient;

public class Location_Beauty {

    Double latitude;
    Double longtude;

    public Location_Beauty(Double latitude, Double longtude) {
        this.latitude = latitude;
        this.longtude = longtude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtude() {
        return longtude;
    }

    public void setLongtude(Double longtude) {
        this.longtude = longtude;
    }
}
