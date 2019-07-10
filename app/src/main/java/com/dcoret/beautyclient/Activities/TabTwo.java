package com.dcoret.beautyclient.Activities;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapterTab;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class TabTwo extends Fragment {

    public static RecyclerView recyclerView;
    String[] items = {"Offer 1", "Offer 2", "Offer 3", "Offer 4", "Offer 5", "Offer 6", "Offer 7", "Offer 8", "Offer 9", "Offer 10"
            , "Offer 11", "Offer 12", "Offer 13", "Offer 14", "Offer 15", "Offer 16", "Offer 17", "Offer 18", "Offer 19", "Offer 20"
    };



    public static  String name="tabthree";
    View view;
   public static SwipeRefreshLayout pullToRefresh;
    public static   ArrayList<DataOffer>  arrayList=new ArrayList<>();
   public static OffersAdapterTab offersAdapterTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_two, container, false);


        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                browseService(true); // your code
                arrayList.clear();
                offersAdapterTab.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
                APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

            }
        });

        if (ServicesTabsFragment.updateoffr) {
            APICall.automatedBrowseOffers("8", "1", BeautyMainPage.context);
        }
        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyReservations.context));
        offersAdapterTab=new OffersAdapterTab(BeautyMainPage.context, arrayList);
        recyclerView.setAdapter(offersAdapterTab);

        return view;
    }

    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
        offersAdapterTab.notifyDataSetChanged();
    }
}
