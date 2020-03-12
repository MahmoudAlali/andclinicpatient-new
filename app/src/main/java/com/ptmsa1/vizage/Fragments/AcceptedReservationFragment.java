package com.ptmsa1.vizage.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.ReservationsAdapter2;
import com.ptmsa1.vizage.R;


public class AcceptedReservationFragment extends Fragment {


    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    public static RecyclerView service_select;
    ImageView sortbtn;
    public static ReservationsAdapter2 reservationsAdapter2;
    String filter,sort;


    public static String tmp="1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        service_select=view.findViewById(R.id.incom_ree);
        sortbtn= MyReservationFragment.view.findViewById(R.id.sort);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        MyReservationFragment.tab="1";
        MyReservationFragment.groupbooking="";
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

        APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.bookingFilter("1","10","0");






        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
        if (MyReservationFragment.filtercheck==false) {
            APICall.bookingAutomatedBrowse1("en", "100", MyReservationFragment.serviceId, "1", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);
        }else {
            MyReservationFragment.filtercheck=false;
        }
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

}
