package com.ptmsa1.vizage.DataModel;

import android.widget.Spinner;
import android.widget.TextView;

public class ClientsRelationsViewClass {
    TextView client_name;
    Spinner client_list;

    public ClientsRelationsViewClass(TextView client_name, Spinner client_list) {
        this.client_name = client_name;
        this.client_list = client_list;
    }

    public TextView getClient_name() {
        return client_name;
    }

    public void setClient_name(TextView client_name) {
        this.client_name = client_name;
    }

    public Spinner getClient_list() {
        return client_list;
    }

    public void setClient_list(Spinner client_list) {
        this.client_list = client_list;
    }
}
