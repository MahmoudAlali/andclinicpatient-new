package com.dcoret.beautyclient.DataModel;

import java.util.ArrayList;

public class ClientEffectRequestModel {
    String client_name;
    ArrayList<ClientEffectModel> clientEffectModels;

    public ClientEffectRequestModel(String client_name, ArrayList<ClientEffectModel> clientEffectModels) {
        this.client_name = client_name;
        this.clientEffectModels = clientEffectModels;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public ArrayList<ClientEffectModel> getClientEffectModels() {
        return clientEffectModels;
    }

    public void setClientEffectModels(ArrayList<ClientEffectModel> clientEffectModels) {
        this.clientEffectModels = clientEffectModels;
    }
}
