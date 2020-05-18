package com.ptmsa1.clinicclient.DataModel;

import android.widget.CheckBox;

public class CancelPerClientModel {
    String id;
    CheckBox check;

    public CancelPerClientModel(String id, CheckBox check) {
        this.id = id;
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }
}
