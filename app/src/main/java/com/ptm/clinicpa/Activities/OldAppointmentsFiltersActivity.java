package com.ptm.clinicpa.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.DataModel.ClientServiceDataModel;
import com.ptm.clinicpa.DataModel.ServiceFilter;
import com.ptm.clinicpa.Fragments.ServiceFragment;
import com.ptm.clinicpa.R;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OldAppointmentsFiltersActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    static TextView distance,mylocationbtn,specialityType, clinicName, doctorName,serviceName,excecutionDate,creationDate,appointmentType;
    public static Spinner typeSpinner,genderSpinner,ageSpinner;
    ArrayList<String> specialitiesList=new ArrayList<>();
    private static ArrayList<String> servicesList=new ArrayList<>();
    public static ArrayList<String> supplierServicesNames=new ArrayList();
    public static ArrayList<ClientServiceDataModel> supplierServices=new ArrayList();
    public static HintArrayAdapter adapter;

    public static String filterSpeciality="";
    public static String filterDistance="";
    public static String filterSupplierId="";
    public static String filterExecDate="";
    public static String filterCreateDate="";
    public static String filterServices="";
    public static String filterDoctorName="";
    public static String filterAppointmentType="";
    public static String filterMyLocationLat="",filterMyLocationLng="";
    public static String tempName="",tempFilter="";
    Double lat,lng;
    public static Context context;
    public static String docId;
    public static int syear,smonth,sday,eyear,emonth,eday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_appointments_filters);
        context=this;
         filterSpeciality="";
         filterDistance="";
         filterSupplierId="";
         filterExecDate="";
         filterCreateDate="";
         filterServices="";
         filterDoctorName="";
         filterAppointmentType="";
         filterMyLocationLat="";
         filterMyLocationLng="";
         tempName="";
         tempFilter="";
        filterDistance="{\"num\":2,\"value1\":0,\"value2\":10000}";

        APICall.getAllSpecialities(context);


        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //region request Type
        appointmentType=findViewById(R.id.requestType);
        appointmentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.select_offer_type_dialog_v3);

                final Spinner offer_type=dialog.findViewById(R.id.code);
                HintArrayAdapter adapter=new HintArrayAdapter(context,0);
                adapter .addAll(Arrays.asList(getResources().getStringArray(R.array.appointment_type)));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                offer_type.setAdapter(adapter);
                TextView ok=dialog.findViewById(R.id.confirm);
                TextView cancel=dialog.findViewById(R.id.cancel);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("SELECTED",offer_type.getSelectedItemPosition()+" ddg");
                        if (offer_type.getSelectedItemPosition()==1){
                            dialog.cancel();
                            filterAppointmentType=","+APICall.Filter("5","20");
                        }else if (offer_type.getSelectedItemPosition()==2){
                            dialog.cancel();
                            filterAppointmentType=","+APICall.Filter("5","24");
                        }else if (offer_type.getSelectedItemPosition()==3){
                            dialog.cancel();
                            filterAppointmentType=","+APICall.Filter("5","23");
                        }
                        if (offer_type.getSelectedItemPosition()!=0){

                            appointmentType.setText(getResources().getString(R.string.type)+offer_type.getSelectedItem().toString());
                           // MyBookingRequestsFragment.bookingType=requestType.getText().toString();
                           // MyBookingRequestsFragment.typeFilterTemp=getResources().getString(R.string.type)+offer_type.getSelectedItem().toString();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        tempFilter="";filterAppointmentType="";
                        appointmentType.setText(R.string.appointment_type);
                        Log.e("CNCL",offer_type.getSelectedItemPosition()+" ddg");

                        //  MyBookingRequestsFragment.typeFilterTemp="";
                      //  MyBookingRequestsFragment.typeFilter="";
                       // requestType.setText(getResources().getString(R.string.requestType));

                    }
                });

                /*dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.cancel();
                        tempFilter="";
                        filterAppointmentType="";
                        Log.e("ONCNCLBTN",offer_type.getSelectedItemPosition()+" ddg");

                        appointmentType.setText(R.string.appointment_type);
                    }
                });*/

                dialog.show();

            }
        });
        //endregion

        //region Speciality

        specialityType=findViewById(R.id.offerPrice);
        specialityType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-------------- range price filter--------------------
                if (specialitiesList.size()==0)
                    for (int i=0;i<APICall.allSpecialities.size();i++){
                        if(getResources().getString(R.string.locale).equals("en"))
                            specialitiesList.add(APICall.allSpecialities.get(i).getBdb_name());
                        else
                            specialitiesList.add(APICall.allSpecialities.get(i).getBdb_name_ar());

                    }

                final Dialog namesalonDialog = new Dialog(context);
                namesalonDialog.setContentView(R.layout.provider_name_layout);
                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //final Spinner name=namesalonDialog.findViewById(R.id.name);
                final EditText name=namesalonDialog.findViewById(R.id.name);
                final TextView title=namesalonDialog.findViewById(R.id.title);
                title.setText(R.string.speciality);
                final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
                HintArrayAdapter adapter=new HintArrayAdapter(context,0);
                adapter.addAll(specialitiesList);
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                add_service.setTitle(getResources().getString(R.string.specialities));
                add_service.setAdapter(adapter);
                // set listener
                add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  salonName="\"supplier_name\":" +"\""+ APICall.allSpecialities.get(position).getBdb_name()+"\",";
                        //  salonId="\"SupplierId\":" +"\""+ APICall.allSpecialities.get(position).getBdb_ser_id()+"\",";

                        tempFilter= Filters.getString(Filters.SPECIALITY_ID,APICall.allSpecialities.get(position).getBdb_ser_id());


                        if(getResources().getString(R.string.locale).equals("en"))
                            tempName=APICall.allSpecialities.get(position).getBdb_name();
                        else
                            tempName=APICall.allSpecialities.get(position).getBdb_name_ar();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        tempFilter="";
                        tempName="";
                    }
                });

                Button search = namesalonDialog.findViewById(R.id.search);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if (!name.getSelectedItem().toString().isEmpty()){
                        if (!name.getText().equals("")){
                            namesalonDialog.dismiss();
                            specialityType.setText( tempName);
                           /* filterSupplierName=salonName;
                            filterSupplierId=salonId;*/
                            filterSpeciality=","+tempFilter;
                            tempFilter="";
                            tempName="";
                            clinicName.setText(getResources().getText(R.string.providerName));
                            filterSupplierId="";

                        }else {
                            namesalonDialog.cancel();
                            specialityType.setText(getResources().getText(R.string.speciality));
                            filterSpeciality="";
                            tempFilter="";
                            tempName="";
                        }




                    }
                });
                namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // nameSalonOrProvider.setChecked(false);
                        specialityType.setText(getResources().getText(R.string.speciality));
                        filterSpeciality="";
                        tempFilter="";
                        tempName="";
                        APICall.filterSortAlgorithm("3", "", "");
                        // ServiceFragment.serviceFilters.set(7, new ServiceFilter(false, providerName.getText().toString()));
                        // idsup="";
                    }
                });
                namesalonDialog.show();

            }
        });

        //endregion


        // region get Current Location
        {

            LocationManager locationManager = (LocationManager)
                    ( context).getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            boolean enabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(!enabled)
            {
               // showLocationServiceMsg(BeautyMainPage.context);
            }
            else
            {
                Criteria crit = new Criteria();
                crit.setAccuracy(Criteria.ACCURACY_FINE);
                //locationManager.requestLocationUpdates( locationListener);
                LocationManager locationManager2 = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
                locationManager2.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //Log.e("LATLANG",lat+":"+lng +"FIRST");
                        lat=location.getLatitude();
                        lng=location.getLongitude();
                        Log.e("LATLANG",lat+":"+lng);
                        //APICall.setlocation(lat,lng);
                        filterMyLocationLat=",{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                        filterMyLocationLng=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                    @Override
                    public void onProviderEnabled(String provider) {
                    }
                    @Override
                    public void onProviderDisabled(String provider) {
                    }

                });
            }

        }

        //endregion


        //region Clinic Name

        clinicName = findViewById(R.id.providerName);
        clinicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    APICall.getClinicsForFilter(context,filterMyLocationLat,filterMyLocationLng,filterDistance,filterSpeciality);

            }
        });

        //endregion


        //region Doctor Name
        doctorName=findViewById(R.id.service_rate);
        doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String genderFilter=Filters.getString(Filters.PATIENT_GENDER,APICall.getGender(context));

                    APICall.getDoctorsForFilter(false,context,filterMyLocationLat,filterMyLocationLng,filterDistance,filterSpeciality,filterSupplierId,"");

            }
        });


        //endregion



        // region Service Name
        serviceName=findViewById(R.id.service_place);
        serviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doctorName.getText().toString().equals(getResources().getString(R.string.doctorName))){
                    APICall.showSweetDialog(context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.doc_name_first));
                }
                else
                    APICall.freegetServiceNamesForFilter(context,docId,"");
            }
        });


        //endregion

        // region Execution date
        excecutionDate=findViewById(R.id.service_exec_date);
        excecutionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog name=new Dialog(context);
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
                        tempName=sday+"-"+smonth+" to "+eday+"-"+emonth;
                        String s =syear+"-"+smonth+"-"+sday;
                        String e =eyear+"-"+emonth+"-"+eday;
                        tempFilter = APICall.Filter("2","\""+s+"\"","\""+e+"\"");
                        filterExecDate=","+tempFilter;


//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                        excecutionDate.setText(getResources().getString(R.string.exec_date)+":"+tempName);
                        tempFilter = "";
                        tempName="";
                        //dateFilterTemp=getResources().getString(R.string.requestDateFilter)+":"+startdate;
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.dismiss();
                        tempFilter = "";
                        tempName="";

                    }
                });


                name.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        name.dismiss();
                        tempFilter = "";
                        tempName="";
//                                    service_name.setText("اسم الخدمة");
//                                    service_name.setChecked(false);
//                                    serviceName="";
//                                    filterNames.set(0,"");
//                                    filterPostions.set(0,-1);
                    }
                });
                name.show();
            }
        });


        //endregion


        // region Creation date
        creationDate=findViewById(R.id.creation_date);
        creationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog name=new Dialog(context);
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
                        tempName=sday+"-"+smonth+" to "+eday+"-"+emonth;
                        String s =syear+"-"+smonth+"-"+sday;
                        String e =eyear+"-"+emonth+"-"+eday;
                        tempFilter = APICall.Filter("3","\""+s+"\"","\""+e+"\"");
                        filterCreateDate=","+tempFilter;


//                                        filterPostions.set(0,nameEdTXT.getSelectedItemPosition());
                        creationDate.setText(getResources().getString(R.string.creation_date)+":"+tempName);
                        tempFilter = "";
                        tempName="";
                        //dateFilterTemp=getResources().getString(R.string.requestDateFilter)+":"+startdate;
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.dismiss();
                        tempFilter = "";
                        tempName="";

                    }
                });


                name.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        name.dismiss();
                        tempFilter = "";
                        tempName="";
//                                    service_name.setText("اسم الخدمة");
//                                    service_name.setChecked(false);
//                                    serviceName="";
//                                    filterNames.set(0,"");
//                                    filterPostions.set(0,-1);
                    }
                });
                name.show();
            }
        });


        //endregion
    }

    public static void showClinicsNamesFilterDialog(final Context context)
    {
        servicesList.clear();
        for (int i=0;i<APICall.allClinics.size();i++){
            if(context.getResources().getString(R.string.locale).equals("en"))
                servicesList.add(APICall.allClinics.get(i).getBdb_name());
            else
                servicesList.add(APICall.allClinics.get(i).getBdb_name_ar());
        }

        final Dialog namesalonDialog = new Dialog(context);
        namesalonDialog.setContentView(R.layout.provider_name_layout);
        namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //final Spinner name=namesalonDialog.findViewById(R.id.name);
        final EditText name=namesalonDialog.findViewById(R.id.name);
        final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
        adapter=new HintArrayAdapter(context,0);
        adapter.addAll(servicesList);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        String s = context.getResources().getString(R.string.healthCenteres)+" :";
        add_service.setTitle(s);
        add_service.setAdapter(adapter);
        // set listener
        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
                //  salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

                tempFilter =Filters.getString(Filters.CLINIC_ID,APICall.allClinics.get(position).getBdb_ser_id());
                if(context.getResources().getString(R.string.locale).equals("en"))
                    tempName=APICall.allClinics.get(position).getBdb_name();
                else
                    tempName=APICall.allClinics.get(position).getBdb_name_ar();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                tempName="";
                tempFilter="";
            }
        });


        Button search = namesalonDialog.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!name.getSelectedItem().toString().isEmpty()){
                if (!name.getText().equals("")){
                    namesalonDialog.dismiss();
                    clinicName.setText( tempName);
                    // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                    //filterSupplierName=tempFilter;
                    filterSupplierId=","+tempFilter;
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                  //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));

                    doctorName.setText(context.getResources().getText(R.string.doctorName));
                    filterDoctorName="";
                    tempFilter="";
                    tempName="";
                    // bdb_name="\"SupplierId\":"+idsup+",";
                }else {
                    namesalonDialog.cancel();
                    clinicName.setText(context.getResources().getText(R.string.providerName));
                    filterSupplierId="";
                    tempFilter="";
                    tempName="";
                    //  APICall.filterSortAlgorithm("3", "", "");
                    //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, providerName.getText().toString()));

                    //  bdb_name="";
                }




            }
        });
        namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // nameSalonOrProvider.setChecked(false);
                clinicName.setText(context.getResources().getText(R.string.providerName));
                filterSupplierId="";
                tempFilter="";
                tempName="";
                APICall.filterSortAlgorithm("3", "", "");
                //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, clinicName.getText().toString()));
                // idsup="";
            }
        });
        namesalonDialog.show();
    }
    public static void showServicesNamesFilterDialog(final Context context)
    {
        servicesList.clear();
        for (int i=0;i<APICall.allSupplierServices.size();i++){
            if(context.getResources().getString(R.string.locale).equals("en"))
                servicesList.add(APICall.allSupplierServices.get(i).getBdb_name());
            else
                servicesList.add(APICall.allSupplierServices.get(i).getBdb_name_ar());
        }

        final Dialog namesalonDialog = new Dialog(context);
        namesalonDialog.setContentView(R.layout.provider_name_layout);
        namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //final Spinner name=namesalonDialog.findViewById(R.id.name);
        final EditText name=namesalonDialog.findViewById(R.id.name);
        final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
        adapter=new HintArrayAdapter(context,0);
        adapter.addAll(servicesList);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        String s = context.getResources().getString(R.string.service)+" :";
        add_service.setTitle(s);
        add_service.setAdapter(adapter);
        // set listener
        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
                //  salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

               // tempFilter =Filters.getString(Filters.CLINIC_ID,APICall.allSupplierServices.get(position).getBdb_ser_id());
                tempFilter="{\"bdb_ser_id\":"+APICall.allSupplierServices.get(position).getBdb_ser_id()+"}";
                if(context.getResources().getString(R.string.locale).equals("en"))
                    tempName=APICall.allSupplierServices.get(position).getBdb_name();
                else
                    tempName=APICall.allSupplierServices.get(position).getBdb_name_ar();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                tempName="";
                tempFilter="";
            }
        });


        Button search = namesalonDialog.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!name.getSelectedItem().toString().isEmpty()){
                if (!name.getText().equals("")){
                    namesalonDialog.dismiss();
                    serviceName.setText( tempName);
                    // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                    //filterSupplierName=tempFilter;
                    filterServices=","+tempFilter;
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                   // ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));

                   // doctorName.setText(context.getResources().getText(R.string.doctorName));
                   // filterDoctorName="";
                    tempFilter="";
                    tempName="";
                    // bdb_name="\"SupplierId\":"+idsup+",";
                }else {
                    namesalonDialog.cancel();
                    serviceName.setText(context.getResources().getText(R.string.service));
                    filterServices="";
                    tempFilter="";
                    tempName="";
                    //  APICall.filterSortAlgorithm("3", "", "");
                    //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, providerName.getText().toString()));

                    //  bdb_name="";
                }




            }
        });
        namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // nameSalonOrProvider.setChecked(false);
                serviceName.setText(context.getResources().getText(R.string.providerName));
                filterServices="";
                tempFilter="";
                tempName="";
               // APICall.filterSortAlgorithm("3", "", "");
                //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, clinicName.getText().toString()));
                // idsup="";
            }
        });
        namesalonDialog.show();
    }
    public static void showDoctorsNamesFilterDialog(final Context context)
    {
        servicesList.clear();
        for (int i=0;i<APICall.allDoctors.size();i++){
            if(context.getResources().getString(R.string.locale).equals("en"))
                servicesList.add(APICall.allDoctors.get(i).getBdb_name());
            else
                servicesList.add(APICall.allDoctors.get(i).getBdb_name_ar());
        }

        final Dialog namesalonDialog = new Dialog(context);
        namesalonDialog.setContentView(R.layout.provider_name_layout);
        namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //final Spinner name=namesalonDialog.findViewById(R.id.name);
        final EditText name=namesalonDialog.findViewById(R.id.name);
        final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
        adapter=new HintArrayAdapter(context,0);
        adapter.addAll(servicesList);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        String s = context.getResources().getString(R.string.doctors)+" :";
        add_service.setTitle(s);
        add_service.setAdapter(adapter);
        // set listener
        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
                //  salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

                docId=APICall.allDoctors.get(position).getBdb_ser_id();
                tempFilter=Filters.getString(Filters.DOCTOR_ID,APICall.allDoctors.get(position).getBdb_ser_id());
                if(context.getResources().getString(R.string.locale).equals("en"))
                    tempName=APICall.allDoctors.get(position).getBdb_name();
                else
                    tempName=APICall.allDoctors.get(position).getBdb_name_ar();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                tempFilter="";
                tempName="";
                docId="";
            }
        });

        Button search = namesalonDialog.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!name.getSelectedItem().toString().isEmpty()){
                if (!name.getText().equals("")){
                    namesalonDialog.dismiss();
                    doctorName.setText( tempName);
                    // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                    filterDoctorName=","+tempFilter;
                    tempFilter="";
                    tempName="";
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                    //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));

                    // bdb_name="\"SupplierId\":"+idsup+",";
                }else {
                    namesalonDialog.cancel();
                    doctorName.setText(context.getResources().getText(R.string.doctorName));
                    filterDoctorName="";
                    tempFilter="";
                    tempName="";
                    docId="";
                    //  APICall.filterSortAlgorithm("3", "", "");
                    //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, providerName.getText().toString()));

                    //  bdb_name="";
                }




            }
        });
        namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // nameSalonOrProvider.setChecked(false);
                doctorName.setText(context.getResources().getText(R.string.doctorName));
                filterDoctorName="";
                tempFilter="";
                tempName="";
                docId="";
                APICall.filterSortAlgorithm("3", "", "");
                //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, clinicName.getText().toString()));
                // idsup="";
            }
        });
        namesalonDialog.show();
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
