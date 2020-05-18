package com.ptmsa1.clinicclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Adapters.ReservationsInsideOfferAdapter;
import com.ptmsa1.clinicclient.DataModel.BookingAutomatedBrowseData;
import com.ptmsa1.clinicclient.DataModel.DataReservation;
import com.ptmsa1.clinicclient.R;
import com.ptmsa1.clinicclient.Adapters.ReservationsAdapter;
import com.github.clans.fab.FloatingActionMenu;


import java.util.ArrayList;

public class ReservationFragment extends Fragment {
    FloatingActionMenu fbmenu;
    ImageView reserv_btn_filter,sortbtn;
    RecyclerView recyclerView , recyclerView_cancel;
    public static String filter="",sort="";
    public static SwipeRefreshLayout pullToRefresh;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData=new ArrayList<>();
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData_cancel=new ArrayList<>();
    public static ReservationsAdapter reservationsAdapter;
    public static ReservationsAdapter reservationsAdapter_cancel;
    String[] items={"Resrvation 1","Resrvation 2","Resrvation 3","Resrvation 4","Resrvation 5","Resrvation 6"};
    public static ArrayList<DataReservation> reservations=new ArrayList<>();
    Button allreservation,resrevation_iside_offer;
    View view;

    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.activity_reservation_frag, container, false);


         //------------ init variables-----------
        toolbar=view.findViewById(R.id.toolbar);
        sortbtn=view.findViewById(R.id.sort);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookingAutomatedBrowseData.clear();
                reservationsAdapter.notifyDataSetChanged();
                APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

        bookingAutomatedBrowseData.clear();
        APICall.bookingAutomatedBrowse("en","20","1",filter,sort,BeautyMainPage.context);



        reserv_btn_filter=view.findViewById(R.id.reserv_btn_filter);
        allreservation=view.findViewById(R.id.all_reservation);
        resrevation_iside_offer=view.findViewById(R.id.reservation_inside_offer);
        recyclerView=view.findViewById(R.id.review);


        recyclerView_cancel=view.findViewById(R.id.review_cancel);

        //------------- invisible --------
        recyclerView_cancel.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        reservationsAdapter=new ReservationsAdapter(getActivity().getApplicationContext(),bookingAutomatedBrowseData);
        recyclerView.setAdapter(reservationsAdapter);

        recyclerView_cancel.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        reservationsAdapter_cancel=new ReservationsAdapter(getActivity().getApplicationContext(),bookingAutomatedBrowseData_cancel);
        recyclerView_cancel.setAdapter(reservationsAdapter_cancel);
//

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
                        bookingAutomatedBrowseData.clear();
                        reservationsAdapter.notifyDataSetChanged();
                        if (id==R.id.one){
                            //---------wait confirm by provider
                            sort=APICall.bookingSort("1","asc");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);

                        }else if (id==R.id.two){
                            //------- wait for execute--------
                            sort=APICall.bookingSort("1","desc");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
                        }else if (id==R.id.three){
                            //----------- wait for paid----
                            sort=APICall.bookingSort("2","asc");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
                        }else if (id==R.id.four){
                            sort=APICall.bookingSort("2","desc");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
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
                        int id=item.getItemId();
                        bookingAutomatedBrowseData.clear();
                        reservationsAdapter.notifyDataSetChanged();
                        if (id==R.id.one){
                            //---------wait confirm by provider
                            filter=APICall.bookingFilter("1","1","0");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);

                        }else if (id==R.id.two){
                            //------- wait for execute--------
                            filter=APICall.bookingFilter("1","7","0");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
                        }else if (id==R.id.three){
                            //----------- wait for paid----
                            filter=APICall.bookingFilter("1","2","0");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
                        }else if (id==R.id.four){
                            APICall.bookingFilter("1","3","");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
                        }else if (id==R.id.five){
                            bookingAutomatedBrowseData_cancel.clear();
                            recyclerView_cancel.setVisibility(View.VISIBLE);
                            //---------cancel---------
                            filter=APICall.bookingFilter("1","4","0");
                            APICall.bookingAutomatedBrowse("en","10","1",filter,sort,BeautyMainPage.context);
//                            filter=APICall.bookingFilter("1","5","0");
                            APICall.bookingAutomatedBrowsecancel("en","10","1",filter,sort,false,BeautyMainPage.context);
                        }
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
                reservationsAdapter=new ReservationsAdapter(getActivity().getApplicationContext(),bookingAutomatedBrowseData);
                recyclerView.setAdapter(reservationsAdapter);
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
