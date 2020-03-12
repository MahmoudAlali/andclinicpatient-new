package com.ptmsa1.vizage.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptmsa1.vizage.DataModel.DataService;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class ShopCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String price[];
    String items[];
  static ArrayList<DataService> dataServices;
    public ShopCartAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public ShopCartAdapter(Context context,String items[],String[] price){
        this.context=context;
        this.items=items;
        this.price=price;
    }

    public ShopCartAdapter(Context context,ArrayList <DataService> dataServices){
        this.context=context;
        this.dataServices=dataServices;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.shopcart_layout, parent, false);
        ShopCartAdapter.Item item = new ShopCartAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ShopCartAdapter.Item)holder).textView.setText(dataServices.get(position).getName());
        ((Item)holder).price.setText(dataServices.get(position).getPrice()+"");

        ((Item) holder).cancel_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Integer> removeditem=new ArrayList();
                if(dataServices.get(position).isIsoffer()){

                }else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataServices.size();
    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView textView,price,cancel_re;


        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.shop_name);
            price = itemView.findViewById(R.id.price);
            cancel_re = itemView.findViewById(R.id.cancel_re);
        }
    }
}
