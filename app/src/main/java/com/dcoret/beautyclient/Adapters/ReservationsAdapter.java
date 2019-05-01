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

import com.dcoret.beautyclient.Activities.BeautyMainPage_2;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Activities.Reservation;
import com.dcoret.beautyclient.Activities.ReservationDetails;

import java.util.ArrayList;

public class ReservationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
    ArrayList <DataService> services;
    public ReservationsAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public ReservationsAdapter(Context context, ArrayList <DataService> services){
        this.context=context;
        this.services=services;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.reservation_layout,parent,false);
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
        ((Item)holder).textView.setText(services.get(position).getName());
        ((ReservationsAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
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

        ((Item) holder).cancel_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(services.get(position).isIsoffer()){
                    AlertDialog.Builder dialog;
                    try {
                       dialog= new AlertDialog.Builder(Reservation.context);
                    }catch (Exception e){
                        dialog= new AlertDialog.Builder(BeautyMainPage_2.context);

                    }
                          dialog.setTitle("Cancel Reservation")
                            .setMessage("سوف يتم الغاء كامل العرض و حذف الخدمات الاخرى المتعلقة به,هل انت متأكد ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int id=services.get(position).getOfferid();
                                    int size=services.size();
                                    for (int i=0; i<services.size();i++) {
                                        if (services.get(i).isIsoffer() && id == services.get(i).getOfferid()) {
                                            services.remove(i);
                                            if(size>services.size()){
                                                size=services.size();
                                                i=0;
                                            }

                                        }
                                    }
                                    if(services.get(0).getOfferid()==id){
                                        services.remove(0);
                                    }
                                    notifyDataSetChanged();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {
                    AlertDialog.Builder dialog;
                    try {
                        dialog= new AlertDialog.Builder(Reservation.context);
                    }catch (Exception e){
                        dialog= new AlertDialog.Builder(BeautyMainPage_2.context);

                    }
                           dialog .setTitle("Cancel Reservation")
                            .setMessage("هل انت متأكد انك تريد الغاء الحجز ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    services.remove(position);
                                    notifyDataSetChanged();




                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });


            }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

            TextView textView,cancel_re,edit_re;

        public Item(View itemView, MyClickListener listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.rname);
            cancel_re=itemView.findViewById(R.id.cancel_re);
            edit_re=itemView.findViewById(R.id.edit_re);

//            more_btn=itemView.findViewById(R.id.more_btn);
            this.listener = listener;

//            resrv_btn.setOnClickListener();
//            more_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.reserv_btn:
                    listener.resrve(this.getLayoutPosition());
                    break;
//                case R.id.more_btn:
//                    listener.more(this.getLayoutPosition());
//                    break;
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
