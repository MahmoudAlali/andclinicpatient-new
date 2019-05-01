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

import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.Activities.OfferDetails;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class SearchAdapter   extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Boolean grid=false;
    String items[];
    ArrayList<DataService>  dataServices;
    public SearchAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public SearchAdapter(Context context,String items[],boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }
    public SearchAdapter(Context context, ArrayList<DataService> dataService, boolean grid){
        this.context=context;
       this.dataServices=dataService;
        this.grid=grid;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        if(grid==false) {
            row = inflater.inflate(R.layout.offer_layout, parent, false);
        }else {
            row = inflater.inflate(R.layout.offer_layout_grid, parent, false);
        }


        SearchAdapter.Item item = new SearchAdapter.Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((OffersAdapter.Item)holder).textView.setText(dataServices.get(position).getName());
        ((OffersAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, OfferDetails.class);
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });




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
        return dataServices.size();

    }
    public class Item extends RecyclerView.ViewHolder {

        TextView textView,rating,reserv_btn;


        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
            rating = itemView.findViewById(R.id.rating);
            reserv_btn = itemView.findViewById(R.id.reserv_btn);
        }
    }
}




