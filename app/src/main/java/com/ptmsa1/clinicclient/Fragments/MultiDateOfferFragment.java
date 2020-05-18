package com.ptmsa1.clinicclient.Fragments;

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

import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Adapters.CalenderMultiSelectAdapter;
import com.ptmsa1.clinicclient.DataModel.DateClass;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;

public class MultiDateOfferFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;

   public static RecyclerView recyclerView,review;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.multi_date_offer_layout, container, false);

        recyclerView=view.findViewById(R.id.recview);
        review=view.findViewById(R.id.recycleview);

        ArrayList<DateClass> groupOfferClasses =new ArrayList<>();
        groupOfferClasses.add(new DateClass("Saturday","27-9-2019"));
        groupOfferClasses.add(new DateClass("Sunday","28-9-2019"));
        groupOfferClasses.add(new DateClass("monday","29-9-2019"));



        CalenderMultiSelectAdapter calenderSelectAdapter=new CalenderMultiSelectAdapter(getActivity().getApplicationContext(),groupOfferClasses);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(calenderSelectAdapter);
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
