package com.ptmsa1.clinicclient.DataModel;

import java.util.ArrayList;

public class DataReservation {
    int id;
    String name;
    double price;
    double rate;
    boolean isoffer;
    int offerid;
    ArrayList<String> times;

    public DataReservation( String name, double price, double rate, boolean isoffer, int offerid, ArrayList<String> times) {
//        this.id = id;
        this.name = name;
        this.price = price;
        this.rate = rate;
        this.isoffer = isoffer;
        this.offerid = offerid;
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isIsoffer() {
        return isoffer;
    }

    public void setIsoffer(boolean isoffer) {
        this.isoffer = isoffer;
    }

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }
}
