package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {
//    LinearLayout services_tabs;
    LinearLayout bride_service,service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static ArrayList<FilterAndSortModel> filterList=new ArrayList<>();
    public static ArrayList<ServiceFilter> serviceFilters=new ArrayList<>();

    public static int bdb_ser_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_service_type_frag, container, false);
        service_hair=view.findViewById(R.id.service_hair);
        for (int i=1;i<=35;i++){
            serviceFilters.add(new ServiceFilter(false,""));
        }
        Log.e("service_filter",serviceFilters.size()+"");

        toolbar= view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeautyMainPage.navigationView.setForegroundGravity(Gravity.END);
            }
        });

        service_hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                serviceFilters.set(33, new ServiceFilter(false, rateService.getText().toString()));
                Log.e("BDB_SER_ID","1");
                APICall.filterSortAlgorithm("33", "1", "0");
                fragment = new PlaceServiceFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

        return view;
    }
}
