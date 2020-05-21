package com.ptm.clinicpa.DataModel;

public class ServiceItems {
    String bdb_ser_id
            ,bdb_name
            ,bdb_name_ar
            ,bdb_descr
            ,bdb_type
            ,bdb_is_fixed_price
            ,bdb_is_bride_service
            ,images;



    public ServiceItems(String bdb_ser_id, String bdb_name, String bdb_name_ar, String bdb_descr, String bdb_type, String bdb_is_fixed_price,String bdb_is_bride_service, String images) {
        this.bdb_ser_id = bdb_ser_id;
        this.bdb_name = bdb_name;
        this.bdb_name_ar = bdb_name_ar;
        this.bdb_descr = bdb_descr;
        this.bdb_type = bdb_type;
        this.bdb_is_fixed_price = bdb_is_fixed_price;
        this.bdb_is_bride_service = bdb_is_bride_service;
        this.images = images;
    }
    public ServiceItems(String bdb_ser_id, String bdb_name, String bdb_name_ar) {
        this.bdb_ser_id = bdb_ser_id;
        this.bdb_name = bdb_name;
        this.bdb_name_ar = bdb_name_ar;
    }



    public String getBdb_is_bride_service() {
        return bdb_is_bride_service;
    }

    public String getBdb_ser_id() {
        return bdb_ser_id;
    }

    public void setBdb_ser_id(String bdb_ser_id) {
        this.bdb_ser_id = bdb_ser_id;
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

    public String getBdb_descr() {
        return bdb_descr;
    }

    public void setBdb_descr(String bdb_descr) {
        this.bdb_descr = bdb_descr;
    }

    public String getBdb_type() {
        return bdb_type;
    }

    public void setBdb_type(String bdb_type) {
        this.bdb_type = bdb_type;
    }

    public String getBdb_is_fixed_price() {
        return bdb_is_fixed_price;
    }

    public void setBdb_is_fixed_price(String bdb_is_fixed_price) {
        this.bdb_is_fixed_price = bdb_is_fixed_price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
