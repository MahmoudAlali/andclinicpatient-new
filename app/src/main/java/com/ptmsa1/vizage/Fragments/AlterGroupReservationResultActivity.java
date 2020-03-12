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

public class AlterGroupReservationResultActivity extends AppCompatActivity {
    public static ExpandableListView listView;
    public static AltCustomExpandableListAdapter listAdapter;
    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    String url="",urlOut;Boolean isIn = null;

    public static  String filter="";
    public static   Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);

        context=this;
        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";

         filter="{\"Filter\":\t[\n" +
                "    \t{\"num\":34,\"value1\":21.444551364120773,\"value2\":0},\n" +
                "    \t{\"num\":35,\"value1\":39.88135412335396,\"value2\":0},{\"num\":32,\"value1\":0,\"value2\":1000},{\"num\":9,\"value1\":1,\"value2\":0}\t],\n" +
                "    \"date\":\"2020-2-27\",\"multi_salon_client\": 0,  \"multi_salon_clients_rel\": 0,\t\t\"clients\":[\t{\"client_name\":\"user264\",\"client_phone\":\"0500500501\",\"is_current_user\":1,\"date\": \"2020-2-27\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n" +
                "    {\"ser_id\":3,\"ser_time\":60}\n" +
                "    ],\"effect\":[]\n" +
                "    \t\n" +
                "    },\n" +
                "    {\"client_name\":\"c1\",\"client_phone\":\"0500400800\",\"is_current_user\":0,\"rel\":\"0\",\"is_adult\":1,\"date\": \"2020-2-27\",\"services\":[\n" +
                "    {\"ser_id\":1,\"ser_time\":60}\n" +
                "   \n" +
                "    ],\"effect\":[]\n" +
                "    \t\n" +
                "    }]}";

        if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==1){
            url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingInside";
            urlOut=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";
            isIn=true;
        }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()>1){
            url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingOutside";
            urlOut=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";
            isIn=false;
        }



        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        Log.e("URL",url);
        APICall.searchGroupBookingAlt(urlOut,isIn,context);




        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                APICall.searchGroupBookingAlt(urlOut,isIn,context);


//                APICall.searchGroupBooking2(BeautyMainPage.context, finalIsIn);
            }
        });
    }
}
