package com.dcoret.beautyclient.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TimePicker;

import com.dcoret.beautyclient.Adapters.CalenderAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.TimeAdapter;
import com.dcoret.beautyclient.DataClass.DateClass;
import com.dcoret.beautyclient.DataClass.TimeClass;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class IndividualBooking extends AppCompatActivity {


    Context context;
    RecyclerView recyclerView,recyclerViewtime;
    String [] item={"1","2","3","4","1","2","3","4","1","2","3","4"};

    TimePicker timePicker;
    ArrayList<TimeClass> timeClasses=new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_booking);

        timePicker=findViewById(R.id.timePicker);
        timeClasses.add(new TimeClass("09:00","10:00"));
        timeClasses.add(new TimeClass("11:00","12:00"));
        timeClasses.add(new TimeClass("13:00","14:00"));
        timeClasses.add(new TimeClass("20:00","21:00"));
//        timeClasses.add(new TimeClass("09:00:00","10:00:00"));

        timePicker.setLayoutMode(0);


        context=this;

        timePicker.setIs24HourView(true);


        recyclerView=findViewById(R.id.recycleview);
        recyclerViewtime=findViewById(R.id.recycleviewtime);
        recyclerView.setHasFixedSize(true);
        CalenderAdapter calenderAdapter =new CalenderAdapter(this, ServicesAdapter.dateClasses);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(calenderAdapter);

        TimeAdapter time =new TimeAdapter(this, timeClasses);
        LinearLayoutManager manager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewtime.setLayoutManager(manager1);
        recyclerViewtime.setAdapter(time);
    }
    }


