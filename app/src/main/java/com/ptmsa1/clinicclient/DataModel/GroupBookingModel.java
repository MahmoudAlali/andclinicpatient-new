package com.ptmsa1.clinicclient.DataModel;

import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class GroupBookingModel {
    EditText clientName,PhoneNumber;
    Spinner ageRange;
    ArrayList<ClientServiceDataModel> servicesModels;


    public GroupBookingModel(EditText clientName, EditText phoneNumber, Spinner ageRange, ArrayList<ClientServiceDataModel> servicesModels) {
        this.clientName = clientName;
        PhoneNumber = phoneNumber;
        this.ageRange = ageRange;
        this.servicesModels = servicesModels;
    }

    public EditText getClientName() {
        return clientName;
    }

    public void setClientName(EditText clientName) {
        this.clientName = clientName;
    }

    public EditText getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(EditText phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Spinner getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(Spinner ageRange) {
        this.ageRange = ageRange;
    }

    public ArrayList<ClientServiceDataModel> getServicesModels() {
        return servicesModels;
    }

    public void setServicesModels(ArrayList<ClientServiceDataModel> servicesModels) {
        this.servicesModels = servicesModels;
    }




    public static class ServicesModel{
        String ser_id,bdb_time,name,bdb_start_date,emp_id,emp_name,sup_id,ser_sup_id,from,to;

        public ServicesModel(String ser_id, String bdb_time,String name) {
            this.ser_id = ser_id;
            this.bdb_time = bdb_time;
            this.name = name;
        }


        public ServicesModel(String ser_id, String bdb_time, String name, String bdb_start_date, String emp_id, String emp_name,String sup_id,String ser_sup_id) {
            this.ser_id = ser_id;
            this.bdb_time = bdb_time;
            this.name = name;
            this.bdb_start_date = bdb_start_date;
            this.emp_id = emp_id;
            this.emp_name = emp_name;
            this.sup_id= sup_id;
            this.ser_sup_id= ser_sup_id;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getSer_sup_id() {
            return ser_sup_id;
        }

        public String getSup_id() {
            return sup_id;
        }

        public String getEmp_id() {
            return emp_id;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public String getBdb_start_date() {

            return bdb_start_date;
        }

        public String getName() {
            return name;
        }

        public String getSer_id() {
            return ser_id;
        }

        public void setSer_id(String ser_id) {
            this.ser_id = ser_id;
        }

        public String getBdb_time() {
            return bdb_time;
        }

        public void setBdb_time(String bdb_time) {
            this.bdb_time = bdb_time;
        }
    }

}
