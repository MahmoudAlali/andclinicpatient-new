package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ListServicesAdapter;
import com.dcoret.beautyclient.R;

public class ListServicesBrideFragment extends Fragment {


    RecyclerView recyclerView;
    public  static ListServicesAdapter servicesAdapter;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_services_layout, container, false);
        recyclerView=view.findViewById(R.id.recycleview);
        BeautyMainPage.FRAGMENT_NAME="ListServicesBrideFragment";
        BeautyMainPage.is_bride_service="1";

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
         servicesAdapter=new ListServicesAdapter(BeautyMainPage.context, APICall.itemArrayList);
        GridLayoutManager manager = new GridLayoutManager(BeautyMainPage.context,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);


        APICall.getBasicServicebride(BeautyMainPage.context);

        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
            }
        });

        return view;
    }


}
