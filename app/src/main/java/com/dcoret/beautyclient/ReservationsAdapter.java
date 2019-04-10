package com.dcoret.beautyclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReservationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
    public ReservationsAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
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
        ((Item)holder).textView.setText(items[position]);
        ((ReservationsAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, ReservationDetails.class);
                    intent.putExtra("reservation_name",items[position]);
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

            }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

            TextView textView,more_btn,rate;
            Button resrv_btn;
        public Item(View itemView, MyClickListener listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.rname);
            resrv_btn=itemView.findViewById(R.id.reserv_btn);
            more_btn=itemView.findViewById(R.id.more_btn);
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
                case R.id.more_btn:
                    listener.more(this.getLayoutPosition());
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
