package com.dcoret.beautyclient.API;

import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class LayoutList {

    RadioButton empName;
    Spinner avlTime;

    public LayoutList(RadioButton empName, Spinner avlTime) {
        this.empName = empName;
        this.avlTime = avlTime;
    }

    public LayoutList(RadioButton empName) {
        this.empName = empName;

    }
    public RadioButton getEmpName() {
        return empName;
    }

    public void setEmpName(RadioButton empName) {
        this.empName = empName;
    }

    public Spinner getAvlTime() {
        return avlTime;
    }

    public void setAvlTime(Spinner avlTime) {
        this.avlTime = avlTime;
    }
}
