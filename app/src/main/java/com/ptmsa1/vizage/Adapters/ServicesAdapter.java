package com.ptmsa1.vizage.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.CompareModel;
import com.ptmsa1.vizage.DataModel.DateClass;
import com.ptmsa1.vizage.DataModel.BrowseServiceItem;
import com.ptmsa1.vizage.DataModel.Location_Beauty;
import com.ptmsa1.vizage.Fragments.MyIndEffectsActivity;
import com.ptmsa1.vizage.Fragments.PlaceServiceFragment;
import com.ptmsa1.vizage.Activities.TabOne;
import com.ptmsa1.vizage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ListHolder> {
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
    ArrayList<BrowseServiceItem> itemArrayList;

    public ServicesAdapter(Context context, String[] items){
        this.context=context;
        this.items=items;
    }
    public ServicesAdapter(Context context, ArrayList<BrowseServiceItem> itemArrayList, int layout){
        this.context=context;
        this.itemArrayList=itemArrayList;
        this.layout=layout;
    }

    public ServicesAdapter(Context context, String[] items, Boolean list, int layout){
        this.context=context;
        this.items=items;
        this.list=list;
        this.layout=layout;
    }
    /**
     * @param context
     * @param items
     */
    public ServicesAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties , boolean []fav){
        this.context=context;
        this.items=items;
        this.price=price;
        this.rank=rank;
        this.cities=cities;
        this.location_beauties=location_beauties;
        this.fav=fav;
    }


    boolean grid ;
    public ServicesAdapter(Context context, String items[], String[] price, String[] rank, String[] cities, Location_Beauty[] location_beauties, boolean grid, boolean[] fav){
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
        try {
                ( holder).service_name.setText(itemArrayList.get(position).getBdb_sup_name());
                (holder).pro_name.setText(itemArrayList.get(position).getBdb_sup_name());
//                if (position==postion1 || position==postion2){
//                    ((Item) holder).service_compare.setChecked(true);
//                }
            //check check box is already checked or not because recyclerview use recycling method to reuse view


            String deposit= BeautyMainPage.context.getString(R.string.dep_prcntg)+itemArrayList.get(position).getBdb_client_deposit_ratio()+" % ";
            ( holder).depositPrcntg.setText(deposit);

            Log.e("ISCHECKED:"+holder.getLayoutPosition(),isChecked.containsKey(position)+"");
            if (isChecked.containsKey(holder.getLayoutPosition())) {
                holder.service_compare.setChecked(isChecked.get(position));

            } else {
                ( holder).service_compare.setChecked(false);
            }
            APICall.getSalonLogo(BeautyMainPage.context,itemArrayList.get(position).getLogoId(),(holder).logo);

            if(itemArrayList.get(position).getIs_fav_sup().equals("0"))
                (holder).service_fav.setImageResource(R.drawable.un_favorite);
            else
                (holder).service_fav.setImageResource(R.drawable.favorite);


            (holder).service_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemArrayList.get(position).getNewFav().equals("0"))
                    {
                        itemArrayList.get(position).setNewFav("1");
                        (holder).service_fav.setImageResource(R.drawable.favorite);
                        APICall.sendFavorites(BeautyMainPage.context,itemArrayList.get(position).getSup_id(),"1");

                    }
                    else {
                        itemArrayList.get(position).setNewFav("0");
                        APICall.sendUnFavorites(BeautyMainPage.context,itemArrayList.get(position).getSup_id(),"1");
                        (holder).service_fav.setImageResource(R.drawable.un_favorite);
                    }


                }
            });
            ( holder).service_compare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("LayoutPostion",holder.getLayoutPosition()+"");

                        if (( holder).service_compare.isChecked()){
                            if (TabOne.compareModels.size()==3){
                                ( holder).service_compare.setChecked(false);

                                APICall.showSweetDialog(context, ((AppCompatActivity)context).getResources().getString(R.string.alert),
                                        ((AppCompatActivity)context).getResources().getString(R.string.cant_compare_more_than_three_ser));
//                            Toast.makeText(context,"Can't compare more than two services..",Toast.LENGTH_SHORT).show();
                            }else {
                                if (TabOne.compareModels.size() == 0) {
//                                TabOne.compareModels.size() = 1;
                                    srName1 = ( holder).service_name.getText().toString();
                                    spname1 = ( holder).pro_name.getText().toString();
                                    ev1 = itemArrayList.get(position).getTotalRating() + "*";
                                    price1 = ( holder).service_price.getText().toString();
                                    bdb_ser_home = itemArrayList.get(position).getBdb_ser_home();
                                    bdb_ser_salon = itemArrayList.get(position).getBdb_ser_salon();
                                    bdb_ser_hall = itemArrayList.get(position).getBdb_ser_hall();
                                    bdb_hotel = itemArrayList.get(position).getBdb_hotel();
                                    place1 = PlaceServiceFragment.placeSpinner.getSelectedItem().toString();

                                    TabOne.compareModels.add(new CompareModel(srName1,spname1,ev1,price1,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel,place1));


                                    Log.e("plc1", bdb_ser_home + "," + bdb_ser_salon + "," + bdb_ser_hall + "," + bdb_hotel);
//                                postion1=position;
                                } else if (TabOne.compareModels.size() == 1) {
//                                comparenum = 2;
                                    srName2 = ( holder).service_name.getText().toString();
                                    spname2 = ( holder).pro_name.getText().toString();
                                    ev2 = itemArrayList.get(position).getTotalRating() + "*";
                                    price2 = ( holder).service_price.getText().toString();
                                    place2 = PlaceServiceFragment.placeSpinner.getSelectedItem().toString();
                                    bdb_ser_home1 = itemArrayList.get(position).getBdb_ser_home();
                                    bdb_ser_salon1 = itemArrayList.get(position).getBdb_ser_salon();
                                    bdb_ser_hall1 = itemArrayList.get(position).getBdb_ser_hall();
                                    bdb_hotel1 = itemArrayList.get(position).getBdb_hotel();
                                    TabOne.compareModels.add(new CompareModel(srName2,spname2,ev2,price2,bdb_ser_home1,bdb_ser_salon1,bdb_ser_hall1,bdb_hotel1,place2));

//                                postion2=position;
                                    Log.e("plc2", bdb_ser_home1 + "," + bdb_ser_salon1 + "," + bdb_ser_hall1 + "," + bdb_hotel1);
                                } else if (TabOne.compareModels.size() == 2) {
//                                comparenum=3;
                                    srName3= ( holder).service_name.getText().toString();
                                    spname3= ( holder).pro_name.getText().toString();
                                    ev3= itemArrayList.get(position).getTotalRating()+"*";
                                    price3=( holder).service_price.getText().toString();
                                    place3= PlaceServiceFragment.placeSpinner.getSelectedItem().toString();
                                    bdb_ser_home2=itemArrayList.get(position).getBdb_ser_home();
                                    bdb_ser_salon2=itemArrayList.get(position).getBdb_ser_salon();
                                    bdb_ser_hall2=itemArrayList.get(position).getBdb_ser_hall();
                                    bdb_hotel2=itemArrayList.get(position).getBdb_hotel();
//                                postion2=position;
                                    TabOne.compareModels.add(new CompareModel(srName3,spname3,ev3,price3,bdb_ser_home2,bdb_ser_salon2,bdb_ser_hall2,bdb_hotel2,place3));

                                    Log.e("plc3",bdb_ser_home2+","+bdb_ser_salon2+","+bdb_ser_hall2+","+bdb_hotel2);

                                }
                            }
                        }else {
                            for (int i=0;i<TabOne.compareModels.size();i++){
                                Log.e("SPNAMECModel",TabOne.compareModels.get(i).getSpname());
                                Log.e("SPNAMERe",( holder).pro_name.getText().toString());
                                if (TabOne.compareModels.get(i).getSpname().equals(( holder).pro_name.getText().toString())){
                                    TabOne.compareModels.remove(i);
                                }
                            }
                        }

                    Log.e("COMPARE_SIZE",TabOne.compareModels.size()+"");
                    }
                });

                ( holder).service_price.setText(itemArrayList.get(position).getPriceByFilter()+" R");
               try {
                   ( holder).service_rate.setEnabled(false);
                   ( holder).service_rate.setRating(Float.parseFloat(itemArrayList.get(position).getTotalRating()));
               }catch (Exception ee){
                   ee.printStackTrace();
               }

                ( holder).service_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                        Log.e("SERSUPID",ser_sup_id);
//                        try {
//                            ListOfDates(Integer.parseInt(itemArrayList.get(position).getBdb_booking_period()));
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                        Intent intent = new Intent(BeautyMainPage.context, MyIndEffectsActivity.class);
                        //                        intent.putExtra("Service Name","")
                        TabOne.bdb_sup_id=itemArrayList.get(position).getSup_id();
                        TabOne.ser_id=itemArrayList.get(position).getSer_id();
                        TabOne.ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                        TabOne.ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                        TabOne.bdb_time=itemArrayList.get(position).getBdb_time();
                        TabOne.bdb_is_bride=itemArrayList.get(position).getBdb_isbride_ser();
//
                        context.startActivity(intent);



                    }
                });


        }catch (Exception e){
            e.printStackTrace();

            }
            }
    private HashMap<Integer, Boolean> isChecked = new HashMap<>();
    public class ListHolder extends RecyclerView.ViewHolder {
//        CheckBox cb_product;
        TextView  service_name,service_price;
        TextView pro_name, depositPrcntg;
        RatingBar service_rate;
        ImageView service_add,service_fav,logo;
        CheckBox service_compare;
        LinearLayout service_details;
        public ListHolder(View itemView) {
            super(itemView);
            service_compare = (CheckBox) itemView.findViewById(R.id.service_compare);
            service_name=itemView.findViewById(R.id.service_name);
            service_price=itemView.findViewById(R.id.service_price);
            service_add=itemView.findViewById(R.id.service_add);
            service_rate=itemView.findViewById(R.id.service_rate);
            pro_name=itemView.findViewById(R.id.provider_name);
//            more_btn=itemView.findViewById(R.id.more_btn);
            service_compare=itemView.findViewById(R.id.service_compare);
//            service_details=itemView.findViewById(R.id.service_details);
            service_fav=itemView.findViewById(R.id.service_fav);
            depositPrcntg=itemView.findViewById(R.id.depPerc);

            service_compare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    //save checked data in hash map on check change
                    isChecked.put(getAdapterPosition(), b);

                }
            });
            logo=itemView.findViewById(R.id.logoImg);

        }

    }

    @Override
    public int getItemCount() {
            return itemArrayList.size();
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView  service_name,service_price;
        TextView pro_name;
        TextView service_rate,depositPrcntg;
        ImageView service_add,service_fav,logo;
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
            pro_name=itemView.findViewById(R.id.provider_name);
//            more_btn=itemView.findViewById(R.id.more_btn);
            service_compare=itemView.findViewById(R.id.service_compare);
//            service_details=itemView.findViewById(R.id.service_details);
            service_fav=itemView.findViewById(R.id.service_fav);
            logo=itemView.findViewById(R.id.logoImg);
            depositPrcntg=itemView.findViewById(R.id.depPerc);

        }
    }

    public static ArrayList<DateClass>  ListOfDates(int priod){
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
            dateClasses.add(new DateClass(dayofweek,now.get(Calendar.YEAR)+"-"+month+"-"+now.get(Calendar.DAY_OF_MONTH)));

        }


        System.out.print(".");
        for (int i=0;i<dateClasses.size();i++){
            Log.e("EDFR",dateClasses.get(i).getDayOfWeek()+":"+dateClasses.get(i).getDayOfMonth());

        }
        Log.e("EDFR", Arrays.toString(days));
        return dateClasses;
    }
    public static JSONArray getFavorites(final ArrayList<BrowseServiceItem> itemArrayList)
    {
        // String favoriteStr="\" items \" :[";
        JSONArray favoriteStr =new JSONArray();
        JSONObject temp;
        for(int i=0;i<itemArrayList.size();i++)
        {
            if(!itemArrayList.get(i).getNewFav().equals(itemArrayList.get(i).getIs_fav_sup()))
            {
                temp=new JSONObject();
                try {
                    temp.put("bdb_item_id",itemArrayList.get(i).getSup_id());
                    temp.put("bdb_type","0");
                    favoriteStr.put(temp);
                }
                catch (Exception e){}
            }
        }
        return favoriteStr;
    }
}
