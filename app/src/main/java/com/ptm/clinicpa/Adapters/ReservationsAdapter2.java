package com.ptm.clinicpa.Adapters;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.DataModel.AppointmentsDataModel;
import com.ptm.clinicpa.DataModel.BookingAutomatedBrowseData;
import com.ptm.clinicpa.DataModel.DateTimeModel;
import com.ptm.clinicpa.DataModel.ReservationModel;
import com.ptm.clinicpa.Dialog.Dialogs;
import com.ptm.clinicpa.Dialog.MyRunnable;
import com.ptm.clinicpa.Fragments.ExecuteBookActivity;
import com.ptm.clinicpa.Fragments.MyReservation.CancelReservationActivity;
import com.ptm.clinicpa.Fragments.MyReservationFragment;
import com.ptm.clinicpa.Fragments.RateSerEmpActivity;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.ReservatoinDetailsActivity;
import com.ptm.clinicpa.MapsActivityLocation;
import com.ptm.clinicpa.PayFort.IPaymentRequestCallBack;
import com.ptm.clinicpa.PayFort.PayFortData;
import com.ptm.clinicpa.PayFort.PayFortPayment;
import com.ptm.clinicpa.PayFort.PayTestActivity;
import com.ptm.clinicpa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ReservationsAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
    //    ArrayList <DataService> services;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData1;
    public static ArrayList<ReservationModel> bookingAutomatedBrowseData;
    public static ArrayList<AppointmentsDataModel> appointmentsDataModels;
    //    ArrayList<DataReservation> reservations;
    int layout;
    public static Fragment fragment;
    public static Boolean isOffer=false;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    public static String book_id="0",is_action_on="",logoId;
    public static int postionBook;
    public static AppointmentsDataModel reservationModel;
    boolean isNew;
    public ReservationsAdapter2(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public ReservationsAdapter2(Context context, ArrayList<ReservationModel> bookingAutomatedBrowseData){
        this.context=context;
        this.bookingAutomatedBrowseData=bookingAutomatedBrowseData;
        this.layout=layout;
    }
    public ReservationsAdapter2(Context context, ArrayList<AppointmentsDataModel> bookingAutomatedBrowseData,boolean isNew){
        this.context=context;
        this.appointmentsDataModels=bookingAutomatedBrowseData;
        this.layout=layout;
        this.isNew=isNew;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.incom_reservation_layout_ext,parent,false);
        Item item=new Item(row);
        return item;


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        try {


            if(appointmentsDataModels.get(position).getIs_has_change_order().equals("1")&&isNew)
                ((Item)holder).bdb_expected_deposit.setText(context.getResources().getString(R.string.appUnderEditing));
            else
                ((Item)holder).bdb_expected_deposit.setVisibility(View.GONE);



            ((Item)holder).place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appointmentsDataModels.get(position).getBdb_loc_lat()!=null
                            && ! appointmentsDataModels.get(position).getBdb_loc_lat().equals("null")
                            && !appointmentsDataModels.get(position).getBdb_loc_lat().equals("")
                            &&  appointmentsDataModels.get(position).getBdb_loc_long()!=null
                            &&  !appointmentsDataModels.get(position).getBdb_loc_long().equals("null")
                            &&  !appointmentsDataModels.get(position).getBdb_loc_long().equals("")

                    ) {
                        Intent intent = new Intent(context, MapsActivityLocation.class);
                        intent.putExtra("lat", Double.parseDouble(appointmentsDataModels.get(position).getBdb_loc_lat()));
                        intent.putExtra("lang", Double.parseDouble(appointmentsDataModels.get(position).getBdb_loc_long()));
                        context.startActivity(intent);
                }
                    }
            });

           // Log.e("booktype",bookingAutomatedBrowseData.get(position).getBookingType());
            if(context.getString(R.string.locale).equals("en"))
                ((Item)holder).client_name.setText(appointmentsDataModels.get(position).getHealth_center_en());
            else
                ((Item)holder).client_name.setText(appointmentsDataModels.get(position).getHealth_center_ar());


            final String vizitType=appointmentsDataModels.get(position).getVisit_type();

            if(vizitType.equals("0"))
                ((Item)holder).bookingType.setText(R.string.newVisit);
            else if(vizitType.equals("1"))
                ((Item)holder).bookingType.setText(R.string.oldVisit);
            else
                ((Item)holder).bookingType.setText(R.string.unDeterminedVisit);


            ((Item)holder).book_id.setText(context.getString(R.string.ref_number)+": "+appointmentsDataModels.get(position).getBdb_internally_number());
            ((Item)holder).reference_id.setText(context.getString(R.string.book_id)+appointmentsDataModels.get(position).getBdb_appointment_id());

            if(!isNew)
            {
                ((Item)holder).centerRating.setText(appointmentsDataModels.get(position).getHealth_center_rating());
                ((Item)holder).docRating.setText(appointmentsDataModels.get(position).getDoctor_rating());
            }
            else
            {
                ((Item)holder).centerStar.setVisibility(View.GONE);
                ((Item)holder).centerRating.setVisibility(View.GONE);
                ((Item)holder).docRatingLayout.setVisibility(View.GONE);
            }

            Log.e("11111","111");
            if(appointmentsDataModels.get(position).getIs_shifted().equals("1")&& isNew)
            {
                Log.e("getIs_shifted","true");

                ((Item)holder).isShifted.setText(context.getString(R.string.isShifted)+appointmentsDataModels.get(position).getShifted_period());
            }
            else
            {
                Log.e("getIs_shifted","false");

                ((Item)holder).isShifted.setVisibility(View.GONE);
            }
            Log.e("11111","222");

            if(appointmentsDataModels.get(position).getIs_checked_in().equals("1"))
            {
                Log.e("getIs_checked_in","true");

                ((Item)holder).isChecked.setImageResource(R.drawable.ic_checked);
            }
            Log.e("11111","333");

            ((Item)holder).date.setText(APICall.convertToArabic(appointmentsDataModels.get(position).getBdb_start_date()));
            if (appointmentsDataModels.get(position).getBooking_place().equals("0")) {
                Log.e("11111","444");
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.salon));
            }else if (appointmentsDataModels.get(position).getBooking_place().equals("1")){
                Log.e("11111","555");

                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.home));
            }

            Log.e("11111","666");

            String docN=context.getString(R.string.doctorName) +": "+appointmentsDataModels.get(position).getDoctor_name();
            ((Item)holder).docName.setText(docN);
            Log.e("11111","777");

            ((Item)holder).patName.setText(appointmentsDataModels.get(position).getClient_name());
            Log.e("11111","888");
            int servicesPrice=0,basicPrice=0;
            try
            {
                 servicesPrice=Integer.parseInt(appointmentsDataModels.get(position).getServices_price());

            }
            catch (Exception e){}
            try {
                 basicPrice=Integer.parseInt(appointmentsDataModels.get(position).getBasic_price());

            }
            catch (Exception e){}

            int a=servicesPrice+basicPrice;
            String allPrice=a+" "+context.getString(R.string.ryal);
            if(appointmentsDataModels.get(position).getBasic_price().equals("null"))
            {
                ((Item)holder).totalPrice.setText(context.getString(R.string.unDeterminedPrice));
            }
            else
              ((Item)holder).totalPrice.setText(allPrice);


            final String appointmentType=appointmentsDataModels.get(position).getBdb_is_group_booking();

            if(appointmentType.equals("20")||appointmentType.equals("23")||appointmentType.equals("24")||appointmentType.equals("21")) // individual appointment
            {
                for (int i=0;i<appointmentsDataModels.get(position).getServices().size();i++)
                {
                    addLayout(((Item)holder).myroot,appointmentsDataModels.get(position).getServices().get(i).getService_en_name(),appointmentsDataModels.get(position).getServices().get(i).getService_ar_name());
                }
            }
            else //group appointment
            {
                //do nothing
            }




           /* //--- testing-----
            if(bookingAutomatedBrowseData.get(position).getBdb_refund_days().equals("0"))
            {
                String refunfString =context.getString(R.string.youCan)
                        +" "
                        +bookingAutomatedBrowseData.get(position).getBdb_refund_amount()
                        +" "
                        +context.getString(R.string.through)
                        +" "
                        +bookingAutomatedBrowseData.get(position).getBdb_refund_hours()
                        +" "
                        +context.getString(R.string.hours);
                ((Item) holder).refundText.setText(refunfString);

            }
            else
            {
                String refunfString =context.getString(R.string.youCan)
                        +" "
                        +bookingAutomatedBrowseData.get(position).getBdb_refund_amount()
                        +" "
                        +context.getString(R.string.through)
                        +" "
                        +bookingAutomatedBrowseData.get(position).getBdb_refund_days()
                        +" "
                        +context.getString(R.string.daysAnd)
                        +" "
                        +bookingAutomatedBrowseData.get(position).getBdb_refund_hours()
                        +" "
                        +context.getString(R.string.hours);
                ((Item) holder).refundText.setText(refunfString);

            }*/

//            ((Item) holder).accept.setText(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status());
           /* if (MyReservationFragment.tab.equals("2")){
//                ((Item) holder).delay.setText(R.string.deposit);
                ((Item) holder).delay.setVisibility(View.GONE);
                ((Item) holder).space2.setVisibility(View.GONE);
            }
            if (MyReservationFragment.tab.equals("1")){


                if (bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status().equals("2")){
                    ((Item) holder).status.setText(R.string.accepted_res);
                    if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("1")
                            || bookingAutomatedBrowseData.get(position).getIs_action_on().equals("true")) {
                        ((Item) holder).delay.setVisibility(View.VISIBLE);
                        ((Item) holder).space2.setVisibility(View.VISIBLE);
                        ((Item) holder).delay.setText(R.string.deposit);
                    }else {
                        ((Item) holder).delay.setVisibility(View.GONE);
                        ((Item) holder).space2.setVisibility(View.GONE);

                    }

                }else  if (bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status().equals("8")){
                    ((Item) holder).status.setText(R.string.waiting_res);
                    ((Item) holder).delay.setVisibility(View.GONE);
                    ((Item) holder).space2.setVisibility(View.GONE);

                }
//                ((Item) holder).delay.setVisibility(View.GONE);
            }
            else{
                ((Item) holder).status.setVisibility(View.GONE);
            }

            if (!MyReservationFragment.tab.equals("3")){
                ((Item) holder).rating.setVisibility(View.GONE);
                ((Item) holder).refundText.setVisibility(View.VISIBLE);
                if (bookingAutomatedBrowseData.get(position).getIs_rating_on().equals("1"))
                    for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++)
                        if (bookingAutomatedBrowseData.get(position).getData().get(i).getBdb_confirm_exec_user().equals("1")){
                            ((Item) holder).time.setVisibility(View.VISIBLE);
                            ((Item)holder).space.setVisibility(View.VISIBLE);
                            ((Item) holder).time.setText(R.string.rate);
                            break;
                        }
            }else {
                try {
                    float rate=Float.parseFloat(bookingAutomatedBrowseData.get(position).getData().get(0).getProvider_rating());
                    ((Item) holder).rating.setRating(rate);

                }catch (Exception e){
                    ((Item) holder).rating.setRating(0f);
                }

            }


            if (MyReservationFragment.tab.equals("3")){
                ((Item) holder).refuse.setVisibility(View.GONE);
                ((Item) holder).delay.setVisibility(View.GONE);
                ((Item) holder).time.setVisibility(View.GONE);
                ((Item)holder).space2.setVisibility(View.GONE);
                ((Item)holder).space.setVisibility(View.GONE);
//                ((Item) holder).time.setVisibility(View.GONE);
                ((Item) holder).status.setVisibility(View.VISIBLE);

                String st=bookingAutomatedBrowseData.get(position).getBdb_is_executed();
                if (st.equals("1")){
                    ((Item) holder).status.setText(R.string.not_completly_executed);
                    ((Item) holder).rating.setVisibility(View.VISIBLE);
                }else if (st.equals("4")){
                    ((Item) holder).status.setText(R.string.cancelled_after_deposit);
                    ((Item) holder).rating.setVisibility(View.GONE);
                }else if (st.equals("5")){
                    ((Item) holder).status.setText(R.string.cancelled_before_deposit);
                    ((Item) holder).rating.setVisibility(View.GONE);
                }else if (st.equals("7")){
                    ((Item) holder).status.setText(R.string.completly_executed);
                    ((Item) holder).rating.setVisibility(View.VISIBLE);
                }

            }

            ((Item) holder).delay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookingAutomatedBrowseData.get(position).getData().get(0).getIs_action_on_inside().equals("true")){
                        //------------- make deposit here-------------
                        final Dialog dialog=new Dialog(context);
                        dialog.setContentView(R.layout.payment_layout_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        final RadioButton radio_check=dialog.findViewById(R.id.radio_check);
                        TextView next=dialog.findViewById(R.id.next);
                        TextView cancel=dialog.findViewById(R.id.cancel);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (radio_check.isChecked()) {
                                    dialog.cancel();
                                    if (bookingAutomatedBrowseData.get(position).getBdb_expected_deposit().equals("0")) {
                                        try {
                                            float deposit = (Float.parseFloat(bookingAutomatedBrowseData.get(position).getTotalPrice()) / 10f);
//                                getNewPaymentCode(context,deposit,"SAR");


//                                initilizePayFortSDK();

                                            Intent intent = new Intent(context, PayTestActivity.class);
                                            intent.putExtra("amount", deposit + "");
                                            intent.putExtra("name_booking", bookingAutomatedBrowseData.get(position).getBdb_name_booking() + "");
                                            context.startActivity(intent);
//                                getNewPaymentCode(context,deposit+"","SAR",BeautyMainPage.bdb_email,device_id,bookingAutomatedBrowseData.get(position).getBdb_name_booking(),fortCallback, (IPaymentRequestCallBack) BeautyMainPage);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            float deposit = (Float.parseFloat(bookingAutomatedBrowseData.get(position).getBdb_expected_deposit()));
//                                getNewPaymentCode(context,deposit,"SAR");


//                                initilizePayFortSDK();

                                            Intent intent = new Intent(context, PayTestActivity.class);
                                            intent.putExtra("amount", deposit + "");
                                            intent.putExtra("name_booking", bookingAutomatedBrowseData.get(position).getBdb_name_booking() + "");
                                            context.startActivity(intent);
//                                getNewPaymentCode(context,deposit+"","SAR",BeautyMainPage.bdb_email,device_id,bookingAutomatedBrowseData.get(position).getBdb_name_booking(),fortCallback, (IPaymentRequestCallBack) BeautyMainPage);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(context,context.getResources().getString(R.string.select_pay_method),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                       cancel.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               dialog.cancel();
                           }
                       });

                       dialog.show();

                    }else {
                        APICall.showSweetDialog(context,"",context.getResources().getString(R.string.deposit_can_paid_alert));

                    }
                }
            });
*/

           /* String inner=bookingAutomatedBrowseData.get(position).getBdb_inner_booking();
            String byMe=bookingAutomatedBrowseData.get(position).getBooked_by_me();
*/
           /* if (inner.equals("1")){
                ((Item)holder).inner_res.setImageResource(R.drawable.inner_booking);
            }
            else {
                if(byMe.equals("false"))
                    ((Item)holder).inner_res.setImageResource(R.drawable.other_client_booking);
                else
                    ((Item)holder).inner_res.setImageResource(R.drawable.client_booking);

            }
*/


           /* boolean allExecuted=false;

//                if (position<3)

            ArrayList<DateTimeModel> dateTimeModels=getBookTimeToCheck(bookingAutomatedBrowseData.get(position));
            for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++) {

                Log.e("CONFIRMEXEC"+position, bookingAutomatedBrowseData.get(position).getClient_name()+bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name() + "::" + isPast(bookingAutomatedBrowseData.get(position).getData().get(i)));
//                    Log.e("Multi");
                if (bookingAutomatedBrowseData.get(position).getBookingType().equals("3") ||
                        bookingAutomatedBrowseData.get(position).getBookingType().equals("5") ||
                        bookingAutomatedBrowseData.get(position).getBookingType().equals("8") ||
                        bookingAutomatedBrowseData.get(position).getBookingType().equals("13") ||
                        bookingAutomatedBrowseData.get(position).getBookingType().equals("21") ||
                        bookingAutomatedBrowseData.get(position).getBookingType().equals("24")){
                    Log.e("Multi","Ok");
                    Log.e("MultiSize",bookingAutomatedBrowseData.get(position).getData().size()+"");
                    if ((i!=0 && i!=bookingAutomatedBrowseData.get(position).getData().size()-1)
                            && bookingAutomatedBrowseData.get(position).getData().size()>2){

                        if (dateTimeModels.get(i).getDate()
                                .equals(dateTimeModels.get(i-1).getDate())
                                && !dateTimeModels.get(i).getDate()
                                .equals(dateTimeModels.get(i+1).getDate())){

                            if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition()))
//                                    && bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition()).getBdb_confirm_exec_user().equals("0")
                            ) {
                                allExecuted = true;
//                                ((Item) holder).time.setVisibility(View.VISIBLE);
                            }

                        }else if (!dateTimeModels.get(i).getDate()
                                .equals(dateTimeModels.get(i-1).getDate())) {

//                                    if (isPast(bookingAutomatedBrowseData.get(position).getData().get(i-1)))
                            if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i-1).getPosition()))
//                                    && bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i-1).getPosition()).getBdb_confirm_exec_user().equals("0")
                            ) {
                                allExecuted = true;
//                                ((Item) holder).time.setVisibility(View.VISIBLE);
                            }
                        }else if (!dateTimeModels.get(i).getDate()
                                .equals(dateTimeModels.get(i+1).getDate())
                                && i+1==dateTimeModels.size()) {

//                                    if (isPast(bookingAutomatedBrowseData.get(position).getData().get(i)))
//                                        allExecuted = true;

                            if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i+1).getPosition()))
//                                    && bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i+1).getPosition()).getBdb_confirm_exec_user().equals("0")
                            ) {
                                allExecuted = true;
//                                ((Item) holder).time.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    if (bookingAutomatedBrowseData.get(position).getData().size()==2){
                        Log.e("967"+bookingAutomatedBrowseData.get(position).getClient_name()+i,isPast(bookingAutomatedBrowseData.get(position).getData().get(i))+"");
                        if (isPast(bookingAutomatedBrowseData.get(position).getData().get(i))
//                                && bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition()).getBdb_confirm_exec_user().equals("0")
                        ) {
                            allExecuted = true;
//                            ((Item) holder).time.setVisibility(View.VISIBLE);

                        }
                    }

                }
                else
                    Log.e("967"+bookingAutomatedBrowseData.get(position).getClient_name()+i,isPast(bookingAutomatedBrowseData.get(position).getData().get(i))+"");

                if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition()))
//                        && bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition()).getBdb_confirm_exec_user().equals("0")
                ) {
                    allExecuted = true;
//                    ((Item) holder).time.setVisibility(View.VISIBLE);
//
                }
                }
//                }

            Log.e("Executed99","is:"+allExecuted+"");
            if(allExecuted && !BeautyMainPage.FRAGMENT_NAME.equals("MYRESERVATIONEXECUTEDFRAGMENT")) {
                ((Item) holder).time.setText(R.string.Executed);
                if (!MyReservationFragment.tab.equals("3")) {
                    ((Item) holder).time.setVisibility(View.VISIBLE);
                    ((Item)holder).space.setVisibility(View.VISIBLE);

                }
            }
            else
            {
                ((Item)holder).space.setVisibility(View.GONE);
                ((Item)holder).time.setVisibility(View.GONE);

            }


            if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("true")){
                ((Item) holder).time.setText(R.string.Executed);

            }else if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("false")
                    && bookingAutomatedBrowseData.get(position).getIs_rating_on().equals("true")){
                ((Item) holder).time.setText(R.string.rate);

            }
*/



            if(!isNew || appointmentsDataModels.get(position).getCan_cancel().equals("0"))
            {
                ((Item) holder).refuse.setVisibility(View.GONE);
                ((Item) holder).space2.setVisibility(View.GONE);
            }
            else
            {
                ((Item) holder).refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reservationModel=appointmentsDataModels.get(position);
                        if (appointmentsDataModels.get(position).getCan_cancel().equals("true")
                                ||appointmentsDataModels.get(position).getCan_cancel().equals("1")) {
//
                            APICall.appointmentProcessing(appointmentsDataModels.get(position).getBdb_appointment_id(), 5, "0", context);

                        }
                        else {
                            APICall.showSweetDialog(context,"",context.getResources().getString(R.string.this_res_can_only_canceld_by_the_owner));
                        }
                    }
                });


            }


            if(!isNew || appointmentsDataModels.get(position).getCan_check_in().equals("0"))
            {
                ((Item) holder).checkIn.setVisibility(View.GONE);
                ((Item) holder).space.setVisibility(View.GONE);
            }
            else
            {
                ((Item)holder).checkIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("CheckIn","ediiiiiiiiiiit..");

                        book_id=appointmentsDataModels.get(position).getBdb_appointment_id();
                        if(appointmentsDataModels.get(position).getHealth_record().equals("")||appointmentsDataModels.get(position).getHealth_record().equals("null"))
                        {
                            Dialogs getReasonDialog =new Dialogs(context, R.string.empty, R.string.enterReasonMsg, R.string.ok,OnClickCallMeBtn,context.getString(R.string.med_id));
                            getReasonDialog.show();
                        }
                        else
                        {
                            APICall.CheckIn(context,book_id,appointmentsDataModels.get(position).getHealth_record());
                        }


                    }
                });
            }

            if(isNew&&appointmentsDataModels.get(position).getCan_order_change().equals("1"))
            {
                ((Item)holder).edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("edit","ediiiiiiiiiiit..");

                        final Dialog dialog = new Dialog(context);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        dialog.setContentView(R.layout.edit_appointment_layout);
                        final TextView oldTime,oldDate,newTime,newDate,ok,cancel;
                        final CheckBox canBeEdited;
                        newTime = dialog.findViewById(R.id.newTime);
                        newDate = dialog.findViewById(R.id.newDate);
                        oldDate = dialog.findViewById(R.id.oldDate);
                        oldTime = dialog.findViewById(R.id.oldTime);
                        canBeEdited = dialog.findViewById(R.id.canBeEdited);
                        ok = dialog.findViewById(R.id.confirm);
                        cancel = dialog.findViewById(R.id.cancel);
                        oldDate.setText(appointmentsDataModels.get(position).getBdb_start_date());
                        oldTime.setText(appointmentsDataModels.get(position).getBdb_start_time());
                        newTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog=new Dialog(context);
                                dialog.setContentView(R.layout.time_select_layout);
                                final TimePicker timePicker=dialog.findViewById(R.id.time_picker);
                                TextView ok=dialog.findViewById(R.id.confirm);
                                TextView cancel=dialog.findViewById(R.id.cancel);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        ///startWorkHour=timePicker.getHour();
                                        //   startWorkMinutes=timePicker.getMinute();
                                        String ho,min;
                                        if (timePicker.getHour()<10){
                                            ho="0"+timePicker.getHour();
                                        }else {
                                            ho=timePicker.getHour()+"";
                                        }

                                        if (timePicker.getMinute()<10){
                                            min="0"+timePicker.getMinute();
                                        }else {
                                            min=timePicker.getMinute()+"";
                                        }
                                        String st = ho + ":" + min+":"+"00";
//                        String st=timePicker.getHour()+":"+timePicker.getMinute();
                                        newTime.setText(st);
                                    }
                                });
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();


                            }
                        });

                        newDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog=new Dialog(context);
                                dialog.setContentView(R.layout.select_date);
                                TextView confirm=dialog.findViewById(R.id.confirm);
                                TextView cancel=dialog.findViewById(R.id.cancel);
                                final DatePicker datePicker=dialog.findViewById(R.id.date_picker);
                                datePicker.setMinDate(System.currentTimeMillis());

//                RequestProvidersFragment.bdb_booking_period)
                                try {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(RequestProvidersFragment.bdb_booking_period));
                                    datePicker.setMaxDate(calendar.getTimeInMillis());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        int month=datePicker.getMonth()+1;
                                        newDate.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });


                                dialog.show();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        ok.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                String newDateStr,newTimeStr;
                                String keep_current="0";
                                if(canBeEdited.isChecked())
                                    keep_current="0";
                                else
                                    keep_current="1";
                                if(newDate.getText().toString().equals(""))
                                {
                                    newDateStr=oldDate.getText().toString();
                                }
                                else
                                {
                                    newDateStr=newDate.getText().toString();
                                }
                                if(newTime.getText().toString().equals(""))
                                {
                                    newTimeStr=oldTime.getText().toString();
                                }
                                else
                                {
                                    newTimeStr=newTime.getText().toString();
                                }
                                APICall.editAppointment(context,appointmentsDataModels.get(position).getBdb_appointment_id(),newTimeStr,newDateStr,keep_current);
                            }
                        });
                        dialog.show();


                    }
                });
            }

            else if(!isNew&&appointmentsDataModels.get(position).getCan_rating().equals("1"))
            {
                ((Item)holder).edit.setText(R.string.evaluate);
                ((Item)holder).edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i =new Intent(context,RateSerEmpActivity.class);
                        i.putExtra("doctor_name",appointmentsDataModels.get(position).getDoctor_name());
                        i.putExtra("patient_name",appointmentsDataModels.get(position).getClient_name());
                        i.putExtra("appointment_id",appointmentsDataModels.get(position).getBdb_appointment_id());
                        i.putExtra("center_id",appointmentsDataModels.get(position).getHealth_center_id());
                        i.putExtra("doctor_id",appointmentsDataModels.get(position).getDoctor_id());

                        context.startActivity(i);

                    }
                });
            }
            else
            {
                ((Item)holder).edit.setVisibility(View.GONE);
                ((Item)holder).space.setVisibility(View.GONE);

            }

            APICall.getSalonLogoDltWhenEmpty(BeautyMainPage.context,appointmentsDataModels.get(position).getBdb_health_center_logo_id(),((Item)holder).logoImg);

            ((Item) holder).book_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        book_id=appointmentsDataModels.get(position).getBdb_appointment_id();
                        logoId=appointmentsDataModels.get(position).getBdb_health_center_logo_id();

                        postionBook=position;


                        Intent intent=new Intent(context, ReservatoinDetailsActivity.class);
                       // intent.putExtra("appointment_id",appointmentsDataModels.get(position).getBdb_appointment_id());
                        intent.putExtra("internally_book",appointmentsDataModels.get(position).getBdb_appointment_id());
                        ((AppCompatActivity)context).startActivity(intent);



//                        fragment = new ReservationDetailsFragment();
//                        fm = ((AppCompatActivity) context).getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            //---------- Edit reservation listener--------------


//            String booktype=bookingAutomatedBrowseData.get(position).getBookingType();
//
//            if (booktype.equals("0")){
//                ((Item)holder).bookType.setText(context.getResources().getText(R.string.single_booking));
//            }else {
//                ((Item)holder).bookType.setText(context.getResources().getText(R.string.multi_booking));
//            }

           // ((Item)holder).totalPrice.setText(APICall.convertToArabic(bookingAutomatedBrowseData.get(position).getTotalPrice())+context.getResources().getString(R.string.ryal));


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
//        Log.e("bookingAutomatedcheck",bookingAutomatedBrowseData.size()+"");

        return appointmentsDataModels.size();
    }


    public static void addLayout(final LinearLayout myroot, String serviceName, final int postion, final int i){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(APICall.layout, myroot, false);
        TextView emp_name,book_details,re_cancel;
        TextView accept=null,refuse,edit_time;

//        R.layout.accept_reservation_layout_v2;

        emp_name=layout2.findViewById(R.id.rname);
//        if (APICall.layout==R.layout.incom_reservation_layout) {
//            accept = layout2.findViewById(R.id.accept);
//        }
//        refuse=layout2.findViewById(R.id.refuse);
//        re_cancel=layout2.findViewById(R.id.cancel);
        edit_time=layout2.findViewById(R.id.edit_time);
//        book_details=layout2.findViewById(R.id.book_Details);
        emp_name.setText(serviceName);


        if (APICall.layout== R.layout.incom_reservation_layout){

//            accept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new AlertDialog.Builder(BeautyMainPage.context)
//                            .setTitle("Accept")
//                            .setMessage("Do you want accept This Reservation?")
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //-------------api here
////                                    APICall.bookingProcessing(bookingAutomatedBrowseData.get(postion).getData().get(i).getBdb_id(),"2",BeautyMainPage.context);
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //----------------- api here
//                                    dialog.cancel();
//                                }
//                            })
//                            .show()
//                    ;
//
//                }
//            });

        }



        if (APICall.layout== R.layout.accept_reservation_layout_v2) {

//            refuse.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String title;
//
//                    new AlertDialog.Builder(BeautyMainPage.context)
//                            .setTitle("Cancel")
//                            .setMessage("Do you want cancel This Reservation?")
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //-------------api here
////                                    APICall.cancelUnPaidBooking(bookingAutomatedBrowseData.get(postion).getData().get(i).getBdb_id(), ProviderMainPage.context);
//
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //----------------- api here
//                                    dialog.cancel();
//                                }
//                            })
//                            .show()
//
//                    ;
//
//                }
//            });
        }

        if (APICall.layout== R.layout.incom_reservation_layout) {

//            refuse.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String title;
//
//                    new AlertDialog.Builder(BeautyMainPage.context)
//                            .setTitle("Cancel")
//                            .setMessage("Do you want cancel This Reservation?")
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //-------------api here
////                                    APICall.bookingProcessing(bookingAutomatedBrowseData.get(postion).getData().get(i).getBdb_id(), "0", ProviderMainPage.context);
//
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //----------------- api here
//                                    dialog.cancel();
//                                }
//                            })
//                            .show()
//
//                    ;
//
//                }
//            });
        }
        if (i!=bookingAutomatedBrowseData.get(postion).getData().size()-1) {
//            if (APICall.layout==R.layout.incom_reservation_layout) {
//                accept.setVisibility(View.GONE);
//            }
//            refuse.setVisibility(View.GONE);
//            edit_time.setVisibility(View.GONE);
        }
//        book_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//            }
//        });

//        ImageView delete=layout2.findViewById(R.id.delete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myroot.removeView(layout2);
//            }
//        });
//
        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addLayout(final LinearLayout myroot, String serviceName,String serviceName_ar){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_layout, myroot, false);
        TextView emp_name;
        emp_name=layout2.findViewById(R.id.rname);
        if(BeautyMainPage.context.getString(R.string.locale).equals("en"))
            emp_name.setText(serviceName);
        else
            emp_name.setText(serviceName_ar);

        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
    }

    public class Item extends RecyclerView.ViewHolder {
//        MyClickListener listener;

        TextView bookingType,book_id,bdb_expected_deposit,client_name,status,checkIn,reference_id, totalPrice,booking_place,export_invoice,date,accept,refuse,edit,refundText;
        ImageView book_Details,inner_res,logoImg,place,isChecked;
        ColorRatingBar rating;
        Space space,space2;

        TextView centerRating,docRating,isShifted,docName,patName;
        LinearLayout myroot,centerStar,docRatingLayout,actions;
        public Item(View itemView) {
            super(itemView);
            bookingType=itemView.findViewById(R.id.booktype);
            myroot=itemView.findViewById(R.id.myroot);
            status=itemView.findViewById(R.id.status);
            checkIn=itemView.findViewById(R.id.delay);
            rating=itemView.findViewById(R.id.rating);
            book_id=itemView.findViewById(R.id.book_id);
            place=itemView.findViewById(R.id.place);
            bdb_expected_deposit=itemView.findViewById(R.id.bdb_expected_deposit);
            reference_id=itemView.findViewById(R.id.reference_number);
            space=itemView.findViewById(R.id.space);
            space2=itemView.findViewById(R.id.space2);

            totalPrice=itemView.findViewById(R.id.total_price);
           // inner_res=itemView.findViewById(R.id.inner_res);
            client_name=itemView.findViewById(R.id.client_name);
            date=itemView.findViewById(R.id.start_date);
            booking_place=itemView.findViewById(R.id.booking_place);
            book_Details=itemView.findViewById(R.id.book_details);
            refuse=itemView.findViewById(R.id.refuse);
//            accept=itemView.findViewById(R.id.accept);
            edit=itemView.findViewById(R.id.time);
            logoImg=itemView.findViewById(R.id.logoImg);
            refundText=itemView.findViewById(R.id.refundText);
            docRating=itemView.findViewById(R.id.doctor_rate);
            docRatingLayout=itemView.findViewById(R.id.doctor_rateLayout);
            centerRating=itemView.findViewById(R.id.provider_rate);
            centerStar=itemView.findViewById(R.id.centerRatingStar);
            isShifted=itemView.findViewById(R.id.is_shifted);
            isChecked=itemView.findViewById(R.id.isCheckedIn);
            docName=itemView.findViewById(R.id.doctorName);
            patName=itemView.findViewById(R.id.patientName);
            actions=itemView.findViewById(R.id.actions);

        }


    }

    public static boolean isPast(BookingAutomatedBrowseData reservation) throws ParseException {
        boolean result = false;
        if (reservation.getBdb_status().equals("7")) {
            NumberFormat formatter = new DecimalFormat("00");
            Calendar calendar = Calendar.getInstance();
            Log.e("calender", calendar.getTime().toString());
            DateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
            DateFormat dayFormate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dayFormate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            String timeNow = calendar.get(Calendar.HOUR_OF_DAY) + ":" + minutes+":"+"00";
            String NowTimeStr = (hour24hrs + 3) % 24 + ":" + minutes + ":" + "00";
//            String =String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)+"");
            Log.e("calMonth",calendar.get(Calendar.DAY_OF_MONTH)+"");
            String dom = formatter.format(calendar.get(Calendar.DAY_OF_MONTH));
            String NowDayStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +dom ;
            String ReserDateStr = reservation.getBdb_start_date();
            String ReserTimeStr = reservation.getBdb_end_time();
//                    getBdb_end_time();
            Log.e("ReserDateStr", ReserDateStr);
            Log.e("ReserTimeStr", ReserTimeStr);
            Log.e("NowDayStr", NowDayStr);
            Date ReserDate = dayFormate.parse(ReserDateStr);
            Date ReserTime = timeFormate.parse(ReserTimeStr);
            Date NowDate = dayFormate.parse(NowDayStr);
            Date NowTime = timeFormate.parse(NowTimeStr);
            if (ReserTime.getMinutes() + 10 >= 60) {
                ReserTime.setMinutes(00);
                ReserTime.setHours(ReserTime.getHours() + 1);
            } else
                ReserTime.setMinutes(ReserTime.getMinutes() + 10);
            ReserTimeStr = ReserTime.getHours()+":"+ReserTime.getMinutes()+":00";
//            if ((hour24hrs + 3) > 24) {
//                calendar.add(Calendar.DATE, 1);
//                NowDayStr = calendar.get(Calendar.DATE) + "";
////                NowDate = dayFormate1.parse(NowDayStr);
//            }
            Log.e("BDB_ID",reservation.getBdb_id());
            NowDate = dayFormate1.parse(NowDayStr+" "+timeNow);
            ReserDate = dayFormate1.parse(ReserDateStr+" "+ReserTimeStr);
            Log.e("TIMENOW",reservation.getBdb_id()+" "+NowDate.toString()+" "+timeNow);
            Log.e("ReserDateStr", ReserDateStr);
            Log.e("ReserTimeStr", ReserTimeStr);
            Log.e("COMPARENOW", NowDate.toString());
            Log.e("COMPARERESE", ReserDate.toString());

            Log.e("NowDate", NowDate.toString());
            Log.e("ReserDate", ReserDate.toString());


            if (NowDate.compareTo(ReserDate) > 0) {
                result = true;
            } else if (NowDate.compareTo(ReserDate) == 0) {
                if (NowTime.compareTo(ReserTime) > 0) {
                    result = true;
                }
            }
        }
        return result;
    }
    public static boolean isPast(String startDate,String EndTime) throws ParseException {
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        DateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
        DateFormat dayFormate =new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dayFormate1 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String NowTimeStr = (hour24hrs+3)%24 + ":" + minutes+":"+"00";
        String timeNow = calendar.get(Calendar.HOUR_OF_DAY) + ":" + minutes+":"+"00";
        String NowDayStr = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
        String ReserDateStr =  startDate;
        String ReserTimeStr = EndTime;
        Date ReserTime = timeFormate.parse(ReserTimeStr);
        Date NowDate = dayFormate.parse(NowDayStr);
        Date NowTime = timeFormate.parse(NowTimeStr);
        if(ReserTime.getMinutes()+10>=60)
        {
            ReserTime.setMinutes(00);
            ReserTime.setHours(ReserTime.getHours()+1);
        }
        else
            ReserTime.setMinutes(ReserTime.getMinutes()+10);

        ReserTimeStr = ReserTime.getHours()+":"+ReserTime.getMinutes()+":00";

//        if((hour24hrs+3) > 24)
//        {
//            calendar.add(Calendar.DATE,1);
//            NowDayStr = calendar.get(Calendar.DATE)+"";
////            String time=calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+"00";
//            NowDate = dayFormate1.parse(NowDayStr+" "+timeNow);
//            Log.e("COMPARENOW",NowDate.toString()+" "+timeNow);
//
//        }
        NowDate = dayFormate1.parse(NowDayStr+" "+timeNow);
        Date ReserDate=dayFormate1.parse(ReserDateStr+" "+ReserTimeStr);

        Log.e("TIMENOW",NowDate.toString()+" "+timeNow);


        Log.e("ReserDateStr",ReserDateStr);
        Log.e("ReserTimeStr",ReserTimeStr);
        Log.e("NowTimeStr",NowTimeStr);
        Log.e("COMPARENOW",NowDate.toString());
        Log.e("COMPARERESE",ReserDate.toString());

        if( NowDate.compareTo(ReserDate)>0)
        {
            result=true;
        }
        else if(NowDate.compareTo(ReserDate)==0)
        {
            if( NowTime.compareTo(ReserTime)>0)
            {
                result=true;
            }
        }
        return result;
    }
    public static ArrayList<DateTimeModel> getBookTimeToCheck(ReservationModel reservationList){
//        BookingAutomatedBrowseData reservation = null;
        ArrayList<DateTimeModel> dateTimeModels=new ArrayList<>();
        for (int i=0;i<reservationList.getData().size();i++){
            dateTimeModels.add(new DateTimeModel(reservationList.getData().get(i).getBdb_start_date(),reservationList.getData().get(i).getBdb_start_time()
//                    getBdb_end_time()
                    ,i));
        }



        Collections.sort(dateTimeModels, new Comparator<DateTimeModel>() {

            @Override
            public int compare(DateTimeModel o1, DateTimeModel o2) {
                try {
                    return new SimpleDateFormat("YY:MM:DD").parse(o1.getDate()).compareTo(new SimpleDateFormat("YY:MM:DD").parse(o2.getDate()));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });
        Collections.sort(dateTimeModels, new Comparator<DateTimeModel>() {

            @Override
            public int compare(DateTimeModel o1, DateTimeModel o2) {
                try {
                    return new SimpleDateFormat("HH:mm:ss").parse(o1.getTime()).compareTo(new SimpleDateFormat("HH:mm:ss").parse(o2.getTime()));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });
        for (int i=0;i<dateTimeModels.size();i++){
            Log.e("DateModel"+i,dateTimeModels.get(i).getDate()+" "+dateTimeModels.get(i).getTime());
        }
//        System.out.println();

        return  dateTimeModels;
    }

    public static String response1="";
    public  void getNewPaymentCode(final Context context, final String amount, String currency, final String customer_email, final String device_id, String name_booking, final FortCallBackManager fortCallback, final IPaymentRequestCallBack iPaymentRequestCallBack)
    {

//        payFortData.sdkToken = "";


        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                APICall.showDialog(context);
//
            }
        });

        //        String url = API_PREFIX_NAME+"/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("amount",amount);
            postdata.put("service_command","SDK_TOKEN");
            postdata.put("customer_email","hazem.ali1466@gmail.com");
            postdata.put("language",APICall.ln);
            postdata.put("currency",currency);
            postdata.put("device_id",device_id);
            postdata.put("bdb_name_booking",name_booking);
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e("PostData",postdata.toString());
        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(APICall.API_PREFIX_NAME+"/api/payment/getNewPaymentCode")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+APICall.gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.e("Payment_Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        APICall.pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname"))
                {
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });


                }
                else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            APICall.showSweetDialog(context,"",context.getResources().getString(R.string.an_error_occurred));

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                final String mMessage = response.body().string();
                Log.e("Token", APICall.gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        APICall.pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                        JSONObject object=j.getJSONObject("result");
                        response1=object.toString();
                        final String signature=object.getString("signature");
                        final String merchant_identifier=object.getString("merchant_identifier");
                        final String access_code=object.getString("access_code");
                        final String merchant_reference=object.getString("merchant_reference");
                        final String sdk_token=object.getString("sdk_token");
                        Log.e("this_is","APIBefor");

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                requestForPayfortPayment(amount,sdk_token,signature ,merchant_identifier ,access_code
                                        ,merchant_reference,customer_email,device_id,fortCallback,iPaymentRequestCallBack,mMessage);
                            }
                        });



                    }

                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            je.printStackTrace();
                            // Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }
    private void requestForPayfortPayment(String deposit,String sdk_token,String signature ,String merchant_identifier ,String access_code
            ,String merchant_reference ,String customer_email,String device_id ,FortCallBackManager fortCallback, IPaymentRequestCallBack iPaymentRequestCallBack,String mMessage) {

        PayFortData payFortData = new PayFortData();
        if (!TextUtils.isEmpty(deposit)) {
            payFortData.serviceCommand = "SDK_TOKEN";
            payFortData.deviceId = device_id;
//            payFortData.merchantIdentifier = merchant_identifier;
//            payFortData.accessCode = access_code;
//            payFortData.signature = signature;
            payFortData.amount = String.valueOf((int) (Float.parseFloat(deposit) * 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = customer_email;
            payFortData.language = "en";
            payFortData.merchantReference = merchant_reference;
            payFortData.sdkToken = sdk_token;
//            payFortData.paymentResponse = mMessage;

//            payFortData.status= "22";
            Log.e("amount","is: "+payFortData.amount);
            Log.e("command","is: "+payFortData.command);
            Log.e("currency","is: "+payFortData.currency);
            Log.e("customerEmail","is: "+payFortData.customerEmail);
            Log.e("deviceId","is: "+payFortData.deviceId);
            Log.e("sdkToken","is: "+payFortData.sdkToken);
            Log.e("signature","is: "+payFortData.signature);
            Log.e("merchantReference","is: "+payFortData.merchantReference);
            Log.e("merchantIdentifier","is: "+payFortData.merchantIdentifier);
            Log.e("accessCode","is: "+payFortData.accessCode);


//            parameters.put("amount", String.valueOf(payFortData.amount));
//            parameters.put("command", payFortData.command);
//            parameters.put("currency", payFortData.currency);
//            parameters.put("customer_email", payFortData.customerEmail);
//            parameters.put("language", payFortData.language);
//            parameters.put("merchant_reference", payFortData.merchantReference);
//            parameters.put("sdk_token", sdkToken);



            PayFortPayment payFortPayment = new PayFortPayment((AppCompatActivity)context, fortCallback,   iPaymentRequestCallBack);
            payFortPayment.requestForPayment(payFortData);
        }
    }

    public static FortCallBackManager fortCallback = null;
    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }
    private MyRunnable OnClickCallMeBtn =new MyRunnable()
    {
        @Override
        public void run() {
            APICall.CheckIn(context,book_id,getValue());

        }
    };
}
