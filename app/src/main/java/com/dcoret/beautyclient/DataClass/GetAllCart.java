package com.dcoret.beautyclient.DataClass;

public class GetAllCart {

        String bdb_id,
                bdb_ser_sup_id,
                bdb_employee_id,
                bdb_price,
                bdb_start_date,
                bdb_start_time,
                supplier_name,
                bdb_service_name_ar,
                bdb_service_name_en,
                bdb_ser_hotel,
                bdb_ser_hall,
                bdb_ser_home,
                bdb_ser_salon,
                bdb_is_group_booking,
                bdb_user_name,
                bdb_user_phone,
                bdb_pack_booking,
                bdb_main_booking;

        public GetAllCart(String bdb_id, String bdb_ser_sup_id, String bdb_employee_id, String bdb_price, String bdb_start_date, String bdb_start_time, String supplier_name, String bdb_service_name_ar, String bdb_service_name_en, String bdb_ser_hotel, String bdb_ser_hall, String bdb_ser_home, String bdb_ser_salon, String bdb_is_group_booking, String bdb_user_name, String bdb_user_phone, String bdb_pack_booking, String bdb_main_booking) {
            this.bdb_id = bdb_id;
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_employee_id = bdb_employee_id;
            this.bdb_price = bdb_price;
            this.bdb_start_date = bdb_start_date;
            this.bdb_start_time = bdb_start_time;
            this.supplier_name = supplier_name;
            this.bdb_service_name_ar = bdb_service_name_ar;
            this.bdb_service_name_en = bdb_service_name_en;
            this.bdb_ser_hotel = bdb_ser_hotel;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_is_group_booking = bdb_is_group_booking;
            this.bdb_user_name = bdb_user_name;
            this.bdb_user_phone = bdb_user_phone;
            this.bdb_pack_booking = bdb_pack_booking;
            this.bdb_main_booking = bdb_main_booking;
        }


        public String getBdb_id() {
            return bdb_id;
        }

        public void setBdb_id(String bdb_id) {
            this.bdb_id = bdb_id;
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

        public String getBdb_service_name_ar() {
            return bdb_service_name_ar;
        }

        public void setBdb_service_name_ar(String bdb_service_name_ar) {
            this.bdb_service_name_ar = bdb_service_name_ar;
        }

        public String getBdb_service_name_en() {
            return bdb_service_name_en;
        }

        public void setBdb_service_name_en(String bdb_service_name_en) {
            this.bdb_service_name_en = bdb_service_name_en;
        }

        public String getBdb_ser_hotel() {
            return bdb_ser_hotel;
        }

        public void setBdb_ser_hotel(String bdb_ser_hotel) {
            this.bdb_ser_hotel = bdb_ser_hotel;
        }

        public String getBdb_ser_hall() {
            return bdb_ser_hall;
        }

        public void setBdb_ser_hall(String bdb_ser_hall) {
            this.bdb_ser_hall = bdb_ser_hall;
        }

        public String getBdb_ser_home() {
            return bdb_ser_home;
        }

        public void setBdb_ser_home(String bdb_ser_home) {
            this.bdb_ser_home = bdb_ser_home;
        }

        public String getBdb_ser_salon() {
            return bdb_ser_salon;
        }

        public void setBdb_ser_salon(String bdb_ser_salon) {
            this.bdb_ser_salon = bdb_ser_salon;
        }

        public String getBdb_is_group_booking() {
            return bdb_is_group_booking;
        }

        public void setBdb_is_group_booking(String bdb_is_group_booking) {
            this.bdb_is_group_booking = bdb_is_group_booking;
        }

        public String getBdb_user_name() {
            return bdb_user_name;
        }

        public void setBdb_user_name(String bdb_user_name) {
            this.bdb_user_name = bdb_user_name;
        }

        public String getBdb_user_phone() {
            return bdb_user_phone;
        }

        public void setBdb_user_phone(String bdb_user_phone) {
            this.bdb_user_phone = bdb_user_phone;
        }

        public String getBdb_pack_booking() {
            return bdb_pack_booking;
        }

        public void setBdb_pack_booking(String bdb_pack_booking) {
            this.bdb_pack_booking = bdb_pack_booking;
        }

        public String getBdb_main_booking() {
            return bdb_main_booking;
        }

        public void setBdb_main_booking(String bdb_main_booking) {
            this.bdb_main_booking = bdb_main_booking;
        }

    class GroupBooking{
            String groupBookingName;
        String   bdb_id,
                bdb_ser_sup_id,
                bdb_employee_id,
                bdb_price,
                bdb_start_date,
                bdb_start_time,
                supplier_name,
                bdb_service_name_ar,
                bdb_service_name_en,
                bdb_ser_hotel,
                bdb_ser_hall,
                bdb_ser_home,
                bdb_ser_salon,
                bdb_is_group_booking,
                bdb_user_name,
                bdb_user_phone,
                bdb_pack_booking,
                bdb_main_booking;

        public GroupBooking(String bdb_id, String bdb_ser_sup_id, String bdb_employee_id, String bdb_price, String bdb_start_date, String bdb_start_time, String supplier_name, String bdb_service_name_ar, String bdb_service_name_en, String bdb_ser_hotel, String bdb_ser_hall, String bdb_ser_home, String bdb_ser_salon, String bdb_is_group_booking, String bdb_user_name, String bdb_user_phone, String bdb_pack_booking, String bdb_main_booking) {
            this.bdb_id = bdb_id;
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_employee_id = bdb_employee_id;
            this.bdb_price = bdb_price;
            this.bdb_start_date = bdb_start_date;
            this.bdb_start_time = bdb_start_time;
            this.supplier_name = supplier_name;
            this.bdb_service_name_ar = bdb_service_name_ar;
            this.bdb_service_name_en = bdb_service_name_en;
            this.bdb_ser_hotel = bdb_ser_hotel;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_is_group_booking = bdb_is_group_booking;
            this.bdb_user_name = bdb_user_name;
            this.bdb_user_phone = bdb_user_phone;
            this.bdb_pack_booking = bdb_pack_booking;
            this.bdb_main_booking = bdb_main_booking;
        }


        public String getGroupBookingName() {
            return groupBookingName;
        }

        public void setGroupBookingName(String groupBookingName) {
            this.groupBookingName = groupBookingName;
        }

        public String getBdb_id() {
            return bdb_id;
        }

        public void setBdb_id(String bdb_id) {
            this.bdb_id = bdb_id;
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

        public String getBdb_service_name_ar() {
            return bdb_service_name_ar;
        }

        public void setBdb_service_name_ar(String bdb_service_name_ar) {
            this.bdb_service_name_ar = bdb_service_name_ar;
        }

        public String getBdb_service_name_en() {
            return bdb_service_name_en;
        }

        public void setBdb_service_name_en(String bdb_service_name_en) {
            this.bdb_service_name_en = bdb_service_name_en;
        }

        public String getBdb_ser_hotel() {
            return bdb_ser_hotel;
        }

        public void setBdb_ser_hotel(String bdb_ser_hotel) {
            this.bdb_ser_hotel = bdb_ser_hotel;
        }

        public String getBdb_ser_hall() {
            return bdb_ser_hall;
        }

        public void setBdb_ser_hall(String bdb_ser_hall) {
            this.bdb_ser_hall = bdb_ser_hall;
        }

        public String getBdb_ser_home() {
            return bdb_ser_home;
        }

        public void setBdb_ser_home(String bdb_ser_home) {
            this.bdb_ser_home = bdb_ser_home;
        }

        public String getBdb_ser_salon() {
            return bdb_ser_salon;
        }

        public void setBdb_ser_salon(String bdb_ser_salon) {
            this.bdb_ser_salon = bdb_ser_salon;
        }

        public String getBdb_is_group_booking() {
            return bdb_is_group_booking;
        }

        public void setBdb_is_group_booking(String bdb_is_group_booking) {
            this.bdb_is_group_booking = bdb_is_group_booking;
        }

        public String getBdb_user_name() {
            return bdb_user_name;
        }

        public void setBdb_user_name(String bdb_user_name) {
            this.bdb_user_name = bdb_user_name;
        }

        public String getBdb_user_phone() {
            return bdb_user_phone;
        }

        public void setBdb_user_phone(String bdb_user_phone) {
            this.bdb_user_phone = bdb_user_phone;
        }

        public String getBdb_pack_booking() {
            return bdb_pack_booking;
        }

        public void setBdb_pack_booking(String bdb_pack_booking) {
            this.bdb_pack_booking = bdb_pack_booking;
        }

        public String getBdb_main_booking() {
            return bdb_main_booking;
        }

        public void setBdb_main_booking(String bdb_main_booking) {
            this.bdb_main_booking = bdb_main_booking;
        }
    }




}
