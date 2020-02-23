package com.dcoret.beautyclient.Adapters;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dcoret.beautyclient.DataModel.OfferClientsModel;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabTwo;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SelectDateOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    String items[];
    ArrayList<String> strings;
    Boolean layout;
    ArrayList<OfferClientsModel.ServiceDetails> serviceDetails;
    //    public static ArrayList<SelectDateOfferModel> selectDateOfferModels=new ArrayList<>();
    public static ArrayList<TextView> dates=new ArrayList<>();
    public SelectDateOfferAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }

//    public SelectDateOfferAdapter(Context context, ArrayList<String> strings){
//        this.context=context;
//        this.strings=strings;
//    }
 public SelectDateOfferAdapter(Context context, ArrayList<OfferClientsModel.ServiceDetails> serviceDetails){
        this.context=context;
        this.serviceDetails=serviceDetails;
    }

    public SelectDateOfferAdapter(Context context, String items[], Boolean layout){
        this.context=context;
        this.items=items;
        this.layout=layout;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.select_date_offer_layout, parent, false);
        SelectDateOfferAdapter.Item item=new SelectDateOfferAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.select_date);
                TextView ok=dialog.findViewById(R.id.confirm);
                TextView cancel=dialog.findViewById(R.id.cancel);
                final DatePicker datePicker=dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(System.currentTimeMillis());

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date  date=sdf.parse(TabTwo.arrayList.get(position).getBdb_offer_end());

                    datePicker.setMaxDate(date.getTime());

                }catch (Exception e){
                    e.printStackTrace();
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        int month=datePicker.getMonth()+1;
                        ((Item)holder).select_time.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
//                        ((Item)holder).select_time.setText(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
                    }
                });

                dialog.show();
            }
        });

        if (context.getResources().getString(R.string.locale).equals("ar")) {
            ((Item) holder).name.setText(serviceDetails.get(position).getBdb_name_ar());
        }else
            ((Item)holder).name.setText(serviceDetails.get(position).getBdb_ser_name_en());
        dates.add(((Item)holder).select_time);
    }

    @Override
    public int getItemCount() {
        return serviceDetails.size();
    }



    public class Item extends RecyclerView.ViewHolder {

        TextView name,select_time;


        LinearLayout selectdate;
        public Item(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.service_name);
            select_time=itemView.findViewById(R.id.select_time);

            selectdate=itemView.findViewById(R.id.select_date);




        }
    }

}