package com.ptm.clinicpa.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.MyReservations;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Adapters.OffersAdapterTab;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.R;

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
        arrayList.clear();
        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyReservations.context));
        offersAdapterTab=new OffersAdapterTab(BeautyMainPage.context,  APICall.offers,"");
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        String filter2="\"Filter\":["+APICall.Filter("7","1")+
                freeBookingFragment.filterGender+
                freeBookingFragment.filterDoctorName+
                freeBookingFragment.filterServicePlace+
                freeBookingFragment.filterSupplierName;
                if(!freeBookingFragment.filterSpeciality.equals(""))
                  filter2+=","+freeBookingFragment.filterSpeciality;

                filter2+=freeBookingFragment.filterAgeRange;

        if(!freeBookingFragment.filterDistance.equals(""))
            filter2+=","+freeBookingFragment.filterDistance;
        else
        {
            filter2+=",{\"num\":2,\"value1\":0,\"value2\":100000}";
        }
        if(freeBookingFragment.filterMyLocationLat.equals(""))
            filter2+=",{\"num\":34,\"value1\":"+ Offers.Lat+",\"value2\":0}"+",{\"num\":35,\"value1\":"+Offers.Long+",\"value2\":0}";
        else
        {
            filter2+=","+freeBookingFragment.filterMyLocationLat+","+
                    freeBookingFragment.filterMyLocationLng;
        }
        filter2+=
               "]";

        final String filter =filter2;


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                browseService(true); // your code
                APICall.offers.clear();
                offersAdapterTab.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
                APICall.automatedBrowseOffers( 1+"", BeautyMainPage.context, filter,"",offersAdapterTab);

               // APICall.automatedBrowseOffersForRequests("10","1",BeautyMainPage.context);

            }
        });
        APICall.automatedBrowseOffers( 1+"", BeautyMainPage.context, filter,"",offersAdapterTab);

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

