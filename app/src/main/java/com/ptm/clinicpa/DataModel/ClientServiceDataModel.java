package com.ptm.clinicpa.DataModel;

public class ClientServiceDataModel {
    private String bdb_ser_id,
            bdb_ser_sup_id,
            bdb_name,
            bdb_name_ar,
            cost,
            time,
            rounded_time,
            bdb_cat_id;

    public ClientServiceDataModel(String bdb_ser_id, String bdb_ser_sup_id, String bdb_name, String bdb_name_ar, String bdb_cat_id)
    {
        this.bdb_ser_id=bdb_ser_id;
        this.bdb_ser_sup_id=bdb_ser_sup_id;
        this.bdb_name=bdb_name;
        this.bdb_name_ar=bdb_name_ar;
        this.bdb_cat_id=bdb_cat_id;

    }
    public ClientServiceDataModel(String bdb_ser_id, String bdb_ser_sup_id, String bdb_name, String bdb_name_ar, String bdb_cat_id,
                                  String cost,String time,
                                  String rounded_time)
    {
        this.bdb_ser_id=bdb_ser_id;
        this.bdb_ser_sup_id=bdb_ser_sup_id;
        this.bdb_name=bdb_name;
        this.bdb_name_ar=bdb_name_ar;
        this.bdb_cat_id=bdb_cat_id;
        this.time=time;
        this.cost=cost;
        this.rounded_time=rounded_time;

    }
    public ClientServiceDataModel(String bdb_ser_id, String bdb_name, String bdb_name_ar)
    {
        this.bdb_ser_id=bdb_ser_id;
        this.bdb_name=bdb_name;
        this.bdb_name_ar=bdb_name_ar;
    }
    public ClientServiceDataModel(String bdb_ser_id,String bdb_ser_sup_id, String bdb_name, String bdb_name_ar)
    {
        this.bdb_ser_id=bdb_ser_id;
        this.bdb_name=bdb_name;
        this.bdb_name_ar=bdb_name_ar;
        this.bdb_ser_sup_id=bdb_ser_sup_id;
    }

    public String getBdb_cat_id() {
        return bdb_cat_id;
    }

    public String getBdb_name_ar() {
        return bdb_name_ar;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public String getBdb_ser_id() {
        return bdb_ser_id;
    }

    public String getBdb_ser_sup_id() {
        return bdb_ser_sup_id;
    }

    public String getCost() {
        return cost;
    }

    public String getTime() {
        return time;
    }

    public String getRounded_time() {
        return rounded_time;
    }
}
