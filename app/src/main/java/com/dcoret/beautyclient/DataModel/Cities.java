package com.dcoret.beautyclient.DataModel;

public class Cities {
    String bdb_id,bdb_region_id,bdb_name,bdb_name_ar,bdb_default_dim;

    public Cities(String bdb_id, String bdb_region_id, String bdb_name, String bdb_name_ar, String bdb_default_dim) {
        this.bdb_id = bdb_id;
        this.bdb_region_id = bdb_region_id;
        this.bdb_name = bdb_name;
        this.bdb_name_ar = bdb_name_ar;
        this.bdb_default_dim = bdb_default_dim;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public void setBdb_id(String bdb_id) {
        this.bdb_id = bdb_id;
    }

    public String getBdb_region_id() {
        return bdb_region_id;
    }

    public void setBdb_region_id(String bdb_region_id) {
        this.bdb_region_id = bdb_region_id;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public void setBdb_name(String bdb_name) {
        this.bdb_name = bdb_name;
    }

    public String getBdb_name_ar() {
        return bdb_name_ar;
    }

    public void setBdb_name_ar(String bdb_name_ar) {
        this.bdb_name_ar = bdb_name_ar;
    }

    public String getBdb_default_dim() {
        return bdb_default_dim;
    }

    public void setBdb_default_dim(String bdb_default_dim) {
        this.bdb_default_dim = bdb_default_dim;
    }
}
