package com.dcoret.beautyclient;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class TabThree extends Fragment {

    RecyclerView recyclerView;
    String[] items = {"Offer 1", "Offer 2", "Offer 3", "Offer 4", "Offer 5", "Offer 6", "Offer 7", "Offer 8", "Offer 9", "Offer 10"
            , "Offer 11", "Offer 12", "Offer 13", "Offer 14", "Offer 15", "Offer 16", "Offer 17", "Offer 18", "Offer 19", "Offer 20"
    };
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_two, container, false);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(MyReservations.context, 2));
        recyclerView.setAdapter(new OffersAdapter(MyReservations.context, items, true));

        return view;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            recyclerView = view.findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MyReservations.context));
                            recyclerView.setAdapter(new OffersAdapter(MyReservations.context, items, false));
                            return true;
                        case R.id.grid:
                            recyclerView = view.findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new GridLayoutManager(MyReservations.context, 2));
                            recyclerView.setAdapter(new OffersAdapter(MyReservations.context, items, true));
                            return true;
                    }

                    return false;
                }
            };
}
