package com.ptm.clinicpa.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Adapters.BookingRequestsAdapter;
import com.ptm.clinicpa.Fragments.MyBookingRequestsFragment;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

public class NewBookingRequestsFragment extends Fragment {

    Fragment fragment;

    public static RecyclerView service_select;
    ImageView sortbtn;
    public static BookingRequestsAdapter bookingRequestsAdapter;
    String filter,sort;
    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};
    ArrayList<String> filters=new ArrayList<>();

    Boolean isScrolling=false;
    int curentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public  static int pageNum=1;

    public static String tmp="1";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        service_select=view.findViewById(R.id.incom_ree);
        MyBookingRequestsFragment.progressBar=view.findViewById(R.id.progress);
        sortbtn= MyBookingRequestsFragment.view.findViewById(R.id.sort);
        manager=new LinearLayoutManager(BeautyMainPage.context);
        service_select.setLayoutManager(manager);
        MyBookingRequestsFragment.tab="1";
        MyBookingRequestsFragment.salonFilterTemp="";
        MyBookingRequestsFragment.salonFilter="";
        MyBookingRequestsFragment.dateFilterTemp="";
        MyBookingRequestsFragment.dateFilter="";
        MyBookingRequestsFragment.typeFilterTemp="";
        MyBookingRequestsFragment.typeFilter="";
        MyBookingRequestsFragment.creationDateFilter="";
        MyBookingRequestsFragment.creationDateFilterTemp="";
        MyBookingRequestsFragment.sortbtn.setImageResource(R.drawable.ic_descending_arrangement_grey);
        MyBookingRequestsFragment.filterbtn.setImageResource(R.drawable.ic_filter_list_black_grey);
        MyBookingRequestsFragment.sortbtn.setVisibility(View.GONE);
        MyBookingRequestsFragment.filterbtn.setVisibility(View.GONE);
/*
        MyBookingRequestsFragment.groupbooking="";
*/
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
/*
        MyBookingRequestsFragment.groupbooking="";
*/
//        reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels,0);

//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

      //  APICall.layout= R.layout.incom_reservation_layout;
        APICall.filter=filter= APICall.Filter("1","9","0");
        filters.clear();

        filters.add(APICall.filter);
       /* if(!MyBookingRequestsFragment.dateFilter.equals(""))
            filters.add(MyBookingRequestsFragment.dateFilter);
        if(!MyBookingRequestsFragment.typeFilter.equals(""))
            filters.add(MyBookingRequestsFragment.typeFilter);
        if(!MyBookingRequestsFragment.salonFilter.equals(""))
            filters.add(MyBookingRequestsFragment.salonFilter);
*/
        APICall.filter=APICall.Filter(filters);


        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
     //   if (MyBookingRequestsFragment.filtercheck==false) {
            APICall.requestsAutomatedBrowse("en", "100", MyBookingRequestsFragment.serviceId, "1", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);
       /* }else {
            MyBookingRequestsFragment.filtercheck=false;
        }*/
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

    private void getdata() {
        MyBookingRequestsFragment.progressBar.setVisibility(View.VISIBLE);
        pageNum++;
        APICall.requestsAutomatedBrowseScrolling(APICall.ln, "20", MyBookingRequestsFragment.serviceId, pageNum, APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp);

    }
}
