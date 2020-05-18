package com.ptmsa1.clinicclient.DataModel;

import java.util.ArrayList;

public class ReservationModel {
    String bookingType;
    String totalPrice;
    String bdb_name_booking;
    String startTime;
    String place,client_name,booking_price;
    String bdb_inner_booking;
    String bdb_is_executed;
    String logoId;
    String booked_by_me;
    String is_action_on;
    String is_rating_on;
    String bdb_refund_days;
    String bdb_refund_hours;
    String bdb_refund_amount;
    ArrayList<BookingAutomatedBrowseData> data;


    public ReservationModel(String booked_by_me,String logoId,String bookingType,String bdb_is_executed, String totalPrice, String startTime, String place,String client_name,String bdb_inner_booking, String is_action_on,String is_rating_on,String bdb_name_booking, ArrayList<BookingAutomatedBrowseData> data) {
        this.bookingType = bookingType;
        this.totalPrice = totalPrice;
        this.startTime = startTime;
        this.place = place;
        this.data = data;
        this.client_name = client_name;
        this.booking_price = booking_price;
        this.bdb_inner_booking=bdb_inner_booking;
        this.bdb_is_executed=bdb_is_executed;
        this.logoId=logoId;
        this.booked_by_me=booked_by_me;
        this.is_action_on=is_action_on;
        this.is_rating_on=is_rating_on;
        this.bdb_name_booking=bdb_name_booking;
    }


    String is_per_client,bdb_internally_number;
    public ReservationModel(String booked_by_me,String logoId,String bookingType,String bdb_is_executed, String totalPrice, String startTime, String place,String client_name,String bdb_inner_booking, String is_action_on,String is_rating_on,String bdb_name_booking,String is_per_client, ArrayList<BookingAutomatedBrowseData> data) {
        this.bookingType = bookingType;
        this.totalPrice = totalPrice;
        this.startTime = startTime;
        this.place = place;
        this.data = data;
        this.client_name = client_name;
        this.booking_price = booking_price;
        this.bdb_inner_booking=bdb_inner_booking;
        this.bdb_is_executed=bdb_is_executed;
        this.logoId=logoId;
        this.booked_by_me=booked_by_me;
        this.is_action_on=is_action_on;
        this.is_rating_on=is_rating_on;
        this.bdb_name_booking=bdb_name_booking;
        this.is_per_client=is_per_client;
    }
    String bdb_expected_deposit,bdb_loc_lat,bdb_loc_long;
    public ReservationModel(String bdb_internally_number,String booked_by_me,String logoId,String bookingType,String bdb_is_executed, String totalPrice, String startTime, String place,String client_name,String bdb_inner_booking, String is_action_on,String is_rating_on,String bdb_name_booking,String is_per_client,String bdb_expected_deposit,String bdb_loc_lat,String bdb_loc_long, ArrayList<BookingAutomatedBrowseData> data,String bdb_refund_amount,String bdb_refund_days,String bdb_refund_hours) {
        this.bookingType = bookingType;
        this.totalPrice = totalPrice;
        this.startTime = startTime;
        this.place = place;
        this.data = data;
        this.client_name = client_name;
        this.booking_price = booking_price;
        this.bdb_inner_booking=bdb_inner_booking;
        this.bdb_is_executed=bdb_is_executed;
        this.logoId=logoId;
        this.booked_by_me=booked_by_me;
        this.is_action_on=is_action_on;
        this.is_rating_on=is_rating_on;
        this.bdb_name_booking=bdb_name_booking;
        this.is_per_client=is_per_client;
        this.bdb_expected_deposit=bdb_expected_deposit;
        this.bdb_internally_number=bdb_internally_number;
        this.bdb_loc_lat=bdb_loc_lat;
        this.bdb_loc_long=bdb_loc_long;
        this.bdb_refund_amount=bdb_refund_amount;
        this.bdb_refund_days=bdb_refund_days;
        this.bdb_refund_hours=bdb_refund_hours;
    }

    public String getBdb_loc_lat() {
        return bdb_loc_lat;
    }

    public void setBdb_loc_lat(String bdb_loc_lat) {
        this.bdb_loc_lat = bdb_loc_lat;
    }

    public String getBdb_loc_long() {
        return bdb_loc_long;
    }

    public void setBdb_loc_long(String bdb_loc_long) {
        this.bdb_loc_long = bdb_loc_long;
    }

    public void setIs_per_client(String is_per_client) {
        this.is_per_client = is_per_client;
    }

    public void setBdb_internally_number(String bdb_internally_number) {
        this.bdb_internally_number = bdb_internally_number;
    }

    public String getBdb_expected_deposit() {
        return bdb_expected_deposit;
    }

    public void setBdb_expected_deposit(String bdb_expected_deposit) {
        this.bdb_expected_deposit = bdb_expected_deposit;
    }

    public String getBdb_internally_number() {
        return bdb_internally_number;
    }

    public String getIs_per_client() {
        return is_per_client;
    }

    public String getBdb_name_booking() {
        return bdb_name_booking;
    }

    public void setBdb_name_booking(String bdb_name_booking) {
        this.bdb_name_booking = bdb_name_booking;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public void setBooked_by_me(String booked_by_me) {
        this.booked_by_me = booked_by_me;
    }

    public String getIs_action_on() {
        return is_action_on;
    }

    public void setIs_action_on(String is_action_on) {
        this.is_action_on = is_action_on;
    }

    public String getIs_rating_on() {
        return is_rating_on;
    }

    public void setIs_rating_on(String is_rating_on) {
        this.is_rating_on = is_rating_on;
    }

    public void setBooking_price(String booking_price) {
        this.booking_price = booking_price;
    }

    public void setBdb_inner_booking(String bdb_inner_booking) {
        this.bdb_inner_booking = bdb_inner_booking;
    }

    public String getBdb_is_executed() {
        return bdb_is_executed;
    }

    public void setBdb_is_executed(String bdb_is_executed) {
        this.bdb_is_executed = bdb_is_executed;
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

    public String getLogoId() {
        return logoId;
    }

    public String getBooked_by_me() {
        return booked_by_me;
    }

    public String getBdb_refund_amount() {
        return bdb_refund_amount;
    }

    public String getBdb_refund_days() {
        return bdb_refund_days;
    }

    public String getBdb_refund_hours() {
        return bdb_refund_hours;
    }
}
