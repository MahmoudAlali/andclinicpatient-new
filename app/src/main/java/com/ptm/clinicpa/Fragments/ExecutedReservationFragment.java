package com.ptm.clinicpa.Fragments;

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
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.OldAppointmentsFiltersActivity;
import com.ptm.clinicpa.Adapters.ReservationsAdapter2;
import com.ptm.clinicpa.R;

//import com.dcoret.beautyclient..APICallCall;


public class ExecutedReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    public static RecyclerView service_select;
    ReservationsAdapter2 reservationsAdapter2;


    String filterFromNotification="";
    Boolean isScrolling=false;
    int curentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public static int pageNum=1;


    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};
    public static String filter,sort;
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

        filterFromNotification="";
        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        MyReservationFragment.tab="3";
        MyReservationFragment.groupbooking="";

        MyReservationFragment.note_cancel.setVisibility(View.GONE);
                service_select=view.findViewById(R.id.incom_ree);
        MyReservationFragment.progressBar=view.findViewById(R.id.progress);
        MyReservationFragment.filterbtn.setImageResource(R.drawable.filter_on);
        MyReservationFragment.sortbtn.setImageResource(R.drawable.sort_on);
        MyReservationFragment.sortbtn.setClickable(true);
        MyReservationFragment.filterbtn.setClickable(true);
      //  MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.reservationModels);
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


        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String book_id_for_filter="";
        if (bundle != null) {
            book_id_for_filter = bundle.getString("book_id_for_filter");
            Log.e("NotifDepoif",book_id_for_filter);

        }

        if(!book_id_for_filter.equals(""))
        {
            filterFromNotification=APICall.Filter("56",book_id_for_filter,"0")+",";
        }


        //endregion




        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
        APICall.filter=filter= APICall.bookingFilterV1("1","3","0");
        filter="\"Filter\":["+filterFromNotification+APICall.Filter("1","3","0")+
                OldAppointmentsFiltersActivity.filterCreateDate+
                OldAppointmentsFiltersActivity.filterDoctorName+
                OldAppointmentsFiltersActivity.filterExecDate+
                OldAppointmentsFiltersActivity.filterMyLocationLat+
                OldAppointmentsFiltersActivity.filterMyLocationLng+
                OldAppointmentsFiltersActivity.filterServices+
                OldAppointmentsFiltersActivity.filterAppointmentType+
                OldAppointmentsFiltersActivity.filterSpeciality+
                OldAppointmentsFiltersActivity.filterSupplierId
//        {\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+val2+"}
                +"]";

        APICall.appointmentsAutomatedBrowse(APICall.ln, "20", MyReservationFragment.serviceId, "1", filter, "", BeautyMainPage.context, APICall.layout,tmp,false);

      /*  if (MyReservationFragment.filtercheck==false) {
            if (filter==null){

            }
        }else {
            MyReservationFragment.filtercheck=false;
        }
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);*/
        return view;
    }

    @Override
    public void onResume() {
        //APICall.filter=filter= APICall.bookingFilterV1("1","3","0");
        filter="\"Filter\":["+APICall.Filter("1","3","0")+
                OldAppointmentsFiltersActivity.filterCreateDate+
                OldAppointmentsFiltersActivity.filterDoctorName+
                OldAppointmentsFiltersActivity.filterExecDate+
                OldAppointmentsFiltersActivity.filterMyLocationLat+
                OldAppointmentsFiltersActivity.filterMyLocationLng+
                OldAppointmentsFiltersActivity.filterServices+
                OldAppointmentsFiltersActivity.filterAppointmentType+
                OldAppointmentsFiltersActivity.filterSpeciality+
                OldAppointmentsFiltersActivity.filterSupplierId
//        {\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+val2+"}
                +"]";

        APICall.appointmentsAutomatedBrowse(APICall.ln, "20", MyReservationFragment.serviceId, "1", filter, "", BeautyMainPage.context, APICall.layout,tmp,false);

        super.onResume();
    }

    private void getdata() {
        MyReservationFragment.progressBar.setVisibility(View.VISIBLE);
        pageNum++;
        APICall.appointmentsAutomatedBrowseScrolling(APICall.ln, "20", MyReservationFragment.serviceId, pageNum+"", filter, "", BeautyMainPage.context, APICall.layout,tmp,false);
    }

}
