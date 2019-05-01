package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.Activities.MyReservations;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.R;

public class ServiceFragment extends Fragment {
LinearLayout services_tabs;
LinearLayout bride_service;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_service_type_frag, container, false);
        services_tabs=view.findViewById(R.id.services_tabs);
        bride_service=view.findViewById(R.id.bride_service);
        services_tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), MyReservations.class);
                startActivity(intent);
            }
        });

        bride_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), Offers.class);
                startActivity(intent);
            }
        });



        return view;
    }
}
