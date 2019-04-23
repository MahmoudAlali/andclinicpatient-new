package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.MyReservations;
import com.dcoret.beautyclient.OffersAdapter;
import com.dcoret.beautyclient.R;

public class ServiceFragment extends Fragment {
LinearLayout services_tabs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_service_type_frag, container, false);
        services_tabs=view.findViewById(R.id.services_tabs);
        services_tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(), MyReservations.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
