package com.ptm.clinicpa.DataModel;

public class FavoriteModel {
    String  bdb_fav_id,
            bdb_name,
            bdb_name_ar,
            bdb_type,new_fav,bdb_cat_id,bdb_logo_id,
    bdb_item_id;

    public FavoriteModel(String bdb_fav_id, String bdb_name, String bdb_name_ar, String bdb_type, String bdb_item_id,String bdb_cat_id,String bdb_logo_id) {
        this.bdb_fav_id = bdb_fav_id;
        this.bdb_name = bdb_name;
        this.bdb_name_ar = bdb_name_ar;
        this.bdb_type = bdb_type;
        this.bdb_item_id=bdb_item_id;
        this.bdb_cat_id=bdb_cat_id;
        this.bdb_logo_id=bdb_logo_id;

        new_fav="1";

    }

    public String getBdb_fav_id() {
        return bdb_fav_id;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public String getBdb_name_ar() {
        return bdb_name_ar;
    }

    public String getBdb_type() {
        return bdb_type;
    }

    public String getBdb_item_id() {
        return bdb_item_id;
    }

    public void setNew_fav(String new_fav) {
        this.new_fav = new_fav;
    }

    public String getBdb_cat_id() {
        return bdb_cat_id;
    }

    public String getBdb_logo_id() {
        return bdb_logo_id;
    }

    public String getNew_fav() {
        return new_fav;
    }
}
