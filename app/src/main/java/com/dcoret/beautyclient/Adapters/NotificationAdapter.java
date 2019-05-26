package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcoret.beautyclient.R;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Boolean grid=false;
    String items[];
    public NotificationAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public NotificationAdapter(Context context,String items[],boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.notify_layout, parent, false);
        NotificationAdapter.Item item = new NotificationAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NotificationAdapter.Item)holder).textView.setText(items[position]);
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
//                ReservationDialog dialog=new ReservationDialog(Offers.context);
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

        TextView textView,rating;


        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.notyfication_main);
//            rating = itemView.findViewById(R.id.rating);
        }
    }
}
