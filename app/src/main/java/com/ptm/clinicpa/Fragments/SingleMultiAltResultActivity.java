package com.ptm.clinicpa.Fragments;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Constants;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.AltCustomExpandableListAdapter;
import com.ptm.clinicpa.Adapters.GroupReservationsAdapter;
import com.ptm.clinicpa.R;

public class SingleMultiAltResultActivity extends AppCompatActivity {

    public static ExpandableListView listView;
    public static AltCustomExpandableListAdapter listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    String url="",urlAlt="";

    public static CoordinatorLayout noSolutionMsg;
    public  static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);
        context=this;

        final String filter=getIntent().getStringExtra("filter1");

        if (MultiIndividualBookingReservationFragment.choose_occision.getSelectedItemPosition()==2){
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBooking3_13Inside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";
            }else {
                url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBooking3_13Outside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";

            }
        }else {
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url = APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingInside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";
            }else {
                url = APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingOutside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";

            }
        }





        Log.e("dateURLS",url);
        Log.e("filter",filter);
        Log.e("FNAME", BeautyMainPage.FRAGMENT_NAME);

        listView=findViewById(R.id.list_view);
        noSolutionMsg=findViewById(R.id.noSolMsg);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        APICall.searchGroupBookingMultiAlt(urlAlt,filter,context);
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
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                String filter="\"multi_salon_client\": \"0\",  \n" +
//                        "\t\"multi_salon_clients_rel\": \"0\",\n" +
//                        "         \"clients\": [\n" +
//                        "        \t\t{\"client_name\": \"user264\",\"client_phone\": \"0500500501\",\"is_current_user\": 1,\"is_adult\":1,\"rel\":\"0\",\"date\": \"2020-2-25\",\"services\": [ {\"ser_id\": 3,\"ser_time\": 60 },{\"ser_id\": 1,\"ser_time\": 60 }],\"effect\":[]}\n" +
//                        "        ]}";
                APICall.searchGroupBookingMultiAlt(urlAlt,filter,context);
            }
        });

    }
}
