package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.API.Constants;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.GroupEffectAdapter;
import com.ptmsa1.vizage.DataModel.ClientEffectModel;
import com.ptmsa1.vizage.DataModel.ClientEffectRequestModel;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class MySingleMultiEffectActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;

    public static Context context;
    public static LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context = this;

//        select_cat=findViewById(R.id.select_cat);
        update = findViewById(R.id.update);
        root= findViewById(R.id.root);
//        recyclerView = findViewById(R.id.recycleview);
//        effectAdapter = new GroupEffectAdapter(BeautyMainPage.context, APICall.clientEffectRequestModels);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

        String filter = "  { \"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \"" + BeautyMainPage.client_name + "\",\n" +
                "            \"client_phone\": \"" + BeautyMainPage.client_number + "\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                {\n";


        for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {
            if (i == 0) {
                filter += "\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + "\n";
            } else {
                filter += ",\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + "\n";
            }
        }

        filter += "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";


        Log.e("Effectfilter", getEffectClients());
        APICall.getMyEffectsClientMulti(context, getEffectClients(), effectAdapter);


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String f= getEffects();
                Log.e("Effectfilter",f);
                Log.e("getFilterMultiDates",getFilterMultiDates(f));

                Intent intent = new Intent(context, MultiBookingIndividualResultActivity.class);
                intent.putExtra("filter",getfilter(f));
                Log.e("Allfilter",getfilter(f));
                startActivity(intent);
            }
        });
    }


    static String effectFilter = "";

    public static String getEffectClients() {
        String clients = "{\"clients\":[";
        try {

            for (int i = 0; i < MultiIndividualBookingReservationFragment.clientsViewData.size(); i++) {
                if (i == 0) {
                    clients += "\t{\"client_name\":\"" +
                            MultiIndividualBookingReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\"" +
                            MultiIndividualBookingReservationFragment.clientsViewData.get(i).getPhone_number().getText() + "\",\"is_current_user\":" +
                            MultiIndividualBookingReservationFragment.clientsViewData.get(i).getIs_current_user() +
                            ",\"date\": \"" + PlaceServiceGroupFragment.dateFilter + "\"" +
//                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients + "\t{\"client_name\":\"" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\"" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getPhone_number().getText() + "\",\"is_current_user\":" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getIs_current_user()
//                            +",\"rel\":\"0\",\"is_adult\":1"+
                            + ",\"date\": \"" + PlaceServiceGroupFragment.dateFilter + "\""
                            + ",\"services\":[\n";

                }
                Log.e("SIZE", "" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + "}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + "}\n";
                    }
                    Log.e("Ser_Id", MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
                }
                if (MultiIndividualBookingReservationFragment.clientsViewData.size() == 1) {
                    clients = clients + "]}";
                } else if (i == MultiIndividualBookingReservationFragment.clientsViewData.size() - 1) {
                    clients = clients + "]}";

                } else {
                    clients = clients + "]},";

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        clients = clients + "]}";

//            Log.e("clients",clients);
        return clients;
    }

    public static String getfilter(String eff_filter) {
        String clientf = "";
//        SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        String username = BeautyMainPage.client_name;
        String phone = BeautyMainPage.client_number;

        clientf="\"multi_salon_client\": 0,  \"multi_salon_clients_rel\": 0,\t\t\"clients\":[\t{\"client_name\":\""+BeautyMainPage.client_name+"\",\"client_phone\":\""+BeautyMainPage.client_number+"\",\"is_current_user\":1,\"date\": \""+PlaceServiceMultipleBookingFragment.dateFilter+"\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n" ;
        for (int i=0;i<MultiIndividualBookingReservationFragment.servicesForClientGroups.size();i++){
            if (i==0){
                clientf += "{\"ser_id\":"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId()+"}\n" ;
            }else {
                clientf += ",{\"ser_id\":"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId()+"}\n" ;
            }
        }

               clientf +="],\"effect\":["+eff_filter+"] " +
                       " \t}\n" +
                       "    ]\n" +
                       "\t\n" +
                       "}";



        return clientf;
    }



    public static String getFilterMultiDates(String effects){
        String f="";
        String clientf=null;
        clientf = "\"multi_salon_client\": 0,  \"multi_salon_clients_rel\": 0,\t\t\"clients\":[";

        for (int i=0;i<MultiIndividualBookingReservationFragment.servicesForClientGroups.size();i++)
            {
                if (clientf==null || i==0) {
                    clientf+="\t{\"client_name\":\"" + BeautyMainPage.client_name + "\",\"client_phone\":\"" + BeautyMainPage.client_number + "\",\"is_current_user\":1,\"date\": \"" + PlaceServiceMultipleBookingFragment.dateFilter + "\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n";
//                    clientf = "\"multi_salon_client\": 0,  \"multi_salon_clients_rel\": 0,\t\t\"clients\":[\t{\"client_name\":\"" + BeautyMainPage.client_name + "\",\"client_phone\":\"" + BeautyMainPage.client_number + "\",\"is_current_user\":1,\"date\": \"" + PlaceServiceMultipleBookingFragment.dateFilter + "\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n";
                    clientf += "{\"ser_id\":"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId()+"}\n" ;
                    clientf +="],\"effect\":["+effects+"] " +
                            " \t}\n" ;
                }else {
                    clientf+=",\t{\"client_name\":\"" + BeautyMainPage.client_name + "\",\"client_phone\":\"" + BeautyMainPage.client_number + "\",\"is_current_user\":1,\"date\": \"" + PlaceServiceMultipleBookingFragment.dateFilter + "\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n";

//                    clientf += ",{\"multi_salon_client\": 0,  \"multi_salon_clients_rel\": 0,\t\t\"clients\":[\t{\"client_name\":\"" + BeautyMainPage.client_name + "\",\"client_phone\":\"" + BeautyMainPage.client_number + "\",\"is_current_user\":1,\"date\": \"" + PlaceServiceMultipleBookingFragment.dateFilter + "\",\"rel\":\"0\",\"is_adult\":1 ,\"services\":[\n";
                    clientf += "{\"ser_id\":"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId()+"}\n" ;
                    clientf +="],\"effect\":["+effects+"] " +
                            " \t}\n" ;
                }




        }
        clientf+=" ]}";
        return clientf;

    }

    static ArrayList<String> effectsArr = new ArrayList<>();
    public static void addCatLayout(final LinearLayout myroot, ClientEffectRequestModel clientEffectModel){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout_main, myroot, false);
        TextView cat_name;
        LinearLayout myroot2;

        cat_name=layout2.findViewById(R.id.cat_name);
        myroot2=layout2.findViewById(R.id.myroot);

        try {
            if (context.getResources().getString(R.string.locale).equals("ar")) {
                cat_name.setText(clientEffectModel.getClientEffectModels().get(0).getCat_name_ar());
            } else
                cat_name.setText(clientEffectModel.getClientEffectModels().get(0).getCat_name());
        }catch (Exception e){
            e.printStackTrace();
        }
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
//        Log.e("Effects_cat",clientEffectModel.getClientEffectModels().get(0).getCat_name());
       try {
           for (int i = 0; i < clientEffectModel.getClientEffectModels().get(0).getEffects().size(); i++) {
               Log.e("Effects_name", clientEffectModel.getClientEffectModels().get(0).getEffects().get(i).getBdb_effect_name_ar());
               addlayout(myroot2, clientEffectModel.getClientEffectModels().get(0).getEffects().get(i));
           }
       }catch (Exception e){
           e.printStackTrace();
       }

    }
    public static void addlayout(final LinearLayout myroot, final ClientEffectModel.Effects effects){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout, myroot, false);
        TextView effect_name,txtOne,txtTwo,txtThree,txtFour,txtFive;
        ImageView one,two,three,four,five;
        final LinearLayout dzero,done,dtwo,dthree,dfour,dfive;
        dzero=layout2.findViewById(R.id.dzereo);
        done=layout2.findViewById(R.id.d_one);
        dtwo=layout2.findViewById(R.id.dtwo);
        dthree=layout2.findViewById(R.id.dthree);
        dfour=layout2.findViewById(R.id.dfour);
        dfive=layout2.findViewById(R.id.dfive);
        one=layout2.findViewById(R.id.one);
        two=layout2.findViewById(R.id.two);
        three=layout2.findViewById(R.id.three);
        four=layout2.findViewById(R.id.four);
        five=layout2.findViewById(R.id.five);
        txtOne=layout2.findViewById(R.id.textOne);
        txtTwo=layout2.findViewById(R.id.textTwo);
        txtThree=layout2.findViewById(R.id.textThree);
        txtFour=layout2.findViewById(R.id.textFour);
        txtFive=layout2.findViewById(R.id.textFive);

        int id =Integer.parseInt(effects.getBdb_effect_id());
        {
            one.setImageResource(Constants.effectsImgs[id][0]);
            two.setImageResource(Constants.effectsImgs[id][1]);
            three.setImageResource(Constants.effectsImgs[id][2]);
            four.setImageResource(Constants.effectsImgs[id][3]);
            five.setImageResource(Constants.effectsImgs[id][4]);
            if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("en"))
            {
                txtOne.setText(Constants.effectStoredStringArayEn[id][0]);
                txtTwo.setText(Constants.effectStoredStringArayEn[id][1]);
                txtThree.setText(Constants.effectStoredStringArayEn[id][2]);
                txtFour.setText(Constants.effectStoredStringArayEn[id][3]);
                txtFive.setText(Constants.effectStoredStringArayEn[id][4]);
            }
            else
            {
                txtOne.setText(Constants.effectStoredStringArayAr[id][0]);
                txtTwo.setText(Constants.effectStoredStringArayAr[id][1]);
                txtThree.setText(Constants.effectStoredStringArayAr[id][2]);
                txtFour.setText(Constants.effectStoredStringArayAr[id][3]);
                txtFive.setText(Constants.effectStoredStringArayAr[id][4]);
            }

        }

        if (effects.getBdb_value().equals(Constants.effectValues[0])) {
            dzero.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[1])){
            done.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[2])){
            dtwo.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[3])){
            dthree.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[4])){
            dfour.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[5])){
            dfive.setBackgroundResource(R.color.colorAccent);
        }



        dzero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value(Constants.effectValues[0]);
//                effects.setBdb_effect_client_id("0");
                dzero.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.transparent);
                dtwo.setBackgroundResource(android.R.color.transparent);
                dthree.setBackgroundResource(android.R.color.transparent);
                dfour.setBackgroundResource(android.R.color.transparent);
                dfive.setBackgroundResource(android.R.color.transparent);
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value(Constants.effectValues[1]);
//                effects.setBdb_effect_client_id("0");
                done.setBackgroundResource(R.color.colorAccent);
                dzero.setBackgroundResource(android.R.color.transparent);
                dtwo.setBackgroundResource(android.R.color.transparent);
                dthree.setBackgroundResource(android.R.color.transparent);
                dfour.setBackgroundResource(android.R.color.transparent);
                dfive.setBackgroundResource(android.R.color.transparent);
            }
        });
        dtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value(Constants.effectValues[2]);
//                effects.setBdb_effect_client_id("0");
                dtwo.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.transparent);
                dzero.setBackgroundResource(android.R.color.transparent);
                dthree.setBackgroundResource(android.R.color.transparent);
                dfour.setBackgroundResource(android.R.color.transparent);
                dfive.setBackgroundResource(android.R.color.transparent);
            }
        });
        dthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value(Constants.effectValues[3]);
//                effects.setBdb_effect_client_id("0");
                dthree.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.transparent);
                dtwo.setBackgroundResource(android.R.color.transparent);
                dzero.setBackgroundResource(android.R.color.transparent);
                dfour.setBackgroundResource(android.R.color.transparent);
                dfive.setBackgroundResource(android.R.color.transparent);
            }
        });
        dfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value(Constants.effectValues[4]);
//                effects.setBdb_effect_client_id("0");
                dfour.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.transparent);
                dtwo.setBackgroundResource(android.R.color.transparent);
                dthree.setBackgroundResource(android.R.color.transparent);
                dzero.setBackgroundResource(android.R.color.transparent);
                dfive.setBackgroundResource(android.R.color.transparent);
            }
        });
        dfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value(Constants.effectValues[5]);
//                effects.setBdb_effect_client_id("0");
                dfive.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.transparent);
                dtwo.setBackgroundResource(android.R.color.transparent);
                dthree.setBackgroundResource(android.R.color.transparent);
                dzero.setBackgroundResource(android.R.color.transparent);
                dfour.setBackgroundResource(android.R.color.transparent);
            }
        });


        effect_name=layout2.findViewById(R.id.effect_name);
        if (BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar")){
            effect_name.setText(effects.getBdb_effect_name_ar());
        }else {
            effect_name.setText(effects.getBdb_effect_name_en());
        }
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
    }

    public  static String getEffects() {
        String ef = "";

        for (int i = 0; i < APICall.clientEffectRequestModels.size(); i++) {
            for (int j = 0; j < APICall.clientEffectRequestModels.get(i).getClientEffectModels().size(); j++) {
                for (int k = 0; k < APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().size(); k++) {
                    if (ef.equals("")) {
                        ef = "{\"effect_id\": " + APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_effect_id() +
                                ",\r\n\"effect_value\": " + APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_value() + "\r\n }\n";
                    } else {
                        ef += ",{\"effect_id\": " + APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_effect_id() +
                                ",\r\n\"effect_value\": " + APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_value() + "\r\n }\n";
                    }


                }


            }


        }


        return ef;
    }

}