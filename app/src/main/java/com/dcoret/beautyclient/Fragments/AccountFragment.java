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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;


public class AccountFragment extends Fragment  {
    Button add_del_site;
    public static boolean edit_flag=false;
    Toolbar toolbar;
    MapView mMapView;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    EditText e_bdb_mobile,e_bdb_name,e_bdb_email,e_pass,e_c_pass,old_pass;
    Button save,deleteaccount;
    TextView edit;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_manage_accounts_frag, container, false);


        toolbar=view.findViewById(R.id.toolbar);
        e_bdb_email=view.findViewById(R.id.e_bdb_email);
        e_bdb_name=view.findViewById(R.id.e_bdb_name);
        e_bdb_mobile=view.findViewById(R.id.e_bdb_mobile);
        e_pass=view.findViewById(R.id.pass);
        e_c_pass=view.findViewById(R.id.c_pass);
        old_pass=view.findViewById(R.id.old_pass);
        save=view.findViewById(R.id.save);
        edit=view.findViewById(R.id.edit);
        deleteaccount=view.findViewById(R.id.deleteaccount);


        old_pass.setEnabled(false);
        e_c_pass.setEnabled(false);
        e_pass.setEnabled(false);
        old_pass.setText("123456");
        e_c_pass.setText("123456");
        e_pass.setText("123456");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pass.setText("");
                e_c_pass.setText("");
                e_pass.setText("");
                old_pass.setEnabled(true);
                e_c_pass.setEnabled(true);
                e_pass.setEnabled(true);
                edit_flag=true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.deleteAccount("http://clientapp.dcoret.com/api/auth/user/deleteAccount",BeautyMainPage.context);
            }
        });


        APICall.detailsUser("http://clientapp.dcoret.com/api/auth/user/detailsUser",e_bdb_name,e_bdb_email,e_bdb_mobile,BeautyMainPage.context);
        BeautyMainPage.FRAGMENT_NAME="";
        BeautyMainPage.FRAGMENT_NAME="ACCOUNTFRAGMENT";
        add_del_site=view.findViewById(R.id.add_del_site);
        add_del_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MapFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        Log.d("fargmentname",BeautyMainPage.FRAGMENT_NAME);

        mMapView = view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_flag){
                if (e_pass.getText().toString().equals(e_c_pass.getText().toString())){
                    APICall.update_user("http://clientapp.dcoret.com/api/auth/user/updateUser",e_bdb_name.getText().toString(),e_bdb_email.getText().toString(),e_pass.getText().toString(),old_pass.getText().toString(),BeautyMainPage.context);
                }else {
                    APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.PsswordIncorrectAlert);
                }
                }else {
                    APICall.update_user("http://clientapp.dcoret.com/api/auth/user/updateUser",e_bdb_name.getText().toString(),e_bdb_email.getText().toString(),BeautyMainPage.context);

                }


            }
        });
//        ((BeautyMainPage) getActivity()).setOnBackPressedListener(this);
        return view;
    }

}
