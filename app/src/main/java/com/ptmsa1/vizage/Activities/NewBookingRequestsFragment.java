package com.ptmsa1.vizage.Activities;

import android.app.Fragment;
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
import com.ptmsa1.vizage.Adapters.BookingRequestsAdapter;
import com.ptmsa1.vizage.Fragments.MyBookingRequestsFragment;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class NewBookingRequestsFragment extends Fragment {

    Fragment fragment;

    public static RecyclerView service_select;
    ImageView sortbtn;
    public static BookingRequestsAdapter bookingRequestsAdapter;
    String filter,sort;
    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};
    ArrayList<String> filters=new ArrayList<>();

    public static String tmp="1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        service_select=view.findViewById(R.id.incom_ree);
        sortbtn= MyBookingRequestsFragment.view.findViewById(R.id.sort);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        MyBookingRequestsFragment.tab="1";
/*
        MyBookingRequestsFragment.groupbooking="";
*/
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

      //  APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.Filter("1","9","0");
        filters.clear();

        filters.add(APICall.filter);
        if(!MyBookingRequestsFragment.dateFilter.equals(""))
            filters.add(MyBookingRequestsFragment.dateFilter);
        if(!MyBookingRequestsFragment.typeFilter.equals(""))
            filters.add(MyBookingRequestsFragment.typeFilter);
        if(!MyBookingRequestsFragment.salonFilter.equals(""))
            filters.add(MyBookingRequestsFragment.salonFilter);

        APICall.filter=APICall.Filter(filters);


        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
     //   if (MyBookingRequestsFragment.filtercheck==false) {
            APICall.requestsAutomatedBrowse("en", "100", MyBookingRequestsFragment.serviceId, "1", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);
       /* }else {
            MyBookingRequestsFragment.filtercheck=false;
        }*/
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }
}
