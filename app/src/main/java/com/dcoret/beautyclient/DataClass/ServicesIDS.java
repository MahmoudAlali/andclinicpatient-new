package com.dcoret.beautyclient.DataClass;

public class ServicesIDS {
    String id,name,viewnum;

    public ServicesIDS(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ServicesIDS(String id, String name, String viewnum) {
        this.id = id;
        this.name = name;
        this.viewnum = viewnum;
    }

    public String getViewnum() {
        return viewnum;
    }

    public void setViewnum(String viewnum) {
        this.viewnum = viewnum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
