package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.ReservationsAdapter2;
import com.ptmsa1.vizage.R;

//import com.dcoret.beautyclient..APICallCall;


public class ExecutedReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    public static RecyclerView service_select;
    ReservationsAdapter2 reservationsAdapter2;


    Boolean isScrolling=false;
    int curentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public static int pageNum=1;


    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};
    String filter,sort;
    ImageView sortbtn;
    int layout;
    TextView incom_reservation,accept_reservation,deposited_reservation;
    public String tmp="3";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);
        pageNum=1;

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        MyReservationFragment.tab="3";
        MyReservationFragment.groupbooking="";

        service_select=view.findViewById(R.id.incom_ree);
        MyReservationFragment.progressBar=view.findViewById(R.id.progress);

        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels);
        sortbtn= MyReservationFragment.view.findViewById(R.id.sort);
        manager=new LinearLayoutManager(BeautyMainPage.context);
        service_select.setLayoutManager(manager);
//        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);
        service_select.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                curentItems=manager.getChildCount();
                totalItems=manager.getItemCount();
                scrollOutItems=manager.findFirstVisibleItemPosition();
                if (isScrolling && (curentItems+scrollOutItems==totalItems))
                {
                    //-------- fetch data
                    isScrolling=false;
                    getdata();
                }
            }
        });
//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

        APICall.layout= R.layout.incom_reservation_layout;

        Log.e("ArraABLength",APICall.arrayAB.length()+"is");
//        if (APICall.arrayAB==null)
            APICall.filter=filter= APICall.bookingFilterV1("1","3","0");






        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
        if (MyReservationFragment.filtercheck==false) {
            if (filter==null){
                APICall.filter=filter= APICall.bookingFilterV1("1","3","0");
            }
            APICall.bookingAutomatedBrowse1(APICall.ln, "20", MyReservationFragment.serviceId, "1", filter, "", BeautyMainPage.context, APICall.layout,tmp);
        }else {
            MyReservationFragment.filtercheck=false;
        }
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

    private void getdata() {
        MyReservationFragment.progressBar.setVisibility(View.VISIBLE);
        pageNum++;
        APICall.bookingAutomatedBrowseScrolling(APICall.ln, "20", MyReservationFragment.serviceId, pageNum, APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);
    }

}
