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

import com.dcoret.beautyclient.Activities.MyReservations;
import com.dcoret.beautyclient.Adapters.NotificationAdapter;
import com.dcoret.beautyclient.R;


public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    String notify[]={"Notify1","Notify2","Notify3","Notify4","Notify5","Notify6","Notify7","Notify8"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_notification, container, false);
//        services_tabs=view.findViewById(R.id.services_tabs);
//        services_tabs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity().getApplicationContext(), MyReservations.class);
//                startActivity(intent);
//            }
//        });
        recyclerView = view.findViewById(R.id.notify_re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new NotificationAdapter(getActivity().getApplicationContext(), notify));

        return view;
    }
}
