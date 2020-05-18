package com.ptmsa1.clinicclient.Fragments.Points;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.R;

public class PointsMainFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    TextView myPoint,pointDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.points_main_layout, container, false);
        myPoint = view.findViewById(R.id.service_Sw);
        pointDetails = view.findViewById(R.id.offer_sw);


        pointDetails.setBackgroundResource(android.R.color.transparent);
        myPoint.setBackgroundResource(R.drawable.shadow_service_tab);

        fragment=new PointsFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment2, fragment);
        fragmentTransaction.commit();


        //----------- back btn process------
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) BeautyMainPage.context).onBackPressed();
            }
        });


        pointDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPoint.setBackgroundResource(android.R.color.transparent);

                pointDetails.setBackgroundResource(R.drawable.shadow_service_tab);
              fragment=new PointsSecondFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment2, fragment);
                fragmentTransaction.commit();
            }
        });



        myPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointDetails.setBackgroundResource(android.R.color.transparent);
                myPoint.setBackgroundResource(R.drawable.shadow_service_tab);
                fragment=new PointsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment2, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
