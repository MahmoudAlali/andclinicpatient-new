package com.dcoret.beautyclient.Fragments.GroupBooking;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterSearchGroupBooking2;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.R;

public class GroupReservationResultActivity extends AppCompatActivity {

    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);

        String url="";Boolean isIn = null;
        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";

        if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
            isIn=true;
        }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()>1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
            isIn=false;
        }



        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        Log.e("URL",url);
        APICall.searchGroupBooking(url,isIn,BeautyMainPage.context);


        final Boolean finalIsIn = isIn;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBooking2(BeautyMainPage.context, finalIsIn);
            }
        });
    }
}
