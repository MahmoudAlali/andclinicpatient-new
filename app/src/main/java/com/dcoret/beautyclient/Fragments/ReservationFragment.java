package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ReservationsInsideOfferAdapter;
import com.dcoret.beautyclient.DataClass.DataReservation;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter;
import com.github.clans.fab.FloatingActionMenu;


import java.util.ArrayList;

public class ReservationFragment extends Fragment {
    FloatingActionMenu fbmenu;
    LinearLayout reserv_btn_filter;
    RecyclerView recyclerView;
    String[] items={"Resrvation 1","Resrvation 2","Resrvation 3","Resrvation 4","Resrvation 5","Resrvation 6"};
    public static ArrayList<DataReservation> reservations=new ArrayList<>();
    Button allreservation,resrevation_iside_offer;
    View view;

    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.activity_reservation_frag, container, false);




        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });





        reserv_btn_filter=view.findViewById(R.id.reserv_btn_filter);
        allreservation=view.findViewById(R.id.all_reservation);
        resrevation_iside_offer=view.findViewById(R.id.reservation_inside_offer);
        recyclerView=view.findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new ReservationsAdapter(getActivity().getApplicationContext(),items));
        reserv_btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), reserv_btn_filter);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
        allreservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView=view.findViewById(R.id.review);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                recyclerView.setAdapter(new ReservationsAdapter(getActivity().getApplicationContext(),items));
            }
        });
        resrevation_iside_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView=view.findViewById(R.id.review);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                recyclerView.setAdapter(new ReservationsInsideOfferAdapter(getActivity().getApplicationContext(),items));
            }
        });
        return view;
        }

}
