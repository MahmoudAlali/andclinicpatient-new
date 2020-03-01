package com.dcoret.beautyclient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dcoret.beautyclient.R;

public class ServiceDetails extends AppCompatActivity {
TextView name_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        Intent intent=getIntent();
        String name = intent.getStringExtra("service_name");
        name_service=findViewById(R.id.name_service);
        name_service.setText(name);
    }
}
