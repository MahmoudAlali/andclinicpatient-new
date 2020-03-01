package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterSearchGroupBooking2;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.R;

public class GroupReservationResultFragment extends Fragment {


        public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;



    String items[]={"1","2","3","4","5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);


        String url="";Boolean isIn = null;
        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";

        if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
            isIn=true;
        }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()>1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
            isIn=false;
        }



        listView=view.findViewById(R.id.list_view);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        Log.e("URL",url);
//        APICall.searchGroupBooking(url,,isIn,BeautyMainPage.context);


        final Boolean finalIsIn = isIn;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBooking2(BeautyMainPage.context, finalIsIn);
            }
        });

        return view;
    }
}
