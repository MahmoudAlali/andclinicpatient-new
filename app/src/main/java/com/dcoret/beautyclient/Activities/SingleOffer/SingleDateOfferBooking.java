package com.dcoret.beautyclient.Activities.SingleOffer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.OfferBookingResult;
import com.dcoret.beautyclient.Adapters.ShowServicesAdapter;
import com.dcoret.beautyclient.DataModel.OfferClientsModel;
import com.dcoret.beautyclient.Fragments.OffersForRequest;
import com.dcoret.beautyclient.Fragments.PlaceServiceFragment;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Fragments.freeBookingFragment;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleDateOfferBooking extends AppCompatActivity {
    EditText client_name,phone_number;
    public static ArrayList<OfferClientsModel> offerClientsModels=new ArrayList<>();

    //    DatePicker datePicker;
    Button next;
    static TextView showDate;
    CoordinatorLayout selectdate;
    public static ShowServicesAdapter offerAdapter;
    Context context;
    RecyclerView recyclerView;
   static String place_num="",price_num="";
    public static String end_date;
    String bdb_pack_id;
    String is_effects_on;

    public static ArrayList<String> services=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date_offer_booking);

        context=this;
        final int postion=getIntent().getIntExtra("postion",0);
        final String otype=getIntent().getStringExtra("offertype");


        client_name=findViewById(R.id.client_name);
        phone_number=findViewById(R.id.phone_number);
        selectdate=findViewById(R.id.select_date);
        showDate=findViewById(R.id.date);
        next=findViewById(R.id.next);

        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            end_date = OffersForRequest.arrayList.get(postion).getBdb_offer_end();
            bdb_pack_id = OffersForRequest.arrayList.get(postion).getBdb_pack_code();
            is_effects_on = OffersForRequest.arrayList.get(postion).getBdb_is_effects_on();
        }
        else {
            end_date = TabTwo.arrayList.get(postion).getBdb_offer_end();
            bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();
            is_effects_on = TabTwo.arrayList.get(postion).getBdb_is_effects_on();
        }
/*
        //region CHECK_NOTIFICATION
        String notification = "";
        try {
            notification=getIntent().getStringExtra("notification");

        }
        catch (Exception e){
            notification="";
        }
        try {
            if (!notification.equals("")) {
                bdb_pack_id = getIntent().getStringExtra("bdb_pack_id");
                is_effects_on = getIntent().getStringExtra("is_effects_on");
                end_date = getIntent().getStringExtra("offer_end");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //endregion
*/

        recyclerView=findViewById(R.id.recycleview);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.select_date);
                TextView ok=dialog.findViewById(R.id.confirm);
                TextView cancel=dialog.findViewById(R.id.cancel);
                final DatePicker datePicker=dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(System.currentTimeMillis());

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date=sdf.parse(end_date);

                    datePicker.setMaxDate(date.getTime());

                }catch (Exception e){
                    e.printStackTrace();
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        int month=datePicker.getMonth()+1;
                        showDate.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
//                        ((Item)holder).select_time.setText(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
                    }
                });

                dialog.show();
            }
        });

        offerAdapter=new ShowServicesAdapter(context,offerClientsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(offerAdapter);

        APICall.browseOneOfferv2(bdb_pack_id,offerClientsModels,offerAdapter,context);

        if(!BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()){
                case 1:
                    place_num="9";
                    price_num="32";
                    break;
                case 2:
                    place_num="8";
                    price_num="1";
                    break;
                case 3:
                    place_num="10";
                    price_num="30";
                    break;
                case 4:
                    place_num="11";
                    price_num="31";
                    break;

            }

        }
        else
        {
            switch (freeBookingFragment.placeSpinner.getSelectedItemPosition()){
                case 1:
                    place_num="9";
                    price_num="32";
                    break;
                case 2:
                    place_num="8";
                    price_num="1";
                    break;
                case 3:
                    place_num="10";
                    price_num="30";
                    break;
                case 4:
                    place_num="11";
                    price_num="31";
                    break;

            }

        }




        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bdb_pack_code=bdb_pack_id;
                    String date=showDate.getText().toString();
                    String cname= BeautyMainPage.client_name;
                    String cphone=BeautyMainPage.client_number;
                    String offerType=otype;
                    String offerplace="";
//                    ArrayList<String> arrayList =new ArrayList<>();
                    String services="";
                    String bdb_ser_sup_id="",bdb_time="";
                    for (int i=0;i<offerClientsModels.get(0).getServiceDetails().size();i++){
                        bdb_ser_sup_id=offerClientsModels.get(0).getServiceDetails().get(i).getBdb_ser_sup_id();
                        bdb_time=offerClientsModels.get(0).getServiceDetails().get(i).getBdb_time();
                        if (i==0){
                            services="\t\t{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": "+bdb_time+" }\n";
                        }else {
                            services=services+"\t\t,{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": "+bdb_time+" }\n";
                        }
                    }

                    String postdata=
                            "{\n" +
                                    "    \"Filter\": [\n" +
                                    "        {\n" +
                                    "            \"num\": 34,\n" +
                                    "            \"value1\": "+ PlaceServiceFragment.lat+",\n" +
                                    "            \"value2\": 0\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"num\": 35,\n" +
                                    "            \"value1\": "+PlaceServiceFragment.lng+",\n" +
                                    "            \"value2\": 0\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"num\": "+price_num+",\n" +
                                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"num\": "+place_num+",\n" +
                                    "            \"value1\": 1,\n" +
                                    "            \"value2\": 0\n" +
                                    "        }\n" +
                                    "    ],\n" +

                                    "\"bdb_pack_code\":"+bdb_pack_code+",\"date\":\""+date+"\",\"clients\": [        \n" +
                                    "\t{\"client_name\": \""+cname+"\",\"client_phone\": \""+cphone+"\",\"is_current_user\": 0,\n" +
                                    "\"is_adult\":1\n" +",\"date\":\""+date+"\",\n"+
                                    "\t\"services\": [\n" +
                                    services+
                                    "\t\t],\"effect\":[]\n" +
                                    "\t}\n" +
                                    "\t],\"offer_type\":"+offerType+"\n" +
                                    "}";
                    Log.e("postdata",postdata);
                    offerplace=offerClientsModels.get(0).getBdb_offer_place();
                    if (is_effects_on.equals("1")) {

                        Intent intent = new Intent(context, SingleOfferEffect.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offerType);
                        intent.putExtra("position", postion);
                        intent.putExtra("bdb_pack_id",bdb_pack_id);
                        intent.putExtra("notification","true");
                        intent.putExtra("place", offerplace);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, OfferBookingResult.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offerType);
                        intent.putExtra("place", offerplace);
                        startActivity(intent);
                    }
                }



            });

        }
        else
        {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bdb_pack_code=bdb_pack_id;
                    String date=showDate.getText().toString();
                    String cname= BeautyMainPage.client_name;
                    String cphone=BeautyMainPage.client_number;
                    String offerType=otype;
                    String offerplace="";
//                    ArrayList<String> arrayList =new ArrayList<>();
                    String services="";
                    String bdb_ser_sup_id="",bdb_time="";
                    for (int i=0;i<offerClientsModels.get(0).getServiceDetails().size();i++){
                        bdb_ser_sup_id=offerClientsModels.get(0).getServiceDetails().get(i).getBdb_ser_sup_id();
                        bdb_time=offerClientsModels.get(0).getServiceDetails().get(i).getBdb_time();
                        if (i==0){
                            services="\t\t{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": "+bdb_time+" }\n";
                        }else {
                            services=services+"\t\t,{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": "+bdb_time+" }\n";
                        }
                    }

                    String postdata=
                            "{\n" +
                                    "    \"Filter\": [\n" +
                                    "        {\n" +
                                    "            \"num\": 34,\n" +
                                    "            \"value1\": "+ PlaceServiceFragment.lat+",\n" +
                                    "            \"value2\": 0\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"num\": 35,\n" +
                                    "            \"value1\": "+PlaceServiceFragment.lng+",\n" +
                                    "            \"value2\": 0\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"num\": "+price_num+",\n" +
                                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"num\": "+place_num+",\n" +
                                    "            \"value1\": 1,\n" +
                                    "            \"value2\": 0\n" +
                                    "        }\n" +
                                    "    ],\n" +

                                    "\"bdb_pack_code\":"+bdb_pack_code+",\"date\":\""+date+"\",\"clients\": [        \n" +
                                    "\t{\"client_name\": \""+cname+"\",\"client_phone\": \""+cphone+"\",\"is_current_user\": 0,\n" +
                                    "\"is_adult\":1\n" +",\"date\":\""+date+"\",\n"+
                                    "\t\"services\": [\n" +
                                    services+
                                    "\t\t],\"effect\":[]\n" +
                                    "\t}\n" +
                                    "\t],\"offer_type\":"+offerType+"\n" +
                                    "}";
                    Log.e("postdata",postdata);
                    offerplace=offerClientsModels.get(0).getBdb_offer_place();
                    if (is_effects_on.equals("1")) {

                        Intent intent = new Intent(context, SingleOfferEffect.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offerType);
                        intent.putExtra("position", postion);
                        intent.putExtra("bdb_pack_id",bdb_pack_id);
                        intent.putExtra("notification","true");
                        intent.putExtra("place", offerplace);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, OfferBookingResult.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offerType);
                        intent.putExtra("place", offerplace);
                        startActivity(intent);
                    }
                }



            });

        }







    }
}

