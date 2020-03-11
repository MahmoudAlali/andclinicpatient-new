package com.dcoret.beautyclient.Fragments;


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

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.R;

//import com.dcoret.beautyclient.API.APICallCall;


public class DepositReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Spinner category;
    public static RecyclerView service_select;
    public static ReservationsAdapter2 reservationsAdapter2;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    TextView incom_reservation,accept_reservation,deposited_reservation;
    String filter,sort;
    ImageView sortbtn;
    int layout;
    public String tmp="2";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        MyReservationFragment.tab="2";
        MyReservationFragment.groupbooking="";


        service_select=view.findViewById(R.id.incom_ree);
        sortbtn= MyReservationFragment.view.findViewById(R.id.sort);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

        APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.bookingFilter("1","7","0");

        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String book_id="";
        if (bundle != null) {
            book_id = bundle.getString("book_id");
            Log.e("NotifDepoif",book_id);

        }

        if(!book_id.equals(""))
        {
            bundle.putString("book_id", book_id);
            Log.e("NotifDepo",book_id);
            //    MyReservationFragment.reservationsAdapter2.book_id=book_id;
            Log.e("BookID",book_id);
            Intent intent=new Intent(BeautyMainPage.context, ReservatoinDetailsActivity.class);
            intent.putExtra("book_id",book_id);
            startActivity(intent);
        }


        //endregion





        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
        if (MyReservationFragment.filtercheck==false) {
            APICall.bookingAutomatedBrowse1("en", "100", MyReservationFragment.serviceId, "1", filter, "", BeautyMainPage.context, APICall.layout,tmp);
        }else {
            MyReservationFragment.filtercheck=false;
        }
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

}
