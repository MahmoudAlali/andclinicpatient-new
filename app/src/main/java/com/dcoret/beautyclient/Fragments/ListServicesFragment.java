package com.dcoret.beautyclient.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Adapters.ListServicesAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.ListServiceModel;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class ListServicesFragment extends Fragment {


    RecyclerView recyclerView;
    public  static ListServicesAdapter servicesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_services_layout, container, false);
        recyclerView=view.findViewById(R.id.recycleview);
        BeautyMainPage.FRAGMENT_NAME="ListServicesFragment";
        BeautyMainPage.is_bride_service="0";

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
         servicesAdapter=new ListServicesAdapter(BeautyMainPage.context, APICall.itemArrayList);
        GridLayoutManager manager = new GridLayoutManager(BeautyMainPage.context,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);


        APICall.getBasicService(BeautyMainPage.context);

       Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
            }
        });





        return view;
    }


}
