package com.dcoret.beautyclient.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
//import com.dcoret.beautyclient.API.APICallCall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;


public class DepositReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Spinner category;
    RecyclerView service_select;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    TextView incom_reservation,accept_reservation,deposited_reservation;
    String filter,sort;
    ImageView sortbtn;
    int layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";

        Toolbar toolbar;
        toolbar=view.findViewById(R.id.toolbarm);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((AppCompatActivity)ProviderMainPage.context).onBackPressed();
//            }
//        });

        service_select=view.findViewById(R.id.incom_ree);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        service_select.setAdapter(new DepositReservationAdapter(ProviderMainPage.context,items));
        APICall.layout=R.layout.deposit_reservation_layout_v2;
        APICall.filter=APICall.bookingFilter("1","7","0");
        sortbtn=MyReservationFragment.view.findViewById(R.id.sort);
        sortbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu_sort, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        if (id==R.id.one){
                            //---------wait confirm by provider
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.bookingAutomatedBrowse("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,layout);
                        }else if (id==R.id.two){
                            //------- wait for execute--------
                            APICall.sort=APICall.bookingSort("1","desc");
                            APICall.bookingAutomatedBrowse("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,layout);
                        }else if (id==R.id.three){
                            //----------- wait for paid----
                            sort=APICall.bookingSort("2","asc");
                            APICall.bookingAutomatedBrowse("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,layout);
                        }else if (id==R.id.four){
                            APICall.sort=APICall.bookingSort("2","desc");
                            APICall.bookingAutomatedBrowse("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,layout);
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });

        //------- wait for execute--------
//        APICall.bookingAutomatedBrowse("en","10","","1",APICall.filter,"",BeautyMainPage.context,R.layout.deposit_reservation_layout);
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter);
        if (MyReservationFragment.filtercheck==false) {
            APICall.bookingAutomatedBrowse("en", "10", MyReservationFragment.serviceId, "1", APICall.filter, "", BeautyMainPage.context, APICall.layout);
        }else {
            MyReservationFragment.filtercheck=false;
        }
        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);

        return view;
    }

}
