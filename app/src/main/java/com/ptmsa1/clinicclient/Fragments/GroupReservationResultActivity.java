package com.ptmsa1.clinicclient.Fragments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Adapters.CustomExpandableListAdapter;
import com.ptmsa1.clinicclient.Adapters.CustomExpandableListAdapterSearchGroupBooking2;
import com.ptmsa1.clinicclient.Adapters.GroupReservationsAdapter;
import com.ptmsa1.clinicclient.R;

public class GroupReservationResultActivity extends AppCompatActivity {

    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    String url="",urlOut;Boolean isIn = null;

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);

        context=this;
        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";

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
        APICall.searchGroupBooking(url,urlOut,isIn,context);


        final Boolean finalIsIn = isIn;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBooking(url,urlOut,isIn,context);

//                APICall.searchGroupBooking2(BeautyMainPage.context, finalIsIn);
            }
        });
    }
}
