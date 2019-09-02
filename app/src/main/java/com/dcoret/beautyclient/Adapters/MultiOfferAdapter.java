package com.dcoret.beautyclient.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.DateClass;
import com.dcoret.beautyclient.DataClass.GroupOfferClass;
import com.dcoret.beautyclient.DataClass.MultiDateOfferClass;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class MultiOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static Dialog dialog;
    static TextView dateShowed;
    static String dateShow="Date";
    Context context;
    public static boolean list;
    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
//    int layout;
    ArrayList<BrowseServiceItem> itemArrayList;

    ArrayList<MultiDateOfferClass> itemsClassArrayList;

    public MultiOfferAdapter(Context context, String[] items){
        this.context=context;
        this.items=items;
    }


    public MultiOfferAdapter(Context context, ArrayList<MultiDateOfferClass> itemsClassArrayList){
        this.context=context;
        this.itemsClassArrayList=itemsClassArrayList;
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
        row = inflater.inflate(R.layout.multi_offer_adpter_layout, parent, false);
        MultiOfferAdapter.Item item=new MultiOfferAdapter.Item(row);
        return item;
    }


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//        ((Item)holder).service_name.setText(itemsClassArrayList.get(position).getName());
//        ((Item)holder).select_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
            }

    @Override
    public int getItemCount() {
            return itemsClassArrayList.size();
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView  date,service_name;
        ImageView select_date;
        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
//            select_date=itemView.findViewById(R.id.select_date);
            service_name=itemView.findViewById(R.id.service_name);
        }
    }

//    public static ArrayList<DateClass> ListOfDates(int priod){
//        Calendar now = Calendar.getInstance();
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//        Log.e("Today is ","");
//
//        String[] days = new String[priod];
////        int delta = now.get(GregorianCalendar.DAY_OF_WEEK); //add 2 if your week start on monday
////        now.add(Calendar.DAY_OF_MONTH, delta );
//        for (int i = 0; i < priod; i++)
//        {
//            days[i] = format.format(now.getTime());
//            now.add(Calendar.DAY_OF_MONTH, 1);
//            Log.e("Today is ","");
//            String dayofweek="";
//            int day = now.get(Calendar.DAY_OF_WEEK);
//
//            switch (day) {
//                case 1:
//                    dayofweek="Sunday";
//                    Log.e("Sunday",day+"");
//                    break;
//                case 2:
//                    dayofweek="Monday";
//
//                    Log.e("Monday",day+"");
//                    break;
//                case 3:
//                    dayofweek="Tuesday";
//
//                    Log.e("Tuesday",day+"");
//                    break;
//                case 4:
//                    dayofweek="Wednesday";
//
//                    Log.e("Wednesday",day+"");
//                    break;
//                case 5:
//                    dayofweek="Thursday";
//
//                    Log.e("Thursday",day+"");
//                    break;
//                case 6:
//                    dayofweek="Friday";
//
//                    Log.e("Friday",day+"");
//                    break;
//                case 7:
//                    dayofweek="Saturday";
//
//                    Log.e("Saturday",day+"");
//            }
//            int month=now.get(Calendar.MONTH)+1;
//            dateClasses.add(new DateClass(dayofweek,now.get(Calendar.YEAR)+"-"+month+"-"+now.get(Calendar.DAY_OF_MONTH)));
//
//        }
//
//
//        System.out.print(".");
//        for (int i=0;i<dateClasses.size();i++){
//            Log.e("EDFR",dateClasses.get(i).getDayOfWeek()+":"+dateClasses.get(i).getDayOfMonth());
//
//        }
//        Log.e("EDFR", Arrays.toString(days));
//        return dateClasses;
//    }

}
