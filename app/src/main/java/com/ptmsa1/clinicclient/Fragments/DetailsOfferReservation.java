package com.ptmsa1.clinicclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptmsa1.clinicclient.Adapters.DetailsOfferAdapter;
import com.ptmsa1.clinicclient.DataModel.DaitelsOfferReservationClass;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;

public class DetailsOfferReservation extends Fragment {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_details_offer_reservation, container, false);
        recyclerView=view.findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        ArrayList<DaitelsOfferReservationClass> daitelsOfferReservationClasses=new ArrayList<>();
        daitelsOfferReservationClasses.add(new DaitelsOfferReservationClass("","","","",null));
        daitelsOfferReservationClasses.add(new DaitelsOfferReservationClass("","","","",null));
        daitelsOfferReservationClasses.add(new DaitelsOfferReservationClass("","","","",null));
        daitelsOfferReservationClasses.add(new DaitelsOfferReservationClass("","","","",null));
        daitelsOfferReservationClasses.add(new DaitelsOfferReservationClass("","","","",null));

        DetailsOfferAdapter detailsOfferAdapter =new DetailsOfferAdapter(getActivity().getApplicationContext(), daitelsOfferReservationClasses);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(detailsOfferAdapter);
        Log.e("itemCount",detailsOfferAdapter.getItemCount()+"");

        return view;
    }
}
