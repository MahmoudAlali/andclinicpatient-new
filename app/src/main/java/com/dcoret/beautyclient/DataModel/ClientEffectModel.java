package com.dcoret.beautyclient.DataModel;

import java.util.ArrayList;

public class ClientEffectModel {
    String cat_id,cat_name,cat_name_ar,client_name;
    ArrayList<Effects> effects;

    public ClientEffectModel(String cat_id, String cat_name, String cat_name_ar, ArrayList<Effects> effects) {

        this.cat_id = cat_id;
        this.cat_name = cat_name;

        this.cat_name_ar = cat_name_ar;
        this.effects = effects;
    }
    public ClientEffectModel(String cat_id, String cat_name, String cat_name_ar,String client_name, ArrayList<Effects> effects) {

        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.client_name= client_name;

        this.cat_name_ar = cat_name_ar;
        this.effects = effects;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_name_ar() {
        return cat_name_ar;
    }

    public void setCat_name_ar(String cat_name_ar) {
        this.cat_name_ar = cat_name_ar;
    }

    public ArrayList<Effects> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effects> effects) {
        this.effects = effects;
    }

    public static class Effects{
        String bdb_effect_id,bdb_client_id,bdb_effect_name_ar,bdb_effect_name_en,bdb_value,bdb_effect_client_id;

        public Effects(String bdb_effect_id, String bdb_client_id, String bdb_effect_name_ar, String bdb_effect_name_en, String bdb_value, String bdb_effect_client_id) {
            this.bdb_effect_id = bdb_effect_id;
            this.bdb_client_id = bdb_client_id;
            this.bdb_effect_name_ar = bdb_effect_name_ar;
            this.bdb_effect_name_en = bdb_effect_name_en;
            this.bdb_value = bdb_value;
            this.bdb_effect_client_id = bdb_effect_client_id;
        }


        public String getBdb_effect_id() {
            return bdb_effect_id;
        }

        public void setBdb_effect_id(String bdb_effect_id) {
            this.bdb_effect_id = bdb_effect_id;
        }

        public String getBdb_client_id() {
            return bdb_client_id;
        }

        public void setBdb_client_id(String bdb_client_id) {
            this.bdb_client_id = bdb_client_id;
        }

        public String getBdb_effect_name_ar() {
            return bdb_effect_name_ar;
        }

        public void setBdb_effect_name_ar(String bdb_effect_name_ar) {
            this.bdb_effect_name_ar = bdb_effect_name_ar;
        }

        public String getBdb_effect_name_en() {
            return bdb_effect_name_en;
        }

        public void setBdb_effect_name_en(String bdb_effect_name_en) {
            this.bdb_effect_name_en = bdb_effect_name_en;
        }

        public String getBdb_value() {
            return bdb_value;
        }

        public void setBdb_value(String bdb_value) {
            this.bdb_value = bdb_value;
        }

        public String getBdb_effect_client_id() {
            return bdb_effect_client_id;
        }

        public void setBdb_effect_client_id(String bdb_effect_client_id) {
            this.bdb_effect_client_id = bdb_effect_client_id;
        }
    }

}
