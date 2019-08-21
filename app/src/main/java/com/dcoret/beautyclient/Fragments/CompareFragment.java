package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.R;

public class CompareFragment extends Fragment  {
     String places2="",places1="",places3="";
    TextView srname1,srname2,srname3,prName1,prName2,prName3,ev1,ev2,ev3,place1,place2,place3,price1,price2,price3;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_compartion, container, false);
        BeautyMainPage.FRAGMENT_NAME="COMPAREFRAGMENT";

        srname1=view.findViewById(R.id.sr1);
        srname2=view.findViewById(R.id.sr2);
        srname3=view.findViewById(R.id.sr3);
        prName1=view.findViewById(R.id.pr1);
        prName2=view.findViewById(R.id.pr2);
        prName3=view.findViewById(R.id.pro3);
        ev1=view.findViewById(R.id.ev1);
        ev2=view.findViewById(R.id.ev2);
        ev3=view.findViewById(R.id.ev3);
        place1=view.findViewById(R.id.place1);
        place2=view.findViewById(R.id.place2);
        place3=view.findViewById(R.id.place3);
        price1=view.findViewById(R.id.loc1);
        price2=view.findViewById(R.id.loc2);
        price3=view.findViewById(R.id.price3);



        srname1.setText(ServicesAdapter.srName1);
        srname2.setText(ServicesAdapter.srName2);
        srname3.setText(ServicesAdapter.srName3);
        prName1.setText(ServicesAdapter.spname1);
        prName2.setText(ServicesAdapter.spname2);
        prName3.setText(ServicesAdapter.spname3);
        ev1.setText(ServicesAdapter.ev1);
        ev2.setText(ServicesAdapter.ev2);
        ev3.setText(ServicesAdapter.ev3);


        //----------------------------------------------
        if (ServicesAdapter.bdb_ser_salon.equals("1")){
            places1=places1+"salon";
        }
        if (ServicesAdapter.bdb_hotel.equals("1")){
            places1=places1+", hotel";
        }
        if (ServicesAdapter.bdb_ser_hall.equals("1")){
            places1=places1+", hall";
        }
        if (ServicesAdapter.bdb_ser_home.equals("1")){
            places1=places1+", home";
        }
        //----------------------------------------------
        if (ServicesAdapter.bdb_ser_salon1.equals("1")){
            places2=places2+", salon";
        }
        if (ServicesAdapter.bdb_hotel1.equals("1")){
            places2=places2+", hotel";
        }
        if (ServicesAdapter.bdb_ser_hall1.equals("1")){
            places2=places2+", hall";
        }
        if (ServicesAdapter.bdb_ser_home1.equals("1")){
            places2=places2+", home";
        }
        //----------------------------------------------
        if (ServicesAdapter.bdb_ser_salon2.equals("1")){
            places3=places3+", salon";
        }
        if (ServicesAdapter.bdb_hotel2.equals("1")){
            places3=places3+", hotel";
        }
        if (ServicesAdapter.bdb_ser_hall2.equals("1")){
            places3=places3+", hall";
        }
        if (ServicesAdapter.bdb_ser_home2.equals("1")){
            places3=places3+", home";
        }

        place1.setText(places1);
        place2.setText(places2);
        place3.setText(places3);
        price1.setText(ServicesAdapter.price1);
        price2.setText(ServicesAdapter.price2);
        price3.setText(ServicesAdapter.price3);
        ServicesAdapter.srName1="";
        ServicesAdapter.srName3="";
        ServicesAdapter.srName2="";
        ServicesAdapter.spname1="";
        ServicesAdapter.spname2="";
        ServicesAdapter.spname3="";
        ServicesAdapter.ev1="";
        ServicesAdapter.ev2="";
        ServicesAdapter.ev3="";
        ServicesAdapter.place1="";
        ServicesAdapter.place2="";
        ServicesAdapter.place3="";
        ServicesAdapter.price1="";
        ServicesAdapter.price2="";
        ServicesAdapter.price3="";


        Log.d("doback",BeautyMainPage.FRAGMENT_NAME);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ServicesTabsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });
                return view;
    }

}
