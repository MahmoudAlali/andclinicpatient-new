package com.dcoret.beautyclient.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.BookingRequestsAdapter;
import com.dcoret.beautyclient.Fragments.MyBookingRequestsFragment;
import com.dcoret.beautyclient.R;

public class NewBookingRequestsFragment extends Fragment {

    Fragment fragment;

    public static RecyclerView service_select;
    ImageView sortbtn;
    public static BookingRequestsAdapter bookingRequestsAdapter;
    String filter,sort;
    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    public static String tmp="1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        service_select=view.findViewById(R.id.incom_ree);
        sortbtn= MyBookingRequestsFragment.view.findViewById(R.id.sort);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        MyBookingRequestsFragment.tab="1";
        MyBookingRequestsFragment.groupbooking="";
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

      //  APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.bookingFilter("1","9","0");






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
