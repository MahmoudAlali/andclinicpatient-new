package com.dcoret.beautyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class Reservation extends AppCompatActivity {
    RecyclerView recyclerView;
    String[] items={"Resrvation 1","Resrvation 2","Resrvation 3","Resrvation 4","Resrvation 5","Resrvation 6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        setTitle("حجوزاتي");
        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReservationsAdapter(getApplicationContext(),items));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menue, menu);
        return true;
    }

    public void myservices(View view) {
        Intent intent=new Intent(this,
                MyReservations.class  // خطا تسمية هي Services

        );
        startActivity(intent);
    }

    public void offers(View view) {
        Intent intent=new Intent(this,Offers.class);
        startActivity(intent);
    }
}
