package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.dcoret.beautyclient.API.APICall;
//import com.dcoret.beautyclient.Activities.AddOffersInside.SingleDateMultiClientOfferBooking;
//import com.dcoret.beautyclient.Activities.AddOffersInside.SingleDateOfferBooking;
import com.dcoret.beautyclient.Activities.MultiDateOfferBooking;
import com.dcoret.beautyclient.DataClass.OfferModel;
import com.dcoret.beautyclient.R;
import com.paytabs.paytabs_sdk.http.APIClient;

import java.util.ArrayList;

public class OfferBookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    String items[];
    ArrayList<OfferModel> offerModels;
    Boolean layout;
    public OfferBookingAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }

    public OfferBookingAdapter(Context context, ArrayList<OfferModel> offerModels){
        this.context=context;
        this.offerModels=offerModels;
    }

    public OfferBookingAdapter(Context context, String items[], Boolean layout){
        this.context=context;
        this.items=items;
        this.layout=layout;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.offer_book_layout, parent, false);
        OfferBookingAdapter.Item item=new OfferBookingAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,  int position1) {
        final int position=position1;

        ((Item) holder).old_price.setPaintFlags(((Item) holder).old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        ((Item) holder).bid_time.setText("Bid Time:"+ APICall.convertToArabic(offerModels.get(position).getNum_of_times()));
        try {
            ((Item) holder).discount.setText(APICall.convertToArabic(offerModels.get(position).getDiscount().substring(0,3)+"%"));
        }catch (Exception e){
            ((Item) holder).discount.setText(APICall.convertToArabic(offerModels.get(position).getDiscount()+"%"));
        }
        ((Item) holder).new_price.setText(APICall.convertToArabic(offerModels.get(position).getNewPrice())+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal));
        ((Item) holder).old_price.setText(APICall.convertToArabic(offerModels.get(position).getOldPrice())+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal));
        ((Item) holder).offer_start.setText(((AppCompatActivity)context).getResources().getString(R.string.start_at)+APICall.convertToArabic(offerModels.get(position).getBdb_offer_start()));
        ((Item) holder).end_offer.setText(((AppCompatActivity)context).getResources().getString(R.string.valid_until)+APICall.convertToArabic(offerModels.get(position).getBdb_offer_end()));
        String serviecs="";
//        for (int i=0;i<2;i++) {
        if (Integer.parseInt(offerModels.get(position).getService_count())>=2){
            serviecs=offerModels.get(position).getSersup_ids().get(0).getBdb_name()+",..";
        }else {
            serviecs=offerModels.get(position).getSersup_ids().get(0).getBdb_name();
        }
//        }

        Log.e("discount",offerModels.get(position).getDiscount());
        Log.e("Status",offerModels.get(position).getBdb_offer_status());

//        if (offerModels.get(position).getBdb_offer_status().equals("0")){
//            ((Item) holder).background_offer.setBackgroundResource(R.color.graydark);
//            ((Item) holder).book.setVisibility(View.GONE);
//        }else {
//            ((Item) holder).background_offer.setBackgroundResource(R.drawable.shadow_blue_c6);
//            ((Item) holder).book.setVisibility(View.VISIBLE);
//        }

//        ((Item) holder).offer_type.setTextColor(Color.RED);
        if (offerModels.get(position).getBdb_offer_type().equals("1") ||offerModels.get(position).getBdb_offer_type().equals("4")) {
            ((Item) holder).offer_type.setText(((AppCompatActivity)context).getResources().getString(R.string.single_offer_same));
        }else if (offerModels.get(position).getBdb_offer_type().equals("2") ||offerModels.get(position).getBdb_offer_type().equals("5")) {
            ((Item) holder).offer_type.setText(((AppCompatActivity)context).getResources().getString(R.string.single_offer_multi));
        }else if (offerModels.get(position).getBdb_offer_type().equals("3") ||offerModels.get(position).getBdb_offer_type().equals("6")) {
            ((Item) holder).offer_type.setText(((AppCompatActivity)context).getResources().getString(R.string.group_offer));
        }

        ((Item) holder).book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OfferType",offerModels.get(position).getBdb_offer_type());

//                if (offerModels.get(position).getBdb_offer_type().equals("2")
//                        || offerModels.get(position).getBdb_offer_type().equals("5")){
//                    Intent  intent=new Intent(context, MultiDateOfferBooking.class);
//                    intent.putExtra("postion",position);
//                    intent.putExtra("offertype",offerModels.get(position).getBdb_offer_type());
//                    ((AppCompatActivity)context).startActivity(intent);
//
//                }else if (offerModels.get(position).getBdb_offer_type().equals("1")
//                        || offerModels.get(position).getBdb_offer_type().equals("4")){
////                    Intent  intent=new Intent(context, SingleDateOfferBooking.class);
//                    intent.putExtra("postion",position);
//                    intent.putExtra("offertype",offerModels.get(position).getBdb_offer_type());
//                    ((AppCompatActivity)context).startActivity(intent);
//                }else if (offerModels.get(position).getBdb_offer_type().equals("3")
//                        || offerModels.get(position).getBdb_offer_type().equals("6")){
//
//                    Intent  intent=new Intent(context, SingleDateMultiClientOfferBooking.class);
//                    intent.putExtra("postion",position);
//                    intent.putExtra("offertype",offerModels.get(position).getBdb_offer_type());
//                    ((AppCompatActivity)context).startActivity(intent);
//                }
            }
        });
        ((Item) holder).name.setText(serviecs);
        ((Item) holder).info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.menu_example, popup.getMenu());
                for (int i=0;i<offerModels.get(position).getSersup_ids().size();i++){
                    popup.getMenu().add(offerModels.get(position).getSersup_ids().get(i).getBdb_name());
                }

                popup.show();
            }
        });

        if (offerModels.get(position).getBdb_is_old_on().equals("0")){
            ((Item)holder).old.setText("طفلة");
        }else if (offerModels.get(position).getBdb_is_old_on().equals("1")){
            ((Item)holder).old.setText("بالغة");
        }else if (offerModels.get(position).getBdb_is_old_on().equals("2")){
            ((Item)holder).old.setText("لا فرق");
        }

        if (offerModels.get(position).getBdb_offer_place().equals("0")){
            ((Item)holder).place.setText("صالون");
        }else if (offerModels.get(position).getBdb_offer_place().equals("1")){
            ((Item)holder).place.setText("منزل العميلة");
        }else if (offerModels.get(position).getBdb_offer_place().equals("2")){
            ((Item)holder).place.setText("صالة");
        }else if (offerModels.get(position).getBdb_offer_place().equals("3")){
            ((Item)holder).place.setText("فندق");
        }


//        ((Item)holder).

    }

    @Override
    public int getItemCount() {
        return offerModels.size();
    }
    public class Item extends RecyclerView.ViewHolder {

        TextView name,bid_time,book,old ,place,offer_type,offer_start,end_offer,old_price,new_price,discount,delete,edit;

        ImageView info;
        LinearLayout background_offer;
        public Item(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            old=itemView.findViewById(R.id.old);
            place=itemView.findViewById(R.id.place);
            offer_type=itemView.findViewById(R.id.offer_type);
            bid_time=itemView.findViewById(R.id.bid_time);
            end_offer=itemView.findViewById(R.id.offer_end);
            offer_start=itemView.findViewById(R.id.offer_start);
            book=itemView.findViewById(R.id.stop);
            old_price=itemView.findViewById(R.id.old_price);
            new_price=itemView.findViewById(R.id.new_price);
            discount=itemView.findViewById(R.id.discount);
            background_offer=itemView.findViewById(R.id.background_offer);
            info=itemView.findViewById(R.id.info);



        }
    }

}