package com.dcoret.beautyclient.DataClass;

import java.util.ArrayList;

public class SearchBookingDataSTR {
    String salon_id,salon_name,client_response,client_name,is_current_user;
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
        String ser_id,ser_name,ser_name_ar,emp_id,sup_id,ser_sup_id,from,to;


        public Solution(String ser_id, String ser_name, String ser_name_ar, String emp_id, String sup_id, String ser_sup_id, String from, String to) {
            this.ser_id = ser_id;
            this.ser_name = ser_name;
            this.ser_name_ar = ser_name_ar;
            this.emp_id = emp_id;
            this.sup_id = sup_id;
            this.ser_sup_id = ser_sup_id;
            this.from = from;
            this.to = to;
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
