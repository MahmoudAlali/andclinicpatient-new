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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.DataModel.ClientServiceDataModel;
import com.ptm.clinicpa.DataModel.ClientsViewData;
import com.ptm.clinicpa.DataModel.GroupBookingModel;
import com.ptm.clinicpa.Fragments.FreeGroupBooking;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CreateGroupRequestActivity extends AppCompatActivity {

    public static Button next,addNewClient,add_date;
    public static Context context;
    static LinearLayout show_clients;
    public static ArrayList<String> supplierServicesNames=new ArrayList();
    public static ArrayList<ClientServiceDataModel> supplierServices=new ArrayList();
    public static ArrayList<GroupBookingModel> clientsArrayList=new ArrayList();
    private static ArrayList<String> servicesList=new ArrayList<>();

    public static ArrayList<ClientsViewData> clientsViewData=new ArrayList<>();
    public static HintArrayAdapter  adapter2;

    public static String is_group_booking="";
    public  String postdata;
    public static String sup_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_request);
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
        show_clients=findViewById(R.id.show_clients);
        sup_id = getIntent().getStringExtra("sup_id");

        context=this;
       // APICall.freegetServiceNames(context,sup_id, FreeGroupBooking.Place);

        APICall.getAllSpecialities(context);
        APICall.getAllRelatives(context,sup_id);

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

        addNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addLayout2("");
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.e("GroupFilter", API.groupBookingModels.size()+"");


               /* for (int i=0;i<clientsArrayList.size();i++){
                    Log.e("effect client "+i+":",getEffectsOneClient(clientsArrayList.get(i)));
                }*/

                //  Log.e("DateDecemal", API.arabicToDecimal(add_date.getText().toString()));
                Boolean check = true;
                for (int i = 0; i < clientsArrayList.size(); i++) {
                    /*if (!APICall.checkNumber(clientsArrayList.get(i).getPhoneNumber().getText().toString(), context)) {
                        check = false;
                        break;
                    } else*/
                    if (clientsArrayList.get(i).getClientName().getText().toString().length() == 0) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_c_name), false);
                        check = false;
                        break;

                    } else if (clientsArrayList.get(i).getAgeRange().getSelectedItemPosition() == 0){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_range), false);
                        check = false;
                        break;

                    }
                    else if (clientsArrayList.get(i).getGenderSpinner().getSelectedItemPosition() == 0){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_range), false);
                        check = false;
                        break;

                    }
                    else if (clientsArrayList.get(i).getRelationSpinner().getSelectedItemPosition() == 0&& !clientsArrayList.get(i).getIsCurrentUser().isChecked() ){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_range), false);
                        check = false;
                        break;

                    }/*else if (clientsArrayList.get(i).getServicesModels().size() == 0) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.add_atleast_one_service), false);
                        check = false;
                        break;
                    }*/


                }

                if (check){
                    if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.select_date_of_booking)
                                , false);
                        check = false;
                    } else if (clientsArrayList.size() == 0) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.add_atleast_one_client)
                                , false);
                        check = false;
                    }
                }




                if (check) {
                    if(FreeGroupBooking.filterType.equals("0"))//offer
                    {
                        if(clientsArrayList.size()==1)
                            is_group_booking="23";//free single offer
                        else
                            is_group_booking="25";//free group offer
                    }
                    else //normal
                    {
                        if(clientsArrayList.size()==1)
                            is_group_booking="20";//free single
                        else
                            is_group_booking="22";//free group
                    }


                   /* postdata="";
                    postdata=getgroupFilter(API.groupBookingModels,bookplce, API.arabicToDecimal(add_date.getText().toString()));

                    Log.e("PostDataddd", postdata);
                    Log.e("getClientsEffect",  geteffectclient(API.groupBookingModels));


*/

                   /* Intent intent = new Intent(context, AddEffectsToRequestActivity.class);
                   *//* intent.putExtra("postdata",postdata);
                    intent.putExtra("seearchgroup","1");
                    intent.putExtra("rtype", API.rtpe);
                    intent.putExtra("effects",geteffectclient(API.groupBookingModels));
                    intent.putExtra("date", API.arabicToDecimal(add_date.getText().toString()));*//*
                    startActivity(intent);*/
                    APICall.addGroupBookingRequest2(freeBookingFragment.filterMyLocationLat+"",freeBookingFragment.filterMyLocationLng+"", CreateGroupRequestActivity.add_date.getText().toString()+"","center",CreateGroupRequestActivity.is_group_booking,getClients(1),context,"");


//                    onBackPressed();
//                    finish();
                }

            }
        });



    }
    static ArrayList<String> specialitiesList=new ArrayList<>();
    static ArrayList<String> relativesList=new ArrayList<>();

    private static void addLayout2(String s) {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.group_request_client_layout, show_clients, false);

        ImageView delete =  layout2.findViewById(R.id.delete);
        final EditText cnumber=layout2.findViewById(R.id.phone_number);
        final AutoCompleteTextView cname=layout2.findViewById(R.id.client_name);
        final LinearLayout adding_service_layout=layout2.findViewById(R.id.adding_service_layout);
        final Spinner ageRange=layout2.findViewById(R.id.age_range);
        final Spinner addService=layout2.findViewById(R.id.add_service);

        final Spinner ageSpinner = layout2.findViewById(R.id.age_range);
        final Spinner genderSpinner = layout2.findViewById(R.id.gender);
        final Spinner servicesSpinner = layout2.findViewById(R.id.add_service);
        final Spinner relativeSpinner = layout2.findViewById(R.id.relative);
        final Spinner doctorName = layout2.findViewById(R.id.doctorName);
        final Spinner doctorSpeciality = layout2.findViewById(R.id.doctorSpeciality);
        final CheckBox personalReserv = layout2.findViewById(R.id.personalReserv);
        final EditText description = layout2.findViewById(R.id.description);
        final EditText healthFileNum = layout2.findViewById(R.id.healthNum);
        final  TextView start_time=layout2.findViewById(R.id.start_time);
//        boolean is_bride_service=false;
        ArrayAdapter adapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        addService.setAdapter(adapter1);

        adapter2= new HintArrayAdapter(context, 0);
        adapter2.addAll(Arrays.asList(context.getResources().getStringArray(R.array.age_range)));
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ageRange.setAdapter(adapter2);


        ArrayAdapter relativeAdapter = new HintArrayAdapter(context, 0);
        relativeAdapter.addAll(Arrays.asList(context.getResources().getStringArray(R.array.relativesType)));
        relativeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        relativeSpinner.setAdapter(relativeAdapter);

        ArrayAdapter genderAdapter = new HintArrayAdapter(context, 0);
        genderAdapter.addAll(Arrays.asList(context.getResources().getStringArray(R.array.gender)));
        genderAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        genderSpinner.setAdapter(genderAdapter);

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

        //clientsArrayList.add(new GroupBookingModel(cname,cnumber,ageRange,servicesModels));
        clientsArrayList.add(new GroupBookingModel(cname,ageRange,genderSpinner,relativeSpinner,doctorName,doctorSpeciality,
                start_time,healthFileNum,description,servicesModels,personalReserv));


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
        if (specialitiesList.size()==0)
        {
            specialitiesList.add(context.getString(R.string.speciality));

            for (int i=0;i<APICall.allSpecialities.size();i++){
                if(context.getResources().getString(R.string.locale).equals("en"))
                    specialitiesList.add(APICall.allSpecialities.get(i).getBdb_name());
                else
                    specialitiesList.add(APICall.allSpecialities.get(i).getBdb_name_ar());

            }
        }


        final ArrayList<String> docNames=new ArrayList<>();
        final ArrayAdapter adapter3 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, docNames);
        adapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        doctorName.setAdapter(adapter3);

        ArrayAdapter adapter2 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, specialitiesList);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        doctorSpeciality.setAdapter(adapter2);
        doctorSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    String filterSpeciality = Filters.getString(Filters.SPECIALITY_ID, APICall.allSpecialities.get(position - 1).getBdb_ser_id());
                    APICall.getDoctors(true, context, "", "", freeBookingFragment.filterDistance, filterSpeciality, "", "",adapter3,docNames);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

              //  filterSpeciality="";
            }
        });


        if (relativesList.size()==0)
            for (int i=0;i<APICall.allRelativesList.size();i++){
                relativesList.add(APICall.allRelativesList.get(i).getBdb_user_name());
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, relativesList);
        cname.setAdapter(adapter);
        cname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(APICall.allRelativesList.get(position).getBdb_gender().equals("0"))
                    genderSpinner.setSelection(1);
                else
                    genderSpinner.setSelection(2);
                relativeSpinner.setSelection(Integer.parseInt(APICall.allRelativesList.get(position).getBdb_relation()));
                if(!APICall.allRelativesList.get(position).getBdb_health_record().equals("null"))
                     healthFileNum.setText(APICall.allRelativesList.get(position).getBdb_health_record());
              //  ageRange.setSelection(Integer.getInteger(APICall.allRelativesList.get(position).get()));

            }
        });
        show_clients.addView(layout2);

    }
    private static void addLayout2(String s, final LinearLayout layout, Spinner addService, final ArrayList<ClientServiceDataModel> servicesModels) {

        final View layout2;

//        ArrayList<GroupBookingModel.ServicesModel> servicesModels1=new ArrayList<>();
        layout2 = LayoutInflater.from(context).inflate(R.layout.show_emp_layout, layout, false);

        final Button emp_name =  layout2.findViewById(R.id.emp_name);
        ImageView delete =  layout2.findViewById(R.id.delete);

        emp_name.setText(s);

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

    public static String getEffectsOneClient(GroupBookingModel groupBookingModels) {
        String names="";
        String services="";
//        for (int i = 0; i < groupBookingModels.ssize(); i++) {
//            if (i == 0) {
        names = "{\"clients\":[{\"client_name\":\"" + clientsArrayList.get(0).getClientName().getText().toString() + "\",\"client_phone\":\"" + clientsArrayList.get(0).getPhoneNumber().getText() + "\",\n" +
                "\t\t\t\"is_current_user\":0,\"services\":[";
        for (int j = 0; j < groupBookingModels.getServicesModels().size(); j++) {
            if (j == 0) {
                services =
                        groupBookingModels.getServicesModels().get(j).getBdb_ser_id();
                names = names + services;
            } else {
                services =
                        "," + groupBookingModels.getServicesModels().get(j).getBdb_ser_id();
                names = names + services;
            }

            if (j == groupBookingModels.getServicesModels().size() - 1) {
                names = names + "]}\n";
            }
        }

        return names+"]}";
    }
/*
    public static String getgroupFilter(ArrayList<GroupBookingModel> groupBookingModels, String bookplace, String date){

        String postdata="{\"booking_place\":\""+bookplace+"\",\"max_emp_count\":5,\"journey_time\":40,\n" +
                "\"date\":\"" + date + "\",\"clients\":[";



        String names="";
        String services="";
        String adultCheck="";
        for (int i=0;i<groupBookingModels.size();i++){
            if (i!=0&&groupBookingModels.get(i).getAgeRange().getSelectedItemPosition()==1){
                adultCheck="0";
            }else {
                adultCheck="1";
            }
            if (i==0){
                names = "{\"client_name\":\"" + API.groupBookingModels.get(i).getClientName().getText().toString() + "\",\"client_phone\":\"" + API.groupBookingModels.get(i).getPhoneNumber().getText() + "\",\n" +
                        "\t\t\t\"is_current_user\":0,\"is_adult\":"+adultCheck+",\"services\":[\n";
                for (int j = 0; j < API.groupBookingModels.get(i).getServicesModels().size(); j++) {
                    if (j == 0) {
                        services =
                                "\t\t\t\t{\"ser_id\":" + API.groupBookingModels.get(i).getServicesModels().get(j).getSer_id() + ",\"ser_time\":" + API.groupBookingModels.get(i).getServicesModels().get(j).getBdb_time() + ",\"effects\": []}\n" ;
                        names = names + services;
                    }else {
                        services =
                                "\t\t\t\t,{\"ser_id\":" + API.groupBookingModels.get(i).getServicesModels().get(j).getSer_id() + ",\"ser_time\":" + API.groupBookingModels.get(i).getServicesModels().get(j).getBdb_time() + ",\"effects\": []}\n" ;
                        names = names + services;
                    }

                    if (j== API.groupBookingModels.get(i).getServicesModels().size()-1){
                        names=names+"]}\n";
                    }
                }
                postdata=postdata+names;

            }else  {
                names = ",{\"client_name\":\"" + API.groupBookingModels.get(i).getClientName().getText().toString() + "\",\"client_phone\":\"" + API.groupBookingModels.get(i).getPhoneNumber().getText() + "\",\n" +
                        "\t\t\t\"is_current_user\":0,\"is_adult\":"+adultCheck+",\"services\":[\n";
                for (int j = 0; j < API.groupBookingModels.get(i).getServicesModels().size(); j++) {
                    if (j == 0) {
                        services =
                                "\t\t\t\t{\"ser_id\":" + API.groupBookingModels.get(i).getServicesModels().get(j).getSer_id() + ",\"ser_time\":" + API.arabicToDecimal(API.groupBookingModels.get(i).getServicesModels().get(j).getBdb_time() )+ ",\"effects\": []}\n" ;
                        names = names + services;
                    }else {
                        services =
                                "\t\t\t\t,{\"ser_id\":" + API.groupBookingModels.get(i).getServicesModels().get(j).getSer_id() + ",\"ser_time\":" + API.arabicToDecimal(API.groupBookingModels.get(i).getServicesModels().get(j).getBdb_time() )+ ",\"effects\": []}\n" ;
                        names = names + services;
                    }
                }
                names=names+"]}\n";
                postdata=postdata+names;

            }
        }
        postdata=postdata+"]}";



//        postdata=postdata+names;
        return postdata;

    }
*/
    public static JSONArray getClients(int c)
    {
        JSONArray clients =new JSONArray();
        try {

            for (int i=0;i<clientsArrayList.size();i++) {

                JSONObject client = new JSONObject();

                Log.e("Client1","11");
                client.put("client_name",clientsArrayList.get(i).getClientName().getText().toString());
                client.put("client_phone",clientsArrayList.get(i).getPhoneNumber().getText().toString());
                if(clientsArrayList.get(i).getMedicalFileNumber().getText().toString().length()!=0)
                {
                    client.put("health_record",clientsArrayList.get(i).getMedicalFileNumber().getText());
                }
                Log.e("Client2","11");

                if(!clientsArrayList.get(i).getAppointmentTime().getText().toString().equals("00:00:00"))
                {
                    client.put("start_time",clientsArrayList.get(i).getAppointmentTime().getText());
                }
                Log.e("Client3","11");

                if(clientsArrayList.get(i).getIsCurrentUser().isChecked())
                {
                    client.put("gender",BeautyMainPage.client_gender);
                    client.put("is_current_user","1");
                    client.put("relation","0");
                }
                else
                {
                    client.put("gender",clientsArrayList.get(i).getGenderSpinner().getSelectedItemPosition()-1);
                    client.put("relation",clientsArrayList.get(i).getRelationSpinner().getSelectedItemPosition());
                    client.put("is_current_user","0");
                }

                Log.e("Client4","11");

                client.put("old",(clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()-1));
                Log.e("Client1","11");
                client.put("doctor_id",(clientsArrayList.get(i).getDoctorName()));
                Log.e("rrrr","index-i");

                JSONArray services=new JSONArray() ;
                //JSONObject effects=new JSONObject(effectsArr.get(i)) ;

                // client.put("effect",effects);

                //  Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < clientsArrayList.get(i).getServicesModels().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    JSONObject servic = new JSONObject();

                    servic.put("ser_id",clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
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
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("err",e.getMessage());
        }
        //clients=clients+"]";

        Log.e("clients",clients.toString());
        return clients;
    }

    public static void refreshDoctors(ArrayAdapter adapter)
    {

    }
}

