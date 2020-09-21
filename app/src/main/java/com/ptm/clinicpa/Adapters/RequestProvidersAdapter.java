package com.ptm.clinicpa.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.ptm.clinicpa.Activities.CreateGroupRequestActivity;
import com.ptm.clinicpa.Activities.CreateRequestActivity;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Activities.ProviderSerAndOfferPKG.MainProviderActivity;
import com.ptm.clinicpa.Activities.RelativesActivity;
import com.ptm.clinicpa.DataModel.DateClass;
import com.ptm.clinicpa.DataModel.Location_Beauty;
import com.ptm.clinicpa.DataModel.RequestProviderItem;
import com.ptm.clinicpa.Fragments.PersonalIndivRequest;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
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
    /*(holder).exp.setImageDrawable(null);
    (holder).health.setImageDrawable(null);
*/
    /*if (itemArrayList.get(position).getBdb_has_experience_cer().equals("1")){
        (holder).exp.setImageResource(R.drawable.ic_experience_care);
    }

    if (itemArrayList.get(position).getBdb_has_health_cer().equals("1")){
        (holder).health.setImageResource(R.drawable.ic_health_care);
    }*/

    if (freeBookingFragment.Place.equals("home") && itemArrayList.get(position).getIs_av_outside().equals("0")){
        (holder).place_out_disable.setVisibility(View.VISIBLE);
        (holder).addRequest.setVisibility(View.GONE);
    }

    ( holder).providerName.setText(itemArrayList.get(position).getSup_name());

    if(APICall.isGuest(context).equals("1"))
    {
        (holder).centerFavorite.setVisibility(View.INVISIBLE);
        (holder).doctorFavorite.setVisibility(View.INVISIBLE);
    }
    if(itemArrayList.get(position).getIs_fav_center().equals("1")) {
        (holder).centerFavorite.setImageResource(R.drawable.favorite);
        (holder).centerFavorite.setTag(R.drawable.favorite);
    }
    else
        (holder).centerFavorite.setTag(R.drawable.un_favorite);


    if(itemArrayList.get(position).getIs_fav_doctor().equals("1"))
    {
        (holder).doctorFavorite.setImageResource(R.drawable.favorite);
        (holder).doctorFavorite.setTag(R.drawable.favorite);

    }
    else
        (holder).doctorFavorite.setTag(R.drawable.un_favorite);


    (holder).doctorFavorite.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            if((holder).doctorFavorite.getTag().equals(R.drawable.favorite)) {
                (holder).doctorFavorite.setImageResource(R.drawable.un_favorite);
                APICall.sendUnFavorites(context,itemArrayList.get(position).getSup_id(),"2");
                (holder).doctorFavorite.setTag(R.drawable.un_favorite);

            }
            else {
                (holder).doctorFavorite.setImageResource(R.drawable.favorite);
                APICall.sendFavorites(context,itemArrayList.get(position).getSup_id(),"2");
                (holder).doctorFavorite.setTag(R.drawable.favorite);


            }


        }
    });

    (holder).centerFavorite.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            if((holder).centerFavorite.getTag().equals(R.drawable.favorite)) {
                (holder).centerFavorite.setImageResource(R.drawable.un_favorite);
                APICall.sendUnFavorites(context,itemArrayList.get(position).getHealthCntr_id(),"1");
                (holder).centerFavorite.setTag(R.drawable.un_favorite);

            }
            else {
                (holder).centerFavorite.setImageResource(R.drawable.favorite);
                APICall.sendFavorites(context,itemArrayList.get(position).getHealthCntr_id(),"1");
                (holder).centerFavorite.setTag(R.drawable.favorite);

            }


        }
    });


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
    ( holder).healthCntr.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Offers.bdb_booking_period = Integer.parseInt(itemArrayList.get(position).getBdb_booking_period());
            }catch (Exception e){
                e.printStackTrace();
            }
           // Log.e("PIDDDD","is"+bestOfferItems.get(position).getProvider_id());
            Log.e("Position","is"+position);
            Intent intent=new Intent(context, MainProviderActivity.class);
          //  intent.putExtra("provider_id",bestOfferItems.get(position).getProvider_id());
            intent.putExtra("lat",itemArrayList.get(position).getBdb_loc_lat());
            intent.putExtra("long",itemArrayList.get(position).getBdb_loc_long());
            intent.putExtra("provider_name",(holder).healthCntr.getText().toString());
            intent.putExtra("provider_id",itemArrayList.get(position).getHealthCntr_id());
            if((holder).centerFavorite.getTag().equals(R.drawable.favorite)) {
                intent.putExtra("is_fav","1");
            }
            else {
                intent.putExtra("is_fav","0");
            }
            intent.putExtra("health",itemArrayList.get(position).getBdb_has_health_cer());
            intent.putExtra("exp",itemArrayList.get(position).getBdb_has_experience_cer());
            context.startActivity(intent);
        }
    });
    APICall.getSalonLogoDltWhenEmptyWithCard(BeautyMainPage.context,itemArrayList.get(position).getLogo_id(),(holder).logo,(holder).cardView);

    ( holder).provider_rate.setText(itemArrayList.get(position).getRating());
    if(context.getResources().getString(R.string.locale).equals("ar"))
    {
        ( holder).speciality.setText(itemArrayList.get(position).getSpeciality_ar());
        ( holder).healthCntr.setText(itemArrayList.get(position).getHealthCntr_ar());
    }
    else
    {
        ( holder).speciality.setText(itemArrayList.get(position).getSpeciality());
        ( holder).healthCntr.setText(itemArrayList.get(position).getHealthCntr());
    }


    String deposit= BeautyMainPage.context.getString(R.string.dep_prcntg)+itemArrayList.get(position).getDeposit_prcntg()+" % ";
    ( holder).depositPrcntg.setText(deposit);
    ( holder).addRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(APICall.isGuest(context).equals("1"))
            {
                APICall.showNeedToSignInDialog(context);
            }
            else
            {
               /* Intent i = new Intent(BeautyMainPage.context, CreateRequestActivity.class);
                i.putExtra("sup_id",itemArrayList.get(position).getSup_id());

                RequestProvidersFragment.bdb_booking_period=itemArrayList.get(position).getBdb_booking_period();
                Log.e("t.bdb_booking_period","is"+ RequestProvidersFragment.bdb_booking_period);
                BeautyMainPage.context.startActivity(i);*/

                Intent i = new Intent(context, CreateRequestActivity.class);
                i.putExtra("sup_id",itemArrayList.get(position).getSup_id());
                i.putExtra("age", PersonalIndivRequest.clientAge);
                i.putExtra("relation",PersonalIndivRequest.clientRelation);
                i.putExtra("gender",PersonalIndivRequest.clientGender);
                i.putExtra("client_name",PersonalIndivRequest.clientName);

                BeautyMainPage.lat_out=itemArrayList.get(position).getBdb_loc_lat();
                BeautyMainPage.lang_out=itemArrayList.get(position).getBdb_loc_long();


               /* Intent i = new Intent(context, RelativesActivity.class);
                i.putExtra("sup_id",itemArrayList.get(position).getSup_id());
                i.putExtra("center_id",itemArrayList.get(position).getHealthCntr_id());
                i.putExtra("isBooking",true);
                i.putExtra("max_age",itemArrayList.get(position).getMax_age());
                i.putExtra("min_age",itemArrayList.get(position).getMin_age());
                i.putExtra("supported_gender",itemArrayList.get(position).getSupported_gender());
*/
                RequestProvidersFragment.bdb_booking_period=itemArrayList.get(position).getBdb_booking_period();
                context.startActivity(i);

            }
        }
    });
}
public class ListHolder extends RecyclerView.ViewHolder {
    TextView providerName,provider_rate,place_out_disable,depositPrcntg,healthCntr,speciality;
    ImageView addRequest,logo,place,health,exp,doctorFavorite,centerFavorite;
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
        healthCntr=itemView.findViewById(R.id.healthCntr);
        speciality=itemView.findViewById(R.id.speciality);
        centerFavorite=itemView.findViewById(R.id.centerFavorite);
        doctorFavorite=itemView.findViewById(R.id.doctorFavorite);
        place_out_disable=itemView.findViewById(R.id.place_out_disable);
    }
}
    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


}

