package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.CustomExpandableListAdapter;
import com.ptmsa1.vizage.Adapters.GroupReservationsAdapter;
import com.ptmsa1.vizage.R;

public class GroupReservationOtherResultActivity extends AppCompatActivity {

    public static ExpandableListView listView;
    public static SwipeRefreshLayout pullToRefresh;
    public static CustomExpandableListAdapter listAdapter;
    public static Context context;
    public static GroupReservationsAdapter adapter;
    String url="",urlAlt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);
        BeautyMainPage.FRAGMENT_NAME="GroupReservationOtherResultFragment";
         context=this;

        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);


        if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1){
            url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingInside";
            urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";


        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()>1){
            url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingOutside";
            urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";
        }


        Log.e("GUESTIS","isss"+context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("isGuest","-1"));

        APICall.searchGroupBookingOther(context,url,urlAlt);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingOther(context,url,urlAlt);
            }
        });
    }
}
