package com.dcoret.beautyclient;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabTwo extends Fragment {

    public TabTwo() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab_three,container,false);
        return view;
    }


}
