package com.dcoret.beautyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class Offers extends AppCompatActivity {
    RecyclerView recyclerView;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        setTitle("ابرز العروض");

        recyclerView=findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OffersAdapter(getApplicationContext(),items));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menue, menu);
        return true;
    }

    public void reservation(View view) {
        Intent intent=new Intent(getApplicationContext(),Reservation.class);
        startActivity(intent);
    }
}
