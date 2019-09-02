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
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class GroupOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static Dialog dialog;
    static TextView dateShowed;
    static String dateShow="Date";
    Context context;
    public static boolean list;
    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
//    int layout;
    ArrayList<BrowseServiceItem> itemArrayList;

    ArrayList<GroupOfferClass> itemsClassArrayList;

    public GroupOfferAdapter(Context context, String[] items){
        this.context=context;
        this.items=items;
    }


    public GroupOfferAdapter(Context context, ArrayList<GroupOfferClass> itemsClassArrayList){
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
        row = inflater.inflate(R.layout.multi_date_offer_layout_adapter, parent, false);
        GroupOfferAdapter.Item item=new GroupOfferAdapter.Item(row);
        return item;
    }


    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).service_name.setText(itemsClassArrayList.get(position).getServiceName());
        ((Item)holder).select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DateClass> arrayList=new ArrayList<>();
                arrayList.add(new DateClass("saturday","27/9/2019"));
                arrayList.add(new DateClass("sunday","28/9/2019"));
                arrayList.add(new DateClass("Monday","28/9/2019"));
                arrayList.add(new DateClass("saturday","10/10/2019"));
                arrayList.add(new DateClass("sunday","11/10/2019"));
                arrayList.add(new DateClass("monday","12/10/2019"));
                arrayList.add(new DateClass("saturday","20/10/2019"));
                arrayList.add(new DateClass("sunday","21/1/2019"));
                arrayList.add(new DateClass("monday","22/10/2019"));
                 dialog=new Dialog(BeautyMainPage.context);
                dialog.setContentView(R.layout.select_date_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                RecyclerView recyclerView=dialog.findViewById(R.id.recycleview);
                CalenderSelectAdapter calenderAdapter=new CalenderSelectAdapter(context,arrayList);
                LinearLayoutManager manager = new LinearLayoutManager(context.getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(calenderAdapter);
                dateShowed=((Item)holder).date;
                Log.e("ItemCount",calenderAdapter.getItemCount()+"");
                dialog.show();
            }
        });
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
            select_date=itemView.findViewById(R.id.select_date);
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
