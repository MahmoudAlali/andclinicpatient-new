package com.dcoret.beautyclient.Fragments.Points;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.PointAdapter;
import com.dcoret.beautyclient.R;


public class PointsSecondFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    float ydata[]={25f,20f,30f,15f};
    String xdata[]={"current","spend","out","30day out"};
    RecyclerView recyclerView;
    PointAdapter pointAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.points_second_layout, container, false);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        pointAdapter=new PointAdapter(BeautyMainPage.context, APICall.pointList);
        recyclerView.setAdapter(pointAdapter);
        BeautyMainPage.FRAGMENT_NAME="PointsFragment";

        pointAdapter.notifyDataSetChanged();
        APICall.GetPointsLog(BeautyMainPage.context,pointAdapter);
        return view;
    }

}
