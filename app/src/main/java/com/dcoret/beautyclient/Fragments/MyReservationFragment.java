package com.dcoret.beautyclient.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.DataClass.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.R;


import java.util.ArrayList;

//import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MyReservationFragment extends Fragment {

    public static Fragment fragment;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;
    Spinner category;
   public static TextView incom_reservation,accept_reservation;
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
    public static CheckBox service_name,emp_name,service_date,service_reservation_date,book_type;
    public static boolean filtercheck=false;
    public static ArrayList<String> filterNames=new ArrayList<>();
    public static ArrayList<Integer> filterPostions=new ArrayList<>();
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

        fragment = new IncomReservationFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tabs_fragment, fragment);
        fragmentTransaction.commit();
        tabselected(incom_reservation,accept_reservation,false);




//        filterbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 dialog=new Dialog(BeautyMainPage.context);
//                dialog.setContentView(R.layout.filter_dialog_layout);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                  service_name=dialog.findViewById(R.id.service_name);
//                  emp_name=dialog.findViewById(R.id.emp_name);
//                  service_date=dialog.findViewById(R.id.service_date);
//                  service_reservation_date=dialog.findViewById(R.id.service_r_date);
//                  book_type=dialog.findViewById(R.id.book_type);
//
//                try {
//                    if (APICall.empNames.isEmpty()) {
//                        APICall.empNames.add("Select Employee");
//                        APICall.getEmpSalonS(BeautyMainPage.context);
//                    }
//                    if (APICall.servicesList.isEmpty()) {
//                        APICall.servicesList.add("Select Service");
////                        APICall.pd.dismiss();
////                        APICall.getService("2", ProviderMainPage.context);
//                    }
//
//                    Log.e("FNAMESSIZE", filterNames.size() + "");
//                    for (int k = 0; k < filterNames.size(); k++) {
//                        Log.e("FilterNames", filterNames.get(k));
//                        Log.e("FilterNames", k + "");
//                    }
//                    if (!filterNames.get(0).equals("")) {
//                        service_name.setChecked(true);
//                        service_name.setText(serviceName);
//                    }
//                    if (!empname.equals("")) {
//                        emp_name.setChecked(true);
//                        emp_name.setText(empname);
//                    }
//
//                    if (!startdate.equals("")) {
//                        service_date.setChecked(true);
//                        service_date.setText(filterNames.get(1));
//                    }
//                    if (!start_r_date.equals("")) {
//                        service_reservation_date.setChecked(true);
//                        service_reservation_date.setText(start_r_date);
//                    }
//                    if (!bookingType.equals("")) {
//                        book_type.setChecked(true);
//                        book_type.setText(bookingType);
//                    }
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
////                 }
//
//                  Button clear=dialog.findViewById(R.id.clear);
//                  clear.setOnClickListener(new View.OnClickListener() {
//                      @Override
//                      public void onClick(View v) {
//                          serviceId="";
//                          employee_id="";
//                          service_name.setChecked(false);
//                          emp_name.setChecked(false);
//                          for (int i=0;i<filterNames.size();i++){
//                          filterNames.set(i,"");
//                          filterPostions.set(i,-1);
//                          }
//
//                      }
//                  });
//
//
//                  service_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//                            final Dialog name=new Dialog(ProviderMainPage.context);
//                            name.setContentView(R.layout.emp_name_layout);
//                            name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            TextView typename=name.findViewById(R.id.type_name);
//                            final Spinner nameEdTXT=name.findViewById(R.id.name);
//                            Button ok=name.findViewById(R.id.ok);
//
//                            typename.setText(service_name.getText());
//                            ArrayAdapter adapter=new ArrayAdapter(ProviderMainPage.context,android.R.layout.simple_spinner_item, API.servicesList);
//                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            nameEdTXT.setAdapter(adapter);
//
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (nameEdTXT.getSelectedItemPosition()!=0){
//                                        name.dismiss();
//                                       serviceName = nameEdTXT.getSelectedItem().toString();
//                                       service_name.setText(serviceName);
//                                       serviceId=API.servicesArrayList.get(nameEdTXT.getSelectedItemPosition()-1).getBdb_ser_id();
//                                        Log.e("SERVID",serviceId);
//                                        filterNames.set(0,serviceName);
//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
//
//                                    }
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
//                            serviceId="";
//                            service_name.setText(getResources().getString(R.string.Service_Name));
//                            serviceName="";
//                            service_name.setChecked(false);
//                            filterNames.set(1,"");
//                            filterPostions.set(1,-1);
//                        }
//                    }
//                });
//                service_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//                            final Dialog name=new Dialog(ProviderMainPage.context);
//                            name.setContentView(R.layout.select_date);
//                            name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            final DatePicker datePicker=name.findViewById(R.id.date_picker);
////                            final Spinner nameEdTXT=name.findViewById(R.id.name);
//                            TextView ok=name.findViewById(R.id.ok);
//                            TextView cancel=name.findViewById(R.id.cancel);
//
//
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
////                                    if (.getSelectedItemPosition()!=0){
//                                        name.dismiss();
//                                        startdate = datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear();
//                                        service_date.setText(startdate);
////                                        serviceId=API.servicesArrayList.get();
////                                        Log.e("SERVID",serviceId);
//
//
//                                        filterNames.set(2,startdate);
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
//                            serviceId="";
//                            service_name.setText(getResources().getString(R.string.service_date));
//                            serviceName="";
//                            service_name.setChecked(false);
//                            filterNames.set(2,"");
//                            filterPostions.set(2,-1);
//                        }
//                    }
//                });
//
//
//
//
//                service_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
//                                    startdate = datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear();
//                                    service_date.setText(startdate);
////                                        serviceId=API.servicesArrayList.get();
//                                        Log.e("service_date",startdate+"");
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
//
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
//
//
//
//
//                emp_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//                            final Dialog name=new Dialog(ProviderMainPage.context);
//                            name.setContentView(R.layout.emp_name_layout);
//                            name.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            TextView typename=name.findViewById(R.id.type_name);
//                            final Spinner nameEdTXT=name.findViewById(R.id.name);
//                            Button ok=name.findViewById(R.id.ok);
//
//                            typename.setText(emp_name.getText());
////                            nameEdTXT.setText("");
//                            ArrayAdapter adapter=new ArrayAdapter(ProviderMainPage.context,android.R.layout.simple_spinner_item, API.empNames);
//                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            nameEdTXT.setAdapter(adapter);
//
//
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (nameEdTXT.getSelectedItemPosition()!=0){
//                                        name.dismiss();
//                                        empname = nameEdTXT.getSelectedItem().toString();
//                                        emp_name.setText(empname);
//                                        employee_id=API.employeeSalonList.get(nameEdTXT.getSelectedItemPosition()-1).getBdb_id();
//                                        Log.e("EMPID",employee_id);
//                                        filterNames.set(1,empname);
//                                        filterPostions.set(1,nameEdTXT.getSelectedItemPosition());
//
//                                    }
//                                }
//                            });
//
//                        name.show();
//                        }else {
//                            employee_id="";
//                            emp_name.setText(R.string.employee_name);
//                            empname="";
//                            emp_name.setChecked(false);
//                            filterNames.set(1,"");
//                            filterPostions.set(1,-1);
//
//                        }
//
//                    }
//
//                });
//
//
//
//
//                book_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked){
//
//
//                        }else {
//                            bookingType="";
//                            emp_name.setText(R.string.book_type);
//                            emp_name.setChecked(false);
//                            filterNames.set(4,"");
//                            filterPostions.set(4,-1);
//
//                        }
//
//                    }
//
//                });
//
//
//
//
//
//                 filter=dialog.findViewById(R.id.filter);
//                filter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        filtercheck=true;
//
//                        dialog.cancel();
//                        if (API.layout==R.layout.incom_reservation_layout) {
//                            fragment = new IncomReservationFragment();
//                            fm = getFragmentManager();
//                            fragmentTransaction = fm.beginTransaction();
//                            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
//                            fragmentTransaction.commit();
//                            tabselected(incom_reservation, accept_reservation, deposited_reservation,false);
//                        }else if (API.layout==R.layout.accept_reservation_layout_v2){
//                            fragment = new AcceptReservationFragment();
//                            fm = getFragmentManager();
//                            fragmentTransaction = fm.beginTransaction();
//                            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
//                            fragmentTransaction.commit();
//                            tabselected(accept_reservation,incom_reservation,  deposited_reservation,false);
//                        }else {
//                            fragment = new DepositReservationFragment();
//                            fm = getFragmentManager();
//                            fragmentTransaction = fm.beginTransaction();
//                            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
//                            fragmentTransaction.commit();
//                            tabselected(deposited_reservation,accept_reservation,incom_reservation,false);
//                        }
//                        API.bookingAutomatedBrowse("en","10",MyReservationFragment.serviceId,"1",API.filter,API.sort,ProviderMainPage.context,API.layout);
//
//                   }
//                });
//
//                dialog.show();
//
//            }
//
//
//        });

        incom_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new IncomReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commit();
                tabselected(incom_reservation,accept_reservation,true);

            }
        });

        accept_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(accept_reservation,incom_reservation,true);
                fragment = new AcceptReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
//        deposited_reservation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabselected(accept_reservation,incom_reservation,true);
//                fragment = new DepositReservationFragment();
//                fm = getFragmentManager();
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
//                fragmentTransaction.commit();
//            }
//        });

        return view;
    }
   public static void tabselected(TextView t1, TextView t2, Boolean check){
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
        t1.setTextColor(Color.MAGENTA);
        t2.setTextSize(12);
        t2.setTextColor(Color.BLACK);
//        t3.setTextSize(12);
//        t3.setTextColor(Color.BLACK);

    }
}
