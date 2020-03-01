package com.dcoret.beautyclient.DataModel;

import java.util.ArrayList;

public class DaitelsOfferReservationClass {
    String name,phone,date,time;
    ArrayList serviceList;

    public DaitelsOfferReservationClass(String name, String phone, String date, String time, ArrayList serviceList) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.serviceList = serviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList serviceList) {
        this.serviceList = serviceList;
    }
}
