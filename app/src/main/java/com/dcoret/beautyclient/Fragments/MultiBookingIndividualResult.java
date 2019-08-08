package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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



    RecyclerView recyclerView;
    ArrayList<ReservationClientsEmployee> reservationClientsEmployees1=new ArrayList<>();
    ArrayList<ReservationClientsEmployee> reservationClientsEmployees2=new ArrayList<>();
    ArrayList<ReservationClientsEmployee> reservationClientsEmployees3=new ArrayList<>();
    ArrayList<ReservationClients> reservationClients=new ArrayList<>();


    String items[]={"1","2","3","4","5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);


        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";


        APICall.searchGroupBookingMulti(BeautyMainPage.context);

                listView=view.findViewById(R.id.list_view);

        return view;
    }
}
