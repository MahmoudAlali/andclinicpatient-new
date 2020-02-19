package com.dcoret.beautyclient.Fragments.Points;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.R;

public class PointsFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
     float ydata[]={25f,20f,30f,15f};
     String xdata[]={"current","spend","out","30day out"};
    public static TextView currentPoints,usedPoints,endedPoints,expiredPoints ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.points_layout, container, false);


        BeautyMainPage.FRAGMENT_NAME="PointsFragment";

        //        Toolbar toolbar;
//        toolbar=view.findViewById(R.id.toolbarm);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((AppCompatActivity)ProviderMainPage.context).onBackPressed();
//            }
//        });

//        barChart=view.findViewById(R.id.chart);
//        pieChart=view.findViewById(R.id.chart);

//        barChart.setData(chart("Points",barChart));

//        pieChart.setContentDescription("Points");
//
//        pieChart.setRotationEnabled(true);
//        pieChart.setHoleRadius(25f);
//        pieChart.setTransparentCircleAlpha(0);
//        pieChart.setCenterText("Points");
//        addDataSet(pieChart);
        currentPoints=view.findViewById(R.id.current_points);
        usedPoints=view.findViewById(R.id.used_points);
        endedPoints=view.findViewById(R.id.ended_points);
        expiredPoints=view.findViewById(R.id.expired_points);
        //pointAdapter= new PointAdapter(this.getContext());
        APICall.GetPointsDetails(BeautyMainPage.context);
        return view;
    }



    public static void setData(String used, String ter, String terInMnth, String cur)
    {

        currentPoints.setText(currentPoints.getText()+cur);
        expiredPoints.setText(expiredPoints.getText() + terInMnth);
        endedPoints.setText(endedPoints.getText() + ter);
        usedPoints.setText(usedPoints.getText() + used);

    }

}
