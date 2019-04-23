package com.dcoret.beautyclient.DataClass;

public class DataOffer {

    String name;
    DataService[] services;
    double price;
    boolean fav;

    public DataOffer(String name, DataService[] services, double price, boolean fav) {
        this.name = name;
        this.services = services;
        this.price = price;
        this.fav = fav;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataService[] getServices() {
        return services;
    }

    public void setServices(DataService[] services) {
        this.services = services;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
