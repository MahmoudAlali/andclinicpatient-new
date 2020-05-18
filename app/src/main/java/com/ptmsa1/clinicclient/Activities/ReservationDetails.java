package com.ptmsa1.clinicclient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ptmsa1.clinicclient.R;

public class ReservationDetails extends AppCompatActivity {
    TextView res_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        Intent intent=getIntent();
        String name=intent.getStringExtra("reservation_name");
        res_name=findViewById(R.id.res_name);
        res_name.setText(name);
    }
}
