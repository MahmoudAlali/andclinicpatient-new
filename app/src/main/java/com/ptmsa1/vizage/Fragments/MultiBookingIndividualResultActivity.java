package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.CustomExpandableListAdapterForMultiInd;
import com.ptmsa1.vizage.Adapters.GroupReservationsAdapter;
import com.ptmsa1.vizage.R;

    public class MultiBookingIndividualResultActivity extends AppCompatActivity {

    public static ExpandableListView listView;
    public static CustomExpandableListAdapterForMultiInd listAdapter;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    String url="",urlAlt="";

    public  static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reservation_result_frag);
        context=this;
        Log.e("FNAMEMBI",BeautyMainPage.FRAGMENT_NAME);

        final String filter=getIntent().getStringExtra("filter");

        if (MultiIndividualBookingReservationFragment.choose_occision.getSelectedItemPosition()==2){
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBooking3_13Inside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";
            }else {
                url=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBooking3_13Outside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";

            }
        }else {
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                url = APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingInside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeInside";
            }else {
                url = APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingOutside";
                urlAlt=APICall.API_PREFIX_NAME+"/api/booking/searchGroupBookingAlternativeOutside";

            }
        }





        Log.e("dateURLS",url);
        Log.e("filter",filter);
        Log.e("FNAME", BeautyMainPage.FRAGMENT_NAME);

        listView=findViewById(R.id.list_view);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        APICall.searchGroupBookingMulti(url,urlAlt,filter,context);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBookingMulti(url,urlAlt,filter,context);
            }
        });

    }
}
