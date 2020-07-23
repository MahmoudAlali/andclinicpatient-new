package com.ptm.clinicpa.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.ReservationsAdapter2;
import com.ptm.clinicpa.MapsActivityLocation;
import com.ptm.clinicpa.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReservatoinDetailsActivity extends AppCompatActivity {
//    View view;
public static TextView costTxt,medFileNumber,journeyCost,description, id,empname,booktype,journey_time,phone_number,start_date,ref_id,ac_total_price,salonName,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse;
    public static LinearLayout jrCostLayout,jtime_lay,book_exec_layout, myroot;
    TextView v1,v2,v3,v4;
    RadioButton r1,r2,r3,r4;
    static  Context context;
    public static String logoId;
    ImageView logoImg;
    public static Double lat,lang;

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
        costTxt=findViewById(R.id.costTxt);
        salonName=findViewById(R.id.salon_name);
        logoId="";
        logoImg=findViewById(R.id.logoImg);
        ref_id=findViewById(R.id.ref_id);
        id=findViewById(R.id.id);
        start_date=findViewById(R.id.start_date);
        phone_number=findViewById(R.id.phone_number);
        journey_time=findViewById(R.id.journey_time);
        jtime_lay=findViewById(R.id.jtime_lay);
        book_exec_layout=findViewById(R.id.book_exec_layout);
        description=findViewById(R.id.description);
        journeyCost=findViewById(R.id.journey_price);
        jrCostLayout=findViewById(R.id.jrcostLayout);
        medFileNumber=findViewById(R.id.medical_id);

        //region Check_Notification
        String book_id="";

        String internally_book=getIntent().getStringExtra("internally_book");
        Boolean isNew=getIntent().getBooleanExtra("isNew",false);


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

            APICall.browseOneBooking(book_id,context,logoImg,(CardView) findViewById(R.id.myCardView));
            id.setText(internally_book);
            ref_id.setText(book_id);
            //APICall.getSalonLogo(BeautyMainPage.context,ReservationsAdapter2.logoId,logoImg);

        }
        else
        {
            APICall.browseOneBooking(ReservationsAdapter2.book_id,context,logoImg,(CardView) findViewById(R.id.myCardView));
            id.setText(internally_book);
            ref_id.setText(ReservationsAdapter2.book_id);
            // APICall.getSalonLogo(BeautyMainPage.context,ReservationsAdapter2.logoId,logoImg);
        }

        //endregion

//        id.setText(internally_book);
//        ref_id.setText(ReservationsAdapter2.book_id);
//        v1=findViewById(R.id.vfirst);
//        v2=findViewById(R.id.vsecond);
//        v3=findViewById(R.id.vthird);
//        v4=findViewById(R.id.vforth);
//
//        r1=findViewById(R.id.rfirst);
//        r2=findViewById(R.id.rsecond);
//        r3=findViewById(R.id.rthird);
//        r4=findViewById(R.id.rforth);

        phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(ReservatoinDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                        return;
                    }
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + phone_number.getText().toString().trim()));
                    startActivity(callIntent);
                } else {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + phone_number.getText().toString().trim()));
                    startActivity(callIntent);
                }
            }
        });

        ImageView ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                if (lang!=null && lat!=null) {
                    Intent intent = new Intent(context, MapsActivityLocation.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lang", lang);
                    context.startActivity(intent);
                }


            }
        });


    }

/*
    public static void addLayout(final LinearLayout myroot, String reservationName, String priceVal, String startTimeVal, String bdb_end_time, String bookat, String empName,String ID ,String isExec,String ac_price,String j_cost,String j_time,String c_old,String user_name){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_layout_ext, myroot, false);
        TextView rname,emp_name,old,cname,client_details,price,end_time,starttime,book_at,actual_price,price_j_cost,journey_time;
        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
//        LinearLayout ac_price_lay;
        price=layout2.findViewById(R.id.price);
        actual_price=layout2.findViewById(R.id.actual_price);
//        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);
//        price_j_cost=layout2.findViewById(R.id.price_j_cost);
//        journey_time=layout2.findViewById(R.id.journey_time);
        client_details=layout2.findViewById(R.id.client_details);
        rname=layout2.findViewById(R.id.rname);
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        end_time=layout2.findViewById(R.id.end_time);
        emp_name=layout2.findViewById(R.id.emp_name);
        old=layout2.findViewById(R.id.old);
        cname=layout2.findViewById(R.id.cname);
        if (c_old.equals("0")){
            old.setText(R.string.child);
        }else {
            old.setText(R.string.Adult);
        }
        cname.setText(context.getResources().getString(R.string.cname)+" "+user_name);

//        if (ac_price.equals("0") ||ac_price.equals("") ){
////            ac_price_lay.setVisibility(View.GONE);
//        }else {
//            actual_price.setText(ac_price);
//            ReservatoinDetailsActivity.price.setPaintFlags(ReservatoinDetailsActivity.price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//
//        }

        Log.e("isExec","is"+isExec);
        client_details.setText(priceVal+" "+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else
            isExecuted.setImageResource(R.drawable.ic_cancel);

//        layout2.findViewById(R.id.jcost_lay).setVisibility(View.GONE);
//        layout2.findViewById(R.id.jtime_lay).setVisibility(View.GONE);

        if (!place.getText().toString().equals(R.string.salon)) {
//         layout2.findViewById(R.id.jcost_lay).setVisibility(View.VISIBLE);
//         layout2.findViewById(R.id.jtime_lay).setVisibility(View.VISIBLE);
//            price_j_cost.setText(j_cost + " " + context.getResources().getString(R.string.ryal));
//            journey_time.setText(j_time + " " + context.getResources().getString(R.string.minute));
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
*/

/*
    public static void addHeaderLayout(final LinearLayout myroot, String client_name, String client_old , JSONArray bookings){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext, myroot, false);
        TextView client_details;
        LinearLayout check_hide;
        client_details=layout2.findViewById(R.id.client_details);
        check_hide=layout2.findViewById(R.id.check_hide);
        client_details.setText(client_name);
        TextView old=layout2.findViewById(R.id.old);
        TextView cname=layout2.findViewById(R.id.cname);
        if (client_old.equals("0")){
            old.setText(R.string.child);
        }else {
            old.setText(R.string.Adult);
        }
        cname.setText(context.getResources().getString(R.string.cname)+" "+client_name);

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
            check_hide.setVisibility(View.GONE);
            client_details.setText(R.string.journey_time_emp);
        }else
            client_details.setText(priceRes+APICall.convertToArabic(ptmp+"")+" "+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
*/
    public static void addHeaderLayout(final LinearLayout myroot, String client_name, String old , String price){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext, myroot, false);
        TextView client_details;
        client_details=layout2.findViewById(R.id.client_details);
        TextView c_old=layout2.findViewById(R.id.old);
        TextView cname=layout2.findViewById(R.id.cname);
        String o=context.getResources().getString(R.string.age2)+old+" "+context.getResources().getString(R.string.years);
        if(old.equals("0"))
            c_old.setText(R.string.lessThanYear);
        else if(old.equals("1"))
            c_old.setText(R.string.oneYear);
        else if(old.equals("1"))
            c_old.setText(R.string.twoYears);
        else
            c_old.setText(o);
        String name=context.getResources().getString(R.string.cname)+" "+client_name;
        cname.setText(name);

        int ptmp=0;
        ptmp=Integer.parseInt(price);
        String s=price+" "+context.getResources().getString(R.string.ryal);
        if(ptmp!=0)
            client_details.setText(s);
        else
            client_details.setVisibility(View.GONE);

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
/*
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bdb_end_time,String bookat,String empName ,String isExec,String ac_price,String jcost,String jtime){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext_v1, myroot, false);
        TextView rname,emp_name,price,starttime,book_at,end_time,actual_price,j_cost,j_time,id;
        LinearLayout book_exec_layout_in;

        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        end_time=layout2.findViewById(R.id.end_time);
        book_exec_layout_in=layout2.findViewById(R.id.book_exec_layout_in);
        actual_price=layout2.findViewById(R.id.actual_price);
//        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);
        j_cost=layout2.findViewById(R.id.price_j_cost);
        j_time=layout2.findViewById(R.id.journey_time);

//        if (ac_price.equals("0")){
//            ac_price_lay.setVisibility(View.GONE);
//        }else {
//            actual_price.setText(ac_price);
//        }
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else             isExecuted.setImageResource(R.drawable.ic_cancel);


        if (book_exec_layout.getVisibility()==View.GONE){
            book_exec_layout_in.setVisibility(View.VISIBLE);
        }else {
            book_exec_layout_in.setVisibility(View.GONE);

        }

//        j_cost=layout2.findViewById(R.id.price_j_cost);

//        j_cost.setText(jCost);
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));
//        layout2.findViewById(R.id.jcost_lay).setVisibility(View.GONE);
//        layout2.findViewById(R.id.jtime_lay).setVisibility(View.GONE);

        if (!place.getText().toString().equals(R.string.salon)) {
//            layout2.findViewById(R.id.jcost_lay).setVisibility(View.VISIBLE);
//            layout2.findViewById(R.id.jtime_lay).setVisibility(View.VISIBLE);
//            j_cost.setText(jcost + " " + context.getResources().getString(R.string.ryal));
//            j_time.setText(jtime + " " + context.getResources().getString(R.string.minute));
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
*/
/*
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
        LinearLayout book_exec_layout_in=layout2.findViewById(R.id.book_exec_layout_in);

        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else
            isExecuted.setImageResource(R.drawable.ic_cancel);


        if (book_exec_layout.getVisibility()==View.GONE){
            book_exec_layout_in.setVisibility(View.VISIBLE);
        }else {
            book_exec_layout_in.setVisibility(View.GONE);

        }

//        j_cost=layout2.findViewById(R.id.journey_time);

//        j_cost.setText(jtime);
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));


//        layout2.findViewById(R.id.jcost_lay).setVisibility(View.GONE);
//        layout2.findViewById(R.id.jtime_lay).setVisibility(View.GONE);

        if (!place.getText().toString().equals(R.string.salon)) {
//            layout2.findViewById(R.id.jcost_lay).setVisibility(View.VISIBLE);
//            layout2.findViewById(R.id.jtime_lay).setVisibility(View.VISIBLE);
//            j_cost.setText(jcost + " " + context.getResources().getString(R.string.ryal));
//            j_time.setText(jtime + " " + context.getResources().getString(R.string.minute));
        }
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
*/
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext_v1, myroot, false);
        TextView rname,price;

        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        rname.setText(reservationName);
        if(priceVal.equals("null"))
            price.setText(R.string.unDeterminedPrice);
        else
            price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
/*
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
*/

    public static void addStatusLayout(final LinearLayout myroot, String Status){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext2, myroot, false);
        TextView statusTxt=layout2.findViewById(R.id.rname);
        if (Status.equals("3")){
            statusTxt.setText(R.string.completly_executed);
        }else  if (Status.equals("9")){
            statusTxt.setText(R.string.rejectedByProvider);
        }else  if (Status.equals("5")){
            statusTxt.setText(R.string.canceledByClient);
        }else  if (Status.equals("10")){
            statusTxt.setText(R.string.canceledBySystem);
        }
        else  {
            statusTxt.setVisibility(View.GONE);
        }
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }



}
