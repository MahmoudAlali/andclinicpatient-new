package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class PlaceServiceFragment extends Fragment {
//    LinearLayout services_tabs;
    LinearLayout bride_service,service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    ArrayList citiyname=new ArrayList();
    ArrayList price=new ArrayList();
    ArrayList rate=new ArrayList();
    Button ok;
    Spinner citiesSpinner,priceSpinner,rateSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_place_service_frag, container, false);
        service_hair=view.findViewById(R.id.service_hair);
        BeautyMainPage.FRAGMENT_NAME="PLACESERVICEFRAGMENT";
        Resources res = getResources();
        String placeervcetext=res.getResourceEntryName(R.string.PlaceService);
//        placeervcetext.append();
        citiyname.add(placeervcetext);
        for (int i = 0; i < BeautyMainPage.cities.size(); i++) {
            citiyname.add(BeautyMainPage.cities.get(i).getBdb_name());
        }
        citiesSpinner=view.findViewById(R.id.service_place);
        priceSpinner=view.findViewById(R.id.service_price);
        rateSpinner=view.findViewById(R.id.service_rate);
        ok=view.findViewById(R.id.ok);

        ArrayAdapter adapter=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_dropdown_item,citiyname);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(adapter);

        price.add(res.getResourceEntryName(R.string.ServicePrice));
        ArrayAdapter adapterprice=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_dropdown_item,price);
        adapterprice.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(adapterprice);

        rate.add(res.getResourceEntryName(R.string.ServiceRate));
        ArrayAdapter adapterrate=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_dropdown_item,rate);
        adapterrate.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        rateSpinner.setAdapter(adapterrate);

        toolbar= view.findViewById(R.id.toolbar);






//        service_hair.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment = new ServicesTabsFragment();
//                fm = getActivity().getFragmentManager();
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, fragment);
//                fragmentTransaction.commit();
//            }
//        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // If the navigation drawer is not open then open it, if its already open then close it.
//                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
//                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);

                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ServicesTabsFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
