package com.dcoret.beautyclient.Fragments.IndividualBooking;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterForMultiInd;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabOne;
import com.dcoret.beautyclient.R;

public class BookingIndvidualActivity extends AppCompatActivity {
    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    String url="",urlOut="";
    public static String bdb_is_groupbooking="";

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);


        if (TabOne.bdb_is_bride.equals("0")){
            bdb_is_groupbooking="0";
        }else {
            bdb_is_groupbooking="10";

        }
        context=this;
        BeautyMainPage.FRAGMENT_NAME="BookingIndvidualActivity";

//        if (MultiIndividualBookingReservationFragment.choose_occision.getText().toString().equals("different dates for services")){
//        }





        Log.e("dateURLS",url);
        Log.e("FNAME", BeautyMainPage.FRAGMENT_NAME);

        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        String place_num="";
        String price_num="";
        switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()){
            case 1:
                place_num="9";
                price_num="32";
                url="http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
                urlOut="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeInside";
                break;
            case 2:
                place_num="8";
                price_num="1";
                url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
                urlOut="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeOutside";
                break;
            case 3:
                place_num="10";
                price_num="30";
                url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
                urlOut="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeOutside";
                break;
            case 4:
                place_num="11";
                price_num="31";
                url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
                urlOut="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeOutside";
                break;

        }


//        SharedPreferences preferences=BeautyMainPage.context.getSharedPreferences("LOGIN",)


        final String filter="{\n" +
                "    \"Filter\": [\n" +
                "        {\n" +
                "            \"num\": 34,\n" +
                "            \"value1\": "+PlaceServiceFragment.lat+",\n" +
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
//                "    \"bdb_pack_code\": 141,\n" +
                "    \"multi_salon_client\": 1,\n" +
                "    \"multi_salon_clients_rel\": 1,\n" +
                "            \"date\": \""+PlaceServiceFragment.date.getText().toString()+"\",\n" +
                "    \"sup_id\" : "+TabOne.bdb_sup_id +",\n" +
                "    \"clients\": [\n" +
                "        {\n" +
                "            \"client_name\": \""+BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"rel\": \"0\",\n" +
                "            \"date\": \""+PlaceServiceFragment.date.getText().toString()+"\",\n" +
                "            \"is_adult\": true,\n" +
                "            \"services\": [\n" +
                "                {\n" +
                "                    \"ser_id\": "+TabOne.ser_id+",\n" +
                "                    \"bdb_ser_sup_id\" : "+TabOne.ser_sup_id+",\n" +
                "                    \"ser_time\" : "+TabOne.bdb_time+"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"effect\": [\n" +
//                "                {\n" +
//                "                    \"effect_id\": 3,\n" +
//                "                    \"effect_value\": 1\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"effect_id\": 4,\n" +
//                "                    \"effect_value\": 1\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"effect_id\": 1,\n" +
//                "                    \"effect_value\": 2\n" +
//                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        APICall.searchGroupBooking(filter,url,urlOut,context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBooking(filter,url,urlOut,context);

//                APICall.searchGroupBookingMulti(url,BeautyMainPage.context);

            }
        });


    }
}
