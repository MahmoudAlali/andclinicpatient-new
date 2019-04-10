package com.dcoret.beautyclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShopCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String price[];
    String items[];
    public ShopCartAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public ShopCartAdapter(Context context,String items[],String[] price){
        this.context=context;
        this.items=items;
        this.price=price;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ShopCartAdapter.Item)holder).textView.setText(items[position]);
        ((Item)holder).price.setText(price[position]);
//        ((OffersAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(context, OfferDetails.class);
//                    context.startActivity(intent);
//                }catch (Exception e){
//                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        ((Item) holder).rating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog dialog=new Dialog(Offers.context);
//                dialog.setContentView(R.layout.rating_dialog);
//                dialog.setTitle("تقييم العرض");
//                dialog.show();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView textView,price;


        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.shop_name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
