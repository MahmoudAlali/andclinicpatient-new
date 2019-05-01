package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Activities.Reservation;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter;

import java.util.ArrayList;

public class ReservationFragment extends Fragment {

    RecyclerView recyclerView;
    String[] items={"Resrvation 1","Resrvation 2","Resrvation 3","Resrvation 4","Resrvation 5","Resrvation 6"};
     ArrayList<DataService> services= Reservation.services;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_reservation_frag, container, false);

        recyclerView=view.findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new ReservationsAdapter(getActivity().getApplicationContext(),services));

        return view;
        }
        }
