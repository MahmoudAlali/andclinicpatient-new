package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataModel.BestOfferItem;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class Offers extends AppCompatActivity {
    public static Context context;
    RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static   ArrayList<BestOfferItem> bestOfferItems=new ArrayList<>();
    public static OffersAdapter bestOffer;
    public static  String name="offers";
    static Boolean isFirstOpen=true;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);

        context=this;
        //----------------init recycle view ----------------------------
//        if (bestOfferItems.size()>0){
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bestOfferItems.clear();
                APICall.bestOffer(Offers.this);
            }
        });
        if (isFirstOpen){
            bestOfferItems.clear();
            APICall.bestOffer(Offers.this);
            isFirstOpen=false;
        }

//        }
        recyclerView=findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        bestOffer=new OffersAdapter(this,bestOfferItems);
//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(bestOffer);
        //------------------------ call API bestOffers and get items-----------------
//        APICall.bestOffer(Offers.this);

        //-------------------------------call BagReservation after 5 minutes


    }
    //-------------------- go to main page -----------------------
    public void servicesBeauty(View view) {
        Intent in=new Intent(this,BeautyMainPage.class);
        startActivity(in);
        finish();

    }




}
