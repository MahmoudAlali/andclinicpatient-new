package com.dcoret.beautyclient.Fragments.OtherGroupBooking;

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
    Context context;
    public static GroupReservationsAdapter adapter;
    String url="";
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

        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()>1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
        }



        APICall.searchGroupBookingOther(context,url);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingOther(context,url);
            }
        });
    }
}
