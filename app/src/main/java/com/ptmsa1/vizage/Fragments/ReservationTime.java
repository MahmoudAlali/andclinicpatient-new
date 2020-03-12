package com.ptmsa1.vizage.Fragments;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.ptmsa1.vizage.R;

public class ReservationTime extends AppCompatActivity {

    public static String time;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_time);
        timePicker=findViewById(R.id.timePicker);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void doneclick(View view) {
//        Toast.makeText(this,"استقبلنا طلبك ، سنرد خلال 30 دقيقة كحد أقصى",Toast.LENGTH_LONG).show();
        time=timePicker.getHour()+":"+timePicker.getMinute();
        AddReservation.setDateAndTime(ResevationDate.date,time);
        onBackPressed();
    }
}
