package com.dcoret.beautyclient.DataClass;

import android.widget.EditText;
import android.widget.Spinner;

public class ClientsViewData {
    EditText client_name,phone_number;
    Spinner add_service,age_range,client_status;

    public ClientsViewData(EditText client_name, Spinner add_service, Spinner age_range, Spinner client_status) {
        this.client_name = client_name;
        this.add_service = add_service;
        this.age_range = age_range;
        this.client_status = client_status;
    }

    public ClientsViewData(EditText client_name, EditText phone_number, Spinner add_service, Spinner age_range, Spinner client_status) {
        this.client_name = client_name;
        this.phone_number = phone_number;
        this.add_service = add_service;
        this.age_range = age_range;
        this.client_status = client_status;
    }

    public EditText getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(EditText phone_number) {
        this.phone_number = phone_number;
    }

    public EditText getClient_name() {
        return client_name;
    }





    public void setClient_name(EditText client_name) {
        this.client_name = client_name;
    }

    public Spinner getAdd_service() {
        return add_service;
    }

    public void setAdd_service(Spinner add_service) {
        this.add_service = add_service;
    }

    public Spinner getAge_range() {
        return age_range;
    }

    public void setAge_range(Spinner age_range) {
        this.age_range = age_range;
    }

    public Spinner getClient_status() {
        return client_status;
    }

    public void setClient_status(Spinner client_status) {
        this.client_status = client_status;
    }
}
