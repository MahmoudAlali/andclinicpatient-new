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

public class ReservationsInsideOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
    ArrayList <DataService> services;
    ArrayList<DataReservation> reservations;
    public ReservationsInsideOfferAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public ReservationsInsideOfferAdapter(Context context, ArrayList <DataService> services){
        this.context=context;
        this.services=services;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.reservation_inside_offer_layout,parent,false);
        Item item=new Item(row, new MyClickListener() {
            @Override
            public void resrve(int p) {
                Toast.makeText(context,"ok",Toast.LENGTH_LONG).show();
            }
            @Override
            public void more(int p) {
                Toast.makeText(context,"ok",Toast.LENGTH_LONG).show();
            }
        });
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).textView.setText(items[position]);
        ((ReservationsInsideOfferAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, ReservationDetails.class);
                    intent.putExtra("reservation_name",services.get(position).getName());
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        //---------- Edit reservation listener--------------
        ((Item) holder).edit_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //---------- cancel reservation listener------------
        ((Item) holder).cancel_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(items.length==0){
                    AlertDialog.Builder dialog;
                    try {
                       dialog= new AlertDialog.Builder(BeautyMainPage.context);
                    }catch (Exception e){
                        dialog= new AlertDialog.Builder(BeautyMainPage.context);

                    }
                          dialog.setTitle("Cancel Reservation")
                            .setMessage("سوف يتم الغاء كامل العرض و حذف الخدمات الاخرى المتعلقة به,هل انت متأكد ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    int id = services.get(position).getOfferid();
//                                    int size = services.size();

                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
            }
        });


            }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

            TextView textView,cancel_re,edit_re;

        public Item(View itemView, MyClickListener listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.rname);
            cancel_re=itemView.findViewById(R.id.cancel_re);
            edit_re=itemView.findViewById(R.id.edit_re);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.reserv_btn:
                    listener.resrve(this.getLayoutPosition());
                    break;

                default:
                    break;
            }
        }
    }
    public interface MyClickListener {
        void resrve(int p);
        void more(int p);
    }
}
