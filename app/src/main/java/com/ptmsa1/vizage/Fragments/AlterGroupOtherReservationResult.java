package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.AltCustomExpandableListAdapter;
import com.ptmsa1.vizage.Adapters.CustomExpandableListAdapterSearchGroupBooking2;
import com.ptmsa1.vizage.Adapters.GroupReservationsAdapter;
import com.ptmsa1.vizage.R;

public class AlterGroupOtherReservationResult extends AppCompatActivity {

    public static ExpandableListView listView;
    public static AltCustomExpandableListAdapter listAdapter;
    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    String url="",urlOut;Boolean isIn = null;

    Context context;
    public static String filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);

        context=this;
        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";

        if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1){
            url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingInside";
            urlOut=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";
            isIn=true;
        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()>1){
            url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingOutside";
            urlOut=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";
            isIn=false;
        }

        filter="{\"Filter\":\t[\n" +
                "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":32,\"value1\":0,\"value2\":1000},{\"num\":9,\"value1\":1}\t],\n" +
                "    \"date\":\"2020-3-24\",\"multi_salon_client\": 1,  \"multi_salon_clients_rel\": 1,\t\t\"clients\":[\t{\"client_name\":\"c1\",\"client_phone\":\"0500200600\",\"is_current_user\":0,\"date\": \"2020-3-24\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n" +
                "    {\"ser_id\":3,\"ser_time\":60}\n" +
                "    ],\"effect\":[{\"effect_id\": 1,\n" +
                "    \"effect_value\": -1\n" +
                "     }\n" +
                "    ,{\"effect_id\": 5,\n" +
                "    \"effect_value\": -1\n" +
                "     }\n" +
                "    ,{\"effect_id\": 2,\n" +
                "    \"effect_value\": -1\n" +
                "     }\n" +
                "    ,{\"effect_id\": 3,\n" +
                "    \"effect_value\": -1\n" +
                "     }\n" +
                "    ]},\t{\"client_name\":\"c2\",\"client_phone\":\"0500369369\",\"is_current_user\":0,\"rel\":\"0\",\"is_adult\":1,\"date\": \"2020-3-24\",\"services\":[\n" +
                "    {\"ser_id\":9,\"ser_time\":60}\n" +
                "    ],\"effect\":[]}]}";

        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        Log.e("URL",url);
        APICall.searchGroupBookingAltOther(urlOut,isIn,context);




        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingAltOther(urlOut,isIn,context);


//                APICall.searchGroupBooking2(BeautyMainPage.context, finalIsIn);
            }
        });
    }
}
