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
import com.ptm.clinicpa.DataModel.ClientServiceDataModel;
import com.ptm.clinicpa.DataModel.ClientsViewData;
import com.ptm.clinicpa.DataModel.GroupBookingModel;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.ptm.clinicpa.Activities.GroupOffer.SingleDateMultiClientOfferBooking.adapter2;

public class CreateRequestActivity extends AppCompatActivity {

    public static Button next,addNewClient,add_date;
    public static Context context;
    public static LinearLayout show_clients,servicesLayout;
    public static ArrayList<String> supplierServicesNames=new ArrayList();
    public static ArrayList<ClientServiceDataModel> supplierServices=new ArrayList();
    public static ArrayList<GroupBookingModel> clientsArrayList=new ArrayList();
    public static ArrayList<ClientServiceDataModel> servicesModels=new ArrayList<>();
    public static ArrayList<ClientsViewData> clientsViewData=new ArrayList<>();
    public static TextView start_time;

    public static String is_group_booking="";
    public  String postdata;
    public static String sup_id;
    public static Spinner /*hourSpinner,minutesSpinner,*/relativeSpinner/*ageSpinner*/,genderSpinner,servicesSpinner;

    public  static HintArrayAdapter adapter1;
    public static CheckBox personalReserv;
    public static EditText phoneNumber,ClientName,description,healthFileNum,ageRange;
    int startWorkHour,startWorkMinutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
//        --- init arrays----------
        supplierServicesNames.clear();
        supplierServices.clear();
        clientsArrayList.clear();
        clientsViewData.clear();
//        ---------------------------------



        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addNewClient=findViewById(R.id.add_new_client);
        add_date=findViewById(R.id.add_date);
        next=findViewById(R.id.search);
        servicesLayout=findViewById(R.id.servicesLayout);
       // show_clients=findViewById(R.id.show_clients);
      /*  hourSpinner = findViewById(R.id.hour_from);
        minutesSpinner = findViewById(R.id.minutes_from);*/
        ageRange = findViewById(R.id.age_range);
        genderSpinner = findViewById(R.id.gender);
        servicesSpinner = findViewById(R.id.add_service);
        relativeSpinner = findViewById(R.id.relative);
        personalReserv = findViewById(R.id.personalReserv);
        phoneNumber = findViewById(R.id.phone_number);
        ClientName = findViewById(R.id.client_name);
        description = findViewById(R.id.description);
        healthFileNum = findViewById(R.id.healthNum);
        start_time=findViewById(R.id.start_time);

        sup_id = getIntent().getStringExtra("sup_id");

        context=this;
        APICall.freegetServiceNames(context,sup_id,freeBookingFragment.Place);

        personalReserv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    findViewById(R.id.relativesLayout).setVisibility(View.GONE);
                    phoneNumber.setText(BeautyMainPage.client_number);
                    ClientName.setText(BeautyMainPage.client_name);
                    ageRange.setText(BeautyMainPage.bdb_old);
                    if(BeautyMainPage.client_gender.equals("0"))
                        genderSpinner.setSelection(1);
                    else
                        genderSpinner.setSelection(2);

                }
                else
                {
                    findViewById(R.id.relativesLayout).setVisibility(View.VISIBLE);
                    phoneNumber.setText("");
                    ClientName.setText("");
                    genderSpinner.setSelection(0);
                    ageRange.setText("");




                }
            }
        });
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
        ArrayAdapter relativeAdapter = new HintArrayAdapter(this, 0);
        relativeAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.relativesType)));
        relativeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        relativeSpinner.setAdapter(relativeAdapter);

        ArrayAdapter genderAdapter = new HintArrayAdapter(this, 0);
        genderAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.gender)));
        genderAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        genderSpinner.setAdapter(genderAdapter);

       /* ArrayAdapter adapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        servicesSpinner.setAdapter(adapter1);


        final ArrayList<ClientServiceDataModel> servicesModels=new ArrayList<>();

        servicesSpinner.setOnItemSelectedListener(this);
*/

        /*supplierServicesNames.clear();
        supplierServicesNames.add(getResources().getString(R.string.select_service));*/
        adapter1=new HintArrayAdapter(context, 0);
        adapter1.addAll(supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        servicesSpinner.setAdapter(adapter1);

        servicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    final LinearLayout adding_service_layout = findViewById(R.id.adding_service_layout);

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
                if (!APICall.checkNumber(phoneNumber.getText().toString(), context)) {
                    check = false;
                } else if (ClientName.getText().toString().length() == 0) {
                    APICall.showSweetDialog(context,getResources().getString(R.string.enter_c_name), false);
                    check = false;

                } else if (ageRange.getText().length() == 0){
                    APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_range), false);
                    check = false;

                }
                else if (Integer.parseInt(ageRange.getText().toString())<0 ||Integer.parseInt(ageRange.getText().toString())>100){
                    APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_btwn_range), false);
                    check = false;

                }
                else if (genderSpinner.getSelectedItemPosition() == 0) {
                    APICall.showSweetDialog(context, getResources().getString(R.string.enter_gender), false);
                    check = false;
                }/*else if (servicesModels.size() == 0) {
                    APICall.showSweetDialog(context, getResources().getString(R.string.add_atleast_one_service), false);
                    check = false;
                }*/

                if (check){
                    if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.select_date_of_booking)
                                , false);
                        check = false;
                    }/* else if (clientsArrayList.size() == 0) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.add_atleast_one_client)
                                , false);
                        check = false;
                    }*/
                }




                if (check) {
                    if(freeBookingFragment.filterType.equals("0"))//offer
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
                    APICall.addBookingRequest2(freeBookingFragment.lat+"",freeBookingFragment.lng+"",freeBookingFragment.Place+"",add_date.getText().toString(),CreateRequestActivity.is_group_booking,getClients(1),context,description.getText().toString());

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
        Log.e("nameAPI",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name_ar());
        Log.e("IdAPI",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_id());
        servicesModels.add(new ClientServiceDataModel(APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_id()+"",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_ser_sup_id()+"",APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name(),APICall.allSupplierServices.get(addService.getSelectedItemPosition()-1).getBdb_name_ar()));
        layout.addView(layout2);

    }
public static JSONArray getClients(int c)
{
    JSONArray clients =new JSONArray();
    try {

        for (int i=0;i<CreateRequestActivity.clientsArrayList.size();i++) {


        }
        JSONObject client = new JSONObject();

        client.put("client_name",ClientName.getText().toString());
       // client.put("client_phone",phoneNumber.getText().toString());
       // client.put("start_date",CreateRequestActivity.add_date.getText());
        client.put("doctor_id",sup_id);
        if(healthFileNum.getText().toString().length()!=0)
        {
            client.put("health_record",healthFileNum.getText());
        }
        if(!start_time.getText().toString().equals("00:00:00"))
        {
            client.put("start_time",CreateRequestActivity.start_time.getText());
        }
        if(personalReserv.isChecked())
        {
            client.put("gender",BeautyMainPage.client_gender);
            client.put("relation","0");
        }
        else
        {
            client.put("gender",genderSpinner.getSelectedItemPosition()-1);
            client.put("relation",relativeSpinner.getSelectedItemPosition());
        }
        String s = personalReserv.isChecked()?"1":"0";
        client.put("is_current_user",s);
        client.put("old",(ageRange.getText().toString()));
        Log.e("rrrr","index-i");

        JSONArray services=new JSONArray() ;
        //JSONObject effects=new JSONObject(effectsArr.get(i)) ;

        // client.put("effect",effects);

        //  Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

        for (int j = 0; j < servicesModels.size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
            JSONObject servic = new JSONObject();

            servic.put("ser_id",servicesModels.get(j).getBdb_ser_id());
            services.put(servic);
        }
        client.put("services",services);

        String eff="";
        Log.e("index-i","index-i");
            /*try {
                eff=effectsArr.get(i);
            }catch (Exception e){
                e.printStackTrace();
            }*/
               /* if (CreateRequestActivity.clientsArrayList.size()==1){

                    clients=clients+"],\"effect\":["+eff+"]}";
                }else if (i==CreateRequestActivity.clientsArrayList.size()-1){
                    clients=clients+"],\"effect\":["+eff+"]}";

                }else {
                    clients=clients+"],\"effect\":["+eff+"]},";

                }*/

        clients.put(client);
    }catch (Exception e){
        e.printStackTrace();
        Log.e("err",e.getMessage());
    }
    //clients=clients+"]";

    Log.e("clients",clients.toString());
    return clients;
}


}
