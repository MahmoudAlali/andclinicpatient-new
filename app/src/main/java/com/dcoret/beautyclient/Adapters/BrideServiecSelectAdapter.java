package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dcoret.beautyclient.R;


import java.util.ArrayList;

public class BrideServiecSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


   public static Context context;
  public static ArrayList<String> names;
   public static ArrayList<String> prices;
    int id;
    double sum=0d;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.bride_service_select_layout,parent,false);
        Item item=new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//            Log.d("names", names.toString());
            ((Item) holder).textView.setText(names.get(position) + "");
            ((Item) holder).price.setText(prices.get(position) + "");
            ((Item) holder).select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        sum=Double.parseDouble(((Item) holder).price.getText().toString());
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class Item extends RecyclerView.ViewHolder {

        TextView textView;
        TextView price;
        CheckBox select;
        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.service_name);
            price=itemView.findViewById(R.id.price);
            select=itemView.findViewById(R.id.select);

        }
    }
}
