package com.dcoret.beautyclient.Adapters;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.DataClass.ReservationModel;
import com.dcoret.beautyclient.Fragments.ReservationDetailsFragment;
import com.dcoret.beautyclient.R;


import java.util.ArrayList;

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

            String offtypetmp=bookingAutomatedBrowseData.get(position).getBookingType();
            if (offtypetmp.equals("0")) {
                ((Item) holder).bookType.setText("حجز فردي");
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");

                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("1")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("حجز جماعي");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("2")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("حجز جماعي للغير");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("3")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("حجز متعدد للعميل");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("4")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("عرض فردي (بنفس اليوم)");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("5")) {
                Log.e("uuu","uuuu");
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("عرض فردي (متعدد)");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("6")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("عرض جماعي");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("7")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("عرض عروس فردي");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }else if (offtypetmp.equals("8")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("عرض عروس متعدد الخدمات");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
//                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
//                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
//                }

            }else if (offtypetmp.equals("9")) {
                Log.e("booktypesyze",bookingAutomatedBrowseData.get(position).getData().size()+"");
                ((Item) holder).bookType.setText("عرض عروس و مرافقاتها");
                Log.e("booktypesyze1","1"+bookingAutomatedBrowseData.get(position).getData().get(0).getService_ar_name()+"");
                ((Item)holder).myroot.removeAllViews();
                for (int i=0;i<bookingAutomatedBrowseData.get(position).getData().size();i++){
                    addLayout(((Item)holder).myroot,bookingAutomatedBrowseData.get(position).getData().get(i).getService_ar_name(),position,i);
                }

            }




            ((Item)holder).date.setText(bookingAutomatedBrowseData.get(position).getStartTime());
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


                        fragment = new ReservationDetailsFragment();
                        fm = ((AppCompatActivity) context).getFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, fragment);
                        fragmentTransaction.commit();
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

            ((Item)holder).totalPrice.setText(bookingAutomatedBrowseData.get(position).getTotalPrice()+context.getResources().getString(R.string.ryal));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
//        Log.e("bookingAutomated",bookingAutomatedBrowseData.size()+"");
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

        TextView bookType, totalPrice,booking_place,export_invoice,date,accept,refuse,time;
        ImageView book_Details;

        LinearLayout myroot;
        public Item(View itemView, MyClickListener listener) {
            super(itemView);
            bookType=itemView.findViewById(R.id.booktype);
            myroot=itemView.findViewById(R.id.myroot);

            totalPrice=itemView.findViewById(R.id.total_price);
            date=itemView.findViewById(R.id.start_date);
            booking_place=itemView.findViewById(R.id.booking_place);
            book_Details=itemView.findViewById(R.id.book_details);
            refuse=itemView.findViewById(R.id.refuse);
            accept=itemView.findViewById(R.id.accept);
            time=itemView.findViewById(R.id.edit_time);
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
