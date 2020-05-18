package com.ptmsa1.clinicclient.DataModel;

public class GroupOfferClass {
    String date,serviceName;

    public GroupOfferClass(String date, String serviceName) {
        this.date = date;
        this.serviceName = serviceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
