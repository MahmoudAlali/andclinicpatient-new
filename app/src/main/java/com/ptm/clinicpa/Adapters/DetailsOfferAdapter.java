package com.ptm.clinicpa.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ptm.clinicpa.DataModel.DaitelsOfferReservationClass;
import com.ptm.clinicpa.DataModel.Location_Beauty;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class DetailsOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    int layout;
    Location_Beauty[] location_beauties;
    boolean[] fav;
    ArrayList<DaitelsOfferReservationClass> itemArrayList;


    public DetailsOfferAdapter(Context context, ArrayList<DaitelsOfferReservationClass> itemArrayList){
        this.context=context;
        this.itemArrayList=itemArrayList;
    }

    /**
     * @param context
     * @param items
     */


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
        row = inflater.inflate(R.layout.details_offer_res_layout, parent, false);
        DetailsOfferAdapter.Item item=new DetailsOfferAdapter.Item(row);
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
            pro_name=itemView.findViewById(R.id.provider_name);
//            more_btn=itemView.findViewById(R.id.more_btn);
            service_compare=itemView.findViewById(R.id.service_compare);
//            service_details=itemView.findViewById(R.id.service_details);
            service_fav=itemView.findViewById(R.id.service_fav);

        }
    }
//
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
