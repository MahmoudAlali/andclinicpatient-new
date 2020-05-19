package com.ptm.clinicpa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptm.clinicpa.DataModel.DataService;
import com.ptm.clinicpa.DataModel.Location_Beauty;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView canceltime,canceldate,okdate,oktime;

    Context context;
    String items[];
    String[] price;
    String[] rank;
    String[] cities;
    Location_Beauty[] location_beauties;
    boolean[] fav;
    ArrayList<DataService> dataServices;
    /**
     * @param context
     * @param items
     */
    public FavouriteAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties , boolean []fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;


    }


    boolean grid ;
    public FavouriteAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties, boolean grid, boolean[] fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.grid=grid;
        this.fav=fav;

    }
    public FavouriteAdapter(Context context, String [] items){
        this.context=context;
        this.items=items;
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
        LayoutInflater inflater=LayoutInflater.from(context);
        View row;

             row = inflater.inflate(R.layout.favorites_layout_last, parent, false);
        FavouriteAdapter.Item item=new FavouriteAdapter.Item(row);
        return item;
    }


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            }

    @Override
    public int getItemCount() {
        try {
            return items.length;

        }catch (Exception e){
            return dataServices.size();

        }
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);
        }
    }

}
