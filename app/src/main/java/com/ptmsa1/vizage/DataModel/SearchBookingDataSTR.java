package com.ptmsa1.vizage.DataModel;

import java.util.ArrayList;

public class SearchBookingDataSTR {
    String salon_id,salon_name,total_price,client_response,client_name,is_current_user,client_id;
    ArrayList<Solution> solutions;

    String journeyCost;
    public SearchBookingDataSTR(String salon_id, String salon_name, String client_response, String client_name, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.client_response = client_response;
        this.client_name = client_name;
        this.solutions = solutions;
    }

    public SearchBookingDataSTR(String salon_id, String salon_name, String client_response, String client_name, String is_current_user, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.client_response = client_response;
        this.client_name = client_name;
        this.is_current_user = is_current_user;
        this.solutions = solutions;
    }

    public SearchBookingDataSTR(String salon_id, String salon_name, String client_response, String client_name, String is_current_user, String client_id, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.client_response = client_response;
        this.client_name = client_name;
        this.is_current_user = is_current_user;
        this.client_id = client_id;
        this.solutions = solutions;
    }

    public SearchBookingDataSTR(String salon_id, String salon_name, String total_price, String client_response, String client_name, String is_current_user, String client_id, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.total_price = total_price;
        this.client_response = client_response;
        this.client_name = client_name;
        this.is_current_user = is_current_user;
        this.client_id = client_id;
        this.solutions = solutions;
        this.journeyCost = journeyCost;
    }
    public SearchBookingDataSTR(String salon_id, String salon_name, String total_price,String journeyCost, String client_response, String client_name, String is_current_user, String client_id, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.total_price = total_price;
        this.client_response = client_response;
        this.client_name = client_name;
        this.is_current_user = is_current_user;
        this.client_id = client_id;
        this.solutions = solutions;
        this.journeyCost = journeyCost;
    }
    String journey_time,bdb_loc_lat,bdb_loc_long,bdb_address_id,bdb_client_deposit_ratio;
    public SearchBookingDataSTR(String salon_id, String salon_name, String total_price,String journeyCost, String client_response, String client_name, String is_current_user, String client_id ,String journey_time,String bdb_loc_lat,String bdb_loc_long,String bdb_address_id,String bdb_client_deposit_ratio, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.total_price = total_price;
        this.client_response = client_response;
        this.client_name = client_name;
        this.is_current_user = is_current_user;
        this.client_id = client_id;
        this.solutions = solutions;
        this.journeyCost = journeyCost;
        this.journey_time = journey_time;
        this.bdb_loc_lat= bdb_loc_lat;
        this.bdb_loc_long= bdb_loc_long;
        this.bdb_address_id= bdb_address_id;
        this.bdb_client_deposit_ratio= bdb_client_deposit_ratio;
    }

    String is_booked;
    public SearchBookingDataSTR(String salon_id, String salon_name, String total_price, String client_response, String client_name, String is_current_user, String client_id, ArrayList<Solution> solutions,String is_booked) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.total_price = total_price;
        this.client_response = client_response;
        this.client_name = client_name;
        this.is_current_user = is_current_user;
        this.client_id = client_id;
        this.solutions = solutions;
        this.is_booked=is_booked;
    }


    public SearchBookingDataSTR(String salon_id, String salon_name, ArrayList<Solution> solutions) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.solutions = solutions;
    }

    public String getBdb_client_deposit_ratio() {
        return bdb_client_deposit_ratio;
    }

    public void setBdb_client_deposit_ratio(String bdb_client_deposit_ratio) {
        this.bdb_client_deposit_ratio = bdb_client_deposit_ratio;
    }

    public String getJourney_time() {
        return journey_time;
    }

    public void setJourney_time(String journey_time) {
        this.journey_time = journey_time;
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

    public String getBdb_address_id() {
        return bdb_address_id;
    }

    public void setBdb_address_id(String bdb_address_id) {
        this.bdb_address_id = bdb_address_id;
    }

    public String getJourneyCost() {
        return journeyCost;
    }

    public void setJourneyCost(String journeyCost) {
        this.journeyCost = journeyCost;
    }

    public String getIs_booked() {
        return is_booked;
    }

    public void setIs_booked(String is_booked) {
        this.is_booked = is_booked;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getIs_current_user() {
        return is_current_user;
    }

    public void setIs_current_user(String is_current_user) {
        this.is_current_user = is_current_user;
    }

    public String getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(String salon_id) {
        this.salon_id = salon_id;
    }

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_name(String salon_name) {
        this.salon_name = salon_name;
    }

    public String getClient_response() {
        return client_response;
    }

    public void setClient_response(String client_response) {
        this.client_response = client_response;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public ArrayList<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<Solution> solutions) {
        this.solutions = solutions;
    }

    public static class Solution{
        String ser_id,
                ser_name,
                ser_name_ar,
                emp_id,
                emp_name,
                sup_id,
                ser_sup_id,
                from,
                to,
                bdb_ser_home_price,
                bdb_ser_hall_price,
                bdb_hotel_price,
                bdb_ser_salon_price,
                bdb_ser_home,
                bdb_ser_salon,
                bdb_ser_hall,
                bdb_hotel;
        String date,part_num;

        String salon_name;



        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String emp_name, String sup_id, String ser_sup_id, String from, String to, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_hotel_price, String bdb_ser_salon_price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel) {
            this.ser_id = ser_id;
            this.ser_name = ser_name;
            this.ser_name_ar = ser_name_ar;
            this.emp_id = emp_id;
            this.emp_name = emp_name;
            this.sup_id = sup_id;
            this.ser_sup_id = ser_sup_id;
            this.from = from;
            this.to = to;
            this.bdb_ser_home_price = bdb_ser_home_price;
            this.bdb_ser_hall_price = bdb_ser_hall_price;
            this.bdb_hotel_price = bdb_hotel_price;
            this.bdb_ser_salon_price = bdb_ser_salon_price;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_hotel = bdb_hotel;
        }
        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String emp_name, String sup_id, String ser_sup_id, String from, String to, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_hotel_price, String bdb_ser_salon_price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel,String part_num,String reason) {
            this.ser_id = ser_id;
            this.ser_name = ser_name;
            this.ser_name_ar = ser_name_ar;
            this.emp_id = emp_id;
            this.emp_name = emp_name;
            this.sup_id = sup_id;
            this.ser_sup_id = ser_sup_id;
            this.from = from;
            this.to = to;
            this.bdb_ser_home_price = bdb_ser_home_price;
            this.bdb_ser_hall_price = bdb_ser_hall_price;
            this.bdb_hotel_price = bdb_hotel_price;
            this.bdb_ser_salon_price = bdb_ser_salon_price;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_hotel = bdb_hotel;
            this.part_num= part_num;
            this.reason= reason;
        }

        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String emp_name, String sup_id, String ser_sup_id, String from, String to, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_hotel_price, String bdb_ser_salon_price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel, String date,String bdb_part_num,String nul) {
            this.ser_id = ser_id;
            this.ser_name = ser_name;
            this.ser_name_ar = ser_name_ar;
            this.emp_id = emp_id;
            this.emp_name = emp_name;
            this.sup_id = sup_id;
            this.ser_sup_id = ser_sup_id;
            this.from = from;
            this.to = to;
            this.bdb_ser_home_price = bdb_ser_home_price;
            this.bdb_ser_hall_price = bdb_ser_hall_price;
            this.bdb_hotel_price = bdb_hotel_price;
            this.bdb_ser_salon_price = bdb_ser_salon_price;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_hotel = bdb_hotel;
            this.date = date;
            this.part_num= bdb_part_num;
        }

        String client_name,phone;
        String client_id,is_current_user;
        boolean isbooked;
        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String emp_name, String sup_id, String ser_sup_id, String from, String to, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_hotel_price, String bdb_ser_salon_price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel, String date,String bdb_part_num,String salon_name,String client_name, String client_id,String  is_current_user,String phone,String reason,boolean isbooked) {
            this.ser_id = ser_id;
            this.ser_name = ser_name;
            this.ser_name_ar = ser_name_ar;
            this.emp_id = emp_id;
            this.emp_name = emp_name;
            this.sup_id = sup_id;
            this.ser_sup_id = ser_sup_id;
            this.from = from;
            this.to = to;
            this.bdb_ser_home_price = bdb_ser_home_price;
            this.bdb_ser_hall_price = bdb_ser_hall_price;
            this.bdb_hotel_price = bdb_hotel_price;
            this.bdb_ser_salon_price = bdb_ser_salon_price;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_hotel = bdb_hotel;
            this.date = date;
            this.part_num= bdb_part_num;
            this.salon_name= salon_name;
            this.client_name= client_name;
            this.isbooked= isbooked;
            this.phone= phone;
            this.is_current_user= is_current_user;
            this.client_id= client_id;
            this.reason= reason;
        }

      String reason,offer_ser_sup_id,is_adult;
        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String emp_name, String sup_id, String ser_sup_id, String from, String to, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_hotel_price, String bdb_ser_salon_price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel, String date,String bdb_part_num,String reason,String offer_ser_sup_id,String is_adult) {
            this.ser_id = ser_id;
            this.ser_name = ser_name;
            this.ser_name_ar = ser_name_ar;
            this.emp_id = emp_id;
            this.emp_name = emp_name;
            this.sup_id = sup_id;
            this.ser_sup_id = ser_sup_id;
            this.from = from;
            this.to = to;
            this.bdb_ser_home_price = bdb_ser_home_price;
            this.bdb_ser_hall_price = bdb_ser_hall_price;
            this.bdb_hotel_price = bdb_hotel_price;
            this.bdb_ser_salon_price = bdb_ser_salon_price;
            this.bdb_ser_home = bdb_ser_home;
            this.bdb_ser_salon = bdb_ser_salon;
            this.bdb_ser_hall = bdb_ser_hall;
            this.bdb_hotel = bdb_hotel;
            this.date = date;
            this.part_num= bdb_part_num;
            this.reason= reason;
            this.offer_ser_sup_id= offer_ser_sup_id;
            this.is_adult= is_adult;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getIs_current_user() {
            return is_current_user;
        }

        public void setIs_current_user(String is_current_user) {
            this.is_current_user = is_current_user;
        }

        public boolean isIsbooked() {
            return isbooked;
        }

        public void setIsbooked(boolean isbooked) {
            this.isbooked = isbooked;
        }

        public String getSalon_name() {
            return salon_name;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public void setSalon_name(String salon_name) {
            this.salon_name = salon_name;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getOffer_ser_sup_id() {
            return offer_ser_sup_id;
        }

        public void setOffer_ser_sup_id(String offer_ser_sup_id) {
            this.offer_ser_sup_id = offer_ser_sup_id;
        }

        public String getIs_adult() {
            return is_adult;
        }

        public void setIs_adult(String is_adult) {
            this.is_adult = is_adult;
        }

        public String getPart_num() {
            return part_num;
        }

        public void setPart_num(String part_num) {
            this.part_num = part_num;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
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

        public String getBdb_hotel_price() {
            return bdb_hotel_price;
        }

        public void setBdb_hotel_price(String bdb_hotel_price) {
            this.bdb_hotel_price = bdb_hotel_price;
        }

        public String getBdb_ser_salon_price() {
            return bdb_ser_salon_price;
        }

        public void setBdb_ser_salon_price(String bdb_ser_salon_price) {
            this.bdb_ser_salon_price = bdb_ser_salon_price;
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

        public String getBdb_ser_hall() {
            return bdb_ser_hall;
        }

        public void setBdb_ser_hall(String bdb_ser_hall) {
            this.bdb_ser_hall = bdb_ser_hall;
        }

        public String getBdb_hotel() {
            return bdb_hotel;
        }

        public void setBdb_hotel(String bdb_hotel) {
            this.bdb_hotel = bdb_hotel;
        }

        public String getSer_id() {
            return ser_id;
        }

        public void setSer_id(String ser_id) {
            this.ser_id = ser_id;
        }

        public String getSer_name() {
            return ser_name;
        }

        public void setSer_name(String ser_name) {
            this.ser_name = ser_name;
        }

        public String getSer_name_ar() {
            return ser_name_ar;
        }

        public void setSer_name_ar(String ser_name_ar) {
            this.ser_name_ar = ser_name_ar;
        }

        public String getEmp_id() {
            return emp_id;
        }

        public void setEmp_id(String emp_id) {
            this.emp_id = emp_id;
        }

        public String getSup_id() {
            return sup_id;
        }

        public void setSup_id(String sup_id) {
            this.sup_id = sup_id;
        }

        public String getSer_sup_id() {
            return ser_sup_id;
        }

        public void setSer_sup_id(String ser_sup_id) {
            this.ser_sup_id = ser_sup_id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }


}
