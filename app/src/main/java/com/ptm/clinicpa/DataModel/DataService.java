package com.ptm.clinicpa.DataModel;

public class DataService {

    String name;
    String provider_name;
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

    String city;
    Location_Beauty location_beauty;
//    boolean[] fav;

        //  provider name is missing
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


    public DataService(int offerid,String name,String provider_name, double price, double rating,String city,Location_Beauty location_beauty, boolean fav,boolean isoffer) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.fav = fav;
        this.provider_name=provider_name;
        this.isoffer=isoffer;
        this.offerid=offerid;
        this.location_beauty=location_beauty;
        this.city=city;
    }

    public DataService(int offerid,String name,String provider_name, double price, double rating, boolean fav,boolean isoffer){
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.fav = fav;
        this.provider_name=provider_name;
        this.isoffer=isoffer;
        this.offerid=offerid;


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

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String[] getCities() {
        return cities;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
    }

    public Location_Beauty[] getLocation_beauties() {
        return location_beauties;
    }

    public void setLocation_beauties(Location_Beauty[] location_beauties) {
        this.location_beauties = location_beauties;
    }
}
