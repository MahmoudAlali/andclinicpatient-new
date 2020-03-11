package com.dcoret.beautyclient.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.dcoret.beautyclient.Activities.NewBookingRequestsFragment;
//import com.dcoret.beautyclient.Adapters.BookingRequestsAdapter;
import com.dcoret.beautyclient.Adapters.BookingRequestsAdapter;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.DataModel.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.DataModel.BookingRequestDataModel;
import com.dcoret.beautyclient.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyBookingRequestsFragment extends Fragment {
    public static Fragment fragment;
    public static FragmentManager fm;

    public static FragmentTransaction fragmentTransaction;
    Spinner category;
    public static TextView oldRequests,newRequests;
    //           deposited_reservation;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData=new ArrayList<>();
    public static ArrayList<BookingRequestDataModel> bookingRequestsArraylist=new ArrayList<>();

    public static BookingRequestsAdapter bookingRequestsAdapter;

    public static View view;
    public static String serviceId="",employee_id="";
    ImageView filterbtn;
    public static String serviceName="",empname="",startdate="",start_r_date="",bookingType="";
    public static Button filter;
    public static Dialog dialog;
    public static CheckBox salonName,requestDate,requestType;
    public static boolean filtercheck=false;
    ArrayList<String> servicesList=new ArrayList<>();

    public static String service_date_txt="",tab="1";


    public static int syear,smonth,sday,eyear,emonth,eday;
    public static int sryear,srmonth,srday,eryear,ermonth,erday;

    public  static String tmp="0";

    public static String salonFilter="",dateFilter="",typeFilter="";
    public static String salonFilterTemp="",dateFilterTemp="",typeFilterTemp="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_booking_requests_fragment, container, false);

        salonFilter="";dateFilter="";typeFilter="";

        BeautyMainPage.FRAGMENT_NAME="MyBookingRequestsFragment";
        oldRequests=view.findViewById(R.id.oldRequests);
        newRequests=view.findViewById(R.id.newRequests);
        filterbtn=view.findViewById(R.id.filter);

        Toolbar toolbar;
        toolbar=view.findViewById(R.id.toolbarm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //BeautyMainPage.
            }
        });

        fragment = new NewBookingRequestsFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
        fragmentTransaction.commit();
        tabselected(newRequests,oldRequests,false);


        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String order_id="";
        if (bundle != null) {
            if(bundle.getString("order_id")!=null)
                order_id = bundle.getString("order_id");
        }

        if(!order_id.equals(""))
        {

            Bundle bundle2 = new Bundle();
            bundle2.putString("order_id", order_id);
            tabselected(oldRequests,newRequests,true);
            tab="2";
            fragment = new OldBookingRequestsFragment();
            fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commit();
        }

        String tab_id="";
        if (bundle != null) {
            if(bundle.getString("tab_id")!=null)

                tab_id = bundle.getString("tab_id");
        }

        if(!tab_id.equals(""))
        {
            if(tab_id.equals("2"))
            {
                tabselected(oldRequests,newRequests,true);
                tab="2";
                fragment = new OldBookingRequestsFragment();
            }
            else if(tab_id.equals("1"))
            {
                tabselected(newRequests,oldRequests,true);
                tab="1";
                fragment = new NewBookingRequestsFragment();
            }




            //  Bundle bundle2 = new Bundle();
            //  bundle2.putString("book_id", book_id);

            // fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
            fragmentTransaction.commit();
        }
        /*String execute_book_id="";
        if (bundle != null) {
            if(bundle.getString("execute_book_id")!=null)
                execute_book_id = bundle.getString("execute_book_id");
        }
        String type="";
        if (bundle != null) {
            if(bundle.getString("type")!=null)
                type = bundle.getString("type");
        }

        if(!execute_book_id.equals(""))
        {

            Intent intent=new Intent(BeautyMainPage.context, ExecuteBookActivity.class);
            intent.putExtra("execute_book_id", book_id);
            if(type.equals("4")||type.equals("5")||type.equals("6")||type.equals("7")||type.equals("8")||type.equals("9")||type.equals("11")||type.equals("12"))
                intent.putExtra("isOffer", true);
            else
                intent.putExtra("isOffer", false);

            BeautyMainPage.context.startActivity(intent);
        }*/


        //endregion



        //region FilterBtn




        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(BeautyMainPage.context);
                dialog.setContentView(R.layout.filter_dialog_layout_v3);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                salonName=dialog.findViewById(R.id.service_name);
                requestDate=dialog.findViewById(R.id.service_exec_date);
                requestType=dialog.findViewById(R.id.book_type);
                if(!salonFilterTemp.equals(""))
                {
                    salonName.setChecked(true);
                    salonName.setText(salonFilterTemp);
                }
                if(!dateFilterTemp.equals(""))
                {
                    requestDate.setChecked(true);
                    requestDate.setText(dateFilterTemp);
                }
                if(!typeFilterTemp.equals(""))
                {
                    requestType.setChecked(true);
                    requestType.setText(typeFilterTemp);
                }


                //--------------------- get services---------------------------
                APICall.getOrderedSuppliers(BeautyMainPage.context);


                Button clear=dialog.findViewById(R.id.clear);
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestDate.setChecked(false);
                        requestType.setChecked(false);
                        salonName.setChecked(false);
                        salonFilterTemp="";
                        salonFilter="";
                        dateFilterTemp="";
                        dateFilter="";
                        typeFilterTemp="";
                        typeFilter="";
                    }
                });


                salonName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked)
                        {
//                            servicesList.add("اختاري خدمة");
                            if (servicesList.size()==0)
                                for (int i=0;i<APICall.allSuppliers.size();i++){
                                    servicesList.add(APICall.allSuppliers.get(i).getName());
                                }

                            final Dialog dialog=new Dialog(BeautyMainPage.context);
                            dialog.setContentView(R.layout.select_offer_type_dialog_v3);

                            final Spinner supplier=dialog.findViewById(R.id.code);
                            final TextView msg=dialog.findViewById(R.id.message);
                            msg.setText(R.string.select_supplier);
                            ArrayAdapter adapter;
                            adapter =new ArrayAdapter(BeautyMainPage.context, R.layout.simple_spinner_item_layout_v1,servicesList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            supplier.setAdapter(adapter);
                            TextView ok=dialog.findViewById(R.id.confirm);
                            TextView cancel=dialog.findViewById(R.id.cancel);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                   // if (supplier.getSelectedItemPosition()!=0){

                                        salonName.setText(supplier.getSelectedItem().toString());
                                        salonFilterTemp=supplier.getSelectedItem().toString();
                                        bookingType=requestType.getText().toString();
                                        salonFilter = APICall.Filter("6",APICall.allSuppliers.get(supplier.getSelectedItemPosition()).getId()+"");
                                   // }
                                    dialog.dismiss();
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    serviceId="";
                                    salonName.setText(getResources().getString(R.string.requestSalonFilter));
                                    salonFilterTemp="";
                                    salonFilter="";
                                    serviceName="" ;
                                    salonName.setChecked(false);
                                    dialog.dismiss();

                                }
                            });

                            dialog.show();
                           /* final ArrayList<Integer> mUserItems=new ArrayList<>();
                            CharSequence[] listser=new CharSequence[servicesList.size()];
                            listser=servicesList.toArray(listser);
                            AlertDialog.Builder builder=new AlertDialog.Builder(BeautyMainPage.context);
                            builder.setTitle(getString(R.string.suppliers));
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
                                    String service="اسم المزودة:";
                                    for (int i=0;i<mUserItems.size();i++) {
                                        Log.e("ser" + i, mUserItems.get(i) + "");
                                        service=service+"-"+servicesList.get(mUserItems.get(i));
//                                        if(ProviderMainPage.FRAGMENT_NAME.equals("ServiceReportActivity")){
                                        if(i==0) {
                                            serviceId = APICall.allSuppliers.get(mUserItems.get(i)).getId();
                                        }else {
                                            serviceId =serviceId+","+APICall.allSuppliers.get(mUserItems.get(i)).getId();
                                        }
//                                        }

//                                        serviceInsideOfferList.add(servicesList.get(mUserItems.get(i)));

                                    }


                                    serviceName=service;
                                    salonName.setText(service);
                                    Log.e("SERVID",serviceId);
                                    filterNames.set(0,serviceName);
//                                    filterPostions.set(0,;
                                }
                            });

                            builder.show();*/

                        }
                        else {
                            serviceId="";
                            salonName.setText(getResources().getString(R.string.requestSalonFilter));
                            serviceName="" ;
                            salonName.setChecked(false);
                            salonFilterTemp="";
                            salonFilter="";
                        }
                    }
                });


                requestDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


                                    syear=sDate.getYear();
                                    smonth=sDate.getMonthValue();
                                    sday=sDate.getDayOfMonth();

                                    emonth=eDate.getMonthValue();
                                    eday=eDate.getDayOfMonth();
                                    eyear=eDate.getYear();

//                                    Log.e("dates",calendar.getSelectedDates()+"");
                                    Log.e("Start",sday+"-"+smonth);
                                    Log.e("Start",eday+"-"+emonth);
                                    startdate=sday+"-"+smonth+" to "+eday+"-"+emonth;
                                    String s =sday+"-"+smonth+"-"+syear;
                                    String e =eday+"-"+emonth+"-"+eyear;
                                    dateFilter = APICall.Filter("3","\""+s+"\"","\""+e+"\"");


//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                                    requestDate.setText(getResources().getString(R.string.requestDateFilter)+":"+startdate);
                                    dateFilterTemp=getResources().getString(R.string.requestDateFilter)+":"+startdate;
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    dateFilter = "";
                                    dateFilterTemp="";

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
                            requestDate.setText(getResources().getString(R.string.requestDateFilter));
                            startdate="";
                            dateFilter = "";
                            requestDate.setChecked(false);
                            dateFilterTemp="";
                        }
                    }
                });


                requestType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){

                            final Dialog dialog=new Dialog(BeautyMainPage.context);
                            dialog.setContentView(R.layout.select_offer_type_dialog_v3);

                            final Spinner offer_type=dialog.findViewById(R.id.code);
                            ArrayAdapter adapter=ArrayAdapter.createFromResource(BeautyMainPage.context
                                    ,R.array.request_type
                                    ,android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            offer_type.setAdapter(adapter);
                            TextView ok=dialog.findViewById(R.id.confirm);
                            TextView cancel=dialog.findViewById(R.id.cancel);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (offer_type.getSelectedItemPosition()==1){
                                        dialog.cancel();
                                        typeFilter=APICall.Filter("5","20");
                                    }else if (offer_type.getSelectedItemPosition()==2){
                                        dialog.cancel();
                                        typeFilter=APICall.Filter("5","22");
                                    }else if (offer_type.getSelectedItemPosition()==3){
                                        dialog.cancel();
                                        typeFilter=APICall.Filter("5","23");
                                    }else if (offer_type.getSelectedItemPosition()==4) {
                                        dialog.cancel();
                                        typeFilter=APICall.Filter("5","25");
                                    }
                                    if (offer_type.getSelectedItemPosition()!=0){

                                        requestType.setText(getResources().getString(R.string.type)+offer_type.getSelectedItem().toString());
                                        bookingType=requestType.getText().toString();
                                        typeFilterTemp=getResources().getString(R.string.type)+offer_type.getSelectedItem().toString();
                                    }
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                    typeFilterTemp="";
                                    typeFilter="";
                                }
                            });


                            dialog.show();


                        }else {
                            requestType.setText(R.string.book_type);
                            bookingType="";
                            typeFilter="";
                            typeFilterTemp="";

                        }

                    }

                });





                filter=dialog.findViewById(R.id.filter);
                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filtercheck=true;
                        dialog.cancel();
                        if (tab.equals("1")) {
                            fragment = new NewBookingRequestsFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
                            fragmentTransaction.commit();
                            tabselected(newRequests,oldRequests,true);
                        }else if (tab.equals("2")){
                            fragment = new OldBookingRequestsFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
                            fragmentTransaction.commit();
                            tabselected(oldRequests,newRequests,true);
                        }
                       // APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout,tmp);

                    }
                });

                dialog.show();

            }


        });

        //endregion

        oldRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(oldRequests,newRequests,true);
                tab="2";
                fragment = new OldBookingRequestsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        newRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(newRequests,oldRequests,true);
                tab="1";
                fragment = new NewBookingRequestsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });

     //   ImageView sortbtn=MyReservationFragment.view.findViewById(R.id.sort);

        /*sortbtn.setOnClickListener(new View.OnClickListener() {
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
                      //  bookingRequestsAdapter.notifyDataSetChanged();
                        if (id==R.id.one){
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout,tmp);

                        }else if (id==R.id.two){
                            APICall.sort=APICall.bookingSort("1","desc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout,tmp);
                        }else if (id==R.id.three){
                            APICall.sort=APICall.bookingSort("2","asc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout,tmp);
                        }else if (id==R.id.four){
                            APICall.sort=APICall.bookingSort("2","desc");
                            APICall.bookingAutomatedBrowse1("en","100",MyReservationFragment.serviceId,"1","",APICall.sort,BeautyMainPage.context,APICall.layout,tmp);
                        }
                        Log.e("Sort1",APICall.sort);
                        Log.e("filter1",APICall.filter);
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });


*/
        return view;
    }
    public static void tabselected(TextView t1, TextView t2, Boolean check){
        try {
            if (check){
                serviceId = "";
                employee_id = "";
//            service_name.setChecked(false);
//            emp_name.setChecked(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        t1.setTextSize(14);
        t1.setBackgroundResource(R.drawable.shadow_accent_c5);
        t2.setTextSize(12);
        t2.setBackgroundResource(android.R.color.transparent);


    }
   /* public static void updateDeposit(){
        fragment = new AcceptedReservationFragment();
        fm = ((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tabs_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }*/
}
