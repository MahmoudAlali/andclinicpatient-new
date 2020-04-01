package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.RequestProvidersAdapter;
import com.ptmsa1.vizage.DataModel.RequestProviderItem;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

import static com.ptmsa1.vizage.Activities.TabOne.compareModels;

public class RequestProvidersFragment extends Fragment {

    public static ArrayList<RequestProviderItem> providerItems=new ArrayList<>();
    public static RequestProvidersAdapter providersAdapter;
    public static RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static int pageNum=1;
    public static int PagesCount;
    public static LinearLayout previousPage,nextPage;
    public static TextView pageNumView;
    public static String bdb_booking_period;
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        compareModels.clear();
        View view= inflater.inflate(R.layout.request_providers_fragment,container,false);
       // filter =view.findViewById(R.id.filter);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        previousPage = view.findViewById(R.id.pagePrev);
        nextPage = view.findViewById(R.id.pageNext);
        pageNumView = view.findViewById(R.id.pagenum);


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Pages","false");
                pageNum++;
                providerItems.clear();

                providersAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
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
                    APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);
                }
            }
        });
        pageNumView.setText(getResources().getString(R.string.page)+": "+pageNum);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                providerItems.clear();

                providersAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
                APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);
            }
        });
        APICall.automatedProvidersBrowse( pageNum+"", BeautyMainPage.context);




        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        providersAdapter=new RequestProvidersAdapter(BeautyMainPage.context,  providerItems,R.layout.providers_layout_adapter_last);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(providersAdapter);

        return view;
    }


    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
        providersAdapter.notifyDataSetChanged();
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
}
