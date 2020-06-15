package com.ptm.clinicpa.Adapters;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Fragments.ReservatoinDetailsActivity;
import com.ptm.clinicpa.DataModel.BookingAutomatedBrowseData;
import com.ptm.clinicpa.DataModel.ReservationModel;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

public class ReservationsAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
    //    ArrayList <DataService> services;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData1;
    public static ArrayList<ReservationModel> bookingAutomatedBrowseData;
    //    ArrayList<DataReservation> reservations;
    int layout;
    public static Fragment fragment;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    public static String book_id="0";
    public static int postionBook;
    public ReservationsAdapter3(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public ReservationsAdapter3(Context context, ArrayList<ReservationModel> bookingAutomatedBrowseData, int layout){
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




            String inner=bookingAutomatedBrowseData.get(position).getBdb_inner_booking();

            if (inner.equals("0")){
                ((Item)holder).inner_res.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            }else {
                ((Item)holder).inner_res.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            }






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
           // inner_res=itemView.findViewById(R.id.inner_res);
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
}
