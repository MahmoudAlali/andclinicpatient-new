package com.ptm.clinicpa.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.UiAutomation;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.NewBookingRequestsFragment;
import com.ptm.clinicpa.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyBookingRequestsFilters extends Fragment {
    public static View view;
    public static TextView salonName,requestDate,requestType,creationDate;
    ArrayList<String> servicesList=new ArrayList<>();
    public static Button filter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.my_booking_requests_fragment_filter, container, false);
        salonName=view.findViewById(R.id.providerName);
        requestDate=view.findViewById(R.id.order_date);
        requestType=view.findViewById(R.id.book_type);
        creationDate=view.findViewById(R.id.creation_date);
        BeautyMainPage.FRAGMENT_NAME="MyBookingRequestsFilters";

        /*if(!MyBookingRequestsFragment.salonFilterTemp.equals(""))
        {
            salonName.setText(MyBookingRequestsFragment.salonFilterTemp);
        }
        if(!MyBookingRequestsFragment.dateFilterTemp.equals(""))
        {
            requestDate.setText(MyBookingRequestsFragment.dateFilterTemp);
        }
        if(!MyBookingRequestsFragment.typeFilterTemp.equals(""))
        {
            requestType.setText(MyBookingRequestsFragment.typeFilterTemp);
        }
        if(!MyBookingRequestsFragment.creationDateFilterTemp.equals(""))
        {
            requestType.setText(MyBookingRequestsFragment.creationDateFilterTemp);
        }*/
        MyBookingRequestsFragment.salonFilterTemp="";
        MyBookingRequestsFragment.salonFilter="";
        MyBookingRequestsFragment.dateFilterTemp="";
        MyBookingRequestsFragment.dateFilter="";
        MyBookingRequestsFragment.typeFilterTemp="";
        MyBookingRequestsFragment.typeFilter="";
        MyBookingRequestsFragment.creationDateFilter="";
        MyBookingRequestsFragment.creationDateFilterTemp="";


        //--------------------- get services---------------------------
        APICall.getOrderedSuppliers(BeautyMainPage.context);


       /* Button clear=view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBookingRequestsFragment.salonFilterTemp="";
                MyBookingRequestsFragment.salonFilter="";
                MyBookingRequestsFragment.dateFilterTemp="";
                MyBookingRequestsFragment.dateFilter="";
                MyBookingRequestsFragment.typeFilterTemp="";
                MyBookingRequestsFragment.typeFilter="";
                MyBookingRequestsFragment.creationDateFilter="";
                MyBookingRequestsFragment.creationDateFilterTemp="";
            }
        });*/


       salonName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (servicesList.size()==0)
               {
                   servicesList.add(BeautyMainPage.context.getString(R.string.healthCenteres));
                   for (int i=0;i<APICall.allSuppliers.size();i++){
                       Log.e("adding","supp"+i);

                       if(BeautyMainPage.context.getString(R.string.locale).equals("en"))
                           servicesList.add(APICall.allSuppliers.get(i).getName());
                       else
                           servicesList.add(APICall.allSuppliers.get(i).getName_ar());

                   }
               }


               final Dialog dialog=new Dialog(BeautyMainPage.context);
               dialog.setContentView(R.layout.select_offer_type_dialog_v3);

               final Spinner supplier=dialog.findViewById(R.id.code);
               final TextView msg=dialog.findViewById(R.id.message);
               msg.setText(R.string.select_supplier);
               HintArrayAdapter adapter;
               adapter =new HintArrayAdapter(BeautyMainPage.context, 0);
               adapter.addAll(servicesList);
               adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

               supplier.setAdapter(adapter);
               TextView ok=dialog.findViewById(R.id.confirm);
               TextView cancel=dialog.findViewById(R.id.cancel);

               ok.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       // if (supplier.getSelectedItemPosition()!=0){

                       salonName.setText(supplier.getSelectedItem().toString());
                       MyBookingRequestsFragment.salonFilterTemp=supplier.getSelectedItem().toString();
                       MyBookingRequestsFragment.bookingType=requestType.getText().toString();
                       MyBookingRequestsFragment.salonFilter = APICall.Filter(Filters.CLINIC_ID,APICall.allSuppliers.get(supplier.getSelectedItemPosition()-1).getId()+"");
                       // }
                       dialog.dismiss();
                   }
               });
               cancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       MyBookingRequestsFragment.serviceId="";
                       salonName.setText(getResources().getString(R.string.requestSalonFilter));
                       MyBookingRequestsFragment.salonFilterTemp="";
                       MyBookingRequestsFragment.salonFilter="";
                       MyBookingRequestsFragment.serviceName="" ;
                       dialog.dismiss();

                   }
               });

               dialog.show();

           }
       });


       creationDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Dialog name=new Dialog(BeautyMainPage.context);
               name.setContentView(R.layout.select_date_range);
               name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            final DatePicker datePicker=name.findViewById(R.id.date_picker);
               final CalendarPickerView calendar=name.findViewById(R.id.calendar);
//                            final Spinner nameEdTXT=name.findViewById(R.id.name);
               TextView ok=name.findViewById(R.id.confirm);
               TextView cancel=name.findViewById(R.id.cancel);
               Calendar pastYear = Calendar.getInstance();
               pastYear.add(Calendar.MONTH, -2);
               Calendar nextYear = Calendar.getInstance();
               nextYear.add(Calendar.DATE,1);
               calendar.init(pastYear.getTime(), nextYear.getTime()) //
                       .inMode(CalendarPickerView.SelectionMode.RANGE)
                       .withSelectedDate(new Date());
               calendar.setTypeface(Typeface.SANS_SERIF);


               ok.setOnClickListener(new View.OnClickListener() {
                   @RequiresApi(api = Build.VERSION_CODES.O)
                   @Override
                   public void onClick(View v) {
//                                    if (.getSelectedItemPosition()!=0){
                       name.dismiss();
//                                    int month=datePicker.getMonth()+1;

//                                    service_date_txt = datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth();
//                                        serviceId=API.servicesArrayList.get();
//                                        Log.e("SERVID",serviceId);

//                                    filterNames.set(2,startdate);
                       Date date1=null,date2=null;
                       SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                       try {

                           date1=sdf.parse(calendar.getSelectedDates().get(0).toString());
                           date2=sdf.parse(calendar.getSelectedDates().get(calendar.getSelectedDates().size()-1).toString());
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                       LocalDate sDate=null,eDate=null;
                       try {
                           sDate=date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                           eDate=date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                       }catch (Exception e){
                           e.printStackTrace();
                       }


                       MyBookingRequestsFragment.syearCr=sDate.getYear();
                       MyBookingRequestsFragment.smonthCr=sDate.getMonthValue();
                       MyBookingRequestsFragment.sdayCr=sDate.getDayOfMonth();

                       MyBookingRequestsFragment.emonthCr=eDate.getMonthValue();
                       MyBookingRequestsFragment.edayCr=eDate.getDayOfMonth();
                       MyBookingRequestsFragment.eyearCr=eDate.getYear();

//                                    Log.e("dates",calendar.getSelectedDates()+"");

                       MyBookingRequestsFragment.startdateCr=MyBookingRequestsFragment.sdayCr+"-"+MyBookingRequestsFragment.smonthCr+" to "+MyBookingRequestsFragment.edayCr+"-"+MyBookingRequestsFragment.emonthCr;
                       String s =MyBookingRequestsFragment.syearCr+"-"+MyBookingRequestsFragment.smonthCr+"-"+MyBookingRequestsFragment.sdayCr;
                       String e =MyBookingRequestsFragment.eyearCr+"-"+MyBookingRequestsFragment.emonthCr+"-"+MyBookingRequestsFragment.edayCr;
                       MyBookingRequestsFragment.creationDateFilter = APICall.Filter("3","\""+s+"\"","\""+e+"\"");


//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                       creationDate.setText(getResources().getString(R.string.creationDateFilter)+":"+ MyBookingRequestsFragment.startdateCr);
                       MyBookingRequestsFragment.creationDateFilterTemp=getResources().getString(R.string.creationDateFilter)+":"+ MyBookingRequestsFragment.startdateCr;
                   }
               });
               cancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       name.dismiss();
                       MyBookingRequestsFragment.creationDateFilter = "";
                       MyBookingRequestsFragment.creationDateFilterTemp="";

                       creationDate.setText(getResources().getString(R.string.creationDateFilter));
                   }
               });


               name.setOnCancelListener(new DialogInterface.OnCancelListener() {
                   @Override
                   public void onCancel(DialogInterface dialog) {
                       name.dismiss();
                       MyBookingRequestsFragment.creationDateFilter = "";
                       MyBookingRequestsFragment.creationDateFilterTemp="";
                       creationDate.setText(getResources().getString(R.string.creationDateFilter));

                   }
               });
               name.show();
           }
       });
        requestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog name=new Dialog(BeautyMainPage.context);
                name.setContentView(R.layout.select_date_range);
                name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            final DatePicker datePicker=name.findViewById(R.id.date_picker);
                final CalendarPickerView calendar=name.findViewById(R.id.calendar);
//                            final Spinner nameEdTXT=name.findViewById(R.id.name);
                TextView ok=name.findViewById(R.id.confirm);
                TextView cancel=name.findViewById(R.id.cancel);
                Calendar pastYear = Calendar.getInstance();
                pastYear.add(Calendar.MONTH, -2);
                Calendar nextYear = Calendar.getInstance();
                nextYear.add(Calendar.DATE,1);
                calendar.init(pastYear.getTime(), nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE)
                        .withSelectedDate(new Date());
                calendar.setTypeface(Typeface.SANS_SERIF);


                ok.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
//                                    if (.getSelectedItemPosition()!=0){
                        name.dismiss();
                        Date date1=null,date2=null;
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        try {

                            date1=sdf.parse(calendar.getSelectedDates().get(0).toString());
                            date2=sdf.parse(calendar.getSelectedDates().get(calendar.getSelectedDates().size()-1).toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        LocalDate sDate=null,eDate=null;
                        try {
                            sDate=date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            eDate=date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        MyBookingRequestsFragment.syear=sDate.getYear();
                        MyBookingRequestsFragment.smonth=sDate.getMonthValue();
                        MyBookingRequestsFragment.sday=sDate.getDayOfMonth();

                        MyBookingRequestsFragment.emonth=eDate.getMonthValue();
                        MyBookingRequestsFragment.eday=eDate.getDayOfMonth();
                        MyBookingRequestsFragment.eyear=eDate.getYear();

//                                    Log.e("dates",calendar.getSelectedDates()+"");

                        MyBookingRequestsFragment.startdate=MyBookingRequestsFragment.sday+"-"+MyBookingRequestsFragment.smonth+" to "+MyBookingRequestsFragment.eday+"-"+MyBookingRequestsFragment.emonth;
                        String s =MyBookingRequestsFragment.syear+"-"+MyBookingRequestsFragment.smonth+"-"+MyBookingRequestsFragment.sday;
                        String e =MyBookingRequestsFragment.eyear+"-"+MyBookingRequestsFragment.emonth+"-"+MyBookingRequestsFragment.eday;
                        MyBookingRequestsFragment.dateFilter = APICall.Filter("2","\""+s+"\"","\""+e+"\"");


//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                        requestDate.setText(getResources().getString(R.string.requestDateFilter)+":"+ MyBookingRequestsFragment.startdate);
                        MyBookingRequestsFragment.dateFilterTemp=getResources().getString(R.string.requestDateFilter)+":"+ MyBookingRequestsFragment.startdate;
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.dismiss();
                        MyBookingRequestsFragment.dateFilter = "";
                        MyBookingRequestsFragment.dateFilterTemp="";
                        requestDate.setText(getResources().getString(R.string.exec_date));


                    }
                });


                name.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        name.dismiss();
                        MyBookingRequestsFragment.dateFilter = "";
                        MyBookingRequestsFragment.dateFilterTemp="";
                        requestDate.setText(getResources().getString(R.string.exec_date));

                    }
                });
                name.show();
            }
        });
       requestType.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Dialog dialog=new Dialog(BeautyMainPage.context);
               dialog.setContentView(R.layout.select_offer_type_dialog_v3);

               final Spinner offer_type=dialog.findViewById(R.id.code);
               HintArrayAdapter adapter=new HintArrayAdapter(BeautyMainPage.context,0);
               adapter .addAll(Arrays.asList(getResources().getStringArray(R.array.request_type)));
               adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

               offer_type.setAdapter(adapter);
               TextView ok=dialog.findViewById(R.id.confirm);
               TextView cancel=dialog.findViewById(R.id.cancel);

               ok.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (offer_type.getSelectedItemPosition()==1){
                           dialog.cancel();
                           MyBookingRequestsFragment.typeFilter=APICall.Filter("5","20");
                       }else if (offer_type.getSelectedItemPosition()==2){
                           dialog.cancel();
                           MyBookingRequestsFragment.typeFilter=APICall.Filter("5","22");
                       }else if (offer_type.getSelectedItemPosition()==3){
                           dialog.cancel();
                           MyBookingRequestsFragment.typeFilter=APICall.Filter("5","23");
                       }else if (offer_type.getSelectedItemPosition()==4) {
                           dialog.cancel();
                           MyBookingRequestsFragment.typeFilter=APICall.Filter("5","25");
                       }
                       if (offer_type.getSelectedItemPosition()!=0){

                           requestType.setText(getResources().getString(R.string.type)+offer_type.getSelectedItem().toString());
                           MyBookingRequestsFragment.bookingType=requestType.getText().toString();
                           MyBookingRequestsFragment.typeFilterTemp=getResources().getString(R.string.type)+offer_type.getSelectedItem().toString();
                       }
                   }
               });
               cancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.cancel();
                       MyBookingRequestsFragment.typeFilterTemp="";
                       MyBookingRequestsFragment.typeFilter="";
                       requestType.setText(getResources().getString(R.string.requestType));

                   }
               });

               dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                   @Override
                   public void onCancel(DialogInterface dialog) {
                       dialog.cancel();
                       MyBookingRequestsFragment.typeFilterTemp="";
                       MyBookingRequestsFragment.typeFilter="";
                       requestType.setText(getResources().getString(R.string.requestType));
                   }
               });

               dialog.show();


           }
       });


        filter=view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyBookingRequestsFragment.isNew=false;
                Fragment fragment = new MyBookingRequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
