package com.dcoret.beautyclient.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.R;


public class OfferBookingResult extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    //    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
//    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;

    public static Context context;
    public static String offertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_offer_group_result_);
        BeautyMainPage.FRAGMENT_NAME="OfferBookingResult";

        context=this;
//        ProviderMainPage.FRAGMENT_NAME="GroupReservationResultFragment";
        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        final String filter=getIntent().getStringExtra("filter");
         offertype=getIntent().getStringExtra("offertype");

         APICall.offertypeAll=offertype;

        if (offertype.equals("2") ||offertype.equals("5"))
        {
            APICall.searchGroupOfferBooking25(context,filter);
        }else {
            APICall.searchGroupOfferBooking(context, filter);
        }
//        APICallCall.searchGroupBooking2(BeautyMainPage.context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (offertype.equals("2") ||offertype.equals("5"))
                {
                    APICall.searchGroupOfferBooking25(context,filter);
                }else {
                    APICall.searchGroupOfferBooking(context, filter);
                }
            }
        });



    }
}


