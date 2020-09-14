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
import com.ptm.clinicpa.Fragments.DepositReservationFragment;
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
    public static String book_id="0",is_action_on="",logoId,healthRecord="";
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
        View row=inflater.inflate(R.layout.incom_reservation_layout_ext2,parent,false);
        Item item=new Item(row);
        return item;


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        try {


            if(appointmentsDataModels.get(position).getIs_has_change_order().equals("1")&&isNew)
                ((Item)holder).bdb_expected_deposit.setText(context.getResources().getString(R.string.appUnderEditing));
            else
            {
                ((Item)holder).bdb_expected_deposit.setVisibility(View.GONE);
                ((Item)holder).isEdited.setVisibility(View.GONE);

            }



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

            String visit=context.getString(R.string.visit_type);
            if(vizitType.equals("0"))
                visit+=context.getString(R.string.newVisit);
            else if(vizitType.equals("1"))
                visit+=context.getString(R.string.oldVisit);
            else
                visit+=context.getString(R.string.unDeterminedVisit);

            ((Item)holder).visitType.setText(visit);




            ((Item)holder).book_id.setText(context.getString(R.string.ref_number)+": "+appointmentsDataModels.get(position).getBdb_appointment_id());
            ((Item)holder).reference_id.setText(context.getString(R.string.book_id)+appointmentsDataModels.get(position).getBdb_internally_number());

            if(!isNew)
            {
                ((Item)holder).centerRating.setText(appointmentsDataModels.get(position).getHealth_center_rating());
                ((Item)holder).docRating.setText(appointmentsDataModels.get(position).getDoctor_rating());
                if (appointmentsDataModels.get(position).getStatus().equals("0")) {
                    Log.e("11111","444");
                    ((Item) holder).status.setImageResource(R.drawable.appointment_done);
                }else if (appointmentsDataModels.get(position).getStatus().equals("1")){
                    Log.e("11111","555");

                    ((Item) holder).status.setImageResource(R.drawable.appointment_cancelled);
                }
            }
            else
            {
                ((Item)holder).centerStar.setVisibility(View.GONE);
                ((Item)holder).centerRating.setVisibility(View.GONE);
                ((Item)holder).docRatingLayout.setVisibility(View.GONE);
                ((Item)holder).status.setVisibility(View.GONE);
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

                ((Item)holder).isChecked.setImageResource(R.drawable.checked_in);
            }
            Log.e("11111","333");

            ((Item)holder).date.setText(APICall.convertToArabic(appointmentsDataModels.get(position).getBdb_start_date()));
            ((Item)holder).booktime.setText(APICall.convertToArabic(appointmentsDataModels.get(position).getBdb_start_time()));

            String place=context.getString(R.string.service_place);
            if (appointmentsDataModels.get(position).getBooking_place().equals("0")) {
                Log.e("11111","444");
                ((Item) holder).booking_place.setImageResource(R.drawable.service_at_center);
                place+=context.getString(R.string.salon);
            }else if (appointmentsDataModels.get(position).getBooking_place().equals("1")){
                Log.e("11111","555");
                place+=context.getString(R.string.home);
                ((Item) holder).booking_place.setImageResource(R.drawable.service_at_home);
            }
            ((Item) holder).bookPlaceTxt.setText(place);

            String speciality;
            if (context.getString(R.string.locale).equals("en"))
             speciality=context.getString(R.string.speciality_points)+" "+appointmentsDataModels.get(position).getSpecialization_en();
            else
                speciality=context.getString(R.string.speciality_points)+" "+appointmentsDataModels.get(position).getSpecialization_ar();


            ((Item) holder).speciality.setText(speciality);


            Log.e("11111","666");

            String docN=context.getString(R.string.doctorName) +": "+appointmentsDataModels.get(position).getDoctor_name();
            ((Item)holder).docName.setText(docN);
            Log.e("11111","777");

            String patientName=context.getString(R.string.patient_name)+": "+appointmentsDataModels.get(position).getClient_name();
            ((Item)holder).patName.setText(patientName);
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
            final String appointmentType=appointmentsDataModels.get(position).getBdb_is_group_booking();
            String name =context.getString(R.string.total_price);
            String serPrieName =context.getString(R.string.ser_cost);
            if(appointmentType.equals("23")||appointmentType.equals("24")||appointmentType.equals("25")) // offer appointment
            {
                name=context.getString(R.string.cost_offer);
                ((Item)holder).ser_price.setVisibility(View.GONE);
            }
            String allPrice=name+a+" "+context.getString(R.string.ryal);
            String allPrice2=serPrieName+servicesPrice+" "+context.getString(R.string.ryal);
            if(appointmentsDataModels.get(position).getBasic_price().equals("null")||appointmentsDataModels.get(position).getBasic_price().equals("0"))
            {
                ((Item)holder).totalPrice.setText(context.getString(R.string.unDeterminedPrice));
            }
            else
              ((Item)holder).totalPrice.setText(allPrice);
            if(appointmentsDataModels.get(position).getServices_price().equals("null")||appointmentsDataModels.get(position).getServices_price().equals("0"))
            {
                ((Item)holder).ser_price.setText(context.getString(R.string.unDeterminedPrice));
            }
            else
                ((Item)holder).ser_price.setText(allPrice2);



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


            if(isNew)
            {
                if( appointmentsDataModels.get(position).getCan_check_in().equals("0"))
                {
                    ((Item) holder).checkIn.setVisibility(View.GONE);
                    ((Item) holder).space.setVisibility(View.GONE);
                }
                else if( appointmentsDataModels.get(position).getCan_check_in().equals("2"))
                {
                    ((Item) holder).checkIn.setVisibility(View.GONE);
                    ((Item) holder).space.setVisibility(View.GONE);
                    ((Item) holder).isBeingChecked.setVisibility(View.VISIBLE);
                }
                else if( appointmentsDataModels.get(position).getCan_check_in().equals("1"))
                {
                    ((Item)holder).checkIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("CheckIn","ediiiiiiiiiiit..");

                            book_id=appointmentsDataModels.get(position).getBdb_appointment_id();
                            healthRecord=appointmentsDataModels.get(position).getHealth_record();
                       /* if(appointmentsDataModels.get(position).getHealth_record().equals("")||appointmentsDataModels.get(position).getHealth_record().equals("null"))
                        {
                            Dialogs getReasonDialog =new Dialogs(context, R.string.empty, R.string.enterReasonMsg, R.string.ok,OnClickCallMeBtn,context.getString(R.string.med_id));
                            getReasonDialog.show();
                        }
                        else
                        {
                            APICall.CheckIn(context,book_id,appointmentsDataModels.get(position).getHealth_record());
                        }*/
                            ((AppCompatActivity)context).startActivityForResult(DepositReservationFragment.getPickImageChooserIntent(), 200);



                        }
                    });
                }
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


            if(appointmentType.equals("20"))
                ((Item)holder).bookingType.setText(context.getString(R.string.indivappointmentOffer));
            else if(appointmentType.equals("21"))
                ((Item)holder).bookingType.setText(context.getString(R.string.indivMultiappointment));
            else if(appointmentType.equals("22"))
                ((Item)holder).bookingType.setText(context.getString(R.string.groupappointment));
            else if(appointmentType.equals("23"))
                ((Item)holder).bookingType.setText(context.getString(R.string.indivappointmentOffer));
            else if(appointmentType.equals("24"))
                ((Item)holder).bookingType.setText(context.getString(R.string.indivMultiappointmentOffer));
            else if(appointmentType.equals("25"))
                ((Item)holder).bookingType.setText(context.getString(R.string.groupAppoinmentOffer));

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



                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
//        Log.e("bookingAutomatedcheck",bookingAutomatedBrowseData.size()+"");

        return appointmentsDataModels.size();
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

        TextView isBeingChecked,speciality,bookPlaceTxt,booktime,visitType,bookingType,book_id,bdb_expected_deposit,client_name,checkIn,reference_id, ser_price,totalPrice,export_invoice,date,accept,refuse,edit,refundText;
        ImageView book_Details,inner_res,logoImg,place,isChecked,booking_place,status,isEdited;
        ColorRatingBar rating;
        Space space,space2;

        TextView centerRating,docRating,isShifted,docName,patName;
        LinearLayout myroot,centerStar,docRatingLayout,actions;
        public Item(View itemView) {
            super(itemView);
            isEdited=itemView.findViewById(R.id.isEdited);
            speciality=itemView.findViewById(R.id.specialization);
            bookingType=itemView.findViewById(R.id.booktype);
            ser_price=itemView.findViewById(R.id.service_price);
            visitType=itemView.findViewById(R.id.visit_type);
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
            booktime=itemView.findViewById(R.id.booktime);
            booking_place=itemView.findViewById(R.id.booking_place);
            bookPlaceTxt=itemView.findViewById(R.id.booking_placeTxt);
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
            isBeingChecked=itemView.findViewById(R.id.isBeingChecked);

        }


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
    private MyRunnable OnClickCallMeBtn =new MyRunnable()
    {
        @Override
        public void run() {
           // APICall.CheckIn(context,book_id,getValue());

        }
    };
}
