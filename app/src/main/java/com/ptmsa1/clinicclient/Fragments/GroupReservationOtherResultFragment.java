package com.ptmsa1.clinicclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Adapters.CustomExpandableListAdapter;
import com.ptmsa1.clinicclient.Adapters.GroupReservationsAdapter;
import com.ptmsa1.clinicclient.R;

public class GroupReservationOtherResultFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    public static ExpandableListView listView;
    public static SwipeRefreshLayout pullToRefresh;
    public static CustomExpandableListAdapter listAdapter;

    public static GroupReservationsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);

        BeautyMainPage.FRAGMENT_NAME="GroupReservationOtherResultFragment";

        listView=view.findViewById(R.id.list_view);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
//        APICall.searchGroupBookingOther(BeautyMainPage.context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                APICall.searchGroupBookingOther(BeautyMainPage.context);
            }
        });

        return view;
    }
}
