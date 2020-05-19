package com.ptm.clinicpa.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Constants;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.CustomExpandableListAdapter;
import com.ptm.clinicpa.Adapters.CustomExpandableListAdapterSearchGroupBooking2;
import com.ptm.clinicpa.Adapters.GroupReservationsAdapter;
import com.ptm.clinicpa.R;

public class GroupReservationResultFragment extends Fragment {


        public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static CustomExpandableListAdapterSearchGroupBooking2 listAdapter2;
    public static GroupReservationsAdapter adapter;
    public static SwipeRefreshLayout pullToRefresh;
    public static CoordinatorLayout noSolutionMsg;



    String items[]={"1","2","3","4","5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);

        noSolutionMsg=view.findViewById(R.id.noSolMsg);

        String url="";Boolean isIn = null;
        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";

        if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingInside";
            isIn=true;
        }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()>1){
            url="http://clientapp.dcoret.com/api/booking/searchGroupBookingOutside";
            isIn=false;
        }
        TextView msgOfKnownProviders,msgOfClientsNames;
        msgOfClientsNames=view.findViewById(R.id.messageOfClientsNames);
        msgOfKnownProviders=view.findViewById(R.id.messageOfKnownProviders);
        if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
        {
            msgOfClientsNames.setText(Constants.messageOfClientsNames_ar);
            msgOfKnownProviders.setText(Constants.messageOfKnownProviders_ar);
        }
        else
        {
            msgOfClientsNames.setText(Constants.messageOfClientsNames_en);
            msgOfKnownProviders.setText(Constants.messageOfKnownProviders_en);
        }


        listView=view.findViewById(R.id.list_view);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        Log.e("URL",url);
//        APICall.searchGroupBooking(url,,isIn,BeautyMainPage.context);


        final Boolean finalIsIn = isIn;
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall.searchGroupBooking2(BeautyMainPage.context, finalIsIn);
            }
        });

        return view;
    }
}
