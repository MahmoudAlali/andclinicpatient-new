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

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.IndividualBooking;
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
    static  LinearLayout postionSelected;

    ArrayList<DateClass> dateClasses;
    public CalenderAdapter(Context context, ArrayList<DateClass> dateClasses){
        this.context=context;
        this.dateClasses=dateClasses;
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
                IndividualBooking.idforemp=-1;
                IndividualBooking.alltimesSpinner.setSelection(0);

                postionSelected=((Item)holder).calLayout;
                try{
                    postionSelected.setBackgroundResource(R.drawable.border_gray);
                }catch (Exception e){
                    e.printStackTrace();
                }
                IndividualBooking.startdate= ((Item)holder).dayOfMonth.getText().toString();

                ((Item)holder).calLayout.setBackgroundResource(R.drawable.border_selected);
                Log.e("bdb_id",IndividualBooking.bdb_id);
                Log.e("bdb_id",((Item)holder).dayOfMonth.getText().toString());
                IndividualBooking.showEmp.removeAllViews();
                IndividualBooking.dateSelected=((Item)holder).dayOfMonth.getText().toString();
                APICall.searchBooking("15",((Item)holder).dayOfMonth.getText().toString(),context);
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
