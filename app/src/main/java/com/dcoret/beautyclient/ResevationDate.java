package com.dcoret.beautyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dcoret.beautyclient.Activities.BeautyMainPage;

public class ResevationDate extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resevation_date);

    }

    public void reservationTime(View view) {
        Intent intent=new Intent(this,ReservationTime.class);
        finish();
        startActivity(intent);
    }


}
