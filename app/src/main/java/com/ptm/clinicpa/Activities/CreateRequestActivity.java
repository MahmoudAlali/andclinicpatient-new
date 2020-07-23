package com.ptm.clinicpa.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.DataModel.AppointmentsDataModel;
import com.ptm.clinicpa.DataModel.ClientServiceDataModel;
import com.ptm.clinicpa.DataModel.ClientsViewData;
import com.ptm.clinicpa.DataModel.GroupBookingModel;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.ptm.clinicpa.Activities.AddRelativeActivity.relativeSpinner;
import static com.ptm.clinicpa.Activities.GroupOffer.SingleDateMultiClientOfferBooking.adapter2;

public class CreateRequestActivity extends AppCompatActivity {

    public static Button next,addNewClient,add_date;
    public static Context context;
    public static LinearLayout show_clients,servicesLayout;
    public static ArrayList<String> supplierServicesNames=new ArrayList();
    public static ArrayList<String> agesList=new ArrayList();
    public static ArrayList<ClientServiceDataModel> supplierServices=new ArrayList();
    public static ArrayList<GroupBookingModel> clientsArrayList=new ArrayList();
    public static ArrayList<ClientServiceDataModel> servicesModels=new ArrayList<>();
    public static ArrayList<ClientsViewData> clientsViewData=new ArrayList<>();
    public static TextView start_time;

    public  static  DatePicker dateDialog;
    public static String is_group_booking="";
    public  String postdata;
    public static String sup_id,pack_code,latNum,longNum;
    public  static Date startDate,endDate;
    public static Spinner /*hourSpinner,minutesSpinner,*//*relativeSpinner,genderSpinner,*/servicesSpinner;

    //public static SearchableSpinner ageSpinner;
    public static boolean isOffer;
    public  static HintArrayAdapter adapter1,adapterAge;
    public static CheckBox personalReserv;
    public static EditText phoneNumber,ClientName,description,healthFileNum;
    int startWorkHour,startWorkMinutes;
    public static LinearLayout adding_service_layout,offerInfoLayout;
    public static String age,gender,relation,health_record,client_name;
    public static TextView speciality,centerName,doctorName,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        context=this;

        health_record="";
//        --- init arrays----------
        supplierServicesNames.clear();
        supplierServices.clear();
        clientsArrayList.clear();
        clientsViewData.clear();
//        ---------------------------------

        age =getIntent().getStringExtra("age");
        relation =getIntent().getStringExtra("relation");
        gender =getIntent().getStringExtra("gender");
        health_record =getIntent().getStringExtra("health_record");
        client_name =getIntent().getStringExtra("client_name");


        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //addNewClient=findViewById(R.id.add_new_client);
        add_date=findViewById(R.id.add_date);
        next=findViewById(R.id.search);
        servicesLayout=findViewById(R.id.servicesLayout);
        offerInfoLayout=findViewById(R.id.offerInfoLayout);
        speciality=findViewById(R.id.speciality);
        doctorName=findViewById(R.id.doctorName);
        centerName=findViewById(R.id.healthCntr);
        title=findViewById(R.id.title);
        String s= context.getString(R.string.normal_request)+" "+client_name;
        title.setText(s);

        // show_clients=findViewById(R.id.show_clients);
      /*  hourSpinner = findViewById(R.id.hour_from);
        minutesSpinner = findViewById(R.id.minutes_from);*/
       // ageSpinner = findViewById(R.id.age_range);
       // genderSpinner = findViewById(R.id.gender);
        servicesSpinner = findViewById(R.id.add_service);
       // relativeSpinner = findViewById(R.id.relative);
      //  personalReserv = findViewById(R.id.personalReserv);
       // phoneNumber = findViewById(R.id.phone_number);
        ClientName = findViewById(R.id.client_name);
        description = findViewById(R.id.description);
      //  healthFileNum = findViewById(R.id.healthNum);
        start_time=findViewById(R.id.start_time);
      //  ageSpinner.setTitle(context.getString(R.string.age));

        ClientName.setText(client_name);
        ClientName.setEnabled(false);
        servicesModels.clear();
        sup_id = getIntent().getStringExtra("sup_id");
        isOffer = getIntent().getBooleanExtra("is_offer",false);
        if(isOffer) {
            pack_code = getIntent().getStringExtra("pack_code");
            latNum = getIntent().getStringExtra("latNum");
            longNum = getIntent().getStringExtra("longNum");
        }

        if(!isOffer)
            APICall.freegetServiceNames(context,sup_id,freeBookingFragment.Place);

        else
        {
            servicesLayout.setVisibility(View.GONE);
            APICall.browseOneOffer(context,pack_code);
        }
     /*   personalReserv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    findViewById(R.id.relativesLayout).setVisibility(View.GONE);
                    phoneNumber.setText(BeautyMainPage.client_number);
                    ClientName.setText(BeautyMainPage.client_name);
                    if(isOffer)
                    {
                        if(BeautyMainPage.client_gender.equals(APICall.offerBrowse.getSupported_gender())||APICall.offerBrowse.getSupported_gender().equals("2"))
                        {
                            if(BeautyMainPage.client_gender.equals("0"))
                                genderSpinner.setSelection(1);
                            else
                                genderSpinner.setSelection(2);

                            int age=Integer.parseInt(BeautyMainPage.bdb_old);
                          //  ageSpinner.setSelection(age+1);
                            String search="";
                            if(age==0)
                            {
                                search=context.getString(R.string.lessThanYear);
                            }
                            else if(age==1)
                                search=context.getString(R.string.oneYear);
                            else if(age==2)
                                search=context.getString(R.string.twoYears);
                            else
                            {
                                search=age+" "+context.getString(R.string.years);
                            }
                            int index=-1;
                            for (int i=0;i<agesList.size();i++
                                 ) {
                                if(agesList.get(i).equals(search))
                                {
                                    index=i;
                                    break;
                                }
                            }
                            if(index!=-1)
                                ageSpinner.setSelection(index);
                            else
                                APICall.showSweetDialog(context,R.string.cant_reserv_yourself);



                        }
                        else
                        {
                            APICall.showSweetDialog(context,R.string.cant_reserv_yourself);
                        }

                    }
                    else
                    {
                        int age=Integer.parseInt(BeautyMainPage.bdb_old);
                        ageSpinner.setSelection(age+1);
                        if(BeautyMainPage.client_gender.equals("0"))
                            genderSpinner.setSelection(1);
                        else
                            genderSpinner.setSelection(2);
                    }


                }
                else
                {
                    findViewById(R.id.relativesLayout).setVisibility(View.VISIBLE);
                    phoneNumber.setText("");
                    ClientName.setText("");
                    genderSpinner.setSelection(0);
                    ageSpinner.setSelection(0);




                }
            }
        });*/
        ArrayAdapter adapter = new HintArrayAdapter(this, 0);
        adapter.addAll(Arrays.asList(getResources().getStringArray(R.array.hours)));
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
     //   hourSpinner.setAdapter(adapter);



//        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(ProviderMainPage.context, R.array.minutes, R.layout.simple_spinner_item_layout_v1);
        ArrayAdapter adapter2 = new HintArrayAdapter(this, 0);
        adapter2.addAll(Arrays.asList(getResources().getStringArray(R.array.minutes)));
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
    //    minutesSpinner.setAdapter(adapter2);

       /* ArrayAdapter ageAdapter = new HintArrayAdapter(this, 0);
        ageAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.age)));
        ageAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ageSpinner.setAdapter(ageAdapter);
*/
       /* ArrayAdapter relativeAdapter = new HintArrayAdapter(this, 0);
        relativeAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.relativesType)));
        relativeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);*/
        //relativeSpinner.setAdapter(relativeAdapter);

      /*  ArrayAdapter genderAdapter = new HintArrayAdapter(this, 0);
        genderAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.gender)));
        genderAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        genderSpinner.setAdapter(genderAdapter);
*/
       /* ArrayAdapter adapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        servicesSpinner.setAdapter(adapter1);


        final ArrayList<ClientServiceDataModel> servicesModels=new ArrayList<>();

        servicesSpinner.setOnItemSelectedListener(this);
*/

        /*supplierServicesNames.clear();
        supplierServicesNames.add(getResources().getString(R.string.select_service));*/
      /*  agesList.clear();
        agesList.add(context.getString(R.string.age));
        agesList.add(context.getString(R.string.lessThanYear));
        agesList.add(context.getString(R.string.oneYear));
        agesList.add(context.getString(R.string.twoYears));

        for (int i=3;i<101;i++)
        {
            String a=i+" "+context.getString(R.string.twoYears);
            agesList.add(a);
        }
        adapterAge=new HintArrayAdapter(context, 0);
        adapterAge.addAll(agesList);
        adapterAge.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ageSpinner.setAdapter(adapterAge);
*/

        adapter1=new HintArrayAdapter(context, 0);
        adapter1.addAll(supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        servicesSpinner.setAdapter(adapter1);
        adding_service_layout = findViewById(R.id.adding_service_layout);

        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {



                    Log.e("Step", "1");

                    addLayout2(servicesSpinner.getSelectedItem() + "", adding_service_layout, servicesSpinner, servicesModels);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.select_date);
                TextView confirm=dialog.findViewById(R.id.confirm);
                TextView cancel=dialog.findViewById(R.id.cancel);
                final DatePicker datePicker=dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(startDate.getTime());

//                RequestProvidersFragment.bdb_booking_period)
                try {
                    Calendar calendar = Calendar.getInstance();
                  //  calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(RequestProvidersFragment.bdb_booking_period));
                    datePicker.setMaxDate(endDate.getTime());
                }catch (Exception e){
                    e.printStackTrace();
                }

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        int month=datePicker.getMonth()+1;
                        add_date.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        add_date.setText(R.string.select_date);
                    }
                });

                dateDialog=datePicker;

                dialog.show();
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
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
                        startWorkHour=timePicker.getHour();
                        startWorkMinutes=timePicker.getMinute();
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
                        start_time.setText(st);
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
       /* addNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    addLayout2("");
            }
        });*/


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Log.e("GroupFilter", API.groupBookingModels.size()+"");


               /* for (int i=0;i<clientsArrayList.size();i++){
                    Log.e("effect client "+i+":",getEffectsOneClient(clientsArrayList.get(i)));
                }*/

              //  Log.e("DateDecemal", API.arabicToDecimal(add_date.getText().toString()));
                Boolean check = true;
/*
                for (int i = 0; i < clientsArrayList.size(); i++) {
                    if (!APICall.checkNumber(clientsArrayList.get(i).getPhoneNumber().getText().toString(), context)) {
                        check = false;
                        break;
                    } else if (clientsArrayList.get(i).getClientName().getText().toString().length() == 0) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_c_name), false);
                        check = false;
                        break;

                    } else if ( i!=0&&clientsArrayList.get(i).getAgeRange().getSelectedItemPosition() == 0){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_range), false);
                        check = false;
                        break;

                    }else if (clientsArrayList.get(i).getServicesModels().size() == 0) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.add_atleast_one_service), false);
                        check = false;
                        break;
                    }


                }
*/
               /* if (!APICall.checkNumber(phoneNumber.getText().toString(), context)) {
                    check = false;
                } else if (ClientName.getText().toString().length() == 0) {
                    APICall.showSweetDialog(context,getResources().getString(R.string.enter_c_name), false);
                    check = false;

                } else if (ageSpinner.getSelectedItemPosition() == 0){
                    APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_range), false);
                    check = false;

                }*/
                /*else if (Integer.parseInt(ageRange.getText().toString())<0 ||Integer.parseInt(ageRange.getText().toString())>100){
                    APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_btwn_range), false);
                    check = false;

                }*/
               /* else if (genderSpinner.getSelectedItemPosition() == 0) {
                    APICall.showSweetDialog(context, getResources().getString(R.string.enter_gender), false);
                    check = false;
                }*//*else if (servicesModels.size() == 0) {
                    APICall.showSweetDialog(context, getResources().getString(R.string.add_atleast_one_service), false);
                    check = false;
                }*//*
*/
                if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                    APICall.showSweetDialog(context,getResources().getString(R.string.select_date_of_booking), false);
                    check = false;
                }

                if (check) {
                    if(freeBookingFragment.filterType.equals("0")||isOffer)//offer
                    {
                        if(clientsArrayList.size()<=1)
                            is_group_booking="23";//free single offer
                        else
                            is_group_booking="25";//free group offer
                    }
                    else //normal
                    {
                        if(clientsArrayList.size()<=1)
                            is_group_booking="20";//free single
                        else
                            is_group_booking="22";//free group
                    }


                   /* postdata="";
                    postdata=getgroupFilter(API.groupBookingModels,bookplce, API.arabicToDecimal(add_date.getText().toString()));

                    Log.e("PostDataddd", postdata);
                    Log.e("getClientsEffect",  geteffectclient(API.groupBookingModels));


*/

                //    Intent intent = new Intent(context, AddEffectsToRequestActivity.class);
                   /* intent.putExtra("postdata",postdata);
                    intent.putExtra("seearchgroup","1");
                    intent.putExtra("rtype", API.rtpe);
                    intent.putExtra("effects",geteffectclient(API.groupBookingModels));
                    intent.putExtra("date", API.arabicToDecimal(add_date.getText().toString()));*/
                 //   startActivity(intent);
                    if(!isOffer)
                    APICall.addBookingRequest2("",freeBookingFragment.lat+"",freeBookingFragment.lng+"",freeBookingFragment.Place+"",add_date.getText().toString(),CreateRequestActivity.is_group_booking,getClients(1),context,description.getText().toString());

                    else {
                        String place;
                        if(APICall.offerBrowse.getBdb_offer_place().equals("0"))
                            place="center";
                        else
                            place="home";
                        APICall.addBookingRequest2(APICall.offerBrowse.getBdb_pack_code(),latNum, longNum,place, add_date.getText().toString(), CreateRequestActivity.is_group_booking, getClients(1), context, description.getText().toString());
                    }

//                    onBackPressed();
//                    finish();
                }

            }
        });



    }

    private static void addLayout2(String s) {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.show_client_layout, show_clients, false);

        ImageView delete =  layout2.findViewById(R.id.delete);
        final EditText cnumber=layout2.findViewById(R.id.phone_number);
        final EditText cname=layout2.findViewById(R.id.client_name);
        final LinearLayout adding_service_layout=layout2.findViewById(R.id.adding_service_layout);
        final EditText ageRange=layout2.findViewById(R.id.age_range);
        final Spinner addService=layout2.findViewById(R.id.add_service);
       /* adapter2= new HintArrayAdapter(context, 0);
        adapter2.addAll(Arrays.asList(context.getResources().getStringArray(R.array.age_range)));
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        //ageRange.setAdapter(adapter2);*/

//        boolean is_bride_service=false;
        ArrayAdapter adapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        addService.setAdapter(adapter1);


        final ArrayList<ClientServiceDataModel> servicesModels=new ArrayList<>();

        addService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){

                    addLayout2(addService.getSelectedItem()+"",adding_service_layout,addService,servicesModels);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        clientsArrayList.add(new GroupBookingModel(cname,cnumber,ageRange,servicesModels));


//        emp_name.setText(s);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i< clientsArrayList.size(); i++) {
                    if (cname.equals(clientsArrayList.get(i).getClientName())) {
                        show_clients.removeView(layout2);
                        clientsArrayList.remove(i);
                        break;
                    }
                }
            }
        });



        show_clients.addView(layout2);

    }
    private static void addLayout2(String s, final LinearLayout layout, Spinner addService, final ArrayList<ClientServiceDataModel> servicesModels) {

        final View layout2;
        Log.e("Step","2");

//        ArrayList<GroupBookingModel.ServicesModel> servicesModels1=new ArrayList<>();
        layout2 = LayoutInflater.from(context).inflate(R.layout.show_emp_layout, layout, false);

        final Button emp_name =  layout2.findViewById(R.id.emp_name);
        ImageView delete =  layout2.findViewById(R.id.delete);

        emp_name.setText(s);
        Log.e("Step","3");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i< servicesModels.size(); i++){
                    if (emp_name.getText().toString().equals(servicesModels.get(i).getBdb_name())||emp_name.getText().toString().equals(servicesModels.get(i).getBdb_name_ar())){
                        servicesModels.remove(i);
                        layout.removeView(layout2);
                        break;
                    }
                }

            }
        });
//        API.groupBookingModels.add(new GroupBookingModel(client_name,client_no,age,))

        Log.e("ServiceName",s);
       // Log.e("nameAPI",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name_ar());
       // Log.e("IdAPI",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_id());
        servicesModels.add(new ClientServiceDataModel(APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_id()+"",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_sup_id()+"",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name(),APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name_ar()));
        layout.addView(layout2);

    }
    private static void addLayout3(String name_en,String name_ar,String bdb_ser_id,String bdb_ser_sup_id, final LinearLayout layout, final ArrayList<ClientServiceDataModel> servicesModels) {

        final View layout2;
        Log.e("Step","2");

//        ArrayList<GroupBookingModel.ServicesModel> servicesModels1=new ArrayList<>();
        layout2 = LayoutInflater.from(context).inflate(R.layout.show_emp_layout2, layout, false);

        final Button emp_name =  layout2.findViewById(R.id.emp_name);
        ImageView delete =  layout2.findViewById(R.id.delete);

        if(context.getString(R.string.locale).equals("en"))
            emp_name.setText(name_en);
        else
            emp_name.setText(name_ar);

        Log.e("Step","3");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i< servicesModels.size(); i++){
                    if (emp_name.getText().toString().equals(servicesModels.get(i).getBdb_name())||emp_name.getText().toString().equals(servicesModels.get(i).getBdb_name_ar())){
                        servicesModels.remove(i);
                        layout.removeView(layout2);
                        break;
                    }
                }

            }
        });
//        API.groupBookingModels.add(new GroupBookingModel(client_name,client_no,age,))

        Log.e("ServiceName",name_ar);
        // Log.e("nameAPI",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name_ar());
        // Log.e("IdAPI",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_id());
        servicesModels.add(new ClientServiceDataModel(bdb_ser_id,bdb_ser_sup_id,name_en,name_ar));
        layout.addView(layout2);

    }

    public static JSONArray getClients(int c)
{
    JSONArray clients =new JSONArray();
    try {


        JSONObject client = new JSONObject();

        client.put("client_name",client_name);
       // client.put("client_phone",phoneNumber.getText().toString());
       // client.put("start_date",CreateRequestActivity.add_date.getText());
        client.put("doctor_id",sup_id);
        try {
            if(!health_record.equals(""))
            {
                client.put("health_record",health_record);
            }
        }
        catch (Exception e)
        {
            Log.e("health_recordERR",e.getMessage());
        }

        if(!start_time.getText().toString().equals("00:00:00"))
        {
            client.put("start_time",CreateRequestActivity.start_time.getText());
        }
       /* if(personalReserv.isChecked())
        {
            client.put("gender",BeautyMainPage.client_gender);
            client.put("relation","0");
        }
        else*/
        {
            client.put("gender",gender);
            client.put("relation",relation);
        }
        String s = relation.equals("0")?"1":"0";
        client.put("is_current_user",s);
        client.put("old",age);
        Log.e("rrrr","index-i");

        JSONArray services=new JSONArray() ;
        for (int j = 0; j < servicesModels.size(); j++) {
            JSONObject servic = new JSONObject();

            if(!isOffer)
                servic.put("ser_id",servicesModels.get(j).getBdb_ser_id());
            else
                servic.put("bdb_ser_sup_id",servicesModels.get(j).getBdb_ser_sup_id());
            services.put(servic);
        }
        if(servicesModels.size()!=0)
        client.put("services",services);

        Log.e("index-i","index-i");
        clients.put(client);
    }catch (Exception e){
        e.printStackTrace();
        Log.e("err",e.getMessage());
    }
    Log.e("clients",clients.toString());
    return clients;
}
public static void showServices(String name_en,String name_ar,String serId,String serSupId )
{
    addLayout3(name_en,name_ar,serId,serSupId, adding_service_layout, servicesModels);

}
public static void setOffer()
{
    for (int i=0;i<APICall.offerBrowse.getSersup_ids().size();i++)
    {
        addLayout3(APICall.offerBrowse.getSersup_ids().get(i).getBdb_name(),
                APICall.offerBrowse.getSersup_ids().get(i).getBdb_name_ar(),
                APICall.offerBrowse.getSersup_ids().get(i).getBdb_ser_id(),
                APICall.offerBrowse.getSersup_ids().get(i).getBdb_ser_sup_id(),
                adding_service_layout,servicesModels);
    }
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"); // or "YYYY-MM-DDThh:mm:ss±0000"
    String dateInString = APICall.offerBrowse.getBdb_offer_start();
    String dateInString2 = APICall.offerBrowse.getBdb_offer_end();
    inputFormat.setTimeZone(TimeZone.getTimeZone("GMT") );
    Date date=null;
    Date date2=null;

    try
    {
        date = inputFormat.parse(dateInString);
        date2 = inputFormat.parse(dateInString2);
    }
    catch (Exception e)
    {
    }
    Calendar c= Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE,-1);
    date=c.getTime();

    c.setTime(date2);
    c.add(Calendar.DATE,-1);

    date2=c.getTime();
    Date now = Calendar.getInstance().getTime();
    if(now.before(date))
    startDate=date;
    else
        startDate=now;
    endDate=date2;

    offerInfoLayout.setVisibility(View.VISIBLE);
    doctorName.setText(APICall.offerBrowse.getBdb_doctor_name());
    if(context.getString(R.string.locale).equals("ar")) {
        speciality.setText(APICall.offerBrowse.getSpecialization_ar());
        centerName.setText(APICall.offerBrowse.getHealth_center_name_ar());
    }
    else {
        speciality.setText(APICall.offerBrowse.getSpecialization_en());
        centerName.setText(APICall.offerBrowse.getHealth_center_name_en());
    }

    String s= context.getString(R.string.offer_request)+" "+client_name;
    title.setText(s);

    //addLayout3(name_en,name_ar,serId,serSupId, adding_service_layout, servicesModels);
  /*  if(APICall.offerBrowse.getSupported_gender().equals("0"))
    {
        genderSpinner.setSelection(1);
    }
    else if(APICall.offerBrowse.getSupported_gender().equals("1"))
    {
        genderSpinner.setSelection(2);
    }
    if(APICall.offerBrowse.getMaxAge().equals(APICall.offerBrowse.getMinAge()))
    {
        int l=Integer.parseInt(APICall.offerBrowse.getMaxAge());
       ageSpinner.setSelection(l+1);
       ageSpinner.setEnabled(false);
    }
    else
    {
        agesList.clear();
        agesList.add(context.getString(R.string.age));
        int maxAge=Integer.parseInt(APICall.offerBrowse.getMaxAge());

        for (int i=1;i<=maxAge+1;i++)
        {
            if(i==1)
            {
                agesList.add(context.getString(R.string.lessThanYear));
            }
            else if(i==2)
                agesList.add(context.getString(R.string.oneYear));
            else if(i==3)
                agesList.add(context.getString(R.string.twoYears));
            else
            {
                String a=i-1+" "+context.getString(R.string.years);
                agesList.add(a);
            }
        }
        adapterAge=new HintArrayAdapter(context, 0);
        adapterAge.addAll(agesList);
        adapterAge.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ageSpinner.setAdapter(adapterAge);
    }
*/
}

    @Override
    public void onBackPressed() {
        Log.e("Back","Pressed");
        super.onBackPressed();
    }
}
