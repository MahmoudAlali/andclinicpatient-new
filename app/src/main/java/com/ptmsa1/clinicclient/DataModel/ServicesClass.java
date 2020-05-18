package com.ptmsa1.clinicclient.DataModel;

public class ServicesClass {
    String bdb_ser_sup_id
            ,bdb_ser_salon
            ,bdb_ser_home
            ,bdb_ser_hall
            ,bdb_ser_salon_price
            ,bdb_ser_home_price
            ,bdb_ser_hall_price
            ,bdb_time

            ,bdb_hotel
            ,bdb_hotel_price
            ,TotalRating
            ,bdb_ser_id
            ,bdb_name
            ,bdb_descr
            ,bdb_type
            ,bdb_name_ar
            ,bdb_is_fixed_time
            ,bdb_is_hair_service
            //            ,bdb_max_clients
//            ,bdb_ser_id
//            ,bdb_sup_id
//            ,bdb_updated_at
//            ,bdb_is_offer
//            ,bdb_offer_status
//            ,bdb_offer_start
//            ,bdb_offer_end
//            ,bdb_pack_code
//            ,bdb_is_best
//            ,bdb_orgin_id
//            ,bdb_is_current_price
            ;


    public ServicesClass(String bdb_ser_sup_id, String bdb_ser_salon, String bdb_ser_home, String bdb_ser_hall, String bdb_ser_salon_price, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_time, String bdb_hotel, String bdb_hotel_price, String totalRating, String bdb_ser_id, String bdb_name, String bdb_descr, String bdb_type, String bdb_name_ar, String bdb_is_fixed_time, String bdb_is_hair_service) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_ser_salon_price = bdb_ser_salon_price;
        this.bdb_ser_home_price = bdb_ser_home_price;
        this.bdb_ser_hall_price = bdb_ser_hall_price;
        this.bdb_time = bdb_time;
        this.bdb_hotel = bdb_hotel;
        this.bdb_hotel_price = bdb_hotel_price;
        TotalRating = totalRating;
        this.bdb_ser_id = bdb_ser_id;
        this.bdb_name = bdb_name;
        this.bdb_descr = bdb_descr;
        this.bdb_type = bdb_type;
        this.bdb_name_ar = bdb_name_ar;
        this.bdb_is_fixed_time = bdb_is_fixed_time;
        this.bdb_is_hair_service = bdb_is_hair_service;
    }


    public String getBdb_ser_sup_id() {
        return bdb_ser_sup_id;
    }

    public void setBdb_ser_sup_id(String bdb_ser_sup_id) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
    }

    public String getBdb_ser_salon() {
        return bdb_ser_salon;
    }

    public void setBdb_ser_salon(String bdb_ser_salon) {
        this.bdb_ser_salon = bdb_ser_salon;
    }

    public String getBdb_ser_home() {
        return bdb_ser_home;
    }

    public void setBdb_ser_home(String bdb_ser_home) {
        this.bdb_ser_home = bdb_ser_home;
    }

    public String getBdb_ser_hall() {
        return bdb_ser_hall;
    }

    public void setBdb_ser_hall(String bdb_ser_hall) {
        this.bdb_ser_hall = bdb_ser_hall;
    }

    public String getBdb_ser_salon_price() {
        return bdb_ser_salon_price;
    }

    public void setBdb_ser_salon_price(String bdb_ser_salon_price) {
        this.bdb_ser_salon_price = bdb_ser_salon_price;
    }

    public String getBdb_ser_home_price() {
        return bdb_ser_home_price;
    }

    public void setBdb_ser_home_price(String bdb_ser_home_price) {
        this.bdb_ser_home_price = bdb_ser_home_price;
    }

    public String getBdb_ser_hall_price() {
        return bdb_ser_hall_price;
    }

    public void setBdb_ser_hall_price(String bdb_ser_hall_price) {
        this.bdb_ser_hall_price = bdb_ser_hall_price;
    }

    public String getBdb_time() {
        return bdb_time;
    }

    public void setBdb_time(String bdb_time) {
        this.bdb_time = bdb_time;
    }

    public String getBdb_hotel() {
        return bdb_hotel;
    }

    public void setBdb_hotel(String bdb_hotel) {
        this.bdb_hotel = bdb_hotel;
    }

    public String getBdb_hotel_price() {
        return bdb_hotel_price;
    }

    public void setBdb_hotel_price(String bdb_hotel_price) {
        this.bdb_hotel_price = bdb_hotel_price;
    }

    public String getTotalRating() {
        return TotalRating;
    }

    public void setTotalRating(String totalRating) {
        TotalRating = totalRating;
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

    public String getBdb_name_ar() {
        return bdb_name_ar;
    }

    public void setBdb_name_ar(String bdb_name_ar) {
        this.bdb_name_ar = bdb_name_ar;
    }

    public String getBdb_is_fixed_time() {
        return bdb_is_fixed_time;
    }

    public void setBdb_is_fixed_time(String bdb_is_fixed_time) {
        this.bdb_is_fixed_time = bdb_is_fixed_time;
    }

    public String getBdb_is_hair_service() {
        return bdb_is_hair_service;
    }

    public void setBdb_is_hair_service(String bdb_is_hair_service) {
        this.bdb_is_hair_service = bdb_is_hair_service;
    }
}
