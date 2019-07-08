package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.ReservationDialog;
import com.dcoret.beautyclient.DataClass.BestOfferItem;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.Fragments.ShoppingCartFragment;
import com.dcoret.beautyclient.R;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
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
        DecimalFormat df = new DecimalFormat("0.00");
        float old_prc=Float.parseFloat(Double.parseDouble(bestOfferItems.get(position).getOld_price())+"");
        old_prc = Float.parseFloat(df.format(old_prc));
        float new_prc=Float.parseFloat(Double.parseDouble(bestOfferItems.get(position).getNew_price())+"");
        new_prc = Float.parseFloat(df.format(new_prc));
        float tot_dis=Float.parseFloat(Double.parseDouble(bestOfferItems.get(position).getTotal_discount())+"");
        tot_dis = Float.parseFloat(df.format(tot_dis));


        ((Item)holder).pack_code.setText("#"+bestOfferItems.get(position).getPack_code());
        ((Item)holder).pro_name.setText(bestOfferItems.get(position).getProvider_name());
        ((Item)holder).ser_count.setText(bestOfferItems.get(position).getService_count());
        ((Item) holder).old_price.setText(old_prc+"");
        ((Item) holder).new_price.setText(new_prc+"");
        ((Item) holder).total_dis.setText("dis: "+tot_dis+"%");
        ((Item)holder).old_price.setPaintFlags(((Item)holder).old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        try {

            ((OffersAdapter.Item) holder).textView.setText(offers.get(position).getName());
            ((Item) holder).pro_name.setText(offers.get(position).getServices()[0].getProvider_name());
            ((Item) holder).price.setText(offers.get(position).getPrice() + "");
            ((Item) holder).rating.setText(offers.get(position).getRate() + "");
            OFFER_RESERVATION_TYPE.add(offers.get(position).getOffer_type());
            ((OffersAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


        }catch (Exception e){
//        Toast.makeText(context,e.getMessage()+"",Toast.LENGTH_LONG).show();
        }
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
    public static class Item extends RecyclerView.ViewHolder {
        TextView textView,pack_code,rating,price,pro_name,reserv_offer,ser_count,total_dis,new_price,old_price;
        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
            pro_name = itemView.findViewById(R.id.pro_name);
            reserv_offer = itemView.findViewById(R.id.reserv_offer);
            rating = itemView.findViewById(R.id.rank);
            price = itemView.findViewById(R.id.price);
            ser_count = itemView.findViewById(R.id.ser_count);
            pack_code = itemView.findViewById(R.id.packCode);
            total_dis = itemView.findViewById(R.id.total_dis);
            new_price = itemView.findViewById(R.id.new_price);
            old_price = itemView.findViewById(R.id.old_price);
        }
    }
//    static String token_provider="enTW789hyvs:APA91bGyfEMJKFEZ6NuhvCFAg_Abx6rB9kmdMEW6vPnGRSKJJ3BQNDaKtISf59GuWyS7tBWNdT-ZLOkn3-Nz3IzHZfB911syZzHsRfjk64KGcfG0FAQ0wEAxuFc9buspiowZJmJsQ7lP";
//    static String api_key_header_value_provider = "Key=AAAAAAXCVwM:APA91bFiJYACTd-gZPdHrymnwcypg2IQ6JfSdTqUWqt95VANEyTe7H8NAn2nUnwfoau63QdJTXrxpLR5ZyDQ2-PL6TfPCCH7JJrocD1-SkfE7qrfMIqZvu09ICnD72OqAzuB-o85WawO";
//
//    static void sendnotification_provider(Context context,String title,String body ,String action1,String action2) {
//
//        try{
//            RequestQueue queue = Volley.newRequestQueue(context);
//            String url = "https://fcm.googleapis.com/fcm/send";
//            JSONObject data1 = new JSONObject();
//            data1.put("title", title);
//            data1.put("body", body);
//            data1.put("action1" ,action1);
//            data1.put("action2" ,action2);
//            JSONObject notification_data = new JSONObject();
//            notification_data.put("to",token_provider);
//            notification_data.put("data", data1);
//            System.out.println(notification_data);
//
//            JsonObjectRequest request = new JsonObjectRequest(url, notification_data, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }){
//                @Override
//                public Map<String, String> getHeaders() {
//                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json");
//                    headers.put("Authorization", api_key_header_value_provider);
//                    System.out.println("Send to provider");
//                    return headers;
//                }
//            };
//
//            queue.add(request);
//            System.out.println(request);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }


}
