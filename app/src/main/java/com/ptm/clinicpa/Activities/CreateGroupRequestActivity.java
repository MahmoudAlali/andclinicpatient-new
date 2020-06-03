package com.ptm.clinicpa.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.DataModel.ClientServiceDataModel;
import com.ptm.clinicpa.DataModel.ClientsViewData;
import com.ptm.clinicpa.DataModel.GroupBookingModel;
import com.ptm.clinicpa.Fragments.FreeGroupBooking;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;

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
        APICall.freegetServiceNames(context,sup_id, FreeGroupBooking.Place);

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
                    if(freeBookingFragment.filterType.equals("0"))//offer
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

                    Intent intent = new Intent(context, AddEffectsToRequestActivity.class);
                   /* intent.putExtra("postdata",postdata);
                    intent.putExtra("seearchgroup","1");
                    intent.putExtra("rtype", API.rtpe);
                    intent.putExtra("effects",geteffectclient(API.groupBookingModels));
                    intent.putExtra("date", API.arabicToDecimal(add_date.getText().toString()));*/
                    startActivity(intent);

//                    onBackPressed();
//                    finish();
                }

            }
        });



    }

    private static void addLayout2(String s) {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.group_request_client_layout, show_clients, false);

        ImageView delete =  layout2.findViewById(R.id.delete);
        final EditText cnumber=layout2.findViewById(R.id.phone_number);
        final EditText cname=layout2.findViewById(R.id.client_name);
        final LinearLayout adding_service_layout=layout2.findViewById(R.id.adding_service_layout);
        final Spinner ageRange=layout2.findViewById(R.id.age_range);
        final Spinner addService=layout2.findViewById(R.id.add_service);

//        boolean is_bride_service=false;
        ArrayAdapter adapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, supplierServicesNames);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        addService.setAdapter(adapter1);

        adapter2= new HintArrayAdapter(context, 0);
        adapter2.addAll(Arrays.asList(context.getResources().getStringArray(R.array.age_range)));
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ageRange.setAdapter(adapter2);

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


}

