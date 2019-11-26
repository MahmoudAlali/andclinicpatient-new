package com.dcoret.beautyclient.DataClass;

import java.util.ArrayList;

public class ReservationModel {
    String bookingType;
    String totalPrice;
    String startTime;
    String place,client_name,booking_price;
    String bdb_inner_booking;
    ArrayList<BookingAutomatedBrowseData> data;

    public ReservationModel(String bookingType, String totalPrice, String startTime, String place,String client_name,String bdb_inner_booking, ArrayList<BookingAutomatedBrowseData> data) {
        this.bookingType = bookingType;
        this.totalPrice = totalPrice;
        this.startTime = startTime;
        this.place = place;
        this.data = data;
        this.client_name = client_name;
        this.booking_price = booking_price;
        this.bdb_inner_booking=bdb_inner_booking;
    }


    public String getBooking_price() {
        return booking_price;
    }

    public String getBdb_inner_booking() {
        return bdb_inner_booking;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public ArrayList<BookingAutomatedBrowseData> getData() {
        return data;
    }

    public void setData(ArrayList<BookingAutomatedBrowseData> data) {
        this.data = data;
    }
}
