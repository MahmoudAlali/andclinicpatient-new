package com.ptmsa1.clinicclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.API.HintArrayAdapter;
import com.ptmsa1.clinicclient.Activities.ProviderSerAndOfferPKG.MainProviderActivity;
import com.ptmsa1.clinicclient.Activities.TabOne;
import com.ptmsa1.clinicclient.DataModel.BrowseServiceItem;
import com.ptmsa1.clinicclient.DataModel.DataService;
import com.ptmsa1.clinicclient.DataModel.DateClass;
import com.ptmsa1.clinicclient.Fragments.MyIndEffectsActivity;
import com.ptmsa1.clinicclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesProviderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TextView canceltime,canceldate,okdate,oktime;

    Context context;
    ArrayList<DataService> dataServices;
    ArrayList<BrowseServiceItem> itemArrayList;
    int [] servicsImgsBasic ={ R.drawable.hair_basic,
            R.drawable.makeup_basic,
            R.drawable.massage_basic,
            R.drawable.spa_basic,
            R.drawable.nails_basic,
            R.drawable.body_basic,
            R.drawable.skin_basic,
            R.drawable.eyebrows_basic
    };
    /**
     * @param context

     */

    public ServicesProviderAdapter(Context context,ArrayList<BrowseServiceItem> itemArrayList){
        this.context=context;
        this.itemArrayList=itemArrayList;

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

             row = inflater.inflate(R.layout.service_layout_provider, parent, false);

        ServicesProviderAdapter.Item item=new ServicesProviderAdapter.Item(row);
        return item;
    }




    String date;
    Dialog dialog1,dialog;
    public static int placePos=0;
    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        if (APICall.ln.equals("ar"))
        ((Item)holder).name.setText(itemArrayList.get(position).getBdb_name_ar());
        else
            ((Item)holder).name.setText(itemArrayList.get(position).getBdb_name());

        ArrayList<String> places= new ArrayList<>();

        places.add(context.getResources().getString(R.string.PlaceService));

        if (itemArrayList.get(position).getBdb_ser_salon().equals("1")) {
            places.add(context.getResources().getString(R.string.salon)+":"+itemArrayList.get(position).getBdb_ser_salon_price());
        }else {
            places.add(context.getResources().getString(R.string.salon)+": 0");
        }

        if (itemArrayList.get(position).getBdb_ser_home().equals("1")) {
            places.add(context.getResources().getString(R.string.home)+":"+itemArrayList.get(position).getBdb_ser_home_price());
        }else {
            places.add(context.getResources().getString(R.string.home)+": 0");
        }

        if (itemArrayList.get(position).getBdb_ser_hall().equals("1")) {
            places.add(context.getResources().getString(R.string.hall)+":"+itemArrayList.get(position).getBdb_ser_hall_price());
        }else {
            places.add(context.getResources().getString(R.string.hall)+": 0");
        }

        if (itemArrayList.get(position).getBdb_hotel().equals("1")) {
            places.add(context.getResources().getString(R.string.hotel)+":"+itemArrayList.get(position).getBdb_hotel_price());
        }else {
            places.add(context.getResources().getString(R.string.hotel)+": 0");
        }
        ((Item)holder).image.setImageResource(servicsImgsBasic[Integer.parseInt(itemArrayList.get(position).getCatId())]);

        APICall.getSalonLogo(context,itemArrayList.get(position).getImage_1(),((Item) holder).image);
        APICall.getSalonLogo(context,itemArrayList.get(position).getImage_2(),((Item) holder).image2);


        HintArrayAdapter adapter=new HintArrayAdapter(context,0);
        adapter.addAll(places);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ((Item)holder).place.setAdapter(adapter);

        ((Item)holder).add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainProviderActivity.date.getText().toString().equals(context.getResources().getString(R.string.date))){
                    APICall.showSweetDialog(context,"",context.getResources().getString(R.string.please_add_date));
                }else if (MainProviderActivity.date.getText().toString().equals(context.getResources().getString(R.string.MyLocation))){
                    APICall.showSweetDialog(context,"",context.getResources().getString(R.string.please_add_location));
                }else if (((Item)holder).place.getSelectedItemPosition()==0){
                    APICall.showSweetDialog(context,"",context.getResources().getString(R.string.add_service_place));
                }else if (((Item)holder).place.getSelectedItemPosition()==1  && (itemArrayList.get(position).getBdb_ser_salon_price().equals("0"))){

                        APICall.showSweetDialog(context,"",context.getResources().getString(R.string.pro_not_support_place));
                }else if (((Item)holder).place.getSelectedItemPosition()==2 && (itemArrayList.get(position).getBdb_ser_home_price().equals("0"))){

                        APICall.showSweetDialog(context,"",context.getResources().getString(R.string.pro_not_support_place));

                }else if (((Item)holder).place.getSelectedItemPosition()==3 && (itemArrayList.get(position).getBdb_ser_hall_price().equals("0"))){

                        APICall.showSweetDialog(context,"",context.getResources().getString(R.string.pro_not_support_place));

                }else if (((Item)holder).place.getSelectedItemPosition()==4 && (itemArrayList.get(position).getBdb_hotel_price().equals("0"))){

                        APICall.showSweetDialog(context,"",context.getResources().getString(R.string.pro_not_support_place));

                }else {

                    ServicesAdapter.ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                            Log.e("SERSUPID",ServicesAdapter.ser_sup_id);
                            ListOfDates(Integer.parseInt(itemArrayList.get(position).getBdb_booking_period()));
                            Intent intent = new Intent(context, MyIndEffectsActivity.class);
                            //                        intent.putExtra("Service Name","")
                            placePos=((Item)holder).place.getSelectedItemPosition();
                            TabOne.bdb_sup_id=itemArrayList.get(position).getSup_id();
                            TabOne.ser_id=itemArrayList.get(position).getSer_id();
                            TabOne.ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                            TabOne.ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                            TabOne.bdb_time=itemArrayList.get(position).getBdb_time();
                            TabOne.bdb_is_bride=itemArrayList.get(position).getBdb_isbride_ser();
//
                            context.startActivity(intent);

                }
            }
        });




    }





//    public  static ArrayList<DateClass> dateClasses=new ArrayList<>() ;



    public static ArrayList<DateClass> ListOfDates(int priod){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Log.e("Today is ","");

        String[] days = new String[priod];
//        int delta = now.get(GregorianCalendar.DAY_OF_WEEK); //add 2 if your week start on monday
//        now.add(Calendar.DAY_OF_MONTH, delta );
        for (int i = 0; i < priod; i++)
        {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
            Log.e("Today is ","");
            String dayofweek="";
            int day = now.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case 1:
                    dayofweek="Sunday";
                    Log.e("Sunday",day+"");
                    break;
                case 2:
                    dayofweek="Monday";

                    Log.e("Monday",day+"");
                    break;
                case 3:
                    dayofweek="Tuesday";

                    Log.e("Tuesday",day+"");
                    break;
                case 4:
                    dayofweek="Wednesday";

                    Log.e("Wednesday",day+"");
                    break;
                case 5:
                    dayofweek="Thursday";

                    Log.e("Thursday",day+"");
                    break;
                case 6:
                    dayofweek="Friday";

                    Log.e("Friday",day+"");
                    break;
                case 7:
                    dayofweek="Saturday";

                    Log.e("Saturday",day+"");
            }
            int month=now.get(Calendar.MONTH)+1;
            ServicesAdapter.dateClasses.add(new DateClass(dayofweek,now.get(Calendar.YEAR)+"-"+month+"-"+now.get(Calendar.DAY_OF_MONTH)));

        }


        System.out.print(".");
        for (int i=0;i<ServicesAdapter.dateClasses.size();i++){
            Log.e("EDFR",ServicesAdapter.dateClasses.get(i).getDayOfWeek()+":"+ServicesAdapter.dateClasses.get(i).getDayOfMonth());

        }
        Log.e("EDFR", Arrays.toString(days));
        return ServicesAdapter.dateClasses;
    }


    @Override
    public int getItemCount() {
        try {
            return itemArrayList.size();

        }catch (Exception e){
            return dataServices.size();

        }
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView name;
        Spinner place;
        ImageView add,image,image2;

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            place=itemView.findViewById(R.id.place);
            add=itemView.findViewById(R.id.add);
            image=itemView.findViewById(R.id.image);
            image2=itemView.findViewById(R.id.image2);
        }
    }
}
