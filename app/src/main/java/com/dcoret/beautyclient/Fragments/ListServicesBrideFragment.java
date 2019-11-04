package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_services_layout, container, false);
        recyclerView=view.findViewById(R.id.recycleview);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
         servicesAdapter=new ListServicesAdapter(BeautyMainPage.context, APICall.itemArrayList);
        GridLayoutManager manager = new GridLayoutManager(BeautyMainPage.context,3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);


        APICall.getBasicServicebride(BeautyMainPage.context);


        return view;
    }


}
