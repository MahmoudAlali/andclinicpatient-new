package com.ptm.clinicpa.DataModel;

import android.widget.TextView;

public class ServicesIDS {
    String id,name,sertime,viewnum;
    TextView date;

    public ServicesIDS(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ServicesIDS(String id, String name, String viewnum) {
        this.id = id;
        this.name = name;
        this.viewnum = viewnum;
        this.date = date;
    }
    public ServicesIDS(String id, String name, TextView date, String viewnum) {
        this.id = id;
        this.name = name;
        this.viewnum = viewnum;
        this.date = date;
    }
    public ServicesIDS(String id, String name,String sertime, TextView date, String viewnum) {
        this.id = id;
        this.name = name;
        this.viewnum = viewnum;
        this.date = date;
        this.sertime= sertime;
    }

    public String getSertime() {
        return sertime;
    }

    public void setSertime(String sertime) {
        this.sertime = sertime;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getDate() {
        return date;
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
