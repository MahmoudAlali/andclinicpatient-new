package com.ptm.clinicpa.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.meg7.widget.SvgImageView;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.CreateRequestActivity;
import com.ptm.clinicpa.Activities.GroupOffer.SingleDateMultiClientOfferBooking;
import com.ptm.clinicpa.Activities.MultiDateOffer.MultiDateOfferBooking;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Activities.ProviderSerAndOfferPKG.MainProviderActivity;
import com.ptm.clinicpa.Activities.RelativesActivity;
import com.ptm.clinicpa.Activities.SingleOffer.SingleDateOfferBooking;
import com.ptm.clinicpa.DataModel.BestOfferItem;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.DataModel.ServiceItem;
import com.ptm.clinicpa.DataExample.OffersData;
import com.ptm.clinicpa.R;
//import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public  class OffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    ArrayList<DataOffer> offers=new ArrayList<>();
    String name;
    ArrayList<ServiceItem> serviceItems;
    public static BestOfferItem bestOItem;
    static int counter =0;
    ArrayList<BestOfferItem> bestOfferItems;
    ArrayList<String> OFFER_RESERVATION_TYPE=new ArrayList<>();
    public static HashMap<String,Bitmap> logoImages = new HashMap<>();
    int []backgrounds ={R.drawable.blue_offer_background,R.drawable.pink_offer_background,R.drawable.prpl_offer_background,R.drawable.page_offer_background,R.drawable.brown_offer_background};
    int []backgroundsAr ={R.drawable.blue_offer_background_ar,R.drawable.pink_offer_background_ar,R.drawable.prpl_offer_background_ar,R.drawable.page_offer_background_ar,R.drawable.brown_offer_background_ar};
    int []offerBackGrounds ={R.drawable.blue_offer_type,R.drawable.pink_offer_type,R.drawable.prpl_offer_type,R.drawable.page_offer_type,R.drawable.brown_offer_type};
    public OffersAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
//    public OffersAdapter(Context context, ArrayList<ServiceItem> serviceItems){
//        this.context=context;
//        this.serviceItems=serviceItems;
//    }

    public OffersAdapter(Context context, ArrayList<BestOfferItem> bestOfferItems){
        this.context=context;
        this.bestOfferItems=bestOfferItems;
    }
    public OffersAdapter(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }


    public OffersAdapter(Context context, ArrayList<DataOffer> offers, boolean grid, String name){
        this.context=context;
        this.offers=offers;
        this.grid=grid;
        this.name=name;
        new OffersData(offers);


    }


    JSONObject object;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View row;

//        object=APICall.browseclass(Offers.context);

       /* if(counter%2==0) {
             row = inflater.inflate(R.layout.left_offer_new_layout, parent, false);
        }else {
             row = inflater.inflate(R.layout.right_offer_new_layout, parent, false);
        }*/
       // row = inflater.inflate(R.layout.left_offer_new_layout, parent, false);

        if(context.getResources().getString(R.string.locale).equals("ar"))
            row = inflater.inflate(R.layout.left_offer_new_layout2, parent, false);
        else
            row = inflater.inflate(R.layout.right_offer_new_layout2, parent, false);



        counter++;
            OffersAdapter.Item item = new OffersAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//---------------------for prices decimal format-----------------
        try {
            if (bestOfferItems.get(position).getBdb_has_experience_cer().equals("1")){
                ((Item)holder).exp.setImageResource(R.drawable.ic_experience_care);
            }
            if (bestOfferItems.get(position).getBdb_has_health_cer().equals("1")){
                ((Item)holder).health.setImageResource(R.drawable.ic_health_care);
            }


            DecimalFormat integer=new DecimalFormat("#");
            DecimalFormat doub=new DecimalFormat("#.##");
//            float old_prc = Float.parseFloat(nFormate(Double.parseDouble(bestOfferItems.get(position).getOld_price()) ));
//            float new_prc = Float.parseFloat(nFormate(Double.parseDouble(bestOfferItems.get(position).getNew_price()) ));
//            float tot_dis = Float.parseFloat(nFormate(Double.parseDouble(bestOfferItems.get(position).getTotal_discount()) ));

//        ((Item)holder).pack_code.setText("#"+bestOfferItems.get(position).getPack_code());
            ((Item) holder).pro_name.setText(bestOfferItems.get(position).getProvider_name());
            if(context.getString(R.string.locale).equals("en"))
                ((Item) holder).speciality.setText(bestOfferItems.get(position).getSpeciality_en());
            else
                ((Item) holder).speciality.setText(bestOfferItems.get(position).getSpeciality_ar());
            if(context.getString(R.string.locale).equals("en"))
                ((Item) holder).centerName.setText(bestOfferItems.get(position).getHealth_center_en());
            else
                ((Item) holder).centerName.setText(bestOfferItems.get(position).getHealth_center_ar());


            // ((Item) holder).centerName.setText(bestOfferItems.get(position).getProvider_name());
            String deposit= BeautyMainPage.context.getString(R.string.dep_prcntg)+bestOfferItems.get(position).getDeposit_prcntg()+" % ";
            ( (Item)holder).depositPrcntg.setText(deposit);
            //((Item) holder).depositPrcntg.setText(bestOfferItems.get(position).getDeposit_prcntg());
            ((Item) holder).pro_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Offers.bdb_booking_period = Integer.parseInt(bestOfferItems.get(position).getBdb_booking_period());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.e("PIDDDD","is"+bestOfferItems.get(position).getProvider_id());
                    Log.e("Position","is"+position);
                    Intent intent=new Intent(context, MainProviderActivity.class);
                    intent.putExtra("provider_id",bestOfferItems.get(position).getProvider_id());
                    intent.putExtra("provider_name",((Item)holder).pro_name.getText().toString());
                    intent.putExtra("health",bestOfferItems.get(position).getBdb_has_health_cer());
                    intent.putExtra("exp",bestOfferItems.get(position).getBdb_has_experience_cer());
                    context.startActivity(intent);
                }
            });



            ((Item)holder).
                    add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BeautyMainPage.FRAGMENT_NAME="Offers";

                    bestOItem=bestOfferItems.get(position);
                    try{
                        Log.e("bestOItem","is"+bestOItem);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                   /* Intent i=new Intent(context, CreateRequestActivity.class);
                    i.putExtra("is_offer",true);
                    i.putExtra("sup_id",bestOfferItems.get(position).getProvider_id());
                    i.putExtra("pack_code",bestOfferItems.get(position).getPack_code());
                    i.putExtra("longNum",Offers.Long);
                    i.putExtra("latNum",Offers.Lat);
                    context.startActivity(i);*/

                    Intent i = new Intent(BeautyMainPage.context, RelativesActivity.class);
                    i.putExtra("sup_id",bestOfferItems.get(position).getProvider_id());
                    i.putExtra("center_id",bestOfferItems.get(position).getHealth_center_id());
                    i.putExtra("isBooking",true);
                    i.putExtra("is_offer",true);
                    i.putExtra("pack_code",bestOfferItems.get(position).getPack_code());
                    i.putExtra("longNum",Offers.Long);
                    i.putExtra("latNum",Offers.Lat);
                    i.putExtra("max_age",bestOfferItems.get(position).getMax_age());
                    i.putExtra("min_age",bestOfferItems.get(position).getMin_age());
                    i.putExtra("supported_gender",bestOfferItems.get(position).getSupported_gender());

                    context.startActivity(i);

                }
            });

            APICall.getSalonLogoDltWhenEmpty(context,bestOfferItems.get(position).getProvider_logo_id(),((Item) holder).logoImg);

            if (bestOfferItems.get(position).getOffer_type().equals("1") ) {// individual
                ((Item) holder).offer_type.setText(BeautyMainPage.context.getResources().getString(R.string.indiv));

            }else if (bestOfferItems.get(position).getOffer_type().equals("2") ) {// multi dates individual
                ((Item) holder).offer_type.setText(BeautyMainPage.context.getResources().getString(R.string.indiv));
            }else if (bestOfferItems.get(position).getOffer_type().equals("3") ) {// group
                ((Item) holder).offer_type.setText(BeautyMainPage.context.getResources().getString(R.string.group));
            }
//        ((Item)holder).ser_count.setText(bestOfferItems.get(position).getService_count());
            ((Item) holder).old_price.setText(APICall.convertToArabic(integer.format(Double.parseDouble(bestOfferItems.get(position).getOld_price())) + ""));
            ((Item) holder).new_price.setText(APICall.convertToArabic(integer.format(Double.parseDouble(bestOfferItems.get(position).getNew_price())) + ""));
            String on= context.getResources().getString(R.string.on);
            String sevices= context.getResources().getString(R.string.ser);
            String oneService= context.getResources().getString(R.string.oneService);
            if(bestOfferItems.get(position).getService_count().equals("1"))
                ((Item) holder).onServices.setText(oneService );
            else
                ((Item) holder).onServices.setText(on+" " +bestOfferItems.get(position).getService_count()+ " "+sevices );
            if(context.getResources().getString(R.string.locale).equals("ar"))
                ((Item) holder).total_dis.setText(APICall.convertToArabic(doub.format(Double.parseDouble(bestOfferItems.get(position).getTotal_discount() ))+ "% "));
            else
                ((Item) holder).total_dis.setText(doub.format(Double.parseDouble(bestOfferItems.get(position).getTotal_discount() ))+ "% ");

            ((Item) holder).old_price.setPaintFlags(((Item) holder).old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ((Item) holder).info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONArray jsonArray = bestOfferItems.get(position).getSersup_ids();

//                    APICall.arabicToDecimal()
//               StringBuilder infoItem=new StringBuilder();
                    try {
                        PopupMenu popup = new PopupMenu(context, ((Item) holder).info);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String bdb_name_ar;
                            if(context.getResources().getString(R.string.locale).equals("ar"))
                            {
                                bdb_name_ar = jsonObject.getString("bdb_name_ar");
                            }
                            else
                                bdb_name_ar = jsonObject.getString("bdb_name");

                            popup.getMenu().add(bdb_name_ar);
//                       if (i==jsonArray.length()-1){
//                           infoItem.append(bdb_name_ar);
//                       }else {
//                           infoItem.append(bdb_name_ar+"\n");
//                       }
                        }

                        popup.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


//                new BubbleShowCaseBuilder((AppCompatActivity)context) //Activity instance
//                        .title(infoItem.toString()) //Any title for the bubble view
//                        .targetView(((Item)holder).info) //View to point out
//                        .show(); //Display the ShowCase
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


//        try {
//
//            ((OffersAdapter.Item) holder).textView.setText(offers.get(position).getName());
//            ((Item) holder).pro_name.setText(offers.get(position).getServices()[0].getProvider_name());
//            ((Item) holder).price.setText(offers.get(position).getPrice() + "");
//            ((Item) holder).rating.setText(offers.get(position).getRate() + "");
//            OFFER_RESERVATION_TYPE.add(offers.get(position).getOffer_type());
//            ((OffersAdapter.Item) holder).textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//        }catch (Exception e){
////        Toast.makeText(context,e.getMessage()+"",Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public int getItemCount() {
        try {
            return bestOfferItems.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }


    }
    public static String nFormate(double d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(2);
        String st= nf.format(d);
        return st;
    }
    public static String nFormatePercent(double d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(0);
        String st= nf.format(d);
        return st;
    }
    public static class Item extends RecyclerView.ViewHolder {
        TextView speciality,centerName,textView,pack_code,rating,price,pro_name,offer_type,reserv_offer,ser_count,total_dis,new_price,old_price,onServices,depositPrcntg;
        ImageView info,logoImg2,add,exp,health;
        SvgImageView logoImg;
        LinearLayout itemBackground;
        public Item(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rname);
            pro_name = itemView.findViewById(R.id.pro_name);
            info = itemView.findViewById(R.id.info);
            reserv_offer = itemView.findViewById(R.id.reserv_offer);
            rating = itemView.findViewById(R.id.rank);
            price = itemView.findViewById(R.id.price);
            onServices = itemView.findViewById(R.id.onServices);
            price = itemView.findViewById(R.id.price);
//            ser_count = itemView.findViewById(R.id.ser_count);
//            pro_name = itemView.findViewById(R.id.pro_name);
            total_dis = itemView.findViewById(R.id.disAmount);
            add = itemView.findViewById(R.id.add);
            logoImg = itemView.findViewById(R.id.logoImg);
            itemBackground = itemView.findViewById(R.id.itemBackground);
            new_price = itemView.findViewById(R.id.new_price);
            old_price = itemView.findViewById(R.id.old_price);
            offer_type = itemView.findViewById(R.id.offer_type);
            depositPrcntg = itemView.findViewById(R.id.depPerc);
            exp = itemView.findViewById(R.id.exp);
            health = itemView.findViewById(R.id.health);
            centerName = itemView.findViewById(R.id.centerName);
            speciality = itemView.findViewById(R.id.speciality);
        }
    }
}
