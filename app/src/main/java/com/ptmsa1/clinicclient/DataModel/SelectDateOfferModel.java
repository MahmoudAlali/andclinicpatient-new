package com.ptmsa1.clinicclient.DataModel;

import android.widget.CheckBox;
import android.widget.EditText;


public class SelectDateOfferModel {

    EditText cname,phone_number;
    int postion;
    CheckBox checkme;

    public SelectDateOfferModel(EditText cname, EditText phone_number) {
        this.cname = cname;
        this.phone_number = phone_number;
    }

    public SelectDateOfferModel(EditText cname, EditText phone_number,CheckBox checkme, int postion) {
        this.cname = cname;
        this.phone_number = phone_number;
        this.postion = postion;
        this.checkme= checkme;
    }

    public CheckBox getCheckme() {
        return checkme;
    }

    public void setCheckme(CheckBox checkme) {
        this.checkme = checkme;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public EditText getCname() {
        return cname;
    }

    public void setCname(EditText cname) {
        this.cname = cname;
    }

    public EditText getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(EditText phone_number) {
        this.phone_number = phone_number;
    }
}
