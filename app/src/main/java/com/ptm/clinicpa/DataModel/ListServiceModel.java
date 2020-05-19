package com.ptm.clinicpa.DataModel;

public class ListServiceModel {
String  bdb_ser_id,
        bdb_name,
        bdb_name_ar,
        bdb_descr,
        bdb_type,
        bdb_is_fixed_price,
        bdb_is_fixed_time,
        bdb_is_hair_service,
        bdb_is_bride_service,
        images,
        is_fav,
        new_fav;


    public ListServiceModel(String bdb_ser_id, String bdb_name, String bdb_name_ar, String bdb_descr, String bdb_type, String bdb_is_fixed_price, String bdb_is_fixed_time, String bdb_is_hair_service, String bdb_is_bride_service, String images,String is_fav) {
        this.bdb_ser_id = bdb_ser_id;
        this.bdb_name = bdb_name;
        this.bdb_name_ar = bdb_name_ar;
        this.bdb_descr = bdb_descr;
        this.bdb_type = bdb_type;
        this.bdb_is_fixed_price = bdb_is_fixed_price;
        this.bdb_is_fixed_time = bdb_is_fixed_time;
        this.bdb_is_hair_service = bdb_is_hair_service;
        this.bdb_is_bride_service = bdb_is_bride_service;
        this.images = images;
        this.is_fav=is_fav;
        new_fav=is_fav;
    }

    public String getBdb_ser_id() {
        return bdb_ser_id;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public String getBdb_name_ar() {
        return bdb_name_ar;
    }

    public String getBdb_descr() {
        return bdb_descr;
    }

    public String getBdb_type() {
        return bdb_type;
    }

    public String getBdb_is_fixed_price() {
        return bdb_is_fixed_price;
    }

    public String getBdb_is_fixed_time() {
        return bdb_is_fixed_time;
    }

    public String getBdb_is_hair_service() {
        return bdb_is_hair_service;
    }

    public String getBdb_is_bride_service() {
        return bdb_is_bride_service;
    }

    public String getImages() {
        return images;
    }

    public String getIs_fav() {
        return is_fav;
    }

    public void setIs_fav(String is_fav) {
        this.is_fav = is_fav;
    }

    public String getNew_fav() {
        return new_fav;
    }

    public void setNew_fav(String new_fav) {
        this.new_fav = new_fav;
    }
}
