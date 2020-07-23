package com.ptm.clinicpa.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Adapters.BookingRequestsAdapter;
import com.ptm.clinicpa.Fragments.MyBookingRequestsFragment;
import com.ptm.clinicpa.MapsActivityLocation;
import com.ptm.clinicpa.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookingRequestDetailsActivity  extends AppCompatActivity {
    //    View view;
    public static TextView costTxt,journeyCost,id,order_on,phone_number,s_name,exec_order,empname,booktype,ac_total_price,salonName,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse,date,medFileNumber,description;
    public static LinearLayout myroot,descriptionLayout,costLayout,jrCostLayout;
    TextView v1,v2,v3,v4;
    RadioButton r1,r2,r3,r4;
    static Context context;
    public static String logoId;
    ImageView logoImg;
    ImageView location;
    static int [] categoryImages ={ R.drawable.hair_basic,
            R.drawable.makeup_basic,
            R.drawable.massage_basic,
            R.drawable.spa_basic,
            R.drawable.nails_basic,
            R.drawable.body_basic,
            R.drawable.skin_basic,
            R.drawable.eyebrows_basic
    };
    public static Double bdb_loc_lat,bdb_loc_long;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_details_layout);


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
        journeyCost=findViewById(R.id.journey_price);
        costTxt=findViewById(R.id.costTxt);
        jrCostLayout=findViewById(R.id.jrcostLayout);
        logoId="";
        logoImg=findViewById(R.id.logoImg);
        date=findViewById(R.id.date);
        id=findViewById(R.id.id);
        location=findViewById(R.id.location);
        order_on=findViewById(R.id.order_on);
        exec_order=findViewById(R.id.exec_order);
        s_name=findViewById(R.id.s_name);
        phone_number=findViewById(R.id.phone_number);
        description=findViewById(R.id.description);
        medFileNumber=findViewById(R.id.medical_id);
        descriptionLayout=findViewById(R.id.descriptionLayout);
        costLayout=findViewById(R.id.costLayout);

/*
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
*/
        phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(BookingRequestDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

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
        String book_id=getIntent().getStringExtra("order_id");
        APICall.browseOneBookingRequest(book_id,context,logoImg,(CardView) findViewById(R.id.myCardView));
        id.setText(book_id);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bdb_loc_lat!=null && bdb_loc_long!=null){
                    Intent intent=new Intent(context, MapsActivityLocation.class);
                    intent.putExtra("lat",bdb_loc_lat);
                    intent.putExtra("lang",bdb_loc_long);
                    startActivity(intent);
                }
            }
        });



    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String catigoryVal,String cost,String requestedOn,String docName,String status,String bdb_is_group_booking)
    {
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.request_details_main_layout_ext_v1, myroot, false);
        TextView rname,doctorName,statusTxt;
        TextView VCost;
        TextView requestedOnView;

        ImageView categoryImg=layout2.findViewById(R.id.categoryImg);
        rname=layout2.findViewById(R.id.rname);
        doctorName=layout2.findViewById(R.id.doctorName);
        statusTxt=layout2.findViewById(R.id.status);
        requestedOnView=layout2.findViewById(R.id.book_at);
        VCost=layout2.findViewById(R.id.cost);
        String c =cost+context.getResources().getString(R.string.ryal);
        if(cost.equals("null"))
            VCost.setText(R.string.unDeterminedPrice);
        else
            VCost.setText(c);
        requestedOnView.setText(requestedOn);
        rname.setText(reservationName);
//        int index =Integer.parseInt(catigoryVal);
   //     categoryImg.setImageResource(categoryImages[index]);
//        rname.setText(reservationName);
//
        if(bdb_is_group_booking.equals("20")||bdb_is_group_booking.equals("21")||bdb_is_group_booking.equals("23")||bdb_is_group_booking.equals("24"))
        {
            doctorName.setVisibility(View.GONE);
              statusTxt.setVisibility(View.GONE);
        }
        else
        {
            doctorName.setText(docName);
            if (status.equals("1")){
                statusTxt.setText(R.string.approved);
            }else  if (status.equals("2")){
                statusTxt.setText(R.string.rejectedByProvider);
            }else  if (status.equals("3")){
                statusTxt.setText(R.string.canceledByClient);
            }else  if (status.equals("4")){
                statusTxt.setText(R.string.canceledBySystem);
            }
            else  if (status.equals("0")){
                statusTxt.setVisibility(View.GONE);
            }


        }

            ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }

    public static void addHeaderLayout(final LinearLayout myroot, String old, String cname, JSONArray costtxt, String client_name, String client_old ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.request_details_header_layout, myroot, false);
        TextView client_details,c_name,c_old;
        client_details=layout2.findViewById(R.id.client_details);
        c_name=layout2.findViewById(R.id.c_name);
        c_old=layout2.findViewById(R.id.c_old);
        c_name.setText(context.getResources().getString(R.string.cname)+" "+cname);
       /* if (old.equals("1")) {
            c_old.setText(R.string.Adult);
        }else {
            c_old.setText(R.string.child);
        }*/
       if(old.equals("0"))
           c_old.setText(R.string.lessThanYear);
       else if(old.equals("1"))
           c_old.setText(R.string.oneYear);
       else if(old.equals("1"))
           c_old.setText(R.string.twoYears);
       else
           c_old.setText(client_old);
        int costt=0;
        for (int j=0;j<costtxt.length();j++){

//
//                                    if (!name.equals("booking_wast_time")) {

            try {
                JSONObject object1=costtxt.getJSONObject(j);
                Log.e("objectBookings",object1.toString());
                final String
                        cost = object1.getString("cost");
                costt+=Integer.parseInt(cost);

//                                    ReservatoinDetailsActivity.time.setText(convertToArabic(bdb_start_date));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String s=costt+" "+context.getResources().getString(R.string.ryal);
        if(costt==0)
        {
            client_details.setVisibility(View.GONE);
        }
        else
            client_details.setText(s);

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }



}

