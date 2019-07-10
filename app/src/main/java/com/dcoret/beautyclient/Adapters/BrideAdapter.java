package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.Fragments.ShoppingCartFragment;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Service.PushNotifications;

public class BrideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
    public BrideAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public BrideAdapter(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    public BrideAdapter(Context context, DataOffer[] offers, boolean grid){
        this.context=context;
        this.offers=offers;
        this.grid=grid;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row;

            row = inflater.inflate(R.layout.offer_layout_example, parent, false);
            BrideAdapter.Item item = new BrideAdapter.Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
//        ((BrideAdapter.Item)holder).textView.setText(offers[position].getName());
//                try {
//                ((Item) holder).reserv_offer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for (int i=0;i<offers[position].getServices().length;i++) {
//                            ShoppingCartFragment.dataServices.add(offers[position].getServices()[i]);
////                            Reservation.services.add(offers[position].getServices()[i]);
//                        }
//                        Toast.makeText(context,"Offers Reserved",Toast.LENGTH_LONG).show();
//                        new PushNotifications().sendnotification_provider(context,"offers","تم حجز عرض من قبل احد الزبائن","accept","cancel");
//                    }
//                });
//            }catch (Exception e){
//
//            }
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
        //-------------- variables-----------
        TextView textView,rating,reserv_offer;

        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
            reserv_offer = itemView.findViewById(R.id.reserv_offer);
        }
    }
}
