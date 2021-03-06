package com.ptm.clinicpa.Activities;



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
import android.widget.Button;
import android.widget.ExpandableListView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Adapters.BagReservationAdapter;
import com.ptm.clinicpa.Adapters.CustomExpandableListBagAdapter;
import com.ptm.clinicpa.Adapters.ServicesAdapter;
import com.ptm.clinicpa.DataModel.BrowseServiceItem;
import com.ptm.clinicpa.DataModel.GetCart;
import com.ptm.clinicpa.R;

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
    Button res_all;


    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_single_bag, container, false);

        recyclerView=view.findViewById(R.id.review);
        res_all=view.findViewById(R.id.res_all);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCarts.clear();
                APICall.getAllCart(BeautyMainPage.context);
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



        //------------------- new for test //----------------- reserve all single reservations
        res_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<APICall.singleBookingList.size();i++) {
                    APICall.moveAllCartToBooking( APICall.singleBookingList.get(i).getBdb_id(), APICall.singleBookingList.get(i).getBdb_is_group_booking(),i,BeautyMainPage.context);
                }
            }
        });

        Button delete_all=view.findViewById(R.id.delete_all);
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  --------- delete offer and reserve group booking
                for (int i=0;i<APICall.singleBookingList.size();i++) {
                    APICall.deletallFromCart( APICall.singleBookingList.get(i).getBdb_id(),i,BeautyMainPage.context);
                }
            }
        });


//        APICall.searchGroupBookingBag(BeautyMainPage.context);
//        APICall.getAllCart(BeautyMainPage.context);

        return view;
    }
}


