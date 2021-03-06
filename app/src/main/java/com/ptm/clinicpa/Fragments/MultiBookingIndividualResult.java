package com.ptm.clinicpa.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.CustomExpandableListAdapterForMultiInd;
import com.ptm.clinicpa.Adapters.GroupReservationsAdapter;
import com.ptm.clinicpa.R;

public class MultiBookingIndividualResult extends Fragment {

    public static ExpandableListView listView;
    public static CustomExpandableListAdapterForMultiInd listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    String url="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);

        if (MultiIndividualBookingReservationFragment.choose_occision.getSelectedItemPosition()==2){
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url="http://clientapp.dcoret.com/api/booking/searchGroupBooking3_13Inside";
            }else {
                url="http://clientapp.dcoret.com/api/booking/searchGroupBooking3_13Outside";

            }
        }else {
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url = "http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
            }else {
                url = "http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";

            }
        }





            Log.e("dateURLS",url);
            Log.e("FNAME",BeautyMainPage.FRAGMENT_NAME);

        listView=view.findViewById(R.id.list_view);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
//        APICall.searchGroupBookingMulti(url,BeautyMainPage.context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                APICall.searchGroupBookingMulti(url,BeautyMainPage.context);
            }
        });

        return view;
    }
}
