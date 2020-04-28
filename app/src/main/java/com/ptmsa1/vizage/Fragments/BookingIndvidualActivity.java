package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.API.Constants;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.ProviderSerAndOfferPKG.MainProviderActivity;
import com.ptmsa1.vizage.Adapters.CustomExpandableListAdapter;
import com.ptmsa1.vizage.Adapters.GroupReservationsAdapter;
import com.ptmsa1.vizage.Activities.TabOne;
import com.ptmsa1.vizage.Adapters.ServicesProviderAdapter;
import com.ptmsa1.vizage.R;

public class BookingIndvidualActivity extends AppCompatActivity {
    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    String url="",urlOut="";
    public static String bdb_is_groupbooking="";

    public static Context context;
    public static int multi_salon_client=0;
    public static String filterAlt="";
    public static CoordinatorLayout noSolutionMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);
        multi_salon_client=0;
        filterAlt="";
        if (TabOne.bdb_is_bride.equals("0")){
            bdb_is_groupbooking="0";
        }else {
            bdb_is_groupbooking="10";

        }
        context=this;
        BeautyMainPage.FRAGMENT_NAME="BookingIndvidualActivity";
        noSolutionMsg=findViewById(R.id.noSolMsg);
        TextView msgOfKnownProviders,msgOfClientsNames;
        msgOfClientsNames=findViewById(R.id.messageOfClientsNames);
        msgOfKnownProviders=findViewById(R.id.messageOfKnownProviders);
        if(context.getResources().getString(R.string.locale).equals("ar"))
        {
            msgOfClientsNames.setText(Constants.messageOfClientsNames_ar);
            msgOfKnownProviders.setText(Constants.messageOfKnownProviders_ar);
        }
        else
        {
            msgOfClientsNames.setText(Constants.messageOfClientsNames_en);
            msgOfKnownProviders.setText(Constants.messageOfKnownProviders_en);
        }
//        if (MultiIndividualBookingReservationFragment.choose_occision.getText().toString().equals("different dates for services")){
//        }





        Log.e("dateURLS",url);
        Log.e("FNAME", BeautyMainPage.FRAGMENT_NAME);

        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        String place_num="";
        String price_num="";
        try {
            switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()) {
                case 1:
                    place_num = "9";
                    price_num = "32";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingInside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeInside";
                    break;
                case 2:
                    place_num = "8";
                    price_num = "1";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingOutside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeOutside";
                    break;
                case 3:
                    place_num = "10";
                    price_num = "30";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingOutside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeOutside";
                    break;
                case 4:
                    place_num = "11";
                    price_num = "31";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingOutside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeOutside";
                    break;

            }
        }catch (Exception e){
            switch (ServicesProviderAdapter.placePos) {
                case 1:
                    place_num = "9";
                    price_num = "32";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingInside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeInside";
                    break;
                case 2:
                    place_num = "8";
                    price_num = "1";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingOutside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeOutside";
                    break;
                case 3:
                    place_num = "10";
                    price_num = "30";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingOutside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeOutside";
                    break;
                case 4:
                    place_num = "11";
                    price_num = "31";
                    url = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingOutside";
                    urlOut = APICall.API_PREFIX_NAME + "/api/booking/searchGroupBookingAlternativeOutside";
                    break;

            }


        }

//        SharedPreferences preferences=BeautyMainPage.context.getSharedPreferences("LOGIN",)

        String date="";
        try{
            date=PlaceServiceFragment.date.getText().toString();
        }catch (Exception e){
            date= MainProviderActivity.date.getText().toString();
        }

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
                "    \"multi_salon_client\": "+0+",\n" +
                "    \"multi_salon_clients_rel\": 0,\n" +
                "            \"date\": \""+date+"\",\n" +
                "    \"sup_id\" : "+TabOne.bdb_sup_id +",\n" +
                "    \"clients\": [\n" +
                "        {\n" +
                "            \"client_name\": \""+BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"rel\": \"0\",\n" +
                "            \"date\": \""+date+"\",\n" +
                "            \"is_adult\": true,\n" +
                "            \"services\": [\n" +
                "                {\n" +
                "                    \"ser_id\": "+TabOne.ser_id+",\n" +
                "                    \"bdb_ser_sup_id\" : "+TabOne.ser_sup_id+",\n" +
                "                    \"ser_time\" : "+TabOne.bdb_time+"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"effect\": [\n" +
                MyIndEffectsActivity.effectFilter+
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";





         filterAlt="{\n" +
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
                "    \"multi_salon_client\": "+1+",\n" +
                "    \"multi_salon_clients_rel\": 1,\n" +
                "            \"date\": \""+date+"\",\n" +
                "    \"sup_id\" : "+TabOne.bdb_sup_id +",\n" +
                "    \"clients\": [\n" +
                "        {\n" +
                "            \"client_name\": \""+BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"rel\": \"0\",\n" +
                "            \"date\": \""+date+"\",\n" +
                "            \"is_adult\": true,\n" +
                "            \"services\": [\n" +
                "                {\n" +
                "                    \"ser_id\": "+TabOne.ser_id+",\n" +
                "                    \"bdb_ser_sup_id\" : "+TabOne.ser_sup_id+",\n" +
                "                    \"ser_time\" : "+TabOne.bdb_time+"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"effect\": [\n" +
                MyIndEffectsActivity.effectFilter+
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
