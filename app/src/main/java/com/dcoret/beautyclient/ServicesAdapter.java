package com.dcoret.beautyclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    String[] price;
    String[] rank;
    String[] cities;
    Location_Beauty[] location_beauties;

    /**
     * @param context
     * @param items
     */
    public ServicesAdapter(Context context,String items[],String[] price,String[] rank,String[] cities,Location_Beauty[] location_beauties){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;


    }


    boolean grid ;
    public ServicesAdapter(Context context,String items[],String[] price,String[] rank,String[] cities,Location_Beauty[] location_beauties,boolean grid){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.grid=grid;

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
             row = inflater.inflate(R.layout.service_layout_example_list, parent, false);
        } else {
          row = inflater.inflate(R.layout.service_layout_example_grid, parent, false);
        }
        ServicesAdapter.Item item=new ServicesAdapter.Item(row);
        return item;
    }




    String date;
    Dialog dialog1,dialog;
    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((ServicesAdapter.Item)holder).textView.setText(items[position]);
        ((Item)holder).price.setText(price[position]);
        ((Item)holder).rank.setText(rank[position]);

        ((Item) holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, ServiceDetails.class);
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                }
        });



       try {
             dialog = new Dialog(Services.context);
             dialog1 = new Dialog(Services.context);

       }catch (Exception e){
           dialog = new Dialog(MyReservations.context);
           dialog1 = new Dialog(MyReservations.context);
       }

        dialog.setContentView(R.layout.dialog_calender);
        dialog1.setContentView(R.layout.dialog_calender_time);
        final DatePicker datePicker=dialog.findViewById(R.id.date);
        final TimePicker timePicker=dialog1.findViewById(R.id.time);

        TextView okdate=dialog.findViewById(R.id.ok_date);
         TextView oktime=dialog1.findViewById(R.id.ok_time);
        TextView canceldate=dialog.findViewById(R.id.cancel_date);
        TextView canceltime=dialog1.findViewById(R.id.cancel_time);
        ((Item) holder).resrv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            dialog.show();



//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                alertDialog.setTitle("Your title");
//                alertDialog.setMessage("your message ");
//                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // DO SOMETHING HERE
//
//                    }
//                });
//                AlertDialog dialog = alertDialog.create();
//                dialog.show();


            }

        });

        canceldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        canceltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });

        okdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              dialog.setContentView(R.layout.dialog_calender_time);
                dialog.cancel();
                date=datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+" - ";
              dialog1.show();
            }
        });
        oktime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
               date=date+timePicker.getHour()+":"+timePicker.getMinute()+"";
                dialog1.cancel();

               try{
                   Toast.makeText(Services.context,((Item) holder).textView.getText().toString()+"   "+date,Toast.LENGTH_LONG).show();
               }catch (Exception e){
                   Toast.makeText(MyReservations.context,((Item) holder).textView.getText().toString()+"   "+date,Toast.LENGTH_LONG).show();

               }


            }
        });
        ((Item) holder).rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(MyReservations.context)
                        ;
                dialog.setContentView(R.layout.rating_dialog);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    /**
     * @see android.support.v7.widget.RecyclerView.ViewHolder
     */
    public class Item extends RecyclerView.ViewHolder {

        TextView textView,price,rate,rank;
        TextView more_btn;
        Button resrv_btn;

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rname);
            price=itemView.findViewById(R.id.price);
            rank=itemView.findViewById(R.id.rank);
            resrv_btn=itemView.findViewById(R.id.reserv_btn);
            more_btn=itemView.findViewById(R.id.more_btn);
            rate=itemView.findViewById(R.id.rate);

        }
    }
}
