package com.dcoret.beautyclient.DataModel;

//import com.dcoret.beautyclient.Adapters.BookingRequestsAdapter;
import com.dcoret.beautyclient.DataExample.ServicesData;

import java.util.ArrayList;

public class BookingRequestClientDataModel {
    private String
            bdb_start_date,
            bdb_end_date,
            bdb_client_old,
            bdb_is_current_user,
            bdb_client_name,
            bdb_client_phone;
    private ArrayList<ClientServiceDataModel> services = new ArrayList<>();

    public BookingRequestClientDataModel (String bdb_start_date, String bdb_end_date, String bdb_client_old,
                                          String bdb_is_current_user, String bdb_client_name, String bdb_client_phone,ArrayList<ClientServiceDataModel> services)
    {
        this.bdb_start_date = bdb_start_date;
        this.bdb_end_date = bdb_end_date;
        this.bdb_client_old = bdb_client_old;
        this.bdb_is_current_user = bdb_is_current_user;
        this.bdb_client_name = bdb_client_name;
        this.bdb_client_phone = bdb_client_phone;
        this.services = services;
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
}
