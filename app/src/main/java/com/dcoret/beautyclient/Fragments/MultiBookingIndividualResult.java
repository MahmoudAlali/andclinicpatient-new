package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterForMultiInd;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.DataClass.ReservationClients;
import com.dcoret.beautyclient.DataClass.ReservationClientsEmployee;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class MultiBookingIndividualResult extends Fragment {
//    LinearLayout services_tabs;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

        public static ExpandableListView listView;
    public static CustomExpandableListAdapterForMultiInd listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;



    RecyclerView recyclerView;
    String url="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);

        if (MultiIndividualBookingReservationFragment.choose_occision.getText().toString().equals("different dates for services")){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBooking3_13";
        }else {
            url="http://clientapp.dcoret.com/api/booking/searchGroupBooking";
        }


//        BeautyMainPage.FRAGMENT_NAME="MultiBookingIndiv
//

// idualResult";



            Log.e("dateURLS",url);
            Log.e("FNAME",BeautyMainPage.FRAGMENT_NAME);

        listView=view.findViewById(R.id.list_view);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        APICall.searchGroupBookingMulti(url,BeautyMainPage.context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingMulti(url,BeautyMainPage.context);

            }
        });

        return view;
    }
}
