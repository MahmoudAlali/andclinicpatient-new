package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
//import com.dcoret.beautyclient..APICallCall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;


public class AcceptReservationFragment extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Spinner category;
    RecyclerView service_select;

    static String[] items={"Service 1","Service 2","Service 3","Service 4","Service 5","Service 6"};
    String filter,sort;
    ImageView sortbtn;
    int layout;
    TextView incom_reservation,accept_reservation,deposited_reservation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incom_reservatiom_fragment, container, false);

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        BeautyMainPage.currentFragment=getTargetFragment();
        service_select=view.findViewById(R.id.incom_ree);
        service_select.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        service_select.setAdapter(new AcceptReservationAdapter(ProviderMainPage.context,items));

//        APICall.layout=R.layout.accept_reservation_layout;
//        API.layout=R.layout.accept_reservation_layout_v2;
        APICall.layout=R.layout.accept_reservation_layout_v2;
//        APICall.filter=APICall.bookingFilter("1","2","0");
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
//                        bookingAutomatedBrowseData.clear();
//                        reservationsAdapter.notifyDataSetChanged();
                        if (id==R.id.one){
                            //---------wait confirm by provider
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.bookingAutomatedBrowseoffer("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);

                        }else if (id==R.id.two){
                            //------- wait for execute--------
                            APICall.sort=APICall.bookingSort("1","desc");
                            APICall.bookingAutomatedBrowseoffer("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.three){
                            //----------- wait for paid----
                            APICall.sort=APICall.bookingSort("2","asc");
                            APICall.bookingAutomatedBrowseoffer("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.four){
                            APICall.sort=APICall.bookingSort("2","desc");
                            APICall.bookingAutomatedBrowseoffer("en","10","","1",APICall.filter,APICall.sort,BeautyMainPage.context,APICall.layout);
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });


        //----------- wait for paid----
//        APICall.bookingAutomatedBrowse("en","10","","1",APICall.filter,"",BeautyMainPage.context,R.layout.accept_reservation_layout);
//        service_select.setAdapter(MyReservationFragment.reservationsAdapter);

        if (MyReservationFragment.filtercheck==false) {
            APICall.bookingAutomatedBrowseoffer("en", "10", MyReservationFragment.serviceId, "1", "", "", BeautyMainPage.context, APICall.layout);
        }else {
            MyReservationFragment.filtercheck=false;
        }
        service_select.setAdapter(MyReservationFragment.reservationsAdapter2);
        return view;
    }

}
