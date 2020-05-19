package com.ptm.clinicpa.DataModel;

import java.util.ArrayList;

public class MultiDateOfferClass {
    ArrayList<String> serviceList;
    String name;
    String mobileNumebr;
    String age;

    public MultiDateOfferClass(ArrayList<String> serviceList, String name, String mobileNumebr, String age) {
        this.serviceList = serviceList;
        this.name = name;
        this.mobileNumebr = mobileNumebr;
        this.age = age;
    }

    public ArrayList<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<String> serviceList) {
        this.serviceList = serviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumebr() {
        return mobileNumebr;
    }

    public void setMobileNumebr(String mobileNumebr) {
        this.mobileNumebr = mobileNumebr;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
