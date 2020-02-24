package com.dcoret.beautyclient.Fragments.MyReservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;


import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.DataModel.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.Fragments.MyReservation.Tabs.AcceptedReservationFragment;
import com.dcoret.beautyclient.Fragments.MyReservation.Tabs.DepositReservationFragment;
import com.dcoret.beautyclient.Fragments.MyReservation.Tabs.ExecutedReservationFragment;
import com.dcoret.beautyclient.R;
import com.savvi.rangedatepicker.CalendarPickerView;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MyReservationFragment extends Fragment  {

    public static Fragment fragment;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    Spinner category;
   public static TextView incom_reservation,accept_reservation,deposit_reservation;
//           deposited_reservation;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData=new ArrayList<>();
    public static ReservationsAdapter reservationsAdapter;
    public static ReservationsAdapter2 reservationsAdapter2;
    public static View view;
    public static String serviceId="",employee_id="";
    ImageView filterbtn;
    public static String serviceName="",empname="",startdate="",start_r_date="",bookingType="";
    public static String serviceNamefilter="",empnamefilter="";
    public static Button filter;
    public static Dialog dialog;
    public static CheckBox service_name,service_exec_date,emp_name,service_date,service_reservation_date,book_type;
    public static boolean filtercheck=false;
    public static ArrayList<String> filterNames=new ArrayList<>();
    public static ArrayList<Integer> filterPostions=new ArrayList<>();
    public static String groupbooking="";
    ArrayList<String> servicesList=new ArrayList<>();
    public static boolean [] checkitems;

    public static String service_date_txt="",tab="1";


    public static int syear,smonth,sday,eyear,emonth,eday;
    public static int sryear,srmonth,srday,eryear,ermonth,erday;

    //    public static FloatingTextButton floatingTextButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.my_reseravtion_fragment, container, false);

         if (filterNames.size()==0) {
             //----------- 5 is filter num in dialog
             for (int i = 0; i <5;i++)
             {
                filterNames.add("");
                filterPostions.add(-1);
             }
         }

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        incom_reservation=view.findViewById(R.id.incom_reservation);
//        floatingTextButton=view.findViewById(R.id.action_button);
        accept_reservation=view.findViewById(R.id.accept_reservation);
        deposit_reservation=view.findViewById(R.id.deposit_reservation);
//        deposited_reservation=view.findViewById(R.id.deposited_reservation);
        filterbtn=view.findViewById(R.id.filter);

        Toolbar toolbar;
        toolbar=view.findViewById(R.id.toolbarm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

        fragment = new AcceptedReservationFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tabs_fragment, fragment);
        fragmentTransaction.commit();
        tabselected(incom_reservation,deposit_reservation,accept_reservation,false);






        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(BeautyMainPage.context);
                dialog.setContentView(R.layout.filter_dialog_layout_v2);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                service_name=dialog.findViewById(R.id.service_name);
                emp_name=dialog.findViewById(R.id.emp_name);
                service_exec_date=dialog.findViewById(R.id.service_exec_date);
                  service_reservation_date=dialog.findViewById(R.id.service_date);
                book_type=dialog.findViewById(R.id.book_type);


                //--------------------- get services---------------------------
                APICall.getServicesForFilter("1",BeautyMainPage.context);

                try {
                    if (APICall.empNames.isEmpty()) {
                        APICall.empNames.add("Select Employee");
                        APICall.getEmpSalonS(BeautyMainPage.context);
                    }
                    if (APICall.servicesList.isEmpty()) {
                        APICall.servicesList.add("Select Service");
                        APICall.pd.dismiss();
//                        APICall.getService("2", BeautyMainPage.context);
                    }

                    Log.e("FNAMESSIZE", filterNames.size() + "");
                    for (int k = 0; k < filterNames.size(); k++) {
                        Log.e("FilterNames", filterNames.get(k));
                        Log.e("FilterNames", k + "");
                    }
                    if (!serviceName.equals("")) {
                        service_name.setChecked(true);
                        service_name.setText(serviceName);
                    }else {
                        service_name.setChecked(false);
                        service_name.setText(getResources().getString(R.string.Service_Name));
                    }
                    if (!empname.equals("")) {
                        emp_name.setChecked(true);
                        emp_name.setText(empname);
                    }

                    if (!startdate.equals("")) {
                        service_exec_date.setChecked(true);
                        service_exec_date.setText(startdate);
                    }
                    if (!service_date_txt.equals("")) {
                        service_reservation_date.setChecked(true);
                        service_reservation_date.setText(service_date_txt);
                    }
                    if (!groupbooking.equals("")) {
                        book_type.setChecked(true);
                        book_type.setText(bookingType);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
//                 }

                Button clear=dialog.findViewById(R.id.clear);
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceId="";
                        employee_id="";
                        service_name.setChecked(false);
                        emp_name.setChecked(false);
                        for (int i=0;i<filterNames.size();i++){
                            filterNames.set(i,"");
                            filterPostions.set(i,-1);
                        }

                    }
                });


                service_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
//                            servicesList.add("اختاري خدمة");
                            if (servicesList.size()==0)
                            for (int i=0;i<APICall.serviceForFilter.size();i++){
                                servicesList.add(APICall.serviceForFilter.get(i).getBdb_name_ar());
                            }

                            final ArrayList<Integer> mUserItems=new ArrayList<>();
                            CharSequence[] listser=new CharSequence[servicesList.size()];
                            listser=servicesList.toArray(listser);
                            AlertDialog.Builder builder=new AlertDialog.Builder(BeautyMainPage.context);
                            builder.setTitle("Services");
                            builder.setMultiChoiceItems(listser, checkitems, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                                    if (isChecked){
                                        if(!mUserItems.contains(position)){
                                            mUserItems.add(position);
                                        }else {
                                            mUserItems.remove(position);
                                        }
                                    }
                                }
                            });

                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String service="اسم الخدمة:";
                                    for (int i=0;i<mUserItems.size();i++) {
                                        Log.e("ser" + i, mUserItems.get(i) + "");
                                        service=service+"-"+servicesList.get(mUserItems.get(i));
//                                        if(ProviderMainPage.FRAGMENT_NAME.equals("ServiceReportActivity")){
                                            if(i==0) {
                                                serviceId = APICall.serviceForFilter.get(mUserItems.get(i)).getBdb_ser_id();
                                            }else {
                                                serviceId =serviceId+","+APICall.serviceForFilter.get(mUserItems.get(i)).getBdb_ser_id();
                                            }
//                                        }

//                                        serviceInsideOfferList.add(servicesList.get(mUserItems.get(i)));

                                    }


                                    serviceName=service;
                                    service_name.setText(service);
                                    Log.e("SERVID",serviceId);
                                    filterNames.set(0,serviceName);
//                                    filterPostions.set(0,;
                                }
                            });

                            builder.show();

                        }else {
                            serviceId="";
                            service_name.setText(getResources().getString(R.string.Service_Name));
                            serviceName="" ;
                            service_name.setChecked(false);
                            filterNames.set(1,"");
                            filterPostions.set(1,-1);
                        }
                    }
                });
                service_reservation_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
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


                                    srmonth=sDate.getMonthValue();
                                    srday=sDate.getDayOfMonth();

                                    ermonth=eDate.getMonthValue();
                                    erday=eDate.getDayOfMonth();

//                                    Log.e("dates",calendar.getSelectedDates()+"");
                                    Log.e("Start",srday+"-"+srmonth);
                                    Log.e("Start",erday+"-"+ermonth);
                                    service_date_txt=srday+"-"+srmonth+" to "+erday+"-"+ermonth;

//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                                    service_reservation_date.setText(getResources().getString(R.string.service_reservation_date)+":"+service_date_txt);
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });


                            name.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
//                                    service_name.setText("اسم الخدمة");
//                                    service_name.setChecked(false);
//                                    serviceName="";
//                                    filterNames.set(0,"");
//                                    filterPostions.set(0,-1);
                                }
                            });
                            name.show();
                        }else {
//                            serviceId="";
                            service_date_txt="";
                            Log.e("startdate","000");
                            service_reservation_date.setText(getResources().getString(R.string.service_reservation_date));
//                            serviceName="";
//                            service_date_txt="";
                            service_reservation_date.setChecked(false);
                            filterNames.set(2,"");
                            filterPostions.set(2,-1);
                        }
                    }
                });

                service_exec_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
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


                                    smonth=sDate.getMonthValue();
                                    sday=sDate.getDayOfMonth();

                                    emonth=eDate.getMonthValue();
                                    eday=eDate.getDayOfMonth();

//                                    Log.e("dates",calendar.getSelectedDates()+"");
                                    Log.e("Start",sday+"-"+smonth);
                                    Log.e("Start",eday+"-"+emonth);
                                    startdate=sday+"-"+smonth+" to "+eday+"-"+emonth;

//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                                    service_exec_date.setText(getResources().getString(R.string.service_exec_date)+":"+startdate);
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });


                            name.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
//                                    service_name.setText("اسم الخدمة");
//                                    service_name.setChecked(false);
//                                    serviceName="";
//                                    filterNames.set(0,"");
//                                    filterPostions.set(0,-1);
                                }
                            });
                            name.show();

                        }else {
//                            serviceId="";
                            service_exec_date.setText(getResources().getString(R.string.service_exec_date));
//                            serviceName="";
                            startdate="";
                            service_exec_date.setChecked(false);
//                            filterNames.set(2,"");
                            filterPostions.set(2,-1);
                        }
                    }
                });
//                service_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//                            final Dialog name=new Dialog(BeautyMainPage.context);
//                            name.setContentView(R.layout.select_date);
//                            name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            final DatePicker datePicker=name.findViewById(R.id.date_picker);
////                            final Spinner nameEdTXT=name.findViewById(R.id.name);
//                            TextView ok=name.findViewById(R.id.confirm);
//                            TextView cancel=name.findViewById(R.id.cancel);
//
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
////                                    if (.getSelectedItemPosition()!=0){
//                                    name.dismiss();
//                                    int m=datePicker.getMonth()+1;
//                                    startdate = datePicker.getYear()+"-"+m+"-"+datePicker.getDayOfMonth();
//                                    service_date.setText(startdate);
////                                        serviceId=API.servicesArrayList.get();
//                                    Log.e("service_date",startdate+"");
//
//
//                                    filterNames.set(2,startdate);
////                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
//
//
//                                }
//                            });
//                            cancel.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//
//                            name.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
////                                    service_name.setText("اسم الخدمة");
////                                    service_name.setChecked(false);
////                                    serviceName="";
////                                    filterNames.set(0,"");
////                                    filterPostions.set(0,-1);
//                                }
//                            });
//                            name.show();
//                        }else {
//                            service_date.setText(getResources().getString(R.string.service_date));
//                            startdate="";
//                            service_name.setChecked(false);
//                            filterNames.set(2,"");
//                            filterPostions.set(2,-1);
//                        }
//                    }
//                });
//


//                service_reservation_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//                            final Dialog name=new Dialog(ProviderMainPage.context);
//                            name.setContentView(R.layout.select_date);
//                            name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            final DatePicker datePicker=name.findViewById(R.id.date_picker);
////                            final Spinner nameEdTXT=name.findViewById(R.id.name);
//                            TextView ok=name.findViewById(R.id.confirm);
//                            TextView cancel=name.findViewById(R.id.cancel);
//
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
////                                    if (.getSelectedItemPosition()!=0){
//                                    name.dismiss();
//                                    start_r_date = datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear();
//                                    service_reservation_date.setText(start_r_date);
////                                        serviceId=API.servicesArrayList.get();
//                                        Log.e("start_r_date",start_r_date);
//
//
//                                    filterNames.set(3,start_r_date);
////                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
//
//
//                                }
//                            });
//                            cancel.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//                                }
//                            });
//
//                            name.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
////                                    service_name.setText("اسم الخدمة");
////                                    service_name.setChecked(false);
////                                    serviceName="";
////                                    filterNames.set(0,"");
////                                    filterPostions.set(0,-1);
//                                }
//                            });
//                            name.show();
//                        }else {
//                            service_reservation_date.setText(getResources().getString(R.string.service_reservation_date));
//                            start_r_date="";
//                            service_name.setChecked(false);
//                            filterNames.set(3,"");
//                            filterPostions.set(3,-1);
//                        }
//                    }
//                });
                emp_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            final Dialog name=new Dialog(BeautyMainPage.context);
                            name.setContentView(R.layout.emp_name_layout);
                            name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            TextView typename=name.findViewById(R.id.type_name);
                            final Spinner nameEdTXT=name.findViewById(R.id.name);
                            Button ok=name.findViewById(R.id.ok);

                            typename.setText(emp_name.getText());
//                            nameEdTXT.setText("");
                            ArrayAdapter adapter=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_item, APICall.empNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            nameEdTXT.setAdapter(adapter);


                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (nameEdTXT.getSelectedItemPosition()!=0){
                                        name.dismiss();
                                        empname = nameEdTXT.getSelectedItem().toString();
                                        emp_name.setText(empname);
                                        employee_id=APICall.employeeSalonList.get(nameEdTXT.getSelectedItemPosition()-1).getBdb_id();
                                        Log.e("EMPID",employee_id);
                                        filterNames.set(1,empname);
                                        filterPostions.set(1,nameEdTXT.getSelectedItemPosition());

                                    }
                                }
                            });

                            name.show();
                        }else {
                            employee_id="";
                            emp_name.setText(R.string.employee_name);
                            empname="";
                            emp_name.setChecked(false);
                            filterNames.set(1,"");
                            filterPostions.set(1,-1);

                        }

                    }

                });




                book_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                            final Dialog dialog=new Dialog(BeautyMainPage.context);
                            dialog.setContentView(R.layout.select_offer_type_dialog_v2);

                            final Spinner offer_type=dialog.findViewById(R.id.code);
                            ArrayAdapter adapter=ArrayAdapter.createFromResource(BeautyMainPage.context
                                    ,R.array.offer_type
                                    ,android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            offer_type.setAdapter(adapter);
                            TextView ok=dialog.findViewById(R.id.confirm);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (offer_type.getSelectedItemPosition()==1){
                                        dialog.cancel();
                                        groupbooking="0";
                                    }else if (offer_type.getSelectedItemPosition()==2){
                                        dialog.cancel();
                                        groupbooking="2";
                                    }else if (offer_type.getSelectedItemPosition()==3){
                                        dialog.cancel();
                                        groupbooking="1";
                                    }else if (offer_type.getSelectedItemPosition()==4){
                                        dialog.cancel();
                                        groupbooking="3";
                                    }else if (offer_type.getSelectedItemPosition()==5){
                                        dialog.cancel();
                                        groupbooking="4";
                                    }else if (offer_type.getSelectedItemPosition()==6){
                                        dialog.cancel();
                                        groupbooking="5";
                                    }else if (offer_type.getSelectedItemPosition()==7){
                                        dialog.cancel();
                                        groupbooking="6";
                                    }
                                    if (offer_type.getSelectedItemPosition()!=0){

                                        book_type.setText("نوع العرض: "+offer_type.getSelectedItem().toString());
                                        bookingType=book_type.getText().toString();
                                    }
                                }
                            });

                            dialog.show();


                        }else {
                            book_type.setText(R.string.book_type);
                            bookingType="";
                            groupbooking="";
//                            emp_name.setText(R.string.book_type);
//                            emp_name.setChecked(false);
                            filterNames.set(4,"");
                            filterPostions.set(4,-1);

                        }

                    }

                });





                filter=dialog.findViewById(R.id.filter);
                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filtercheck=true;
                        dialog.cancel();
                        if (APICall.layout==R.layout.incom_reservation_layout) {
                            fragment = new AcceptedReservationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                            fragmentTransaction.commit();
                            tabselected(incom_reservation,deposit_reservation, accept_reservation,false);
                        }else if (APICall.layout==R.layout.accept_reservation_layout_v2){
                            fragment = new ExecutedReservationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                            fragmentTransaction.commit();
                            tabselected(accept_reservation,deposit_reservation,incom_reservation,false);
                        }
                        APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout);

                    }
                });

                dialog.show();

            }


        });

        incom_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tab="1";
                fragment = new AcceptedReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commit();
                tabselected(incom_reservation,accept_reservation,deposit_reservation,true);

            }
        });

        accept_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(accept_reservation,incom_reservation,deposit_reservation,true);
                tab="2";
                fragment = new ExecutedReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        deposit_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(deposit_reservation,accept_reservation,incom_reservation,true);
                fragment = new DepositReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        ImageView sortbtn=MyReservationFragment.view.findViewById(R.id.sort);

        sortbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu_sort, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        APICall.reservationModels.clear();
                        reservationsAdapter2.notifyDataSetChanged();
                        if (id==R.id.one){
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout);

                        }else if (id==R.id.two){
                            APICall.sort=APICall.bookingSort("1","desc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.three){
                            APICall.sort=APICall.bookingSort("2","asc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout);
                        }else if (id==R.id.four){
                            APICall.sort=APICall.bookingSort("2","desc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout);
                        }
                        Log.e("Sort1",APICall.sort);
                        Log.e("filter1",APICall.filter);
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });



        return view;
    }
   public static void tabselected(TextView t1, TextView t2,TextView t3, Boolean check){
        try {
            if (check){
            serviceId = "";
            employee_id = "";
//            service_name.setChecked(false);
//            emp_name.setChecked(false);
            for (int i = 0; i < filterNames.size(); i++) {
                filterNames.set(i, "");
                filterPostions.set(i, -1);
            }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        t1.setTextSize(14);
        t1.setBackgroundResource(R.drawable.shadow_accent_c5);
        t2.setTextSize(12);
        t2.setBackgroundResource(android.R.color.transparent);
        t3.setTextSize(12);
        t3.setBackgroundResource(android.R.color.transparent);

    }
    public static void updateDeposit(){
        fragment = new AcceptedReservationFragment();
        fm = ((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tabs_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
