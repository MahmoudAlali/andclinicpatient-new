package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.CalenderAdapter;
import com.ptmsa1.vizage.Adapters.FixedDateItemsAdapter;
import com.ptmsa1.vizage.DataModel.DateClass;
import com.ptmsa1.vizage.DataModel.FixedDateItemsClass;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class FixedDateOffersFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    RecyclerView recyclerView,review;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fixed_date_offer_layout, container, false);



        ArrayList<DateClass> arrayList=new ArrayList<>();
        arrayList.add(new DateClass("Saturday","26/9/2019"));
        arrayList.add(new DateClass("Sunday","27//9/2019"));


        ArrayList<FixedDateItemsClass> itemsClasses=new ArrayList<>();
        itemsClasses.add(new FixedDateItemsClass("Service 1","emp1","09:00"));
        itemsClasses.add(new FixedDateItemsClass("Service 2","emp2","11:00"));
        itemsClasses.add(new FixedDateItemsClass("Service 3","emp3","10:00"));

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        CalenderAdapter calenderAdapter =new CalenderAdapter(getActivity().getApplicationContext(), arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(calenderAdapter);

        review=view.findViewById(R.id.review);
        FixedDateItemsAdapter fixedDateItemsAdapter=new FixedDateItemsAdapter(getActivity().getApplicationContext(),itemsClasses);
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        review.setLayoutManager(manager1);
        review.setAdapter(fixedDateItemsAdapter);
        Log.e("SizeA",calenderAdapter.getItemCount()+"");
        Log.e("SizeB",fixedDateItemsAdapter.getItemCount()+"");

        Button con=view.findViewById(R.id.con);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DetailsOfferReservation();
                fm = ((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
