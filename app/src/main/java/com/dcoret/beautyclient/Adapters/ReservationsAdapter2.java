package com.dcoret.beautyclient.Adapters;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.ReservatoinDetailsActivity;
import com.dcoret.beautyclient.DataModel.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.DataModel.DateTimeModel;
import com.dcoret.beautyclient.DataModel.ReservationModel;
import com.dcoret.beautyclient.Fragments.MyReservation.ExecuteBookActivity;
import com.dcoret.beautyclient.R;


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
    public static Boolean isOffer;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    public static String book_id="0";
    public static int postionBook;
    public ReservationsAdapter2(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public ReservationsAdapter2(Context context, ArrayList<ReservationModel> bookingAutomatedBrowseData, int layout){
        this.context=context;
        this.bookingAutomatedBrowseData=bookingAutomatedBrowseData;
        this.layout=layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.incom_reservation_layout_ext,parent,false);
        Item item=new Item(row, new MyClickListener() {
            @Override
            public void resrve(int p) {
                Toast.makeText(context,"ok", Toast.LENGTH_LONG).show();

            }

            @Override
            public void more(int p) {
                Toast.makeText(context,"ok", Toast.LENGTH_LONG).show();

            }
        });
        return item;


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        try {
            Log.e("booktype",bookingAutomatedBrowseData.get(position).getBookingType());
            ((Item)holder).client_name.setText(bookingAutomatedBrowseData.get(position).getData().get(0).getSupplier_name());
            String offtypetmp=bookingAutomatedBrowseData.get(position).getBookingType();


            //--- testing-----
//            ((Item) holder).accept.setText(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status());


            String inner=bookingAutomatedBrowseData.get(position).getBdb_inner_booking();

            if (inner.equals("0")){
                ((Item)holder).inner_res.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            }else {
                ((Item)holder).inner_res.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
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

                            if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition())))
                                allExecuted = true;

                        }else if (!dateTimeModels.get(i).getDate()
                                .equals(dateTimeModels.get(i-1).getDate())) {

//                                    if (isPast(bookingAutomatedBrowseData.get(position).getData().get(i-1)))
                            if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i-1).getPosition())))
                                allExecuted = true;
                        }else if (!dateTimeModels.get(i).getDate()
                                .equals(dateTimeModels.get(i+1).getDate())
                                && i+1==dateTimeModels.size()) {

//                                    if (isPast(bookingAutomatedBrowseData.get(position).getData().get(i)))
//                                        allExecuted = true;

                            if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i+1).getPosition())))
                                allExecuted = true;
                        }
                    }
                    if (bookingAutomatedBrowseData.get(position).getData().size()==2){
                        Log.e("967"+bookingAutomatedBrowseData.get(position).getClient_name()+i,isPast(bookingAutomatedBrowseData.get(position).getData().get(i))+"");
                        if (isPast(bookingAutomatedBrowseData.get(position).getData().get(i)))
                            allExecuted = true;
                    }

                }else
                    Log.e("967"+bookingAutomatedBrowseData.get(position).getClient_name()+i,isPast(bookingAutomatedBrowseData.get(position).getData().get(i))+"");

                if (isPast(bookingAutomatedBrowseData.get(position).getData().get(dateTimeModels.get(i).getPosition())))
                    allExecuted = true;
            }
//                }
            if(allExecuted && !BeautyMainPage.FRAGMENT_NAME.equals("MYRESERVATIONEXECUTEDFRAGMENT")) {
                ((Item) holder).time.setText(R.string.Executed);
                ((Item) holder).time.setVisibility(View.VISIBLE);
            }
            else
                ((Item)holder).time.setVisibility(View.GONE);

            ((Item)holder).time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type=bookingAutomatedBrowseData.get(position).getBookingType();
                    if(type.equals("4")||type.equals("5")||type.equals("6")||type.equals("7")||type.equals("8")||type.equals("9")||type.equals("11")||type.equals("12"))
                        isOffer=true;
                    book_id=bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id();
                    Intent intent=new Intent(context, ExecuteBookActivity.class);
                    context.startActivity(intent);

                }
            });

            if (offtypetmp.equals("0")) {
                String s=((AppCompatActivity)context).getResources().getString(R.string.ind_res);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("1")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_booking);

                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("2")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_res_other);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("3")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.multi_cu_booking);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("4")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.single_offer_same);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("5")) {
//                Log.e("uuu","uuuu");
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.single_offer_multi);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("6")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("7")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.individual_bride_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("8")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.multi_service_bride_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("9")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.group_offer_bride);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("10")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.Single_bride_reservation);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("11")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.bride_group_offer);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("12")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                String s=((AppCompatActivity)context).getResources().getString(R.string.bride_group_offer_other);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("13")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                //حجز متعدد (عروس)
                String s=((AppCompatActivity)context).getResources().getString(R.string.Multiple_booking_bride);
                ((Item) holder).bookType.setText(s);
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
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
                    if (bookingAutomatedBrowseData.get(position).getBookingType().equals("7")) {
                        if (bookingAutomatedBrowseData.get(position).getBdb_inner_booking().equals("1")) {
                            //-------cancelpaid api--------
//                            /api/booking/BookingProcessing
                            Log.e("Outer",bookingAutomatedBrowseData.get(position).getBdb_inner_booking());
                            Dialog dialog1=new Dialog(context);
                            dialog1.setContentView(R.layout.map_title_layout);
                            final EditText reason=dialog1.findViewById(R.id.code);
                            TextView ok=dialog1.findViewById(R.id.confirm);
                            TextView message=dialog1.findViewById(R.id.message);
                            message.setText(R.string.enter_reason);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (reason.getText().toString().length()==0){
                                        Toast.makeText(context,R.string.enter_reason,Toast.LENGTH_LONG).show();
                                    }else {
                                        //----------------- cancel paid----------
                                APICall.cancelPaidBooking(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(),reason.getText().toString(),context);
                                    }
                                    }
                            });
                        } else {

                            //------------- cancel paid ----------
                            APICall.bookingProcessing(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(),4,"0",context);

//                            /api/booking/cancelPaidBooking
                        }
                    }else if (bookingAutomatedBrowseData.get(position).getBookingType().equals("2") ||
                    bookingAutomatedBrowseData.get(position).getBookingType().equals("8")) {
                        //---------- book proccessing --------- to 0
//                        /api/booking/BookingProcessing
                        APICall.bookingProcessing(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(),4,"0",context);

                    }else {
                        //---------- Other cases
//                        /api/booking/BookingProcessing
                        APICall.bookingProcessing(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id(),4,"0",context);
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
            ((Item) holder).book_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        book_id=bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_id();
                        Log.e("BookID",book_id);
                        postionBook=position;


                        Intent intent=new Intent(context, ReservatoinDetailsActivity.class);
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


        if (APICall.layout==R.layout.incom_reservation_layout){

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



        if (APICall.layout==R.layout.accept_reservation_layout_v2) {

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

        if (APICall.layout==R.layout.incom_reservation_layout) {

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
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

        TextView bookType,client_name, totalPrice,booking_place,export_invoice,date,accept,refuse,time;
        ImageView book_Details,inner_res;

        LinearLayout myroot;
        public Item(View itemView, MyClickListener listener) {
            super(itemView);
            bookType=itemView.findViewById(R.id.booktype);
            myroot=itemView.findViewById(R.id.myroot);

            totalPrice=itemView.findViewById(R.id.total_price);
            inner_res=itemView.findViewById(R.id.inner_res);
            client_name=itemView.findViewById(R.id.client_name);
            date=itemView.findViewById(R.id.start_date);
            booking_place=itemView.findViewById(R.id.booking_place);
            book_Details=itemView.findViewById(R.id.book_details);
            refuse=itemView.findViewById(R.id.refuse);
//            accept=itemView.findViewById(R.id.accept);
            time=itemView.findViewById(R.id.time);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.reserv_btn:
//                    listener.resrve(this.getLayoutPosition());
//                    break;
////                case R.id.more_btn:
////                    listener.more(this.getLayoutPosition());
////                    break;
//                default:
//                    break;
            }
        }
    }
    public interface MyClickListener {
        void resrve(int p);
        void more(int p);
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
            String ReserTimeStr = reservation.getBdb_start_time();
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
}
