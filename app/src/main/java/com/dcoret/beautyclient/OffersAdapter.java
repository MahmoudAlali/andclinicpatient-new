package com.dcoret.beautyclient;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.DataClass.DataOffer;

public class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
    public OffersAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public OffersAdapter(Context context,String items[],boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    public OffersAdapter(Context context, DataOffer[] offers,boolean grid){
        this.context=context;
        this.offers=offers;
        this.grid=grid;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row;
        if(grid==false) {
             row = inflater.inflate(R.layout.offer_layout_example, parent, false);
        }else {
             row = inflater.inflate(R.layout.offer_layout_example, parent, false);
        }


            OffersAdapter.Item item = new OffersAdapter.Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((OffersAdapter.Item)holder).textView.setText(offers[position].getName());
        ((OffersAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, OfferDetails.class);
                    intent.putExtra("offer_name",offers[position].getName());
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

            try {
                ((Item) holder).reserv_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0;i<offers[position].getServices().length;i++) {
                            ShoppingCart.dataServices.add(offers[position].getServices()[i]);
                            Reservation.services.add(offers[position].getServices()[i]);
                        }
                        Toast.makeText(context,"Offers Reserved",Toast.LENGTH_LONG).show();
                    }
                });

            }catch (Exception e){

            }








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
        try {
            return offers.length;
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView textView,rating,reserv_offer;


        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
//            rating = itemView.findViewById(R.id.rating);
            reserv_offer = itemView.findViewById(R.id.reserv_offer);
        }
    }

}
