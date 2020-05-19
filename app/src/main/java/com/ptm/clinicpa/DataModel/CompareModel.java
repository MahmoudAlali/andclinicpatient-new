package com.ptm.clinicpa.DataModel;

public class CompareModel {
   String  srName ,
    spname,
    ev ,
    price,
    bdb_ser_home ,
    bdb_ser_salon ,
    bdb_ser_hall ,
    bdb_hotel ,
    place ;


    public CompareModel(String srName, String spname, String ev, String price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel, String place) {
        this.srName = srName;
        this.spname = spname;
        this.ev = ev;
        this.price = price;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_hotel = bdb_hotel;
        this.place = place;
    }

    public String getSrName() {
        return srName;
    }

    public void setSrName(String srName) {
        this.srName = srName;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getEv() {
        return ev;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBdb_ser_home() {
        return bdb_ser_home;
    }

    public void setBdb_ser_home(String bdb_ser_home) {
        this.bdb_ser_home = bdb_ser_home;
    }

    public String getBdb_ser_salon() {
        return bdb_ser_salon;
    }

    public void setBdb_ser_salon(String bdb_ser_salon) {
        this.bdb_ser_salon = bdb_ser_salon;
    }

    public String getBdb_ser_hall() {
        return bdb_ser_hall;
    }

    public void setBdb_ser_hall(String bdb_ser_hall) {
        this.bdb_ser_hall = bdb_ser_hall;
    }

    public String getBdb_hotel() {
        return bdb_hotel;
    }

    public void setBdb_hotel(String bdb_hotel) {
        this.bdb_hotel = bdb_hotel;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
