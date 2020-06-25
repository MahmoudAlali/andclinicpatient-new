package com.ptm.clinicpa.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.ReservationsAdapter2;
import com.ptm.clinicpa.R;

//import com.dcoret.beautyclient.API.APICallCall;


public class DepositReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    public static RecyclerView service_select;
    public static ReservationsAdapter2 reservationsAdapter2;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    TextView incom_reservation,accept_reservation,deposited_reservation;
    String filter,sort;
    ImageView sortbtn;
    int layout;
    public String tmp="2";

    Boolean isScrolling=false;
    int curentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public static int pageNum=1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);
        pageNum=1;

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        MyReservationFragment.tab="2";
        MyReservationFragment.groupbooking="";
        MyReservationFragment.note_cancel.setVisibility(View.VISIBLE);

       // MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels);
        service_select=view.findViewById(R.id.incom_ree);
        MyReservationFragment.progressBar=view.findViewById(R.id.progress);
        sortbtn= MyReservationFragment.view.findViewById(R.id.sort);
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

        MyReservationFragment.filterbtn.setImageResource(R.drawable.filter_off);
        MyReservationFragment.sortbtn.setImageResource(R.drawable.sort_off);
        MyReservationFragment.sortbtn.setClickable(false);
        MyReservationFragment.filterbtn.setClickable(false);


//        MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,);

//        service_select.setAdapter(reservationsAdapter2);

        APICall.layout= R.layout.incom_reservation_layout;
        try {
            APICall.arrayAB.remove(0);
        }catch (Exception e){
            e.printStackTrace();
        }
//        if (APICall.arrayAB==null)

        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String book_id="";
        if (bundle != null) {
            book_id = bundle.getString("book_id");
            Log.e("NotifDepoif",book_id);

        }

        if(!book_id.equals(""))
        {
            bundle.putString("book_id", book_id);
            Log.e("NotifDepo",book_id);
            //    MyReservationFragment.reservationsAdapter2.book_id=book_id;
            Log.e("BookID",book_id);
            Intent intent=new Intent(BeautyMainPage.context, ReservatoinDetailsActivity.class);
            intent.putExtra("book_id",book_id);
            startActivity(intent);
        }


        //endregion





        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        APICall.filter= APICall.bookingFilterV1("1","7","0");
        APICall.appointmentsAutomatedBrowse(APICall.ln, "20", MyReservationFragment.serviceId, "1",  APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp,true);

        //---------wait confirm by provider
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }


        private void getdata() {
            MyReservationFragment.progressBar.setVisibility(View.VISIBLE);
            pageNum++;
            APICall.appointmentsAutomatedBrowseScrolling("en", "20", MyReservationFragment.serviceId, pageNum+"", APICall.filter, "", BeautyMainPage.context, APICall.layout,tmp,true);


        }


}
