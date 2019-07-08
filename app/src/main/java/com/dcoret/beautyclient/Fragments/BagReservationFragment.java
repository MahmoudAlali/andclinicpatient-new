package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.BagReservationAdapter;
import com.dcoret.beautyclient.DataClass.DataReservation;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class BagReservationFragment extends Fragment {

    RecyclerView recyclerView;
    String[] items={"Resrvation 1","Resrvation 2","Resrvation 3","Resrvation 4","Resrvation 5","Resrvation 6"};
    //an error
//     ArrayList<DataService> services= Reservation.services;
    public static ArrayList<DataReservation> reservations=new ArrayList<>();


    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_bag_reservation_frag, container, false);


        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });



        recyclerView=view.findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new BagReservationAdapter(getActivity().getApplicationContext(),items));

        return view;
        }
        }
