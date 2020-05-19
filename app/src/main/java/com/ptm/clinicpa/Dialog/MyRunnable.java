package com.ptm.clinicpa.Dialog;

public class MyRunnable implements Runnable {

    private String value;
    public void setValue(String val)
    {
        value=val;
    }
    public String getValue()
    {
        return value;
    }

    @Override
    public void run() {

    }
}
