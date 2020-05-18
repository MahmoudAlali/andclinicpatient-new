package com.ptmsa1.clinicclient.DataModel;

import java.util.ArrayList;

public class SerchGroupBookingData {

    ArrayList<SolutionsCount> completeSolutions;

    public SerchGroupBookingData(ArrayList<SolutionsCount> completeSolutions) {
        this.completeSolutions = completeSolutions;
    }

    public ArrayList<SolutionsCount> getCompleteSolutions() {
        return completeSolutions;
    }

    public void setCompleteSolutions(ArrayList<SolutionsCount> completeSolutions) {
        this.completeSolutions = completeSolutions;
    }

    public static class SolutionsCount {
        String salon_id, salon_name;
        ClientResponse client_response;


        public SolutionsCount(String salon_id, String salon_name, ClientResponse client_response) {
            this.salon_id = salon_id;
            this.salon_name = salon_name;
            this.client_response = client_response;
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

        public ClientResponse getClient_response() {
            return client_response;
        }

        public void setClient_response(ClientResponse client_response) {
            this.client_response = client_response;
        }
    }
    public static class ClientResponse{
    String client_name;
    ArrayList<Solutions> solutions;

    public ClientResponse(String client_name, ArrayList<Solutions> solutions) {
        this.client_name = client_name;
        this.solutions = solutions;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public ArrayList<Solutions> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<Solutions> solutions) {
        this.solutions = solutions;
    }
}
public static class Solutions{
    String ser_id,
            emp_id,
            sup_id,
            ser_sup_id,
            from,
            to ,
            old_from,
            old_to,
            new_from,
            new_to,
            client_name,ser_name,ser_name_ar,is_current_user;


    public Solutions(String ser_id, String emp_id, String sup_id, String ser_sup_id, String from, String to, String old_from, String old_to, String new_from, String new_to, String client_name) {
        this.ser_id = ser_id;
        this.emp_id = emp_id;
        this.sup_id = sup_id;
        this.ser_sup_id = ser_sup_id;
        this.from = from;
        this.to = to;
        this.old_from = old_from;
        this.old_to = old_to;
        this.new_from = new_from;
        this.new_to = new_to;
        this.client_name = client_name;
    }

    public Solutions(String ser_id, String emp_id, String sup_id, String ser_sup_id, String from, String to, String old_from, String old_to, String new_from, String new_to, String client_name, String ser_name, String ser_name_ar) {
        this.ser_id = ser_id;
        this.emp_id = emp_id;
        this.sup_id = sup_id;
        this.ser_sup_id = ser_sup_id;
        this.from = from;
        this.to = to;
        this.old_from = old_from;
        this.old_to = old_to;
        this.new_from = new_from;
        this.new_to = new_to;
        this.client_name = client_name;
        this.ser_name = ser_name;
        this.ser_name_ar = ser_name_ar;
    }

    public Solutions(String ser_id, String emp_id, String sup_id, String ser_sup_id, String from, String to, String old_from, String old_to, String new_from, String new_to, String client_name, String ser_name, String ser_name_ar, String is_current_user) {
        this.ser_id = ser_id;
        this.emp_id = emp_id;
        this.sup_id = sup_id;
        this.ser_sup_id = ser_sup_id;
        this.from = from;
        this.to = to;
        this.old_from = old_from;
        this.old_to = old_to;
        this.new_from = new_from;
        this.new_to = new_to;
        this.client_name = client_name;
        this.ser_name = ser_name;
        this.ser_name_ar = ser_name_ar;
        this.is_current_user = is_current_user;
    }

    public String getIs_current_user() {
        return is_current_user;
    }

    public void setIs_current_user(String is_current_user) {
        this.is_current_user = is_current_user;
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

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
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

    public String getOld_from() {
        return old_from;
    }

    public void setOld_from(String old_from) {
        this.old_from = old_from;
    }

    public String getOld_to() {
        return old_to;
    }

    public void setOld_to(String old_to) {
        this.old_to = old_to;
    }

    public String getNew_from() {
        return new_from;
    }

    public void setNew_from(String new_from) {
        this.new_from = new_from;
    }

    public String getNew_to() {
        return new_to;
    }

    public void setNew_to(String new_to) {
        this.new_to = new_to;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}}

