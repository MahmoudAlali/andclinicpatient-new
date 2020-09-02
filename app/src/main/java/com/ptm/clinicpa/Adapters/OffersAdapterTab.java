package com.ptm.clinicpa.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.CreateRequestActivity;
import com.ptm.clinicpa.Activities.MultiDateOffer.MultiDateOfferBooking;
import com.ptm.clinicpa.Activities.GroupOffer.SingleDateMultiClientOfferBooking;
//import com.dcoret.beautyclient.Activities.SingleDateOfferBooking;
import com.ptm.clinicpa.Activities.RelativesActivity;
import com.ptm.clinicpa.Activities.SingleOffer.SingleDateOfferBooking;
import com.ptm.clinicpa.Activities.TabTwo;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.DataExample.OffersData;
import com.ptm.clinicpa.DataModel.OfferModel;
import com.ptm.clinicpa.Fragments.MyOffersFragment;
import com.ptm.clinicpa.Fragments.PersonalIndivOfferRequest;
import com.ptm.clinicpa.Fragments.PersonalIndivRequest;
import com.ptm.clinicpa.MapsActivityLocation;
import com.ptm.clinicpa.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OffersAdapterTab extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    ArrayList<OfferModel> offers=new ArrayList<>();
    String name;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    String tab;

    public OffersAdapterTab(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public OffersAdapterTab(Context context, String items[], boolean grid){
        this.context=context;
        this.items=items;
        this.grid=grid;
    }
    public OffersAdapterTab(Context context, ArrayList<OfferModel> offers,String f){
        this.context=context;
        this.offers=offers;
        this.tab=f;

       // new OffersData(offers);
    }
    public OffersAdapterTab(Context context, ArrayList<DataOffer> offers){
        this.context=context;
       // this.offers=offers;

        // new OffersData(offers);
    }


    public static int placePos=-1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View row;
             row = inflater.inflate(R.layout.offers_layout_tab_last2, parent, false);
            OffersAdapterTab.Item item = new OffersAdapterTab.Item(row);

        return item;
    }
    DecimalFormat doub=new DecimalFormat("#.##");

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//        DecimalFormat df = new DecimalFormat("0.0");

      /*  ((Item)holder).exp.setImageDrawable(null);
        ((Item)holder).health.setImageDrawable(null);
*/
       /* if (offers.get(position).getBdb_has_experience_cer().equals("1")){
            ((Item)holder).exp.setImageResource(R.drawable.ic_experience_care);
        }

        if (offers.get(position).getBdb_has_health_cer().equals("1")){
            ((Item)holder).health.setImageResource(R.drawable.ic_health_care);
        }*/



        /*if (offers.get(position).getBdb_is_morning_offer().equals("1")) {
            ((Item) holder).morning_offer.setText(context.getResources().getString(R.string.morning_offer));
        }else {
            ((Item) holder).morning_offer.setText(context.getResources().getString(R.string.all_day_offer));

        }*/
       /* ((Item)holder).placeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!offers.get(position).getLatitude().equals("")
                        &&!offers.get(position).getLatitude().equals("null")
                        &&!offers.get(position).getLongitude().equals("")
                        &&!offers.get(position).getLongitude().equals("null")
                ){
                    Intent intent=new Intent(context, MapsActivityLocation.class);
                    intent.putExtra("lat",Double.parseDouble(offers.get(position).getLatitude()));
                    intent.putExtra("lang",Double.parseDouble(offers.get(position).getLongitude()));
                    context.startActivity(intent);
                }
            }
        });*/
       // float old_prc=Float.parseFloat(Double.parseDouble(offers.get(position).getOldPrice())+"");
        Log.e("old_prc","old_prc :"+offers.get(position).getOldPrice());
        if(offers.get(position).getOldPrice().equals("null"))
        {
            ((Item)holder).old_price.setText(doub.format(Double.parseDouble(offers.get(position).getDiscount() ))+ "% ");
        }
        else
        {
            float old_prc=Float.parseFloat(offers.get(position).getOldPrice());
            ((Item)holder).old_price.setText(old_prc+"");

        }

        float discountval=Float.parseFloat(Double.parseDouble(offers.get(position).getDiscount())+"");
     //   old_prc = Float.parseFloat(df.format(old_prc));
     //   discountval = Float.parseFloat(df.format(discountval));
        if(tab.equals("center"))
        {
            ((Item)holder).centerNameLayout.setVisibility(View.GONE);
        }
        if(context.getString(R.string.locale).equals("ar")){
            ((Item)holder).centerName.setText(context.getString(R.string.salon)+": " +offers.get(position).getHealth_center_name_ar());
            ((Item)holder).speciality.setText(context.getString(R.string.speciality_points)+" " +offers.get(position).getSpecialization_ar());

        }
        else
        {
            ((Item)holder).centerName.setText(context.getString(R.string.salon)+": " +offers.get(position).getHealth_center_name_en());
            ((Item)holder).speciality.setText(context.getString(R.string.speciality_points)+" "  +offers.get(position).getSpecialization_en());

        }
         if(offers.get(position).getIs_fav_center().equals("1"))
             ((Item)holder).is_fav_center.setImageResource(R.drawable.favorite);
        if(offers.get(position).getIs_fav_doctor().equals("1"))
            ((Item)holder).is_fav_doctor.setImageResource(R.drawable.favorite);
        ((Item)holder).doctorName.setText(context.getString(R.string.dr) +offers.get(position).getBdb_doctor_name());

        String STR;
        if(offers.get(position).getSupported_gender().equals("0"))
            STR=context.getString(R.string.providedGender)+context.getString( R.string.males);
        else if(offers.get(position).getSupported_gender().equals("1"))
            STR=context.getString(R.string.providedGender)+context.getString(R.string.females);
        else
            STR=context.getString(R.string.providedGender)+context.getString(R.string.females_and_males);

        if (offers.get(position).getBdb_offer_place().equals("0")){
            STR+=" "+context.getString(R.string.in)+" "+context.getString(R.string.salon);
        }else if (offers.get(position).getBdb_offer_place().equals("1")){
            STR+=" "+context.getString(R.string.in)+" "+context.getString(R.string.home);
        }
        ((Item)holder).supportedGender.setText(STR);
        String s;
        if(offers.get(position).getMaxAge().equals(offers.get(position).getMinAge()))
        {
            if(offers.get(position).getMinAge().equals("0"))
                s=context.getString(R.string.lessThanYear);
            else if(offers.get(position).getMinAge().equals("1"))
                s=context.getString(R.string.oneYear);
            else if(offers.get(position).getMinAge().equals("2"))
                s=context.getString(R.string.twoYears);
            else
                s=context.getString(R.string.age2)+" "+offers.get(position).getMinAge()+" "+context.getString(R.string.years);
           // s=offers.get(position).getMinAge().equals("0")?context.getString(R.string.lessThanYear):context.getString(R.string.age2)+" "+offers.get(position).getMinAge()+" "+context.getString(R.string.years);
        }
        else
        {
            if(offers.get(position).getMinAge().equals("0")&&offers.get(position).getMaxAge().equals("1"))
                s=context.getString(R.string.lessThanYear);
            else if(offers.get(position).getMinAge().equals("0"))
                s= context.getString(R.string.lessThanYear)+" ~ "+offers.get(position).getMaxAge()+" "+context.getString(R.string.years);
            else
                s= context.getString(R.string.age2)+" "+offers.get(position).getMinAge()+" ~ "+offers.get(position).getMaxAge()+" "+context.getString(R.string.years);
        }
           // s= /*context.getString(R.string.age2)+*/" "+a+" ~ "+offers.get(position).getMaxAge()+" "+context.getString(R.string.years);

        ((Item)holder).age.setText(s);


        ((Item)holder).new_price.setText(offers.get(position).getNewPrice());
        ((Item)holder).num_of_times.setText(context.getResources().getText(R.string.num_of_times)+offers.get(position).getNum_of_times());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDateString=null ;
        Date d = null;
        try {
            d = sdf.parse(offers.get(position).getBdb_offer_end());
            newDateString= sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((Item)holder).offer_end.setText(context.getResources().getText(R.string.end_offer)+newDateString);
        if (offers.get(position).getBdb_offer_type().equals("1") ||offers.get(position).getBdb_offer_type().equals("4")) {
            ((Item) holder).offer_type.setText(R.string.ind_offer);
        }else if (offers.get(position).getBdb_offer_type().equals("2") ||offers.get(position).getBdb_offer_type().equals("5")) {
            ((Item) holder).offer_type.setVisibility(View.VISIBLE);
        }else if (offers.get(position).getBdb_offer_type().equals("3") ||offers.get(position).getBdb_offer_type().equals("6")) {
            ((Item) holder).offer_type.setText(R.string.group_offer);
        }

        String pack = context.getString(R.string.packCode)+": "+offers.get(position).getBdb_pack_code();
        ((Item)holder).packId.setText(pack);
//        ((Item)holder).offer_type.setText(offers.get(position).getBdb_offer_type());
        String on= context.getResources().getString(R.string.on);
        String sevices= context.getResources().getString(R.string.ser);
        String oneService= context.getResources().getString(R.string.oneService);
        if(offers.get(position).getService_count().equals("1"))
            ((Item) holder).onServices.setText(oneService );
        else
            ((Item) holder).onServices.setText(on+" " +offers.get(position).getService_count()+ " "+sevices );
        if(context.getResources().getString(R.string.locale).equals("ar"))
            ((Item) holder).total_dis.setText(APICall.convertToArabic(doub.format(Double.parseDouble(offers.get(position).getDiscount() ))+ "% "));
        else
            ((Item) holder).total_dis.setText(doub.format(Double.parseDouble(offers.get(position).getDiscount() ))+ "% ");

      //  ((Item)holder).discount.setText(discountval+"% "+context.getResources().getString(R.string.on)+" "+offers.get(position).getService_count()+" "+context.getResources().getString(R.string.onservice));
        ((Item)holder).old_price.setPaintFlags(((Item)holder).old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ((Item)holder).info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    JSONArray jsonArray= offers.get(position).getPack_data();

                    PopupMenu popup = new PopupMenu(context,((Item)holder).info);
                    for(int i = 0; i< offers.get(position).getSersup_ids().size(); i++){
//                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String bdb_ser_name= offers.get(position).getSersup_ids().get(i).getBdb_name ();
                        Log.e("SHOWSoffers",bdb_ser_name);
                        popup.getMenu().add(bdb_ser_name);
                    }
                    popup.show();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        /*if (offers.get(position).getBdb_is_old_on().equals("0")){
            ((Item)holder).age.setText(R.string.child);
        }else if (offers.get(position).getBdb_is_old_on().equals("1")){
            ((Item)holder).age.setText(R.string.Adult);
        }else if (offers.get(position).getBdb_is_old_on().equals("2")){
            ((Item)holder).age.setText(R.string.allAges);
        }*/

     //   String deposit= BeautyMainPage.context.getString(R.string.dep_prcntg)+offers.get(position).getDeposit_ratio()+" % ";
        ( (Item)holder).depositPrcntg.setText("deposit");


        ((Item)holder).add_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(APICall.isGuest(context).equals("1"))
                {
                    APICall.showNeedToSignInDialog(context,"offer","1");
                }
                else
                {
                    int[] location = new int[2];
                    Log.e("ERR","GGGGG");
                    v.getLocationOnScreen(location);
                    //Initialize the Point with x, and y positions
                    Point point = new Point();
                    point.x = location[0];
                    point.y = location[1];
                    showInfoPopup(context,point,offers.get(position).getDoctor_id()
                            ,offers.get(position).getMaxAge(),offers.get(position).getMinAge(),
                            offers.get(position).getSupported_gender(),MyOffersFragment.filterMyLocationLatNum,
                            MyOffersFragment.filterMyLocationLngNum,offers.get(position).getHealth_center_id(),offers.get(position).getBdb_pack_code());

                }




               /* Intent i = new Intent(BeautyMainPage.context, RelativesActivity.class);
                i.putExtra("center_id",offers.get(position).getHealth_center_id());
                i.putExtra("isBooking",true);
                i.putExtra("is_offer",true);
                i.putExtra("sup_id",offers.get(position).getDoctor_id());
                i.putExtra("pack_code",offers.get(position).getBdb_pack_code());
                i.putExtra("longNum", MyOffersFragment.filterMyLocationLngNum);
                i.putExtra("latNum",MyOffersFragment.filterMyLocationLatNum);
                i.putExtra("max_age",offers.get(position).getMaxAge());
                i.putExtra("min_age",offers.get(position).getMinAge());
                i.putExtra("supported_gender",offers.get(position).getSupported_gender());

                context.startActivity(i);*/
            }
        });
    }
    private static void showInfoPopup(final Context context , Point p,final  String sup_id,final String max_age,
                                      final String min_age,final String supported_gender,final String latNum,
                                      final String longNum,final String center_id,final String pack_code) {

        // Inflate the popup_layout.xml
        final PopupWindow changeInfoPopUp = new PopupWindow(context);
        //LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.llStatusChangePopup);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.emp_info_pop_up_menu, null);
        LinearLayout indivPersonal = layout.findViewById(R.id.empServicesLayout);
        if(!supported_gender.equals("2") && !supported_gender.equals(BeautyMainPage.client_gender))
        {
            indivPersonal.setVisibility(View.GONE);
            layout.findViewById(R.id.line).setVisibility(View.GONE);
        }
        if(!(Integer.parseInt(BeautyMainPage.bdb_old)>Integer.parseInt(min_age)&&Integer.parseInt(BeautyMainPage.bdb_old)<Integer.parseInt(max_age)))
        {
            indivPersonal.setVisibility(View.GONE);
            layout.findViewById(R.id.line).setVisibility(View.GONE);
        }
        indivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PersonalIndivOfferRequest();
                Bundle b=new Bundle();
                b.putBoolean("isMe",true);
                b.putBoolean("is_offer",true);
                b.putString("sup_id",sup_id);
                b.putString("center_id",center_id);
                b.putString("pack_code",pack_code);
                b.putString("longNum",longNum);
                b.putString("latNum",latNum);
                b.putString("supported_gender",supported_gender);
                b.putString("sup_id",sup_id);
                b.putString("max_age",max_age);
                b.putString("min_age",min_age);
                fragment.setArguments(b);
                FragmentManager fm = ((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                changeInfoPopUp.dismiss();
            }
        });
        LinearLayout indivOther = layout.findViewById(R.id.empWorkingLayout);
        indivOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PersonalIndivOfferRequest();
                Bundle b=new Bundle();
                b.putBoolean("isMe",false);
                b.putBoolean("is_offer",true);
                b.putString("sup_id",sup_id);
                b.putString("center_id",center_id);
                b.putString("pack_code",pack_code);
                b.putString("longNum",longNum);
                b.putString("latNum",latNum);
                b.putString("supported_gender",supported_gender);
                b.putString("sup_id",sup_id);
                b.putString("max_age",max_age);
                b.putString("min_age",min_age);
                fragment.setArguments(b);
                FragmentManager fm = ((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                changeInfoPopUp.dismiss();
            }
        });
        // Creating the PopupWindow
        changeInfoPopUp.setContentView(layout);
        changeInfoPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeInfoPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeInfoPopUp.setFocusable(true);
        int OFFSET_X = -20;
        int OFFSET_Y = 50;
        changeInfoPopUp.setBackgroundDrawable(new BitmapDrawable());
        changeInfoPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
    }

    @Override
    public int getItemCount() {
        try {
            return offers.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {

        TextView packId,centerName,speciality,doctorName,morning_offer,new_price,supportedGender,age,place,old_price,discount,offer_type,num_of_times,offer_end,total_dis,onServices,depositPrcntg;
        ImageView info,add_offer,placeL,health,exp,is_fav_center,is_fav_doctor;
        LinearLayout centerNameLayout;
        public Item(View itemView) {
            super(itemView);
            packId = itemView.findViewById(R.id.packId);
            centerName = itemView.findViewById(R.id.pro_name);
            is_fav_center = itemView.findViewById(R.id.is_fav_center);
            is_fav_doctor = itemView.findViewById(R.id.is_fav_doctor);
            speciality = itemView.findViewById(R.id.speciality);
            doctorName = itemView.findViewById(R.id.doctorName);
            age = itemView.findViewById(R.id.age_range);
            supportedGender = itemView.findViewById(R.id.age);
            place = itemView.findViewById(R.id.place);
            new_price = itemView.findViewById(R.id.new_price);
            old_price = itemView.findViewById(R.id.old_price);
            discount = itemView.findViewById(R.id.discountVal);
            num_of_times = itemView.findViewById(R.id.num_of_times);
            info = itemView.findViewById(R.id.info);
            offer_end = itemView.findViewById(R.id.offer_end);
            add_offer = itemView.findViewById(R.id.add_offer);
            offer_type = itemView.findViewById(R.id.offer_type);
            total_dis = itemView.findViewById(R.id.disAmount);
            onServices = itemView.findViewById(R.id.onServices);
            depositPrcntg = itemView.findViewById(R.id.depPerc);
            centerNameLayout = itemView.findViewById(R.id.centerNameLayout);
           // placeL = itemView.findViewById(R.id.placeL);
           // health = itemView.findViewById(R.id.health);
           // exp = itemView.findViewById(R.id.exp);
          //  placeL = itemView.findViewById(R.id.placeL);
          //  morning_offer = itemView.findViewById(R.id.morning_offer);

        }
    }

}
