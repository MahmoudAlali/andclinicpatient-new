package com.ptm.clinicpa.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Adapters.EndlessRecyclerViewScrollListener;
import com.ptm.clinicpa.Adapters.HealthCentersAdapter;
import com.ptm.clinicpa.Adapters.RequestProvidersAdapter;
import com.ptm.clinicpa.DataModel.RequestProviderItem;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

import static com.ptm.clinicpa.Activities.TabOne.compareModels;

public class RequestProvidersFragment extends Fragment {

    public static ArrayList<RequestProviderItem> providerItems=new ArrayList<>();
    public static RequestProvidersAdapter providersAdapter;
    public static HealthCentersAdapter centersAdapter;
    public static RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static int pageNum=1;
    public static int PagesCount;
    public static LinearLayout previousPage,nextPage;
    public static TextView pageNumView;

    public static String bdb_booking_period;
    public static boolean isGroup;
    public static ImageView filter;
    public  static ProgressBar progressBar;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        compareModels.clear();
        providerItems.clear();
        View view= inflater.inflate(R.layout.request_providers_fragment,container,false);
       // filter =view.findViewById(R.id.filter);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        previousPage = view.findViewById(R.id.pagePrev);
        nextPage = view.findViewById(R.id.pageNext);
        filter = view.findViewById(R.id.filter);
        pageNumView = view.findViewById(R.id.pagenum);
        isGroup =getArguments().getBoolean("isGroup");
        progressBar=view.findViewById(R.id.progress);


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Pages","false");
                pageNum++;
                providerItems.clear();

                providersAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
                if(isGroup)
                    APICall.automatedCentersBrowseForGroupBooking( pageNum+"", BeautyMainPage.context);
                else
                    APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);

            }
        });
        previousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageNum>1)
                {pageNum--;
                    pageNumView.setText(getResources().getString(R.string.page)+": "+pageNum);
                    providerItems.clear();

                    providersAdapter.notifyDataSetChanged();
                    //---------------------call API for Services and get items-------------
                    if(isGroup)
                        APICall.automatedCentersBrowseForGroupBooking( pageNum+"", BeautyMainPage.context);

                    else
                        APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);                }
            }
        });
        pageNumView.setText(getResources().getString(R.string.page)+": "+pageNum);

        pageNum=1;

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                providerItems.clear();

                pageNum=1;
                try {
                    providersAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                //---------------------call API for Services and get items-------------
                if(isGroup)
                    APICall.automatedCentersBrowseForGroupBooking( pageNum+"", BeautyMainPage.context);

                else
                    APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);            }
        });
        if(isGroup)
        {

            freeBookingFragment.filterDistance="";
            if(FreeGroupBooking.filterMyLocationLat.equals(""))
            {
                freeBookingFragment.filterMyLocationLat="{\"num\":34,\"value1\":"+ Offers.Lat+",\"value2\":0}";
                freeBookingFragment.filterMyLocationLng="{\"num\":35,\"value1\":"+Offers.Long+",\"value2\":0}";
            }
            else
            {
                freeBookingFragment.filterMyLocationLat=FreeGroupBooking.filterMyLocationLat;
                freeBookingFragment.filterMyLocationLng=FreeGroupBooking.filterMyLocationLng;
            }
            APICall.automatedCentersBrowseForGroupBooking( pageNum+"", BeautyMainPage.context);

        }

        else
            APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGroup)
                {
                    Fragment fragment = new FreeGroupBooking();

                    Bundle b= new Bundle();
                    b.putBoolean("isGroup",true);
                    FragmentManager fm = getActivity().getFragmentManager();
                    fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                }
                else
                {
                    Fragment fragment = new freeBookingFragment();
                    Bundle b= new Bundle();
                    b.putBoolean("isGroup",false);
                    FragmentManager fm = getActivity().getFragmentManager();
                    fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                }

            }
        });
        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        if(isGroup)
            centersAdapter=new HealthCentersAdapter(BeautyMainPage.context,  providerItems,R.layout.centers_layout_adapter_last,true);

        else
            providersAdapter=new RequestProvidersAdapter(BeautyMainPage.context,  providerItems,R.layout.providers_layout_adapter_last);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        if (isGroup)
        recyclerView.setAdapter(centersAdapter);
        else
        recyclerView.setAdapter(providersAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                getdata();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        return view;
    }


    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
        if(!isGroup)
        providersAdapter.notifyDataSetChanged();
        else
        centersAdapter.notifyDataSetChanged();
//        recyclerView.invalidate();
    }
    public static void rechekPages(int totalCount){

        if((pageNum*10)<totalCount)
        {
            Log.e("Pages","true"+totalCount);
            nextPage.setClickable(true);
            nextPage.setVisibility(View.VISIBLE);
        }
        else
        {
            Log.e("Pages","false"+totalCount);
            nextPage.setClickable(false);


        }


        if(pageNum==1)
        {
            Log.e("PagesPre","false"+totalCount);
            previousPage.setActivated(false);
        }

        pageNumView.setText(BeautyMainPage.context.getResources().getString(R.string.page)+": "+pageNum);


//        recyclerView.invalidate();
    }

    private void getdata() {
        pageNum++;
        RequestProvidersFragment.progressBar.setVisibility(View.VISIBLE);
        if(isGroup)
            APICall.automatedCentersBrowseForGroupBookingScrolling( pageNum+"", BeautyMainPage.context);

        else
            APICall.automatedProvidersBrowseScroll( pageNum+"", BeautyMainPage.context);

    }

}
