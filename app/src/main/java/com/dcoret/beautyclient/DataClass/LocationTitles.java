package com.dcoret.beautyclient.DataClass;

import com.google.android.gms.maps.model.LatLng;

public class LocationTitles {
    LatLng latLng;
    String title;

    public LocationTitles(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
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
