package com.dcoret.beautyclient.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.R;

public class ReservationDetailsFragment extends Fragment {
    View view;
    public static TextView empname,booktype,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse;
    public static LinearLayout myroot;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.incom_reservation_details_layout, container, false);

        BeautyMainPage.FRAGMENT_NAME="ReservationDetails";

        client_name=view.findViewById(R.id.name);
        myroot=view.findViewById(R.id.myroot);
        time=view.findViewById(R.id.date);
        price=view.findViewById(R.id.total_price);
        place=view.findViewById(R.id.place);
        book_at=view.findViewById(R.id.book_at);
        booktype=view.findViewById(R.id.book_type);
        service_name=view.findViewById(R.id.rname);


        APICall.browseOneBooking(ReservationsAdapter2.book_id,BeautyMainPage.context);



//              service_name.setText(MyReservationFragment.bookingAutomatedBrowseData.get(position).getService_en_name());
//                    empname.setText(bookingAutomatedBrowseData.get(position).getEmployee_name());
//                    time.setText(bookingAutomatedBrowseData.get(position).getBdb_start_date()+","+bookingAutomatedBrowseData.get(position).getBdb_start_time());
//


//
//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(ProviderMainPage.context)
//                        .setTitle("Accept")
//                        .setMessage("Do you want accept This Reservation?")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //-------------api here
//                                API.bookingProcessing1(ReservationsAdapter.book_id,"2",ProviderMainPage.context);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //----------------- api here
//                                dialog.cancel();
//                            }
//                        })
//                        .show();
//            }
//        });
//
//
//        refuse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(ProviderMainPage.context)
//                        .setTitle("Accept")
//                        .setMessage("Do you want cancel This Reservation?")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //-------------api here
//                                API.bookingProcessing1(ReservationsAdapter.book_id,"0",ProviderMainPage.context);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //----------------- api here
//                                dialog.cancel();
//                            }
//                        })
//                        .show();
//
//            }
//        });

//        addLayout(myroot,"make up","100","13:00","17-8-2019","test");

        return view;
    }


    public static void addLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bookat,String empName ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_layout_ext, myroot, false);
        TextView rname,emp_name,price,starttime,book_at;
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);

        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(priceVal);
        starttime.setText(startTimeVal);
        book_at.setText(bookat);

//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }

    public static void addHeaderLayout(final LinearLayout myroot,String client_name,String client_old ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext, myroot, false);
        TextView client_details;
        client_details=layout2.findViewById(R.id.client_details);
        client_details.setText(client_name+","+client_old);

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bookat,String empName ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext, myroot, false);
        TextView rname,emp_name,price,starttime,book_at;
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);

        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(priceVal);
        starttime.setText(startTimeVal);
        book_at.setText(bookat);

//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }


}
