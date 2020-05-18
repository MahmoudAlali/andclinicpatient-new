package com.ptmsa1.clinicclient.Fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.ptmsa1.clinicclient.R;

public class ResevationDate extends AppCompatActivity  {


    public static  String date;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resevation_date);
        datePicker=findViewById(R.id.datePicker);
    }

    public void reservationTime(View view) {
        Intent intent=new Intent(this,ReservationTime.class);
        date=datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear();
        finish();
        startActivity(intent);
    }


}
