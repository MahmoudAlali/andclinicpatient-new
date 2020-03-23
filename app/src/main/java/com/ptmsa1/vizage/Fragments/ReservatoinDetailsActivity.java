package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.ReservationsAdapter2;
import com.ptmsa1.vizage.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReservatoinDetailsActivity extends AppCompatActivity {
//    View view;
public static TextView id,empname,booktype,ac_total_price,salonName,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse;
    public static LinearLayout myroot;
    TextView v1,v2,v3,v4;
    RadioButton r1,r2,r3,r4;
    static  Context context;
    public static String logoId;
    ImageView logoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incom_reservation_details_layout);


         context=this;
        client_name=findViewById(R.id.name);
        myroot=findViewById(R.id.myroot);
        time=findViewById(R.id.date);
        price=findViewById(R.id.total_price);
        ac_total_price=findViewById(R.id.ac_total_price);
        place=findViewById(R.id.place);
        book_at=findViewById(R.id.book_at);
        booktype=findViewById(R.id.book_type);
        service_name=findViewById(R.id.rname);
        salonName=findViewById(R.id.salon_name);
        logoId="";
        logoImg=findViewById(R.id.logoImg);
        id=findViewById(R.id.id);

        //region Check_Notification
        String book_id="";

        try
        {
            Log.e("Notif", "Reserv Details is trying to get bookid");
            book_id=getIntent().getStringExtra("book_id");
        }
        catch (Exception e)
        {
            Log.e("NotifErr",e.getMessage());
        }

        if(book_id!=null)
        {

            APICall.browseOneBooking(book_id,context,logoImg);
            id.setText(book_id);
            //APICall.getSalonLogo(BeautyMainPage.context,ReservationsAdapter2.logoId,logoImg);

        }
        else
        {
            APICall.browseOneBooking(ReservationsAdapter2.book_id,context,logoImg);
            id.setText(ReservationsAdapter2.book_id);
            // APICall.getSalonLogo(BeautyMainPage.context,ReservationsAdapter2.logoId,logoImg);
        }

        //endregion


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
       // APICall.browseOneBooking(ReservationsAdapter2.book_id,context);



//              service_name.setText(MyReservationFragment.bookingAutomatedBrowseData.get(position).getService_en_name());
//                    empname.setText(bookingAutomatedBrowseData.get(position).getEmployee_name());
//                    time.setText(bookingAutomatedBrowseData.get(position).getBdb_start_date()+","+bookingAutomatedBrowseData.get(position).getBdb_start_time());
//


//
//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(BeautyMainPage.context)
//                        .setTitle("Accept")
//                        .setMessage("Do you want accept This Reservation?")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //-------------api here
//                                API.bookingProcessing1(ReservationsAdapter.book_id,"2",BeautyMainPage.context);
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
//                new AlertDialog.Builder(BeautyMainPage.context)
//                        .setTitle("Accept")
//                        .setMessage("Do you want cancel This Reservation?")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //-------------api here
//                                API.bookingProcessing1(ReservationsAdapter.book_id,"0",BeautyMainPage.context);
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

//    public static void addLayout(final LinearLayout myroot,String details,String reservationName,String priceVal,String startTimeVal,String end_time,String bookat,String empName ){
//        final View layout2;
//        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_layout_ext, myroot, false);
//        TextView endtime,rname,emp_name,price,starttime,client_details,book_at;
//        price=layout2.findViewById(R.id.price);
//        client_details=layout2.findViewById(R.id.client_details);
//        rname=layout2.findViewById(R.id.rname);
//        starttime=layout2.findViewById(R.id.time);
//        endtime=layout2.findViewById(R.id.end_time);
//        book_at=layout2.findViewById(R.id.book_at);
//        emp_name=layout2.findViewById(R.id.emp_name);
//
//        rname.setText(reservationName);
//        client_details.setText(details);
//        endtime.setText(APICall.convertToArabic(end_time));
//        emp_name.setText(empName);
//        price.setText(APICall.convertToArabic(priceVal));
//        starttime.setText(APICall.convertToArabic(startTimeVal));
//        book_at.setText(APICall.convertToArabic(bookat));
//
////
//        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myroot.addView(layout2);
//            }
//        });
////
//    }
//    public static void addHeaderLayout(final LinearLayout myroot,String client_name,String client_old ){
//        final View layout2;
//        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext, myroot, false);
//        TextView client_details;
//        client_details=layout2.findViewById(R.id.client_details);
//        client_details.setText(client_name+","+client_old);
//
//        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myroot.addView(layout2);
//            }
//        });
////
//    }
//    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String endtime,String bookat,String empName ){
//        final View layout2;
//        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext, myroot, false);
//        TextView end_time,rname,emp_name,price,starttime,book_at;
//        price=layout2.findViewById(R.id.price);
//        rname=layout2.findViewById(R.id.rname);
//        end_time=layout2.findViewById(R.id.end_time);
//        starttime=layout2.findViewById(R.id.time);
//        book_at=layout2.findViewById(R.id.book_at);
//        emp_name=layout2.findViewById(R.id.emp_name);
//
//        rname.setText(reservationName);
//        end_time.setText(APICall.convertToArabic(endtime));
//        emp_name.setText(APICall.convertToArabic(empName));
//        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
//        starttime.setText(APICall.convertToArabic(startTimeVal));
//        book_at.setText(APICall.convertToArabic(bookat));
//
////
//        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myroot.addView(layout2);
//            }
//        });
////
//    }
    public static void addLayout(final LinearLayout myroot, String reservationName, String priceVal, String startTimeVal, String bdb_end_time, String bookat, String empName,String ID ,String isExec,String ac_price,String j_cost,String j_time){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_layout_ext, myroot, false);
        TextView rname,emp_name,client_details,price,end_time,starttime,book_at,actual_price,price_j_cost,journey_time;
        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        LinearLayout ac_price_lay;
        price=layout2.findViewById(R.id.price);
        actual_price=layout2.findViewById(R.id.actual_price);
        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);
        price_j_cost=layout2.findViewById(R.id.price_j_cost);
        journey_time=layout2.findViewById(R.id.journey_time);
        client_details=layout2.findViewById(R.id.client_details);
        rname=layout2.findViewById(R.id.rname);
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        end_time=layout2.findViewById(R.id.end_time);
        emp_name=layout2.findViewById(R.id.emp_name);

        if (ac_price.equals("0") ||ac_price.equals("") ){
            ac_price_lay.setVisibility(View.GONE);
        }else {
            actual_price.setText(ac_price);
            ReservatoinDetailsActivity.price.setPaintFlags(ReservatoinDetailsActivity.price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }

        client_details.setText(client_name.getText().toString()+", "+priceVal+" "+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        if(isExec.equals(1))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else             isExecuted.setImageResource(R.drawable.ic_cancel);

        layout2.findViewById(R.id.jcost_lay).setVisibility(View.GONE);
        layout2.findViewById(R.id.jtime_lay).setVisibility(View.GONE);

        if (!place.getText().toString().equals(R.string.salon)) {
         layout2.findViewById(R.id.jcost_lay).setVisibility(View.VISIBLE);
         layout2.findViewById(R.id.jtime_lay).setVisibility(View.VISIBLE);
            price_j_cost.setText(j_cost + " " + context.getResources().getString(R.string.ryal));
            journey_time.setText(j_time + " " + context.getResources().getString(R.string.minute));
        }
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));


        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }

    public static void addHeaderLayout(final LinearLayout myroot, String client_name, String client_old , JSONArray bookings){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext, myroot, false);
        TextView client_details;
        client_details=layout2.findViewById(R.id.client_details);
        client_details.setText(client_name);
        int ptmp=0;
        try {
            for (int j = 0; j < bookings.length(); j++) {
                JSONObject object = bookings.getJSONObject(j);
                String bdb_price = object.getString("bdb_price");
                ptmp=ptmp+Integer.parseInt(bdb_price);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String priceRes=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.price);
        if (client_name.equals("booking_wast_time")){
            client_details.setText(R.string.journey_time_emp);
        }else
            client_details.setText(client_name+", "+priceRes+APICall.convertToArabic(ptmp+"")+" "+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bdb_end_time,String bookat,String empName ,String isExec,String ac_price,String jcost,String jtime){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext_v1, myroot, false);
        TextView rname,emp_name,price,starttime,book_at,end_time,actual_price,j_cost,j_time,id;
        LinearLayout ac_price_lay;

        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        end_time=layout2.findViewById(R.id.end_time);
        actual_price=layout2.findViewById(R.id.actual_price);
        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);
        j_cost=layout2.findViewById(R.id.price_j_cost);
        j_time=layout2.findViewById(R.id.journey_time);

        if (ac_price.equals("0")){
            ac_price_lay.setVisibility(View.GONE);
        }else {
            actual_price.setText(ac_price);
        }
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else             isExecuted.setImageResource(R.drawable.ic_cancel);



//        j_cost=layout2.findViewById(R.id.price_j_cost);

//        j_cost.setText(jCost);
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));
        layout2.findViewById(R.id.jcost_lay).setVisibility(View.GONE);
        layout2.findViewById(R.id.jtime_lay).setVisibility(View.GONE);

        if (!place.getText().toString().equals(R.string.salon)) {
            layout2.findViewById(R.id.jcost_lay).setVisibility(View.VISIBLE);
            layout2.findViewById(R.id.jtime_lay).setVisibility(View.VISIBLE);
            j_cost.setText(jcost + " " + context.getResources().getString(R.string.ryal));
            j_time.setText(jtime + " " + context.getResources().getString(R.string.minute));
        }
//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bdb_end_time,String bookat,String empName ,String Id,String isExec,String ac_price,String jtime,String jcost){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext_v1, myroot, false);
        TextView rname,emp_name,price,starttime,book_at,end_time,actual_price,j_cost,j_time,id;
        LinearLayout ac_price_lay;

        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        end_time=layout2.findViewById(R.id.end_time);
        actual_price=layout2.findViewById(R.id.actual_price);
        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);
        j_cost=layout2.findViewById(R.id.price_j_cost);
        j_time=layout2.findViewById(R.id.journey_time);
        if (ac_price.equals("0")){
            ac_price_lay.setVisibility(View.GONE);
        }else {
            actual_price.setText(ac_price);
        }
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else
            isExecuted.setImageResource(R.drawable.ic_cancel);

        j_cost=layout2.findViewById(R.id.journey_time);

        j_cost.setText(jtime);
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));


        layout2.findViewById(R.id.jcost_lay).setVisibility(View.GONE);
        layout2.findViewById(R.id.jtime_lay).setVisibility(View.GONE);

        if (!place.getText().toString().equals(R.string.salon)) {
            layout2.findViewById(R.id.jcost_lay).setVisibility(View.VISIBLE);
            layout2.findViewById(R.id.jtime_lay).setVisibility(View.VISIBLE);
            j_cost.setText(jcost + " " + context.getResources().getString(R.string.ryal));
            j_time.setText(jtime + " " + context.getResources().getString(R.string.minute));
        }
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addMainLayoutLost(final LinearLayout myroot,String startTimeVal,String bdb_end_time,String empName,String reasontxt ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_lost_ext, myroot, false);
        TextView emp_name,starttime,reason;
        starttime=layout2.findViewById(R.id.time);
        emp_name=layout2.findViewById(R.id.emp_name);
        reason=layout2.findViewById(R.id.reason);
        reason.setText(R.string.journey_lost_time);

//        if (reasontxt.equals("0")) {
//            reason.setText(R.string.journey_lost_time);
//        }else if (reasontxt.equals("1")) {
//            reason.setText(R.string.rel_ser_lost_time);
//        }else if (reasontxt.equals("2")) {
//            reason.setText(R.string.wait_emp_lost_time);
//        }
        emp_name.setText(empName);
        starttime.setText(APICall.convertToArabic(startTimeVal+" ~ "+bdb_end_time));

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
