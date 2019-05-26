package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.MyReservations;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.R;

public class ServiceFragment extends Fragment {
LinearLayout services_tabs;
LinearLayout bride_service,service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_service_type_frag, container, false);
        service_hair=view.findViewById(R.id.service_hair);
//        bride_service=view.findViewById(R.id.bride_service);
        service_hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ServicesTabsFragment2();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
