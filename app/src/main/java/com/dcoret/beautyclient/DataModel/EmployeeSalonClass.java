package com.dcoret.beautyclient.DataModel;

public class EmployeeSalonClass {
    String bdb_id;
    String bdb_name;
    String bdb_owner_name;
    String TotalRating;
    String bdb_sup_id;

    public EmployeeSalonClass(String bdb_id, String bdb_name, String bdb_owner_name, String totalRating) {
        this.bdb_id = bdb_id;
        this.bdb_name = bdb_name;
        this.bdb_owner_name = bdb_owner_name;
        TotalRating = totalRating;
    }

    public EmployeeSalonClass(String bdb_id, String bdb_name, String bdb_owner_name, String totalRating, String bdb_sup_id) {
        this.bdb_id = bdb_id;
        this.bdb_name = bdb_name;
        this.bdb_owner_name = bdb_owner_name;
        TotalRating = totalRating;
        this.bdb_sup_id = bdb_sup_id;
    }


    public String getBdb_sup_id() {
        return bdb_sup_id;
    }

    public void setBdb_sup_id(String bdb_sup_id) {
        this.bdb_sup_id = bdb_sup_id;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public void setBdb_id(String bdb_id) {
        this.bdb_id = bdb_id;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public void setBdb_name(String bdb_name) {
        this.bdb_name = bdb_name;
    }

    public String getBdb_owner_name() {
        return bdb_owner_name;
    }

    public void setBdb_owner_name(String bdb_owner_name) {
        this.bdb_owner_name = bdb_owner_name;
    }

    public String getTotalRating() {
        return TotalRating;
    }

    public void setTotalRating(String totalRating) {
        TotalRating = totalRating;
    }
}
