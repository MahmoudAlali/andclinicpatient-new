package com.ptmsa1.vizage.DataModel;

import java.util.ArrayList;

public class EffectFilterModel {


    String client_name;
    ArrayList<String> effects;

    public EffectFilterModel(String client_name, ArrayList<String> effects) {
        this.client_name = client_name;
        this.effects = effects;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public ArrayList<String> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<String> effects) {
        this.effects = effects;
    }
}
