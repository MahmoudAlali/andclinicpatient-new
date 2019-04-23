package com.dcoret.beautyclient;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;

public class Offers extends AppCompatActivity {
    static Context context;
    RecyclerView recyclerView;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};

    DataService[] services=new DataService[]{
      new DataService(1,"service1",30,4.5,false,true),
      new DataService(1,"service2",40,4.5,false,true),
      new DataService(1,"service3",20,4.5,false,true),
      new DataService(1,"service4",50,4.5,false,true),
      new DataService(1,"service5",50,4.5,false,true),

    };
    DataService[] services1=new DataService[]{
            new DataService(2,"خدمة1",30,4.5,false,true),
            new DataService(2,"خدمة2",40,4.5,false,true),
            new DataService(2,"خدمة3",20,4.5,false,true),
            new DataService(2,"خدمة4",50,4.5,false,true),
            new DataService(2,"خدمة5",50,4.5,false,true),

    };
    DataService[] service2=new DataService[]{
            new DataService(3,"serv1",30,4.5,false,true),
            new DataService(3,"serv2",40,4.5,false,true),
            new DataService(3,"serv3",20,4.5,false,true),
            new DataService(3,"serv4",50,4.5,false,true),
            new DataService(3,"serv5",50,4.5,false,true),

    };
    DataService[] service4=new DataService[]{
            new DataService(4,"serv1",40,4.5,false,true),
            new DataService(4,"serv2",40,4.5,false,true),
            new DataService(4,"serv3",20,4.5,false,true),
            new DataService(4,"serv4",50,4.5,false,true),
            new DataService(4,"serv5",50,4.5,false,true),

    };
    DataService[] service5=new DataService[]{
            new DataService(5,"serv1",50,4.5,false,true),
            new DataService(5,"serv2",40,4.5,false,true),
            new DataService(5,"serv3",20,4.5,false,true),
            new DataService(5,"serv4",50,4.5,false,true),
            new DataService(5,"serv5",50,4.5,false,true),

    };

    DataOffer offers[]=new DataOffer[]{
      new DataOffer("offer1",services,150,false),
      new DataOffer("offer2",services1,150,false),
      new DataOffer("offer3",service2,150,false),
      new DataOffer("offer4",service4,150,false),
      new DataOffer("offer5",service5,150,false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        setTitle("ابرز العروض");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView=findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OffersAdapter(getApplicationContext(),offers,false));



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }




    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            recyclerView = findViewById(R.id.review);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new OffersAdapter(getApplicationContext(), offers, false));
                            return true;
                        case R.id.grid:
                            recyclerView =findViewById(R.id.review);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                            recyclerView.setAdapter(new OffersAdapter(getApplicationContext(), offers, true));
                            return true;
                    }

                    return false;
                }
            };











    public void reservation(View view) {
        Intent intent=new Intent(getApplicationContext(),Reservation.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu3, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(this,ShoppingCart.class);
            startActivity(intent);

        }else if(id==R.id.notify){
            Intent intent=new Intent(this,Notification.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
