package com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.MyReservations;
import com.dcoret.beautyclient.Adapters.OffersAdapterTab;
import com.dcoret.beautyclient.DataModel.DataOffer;
import com.dcoret.beautyclient.Fragments.IndividualBooking.ServicesTabsFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class TabTwo extends Fragment {

    public static RecyclerView recyclerView;




    public static  String name="tabthree";
    View view;
   public static SwipeRefreshLayout pullToRefresh;
    public static   ArrayList<DataOffer>  arrayList=new ArrayList<>();
   public static OffersAdapterTab offersAdapterTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_two, container, false);
        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyReservations.context));
        offersAdapterTab=new OffersAdapterTab(BeautyMainPage.context, arrayList);
        TabOne.pagenum=1;
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
            TabTwo.arrayList.clear();
            offersAdapterTab.notifyDataSetChanged();
            APICall.automatedBrowseOffers("8", "1", BeautyMainPage.context);
        }

        recyclerView.setAdapter(offersAdapterTab);

        return view;
    }

    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
      try {
          offersAdapterTab.notifyDataSetChanged();
      }catch (Exception e){
          e.printStackTrace();
      }
    }
}