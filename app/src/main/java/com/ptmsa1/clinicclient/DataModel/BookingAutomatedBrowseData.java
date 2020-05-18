package com.ptmsa1.clinicclient.DataModel;

public class BookingAutomatedBrowseData {
    String   bdb_id
            ,bdb_price
            ,bdb_status
            ,bdb_start_date
            ,bdb_start_time
            ,supplier_name
            ,employee_name
            ,service_en_name
            ,service_ar_name
            ,client_name
            ,booking_price
            ,provider_rating
            ,is_action_on_inside
            ,is_rating_on_inside
            ,bdb_confirm_exec_user
            ,totalItem;


    public BookingAutomatedBrowseData(String bdb_id, String bdb_price, String bdb_status, String bdb_start_date, String bdb_start_time, String supplier_name, String employee_name, String service_en_name, String service_ar_name, String totalItem) {
        this.bdb_id = bdb_id;
        this.bdb_price = bdb_price;
        this.bdb_status = bdb_status;
        this.bdb_start_date = bdb_start_date;
        this.bdb_start_time = bdb_start_time;
        this.supplier_name = supplier_name;
        this.employee_name = employee_name;
        this.service_en_name = service_en_name;
        this.service_ar_name = service_ar_name;
        this.totalItem = totalItem;
    }


    String bdb_end_time;
    public BookingAutomatedBrowseData(String bdb_id, String bdb_price, String bdb_status, String bdb_start_date, String bdb_start_time,String bdb_end_time, String supplier_name, String employee_name, String service_en_name, String service_ar_name, String client_name, String booking_price, String totalItem,String provider_rating) {
        this.bdb_id = bdb_id;
        this.bdb_price = bdb_price;
        this.bdb_status = bdb_status;
        this.bdb_start_date = bdb_start_date;
        this.bdb_start_time = bdb_start_time;
        this.supplier_name = supplier_name;
        this.employee_name = employee_name;
        this.service_en_name = service_en_name;
        this.service_ar_name = service_ar_name;
        this.client_name = client_name;
        this.booking_price = booking_price;
        this.totalItem = totalItem;
        this.bdb_end_time = bdb_end_time;
        this.provider_rating= provider_rating;
    }
    String bdb_paid_deposit;
    public BookingAutomatedBrowseData(String bdb_id, String bdb_price, String bdb_status, String bdb_start_date, String bdb_start_time,String bdb_end_time, String supplier_name, String employee_name, String service_en_name, String service_ar_name, String client_name, String booking_price, String totalItem,String provider_rating,String is_action_on_inside,String is_rating_on_inside,String bdb_confirm_exec_user,String bdb_paid_deposit) {
        this.bdb_id = bdb_id;
        this.bdb_price = bdb_price;
        this.bdb_status = bdb_status;
        this.bdb_start_date = bdb_start_date;
        this.bdb_start_time = bdb_start_time;
        this.supplier_name = supplier_name;
        this.employee_name = employee_name;
        this.service_en_name = service_en_name;
        this.service_ar_name = service_ar_name;
        this.client_name = client_name;
        this.booking_price = booking_price;
        this.totalItem = totalItem;
        this.bdb_end_time = bdb_end_time;
        this.provider_rating= provider_rating;
        this.is_action_on_inside= is_action_on_inside;
        this.is_rating_on_inside= is_rating_on_inside;
        this.bdb_confirm_exec_user= bdb_confirm_exec_user;
        this.bdb_paid_deposit= bdb_paid_deposit;
    }

    public String getBdb_paid_deposit() {
        return bdb_paid_deposit;
    }

    public String getBdb_confirm_exec_user() {
        return bdb_confirm_exec_user;
    }

    public void setBdb_confirm_exec_user(String bdb_confirm_exec_user) {
        this.bdb_confirm_exec_user = bdb_confirm_exec_user;
    }

    public String getIs_action_on_inside() {
        return is_action_on_inside;
    }

    public void setIs_action_on_inside(String is_action_on_inside) {
        this.is_action_on_inside = is_action_on_inside;
    }

    public String getIs_rating_on_inside() {
        return is_rating_on_inside;
    }

    public void setIs_rating_on_inside(String is_rating_on_inside) {
        this.is_rating_on_inside = is_rating_on_inside;
    }

    public String getProvider_rating() {
        return provider_rating;
    }

    public void setProvider_rating(String provider_rating) {
        this.provider_rating = provider_rating;
    }

    public String getBdb_end_time() {
        return bdb_end_time;
    }

    public void setBdb_end_time(String bdb_end_time) {
        this.bdb_end_time = bdb_end_time;
    }

    public String getBooking_price() {
        return booking_price;
    }

    public void setBooking_price(String booking_price) {
        this.booking_price = booking_price;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public void setBdb_id(String bdb_id) {
        this.bdb_id = bdb_id;
    }

    public String getBdb_price() {
        return bdb_price;
    }

    public void setBdb_price(String bdb_price) {
        this.bdb_price = bdb_price;
    }

    public String getBdb_status() {
        return bdb_status;
    }

    public void setBdb_status(String bdb_status) {
        this.bdb_status = bdb_status;
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

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getService_en_name() {
        return service_en_name;
    }

    public void setService_en_name(String service_en_name) {
        this.service_en_name = service_en_name;
    }

    public String getService_ar_name() {
        return service_ar_name;
    }

    public void setService_ar_name(String service_ar_name) {
        this.service_ar_name = service_ar_name;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }
}
