package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.OfferDetails;
import com.dcoret.beautyclient.Activities.Reservation;
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
        if(grid==false) {
             row = inflater.inflate(R.layout.offer_layout_example, parent, false);
        }else {
             row = inflater.inflate(R.layout.offer_layout_example, parent, false);
        }


            BrideAdapter.Item item = new BrideAdapter.Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((BrideAdapter.Item)holder).textView.setText(offers[position].getName());
        ((BrideAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    Intent intent = new Intent(context, OfferDetails.class);
//                    intent.putExtra("offer_name",offers[position].getName());
//                    context.startActivity(intent);
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
                            ShoppingCartFragment.dataServices.add(offers[position].getServices()[i]);
                            Reservation.services.add(offers[position].getServices()[i]);
                        }
                        Toast.makeText(context,"Offers Reserved",Toast.LENGTH_LONG).show();
                        new PushNotifications().sendnotification_provider(context,"offers","تم حجز عرض من قبل احد الزبائن","accept","cancel");
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
