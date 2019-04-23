package com.dcoret.beautyclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    public ServicesAdapter(Context context,String items[],String[] price,String[] rank,String[] cities,Location_Beauty[] location_beauties , boolean []fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;


    }


    boolean grid ;
    public ServicesAdapter(Context context,String items[],String[] price,String[] rank,String[] cities,Location_Beauty[] location_beauties,boolean grid,boolean[] fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.grid=grid;
        this.fav=fav;

    }
    public ServicesAdapter(Context context, ArrayList<DataService> dataServices){
        this.context=context;
        this.dataServices=dataServices;

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
             row = inflater.inflate(R.layout.service_layout_example, parent, false);
        } else {
          row = inflater.inflate(R.layout.service_layout_example, parent, false);
        }
        ServicesAdapter.Item item=new ServicesAdapter.Item(row);
        return item;
    }




    String date;
    Dialog dialog1,dialog;
    static int comparenum=0;
    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        try {
            ((ServicesAdapter.Item)holder).textView.setText(items[position]);
            ((Item)holder).price.setText(price[position]);
            ((Item)holder).rank.setText(rank[position]);
            if(fav[position]){
                ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
                Favorites.dataServices.add(new DataService(0
                        ,items[position]
                        ,Double.parseDouble(price[position])
                        ,Double.parseDouble(rank[position])
                        ,true
                        ,false

                ));
                fav[position]=true;            }
        }catch (Exception e){
            ((ServicesAdapter.Item)holder).textView.setText(dataServices.get(position).getName());
            ((Item)holder).price.setText(dataServices.get(position).getPrice()+"");
            ((Item)holder).rank.setText(dataServices.get(position).getRating()+"");
            if(!dataServices.get(position).isFav()) {
                ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_heart);


            }else {
                ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);


            }
            }


        ((Item) holder).service_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ServiceDetails.class);
                intent.putExtra("service_name",((Item) holder).textView.getText().toString());
                context.startActivity(intent);
            }
        });

            try {
                ((Item) holder).compare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Item) holder).compare.isActivated()) {
                            comparenum--;
                            Toast.makeText(MyReservations.context, comparenum + "", Toast.LENGTH_LONG).show();
                            ((Item) holder).compare.setTextColor(Color.WHITE);
                            ((Item) holder).compare.setActivated(false);
                        } else if (((Item) holder).compare.isActivated() == false && comparenum < 3) {
                            ((Item) holder).compare.setActivated(true);
                            comparenum++;
                            ((Item) holder).compare.setTextColor(Color.GREEN);
                            Toast.makeText(MyReservations.context, comparenum + "", Toast.LENGTH_LONG).show();

                        }

                    }
                        });
        }catch (Exception e){


        }


        try {

    ((Item) holder).favorites.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {


                if (fav[position]) {
                    ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_heart);

                    fav[position] = true;
                } else {
                    ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
                    Favorites.dataServices.add(new DataService(0
                            , items[position]
                            , Double.parseDouble(price[position])
                            , Double.parseDouble(rank[position])
                            , true
                            , false
                    ));
                    fav[position] = false;


                }
            }catch (Exception e){
//                    ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
                    Favorites.dataServices.remove(position);
                    notifyDataSetChanged();

            }

        }
    });
        }catch (Exception e){

        }

//        ((Item) holder).textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(context, ServiceDetails.class);
//                    context.startActivity(intent);
//                }catch (Exception e){
//                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
//                }
//                }
//        });



       try {
             dialog = new Dialog(context);
             dialog1 = new Dialog(context);

       }catch (Exception e){
           dialog = new Dialog(MyReservations.context);
           dialog1 = new Dialog(MyReservations.context);
       }

        dialog.setContentView(R.layout.dialog_calender);
        dialog1.setContentView(R.layout.dialog_calender_time);
        final DatePicker datePicker=dialog.findViewById(R.id.date);
        final TimePicker timePicker=dialog1.findViewById(R.id.time);

         okdate=dialog.findViewById(R.id.ok_date);
          oktime=dialog1.findViewById(R.id.ok_time);
         canceldate=dialog.findViewById(R.id.cancel_date);
         canceltime=dialog1.findViewById(R.id.cancel_time);
try {
    ((Item) holder).resrv_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            dialog.show();
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
                        ShoppingCart.dataServices.add(new DataService(0
                                ,items[position]
                                ,Double.parseDouble(price[position])
                                ,Double.parseDouble(rank[position])
                                ,false
                                ,false
                        ));
                        Reservation.services.add(new DataService(0
                                ,items[position]
                                ,Double.parseDouble(price[position])
                                ,Double.parseDouble(rank[position])
                                ,false
                                ,false
                        ));

                    }catch (Exception e){
                        Toast.makeText(MyReservations.context,((Item) holder).textView.getText().toString()+"   "+date,Toast.LENGTH_LONG).show();
                        ShoppingCart.dataServices.add(new DataService(0
                                ,items[position]
                                ,Double.parseDouble(price[position])
                                ,Double.parseDouble(rank[position])
                                ,false
                                ,false
                        ));

                        Reservation.services.add(new DataService(0
                                ,items[position]
                                ,Double.parseDouble(price[position])
                                ,Double.parseDouble(rank[position])
                                ,false
                                ,false
                        ));

                    }


                }
            });

        }

    });

        }catch (Exception e)
        {

            e.printStackTrace();

        }

        //        ((Item) holder).rate.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Dialog dialog=new Dialog(MyReservations.context)
        //                        ;
        //                dialog.setContentView(R.layout.rating_dialog);
        //                dialog.show();
        //            }
        //        });
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
        TextView more_btn;
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
//            more_btn=itemView.findViewById(R.id.more_btn);
            compare=itemView.findViewById(R.id.compare);
            service_details=itemView.findViewById(R.id.service_details);
            favorites=itemView.findViewById(R.id.favorites_star);

        }
    }
}
