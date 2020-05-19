package com.ptm.clinicpa.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.GroupOfferAdapter;
import com.ptm.clinicpa.DataModel.GroupOfferClass;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

public class GroupOfferFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.multi_date_offer_layout, container, false);


        ArrayList<GroupOfferClass> groupOfferClasses =new ArrayList<>();
        groupOfferClasses.add(new GroupOfferClass("Date","Service1"));
        groupOfferClasses.add(new GroupOfferClass("Date","Service1"));
        groupOfferClasses.add(new GroupOfferClass("Date","Service1"));

        recyclerView=view.findViewById(R.id.recycleview);
        GroupOfferAdapter groupOfferAdapter =new GroupOfferAdapter(getActivity().getApplicationContext(),groupOfferClasses);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(groupOfferAdapter);

        Button con=view.findViewById(R.id.con);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DetailsOfferReservation();
                fm = ((AppCompatActivity) BeautyMainPage.context).getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}
