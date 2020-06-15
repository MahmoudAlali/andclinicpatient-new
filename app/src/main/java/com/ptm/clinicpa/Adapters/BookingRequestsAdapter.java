package com.ptm.clinicpa.Adapters;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.BookingRequestDetailsActivity;
import com.ptm.clinicpa.DataModel.BookingRequestDataModel;
import com.ptm.clinicpa.Fragments.MyBookingRequestsFragment;
import com.ptm.clinicpa.MapsActivityLocation;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

import static com.ptm.clinicpa.Fragments.ReservatoinDetailsActivity.myroot;

public class BookingRequestsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
    //    ArrayList <DataService> services;
   // public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData1;
    public static ArrayList<BookingRequestDataModel> bookingRequestData;
    //    ArrayList<DataReservation> reservations;
    int layout;
    public static Fragment fragment;
    public static Boolean isOffer=false;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    public static String book_id="0",logoId;
    public static int postionBook;
    public BookingRequestsAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public BookingRequestsAdapter(Context context, ArrayList<BookingRequestDataModel> bookingRequestData){
        this.context=context;
        this.bookingRequestData=bookingRequestData;
        this.layout=layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.booking_request_item_layout,parent,false);
        Item item=new Item(row);
        return item;


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

           // Log.e("booktype",bookingAutomatedBrowseData.get(position).getBookingType());
        String S= context.getResources().getString(R.string.doctorNames) +": "/*+bookingRequestData.get(position).getSupplier_name()*/;
            ((Item)holder).doctorName.setText(S);
           // String offtypetmp=bookingAutomatedBrowseData.get(position).getBookingType();


            //--- testing-----
//            ((Item) holder).accept.setText(bookingAutomatedBrowseData.get(position).getData().get(0).getBdb_status());
            if (MyBookingRequestsFragment.tab.equals("2")){
                if (bookingRequestData.get(position).getBdb_status().equals("1")){

                    ((Item) holder).cancel.setText(R.string.requestRelatedBooking);
                }
                else
                    ((Item) holder).cancel.setVisibility(View.GONE);

            }

            // >>>>>>>>>>>>> status
        Log.e("StatusTab",MyBookingRequestsFragment.tab);
        Log.e("StatusType",bookingRequestData.get(position).getBdb_is_group_booking());
            if (MyBookingRequestsFragment.tab.equals("2"))
            {

                if(bookingRequestData.get(position).getBdb_is_group_booking().equals("20")
                        ||bookingRequestData.get(position).getBdb_is_group_booking().equals("21")
                        ||bookingRequestData.get(position).getBdb_is_group_booking().equals("23")
                        ||bookingRequestData.get(position).getBdb_is_group_booking().equals("24"))
                {
                    Log.e("Status",bookingRequestData.get(position).getBdb_status());

                    if (bookingRequestData.get(position).getBdb_status().equals("1")){
                        ((Item) holder).status.setText(R.string.approved);
                    }else  if (bookingRequestData.get(position).getBdb_status().equals("2")){
                        ((Item) holder).status.setText(R.string.rejectedByProvider);
                    }else  if (bookingRequestData.get(position).getBdb_status().equals("3")){
                        ((Item) holder).status.setText(R.string.canceledByClient);
                    }else  if (bookingRequestData.get(position).getBdb_status().equals("4")){
                        ((Item) holder).status.setText(R.string.canceledBySystem);
                    }


                }
                else {

                    ((Item) holder).status.setVisibility(View.GONE);
                }
            }

            else {
                ((Item) holder).status.setVisibility(View.GONE);
            }

            //>>>>>>>> add all services layouts
        ((Item)holder).myroot.removeAllViews();
            if(bookingRequestData.get(position).getBdb_is_group_booking().equals("20")
                    ||bookingRequestData.get(position).getBdb_is_group_booking().equals("21")
                    ||bookingRequestData.get(position).getBdb_is_group_booking().equals("23")
                    ||bookingRequestData.get(position).getBdb_is_group_booking().equals("24")) /// individual
            {
                for ( int i=0;i<bookingRequestData.get(position).getClients().size();i++)
                {
                    for ( int j=0;j<bookingRequestData.get(position).getClients().get(i).getServices().size();j++)
                    {
                        if(context.getResources().getString(R.string.locale).equals("en"))
                            addLayout(((Item)holder).myroot,bookingRequestData.get(position).getClients().get(i).getServices().get(j).getBdb_name());
                        else
                            addLayout(((Item)holder).myroot,bookingRequestData.get(position).getClients().get(i).getServices().get(j).getBdb_name_ar());

                    }


                }
            }


            // >>>>>>>>>>>>> cancel Button
            ((Item) holder).cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (MyBookingRequestsFragment.tab.equals("2"))
                    {
                        Intent i = new Intent(context,BeautyMainPage.class);
                        Log.e("NAMEBOOKING",bookingRequestData.get(position).getBdb_name_booking());
                        i.putExtra("goToReservation",bookingRequestData.get(position).getBdb_name_booking());
                        context.startActivity(i);
                        ((AppCompatActivity) context).finish();
                    }
                    else
                    {
                        Log.e("Outer","بب");
                        final Dialog dialog1=new Dialog(context);
                        dialog1.setContentView(R.layout.sweet_dialog_layout_v4);
                        dialog1.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        TextView confirm=dialog1.findViewById(R.id.confirm);
                        TextView cancel=dialog1.findViewById(R.id.cancel);
                        TextView message=dialog1.findViewById(R.id.message);
                        message.setText(R.string.cancelRequestAlert);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //----------------- cancel paid----------
                                dialog1.dismiss();
                                APICall.cancelBookingRequest(bookingRequestData.get(position).getBdb_id(),context);

                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();
                    }

                            }

            });




            // >>>>>>>>>>>>> date
            ((Item)holder).date.setText(APICall.convertToArabic(bookingRequestData.get(position).getBdb_start_dateReq()));

            //>>>>>>>>> price
        String price = APICall.convertToArabic(bookingRequestData.get(position).getCost());
        price+= " "+BeautyMainPage.context.getResources().getString(R.string.ryal);

        ((Item)holder).totalPrice.setText(price);


        //>>>>>>> ID
        String id =context.getString(R.string.order_id)+ bookingRequestData.get(position).getBdb_id();
        ((Item)holder).ID.setText(id);


        //>>>>>>>> center name
        if(context.getString(R.string.locale).equals("en"))
            ((Item)holder).client_name.setText(bookingRequestData.get(position).getHealthCntr_en());
        else
            ((Item)holder).client_name.setText(bookingRequestData.get(position).getHealthCntr_ar());


        //>>>>>>>>>>>> patient name
        ((Item)holder).show_clients_names.setText(bookingRequestData.get(position).getClients().get(0).getBdb_client_name());




        // >>>>>>>>>>>>> order booking place
            if (bookingRequestData.get(position).getBdb_booking_place().equals("0")) {
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.salon));
            }else if (bookingRequestData.get(position).getBdb_booking_place().equals("1")){
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.home));

            }/*else if (bookingRequestData.get(position).getBdb_booking_place().equals("2")){
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.hall));

            }else if (bookingRequestData.get(position).getBdb_booking_place().equals("3")){
                ((Item) holder).booking_place.setText(context.getResources().getString(R.string.hotel));

            }*/


            // >>>>>>>> Request type
        if(bookingRequestData.get(position).getBdb_is_group_booking().equals("20"))
            ((Item) holder).bookType.setText(R.string.indivRequest);
        else if(bookingRequestData.get(position).getBdb_is_group_booking().equals("21"))
            ((Item) holder).bookType.setText(R.string.indivMultiRequest);
        else if(bookingRequestData.get(position).getBdb_is_group_booking().equals("22"))
            ((Item) holder).bookType.setText(R.string.groupRequest);
        else if(bookingRequestData.get(position).getBdb_is_group_booking().equals("23"))
            ((Item) holder).bookType.setText(R.string.indivRequestOffer);
        else if(bookingRequestData.get(position).getBdb_is_group_booking().equals("24"))
            ((Item) holder).bookType.setText(R.string.indivMultiRequestOffer);
        else if(bookingRequestData.get(position).getBdb_is_group_booking().equals("25"))
            ((Item) holder).bookType.setText(R.string.groupRequestOffer);





        // >>>>>>>>>>>>> provider logo
            APICall.getSalonLogoDltWhenEmpty(BeautyMainPage.context,bookingRequestData.get(position).getLogo_id(),((Item)holder).logoImg);



            //---------------------
        ((Item)holder).place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bookingRequestData.get(position).getBdb_loc_lat().equals("")
                        && !bookingRequestData.get(position).getBdb_loc_lat().equals("null")
                        && !bookingRequestData.get(position).getBdb_loc_long().equals("")
                        && !bookingRequestData.get(position).getBdb_loc_long().equals("null")
                ){
                    Intent intent=new Intent(context, MapsActivityLocation.class);
                    intent.putExtra("lat",Double.parseDouble(bookingRequestData.get(position).getBdb_loc_lat()));
                    intent.putExtra("lang",Double.parseDouble(bookingRequestData.get(position).getBdb_loc_long()));
                    context.startActivity(intent);


                }
            }
        });

        //>>>>>>>>>> Doctor Names
        for (int i=0;i<bookingRequestData.get(position).getClients().size();i++)
        {
            final View layout2;
            layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.doc_name_layout, ((Item)holder).doctorNamesLayout, false);
            TextView doctorName;
            doctorName=layout2.findViewById(R.id.rname);
           // final View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_layout_request, ((Item)holder).doctorNamesLayout, false);

            doctorName.setText(bookingRequestData.get(position).getClients().get(i).getDoctor_name());
           // doctorName.setTextColor(R.color.doctorNames);
           // doctorName.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            //doctorName.setGravity(View.TEXT_ALIGNMENT_CENTER);
           /* LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            doctorName.setLayoutParams(textParam);*/
            if(doctorName.getParent() != null) {
                ((ViewGroup)doctorName.getParent()).removeView(doctorName); // <- fix
            }
            ((Item)holder).doctorNamesLayout.addView(doctorName);
        }



            // >>>>>>>>>>>>> order details
            ((Item) holder).book_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        book_id=bookingRequestData.get(position).getBdb_id();
                        Log.e("BookID",book_id);
                        logoId=bookingRequestData.get(position).getLogo_id();

                        Intent intent=new Intent(context, BookingRequestDetailsActivity.class);
                        intent.putExtra("order_id",book_id);
                        intent.putExtra("logo_id",logoId);

                        context.startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

          //  ((Item)holder).totalPrice.setText(APICall.convertToArabic(bookingAutomatedBrowseData.get(position).getTotalPrice())+context.getResources().getString(R.string.ryal));



    }


    @Override
    public int getItemCount() {
        Log.e("bookingAutomatedcheck",bookingRequestData.size()+"");

        return bookingRequestData.size();
    }
    public static void addLayout(final LinearLayout myroot, String serviceName)
    {
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_layout_request, myroot, false);
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

       /* if (i!=bookingAutomatedBrowseData.get(postion).getData().size()-1) {
//            if (APICall.layout==R.layout.incom_reservation_layout) {
//                accept.setVisibility(View.GONE);
//            }
//            refuse.setVisibility(View.GONE);
//            edit_time.setVisibility(View.GONE);
        }*/
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

        TextView bookType,client_name,status, totalPrice,booking_place,export_invoice,date,accept,cancel,ID,doctorName,show_clients_names;
        ImageView book_Details,inner_res,place,logoImg;
        LinearLayout doctorNamesLayout;

        LinearLayout myroot;
        public Item(View itemView) {
            super(itemView);
            bookType=itemView.findViewById(R.id.booktype);
            myroot=itemView.findViewById(R.id.myroot);
            status=itemView.findViewById(R.id.status);

            totalPrice=itemView.findViewById(R.id.total_price);
            //inner_res=itemView.findViewById(R.id.inner_res);
            client_name=itemView.findViewById(R.id.client_name);
            date=itemView.findViewById(R.id.start_date);
            booking_place=itemView.findViewById(R.id.booking_place);
            book_Details=itemView.findViewById(R.id.book_details);
            cancel=itemView.findViewById(R.id.refuse);
            place=itemView.findViewById(R.id.place);
            logoImg=itemView.findViewById(R.id.logoImg);
            ID=itemView.findViewById(R.id.order_num);
            doctorName=itemView.findViewById(R.id.doctorName);
            show_clients_names=itemView.findViewById(R.id.show_clients_names);
            doctorNamesLayout=itemView.findViewById(R.id.doctorNamesLayout);

        }


    }
}

