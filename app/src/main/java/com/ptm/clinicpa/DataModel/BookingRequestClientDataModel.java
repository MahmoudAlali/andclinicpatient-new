package com.ptm.clinicpa.DataModel;

//import com.dcoret.beautyclient.Adapters.BookingRequestsAdapter;

import java.util.ArrayList;

public class BookingRequestClientDataModel {
    private String
            bdb_start_date,
            start_time,
            bdb_end_date,
            bdb_status,
            specialization_name_ar,
            specialization_name_en,
            doctor_name,
            bdb_client_old,
            bdb_is_current_user,
            bdb_client_name,
            bdb_client_phone;
    private ArrayList<ClientServiceDataModel> services = new ArrayList<>();

    public BookingRequestClientDataModel (String bdb_start_date, String bdb_end_date, String bdb_client_old,
                                          String bdb_is_current_user, String bdb_client_name, String bdb_client_phone,ArrayList<ClientServiceDataModel> services,
                                          String specialization_name_en,
                                          String specialization_name_ar,
                                          String bdb_status,
                                          String start_time,
                                            String doctor_name)
    {
        this.bdb_start_date = bdb_start_date;
        this.bdb_end_date = bdb_end_date;
        this.bdb_client_old = bdb_client_old;
        this.bdb_is_current_user = bdb_is_current_user;
        this.bdb_client_name = bdb_client_name;
        this.bdb_client_phone = bdb_client_phone;
        this.services = services;
        this.doctor_name = doctor_name;
    }

    public ArrayList<ClientServiceDataModel> getClients() {
        return services;
    }

    public String getBdb_client_name() {
        return bdb_client_name;
    }

    public String getBdb_client_old() {
        return bdb_client_old;
    }

    public String getBdb_client_phone() {
        return bdb_client_phone;
    }

    public String getBdb_end_date() {
        return bdb_end_date;
    }

    public String getBdb_is_current_user() {
        return bdb_is_current_user;
    }

    public String getBdb_start_date() {
        return bdb_start_date;
    }

    public ArrayList<ClientServiceDataModel> getServices() {
        return services;
    }

    public String getSpecialization_name_ar() {
        return specialization_name_ar;
    }

    public String getBdb_status() {
        return bdb_status;
    }

    public String getSpecialization_name_en() {
        return specialization_name_en;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getStart_time() {
        return start_time;
    }
}
