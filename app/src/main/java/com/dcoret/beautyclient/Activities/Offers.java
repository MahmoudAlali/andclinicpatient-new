package com.dcoret.beautyclient.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataClass.BestOfferItem;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Offers extends AppCompatActivity {
    public static Context context;
    RecyclerView recyclerView;
    public static   ArrayList<BestOfferItem> bestOfferItems=new ArrayList<>();
    public static OffersAdapter bestOffer;
    public static  String name="offers";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);

        context=this;
        //----------------init recycle view ----------------------------
//        if (bestOfferItems.size()>0){
            bestOfferItems.clear();
//        }
        recyclerView=findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        bestOffer=new OffersAdapter(this,bestOfferItems);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(bestOffer);
        //------------------------ call API bestOffers and get items-----------------
        APICall.bestOffer(Offers.this);

        //-------------------------------call BagReservation after 5 minutes


    }
    //-------------------- go to main page -----------------------
    public void servicesBeauty(View view) {
        Intent in=new Intent(this,BeautyMainPage.class);
        startActivity(in);
        finish();

    }




}
