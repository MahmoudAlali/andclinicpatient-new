package com.dcoret.beautyclient.Fragments.GroupBooking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.EffectAdapter;
import com.dcoret.beautyclient.Adapters.GroupEffectAdapter;
import com.dcoret.beautyclient.Fragments.SingleMultiBooking.MultiBookingIndividualResultActivity;
import com.dcoret.beautyclient.Fragments.SingleMultiBooking.MultiIndividualBookingReservationFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class MyGroupEffectActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        recyclerView=findViewById(R.id.recycleview);
        effectAdapter=new GroupEffectAdapter(BeautyMainPage.context, APICall.clientEffectRequestModels);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(effectAdapter);

        String filter= "  { \"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \""+ BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                {\n" ;


        for (int i = 0; i< MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++){
            if (i==0){
                filter+="\"ser_id\": "+ MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId()+"\n" ;
            }else {
                filter+=",\"ser_id\": "+ MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId()+"\n" ;
            }
        }

        filter+=    "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        Log.e("Effectfilter",getEffectClients());


        APICall.getMyEffectsClientGroup(context,getEffectClients(),effectAdapter);


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEffects();
//                Log.e("Effectfilter",f);

                Intent intent=new Intent(context, GroupReservationResultActivity.class);
//                intent.putExtra("filter",getfilter(f));
                startActivity(intent);
            }
        });
    }


    static String effectFilter="";
    public static String getEffectClients(){
        String clients = "{\"clients\":[";
        try {

            for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++) {
                if (i == 0) {
                    clients += "\t{\"client_name\":\"" +
                            GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+
                            GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+
                            GroupReservationFragment.clientsViewData.get(i).getIs_current_user()+
//                            ",\"date\": \""+PlaceServiceGroupFragment.dateFilter+"\""+
//                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationFragment.clientsViewData.get(i).getIs_current_user()
//                            +",\"rel\":\"0\",\"is_adult\":1"+
//                            ",\"date\": \""+PlaceServiceGroupFragment.dateFilter+"\""
                            +",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() +"}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ "}\n";
                    }
                    Log.e("Ser_Id",GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
                }
                if (GroupReservationFragment.clientsViewData.size()==1){
                    clients=clients+"]}";
                }else if (i==GroupReservationFragment.clientsViewData.size()-1){
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

            for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++) {
                if (i == 0) {
                    clients = "\t{\"client_name\":\"" +
                            GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+
                            GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+
                            GroupReservationFragment.clientsViewData.get(i).getIs_current_user()+
                            ",\"date\": \""+PlaceServiceGroupFragment.dateFilter+"\""+
                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationFragment.clientsViewData.get(i).getIs_current_user()
                            +",\"rel\":\"0\",\"is_adult\":1"+
                            ",\"date\": \""+PlaceServiceGroupFragment.dateFilter+"\""
                            +",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + ",\"ser_time\":60}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ ",\"ser_time\":60}\n";
                    }
                    Log.e("Ser_Id",GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
                }
                String eff="";
                try {
                    eff=effectsArr.get(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (GroupReservationFragment.clientsViewData.size()==1){

                    clients=clients+"],\"effect\":["+eff+"]}";
                }else if (i==GroupReservationFragment.clientsViewData.size()-1){
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


}
