package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ReservationsInsideOfferAdapter;
import com.dcoret.beautyclient.DataClass.DataReservation;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Activities.Reservation;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter;



import java.util.ArrayList;
import java.util.List;

public class ReservationFragment extends Fragment {

    LinearLayout reserv_btn_filter;
    RecyclerView recyclerView;
    String[] items={"Resrvation 1","Resrvation 2","Resrvation 3","Resrvation 4","Resrvation 5","Resrvation 6"};
    //an error
     ArrayList<DataService> services= Reservation.services;
    public static ArrayList<DataReservation> reservations=new ArrayList<>();
    Button allreservation,resrevation_iside_offer;
    View view;


        FloatingActionButton fab,fab1,fab2,fab3;
        Boolean isFABOpen=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.activity_reservation_frag, container, false);
        reserv_btn_filter=view.findViewById(R.id.reserv_btn_filter);
//        fab1=view.findViewById(R.id.fab1);
//        fab2=view.findViewById(R.id.fab2);
//
//        fab=view.findViewById(R.id.fab);

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
//                        Toast.makeText(
//                                MainActivity.this,
//                                "You Clicked : " + item.getTitle(),
//                                Toast.LENGTH_SHORT
//                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isFABOpen){
//                    showFABMenu();
//                }else {
//                    closeFABMenu();
//                }
//            }
//        });

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
//    private void showFABMenu(){
//        isFABOpen=true;
//        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
//        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
//        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
//    }
//
//    private void closeFABMenu(){
//        isFABOpen=false;
//        fab1.animate().translationY(0);
//        fab2.animate().translationY(0);
//        fab3.animate().translationY(0);
//    }


}
