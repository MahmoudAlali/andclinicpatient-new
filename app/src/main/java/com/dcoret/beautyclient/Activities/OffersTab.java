package com.dcoret.beautyclient.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class OffersTab extends Fragment {

    RecyclerView recyclerView;
    String[] items = {"Offer 1", "Offer 2", "Offer 3", "Offer 4", "Offer 5", "Offer 6", "Offer 7", "Offer 8", "Offer 9", "Offer 10"
            , "Offer 11", "Offer 12", "Offer 13", "Offer 14", "Offer 15", "Offer 16", "Offer 17", "Offer 18", "Offer 19", "Offer 20"
    };

    public static  String name="tabthree";

    DataService[] services=new DataService[]{
            new DataService(1,"service1","SANA'A",30,4.5,false,true),
            new DataService(1,"service2","SANA'A",40,4.5,false,true),
            new DataService(1,"service3","SANA'A",20,4.5,false,true),
            new DataService(1,"service4","SANA'A",50,4.5,false,true),
            new DataService(1,"service5","SANA'A",50,4.5,false,true),

    };
    DataService[] services1=new DataService[]{
            new DataService(2,"خدمة1","KAMAR",30,4.5,false,true),
            new DataService(2,"خدمة2","KAMAR",40,4.5,false,true),
            new DataService(2,"خدمة3","KAMAR",20,4.5,false,true),
            new DataService(2,"خدمة4","KAMAR",50,4.5,false,true),
            new DataService(2,"خدمة5","KAMAR",50,4.5,false,true),

    };
    DataService[] service2=new DataService[]{
            new DataService(3,"serv1","SAMAR",30,4.5,false,true),
            new DataService(3,"serv2","SAMAR",40,4.5,false,true),
            new DataService(3,"serv3","SAMAR",20,4.5,false,true),
            new DataService(3,"serv4","SAMAR",50,4.5,false,true),
            new DataService(3,"serv5","SAMAR",50,4.5,false,true),

    };
    DataService[] service4=new DataService[]{
            new DataService(4,"serv1","LAMA",40,4.5,false,true),
            new DataService(4,"serv2","LAMA",40,4.5,false,true),
            new DataService(4,"serv3","LAMA",20,4.5,false,true),
            new DataService(4,"serv4","LAMA",50,4.5,false,true),
            new DataService(4,"serv5","LAMA",50,4.5,false,true),

    };
    DataService[] service5=new DataService[]{
            new DataService(5,"serv1","SANA'A",50,4.5,false,true),
            new DataService(5,"serv2","SANA'A",40,4.5,false,true),
            new DataService(5,"serv3","SANA'A",20,4.5,false,true),
            new DataService(5,"serv4","SANA'A",50,4.5,false,true),
            new DataService(5,"serv5","SANA'A",50,4.5,false,true),

    };

//    DataOffer offers[]= OffersData.offers;
ArrayList<DataOffer> offers= new ArrayList<>();


//            new DataOffer[]{
//            new DataOffer("offer1",services,150,false),
//            new DataOffer("offer2",services1,150,false),
//            new DataOffer("offer3",service2,150,false),
//            new DataOffer("offer4",service4,150,false),
//            new DataOffer("offer5",service5,150,false),
//    };

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_two, container, false);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.list);


        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Offers.context));
        recyclerView.setAdapter(new OffersAdapter(Offers.context, offers, false,name));

        return view;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            recyclerView = view.findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Offers.context));
                            recyclerView.setAdapter(new OffersAdapter(Offers.context, offers, false,name));
                            return true;
                        case R.id.grid:
                            recyclerView = view.findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new GridLayoutManager(Offers.context, 2));
                            recyclerView.setAdapter(new OffersAdapter(Offers.context, offers, true,name));
                            return true;
                    }

                    return false;
                }
            };
}
