package com.dcoret.beautyclient.DataClass;

public class AddToCart {
    String     bdb_ser_sup_id,
            bdb_employee_id,
            bdb_price,
            bdb_ser_salon,
            bdb_ser_home,
            bdb_ser_hall,
            bdb_ser_hotel,
            bdb_start_date,
            bdb_start_time;

    public AddToCart(String bdb_ser_sup_id, String bdb_employee_id, String bdb_price, String bdb_ser_salon, String bdb_ser_home, String bdb_ser_hall, String bdb_ser_hotel, String bdb_start_date, String bdb_start_time) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
        this.bdb_employee_id = bdb_employee_id;
        this.bdb_price = bdb_price;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_ser_hotel = bdb_ser_hotel;
        this.bdb_start_date = bdb_start_date;
        this.bdb_start_time = bdb_start_time;
    }

    public String getBdb_ser_sup_id() {
        return bdb_ser_sup_id;
    }

    public void setBdb_ser_sup_id(String bdb_ser_sup_id) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
    }

    public String getBdb_employee_id() {
        return bdb_employee_id;
    }

    public void setBdb_employee_id(String bdb_employee_id) {
        this.bdb_employee_id = bdb_employee_id;
    }

    public String getBdb_price() {
        return bdb_price;
    }

    public void setBdb_price(String bdb_price) {
        this.bdb_price = bdb_price;
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

    public String getBdb_ser_hotel() {
        return bdb_ser_hotel;
    }

    public void setBdb_ser_hotel(String bdb_ser_hotel) {
        this.bdb_ser_hotel = bdb_ser_hotel;
    }

    public String getBdb_start_date() {
        return bdb_start_date;
    }

    public void setBdb_start_date(String bdb_start_date) {
        this.bdb_start_date = bdb_start_date;
    }

    public String getBdb_start_time() {
        return bdb_start_time;
    }

    public void setBdb_start_time(String bdb_start_time) {
        this.bdb_start_time = bdb_start_time;
    }
}
