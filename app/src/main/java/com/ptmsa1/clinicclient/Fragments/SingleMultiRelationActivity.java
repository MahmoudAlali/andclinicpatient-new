package com.ptmsa1.clinicclient.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.DataModel.ClientsRelationsViewClass;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;

public class SingleMultiRelationActivity extends AppCompatActivity {
    //    LinearLayout myroot;
    Button done;
    Context context;
    CheckBox multi_salon_client_check, multi_salon_client_rel_check;
    public static String multi_salon_clients_rel = "1";
    public static String multi_salon_client = "1";
    public static ArrayList<String> relations = new ArrayList<>();
    public static ArrayList<ClientsRelationsViewClass> clientRelationView = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_multi_relation);
//        myroot=findViewById(R.id.myroot);
        done = findViewById(R.id.done);
        done = findViewById(R.id.done);
        multi_salon_client_check = findViewById(R.id.multi_salon_client);
        multi_salon_client_rel_check = findViewById(R.id.multi_salon_rel);
        context = this;
        ArrayList<String> arrayList = new ArrayList<>();
        Log.e("clientSize", GroupReservationFragment.clientsViewData.size() + "");
//        for (int j = 0; j < GroupReservationFragment.clientsViewData.size(); j++) {
//            arrayList.add(GroupReservationFragment.clientsViewData.get(j).getClient_name().getText().toString());
//        }
//        for (int i = 0; i < GroupReservationFragment.clientsViewData.size(); i++) {
//            Log.e("clientSize", i + "");
//                        ArrayList<String> arrayList1=new ArrayList<>();
//                        for (int j=0;j<GroupReservationFragment.clientsViewData.size();j++) {
//                            if (GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString().
//                                    equals(GroupReservationFragment.clientsViewData.get(j).getClient_name().getText().toString())) {
////                                arrayList1 = new ArrayList<>();
//                                arrayList1.add(GroupReservationFragment.clientsViewData.get(j).getClient_name().getText().toString());
//                            }
//                        }
//            addLayout(GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString(),
//                    arrayList, BeautyMainPage.context,myroot);
//
//        }

            multi_salon_client_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        multi_salon_client = "0";
                    } else {
                        multi_salon_client = "1";
                    }
                }
            });

            multi_salon_client_rel_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!multi_salon_client_check.isChecked()) {
                            multi_salon_clients_rel = "1";
                        } else {
                            multi_salon_clients_rel = "0";
                        }
                    }
                }
            });


//            done.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    relations.clear();
////                    multi_salon_clients_rel =
//
//                }
//            });

//        }
    }
    static String effectFilter="";
    public static String getEffectFilter(){
        effectFilter="";
        for (int i=0;i< APICall.clientEffectModels.size();i++){
            for (int j=0;j<APICall.clientEffectModels.get(i).getEffects().size();j++){
                if (effectFilter.equals("")){
                    effectFilter="{\"effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+"\n,\"effect_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+"}\n";
                }else {
                    effectFilter+=",{\"effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+"\n,\"effect_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+"}\n";

                }
            }
        }
        return effectFilter;
    }


    public static String getfilter(String eff_filter){
        String clientf="";
//        SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);


        String username = BeautyMainPage.client_name;
        String phone = BeautyMainPage.client_number;

        if (MultiIndividualBookingReservationFragment.choose_date.getVisibility() == View.VISIBLE) {
            String date = MultiIndividualBookingReservationFragment.choose_date.getText().toString();

            clientf = " \"multi_salon_client\": \"" + multi_salon_client + "\",\n" +
                    "       \"multi_salon_clients_rel\": \"" + multi_salon_clients_rel + "\",\n" +" \"clients\": [\n" +
                    "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"rel\":\"0\",\"date\": \"" + date + "\",\"services\": [ ";
            String services = "";
            for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {

                if (i == 0) {
                    services = "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }";
                } else {
                    services = services + ",{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }";
                }

            }

            clientf = clientf + services + "" +
                    "]" +
                    ",\"effect\":["+eff_filter+"]" +
                    "}\n]}";
        }
        else {
            clientf = " \"clients\": [\n";
            for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {

                if (i == 0) {
                    clientf = clientf + "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"rel\":\"0\",\"date\": \"" + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                    String services = "";
//                        for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {


                    clientf += "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }]" +
                            ",\"effect\":["+eff_filter+"]\n}";

                } else {

                    clientf = clientf + "\t\t,{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"rel\":\"0\",\"date\": \"" + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                    String services = "";
//                        for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {

                    clientf += "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }]" +
                            ",\"effect\":["+eff_filter+"]\n}";
                }

            }
            clientf = clientf + "]}";
        }
        return clientf;
    }

    public void doneclick(View view) {

//        if (multi_salon_clients_rel.equals("0")) {
//            APICall.showSweetDialog(context, "", "PLease select the relations between clients");
//        } else {
            String filter = "";
            filter = getfilter(getEffectFilter());
        Log.e("filter1",filter);

        Intent intent = new Intent(context, SingleMultiAltResultActivity.class);
            intent.putExtra("filter1", filter);
            startActivity(intent);


            //----- call group filter for booking -------------


//        }
    }
}
