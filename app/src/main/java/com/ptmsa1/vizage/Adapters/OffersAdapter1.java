package com.ptmsa1.vizage.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.DataOffer;
import com.ptmsa1.vizage.DataExample.OffersData;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class OffersAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    ArrayList<DataOffer> offers=new ArrayList<>();
    String name;
    ArrayList<String> OFFER_RESERVATION_TYPE=new ArrayList<>();
    public OffersAdapter1(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public OffersAdapter1(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    public OffersAdapter1(Context context, ArrayList<DataOffer> offers, boolean grid, String name){
        this.context=context;
        this.offers=offers;
        this.grid=grid;
        this.name=name;
        new OffersData(offers);


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(BeautyMainPage.context);
            View row;
            row = inflater.inflate(R.layout.offers_layout_last, parent, false);

            OffersAdapter1.Item item = new OffersAdapter1.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

//        try {
//            ((OffersAdapter1.Item) holder).textView.setText(offers.get(position).getName());
//            ((Item) holder).pro_name.setText(offers.get(position).getServices()[0].getProvider_name());
//            ((Item) holder).price.setText(offers.get(position).getPrice() + "");
//            ((Item) holder).rating.setText(offers.get(position).getRate() + "");
//            OFFER_RESERVATION_TYPE.add(offers.get(position).getOffer_type());
//            ((OffersAdapter1.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    try {
//                    } catch (Exception e) {
//                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    @Override
    public int getItemCount() {
        try {
//            Log.d("Offersize",offers.size()+"");
            return offers.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView textView,rating,price,pro_name,reserv_offer;

        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
            pro_name = itemView.findViewById(R.id.pro_name);
            reserv_offer = itemView.findViewById(R.id.reserv_offer);
            rating = itemView.findViewById(R.id.rank);
            price = itemView.findViewById(R.id.price);
        }
    }
}
