package com.ptmsa1.clinicclient.DataModel;

import java.util.ArrayList;

public class OfferClientsModel {
    String bdb_pack_code;
    String bdb_offer_place;
    ArrayList<ServiceDetails> serviceDetails;

    public OfferClientsModel(String bdb_pack_code, ArrayList<ServiceDetails> serviceDetails) {
        this.bdb_pack_code = bdb_pack_code;
        this.serviceDetails = serviceDetails;
    }

    public OfferClientsModel(String bdb_pack_code, String bdb_offer_place, ArrayList<ServiceDetails> serviceDetails) {
        this.bdb_pack_code = bdb_pack_code;
        this.bdb_offer_place = bdb_offer_place;
        this.serviceDetails = serviceDetails;
    }

    public String getBdb_offer_place() {
        return bdb_offer_place;
    }

    public void setBdb_offer_place(String bdb_offer_place) {
        this.bdb_offer_place = bdb_offer_place;
    }

    public String getBdb_pack_code() {
        return bdb_pack_code;
    }

    public void setBdb_pack_code(String bdb_pack_code) {
        this.bdb_pack_code = bdb_pack_code;
    }

    public ArrayList<ServiceDetails> getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(ArrayList<ServiceDetails> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public static class ServiceDetails{
        String bdb_ser_sup_id,bdb_ser_id,bdb_name_ar,bdb_ser_name_en,bdb_time,is_bride;

        public ServiceDetails(String bdb_ser_sup_id, String bdb_ser_id, String bdb_name_ar, String bdb_ser_name_en, String is_bride) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_ser_id = bdb_ser_id;
            this.bdb_name_ar = bdb_name_ar;
            this.bdb_ser_name_en = bdb_ser_name_en;
            this.is_bride = is_bride;
        }

        public ServiceDetails(String bdb_ser_sup_id, String bdb_ser_id, String bdb_name_ar, String bdb_ser_name_en, String bdb_time, String is_bride) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_ser_id = bdb_ser_id;
            this.bdb_name_ar = bdb_name_ar;
            this.bdb_ser_name_en = bdb_ser_name_en;
            this.bdb_time = bdb_time;
            this.is_bride = is_bride;
        }

        public String getBdb_time() {
            return bdb_time;
        }

        public void setBdb_time(String bdb_time) {
            this.bdb_time = bdb_time;
        }

        public String getBdb_ser_sup_id() {
            return bdb_ser_sup_id;
        }

        public void setBdb_ser_sup_id(String bdb_ser_sup_id) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
        }

        public String getBdb_ser_id() {
            return bdb_ser_id;
        }

        public void setBdb_ser_id(String bdb_ser_id) {
            this.bdb_ser_id = bdb_ser_id;
        }

        public String getBdb_name_ar() {
            return bdb_name_ar;
        }

        public void setBdb_name_ar(String bdb_name_ar) {
            this.bdb_name_ar = bdb_name_ar;
        }

        public String getBdb_ser_name_en() {
            return bdb_ser_name_en;
        }

        public void setBdb_ser_name_en(String bdb_ser_name_en) {
            this.bdb_ser_name_en = bdb_ser_name_en;
        }

        public String getIs_bride() {
            return is_bride;
        }

        public void setIs_bride(String is_bride) {
            this.is_bride = is_bride;
        }
    }
}
