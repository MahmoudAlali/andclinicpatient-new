package com.dcoret.beautyclient.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.ReservationDetails;
import com.dcoret.beautyclient.DataClass.DataReservation;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class BagReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String items[];
    RecyclerView.ViewHolder holder;

    public BagReservationAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.reservation_bag_layout,parent,false);
        BagReservationAdapter.Item item=new BagReservationAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

            }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder  {
        //-----------variables-------------
        TextView textView,cancel_re,edit_re;

        public Item(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rname);
            cancel_re=itemView.findViewById(R.id.cancel_re);
            edit_re=itemView.findViewById(R.id.edit_re);
        }


    }

}
