package com.ptm.clinicpa.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.CreateRequestActivity;
import com.ptm.clinicpa.DataModel.DateClass;
import com.ptm.clinicpa.DataModel.Location_Beauty;
import com.ptm.clinicpa.DataModel.RequestProviderItem;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.MapsActivityLocation;
import com.ptm.clinicpa.R;

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
    (holder).exp.setImageDrawable(null);
    (holder).health.setImageDrawable(null);

    if (itemArrayList.get(position).getBdb_has_experience_cer().equals("1")){
        (holder).exp.setImageResource(R.drawable.ic_experience_care);
    }

    if (itemArrayList.get(position).getBdb_has_health_cer().equals("1")){
        (holder).health.setImageResource(R.drawable.ic_health_care);
    }

    ( holder).providerName.setText(itemArrayList.get(position).getSup_name());
    (holder).place.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemArrayList.get(position).getBdb_loc_lat()!=null
                    && !itemArrayList.get(position).getBdb_loc_lat().equals("null")
                    && !itemArrayList.get(position).getBdb_loc_lat().equals("")
                    && itemArrayList.get(position).getBdb_loc_long()!=null
                    && !itemArrayList.get(position).getBdb_loc_long().equals("null")
                    && !itemArrayList.get(position).getBdb_loc_long().equals("")

            ) {
                Intent intent = new Intent(context, MapsActivityLocation.class);
                intent.putExtra("lat", Double.parseDouble(itemArrayList.get(position).getBdb_loc_lat()));
                intent.putExtra("lang", Double.parseDouble(itemArrayList.get(position).getBdb_loc_long()));
                context.startActivity(intent);
            }
        }
    });
    APICall.getSalonLogoDltWhenEmptyWithCard(BeautyMainPage.context,itemArrayList.get(position).getLogo_id(),(holder).logo,(holder).cardView);

    ( holder).provider_rate.setText(itemArrayList.get(position).getRating());
    String deposit= BeautyMainPage.context.getString(R.string.dep_prcntg)+itemArrayList.get(position).getDeposit_prcntg()+" % ";
    ( holder).depositPrcntg.setText(deposit);
    ( holder).addRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(BeautyMainPage.context, CreateRequestActivity.class);
            i.putExtra("sup_id",itemArrayList.get(position).getSup_id());

            RequestProvidersFragment.bdb_booking_period=itemArrayList.get(position).getBdb_booking_period();
            Log.e("t.bdb_booking_period","is"+ RequestProvidersFragment.bdb_booking_period);
            BeautyMainPage.context.startActivity(i);

        }
    });
}
public class ListHolder extends RecyclerView.ViewHolder {
    TextView providerName,provider_rate,depositPrcntg;
    ImageView addRequest,logo,place,health,exp;
    CardView cardView;
    public ListHolder(View itemView) {
        super(itemView);
        providerName=itemView.findViewById(R.id.service_name);
        addRequest=itemView.findViewById(R.id.add_request);
        provider_rate=itemView.findViewById(R.id.provider_rate);
        logo=itemView.findViewById(R.id.logoImg);
        place=itemView.findViewById(R.id.place);
        depositPrcntg=itemView.findViewById(R.id.depPerc);
        health=itemView.findViewById(R.id.health);
        exp=itemView.findViewById(R.id.exp);
        cardView=itemView.findViewById(R.id.myCardView);
    }
}
    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


}

