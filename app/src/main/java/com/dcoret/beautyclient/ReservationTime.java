package com.dcoret.beautyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ReservationTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_time);
    }

    public void doneclick(View view) {
        onBackPressed();
    }
}
