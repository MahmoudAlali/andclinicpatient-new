package com.ptmsa1.vizage.DataModel;

public class SupInfoClass {


    String name,id,address;
        String bdb_loc_lat,bdb_loc_long;
    public SupInfoClass(String name, String id, String address,String bdb_loc_lat,String bdb_loc_long) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.bdb_loc_lat= bdb_loc_lat;
        this.bdb_loc_long= bdb_loc_long;
    }
    public SupInfoClass(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getBdb_loc_lat() {
        return bdb_loc_lat;
    }

    public void setBdb_loc_lat(String bdb_loc_lat) {
        this.bdb_loc_lat = bdb_loc_lat;
    }

    public String getBdb_loc_long() {
        return bdb_loc_long;
    }

    public void setBdb_loc_long(String bdb_loc_long) {
        this.bdb_loc_long = bdb_loc_long;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
