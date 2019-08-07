package com.dcoret.beautyclient.Activities;



import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.BagReservationAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListBagAdapter;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.GetCart;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class TabSingleBag extends Fragment {

    public static RecyclerView recyclerView;
    public static   ArrayList<BrowseServiceItem>  arrayList=new ArrayList<>();
    static  ServicesAdapter servicesAdapter;

    public static SwipeRefreshLayout pullToRefresh;


    static View view;
    public static BagReservationAdapter bagReservationAdapter;
    public  static  ArrayList<GetCart> getCarts=new ArrayList<>();
//    ExpandableListView listView;

    public static ExpandableListView listView;
    public static CustomExpandableListBagAdapter listAdapter;
    public static BagReservationAdapter adapter;
    static  boolean isFirstOpen=true;


    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_single_bag, container, false);

        recyclerView=view.findViewById(R.id.review);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCarts.clear();
//                APICall.getCart(BeautyMainPage.context);
            }
        });
        if (isFirstOpen){
            getCarts.clear();
//            APICall.getCart(BeautyMainPage.context);
            isFirstOpen=false;
        }

//        APICall.getAllCart(BeautyMainPage.context);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        bagReservationAdapter=new BagReservationAdapter(getActivity().getApplicationContext(),APICall.singleBookingList);
        recyclerView.setAdapter(bagReservationAdapter);

//        APICall.searchGroupBookingBag(BeautyMainPage.context);
//        APICall.getAllCart(BeautyMainPage.context);

        return view;
    }
}


