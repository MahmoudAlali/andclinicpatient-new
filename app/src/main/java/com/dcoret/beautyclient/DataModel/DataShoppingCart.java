package com.dcoret.beautyclient.DataModel;

public class DataShoppingCart {


    int offerId;
    DataService [] services;
    double price;

    public DataShoppingCart(int offerId,DataService[] services, double price) {
        this.services = services;
        this.price = price;
        this.offerId=offerId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
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
}
