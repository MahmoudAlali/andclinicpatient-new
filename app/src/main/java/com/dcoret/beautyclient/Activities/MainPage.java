package com.dcoret.beautyclient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.dcoret.beautyclient.R;

public class MainPage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_example2);
        setTitle("Beauty");

    }

    public void mypoint(View view) {
        Intent intent=new Intent(this, Point.class);
        startActivity(intent);
    }

    public void services(View view) {
        Intent intent=new Intent(this, Services.class);
        startActivity(intent);

    }

    public void offers(View view) {//ابرز العروض
        Intent intent=new Intent(this,Offers.class);
        startActivity(intent);
    }

    public void myreservation(View view) {//حجوزاتي
        Intent intent=new Intent(this,Reservation.class);
        startActivity(intent);

    }


    public void manageAccount(View view) {
        Intent intent=new Intent(this,ManageAccount.class);
        startActivity(intent);
    }

    public void extraServices(View view) {
        Intent intent=new Intent(this,ExtraServices.class);
        startActivity(intent);
    }

    public void favoriteslist(View view) {
        Intent intent=new Intent(this,FavoritesList.class);
        startActivity(intent);

    }

    public void customerServices(View view) {
        Intent intent=new Intent(this,CustomerService.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menue, menu);
        return true;
    }




}
