package com.ptmsa1.clinicclient.DataModel;

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
            supplier_name,
            logo_id,
            cost,
            bdb_loc_lat,
            bdb_loc_long,
            bdb_client_id;

    ArrayList <BookingRequestClientDataModel> clients = new ArrayList<>();

    public BookingRequestDataModel(String bdb_id, String bdb_booking_place, String bdb_location_id, String bdb_journey_time,
                                   String bdb_journey_cost, String bdb_status, String bdb_pack_code, String bdb_is_group_booking,String cost,
                                   String bdb_name_booking, String bdb_reject_reason, String bdb_created_at,
                                   String bdb_sup_id,String supplier_name,String logo_id,  String bdb_client_id,String bdb_loc_lat,String bdb_loc_long
                                   ,ArrayList <BookingRequestClientDataModel> clients)
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
        this.cost=cost;
        this.bdb_reject_reason=bdb_reject_reason;
        this.bdb_created_at=bdb_created_at;
        this.bdb_sup_id=bdb_sup_id;
        this.supplier_name=supplier_name;
        this.logo_id=logo_id;
        this.bdb_client_id=bdb_client_id;
        this.clients=clients;
        this.bdb_loc_lat=bdb_loc_lat;
        this.bdb_loc_long=bdb_loc_long;
    }

    public void setBdb_id(String bdb_id) {
        this.bdb_id = bdb_id;
    }

    public void setBdb_location_id(String bdb_location_id) {
        this.bdb_location_id = bdb_location_id;
    }

    public void setBdb_journey_time(String bdb_journey_time) {
        this.bdb_journey_time = bdb_journey_time;
    }

    public void setBdb_journey_cost(String bdb_journey_cost) {
        this.bdb_journey_cost = bdb_journey_cost;
    }

    public void setBdb_status(String bdb_status) {
        this.bdb_status = bdb_status;
    }

    public void setBdb_pack_code(String bdb_pack_code) {
        this.bdb_pack_code = bdb_pack_code;
    }

    public void setBdb_reject_reason(String bdb_reject_reason) {
        this.bdb_reject_reason = bdb_reject_reason;
    }

    public void setBdb_created_at(String bdb_created_at) {
        this.bdb_created_at = bdb_created_at;
    }

    public void setBdb_sup_id(String bdb_sup_id) {
        this.bdb_sup_id = bdb_sup_id;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public void setLogo_id(String logo_id) {
        this.logo_id = logo_id;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public void setBdb_client_id(String bdb_client_id) {
        this.bdb_client_id = bdb_client_id;
    }

    public void setClients(ArrayList<BookingRequestClientDataModel> clients) {
        this.clients = clients;
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

    public String getLogo_id() {
        return logo_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getCost() {
        return cost;
    }
}
