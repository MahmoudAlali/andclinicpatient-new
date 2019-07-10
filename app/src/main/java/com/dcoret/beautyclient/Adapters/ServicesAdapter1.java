package com.dcoret.beautyclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView canceltime,canceldate,okdate,oktime;

    Context context;
    String items[];
    String[] price;
    String[] rank;
    String[] cities;
    Location_Beauty[] location_beauties;
    boolean[] fav;
    ArrayList<DataService> dataServices;
    ArrayList<DataService> favDataServices=new ArrayList<>();

    /**
     * @param context
     * @param items
     */
    public ServicesAdapter1(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties , boolean []fav){
        this.context=BeautyMainPage.context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;


    }


    boolean grid ;
    public ServicesAdapter1(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties, boolean grid, boolean[] fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.grid=grid;
        this.fav=fav;

    }
    public ServicesAdapter1(Context context, String[] items){
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
        if(grid==false) {
             row = inflater.inflate(R.layout.service_layout_last, parent, false);
        } else {
          row = inflater.inflate(R.layout.service_layout_last, parent, false);
        }
        ServicesAdapter1.Item item=new ServicesAdapter1.Item(row);
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
     * @see android.support.v7.widget.RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView favorites, textView,price,rank,resrv_btn;
        TextView pro_name;
        CheckBox compare;
        LinearLayout service_details;

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.rname);
            price=itemView.findViewById(R.id.price);
            rank=itemView.findViewById(R.id.rank);
            resrv_btn=itemView.findViewById(R.id.reserv_btn);
            pro_name=itemView.findViewById(R.id.pro_name);
//            more_btn=itemView.findViewById(R.id.more_btn);
            compare=itemView.findViewById(R.id.compare);
            service_details=itemView.findViewById(R.id.service_details);
            favorites=itemView.findViewById(R.id.favorites_star);

        }
    }
}
