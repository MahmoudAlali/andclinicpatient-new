package com.dcoret.beautyclient.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.GroupEffectAdapter;
import com.dcoret.beautyclient.DataModel.ClientEffectModel;
import com.dcoret.beautyclient.DataModel.ClientEffectRequestModel;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class MyOtherEffectActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;
    public static LinearLayout root;

    public  static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        root=findViewById(R.id.root);
//        recyclerView=findViewById(R.id.recycleview);
//        effectAdapter=new GroupEffectAdapter(BeautyMainPage.context, APICall.clientEffectRequestModels);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

        Log.e("Effectfilter",getEffectClients());


        APICall.getMyEffectsClientOther(context,getEffectClients(),effectAdapter);


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEffects();
//                Log.e("Effectfilter",f);

                Intent intent=new Intent(context,GroupReservationOtherResultActivity.class);
//                intent.putExtra("filter",getfilter(f));
                startActivity(intent);
            }
        });
    }


    static String effectFilter="";
    public static String getEffectClients(){
        String clients = "{\"clients\":[";
        try {

            for (int i = 0; i< GroupReservationOthersFragment.clientsViewData.size(); i++) {
                if (i == 0) {
                    clients += "\t{\"client_name\":\"" +
                            GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+
                            GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+
                            GroupReservationOthersFragment.clientsViewData.get(i).getIs_current_user()+
//                            ",\"date\": \""+PlaceServiceGroupOthersFragment.dateFilter+"\""+
//                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationOthersFragment.clientsViewData.get(i).getIs_current_user()
//                            +",\"rel\":\"0\",\"is_adult\":1"+
//                            ",\"date\": \""+PlaceServiceGroupOthersFragment.dateFilter+"\""
                            +",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() +"}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ "}\n";
                    }
                    Log.e("Ser_Id",GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
                }
                if (GroupReservationOthersFragment.clientsViewData.size()==1){
                    clients=clients+"]}";
                }else if (i==GroupReservationOthersFragment.clientsViewData.size()-1){
                    clients=clients+"]}";

                }else {
                    clients=clients+"]},";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]}";

//            Log.e("clients",clients);
        return clients;
    }

    public static String getfilter(String eff_filter){
        String clientf="";
//        SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);


        String username = BeautyMainPage.client_name;
        String phone = BeautyMainPage.client_number;


        return clientf;
    }
    static ArrayList<String> effectsArr=new ArrayList<>();

    ArrayList<String> getEffects(){
        for (int i=0;i< APICall.clientEffectRequestModels.size();i++){
            String ef="";
            for (int j=0;j<APICall.clientEffectRequestModels.get(i).getClientEffectModels().size();j++){
                for (int k=0;k<APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().size();k++){
                    if (ef.equals("")){
                        ef= "{\"effect_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_effect_id()+
                                ",\r\n\"effect_value\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_value()+"\r\n }\n";
                    }else {
                        ef+= ",{\"effect_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_effect_id()+
                                ",\r\n\"effect_value\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_value()+"\r\n }\n";
                    }


                }


            }
            Log.e("clientEffectRe.size",APICall.clientEffectRequestModels.get(i).getClientEffectModels().size()+"");
            Log.e("getClient_name",APICall.clientEffectRequestModels.get(i).getClient_name()+"");
            effectsArr.add(ef);

        }


        return effectsArr;
    }

    public static String getClients(){
        String clients = null;
        try {

            for (int i=0;i<GroupReservationOthersFragment.clientsViewData.size();i++) {
                if (i == 0) {
                    clients = "\t{\"client_name\":\"" +
                            GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+
                            GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+
                            GroupReservationOthersFragment.clientsViewData.get(i).getIs_current_user()+
                            ",\"date\": \""+ PlaceServiceGroupOthersFragment.dateFilter+"\""+
                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationOthersFragment.clientsViewData.get(i).getIs_current_user()
                            +",\"rel\":\"0\",\"is_adult\":1"+
                            ",\"date\": \""+PlaceServiceGroupOthersFragment.dateFilter+"\""
                            +",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + ",\"ser_time\":60}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ ",\"ser_time\":60}\n";
                    }
                    Log.e("Ser_Id",GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
                }
                String eff="";
                try {
                    eff=effectsArr.get(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (GroupReservationOthersFragment.clientsViewData.size()==1){

                    clients=clients+"],\"effect\":["+eff+"]}";
                }else if (i==GroupReservationOthersFragment.clientsViewData.size()-1){
                    clients=clients+"],\"effect\":["+eff+"]}";

                }else {
                    clients=clients+"],\"effect\":["+eff+"]},";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]}";

//            Log.e("clients",clients);
        return clients;
    }
    public static void addCatLayout(final LinearLayout myroot, ClientEffectRequestModel clientEffectModel,int position){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout_main, myroot, false);
        TextView cat_name;
        LinearLayout myroot2;

        cat_name=layout2.findViewById(R.id.cat_name);
        myroot2=layout2.findViewById(R.id.myroot);

        try {
            if (context.getResources().getString(R.string.locale).equals("ar")) {
                cat_name.setText(clientEffectModel.getClient_name() + ": " + clientEffectModel.getClientEffectModels().get(position).getCat_name_ar());
            } else
                cat_name.setText(clientEffectModel.getClient_name() + ": " + clientEffectModel.getClientEffectModels().get(position).getCat_name());
        }catch (Exception e){
            if (context.getResources().getString(R.string.locale).equals("ar")) {
                cat_name.setText(clientEffectModel.getClient_name() );
            } else
                cat_name.setText(clientEffectModel.getClient_name() );

            e.printStackTrace();
        }
        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
       try {
//           Log.e("Effects_cat",clientEffectModel.getClientEffectModels().get(position).getCat_name());
           for (int i = 0; i < clientEffectModel.getClientEffectModels().get(position).getEffects().size(); i++) {
               Log.e("Effects_name", clientEffectModel.getClientEffectModels().get(position).getEffects().get(i).getBdb_effect_name_ar());
               addlayout(myroot2, clientEffectModel.getClientEffectModels().get(position).getEffects().get(i));
           }

       }catch (Exception e){
           e.printStackTrace();
       }

    }
    public static void addlayout(final LinearLayout myroot, final ClientEffectModel.Effects effects){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout, myroot, false);
        TextView effect_name;
        final LinearLayout dzero,done,dtwo,dthree,dfour,dfive;
        dzero=layout2.findViewById(R.id.dzereo);
        done=layout2.findViewById(R.id.d_one);
        dtwo=layout2.findViewById(R.id.dtwo);
        dthree=layout2.findViewById(R.id.dthree);
        dfour=layout2.findViewById(R.id.dfour);
        dfive=layout2.findViewById(R.id.dfive);


        if (effects.getBdb_value().equals("0")){
            dzero.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals("1")){
            done.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals("2")){
            dtwo.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals("3")){
            dthree.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals("4")){
            dfour.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals("5")){
            dfive.setBackgroundResource(R.color.colorAccent);
        }



        dzero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value("0");
//                effects.setBdb_effect_client_id("0");
                dzero.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.white);
                dtwo.setBackgroundResource(android.R.color.white);
                dthree.setBackgroundResource(android.R.color.white);
                dfour.setBackgroundResource(android.R.color.white);
                dfive.setBackgroundResource(android.R.color.white);
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value("1");
//                effects.setBdb_effect_client_id("0");
                done.setBackgroundResource(R.color.colorAccent);
                dzero.setBackgroundResource(android.R.color.white);
                dtwo.setBackgroundResource(android.R.color.white);
                dthree.setBackgroundResource(android.R.color.white);
                dfour.setBackgroundResource(android.R.color.white);
                dfive.setBackgroundResource(android.R.color.white);
            }
        });
        dtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value("2");
//                effects.setBdb_effect_client_id("0");
                dtwo.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.white);
                dzero.setBackgroundResource(android.R.color.white);
                dthree.setBackgroundResource(android.R.color.white);
                dfour.setBackgroundResource(android.R.color.white);
                dfive.setBackgroundResource(android.R.color.white);
            }
        });
        dthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value("3");
//                effects.setBdb_effect_client_id("0");
                dthree.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.white);
                dtwo.setBackgroundResource(android.R.color.white);
                dzero.setBackgroundResource(android.R.color.white);
                dfour.setBackgroundResource(android.R.color.white);
                dfive.setBackgroundResource(android.R.color.white);
            }
        });
        dfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value("4");
//                effects.setBdb_effect_client_id("0");
                dfour.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.white);
                dtwo.setBackgroundResource(android.R.color.white);
                dthree.setBackgroundResource(android.R.color.white);
                dzero.setBackgroundResource(android.R.color.white);
                dfive.setBackgroundResource(android.R.color.white);
            }
        });
        dfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.setBdb_value("5");
//                effects.setBdb_effect_client_id("0");
                dfive.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.white);
                dtwo.setBackgroundResource(android.R.color.white);
                dthree.setBackgroundResource(android.R.color.white);
                dzero.setBackgroundResource(android.R.color.white);
                dfour.setBackgroundResource(android.R.color.white);
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


}
