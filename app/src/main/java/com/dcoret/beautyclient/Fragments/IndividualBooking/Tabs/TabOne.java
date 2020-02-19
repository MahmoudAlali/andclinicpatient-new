package com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs;



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
import android.widget.ImageButton;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataModel.BrowseServiceItem;
import com.dcoret.beautyclient.DataModel.CompareModel;
import com.dcoret.beautyclient.Fragments.IndividualBooking.ServicesTabsFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class TabOne extends Fragment {

    public static String bdb_sup_id="";
    public static String ser_id="";
    public static RecyclerView recyclerView;
    public static   ArrayList<BrowseServiceItem>  arrayList=new ArrayList<>();
    public static  ServicesAdapter servicesAdapter;
    public static ArrayList<CompareModel> compareModels=new ArrayList<>();

    public static SwipeRefreshLayout pullToRefresh;
    public static String ser_sup_id="";
    public static String bdb_time="";
    public static String bdb_is_bride="";


    static View view;
    ImageButton filter;
    public static int pagenum=1;
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {


        compareModels.clear();
        view= inflater.inflate(R.layout.tab_one,container,false);
        filter =view.findViewById(R.id.filter);
         pullToRefresh = view.findViewById(R.id.pullToRefresh);



        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                compareModels.clear();

                servicesAdapter.notifyDataSetChanged();
                    //---------------------call API for Services and get items-------------
                    APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", pagenum+"", BeautyMainPage.context);
            }
        });

        if (ServicesTabsFragment.updateServ){
            compareModels.clear();
            try{
                APICall.pd.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
            APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", pagenum+"", BeautyMainPage.context);
            ServicesTabsFragment.updateServ=false;
            ServicesTabsFragment.updateoffr=true;
        }

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        servicesAdapter=new ServicesAdapter(BeautyMainPage.context,  TabOne.arrayList,R.layout.service_layout_adapter_last);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);

            return view;
        }


    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
        servicesAdapter.notifyDataSetChanged();
    }


}


