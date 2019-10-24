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
    LinearLayout bride_service,multiple_individual_booking,service_hair,group_res,group_res_another;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public  static String date="";
    public static ArrayList<FilterAndSortModel> filterList=new ArrayList<>();
    public static ArrayList<ServiceFilter> serviceFilters=new ArrayList<>();

    public static String bdb_ser_id="360";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_service_type_frag, container, false);
        service_hair=view.findViewById(R.id.service_hair);

        //--------- init service filter list ------------
        for (int i=1;i<=35;i++){
            serviceFilters.add(new ServiceFilter(false,""));
        }
        Log.e("service_filter",serviceFilters.size()+"");

        toolbar= view.findViewById(R.id.toolbar);
        group_res= view.findViewById(R.id.group_res);
        group_res_another=view.findViewById(R.id.group_res_another);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeautyMainPage.navigationView.setForegroundGravity(Gravity.END);
            }
        });
        //--------------- service hair cut btn---------------
        service_hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                serviceFilters.set(33, new ServiceFilter(false, rateService.getText().toString()));
                Log.e("BDB_SER_ID","1");
                APICall.filterSortAlgorithm("33", "7", "0");
                fragment = new PlaceServiceFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        //--------------- group reservation btn------------
        group_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.filterSortAlgorithm("33", "1", "0");
                fragment = new PlaceServiceGroupFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        //---------------  reservation for others-----------
        group_res_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.filterSortAlgorithm("33", "1", "0");
                fragment = new PlaceServiceGroupOthersFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        //-------------Multiple individual booking
        multiple_individual_booking=view.findViewById(R.id.individual_res_multi);
        multiple_individual_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.filterSortAlgorithm("33", "1", "0");
                fragment = new PlaceServiceMultipleBookingFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        //------------------ toolbar icon btn ------------
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
