package com.ptmsa1.clinicclient.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.API.Constants;
import com.ptmsa1.clinicclient.Adapters.CustomExpandableListAdapter;
import com.ptmsa1.clinicclient.R;


public class OfferBookingResult extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    //    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
//    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;

    public static Context context;
    public static String offertype;
    public static String place;
    public static CoordinatorLayout noSolutionMsg;


    public static String offerTypeTMP,placeTMP;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_offer_group_result_);
        BeautyMainPage.FRAGMENT_NAME="OfferBookingResult";
        noSolutionMsg=findViewById(R.id.noSolMsg);

        context=this;
//        ProviderMainPage.FRAGMENT_NAME="GroupReservationResultFragment";
        TextView msgOfKnownProviders,msgOfClientsNames;
        msgOfClientsNames=findViewById(R.id.messageOfClientsNames);
        msgOfKnownProviders=findViewById(R.id.messageOfKnownProviders);
        if(context.getResources().getString(R.string.locale).equals("ar"))
        {
            msgOfClientsNames.setText(Constants.messageOfClientsNames_ar);
            msgOfKnownProviders.setText(Constants.messageOfKnownProviders_ar);
        }
        else
        {
            msgOfClientsNames.setText(Constants.messageOfClientsNames_en);
            msgOfKnownProviders.setText(Constants.messageOfKnownProviders_en);
        }
        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        final String filter=getIntent().getStringExtra("filter");
         offertype=getIntent().getStringExtra("offertype");
         String offerplace=getIntent().getStringExtra("offerplace");
         place=getIntent().getStringExtra("place");


         APICall.offertypeAll=offertype;
        Log.e("OFFER_CLASS_NAME4","is"+APICall.OFFER_CLASS_NAME);


        if (BeautyMainPage.bdb_is_guest.equals("1")){
            offerTypeTMP=offertype;
            placeTMP=place;
        }

         if (place.equals("0")){
             url="http://clientapp.dcoret.com/api/service/offer/searchOfferBookingInside";
         }else if ( place.equals("1") || place.equals("2") ||place.equals("3")){
             url="http://clientapp.dcoret.com/api/service/offer/searchOfferBookingOutside";
         }

//        api/service/offer/searchOfferBookingInside


        Log.e("URL",url);
        if (offertype.equals("2") ||offertype.equals("5"))
        {
//            APICall.searchGroupOfferBooking25(context,url,filter);
            APICall.searchGroupOfferBooking(context,url,filter);
        }else {
            APICall.searchGroupOfferBooking(context,url, filter);
        }
//        APICallCall.searchGroupBooking2(BeautyMainPage.context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("URL",url);
                if (offertype.equals("2") ||offertype.equals("5"))
                {
//                    APICall.searchGroupOfferBooking25(context,url,filter);
                    APICall.searchGroupOfferBooking(context,url,filter);
                }else {
                    APICall.searchGroupOfferBooking(context,url, filter);
                }
            }
        });



    }
}


