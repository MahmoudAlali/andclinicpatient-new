package com.dcoret.beautyclient.Fragments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.R;

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
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
            urlAlt="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeInside";


        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()>1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
            urlAlt="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeOutside";
        }



        APICall.searchGroupBookingOther(context,url,urlAlt);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingOther(context,url,urlAlt);
            }
        });
    }
}
