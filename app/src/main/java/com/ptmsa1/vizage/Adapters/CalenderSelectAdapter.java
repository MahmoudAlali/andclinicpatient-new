package com.ptmsa1.vizage.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.DataModel.DataOffer;
import com.ptmsa1.vizage.DataModel.DateClass;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class CalenderSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
    static  LinearLayout postionSelected,tmpSelected;

    ArrayList<DateClass> dateClasses;
    public CalenderSelectAdapter(Context context, ArrayList<DateClass> dateClasses){
        this.context=context;
        this.dateClasses=dateClasses;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row;

            row = inflater.inflate(R.layout.indvisual_booking_layout, parent, false);
            CalenderSelectAdapter.Item item = new CalenderSelectAdapter.Item(row);

        return item;
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).dayOfMonth.setText(dateClasses.get(position).getDayOfMonth()+"");
        ((Item)holder).dayOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupOfferAdapter.dateShow=((Item)holder).dayOfMonth.getText().toString();
                GroupOfferAdapter.dateShowed.setText(GroupOfferAdapter.dateShow);
                GroupOfferAdapter.dialog.dismiss();

            }
        });
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
