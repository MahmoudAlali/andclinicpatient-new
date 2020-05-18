package com.ptmsa1.clinicclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Activities.BookingRequestDetailsActivity;
import com.ptmsa1.clinicclient.Adapters.BookingRequestsAdapter;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;

public class OldBookingRequestsFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Spinner category;
    public static RecyclerView service_select;
    public static BookingRequestsAdapter bookingRequestsAdapter;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    TextView incom_reservation,accept_reservation,deposited_reservation;
    String filter,sort;
    ImageView sortbtn;
    int layout;
    ArrayList<String> filters=new ArrayList<>();



    Boolean isScrolling=false;
    int curentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public  static int pageNum=1;

    public String tmp="2";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        pageNum=1;
        BeautyMainPage.FRAGMENT_NAME="oldBookingRequestsFragment";
        MyBookingRequestsFragment.tab="2";
/*
        MyBookingRequestsFragment.groupbooking="";
*/

        MyBookingRequestsFragment.progressBar=view.findViewById(R.id.progress);

        service_select=view.findViewById(R.id.incom_ree);
        sortbtn= MyBookingRequestsFragment.view.findViewById(R.id.sort);
        manager=new LinearLayoutManager(BeautyMainPage.context);
        service_select.setLayoutManager(manager);
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

       // APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.Filter("1","8","0");

        filters.clear();
        filters.add(APICall.filter);
        if(!MyBookingRequestsFragment.dateFilter.equals(""))
            filters.add(MyBookingRequestsFragment.dateFilter);
        if(!MyBookingRequestsFragment.typeFilter.equals(""))
            filters.add(MyBookingRequestsFragment.typeFilter);
        if(!MyBookingRequestsFragment.salonFilter.equals(""))
            filters.add(MyBookingRequestsFragment.salonFilter);

        APICall.filter=APICall.Filter(filters);
        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String order_id="";
        if (bundle != null) {
            order_id = bundle.getString("order_id");
            Log.e("NotifDepoif",order_id);

        }

        if(!order_id.equals(""))
        {
            /*bundle.putString("order_id", order_id);
            Log.e("NotifDepo",order_id);
            //    MyReservationFragment.reservationsAdapter2.book_id=book_id;
            Log.e("order_id",order_id);*/
            Intent intent=new Intent(BeautyMainPage.context, BookingRequestDetailsActivity.class);
            intent.putExtra("order_id",order_id);
            startActivity(intent);
        }


        //endregion





        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
      //  if (MyBookingRequestsFragment.filtercheck==false) {
            APICall.requestsAutomatedBrowse(APICall.ln, "20", MyBookingRequestsFragment.serviceId, "1", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);
        /*}else {
            MyBookingRequestsFragment.filtercheck=false;
        }*/
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

    private void getdata() {
        pageNum++;
        MyBookingRequestsFragment.progressBar.setVisibility(View.VISIBLE);
        APICall.requestsAutomatedBrowseScrolling(APICall.ln, "20", MyBookingRequestsFragment.serviceId, pageNum, APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);

    }

}
