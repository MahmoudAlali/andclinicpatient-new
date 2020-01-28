package com.dcoret.beautyclient.DataModel;

import java.util.ArrayList;

public class SearchBookingDataSTR {
    String salon_id,salon_name,total_price,client_response,client_name,is_current_user,client_id;
    ArrayList<Solution> solutions;


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
        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String emp_name, String sup_id, String ser_sup_id, String from, String to, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_hotel_price, String bdb_ser_salon_price, String bdb_ser_home, String bdb_ser_salon, String bdb_ser_hall, String bdb_hotel,String part_num,String nul) {
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
