package com.ptmsa1.vizage.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.CreateRequestActivity;
import com.ptmsa1.vizage.DataModel.DateClass;
import com.ptmsa1.vizage.DataModel.Location_Beauty;
import com.ptmsa1.vizage.DataModel.RequestProviderItem;
import com.ptmsa1.vizage.Fragments.RequestProvidersFragment;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class RequestProvidersAdapter extends RecyclerView.Adapter<RequestProvidersAdapter.ListHolder> {
public static String bdb_ser_salon2="";
public static String bdb_hotel2="";
public static String bdb_ser_hall2="";
public static String bdb_ser_home2="";
public static String empid="";
public static String empid23="";
        TextView canceltime,canceldate,okdate,oktime;

        Context context;
public static boolean list;
static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
public  static ArrayList<DateClass> dateClasses=new ArrayList<>() ;
public static String srName1,srName2,srName3,spname1,spname2,spname3,ev1,ev2,ev3,place1,place2,place3,address1,address2,price1,price2,price3;
public static String bdb_ser_home,bdb_ser_hall,bdb_ser_salon,bdb_hotel;
public static String bdb_ser_home1,bdb_ser_hall1,bdb_ser_salon1,bdb_hotel1,ser_sup_id;
//    public static String bdb_ser_home2,bdb_ser_hall2,bdb_ser_salon2,bdb_hotel2;
        int postion1,postion2;

        String[] price;
        String[] rank;
        String[] cities;
        int layout;
        Location_Beauty[] location_beauties;
        boolean[] fav;
        ArrayList<RequestProviderItem> itemArrayList;

public RequestProvidersAdapter(Context context, String[] items){
        this.context=context;
        this.items=items;
        }
public RequestProvidersAdapter(Context context, ArrayList<RequestProviderItem> itemArrayList, int layout){
        this.context=context;
        this.itemArrayList=itemArrayList;
        this.layout=layout;
        }

public RequestProvidersAdapter(Context context, String[] items, Boolean list, int layout){
        this.context=context;
        this.items=items;
        this.list=list;
        this.layout=layout;
        }
/**
 * @param context
 * @param items
 */
public RequestProvidersAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties , boolean []fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;
        }


        boolean grid ;
public RequestProvidersAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties, boolean grid, boolean[] fav){
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
public ListHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context.getApplicationContext());
        View row;
        row = inflater.inflate(layout, parent, false);
        View convertview = inflater.inflate(layout, parent, false);
//        ServicesAdapter.Item item=new ServicesAdapter.Item(row);
        ListHolder holder = new ListHolder(convertview);
        return holder;
        }

        String date;
        Dialog dialog1,dialog;
public static int comparenum=0;
/**
 * @param holder
 * @param position
 */
@Override
public void onBindViewHolder(@NonNull final ListHolder holder, final int position) {

    ( holder).providerName.setText(itemArrayList.get(position).getSup_name());

    APICall.getSalonLogo(BeautyMainPage.context,itemArrayList.get(position).getLogo_id(),(holder).logo);

    ( holder).provider_rate.setText(itemArrayList.get(position).getRating());
    ( holder).addRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(BeautyMainPage.context, CreateRequestActivity.class);
            i.putExtra("sup_id",itemArrayList.get(position).getSup_id());

            RequestProvidersFragment.bdb_booking_period=itemArrayList.get(position).getBdb_booking_period();
            BeautyMainPage.context.startActivity(i);

        }
    });
}
public class ListHolder extends RecyclerView.ViewHolder {
    TextView providerName,provider_rate;
    ImageView addRequest,logo;
    public ListHolder(View itemView) {
        super(itemView);
        providerName=itemView.findViewById(R.id.service_name);
        addRequest=itemView.findViewById(R.id.add_request);
        provider_rate=itemView.findViewById(R.id.provider_rate);
        logo=itemView.findViewById(R.id.logoImg);
    }
}
    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


}

