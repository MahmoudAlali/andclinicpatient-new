package com.dcoret.beautyclient.DataClass;

import com.google.android.gms.maps.model.LatLng;

public class LocationTitles {
    int id;
    LatLng latLng;
    String title;

    public LocationTitles(int id,LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
        this.id=id;
    }
    public LocationTitles(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
        this.id=id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
