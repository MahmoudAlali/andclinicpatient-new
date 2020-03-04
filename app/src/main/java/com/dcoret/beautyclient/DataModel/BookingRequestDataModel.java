package com.dcoret.beautyclient.DataModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class BookingRequestDataModel {
    private String bdb_id,
            bdb_booking_place,
            bdb_location_id,
            bdb_journey_time,
            bdb_journey_cost,
            bdb_status,
            bdb_pack_code,
            bdb_is_group_booking,
            bdb_name_booking,
            bdb_reject_reason,
            bdb_created_at,
            bdb_sup_id,
            bdb_sup_name,
            bdb_client_id;

    ArrayList <BookingRequestClientDataModel> clients = new ArrayList<>();

    public BookingRequestDataModel(String bdb_id, String bdb_booking_place, String bdb_location_id, String bdb_journey_time,
                                   String bdb_journey_cost, String bdb_status, String bdb_pack_code, String bdb_is_group_booking,
                                   String bdb_name_booking, String bdb_reject_reason, String bdb_created_at,
                                   String bdb_sup_id,String bdb_sup_name,  String bdb_client_id)
    {
        this.bdb_id=bdb_id;
        this.bdb_booking_place=bdb_booking_place;
        this.bdb_location_id=bdb_location_id;
        this.bdb_journey_time=bdb_journey_time;
        this.bdb_journey_cost=bdb_journey_cost;
        this.bdb_status=bdb_status;
        this.bdb_pack_code=bdb_pack_code;
        this.bdb_is_group_booking=bdb_is_group_booking;
        this.bdb_name_booking=bdb_name_booking;
        this.bdb_reject_reason=bdb_reject_reason;
        this.bdb_created_at=bdb_created_at;
        this.bdb_sup_id=bdb_sup_id;
        this.bdb_sup_name=bdb_sup_name;
        this.bdb_client_id=bdb_client_id;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public String getBdb_booking_place() {
        return bdb_booking_place;
    }

    public String getBdb_client_id() {
        return bdb_client_id;
    }

    public String getBdb_created_at() {
        return bdb_created_at;
    }

    public String getBdb_is_group_booking() {
        return bdb_is_group_booking;
    }

    public String getBdb_journey_cost() {
        return bdb_journey_cost;
    }

    public String getBdb_journey_time() {
        return bdb_journey_time;
    }

    public String getBdb_location_id() {
        return bdb_location_id;
    }

    public String getBdb_name_booking() {
        return bdb_name_booking;
    }

    public String getBdb_pack_code() {
        return bdb_pack_code;
    }

    public String getBdb_reject_reason() {
        return bdb_reject_reason;
    }

    public String getBdb_status() {
        return bdb_status;
    }

    public String getBdb_sup_id() {
        return bdb_sup_id;
    }

    public void setBdb_is_group_booking(String bdb_is_group_booking) {
        this.bdb_is_group_booking = bdb_is_group_booking;
    }

    public void setBdb_booking_place(String bdb_booking_place) {
        this.bdb_booking_place = bdb_booking_place;
    }

    public void setBdb_name_booking(String bdb_name_booking) {
        this.bdb_name_booking = bdb_name_booking;
    }

    public ArrayList<BookingRequestClientDataModel> getClients() {
        return clients;
    }

    public String getBdb_sup_name() {
        return bdb_sup_name;
    }
}
