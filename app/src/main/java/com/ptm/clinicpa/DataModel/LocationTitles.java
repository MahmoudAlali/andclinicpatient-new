package com.ptm.clinicpa.DataModel;

import com.google.android.gms.maps.model.LatLng;

public class LocationTitles {


    //          "bdb_id"
//          "bdb_user_id"
//          "bdb_city_id"
//          "bdb_loc_lat"
//          "bdb_sup_id"
//          "bdb_descr"
//          "bdb_loc_long"
//          "bdb_is_deleted"
//          "bdb_my_descr"
    String id;
    LatLng latLng;
    String bdb_my_descr;
    String bdb_user_id,bdb_city_id,bdb_sup_id,bdb_descr;

    public LocationTitles(String id,LatLng latLng, String bdb_my_descr) {
        this.latLng = latLng;
        this.bdb_my_descr = bdb_my_descr;
        this.id=id;
    }
    public LocationTitles(LatLng latLng, String bdb_my_descr) {
        this.latLng = latLng;
        this.bdb_my_descr = bdb_my_descr;
        this.id=id;
    }

    public LocationTitles(String id, LatLng latLng, String bdb_my_descr, String bdb_user_id, String bdb_city_id, String bdb_sup_id, String bdb_descr) {
        this.id = id;
        this.latLng = latLng;
        this.bdb_my_descr = bdb_my_descr;
        this.bdb_user_id = bdb_user_id;
        this.bdb_city_id = bdb_city_id;
        this.bdb_sup_id = bdb_sup_id;
        this.bdb_descr = bdb_descr;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getBdb_my_descr() {
        return bdb_my_descr;
    }

    public void setBdb_my_descr(String bdb_my_descr) {
        this.bdb_my_descr = bdb_my_descr;
    }

    public String getBdb_user_id() {
        return bdb_user_id;
    }

    public void setBdb_user_id(String bdb_user_id) {
        this.bdb_user_id = bdb_user_id;
    }

    public String getBdb_city_id() {
        return bdb_city_id;
    }

    public void setBdb_city_id(String bdb_city_id) {
        this.bdb_city_id = bdb_city_id;
    }

    public String getBdb_sup_id() {
        return bdb_sup_id;
    }

    public void setBdb_sup_id(String bdb_sup_id) {
        this.bdb_sup_id = bdb_sup_id;
    }

    public String getBdb_descr() {
        return bdb_descr;
    }

    public void setBdb_descr(String bdb_descr) {
        this.bdb_descr = bdb_descr;
    }
}
