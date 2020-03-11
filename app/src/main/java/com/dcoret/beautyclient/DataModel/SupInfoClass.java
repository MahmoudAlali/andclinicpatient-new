package com.dcoret.beautyclient.DataModel;

public class SupInfoClass {


    String name,id,address;

    public SupInfoClass(String name, String id, String address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }
    public SupInfoClass(String name, String id) {
        this.name = name;
        this.id = id;
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
