package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class OfferFragment extends Fragment {

    RecyclerView recyclerView;
    public static  String name="offerfragment";
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

//    DataOffer offers[]= OffersData.offers;
ArrayList<DataOffer> offers= new ArrayList<>();

//            new DataOffer[]{
//            new DataOffer("offer1",services,150,false),
//            new DataOffer("offer2",services1,150,false),
//            new DataOffer("offer3",service2,150,false),
//            new DataOffer("offer4",service4,150,false),
//            new DataOffer("offer5",service5,150,false),
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_offers_frag, container, false);
        recyclerView= view.findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        recyclerView.setAdapter(new ReservationAdapter(getApplicationContext(),items));
        recyclerView.setAdapter(new OffersAdapter(getActivity().getApplicationContext(),offers,false,name));


        return view;
    }
}
