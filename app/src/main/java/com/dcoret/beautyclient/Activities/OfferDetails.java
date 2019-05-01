package com.dcoret.beautyclient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dcoret.beautyclient.R;

public class OfferDetails extends AppCompatActivity {
TextView offername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        Intent intent=getIntent();
        String name = intent.getStringExtra("offer_name");
            offername=findViewById(R.id.offer_name);
            offername.setText(name);

    }
}
