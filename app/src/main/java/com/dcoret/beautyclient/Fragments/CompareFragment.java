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


import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;

public class CompareFragment extends Fragment  {

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_compartion, container, false);
        BeautyMainPage.FRAGMENT_NAME="COMPAREFRAGMENT";
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
