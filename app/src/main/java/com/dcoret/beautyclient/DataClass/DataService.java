package com.dcoret.beautyclient.DataClass;

import android.content.Context;

public class DataService {

    String name;
    double price;
    double rating;
    boolean fav;
    boolean isoffer;
    int offerid;

//    Context context;
//    String items[];
//    String[] price;
//    String[] rank;
    String[] cities;
    Location_Beauty[] location_beauties;
//    boolean[] fav;


    public DataService(int offerid,String name, double price, double rating, boolean fav,boolean isoffer) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.fav = fav;
        this.isoffer=isoffer;
        this.offerid=offerid;
    }


    public DataService(int offerid,String name, double price, double rating,String[] cities,Location_Beauty[] location_beauties, boolean fav,boolean isoffer) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.fav = fav;
        this.isoffer=isoffer;
        this.offerid=offerid;
        this.location_beauties=location_beauties;
        this.cities=cities;
    }

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public boolean isIsoffer() {
        return isoffer;
    }

    public void setIsoffer(boolean isoffer) {
        this.isoffer = isoffer;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
