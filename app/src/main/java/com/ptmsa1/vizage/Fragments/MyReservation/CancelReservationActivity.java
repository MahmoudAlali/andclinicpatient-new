package com.ptmsa1.vizage.Fragments.MyReservation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.ReservationsAdapter2;
import com.ptmsa1.vizage.DataModel.CancelPerClientModel;
import com.ptmsa1.vizage.Fragments.ReservatoinDetailsActivity;
import com.ptmsa1.vizage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CancelReservationActivity extends AppCompatActivity {
    public static TextView empname,booktype,ac_total_price,salonName,client_name,time,price,place,descr,service_name,status,book_at,max,accept,refuse;
    public static LinearLayout myroot;
    static Boolean checkAllTrue=true;

    static Context context;
    public static ArrayList<Integer> ids=new ArrayList<>();
    public static ArrayList<CancelPerClientModel> cancelPerClientModels=new ArrayList<>();
    public static int count=0;
    static int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incom_reservation_details_layout);

        count=0;
        c=0;
        context=this;
        client_name=findViewById(R.id.name);
        myroot=findViewById(R.id.myroot);
        time=findViewById(R.id.date);
        price=findViewById(R.id.total_price);
        ac_total_price=findViewById(R.id.ac_total_price);
        place=findViewById(R.id.place);
        book_at=findViewById(R.id.book_at);
        booktype=findViewById(R.id.book_type);
        service_name=findViewById(R.id.rname);
        salonName=findViewById(R.id.salon_name);


        APICall.browseOneBookingForCancel(ReservationsAdapter2.book_id,context);

        Button ok=findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                for (int i=0;i<cancelPerClientModels.size();i++){
                    Log.e("idsss"+i,"id"+cancelPerClientModels.get(i).getId());
                    Log.e("checksss"+i,"check"+cancelPerClientModels.get(i).getCheck());
                    if (cancelPerClientModels.get(i).getCheck().isChecked()){
                        c++;
                    }
                }
//                if (checkAllTrue){
//                    if (ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).equals("7")) {
//                        if (ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getBdb_inner_booking().equals("1")) {
//                            //-------cancelpaid api--------
////                            /api/booking/BookingProcessing
//                            Log.e("Outer", ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getBdb_inner_booking());
//                            Dialog dialog1 = new Dialog(context);
//                            dialog1.setContentView(R.layout.map_title_layout);
//                            final EditText reason = dialog1.findViewById(R.id.code);
//                            TextView ok1 = dialog1.findViewById(R.id.confirm);
//                            TextView message = dialog1.findViewById(R.id.message);
//                            message.setText(R.string.enter_reason);
//                            ok1.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    if (reason.getText().toString().length() == 0) {
//                                        Toast.makeText(context, R.string.enter_reason, Toast.LENGTH_LONG).show();
//                                    } else {
//                                        //----------------- cancel paid----------
//                                        APICall.cancelPaidBooking(ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).getBdb_id(), reason.getText().toString(), context);
//                                    }
//                                }
//                            });
//                        } else {
//
//                            //------------- cancel paid ----------
//                            APICall.bookingProcessing(ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).getBdb_id(), 4, "0", context);
//
////                            /api/booking/cancelPaidBooking
//                        }
//                    }
//                    else if (ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).equals("2") ||
//                            ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).equals("8")) {
//                        //---------- book proccessing --------- to 0
////                        /api/booking/BookingProcessing
//                        APICall.bookingProcessing(ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).getBdb_id(), 5, "0", context);
//
//                    }
//                    else {
//                        //---------- Other cases
////                        /api/booking/BookingProcessing
//                        APICall.bookingProcessing(ReservationsAdapter2.bookingAutomatedBrowseData.get(ReservationsAdapter2.postionBook).getData().get(0).getBdb_id(), 5, "0", context);
//                    }
//                }
//                else
                    {

                        Log.e("countCancel","is"+c);
                        Log.e("countCancel1","is"+count);
                        if (c==count){


                                    if (ReservationsAdapter2.reservationModel.getData().get(0).equals("7")) {
                                        if (ReservationsAdapter2.reservationModel.getBdb_inner_booking().equals("1")) {
                                            //-------cancelpaid api--------
//                            /api/booking/BookingProcessing
                                            Log.e("Outer", ReservationsAdapter2.reservationModel.getBdb_inner_booking());
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
                                                        APICall.cancelPaidBooking(ReservationsAdapter2.reservationModel.getData().get(0).getBdb_id(), reason.getText().toString(), context);
                                                    }
                                                }
                                            });
                                        } else {

                                            //------------- cancel paid ----------
                                            APICall.bookingProcessing(ReservationsAdapter2.reservationModel.getData().get(0).getBdb_id(), 4, "0", context);

//                            /api/booking/cancelPaidBooking
                                        }
                                    } else if (ReservationsAdapter2.reservationModel.getData().get(0).equals("2") ||
                                            ReservationsAdapter2.reservationModel.getData().get(0).equals("8")) {
                                        //---------- book proccessing --------- to 0
//                        /api/booking/BookingProcessing
                                        APICall.bookingProcessing(ReservationsAdapter2.reservationModel.getData().get(0).getBdb_id(), 5, "0", context);

                                    } else {
                                        //---------- Other cases
//                        /api/booking/BookingProcessing
                                        APICall.bookingProcessing(ReservationsAdapter2.reservationModel.getData().get(0).getBdb_id(), 5, "0", context);
                                    }


                        }else
                    APICall.cancelPerClient(getfilterCancel(cancelPerClientModels),context);
                }
//                Toast.makeText(context,"Reservation deleted",Toast.LENGTH_LONG).show();
//                onBackPressed();
            }
        });




        }



      public static void addLayout(final LinearLayout myroot, String reservationName, String priceVal, String startTimeVal, String bdb_end_time, String bookat, String empName,String ID ,String isExec,String ac_price,String j_cost,String j_time){
        final View layout2;

        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_layout_ext_v1, myroot, false);
        TextView rname,emp_name,client_details,price,end_time,starttime,book_at,actual_price,price_j_cost,journey_time;
        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        LinearLayout ac_price_lay;
        final CheckBox check;
        price=layout2.findViewById(R.id.price);
        actual_price=layout2.findViewById(R.id.actual_price);
        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);
        price_j_cost=layout2.findViewById(R.id.price_j_cost);
        journey_time=layout2.findViewById(R.id.journey_time);
        client_details=layout2.findViewById(R.id.client_details);
        rname=layout2.findViewById(R.id.rname);
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        end_time=layout2.findViewById(R.id.end_time);
        emp_name=layout2.findViewById(R.id.emp_name);
        check=layout2.findViewById(R.id.check);

        if (ac_price.equals("0") ||ac_price.equals("") ){
            ac_price_lay.setVisibility(View.GONE);
        }else {
            actual_price.setText(ac_price);
            ReservatoinDetailsActivity.price.setPaintFlags(ReservatoinDetailsActivity.price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }

        client_details.setText(client_name.getText().toString()+", "+priceVal+" "+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        if(isExec.equals(1))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else             isExecuted.setImageResource(R.drawable.ic_cancel);


        price_j_cost.setText(j_cost+" "+context.getResources().getString(R.string.ryal));
        journey_time.setText(j_time+" "+context.getResources().getString(R.string.minute));

        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));

        final CancelPerClientModel cancelPerClientModel=new CancelPerClientModel(ID,check);

//        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    cancelPerClientModel.setCheck(true);
//                }else {
//                    cancelPerClientModel.setCheck(false);
//                }
//            }
//        });

        cancelPerClientModels.add(cancelPerClientModel);




//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bdb_end_time,String bookat,String empName ,String Id,String isExec,String ac_price){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext_v1, myroot, false);
        TextView rname,emp_name,price,starttime,book_at,end_time,actual_price,j_cost,id;
        LinearLayout ac_price_lay;

        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        end_time=layout2.findViewById(R.id.end_time);
        actual_price=layout2.findViewById(R.id.actual_price);
        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);

        if (ac_price.equals("0")){
            ac_price_lay.setVisibility(View.GONE);
        }else {
            actual_price.setText(ac_price);
        }
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else             isExecuted.setImageResource(R.drawable.ic_cancel);

//        j_cost=layout2.findViewById(R.id.price_j_cost);

//        j_cost.setText(jCost);
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));

//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addHeaderLayout(final LinearLayout myroot, String client_name, String client_old , JSONArray bookings,String booking_type){
        final View layout2;

        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_header_layout_ext_v1, myroot, false);
        TextView client_details;
        CheckBox check;
        client_details=layout2.findViewById(R.id.client_details);
        check=layout2.findViewById(R.id.checkbox);
        client_details.setText(client_name);
        int ptmp=0;
        try {
            for (int j = 0; j < bookings.length(); j++) {
                JSONObject object = bookings.getJSONObject(j);
                String bdb_price = object.getString("bdb_price");
                ptmp=ptmp+Integer.parseInt(bdb_price);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String priceRes=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.price);
        if (client_name.equals("booking_wast_time")){
//            client_details.setText(R.string.lost_time_emp);
        }else
            client_details.setText(client_name+", "+priceRes+APICall.convertToArabic(ptmp+"")+" "+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
        try {
            for (int j = 0; j < bookings.length(); j++) {
                JSONObject object = bookings.getJSONObject(j);
                Log.e("objectBookings", object.toString());
//                                    if (!name.equals("booking_wast_time")) {
                if (j == 0)
                    client_name = object.getString("bdb_user_name");
//                                    }

                String bdb_price = object.getString("bdb_price"),
                        bdb_id = object.getString("bdb_id"),
                        bdb_start_date = object.getString("bdb_start_date"),
                        bdb_start_time = object.getString("bdb_start_time"),
                        bdb_end_time = object.getString("bdb_end_time"),
                        bdb_is_executed = object.getString("bdb_confirm_exec_sup"),
                        journey_time = object.getString("bdb_journey_time"),
                        bdb_booked_at = object.getString("bdb_booked_at");
                String bdb_emp_name = object.getString("bdb_emp_name");
                String bdb_name = object.getString("bdb_name"),
                        bdb_name_ar = object.getString("bdb_name_ar");

//                                    CancelReservationActivity.time.setText(convertToArabic(bdb_start_date));

                String ac_price = "";
                try {
                    ac_price = object.getString("bdb_sup_final_price");
                } catch (Exception e) {

                }


//                                    if (name.equals("booking_wast_time") ){
//                                        if (!BeautyMainPage.FRAGMENT_NAME.equals("MYRESERVATIONEXECUTEDFRAGMENT"))
//                                            try {
//                                                String reason = object.getString("reason");
//                                                CancelReservationActivity.addMainLayoutLost(CancelReservationActivity.myroot,bdb_start_time,bdb_end_time,bdb_emp_name,reason);
//                                            }catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                    }else
                {
                    if (booking_type.equals("20") || booking_type.equals("21") ||
                            booking_type.equals("22") || booking_type.equals("23")
                            || booking_type.equals("25") || booking_type.equals("24")) {
                        if (context.getResources().getString(R.string.locale).equals("ar"))
                            CancelReservationActivity.addMainLayout(CancelReservationActivity.myroot,
                                    bdb_name_ar, bdb_price, bdb_start_date + ", " + bdb_start_time
                                    , bdb_end_time, bdb_booked_at, bdb_emp_name, bdb_id, bdb_is_executed, ac_price, journey_time);
                        else
                            CancelReservationActivity.addMainLayout(CancelReservationActivity.myroot,
                                    bdb_name, bdb_price, bdb_start_date + ", " + bdb_start_time
                                    , bdb_end_time, bdb_booked_at, bdb_emp_name, bdb_id, bdb_is_executed, ac_price, journey_time);

                    } else if (context.getResources().getString(R.string.locale).equals("ar"))
                        CancelReservationActivity.addMainLayout(CancelReservationActivity.myroot,
                                bdb_name_ar, bdb_price, bdb_start_date + ", " + bdb_start_time
                                , bdb_end_time, bdb_booked_at, bdb_emp_name, bdb_id, bdb_is_executed, ac_price);
                    else
                        CancelReservationActivity.addMainLayout(CancelReservationActivity.myroot,
                                bdb_name, bdb_price, bdb_start_date + ", " + bdb_start_time
                                , bdb_end_time, bdb_booked_at, bdb_emp_name, bdb_id, bdb_is_executed, ac_price);
                }

                cancelPerClientModels.add(new CancelPerClientModel(bdb_id,check));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
//
    }
    public static void addMainLayout(final LinearLayout myroot,String reservationName,String priceVal,String startTimeVal,String bdb_end_time,String bookat,String empName ,String Id,String isExec,String ac_price,String jtime){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_ext_v1, myroot, false);
        TextView rname,emp_name,price,starttime,book_at,end_time,actual_price,j_cost,id;
        LinearLayout ac_price_lay;

        ImageView isExecuted=layout2.findViewById(R.id.isExecuted);
        price=layout2.findViewById(R.id.price);
        rname=layout2.findViewById(R.id.rname);
        end_time=layout2.findViewById(R.id.end_time);
        actual_price=layout2.findViewById(R.id.actual_price);
        ac_price_lay=layout2.findViewById(R.id.ac_price_lay);

        if (ac_price.equals("0")){
            ac_price_lay.setVisibility(View.GONE);
        }else {
            actual_price.setText(ac_price);
        }
        starttime=layout2.findViewById(R.id.time);
        book_at=layout2.findViewById(R.id.book_at);
        emp_name=layout2.findViewById(R.id.emp_name);
        if(isExec.equals("1"))
            isExecuted.setImageResource(R.drawable.ic_checked);
        else             isExecuted.setImageResource(R.drawable.ic_cancel);

        j_cost=layout2.findViewById(R.id.journey_time);

        j_cost.setText(jtime);
        end_time.setText(APICall.convertToArabic(bdb_end_time));
        rname.setText(reservationName);
//        rname.setText(reservationName);
        emp_name.setText(empName);
        price.setText(APICall.convertToArabic(priceVal)+((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.ryal));
        starttime.setText(APICall.convertToArabic(startTimeVal));
        book_at.setText(APICall.convertToArabic(bookat));

//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }
    public static void addMainLayoutLost(final LinearLayout myroot,String startTimeVal,String bdb_end_time,String empName,String reasontxt ){
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.incom_reservation_details_main_layout_lost_ext, myroot, false);
        TextView emp_name,starttime,reason;
        starttime=layout2.findViewById(R.id.time);
        emp_name=layout2.findViewById(R.id.emp_name);
        reason=layout2.findViewById(R.id.reason);

        if (reasontxt.equals("0")) {
            reason.setText(R.string.journey_lost_time);
        }else if (reasontxt.equals("1")) {
            reason.setText(R.string.rel_ser_lost_time);
        }else if (reasontxt.equals("2")) {
            reason.setText(R.string.wait_emp_lost_time);
        }
        emp_name.setText(empName);
        starttime.setText(APICall.convertToArabic(startTimeVal+" ~ "+bdb_end_time));

//
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//
    }




    //{\n    \"cancel_booking\" : [820],\n    \"bdb_name_booking\" : 494\n    \n}


    JSONObject getfilterCancel(ArrayList<CancelPerClientModel> cancelPerClientModels){
        JSONObject object=new JSONObject();
        try{
            for (int i=0;i<cancelPerClientModels.size();i++){
                if (!cancelPerClientModels.get(i).getCheck().isChecked()){
                    checkAllTrue=false;
                    break;
                }
            }
            JSONArray array=new JSONArray();
            Log.e("cancelPerCSize","is:"+cancelPerClientModels.size()+"");
            for (int i=0;i<cancelPerClientModels.size();i++){
                if (cancelPerClientModels.get(i).getCheck().isChecked()){
                    array.put(Integer.parseInt(cancelPerClientModels.get(i).getId()));
                }
            }

            object.put("cancel_booking",array);
            object.put("bdb_name_booking",ReservationsAdapter2.book_id);

        }catch (Exception e){
            e.printStackTrace();
        }


    return object;
    }


}
