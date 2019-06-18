package com.dcoret.beautyclient.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcoret.beautyclient.Activities.BeautyMainPage_2;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Activities.ShoppingCart;

import java.util.ArrayList;

public class ShopCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String price[];
    String items[];
  static ArrayList<DataService> dataServices;
    public ShopCartAdapter(Context context,String items[]){
        this.context=context;
        this.items=items;
    }
    public ShopCartAdapter(Context context,String items[],String[] price){
        this.context=context;
        this.items=items;
        this.price=price;
    }

    public ShopCartAdapter(Context context,ArrayList <DataService> dataServices){
        this.context=context;
        this.dataServices=dataServices;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.shopcart_layout, parent, false);
        ShopCartAdapter.Item item = new ShopCartAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ShopCartAdapter.Item)holder).textView.setText(dataServices.get(position).getName());
        ((Item)holder).price.setText(dataServices.get(position).getPrice()+"");

        ((Item) holder).cancel_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Integer> removeditem=new ArrayList();
                if(dataServices.get(position).isIsoffer()){
                    new AlertDialog.Builder(BeautyMainPage_2.context)
                            .setTitle("Cancel Reservation")
                            .setMessage("سوف يتم الغاء كامل العرض و حذف الخدمات الاخرى المتعلقة به,هل انت متأكد ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int id=dataServices.get(position).getOfferid();
                                    int size=dataServices.size();
                                    for (int i=0; i<dataServices.size();i++) {
                                        if (dataServices.get(i).isIsoffer() && id == dataServices.get(i).getOfferid()) {
                                                   dataServices.remove(i);
                                            if(size>dataServices.size()){
                                                size=dataServices.size();
                                                i=0;
                                            }

                                        }
                                    }
                                    if(dataServices.get(0).getOfferid()==id){
                                        dataServices.remove(0);
                                    }
                                        notifyDataSetChanged();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {
                    new AlertDialog.Builder(BeautyMainPage_2.context)
                            .setTitle("Cancel Reservation")
                            .setMessage("هل انت متأكد انك تريد الغاء الحجز ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dataServices.remove(position);
                                    notifyDataSetChanged();




                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });



//        ((OffersAdapter1.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(context, OfferDetails.class);
//                    context.startActivity(intent);
//                }catch (Exception e){
//                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        ((Item) holder).rating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ReservationDialog dialog=new ReservationDialog(Offers.context);
//                dialog.setContentView(R.layout.rating_dialog);
//                dialog.setTitle("تقييم العرض");
//                dialog.show();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataServices.size();
    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView textView,price,cancel_re;


        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.shop_name);
            price = itemView.findViewById(R.id.price);
            cancel_re = itemView.findViewById(R.id.cancel_re);
        }
    }
}
