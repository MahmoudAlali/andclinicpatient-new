package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.MyReservations;
import com.ptmsa1.vizage.Adapters.OffersAdapterTab;
import com.ptmsa1.vizage.DataModel.DataOffer;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class OffersForRequest extends Fragment {

    public static RecyclerView recyclerView;




    View view;
    public static SwipeRefreshLayout pullToRefresh;
    public static ArrayList<DataOffer> arrayList=new ArrayList<>();
    public static OffersAdapterTab offersAdapterTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_two, container, false);
        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyReservations.context));
        offersAdapterTab=new OffersAdapterTab(BeautyMainPage.context, arrayList);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                browseService(true); // your code
                arrayList.clear();
                offersAdapterTab.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
                APICall.automatedBrowseOffersForRequests("10","1",BeautyMainPage.context);

            }
        });
        APICall.automatedBrowseOffersForRequests("10","1",BeautyMainPage.context);

       /* if (ServicesTabsFragment.updateoffr) {
            TabTwo.arrayList.clear();
            offersAdapterTab.notifyDataSetChanged();
            APICall.automatedBrowseOffersForRequests("10", "1", BeautyMainPage.context);
        }*/

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
