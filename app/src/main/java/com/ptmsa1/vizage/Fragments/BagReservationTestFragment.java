package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.TabOneBag;
import com.ptmsa1.vizage.Activities.TabOthersBag;
import com.ptmsa1.vizage.Activities.TabSingleBag;
import com.ptmsa1.vizage.Adapters.BagReservationAdapter;
import com.ptmsa1.vizage.DataModel.AddToCart;
import com.ptmsa1.vizage.DataModel.DataReservation;
import com.ptmsa1.vizage.DataModel.GetCart;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class BagReservationTestFragment extends Fragment {

    RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public  static  ArrayList<GetCart> getCarts=new ArrayList<>();
    public  static  ArrayList<AddToCart> addToCarts=new ArrayList<>();
    public static  BagReservationAdapter bagReservationAdapter;
   static  boolean isFirstOpen=true;
   TextView individualReservation,groupReservation,otherResevation;
    public static ArrayList<DataReservation> reservations=new ArrayList<>();



    Fragment fragment;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_bag_reservation_test_frag, container, false);

        findViews(view);
//
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getCarts.clear();
//                APICall.getCart(BeautyMainPage.context);
//            }
//        });
//        if (isFirstOpen){
//            getCarts.clear();
//            APICall.getCart(BeautyMainPage.context);
//            isFirstOpen=false;
//        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });
        APICall.getAllCart(BeautyMainPage.context);

        tabselected(individualReservation,groupReservation,otherResevation);
        fragment = new TabSingleBag();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frag, fragment);
        fragmentTransaction.commit();

        //=------------------ tab selected----------------
        individualReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(individualReservation,groupReservation,otherResevation);
                fragment = new TabSingleBag();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frag, fragment);
                fragmentTransaction.commit();
            }
        });

        groupReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(groupReservation,individualReservation,otherResevation);
                fragment = new TabOneBag();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frag, fragment);
                fragmentTransaction.commit();

            }
        });

        otherResevation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(otherResevation,individualReservation,groupReservation);
                fragment = new TabOthersBag();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frag, fragment);
                fragmentTransaction.commit();
            }
        });
        //--------------------------------------------------------

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        bagReservationAdapter=new BagReservationAdapter(getActivity().getApplicationContext(),getCarts);
//        recyclerView.setAdapter(bagReservationAdapter);


        return view;
        }



    void tabselected(TextView t1, TextView t2, TextView t3){
        t1.setTextSize(20);
        t1.setTextColor(Color.MAGENTA);
        t2.setTextSize(18);
        t2.setTextColor(Color.BLACK);
        t3.setTextSize(18);
        t3.setTextColor(Color.BLACK);

    }

    void findViews(View view){
//        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        toolbar=view.findViewById(R.id.toolbar);
//        recyclerView=view.findViewById(R.id.review);
        individualReservation=view.findViewById(R.id.individualRes);
        groupReservation=view.findViewById(R.id.groupRes);
        otherResevation=view.findViewById(R.id.otherRes);
    }

        }