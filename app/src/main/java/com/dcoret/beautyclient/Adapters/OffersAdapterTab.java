package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OffersAdapterTab extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    ArrayList<DataOffer> offers=new ArrayList<>();
    String name;

    public OffersAdapterTab(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public OffersAdapterTab(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }
    public OffersAdapterTab(Context context, ArrayList<DataOffer> offers){
        this.context=context;
        this.offers=offers;

        new OffersData(offers);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View row;
             row = inflater.inflate(R.layout.offers_layout_tab_last, parent, false);

            OffersAdapterTab.Item item = new OffersAdapterTab.Item(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        DecimalFormat df = new DecimalFormat("0.0");
        float old_prc=Float.parseFloat(Double.parseDouble(offers.get(position).getOldPrice())+"");
        float discountval=Float.parseFloat(Double.parseDouble(offers.get(position).getDiscount())+"");
        old_prc = Float.parseFloat(df.format(old_prc));
        discountval = Float.parseFloat(df.format(discountval));
        ((Item)holder).pro_name.setText(offers.get(position).getBdb_sup_name());
        ((Item)holder).new_price.setText(offers.get(position).getNewPrice());
        ((Item)holder).num_of_times.setText(context.getResources().getText(R.string.num_of_times)+offers.get(position).getNum_of_times());
        ((Item)holder).old_price.setText(old_prc+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDateString=null ;
        Date d = null;
        try {
            d = sdf.parse(offers.get(position).getBdb_offer_end());
            newDateString= sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((Item)holder).offer_end.setText(context.getResources().getText(R.string.end_offer)+newDateString);
        ((Item)holder).discount.setText(discountval+"% "+context.getResources().getString(R.string.on)+" "+offers.get(position).getService_count()+" "+context.getResources().getString(R.string.onservice));
        ((Item)holder).old_price.setPaintFlags(((Item)holder).old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ((Item)holder).info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray jsonArray= offers.get(position).getPack_data();

                    PopupMenu popup = new PopupMenu(context,((Item)holder).info);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String bdb_ser_name=jsonObject.getString("bdb_ser_name");
                        popup.getMenu().add(bdb_ser_name);
//                       if (i==jsonArray.length()-1){
//                           infoItem.append(bdb_name_ar);
//                       }else {
//                           infoItem.append(bdb_name_ar+"\n");
//                       }
                    }
                    popup.show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        try {
            return offers.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView pro_name,new_price,old_price,discount,num_of_times,offer_end;
        ImageView info;
        public Item(View itemView) {
            super(itemView);
            pro_name = itemView.findViewById(R.id.pro_name);
            new_price = itemView.findViewById(R.id.new_price);
            old_price = itemView.findViewById(R.id.old_price);
            discount = itemView.findViewById(R.id.discountVal);
            num_of_times = itemView.findViewById(R.id.num_of_times);
            info = itemView.findViewById(R.id.info);
            offer_end = itemView.findViewById(R.id.offer_end);

        }
    }

}
