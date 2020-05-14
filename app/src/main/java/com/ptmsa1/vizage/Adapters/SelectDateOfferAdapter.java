package com.ptmsa1.vizage.Adapters;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.DataModel.OfferClientsModel;
import com.ptmsa1.vizage.Activities.TabTwo;
import com.ptmsa1.vizage.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
//                TabTwo.arrayList.get(position).p
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));


                    Date  date=sdf.parse(TabTwo.arrayList.get(position).getBdb_offer_end());
                    int period=APICall.PERIOD_FOR_SER_OFR;

                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Calendar calendar=Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH,period);
                    Date bpriod=calendar.getTime();
                    Date endDate=null;
                    try {
                        endDate= format.parse(TabTwo.arrayList.get(position).getBdb_offer_end());
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    String bdb_offer_end="";
                    if (endDate.compareTo(bpriod)==1){
                        bdb_offer_end=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                    }else {
                        Calendar c=Calendar.getInstance();
                        c.setTime(endDate);
                        bdb_offer_end=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);

                    }


                    Log.e("Bpriod","is"+bpriod);
                    Log.e("endDate","is"+TabTwo.arrayList.get(position).getBdb_offer_end());
                    Log.e("endDate","is"+bdb_offer_end);
                    Log.e("endDate.compareTo","is"+endDate.compareTo(bpriod));




                    if (format.parse(bdb_offer_end).getTime()>System.currentTimeMillis()) {
                        datePicker.setMaxDate(format.parse(bdb_offer_end).getTime());
                    }else {
                        datePicker.setMaxDate(System.currentTimeMillis());

                    }

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