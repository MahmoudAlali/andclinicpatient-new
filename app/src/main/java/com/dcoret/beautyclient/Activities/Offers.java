package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.ReservationDialog;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.Adapters.OffersAdapterNew;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class Offers extends AppCompatActivity {
    public static Context context;
    RecyclerView recyclerView;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};
    public static  String name="offers";
    BottomNavigationView navigation;
    TextView offer_text;
ArrayList<DataService> arr=new ArrayList<>();


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

    ArrayList<DataOffer> offers= new ArrayList<>();

//            new DataOffer[]{
//      new DataOffer("offer2",services1,150,false),
//      new DataOffer("offer3",service2,150,false),
//      new DataOffer("offer4",service4,150,false),
//      new DataOffer("offer5",service5,150,false),
//    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);
        setTitle("ابرز العروض");
        new ReservationDialog("offers");
        arr.add(services1[0]);
        arr.add(services1[1]);
        arr.add(services1[2]);

        context = this;

        offer_text = findViewById(R.id.offer_text);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.beauty);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        recyclerView = findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new OffersAdapterNew(getApplicationContext(), items ));
    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.beauty:
                            offer_text.setText("ابرز عروض التجميل");
//                            navigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#66BBF3")));
//                            navigation.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#66BBF3")));
                            recyclerView = findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new OffersAdapterNew(getApplicationContext(), items ));
                            return true;
                        case R.id.fashion:
                            offer_text.setText("ابرز عروض الازياء");
                            recyclerView =findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new ServicesAdapter(getApplicationContext(), items));
                            return true;
                    }

                    return false;
                }
            };




    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    static int section;

    public void servicesBeauty(View view) {
        Intent in=new Intent(context,BeautyMainPage.class);
        startActivity(in);

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
