package com.dcoret.beautyclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    String items[];
    public OffersAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.offer_layout,parent,false);
        OffersAdapter.Item item=new OffersAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((OffersAdapter.Item)holder).textView.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
    public class Item extends RecyclerView.ViewHolder {

        TextView textView;

        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
        }
    }

}
