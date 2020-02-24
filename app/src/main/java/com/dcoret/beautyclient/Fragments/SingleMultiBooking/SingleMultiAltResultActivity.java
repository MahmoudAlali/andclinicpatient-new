package com.dcoret.beautyclient.Fragments.SingleMultiBooking;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterForMultiInd;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.R;

public class SingleMultiAltResultActivity extends AppCompatActivity {

    public static ExpandableListView listView;
    public static CustomExpandableListAdapterForMultiInd listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    String url="",urlAlt="";

    public  static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);
        context=this;

        final String filter=getIntent().getStringExtra("filter1");

        if (MultiIndividualBookingReservationFragment.choose_occision.getSelectedItemPosition()==2){
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url="http://clientapp.dcoret.com/api/booking/searchGroupBooking3_13Inside";
                urlAlt="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeInside";
            }else {
                url="http://clientapp.dcoret.com/api/booking/searchGroupBooking3_13Outside";
                urlAlt="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeOutside";

            }
        }else {
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url = "http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
                urlAlt="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeInside";
            }else {
                url = "http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
                urlAlt="http://clientapp.dcoret.com/api/booking/searchGroupBookingAlternativeOutside";

            }
        }





        Log.e("dateURLS",url);
        Log.e("filter",filter);
        Log.e("FNAME", BeautyMainPage.FRAGMENT_NAME);

        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        APICall.searchGroupBookingMultiAlt(urlAlt,filter,context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingMultiAlt(urlAlt,filter,context);
            }
        });

    }
}
