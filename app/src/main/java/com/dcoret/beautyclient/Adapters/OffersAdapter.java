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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.ReservationDialog;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.DataClass.BestOfferItem;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.Fragments.ShoppingCartFragment;
import com.dcoret.beautyclient.R;
//import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public  class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    ArrayList<DataOffer> offers=new ArrayList<>();
    String name;
    ArrayList<ServiceItem> serviceItems;
    ArrayList<BestOfferItem> bestOfferItems;
    ArrayList<String> OFFER_RESERVATION_TYPE=new ArrayList<>();
    public OffersAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
//    public OffersAdapter(Context context, ArrayList<ServiceItem> serviceItems){
//        this.context=context;
//        this.serviceItems=serviceItems;
//    }

    public OffersAdapter(Context context, ArrayList<BestOfferItem> bestOfferItems){
        this.context=context;
        this.bestOfferItems=bestOfferItems;
    }
    public OffersAdapter(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    public OffersAdapter(Context context, ArrayList<DataOffer> offers, boolean grid, String name){
        this.context=context;
        this.offers=offers;
        this.grid=grid;
        this.name=name;
        new OffersData(offers);


    }


    JSONObject object;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View row;

//        object=APICall.browseclass(Offers.context);

        if(grid==false) {
             row = inflater.inflate(R.layout.offers_layout_last, parent, false);
        }else {
             row = inflater.inflate(R.layout.offers_layout_last, parent, false);
        }
            OffersAdapter.Item item = new OffersAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//---------------------for prices decimal format-----------------
        try {
            float old_prc = Float.parseFloat(nFormate(Double.parseDouble(bestOfferItems.get(position).getOld_price()) ));
            float new_prc = Float.parseFloat(nFormate(Double.parseDouble(bestOfferItems.get(position).getNew_price()) ));
            float tot_dis = Float.parseFloat(nFormate(Double.parseDouble(bestOfferItems.get(position).getTotal_discount()) ));

//        ((Item)holder).pack_code.setText("#"+bestOfferItems.get(position).getPack_code());
            ((Item) holder).pro_name.setText(bestOfferItems.get(position).getProvider_name());
//        ((Item)holder).ser_count.setText(bestOfferItems.get(position).getService_count());
            ((Item) holder).old_price.setText(old_prc + "");
            ((Item) holder).new_price.setText(new_prc + "");
            ((Item) holder).total_dis.setText(nFormatePercent(tot_dis) + "% on " + bestOfferItems.get(position).getService_count() + " services");
            ((Item) holder).old_price.setPaintFlags(((Item) holder).old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ((Item) holder).info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONArray jsonArray = bestOfferItems.get(position).getSersup_ids();
//               StringBuilder infoItem=new StringBuilder();
                    try {
                        PopupMenu popup = new PopupMenu(context, ((Item) holder).info);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String bdb_name_ar = jsonObject.getString("bdb_name_ar");
                            popup.getMenu().add(bdb_name_ar);
//                       if (i==jsonArray.length()-1){
//                           infoItem.append(bdb_name_ar);
//                       }else {
//                           infoItem.append(bdb_name_ar+"\n");
//                       }
                        }

                        popup.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                new BubbleShowCaseBuilder((AppCompatActivity)context) //Activity instance
//                        .title(infoItem.toString()) //Any title for the bubble view
//                        .targetView(((Item)holder).info) //View to point out
//                        .show(); //Display the ShowCase
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


//        try {
//
//            ((OffersAdapter.Item) holder).textView.setText(offers.get(position).getName());
//            ((Item) holder).pro_name.setText(offers.get(position).getServices()[0].getProvider_name());
//            ((Item) holder).price.setText(offers.get(position).getPrice() + "");
//            ((Item) holder).rating.setText(offers.get(position).getRate() + "");
//            OFFER_RESERVATION_TYPE.add(offers.get(position).getOffer_type());
//            ((OffersAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//        }catch (Exception e){
////        Toast.makeText(context,e.getMessage()+"",Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public int getItemCount() {
        try {
            return bestOfferItems.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }


    }
    public static String nFormate(double d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(2);
        String st= nf.format(d);
        return st;
    }
    public static String nFormatePercent(double d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(0);
        String st= nf.format(d);
        return st;
    }
    public static class Item extends RecyclerView.ViewHolder {
        TextView textView,pack_code,rating,price,pro_name,reserv_offer,ser_count,total_dis,new_price,old_price;
        ImageView info;
        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
            pro_name = itemView.findViewById(R.id.pro_name);
            info = itemView.findViewById(R.id.info);
            reserv_offer = itemView.findViewById(R.id.reserv_offer);
            rating = itemView.findViewById(R.id.rank);
            price = itemView.findViewById(R.id.price);
//            ser_count = itemView.findViewById(R.id.ser_count);
//            pack_code = itemView.findViewById(R.id.packCode);
            total_dis = itemView.findViewById(R.id.total_dis);
            new_price = itemView.findViewById(R.id.new_price);
            old_price = itemView.findViewById(R.id.old_price);
        }
    }
}
