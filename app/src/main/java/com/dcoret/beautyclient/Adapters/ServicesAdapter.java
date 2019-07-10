package com.dcoret.beautyclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Fragments.AddReservation;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView canceltime,canceldate,okdate,oktime;

    Context context;
    public static boolean list;
    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};

    String[] price;
    String[] rank;
    String[] cities;
    int layout;
    Location_Beauty[] location_beauties;
    boolean[] fav;
    ArrayList<BrowseServiceItem> itemArrayList;

    public ServicesAdapter(Context context, String[] items){
        this.context=context;
        this.items=items;
    }
    public ServicesAdapter(Context context, ArrayList<BrowseServiceItem> itemArrayList, int layout){
        this.context=context;
        this.itemArrayList=itemArrayList;
        this.layout=layout;
    }

    public ServicesAdapter(Context context, String[] items, Boolean list, int layout){
        this.context=context;
        this.items=items;
        this.list=list;
        this.layout=layout;
    }
    /**
     * @param context
     * @param items
     */
    public ServicesAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties , boolean []fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;
    }


    boolean grid ;
    public ServicesAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties, boolean grid, boolean[] fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.grid=grid;
        this.fav=fav;

    }

    /**
     * @param parent
     * @param viewType
     * @return
     * <b>items</b> that are contains the service layout
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context.getApplicationContext());
        View row;
        row = inflater.inflate(layout, parent, false);
        ServicesAdapter.Item item=new ServicesAdapter.Item(row);
        return item;
    }

    String date;
    Dialog dialog1,dialog;
    public static int comparenum=0;
    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        try {
            if (layout==R.layout.service_layout_adapter_last) {
                ((Item) holder).service_name.setText(itemArrayList.get(position).getBdb_sup_name());
                ((Item) holder).pro_name.setText(itemArrayList.get(position).getBdb_sup_name());
                ((Item) holder).service_compare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            comparenum += 1;
                        } else {
                            comparenum -= 1;
                        }
                    }
                });

                ((Item) holder).service_price.setText(itemArrayList.get(position).getPriceByFilter()+" R");
                ((Item) holder).service_rate.setEnabled(false);
                ((Item) holder).service_rate.setRating(Float.parseFloat(itemArrayList.get(position).getBdb_sup_rating()));
                ((Item) holder).service_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BeautyMainPage.context, AddReservation.class);
                        context.startActivity(intent);
                    }
                });

            }else {
                ((Item) holder).service_price.setText(itemArrayList.get(position).getPriceByFilter()+" R");

            }
        }catch (Exception e){
            e.printStackTrace();

            }
            }

    @Override
    public int getItemCount() {
            return itemArrayList.size();
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView  service_name,service_price;
        TextView pro_name;
        RatingBar service_rate;
        ImageView service_add,service_fav;
        CheckBox service_compare;
        LinearLayout service_details;

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);

            service_name=itemView.findViewById(R.id.service_name);
            service_price=itemView.findViewById(R.id.service_price);
            service_add=itemView.findViewById(R.id.service_add);
            service_rate=itemView.findViewById(R.id.service_rate);
            pro_name=itemView.findViewById(R.id.provider_name);
//            more_btn=itemView.findViewById(R.id.more_btn);
            service_compare=itemView.findViewById(R.id.service_compare);
//            service_details=itemView.findViewById(R.id.service_details);
            service_fav=itemView.findViewById(R.id.service_fav);

        }
    }
}
