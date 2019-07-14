package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DateClass;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CalenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;

    ArrayList<DateClass> dateClasses;
    public CalenderAdapter(Context context, ArrayList<DateClass> dateClasses){
        this.context=context;
        this.dateClasses=dateClasses;
    }
    public CalenderAdapter(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    public CalenderAdapter(Context context, DataOffer[] offers, boolean grid){
        this.context=context;
        this.offers=offers;
        this.grid=grid;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row;

            row = inflater.inflate(R.layout.indvisual_booking_layout, parent, false);
            CalenderAdapter.Item item = new CalenderAdapter.Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).dayOfMonth.setText(dateClasses.get(position).getDayOfMonth()+"");
        ((Item)holder).dayOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Item)holder).calLayout.setBackgroundResource(R.color.green);

            }
        });
        ((Item)holder).dayOfWeek.setText(dateClasses.get(position).getDayOfWeek());



//        ((CalenderAdapter.Item)holder).textView.setText(offers[position].getName());
//                try {
//                ((Item) holder).reserv_offer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for (int i=0;i<offers[position].getServices().length;i++) {
//                            ShoppingCartFragment.dataServices.add(offers[position].getServices()[i]);
////                            Reservation.services.add(offers[position].getServices()[i]);
//                        }
//                        Toast.makeText(context,"Offers Reserved",Toast.LENGTH_LONG).show();
//                        new PushNotifications().sendnotification_provider(context,"offers","تم حجز عرض من قبل احد الزبائن","accept","cancel");
//                    }
//                });
//            }catch (Exception e){
//
//            }
    }

    @Override
    public int getItemCount() {
        try {
            return dateClasses.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {
        //-------------- variables-----------
        TextView dayOfWeek,dayOfMonth,reserv_offer;
        LinearLayout calLayout;

        public Item(View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayofweek);
            dayOfMonth = itemView.findViewById(R.id.dayofmonth);
            calLayout = itemView.findViewById(R.id.calLayout);
        }
    }



}
