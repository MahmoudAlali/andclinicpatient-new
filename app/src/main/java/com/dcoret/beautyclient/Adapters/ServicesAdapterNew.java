package com.dcoret.beautyclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dcoret.beautyclient.API.ReservationDialog;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Favorites;
import com.dcoret.beautyclient.Activities.MyReservations;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.Activities.ServiceDetails;
import com.dcoret.beautyclient.AddReservation;
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
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView canceltime,canceldate,okdate,oktime;

    Context context;
    public static boolean list;
//    String items[];
    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};

    String[] price;
    String[] rank;
    String[] cities;
    int layout;
    Location_Beauty[] location_beauties;
    boolean[] fav;
    ArrayList<DataService> dataServices;
    ArrayList<DataService> favDataServices=new ArrayList<>();


    public ServicesAdapterNew(Context context,String[] items){
        this.context=context;
        this.items=items;
    }

    public ServicesAdapterNew(Context context,String[] items,Boolean list,int layout){
        this.context=context;
        this.items=items;
        this.list=list;
        this.layout=layout;
    }
    /**
     * @param context
     * @param items
     */
    public ServicesAdapterNew(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties , boolean []fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;


    }


    boolean grid ;
    public ServicesAdapterNew(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties, boolean grid, boolean[] fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.grid=grid;
        this.fav=fav;

    }
    public ServicesAdapterNew(Context context, ArrayList<DataService> dataServices){
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
        LayoutInflater inflater=LayoutInflater.from(context.getApplicationContext());
        View row;
//        if(grid==false) {
             row = inflater.inflate(layout, parent, false);
//        } else {
//          row = inflater.inflate(R.layout.service_layout_last, parent, false);
//        }
        ServicesAdapterNew.Item item=new ServicesAdapterNew.Item(row);
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
            ((Item)holder).service_name.setText(items[position]);
            ((Item) holder).service_compare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        comparenum+=1;
                    }else {
                        comparenum-=1;
                    }
                }
            });


            ((Item) holder).service_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BeautyMainPage.context, AddReservation.class);
                    context.startActivity(intent);
                }
            });


//            ((Item)holder).price.setText(dataServices.get(position).getPrice()+"");
//            ((Item) holder).pro_name.setText(dataServices.get(position).getProvider_name());
//            ((Item)holder).rank.setText(dataServices.get(position).getRating()+"");
//            if(fav[position]){
//                ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
//                Favorites.dataServices.add(new DataService(0
//                        ,items[position]
//                        ,Double.parseDouble(price[position])
//                        ,Double.parseDouble(rank[position])
//                        ,true
//                        ,false
//
//                ));
//                fav[position]=true;            }
        }catch (Exception e){
            e.printStackTrace();
//            ((ServicesAdapterNew.Item)holder).textView.setText(dataServices.get(position).getName());
//            ((Item)holder).price.setText(dataServices.get(position).getPrice()+"");
//            ((Item)holder).rank.setText(dataServices.get(position).getRating()+"");
//            if(!dataServices.get(position).isFav()) {
//                ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_heart);
//
//
//            }else {
//                ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
//            }
//            }
//
//
//        ((Item) holder).service_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, ServiceDetails.class);
//                intent.putExtra("service_name",((Item) holder).textView.getText().toString());
//                intent.putExtra("provider_name",((Item) holder).pro_name.getText().toString());
//                intent.putExtra("price",((Item) holder).price.getText().toString());
////                intent.putExtra("service_name",((Item) holder).textView.getText().toString());
//                context.startActivity(intent);
            }
//        });
//
//            try {
////                ((Item) holder).compare.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        if (((Item) holder).compare.isActivated()) {
////                            comparenum--;
////                            Toast.makeText(context.getApplicationContext(), comparenum + "", Toast.LENGTH_LONG).show();
//                            ((Item) holder).compare.setTextColor(Color.WHITE);
//                            ((Item) holder).compare.setActivated(false);
//                        } else if (((Item) holder).compare.isActivated() == false && comparenum < 3) {
//                            ((Item) holder).compare.setActivated(true);
//                            comparenum++;
//                            ((Item) holder).compare.setTextColor(Color.GREEN);
//                            Toast.makeText(context.getApplicationContext(), comparenum + "", Toast.LENGTH_LONG).show();
//
//                        }
//
//                    }
//                        });
//        }catch (Exception e){
//
//
//        }
//
//
//        try {
//
//    ((Item) holder).favorites.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            try {
//                if (dataServices.get(position).isFav()) {
//                    ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_heart);
//
//                    fav[position] = false;
//                    dataServices.get(position).setFav(false);
//                } else   {
//                    ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
////                    Favorites.dataServices.add(new DataService(0
////                            , items[position]
////                            , Double.parseDouble(price[position])
////                            , Double.parseDouble(rank[position])
////                            , true
////                            , false
////                    ));
////                    fav[position] = false;
//                    dataServices.get(position).setFav(false);
//
//
//
//                }
//            }catch (Exception e){
//                e.printStackTrace();
////                    ((Item) holder).favorites.setBackgroundResource(R.drawable.ic_favorite_heart_button);
//                try {
//                    Favorites.dataServices.remove(position);
//                    notifyDataSetChanged();
//
//                }catch (Exception ee){
//                    ee.printStackTrace();
//                }
//
//            }
//
//        }
//    });
//        }catch (Exception e){
//
//        }
//
//       try {
//             dialog = new Dialog(MyReservations.context);
//             dialog1 = new Dialog(MyReservations.context);
//
//       }catch (Exception e){
//
//           dialog = new Dialog(Offers.context);
//           dialog1 = new Dialog(Offers.context);
//       }
//
//        dialog.setContentView(R.layout.dialog_calender);
//        dialog1.setContentView(R.layout.dialog_calender_time);
//        final DatePicker datePicker=dialog.findViewById(R.id.date);
//        final TimePicker timePicker=dialog1.findViewById(R.id.time);
//
//         okdate=dialog.findViewById(R.id.ok_date);
//          oktime=dialog1.findViewById(R.id.ok_time);
//         canceldate=dialog.findViewById(R.id.cancel_date);
//         canceltime=dialog1.findViewById(R.id.cancel_time);
//try {
//    ((Item) holder).resrv_btn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            try {
//                ReservationDialog.dateDialog(MyReservations.context,((Item) holder).textView.getText().toString(),"s");
//            }catch (Exception e){
//                ReservationDialog.dateDialog(Offers.context,((Item) holder).textView.getText().toString(),"s");
//            }

//            dialog.show();
//            canceldate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.cancel();
//                }
//            });
//            canceltime.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog1.cancel();
//                }
//            });
//            okdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    date=datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+" - ";
//                    dialog.cancel();
//                    dialog1.show();
//                }
//            });
//            oktime.setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onClick(View v) {
//                    date=date+timePicker.getHour()+":"+timePicker.getMinute()+"";
//                    dialog1.cancel();
//                    try{
//                        Toast.makeText(context.getApplicationContext(),((Item) holder).textView.getText().toString()+"   "+date,Toast.LENGTH_LONG).show();
//                        ShoppingCartFragment.dataServices.add(new DataService(0
//                                ,items[position]
//                                ,Double.parseDouble(price[position])
//                                ,Double.parseDouble(rank[position])
//                                ,false
//                                ,false
//                        ));
//                        Reservation.services.add(new DataService(0
//                                ,items[position]
//                                ,Double.parseDouble(price[position])
//                                ,Double.parseDouble(rank[position])
//                                ,false
//                                ,false
//                        ));
//                        //----------notification for reserve service

//                    }catch (Exception e){
//                        e.printStackTrace();


//                    }

//
//                }
//            });
//
////        }
//
////    });
//
//        }catch (Exception e)
//        {
//
//            e.printStackTrace();
//
//        }

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
            pro_name=itemView.findViewById(R.id.pro_name);
//            more_btn=itemView.findViewById(R.id.more_btn);
            service_compare=itemView.findViewById(R.id.service_compare);
//            service_details=itemView.findViewById(R.id.service_details);
            service_fav=itemView.findViewById(R.id.service_fav);

        }
    }


    static String token_provider="enTW789hyvs:APA91bGyfEMJKFEZ6NuhvCFAg_Abx6rB9kmdMEW6vPnGRSKJJ3BQNDaKtISf59GuWyS7tBWNdT-ZLOkn3-Nz3IzHZfB911syZzHsRfjk64KGcfG0FAQ0wEAxuFc9buspiowZJmJsQ7lP";
    static String api_key_header_value_provider = "Key=AAAAAAXCVwM:APA91bFiJYACTd-gZPdHrymnwcypg2IQ6JfSdTqUWqt95VANEyTe7H8NAn2nUnwfoau63QdJTXrxpLR5ZyDQ2-PL6TfPCCH7JJrocD1-SkfE7qrfMIqZvu09ICnD72OqAzuB-o85WawO";


    static void sendnotification_provider(Context context,String title,String body ,String action1,String action2) {

        try{
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject data1 = new JSONObject();
            data1.put("title", title);
            data1.put("body", body);
            data1.put("action1" ,action1);
            data1.put("action2" ,action2);
            JSONObject notification_data = new JSONObject();
            notification_data.put("to",token_provider);
            notification_data.put("data", data1);
            System.out.println(notification_data);

            JsonObjectRequest request = new JsonObjectRequest(url, notification_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", api_key_header_value_provider);
                    System.out.println("Send to provider");
                    return headers;
                }
            };

            queue.add(request);
            System.out.println(request);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
