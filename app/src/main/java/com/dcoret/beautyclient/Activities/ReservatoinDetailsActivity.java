package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.R;

public class ReservatoinDetailsActivity extends AppCompatActivity {
//    View view;
    public static TextView empname,booktype,salonName,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse;
    public static LinearLayout myroot;
    TextView v1,v2,v3,v4;
    RadioButton r1,r2,r3,r4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incom_reservation_details_layout);


        Context context=this;
        client_name=findViewById(R.id.name);
        myroot=findViewById(R.id.myroot);
        time=findViewById(R.id.date);
        price=findViewById(R.id.total_price);
        place=findViewById(R.id.place);
        book_at=findViewById(R.id.book_at);
        booktype=findViewById(R.id.book_type);
        service_name=findViewById(R.id.rname);
        salonName=findViewById(R.id.salon_name);

//        v1=findViewById(R.id.vfirst);
//        v2=findViewById(R.id.vsecond);
//        v3=findViewById(R.id.vthird);
//        v4=findViewById(R.id.vforth);
//
//        r1=findViewById(R.id.rfirst);
//        r2=findViewById(R.id.rsecond);
//        r3=findViewById(R.id.rthird);
//        r4=findViewById(R.id.rforth);


//        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                v1.setBackgroundResource(R.color.green);
//            }
//        });
//
//        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                v2.setBackgroundResource(R.color.green);
//            }
//        });
//        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                v3.setBackgroundResource(R.color.green);
//            }
//        });
//        r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                v4.setBackgroundResource(R.color.green);
//            }
//        });
        APICall.browseOneBooking(ReservationsAdapter2.book_id,context);



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


        Button ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public static void addLayout(final LinearLayout myroot,String details,String reservationName,String priceVal,String startTimeVal,String end_time,String bookat,String empName ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_layout_ext, myroot, false);
        TextView endtime,rname,emp_name,price,starttime,client_details,book_at;
        price=layout2.findViewById(R.id.price);
        client_details=layout2.findViewById(R.id.client_details);
        rname=layout2.findViewById(R.id.rname);
        starttime=layout2.findViewById(R.id.time);
        endtime=layout2.findViewById(R.id.end_time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);

        rname.setText(reservationName);
        client_details.setText(details);
        endtime.setText(APICall.convertToArabic(end_time));
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));

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
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String endtime,String bookat,String empName ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext, myroot, false);
        TextView end_time,rname,emp_name,price,starttime,book_at;
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        end_time=layout2.findViewById(R.id.end_time);
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);

        rname.setText(reservationName);
        end_time.setText(APICall.convertToArabic(endtime));
        emp_name.setText(APICall.convertToArabic(empName));
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));

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
