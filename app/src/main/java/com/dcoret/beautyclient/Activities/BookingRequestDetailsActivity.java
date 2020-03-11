package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.Fragments.ReservatoinDetailsActivity;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookingRequestDetailsActivity  extends AppCompatActivity {
    //    View view;
    public static TextView id,empname,booktype,ac_total_price,salonName,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse,date;
    public static LinearLayout myroot;
    TextView v1,v2,v3,v4;
    RadioButton r1,r2,r3,r4;
    static Context context;
    public static String logoId;
    ImageView logoImg;
    static int [] categoryImages ={ R.drawable.hair_basic,
            R.drawable.makeup_basic,
            R.drawable.massage_basic,
            R.drawable.spa_basic,
            R.drawable.nails_basic,
            R.drawable.body_basic,
            R.drawable.skin_basic,
            R.drawable.eyebrows_basic
    };
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
        logoId="";
        logoImg=findViewById(R.id.logoImg);
        date=findViewById(R.id.date);
        id=findViewById(R.id.id);

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


        String book_id=getIntent().getStringExtra("order_id");
        APICall.browseOneBookingRequest(book_id,context,logoImg);
        id.setText(book_id);

        Button ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String catigoryVal,String cost,String requestedOn)
    {
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.request_details_main_layout_ext_v1, myroot, false);
        TextView rname;
        TextView VCost;
        TextView requestedOnView;

        ImageView categoryImg=layout2.findViewById(R.id.categoryImg);
        rname=layout2.findViewById(R.id.rname);
        requestedOnView=layout2.findViewById(R.id.book_at);
        VCost=layout2.findViewById(R.id.cost);
        VCost.setText(cost);
        requestedOnView.setText(requestedOn);
        rname.setText(reservationName);
        int index =Integer.parseInt(catigoryVal);
        categoryImg.setImageResource(categoryImages[index]);
//        rname.setText(reservationName);
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
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.request_details_header_layout, myroot, false);
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



}

