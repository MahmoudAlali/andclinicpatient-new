package com.dcoret.beautyclient.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.ReservationDetails;
import com.dcoret.beautyclient.DataClass.DataReservation;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.GetCart;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;
import java.util.Timer;

public class BagReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String items[];
    ArrayList<GetCart> getCarts;
    RecyclerView.ViewHolder holder;

    public BagReservationAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public BagReservationAdapter(Context context, ArrayList<GetCart> getCarts){
        this.context=context;
        this.getCarts=getCarts;
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
        ((Item)holder).service_name.setText(getCarts.get(position).getBdb_service_name_en());
//        Timer timer=new Timer();
        new CountDownTimer(300000, 3000) {

            public void onTick(long millisUntilFinished) {
              int minuts= (int)millisUntilFinished / (60*1000);
              String m=minuts+"";
                ((Item)holder).remainingTime.setText(context.getResources().getText(R.string.reminingtime)+m+"minutes");
            }

            public void onFinish() {
               //-----------------delete service------
            }
        }.start();

        ((Item)holder).mDate.setText(getCarts.get(position).getBdb_start_date());

        ((Item)holder).mTime.setText(getCarts.get(position).getBdb_start_time());
        ((Item)holder).provider_name.setText(getCarts.get(position).getSupplier_name());
        ((Item)holder).reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.moveCartToBooking(getCarts.get(position).getBdb_id(),position,BeautyMainPage.context);
            }
        });
        ((Item)holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.deletFromCart(getCarts.get(position).getBdb_id(),position,BeautyMainPage.context);
            }
        });
//        ((Item)holder).service_place.setText(getCarts.get(position).get());

            }

    @Override
    public int getItemCount() {
        return getCarts.size();
    }

    public class Item extends RecyclerView.ViewHolder  {
        //-----------variables-------------
        TextView textView,cancel_re,edit_re;
        TextView provider_name,service_name,mDate,mTime,service_place,reserve,remainingTime,delete;

        public Item(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rname);
            remainingTime=itemView.findViewById(R.id.remainingTime);
            delete=itemView.findViewById(R.id.delete);
            provider_name=itemView.findViewById(R.id.provider_name);
            service_name=itemView.findViewById(R.id.service_name);
            mDate=itemView.findViewById(R.id.date);
            mTime=itemView.findViewById(R.id.time);
            service_place=itemView.findViewById(R.id.service_place);
            cancel_re=itemView.findViewById(R.id.cancel_re);
            edit_re=itemView.findViewById(R.id.edit_re);
            reserve=itemView.findViewById(R.id.reserve);
        }


    }

}
