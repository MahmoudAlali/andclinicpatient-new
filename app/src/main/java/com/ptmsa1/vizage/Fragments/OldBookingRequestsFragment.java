package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.BookingRequestDetailsActivity;
import com.ptmsa1.vizage.Adapters.BookingRequestsAdapter;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class OldBookingRequestsFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Spinner category;
    public static RecyclerView service_select;
    public static BookingRequestsAdapter bookingRequestsAdapter;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    TextView incom_reservation,accept_reservation,deposited_reservation;
    String filter,sort;
    ImageView sortbtn;
    int layout;
    ArrayList<String> filters=new ArrayList<>();

    public String tmp="2";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        BeautyMainPage.FRAGMENT_NAME="oldBookingRequestsFragment";
        MyBookingRequestsFragment.tab="2";
/*
        MyBookingRequestsFragment.groupbooking="";
*/


        service_select=view.findViewById(R.id.incom_ree);
        sortbtn= MyBookingRequestsFragment.view.findViewById(R.id.sort);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

       // APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.Filter("1","8","0");

        filters.clear();
        filters.add(APICall.filter);
        if(!MyBookingRequestsFragment.dateFilter.equals(""))
            filters.add(MyBookingRequestsFragment.dateFilter);
        if(!MyBookingRequestsFragment.typeFilter.equals(""))
            filters.add(MyBookingRequestsFragment.typeFilter);
        if(!MyBookingRequestsFragment.salonFilter.equals(""))
            filters.add(MyBookingRequestsFragment.salonFilter);

        APICall.filter=APICall.Filter(filters);
        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String order_id="";
        if (bundle != null) {
            order_id = bundle.getString("order_id");
            Log.e("NotifDepoif",order_id);

        }

        if(!order_id.equals(""))
        {
            /*bundle.putString("order_id", order_id);
            Log.e("NotifDepo",order_id);
            //    MyReservationFragment.reservationsAdapter2.book_id=book_id;
            Log.e("order_id",order_id);*/
            Intent intent=new Intent(BeautyMainPage.context, BookingRequestDetailsActivity.class);
            intent.putExtra("order_id",order_id);
            startActivity(intent);
        }


        //endregion





        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
      //  if (MyBookingRequestsFragment.filtercheck==false) {
            APICall.requestsAutomatedBrowse("en", "100", MyBookingRequestsFragment.serviceId, "1", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);
        /*}else {
            MyBookingRequestsFragment.filtercheck=false;
        }*/
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

}
