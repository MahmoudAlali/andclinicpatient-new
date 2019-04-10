package com.dcoret.beautyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Notification extends AppCompatActivity {
    String notify[]={"Notify1","Notify2","Notify3","Notify4","Notify5","Notify6","Notify7","Notify8"};
        RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        recyclerView = findViewById(R.id.notify_re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new NotificationAdapter(getApplicationContext(), notify));

    }
}
