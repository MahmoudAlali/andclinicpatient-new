package com.dcoret.beautyclient.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.Activities.BeautyMainPage_2;
import com.dcoret.beautyclient.Activities.Mapfragment;
import com.dcoret.beautyclient.Activities.MyReservations;
import com.dcoret.beautyclient.R;

public class MapFragment extends Fragment {

    LinearLayout map;
    FloatingActionButton filter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_map_filtering, container, false);
//        services_tabs=view.findViewById(R.id.services_tabs);
//        services_tabs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity().getApplicationContext(), MyReservations.class);
//                startActivity(intent);
//            }
//        });

        map=view.findViewById(R.id.map);
        android.support.v4.app.Fragment fragment = new Mapfragment();
        ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.map,fragment).commit();


        filter=view.findViewById(R.id.filter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(BeautyMainPage_2.context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.filter_dialog);
                dialog.setTitle("Filtering");

                dialog.show();
            }
        });


        return view;
    }
}
