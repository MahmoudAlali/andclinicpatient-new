package com.ptmsa1.vizage.Adapters;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.BookingAutomatedBrowseData;
import com.ptmsa1.vizage.DataModel.DateTimeModel;
import com.ptmsa1.vizage.DataModel.ReservationModel;
import com.ptmsa1.vizage.Fragments.ExecuteBookActivity;
import com.ptmsa1.vizage.Fragments.MyReservation.CancelReservationActivity;
import com.ptmsa1.vizage.Fragments.MyReservationFragment;
import com.ptmsa1.vizage.Fragments.RateSerEmpActivity;
import com.ptmsa1.vizage.Fragments.ReservatoinDetailsActivity;
import com.ptmsa1.vizage.MapsActivityLocation;
import com.ptmsa1.vizage.PayFort.IPaymentRequestCallBack;
import com.ptmsa1.vizage.PayFort.PayFortData;
import com.ptmsa1.vizage.PayFort.PayFortPayment;
import com.ptmsa1.vizage.PayFort.PayTestActivity;
import com.ptmsa1.vizage.R;

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
    //    ArrayList<DataReservation> reservations;
    int layout;
    public static Fragment fragment;
    public static Boolean isOffer=false;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    public static String book_id="0",is_action_on="",logoId;
    public static int postionBook;
    public static ReservationModel reservationModel;
    public ReservationsAdapter2(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public ReservationsAdapter2(Context context, ArrayList<ReservationModel> bookingAutomatedBrowseData){
        this.context=context;
        this.bookingAutomatedBrowseData=bookingAutomatedBrowseData;
        this.layout=layout;
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

            ((Item)holder).bdb_expected_deposit.setText(context.getResources().getString(R.string.deposit_val)+" "+bookingAutomatedBrowseData.get(position).getBdb_expected_deposit());


            ((Item)holder).place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookingAutomatedBrowseData.get(position).getBdb_loc_lat()!=null
                            && ! bookingAutomatedBrowseData.get(position).getBdb_loc_lat().equals("null")
                            && !bookingAutomatedBrowseData.get(position).getBdb_loc_lat().equals("")
                            &&  bookingAutomatedBrowseData.get(position).getBdb_loc_long()!=null
                            &&  !bookingAutomatedBrowseData.get(position).getBdb_loc_long().equals("null")
                            &&  !bookingAutomatedBrowseData.get(position).getBdb_loc_long().equals("")

                    ) {
                        Intent intent = new Intent(context, MapsActivityLocation.class);
                        intent.putExtra("lat", Double.parseDouble(bookingAutomatedBrowseData.get(position).getBdb_loc_lat()));
                        intent.putExtra("lang", Double.parseDouble(bookingAutomatedBrowseData.get(position).getBdb_loc_long()));
                        context.startActivity(intent);
                }
                    }
            });

            Log.e("booktype",bookingAutomatedBrowseData.get(position).getBookingType());
            ((Item)holder).client_name.setText(bookingAutomatedBrowseData.get(position).getData().get(0).getSupplier_name());
            final String offtypetmp=bookingAutomatedBrowseData.get(position).getBookingType();

            ((Item)holder).book_id.setText(bookingAutomatedBrowseData.get(position).getBdb_internally_number());
            ((Item)holder).reference_id.setText(bookingAutomatedBrowseData.get(position).getBdb_name_booking());

            //--- testing-----
//            ((Item) holder).accept.setText(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status());
            if (MyReservationFragment.tab.equals("2")){
//                ((Item) holder).delay.setText(R.string.deposit);
                ((Item) holder).delay.setVisibility(View.GONE);
            }
            if (MyReservationFragment.tab.equals("1")){

                if (bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status().equals("2")){
                    ((Item) holder).status.setText(R.string.accepted_res);
                    if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("1")
                            || bookingAutomatedBrowseData.get(position).getIs_action_on().equals("true")) {
                        ((Item) holder).delay.setVisibility(View.VISIBLE);
                        ((Item) holder).delay.setText(R.string.deposit);
                    }else {
                        ((Item) holder).delay.setVisibility(View.GONE);

                    }

                }else  if (bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status().equals("8")){
                    ((Item) holder).status.setText(R.string.waiting_res);
                    ((Item) holder).delay.setVisibility(View.GONE);

                }
//                ((Item) holder).delay.setVisibility(View.GONE);
            }else{
                ((Item) holder).status.setVisibility(View.GONE);
            }

            if (!MyReservationFragment.tab.equals("3")){
                ((Item) holder).rating.setVisibility(View.GONE);
                if (bookingAutomatedBrowseData.get(position).getIs_rating_on().equals("1"))
                    for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++)
                        if (bookingAutomatedBrowseData.get(position).getData().get(i).getBdb_confirm_exec_user().equals("1")){
                            ((Item) holder).time.setVisibility(View.VISIBLE);
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
//                ((Item) holder).time.setVisibility(View.GONE);
                ((Item) holder).status.setVisibility(View.VISIBLE);

                String st=bookingAutomatedBrowseData.get(position).getBdb_is_executed();
                if (st.equals("1")){
                    ((Item) holder).status.setText("لم يتم التنفيذ بشكل كامل");
                    ((Item) holder).rating.setVisibility(View.VISIBLE);
                }else if (st.equals("4")){
                    ((Item) holder).status.setText("ملغي(بعد دفع العربون)");
                    ((Item) holder).rating.setVisibility(View.GONE);
                }else if (st.equals("5")){
                    ((Item) holder).status.setText("ملغي (لم يتم دفع العربون)");
                    ((Item) holder).rating.setVisibility(View.GONE);
                }else if (st.equals("7")){
                    ((Item) holder).status.setText("منفذ بشكل كامل");
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


            String inner=bookingAutomatedBrowseData.get(position).getBdb_inner_booking();
            String byMe=bookingAutomatedBrowseData.get(position).getBooked_by_me();

            if (inner.equals("1")){
                ((Item)holder).inner_res.setImageResource(R.drawable.inner_booking);
            }
            else {
                if(byMe.equals("false"))
                    ((Item)holder).inner_res.setImageResource(R.drawable.other_client_booking);
                else
                    ((Item)holder).inner_res.setImageResource(R.drawable.client_booking);

            }



            boolean allExecuted=false;

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
                if (!MyReservationFragment.tab.equals("3"))
                ((Item) holder).time.setVisibility(View.VISIBLE);
            }
            else
                ((Item)holder).time.setVisibility(View.GONE);


            if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("true")){
                ((Item) holder).time.setText(R.string.Executed);

            }else if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("false")
                    && bookingAutomatedBrowseData.get(position).getIs_rating_on().equals("true")){
                ((Item) holder).time.setText(R.string.rate);

            }

            ((Item)holder).time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reservationModel=bookingAutomatedBrowseData.get(position);
                    String type=bookingAutomatedBrowseData.get(position).getBookingType();
                    if(type.equals("4")||type.equals("5")||type.equals("6")||type.equals("7")||type.equals("8")||type.equals("9")||type.equals("11")||type.equals("12")){
                        isOffer=true;
                        book_id=bookingAutomatedBrowseData.get(position).getBdb_name_booking();
                    }
                    book_id=bookingAutomatedBrowseData.get(position).getBdb_name_booking();

                    if (bookingAutomatedBrowseData.get(position).getIs_action_on().equals("true")) {
//                        if (bookingAutomatedBrowseData.get(position).getData().get(0).)
                        Intent intent = new Intent(context, ExecuteBookActivity.class);
                        context.startActivity(intent);
                    }else if ((bookingAutomatedBrowseData.get(position).getIs_action_on().equals("false")
                            && bookingAutomatedBrowseData.get(position).getIs_rating_on().equals("true"))
                        || ((Item) holder).time.getText().toString().equals(R.string.rate)
                    ){
                            Intent intent = new Intent(context, RateSerEmpActivity.class);
                            context.startActivity(intent);

                    }
                    }
            });

            if (offtypetmp.equals("0")) {
                String s=((AppCompatActivity)context).getResources().getString(R.string.ind_res);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                     addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(0).getService_en_name(),position,i);

                }

            }
            else if (offtypetmp.equals("1")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_booking);

                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }

            else if (offtypetmp.equals("2")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_res_other);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }
            else if (offtypetmp.equals("3")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.multi_cu_booking);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_en_name(),position,i);

                }

            }
            else if (offtypetmp.equals("4")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.single_offer_same);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_en_name(),position,i);

                }

            }
            else if (offtypetmp.equals("5")) {
//                Log.e("uuu","uuuu");
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.single_offer_multi);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);

                }

            }
            else if (offtypetmp.equals("6")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }
            else if (offtypetmp.equals("7")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.individual_bride_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);

                }

            }
            else if (offtypetmp.equals("8")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.multi_service_bride_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);

                }

            }
            else if (offtypetmp.equals("9")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_offer_bride);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }
            else if (offtypetmp.equals("10")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.Single_bride_reservation);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }
            else if (offtypetmp.equals("11")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.bride_group_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }
            else if (offtypetmp.equals("12")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.bride_group_offer_other);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }
            else if (offtypetmp.equals("13")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                //حجز متعدد (عروس)
                String s=((AppCompatActivity)context).getResources().getString(R.string.Multiple_booking_bride);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                    else
                        addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }

            if (bookingAutomatedBrowseData.get(position).getBdb_inner_booking().equals("1")) {
                ((Item) holder).refuse.setText(R.string.cancel_req);
            }else {
                ((Item) holder).refuse.setText(R.string.cancel);
            }


            ((Item) holder).refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                reservationModel=bookingAutomatedBrowseData.get(position);
                    if (bookingAutomatedBrowseData.get(position).getData().get(0).getIs_action_on_inside().equals("true")
                        ||bookingAutomatedBrowseData.get(position).getData().get(0).getIs_action_on_inside().equals("1")){
//
                        if ((!offtypetmp.equals("3") &&!offtypetmp.equals("1") && !offtypetmp.equals("10")  && !offtypetmp.equals("13")
                            && !offtypetmp.equals("4") && !offtypetmp.equals("5") && !offtypetmp.equals("7") && !offtypetmp.equals("8"))
                        && (bookingAutomatedBrowseData.get(position).getData().size()>1) &&
                                bookingAutomatedBrowseData.get(position).getIs_per_client().equals("1")
                        ){

                        {
                            //------- cancel activity reservation-----------------
                            book_id=bookingAutomatedBrowseData.get(position).getBdb_name_booking();
                            Log.e("BookIDCancel",book_id);
                            postionBook=position;

                            Intent intent=new Intent(context, CancelReservationActivity.class);
                            context.startActivity(intent);


                        }
                        }else {

                            if (bookingAutomatedBrowseData.get(position).getData().get(0).equals("7")) {
                                if (bookingAutomatedBrowseData.get(position).getBdb_inner_booking().equals("1")) {
                                    //-------cancelpaid api--------
//                            /api/booking/BookingProcessing
                                    Log.e("Outer", bookingAutomatedBrowseData.get(position).getBdb_inner_booking());
                                    Dialog dialog1 = new Dialog(context);
                                    dialog1.setContentView(R.layout.map_title_layout);
                                    final EditText reason = dialog1.findViewById(R.id.code);
                                    TextView ok = dialog1.findViewById(R.id.confirm);
                                    TextView message = dialog1.findViewById(R.id.message);
                                    message.setText(R.string.enter_reason);
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (reason.getText().toString().length() == 0) {
                                                Toast.makeText(context, R.string.enter_reason, Toast.LENGTH_LONG).show();
                                            } else {
                                                //----------------- cancel paid----------
                                                APICall.cancelPaidBooking(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(), reason.getText().toString(), context);
                                            }
                                        }
                                    });
                                } else {

                                    //------------- cancel paid ----------
                                    APICall.bookingProcessing(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(), 4, "0", context);

//                            /api/booking/cancelPaidBooking
                                }
                            }
                            else if (bookingAutomatedBrowseData.get(position).getData().get(0).equals("2") ||
                                    bookingAutomatedBrowseData.get(position).getData().get(0).equals("8")) {
                                //---------- book proccessing --------- to 0
//                        /api/booking/BookingProcessing
                                APICall.bookingProcessing(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(), 5, "0", context);

                            }
                            else {
                                //---------- Other cases
//                        /api/booking/BookingProcessing
                                APICall.bookingProcessing(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(), 5, "0", context);
                            }
                        }
                }else {
                        APICall.showSweetDialog(context,"","لا يمكن إلغاء هذا الحجز إلا من قبل العميلة صاحبة الحجز");
                    }
                }
            });



            ((Item)holder).date.setText(APICall.convertToArabic(bookingAutomatedBrowseData.get(position).getStartTime()));
            if (bookingAutomatedBrowseData.get(position).getPlace().equals("0")) {
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.salon));
            }else if (bookingAutomatedBrowseData.get(position).getPlace().equals("1")){
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.home));

            }else if (bookingAutomatedBrowseData.get(position).getPlace().equals("2")){
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.hall));

            }else if (bookingAutomatedBrowseData.get(position).getPlace().equals("3")){
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.hotel));

            }
            APICall.getSalonLogo(BeautyMainPage.context,bookingAutomatedBrowseData.get(position).getLogoId(),((Item)holder).logoImg);

            ((Item) holder).book_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        book_id=bookingAutomatedBrowseData.get(position).getBdb_name_booking();

//                        is_action_on=bookingAutomatedBrowseData.get(position).getIs_action_on();
                        Log.e("RefId",book_id);
                        Log.e("InternallyID",bookingAutomatedBrowseData.get(position).getBdb_internally_number());
                        logoId=bookingAutomatedBrowseData.get(position).getLogoId();

                        postionBook=position;


                        Intent intent=new Intent(context, ReservatoinDetailsActivity.class);
                        intent.putExtra("internally_book",bookingAutomatedBrowseData.get(position).getBdb_internally_number());
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

            ((Item)holder).totalPrice.setText(APICall.convertToArabic(bookingAutomatedBrowseData.get(position).getTotalPrice())+context.getResources().getString(R.string.ryal));


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        Log.e("bookingAutomatedcheck",bookingAutomatedBrowseData.size()+"");

        return bookingAutomatedBrowseData.size();
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

    public class Item extends RecyclerView.ViewHolder {
//        MyClickListener listener;

        TextView bookType,book_id,bdb_expected_deposit,client_name,status,delay,reference_id, totalPrice,booking_place,export_invoice,date,accept,refuse,time;
        ImageView book_Details,inner_res,logoImg,place;
        ColorRatingBar rating;

        LinearLayout myroot;
        public Item(View itemView) {
            super(itemView);
            bookType=itemView.findViewById(R.id.booktype);
            myroot=itemView.findViewById(R.id.myroot);
            status=itemView.findViewById(R.id.status);
            delay=itemView.findViewById(R.id.delay);
            rating=itemView.findViewById(R.id.rating);
            book_id=itemView.findViewById(R.id.book_id);
            place=itemView.findViewById(R.id.place);
            bdb_expected_deposit=itemView.findViewById(R.id.bdb_expected_deposit);
            reference_id=itemView.findViewById(R.id.reference_number);


            totalPrice=itemView.findViewById(R.id.total_price);
            inner_res=itemView.findViewById(R.id.inner_res);
            client_name=itemView.findViewById(R.id.client_name);
            date=itemView.findViewById(R.id.start_date);
            booking_place=itemView.findViewById(R.id.booking_place);
            book_Details=itemView.findViewById(R.id.book_details);
            refuse=itemView.findViewById(R.id.refuse);
//            accept=itemView.findViewById(R.id.accept);
            time=itemView.findViewById(R.id.time);
            logoImg=itemView.findViewById(R.id.logoImg);

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

}
