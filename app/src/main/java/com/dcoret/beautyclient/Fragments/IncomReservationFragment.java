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
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;


public class IncomReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    RecyclerView service_select;
    ImageView sortbtn;
    String filter,sort;
    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        service_select=view.findViewById(R.id.incom_ree);
        sortbtn=MyReservationFragment.view.findViewById(R.id.sort);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));

        APICall.layout=R.layout.incom_reservation_layout;
        APICall.filter=APICall.bookingFilter("1","8","0");



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
//                        bookingAutomatedBrowseData.clear();
//                        reservationsAdapter.notifyDataSetChanged();
                        if (id==R.id.one){
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.two){
                            APICall.sort=APICall.bookingSort("1","desc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.three){
                            APICall.sort=APICall.bookingSort("2","asc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.four){
                            APICall.sort=APICall.bookingSort("2","desc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });




        //---------cancel---------
//        filter=APICall.bookingFilter("1","4","0");

        //---------wait confirm by provider
        if (MyReservationFragment.filtercheck==false) {
            APICall.bookingAutomatedBrowse1("en", "100", MyReservationFragment.serviceId, "1", "", "", BeautyMainPage.context, APICall.layout);
        }else {
            MyReservationFragment.filtercheck=false;
        }
        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

}
