package com.dcoret.beautyclient.Activities.SingleOffer;

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
import com.dcoret.beautyclient.Activities.OfferBookingResult;
import com.dcoret.beautyclient.Adapters.GroupEffectAdapter;
import com.dcoret.beautyclient.Fragments.GroupBooking.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupBooking.GroupReservationResultActivity;
import com.dcoret.beautyclient.Fragments.GroupBooking.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabTwo;
import com.dcoret.beautyclient.Fragments.SingleMultiBooking.MultiIndividualBookingReservationFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class SingleOfferEffect extends AppCompatActivity {

    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;

    Context context;
    static  int position=0;
    String postdata,offerType,offerplace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        recyclerView=findViewById(R.id.recycleview);
       postdata= getIntent().getStringExtra("filter");
        offerType=getIntent().getStringExtra("offertype");
        offerplace=getIntent().getStringExtra("place");



         position=getIntent().getIntExtra("position",0);
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


        for (int i = 0; i< TabTwo.arrayList.get(position).getSersup_ids().size(); i++){
            if (i==0){
                filter+="\"ser_id\": "+ TabTwo.arrayList.get(position).getSersup_ids().get(i).getBdb_ser_id()+"\n" ;
            }else {
                filter+=",\"ser_id\": "+ TabTwo.arrayList.get(position).getSersup_ids().get(i).getBdb_ser_id()+"\n" ;
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
                getEffectFilter();
                Log.e("effects",effectFilter);
//                getEffects();
//                Log.e("Effectfilter",f);

                Intent intent=new Intent(context, OfferBookingResult.class);
                intent.putExtra("filter",getFilter(effectFilter));
                intent.putExtra("offertype",offerType);
                intent.putExtra("position",position);
                intent.putExtra("place",offerplace);
//                intent.putExtra("filter",getfilter(f));
                startActivity(intent);
            }
        });
    }


    static String effectFilter="";
    public static String getEffectClients(){
        String clients = "{\"clients\":[";
        try {



                    clients += "\t{\"client_name\":\"" +
                            BeautyMainPage.client_name + "\",\"client_phone\":\""+
                            BeautyMainPage.client_number+"\",\"is_current_user\":1"+
//                            ",\"date\": \""+PlaceServiceGroupFragment.dateFilter+"\""+
//                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[\n";

//
                Log.e("SIZE",""+TabTwo.arrayList.get(position).getSersup_ids().size());

                for (int j = 0; j < TabTwo.arrayList.get(position).getSersup_ids().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +TabTwo.arrayList.get(position).getSersup_ids().get(j).getBdb_ser_id() +"}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + TabTwo.arrayList.get(position).getSersup_ids().get(j).getBdb_ser_id()+ "}\n";
                    }
                    Log.e("Ser_Id",TabTwo.arrayList.get(position).getSersup_ids().get(j).getBdb_ser_id());
                }


        }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]}]}";

            Log.e("clients",clients);
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

//    ArrayList<String> getEffects(){
//        for (int i=0;i< APICall.clientEffectRequestModels.size();i++){
//            String ef="";
//            for (int j=0;j<APICall.clientEffectRequestModels.get(i).getClientEffectModels().size();j++){
//                for (int k=0;k<APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().size();k++){
//                    if (ef.equals("")){
//                        ef= "{\"effect_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_effect_id()+
//                                ",\r\n\"effect_value\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_value()+"\r\n }\n";
//                    }else {
//                        ef+= ",{\"effect_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_effect_id()+
//                                ",\r\n\"effect_value\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(j).getEffects().get(k).getBdb_value()+"\r\n }\n";
//                    }
//
//
//                }
//
//
//            }
//            Log.e("clientEffectRe.size",APICall.clientEffectRequestModels.get(i).getClientEffectModels().size()+"");
//            Log.e("getClient_name",APICall.clientEffectRequestModels.get(i).getClient_name()+"");
//            effectsArr.add(ef);
//
//        }
//
//
//        return effectsArr;
//    }
    public static void getEffectFilter(){
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
    }


    public String getFilter(String effect){
        String bdb_pack_code=TabTwo.arrayList.get(position).getBdb_pack_code();
        String date=SingleDateOfferBooking.showDate.getText().toString();
        String cname= BeautyMainPage.client_name;
        String cphone=BeautyMainPage.client_number;

        String services="";
        String bdb_ser_sup_id="",bdb_time="";
        for (int i=0;i<SingleDateOfferBooking.offerClientsModels.get(0).getServiceDetails().size();i++){
            bdb_ser_sup_id=SingleDateOfferBooking.offerClientsModels.get(0).getServiceDetails().get(i).getBdb_ser_sup_id();
            bdb_time=SingleDateOfferBooking.offerClientsModels.get(0).getServiceDetails().get(i).getBdb_time();
            if (i==0){
                services="\t\t{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": "+bdb_time+" }\n";
            }else {
                services=services+"\t\t,{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": "+bdb_time+" }\n";
            }
        }

        String postdata=
                "{\n" +
                        "    \"Filter\": [\n" +
                        "        {\n" +
                        "            \"num\": 34,\n" +
                        "            \"value1\": "+ PlaceServiceFragment.lat+",\n" +
                        "            \"value2\": 0\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"num\": 35,\n" +
                        "            \"value1\": "+PlaceServiceFragment.lng+",\n" +
                        "            \"value2\": 0\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"num\": "+SingleDateOfferBooking.price_num+",\n" +
                        "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                        "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"num\": "+SingleDateOfferBooking.place_num+",\n" +
                        "            \"value1\": 1,\n" +
                        "            \"value2\": 0\n" +
                        "        }\n" +
                        "    ],\n" +

                        "\"bdb_pack_code\":"+bdb_pack_code+",\"date\":\""+date+"\",\"clients\": [        \n" +
                        "\t{\"client_name\": \""+cname+"\",\"client_phone\": \""+cphone+"\",\"is_current_user\": 0,\n" +
                        "\"is_adult\":1\n" +",\"date\":\""+date+"\",\n"+
                        "\t\"services\": [\n" +
                        services+
                        "\t\t],\"effect\":["+effect+"]\n" +
                        "\t}\n" +
                        "\t],\"offer_type\":"+offerType+"\n" +
                        "}";
        Log.e("postdata",postdata);
    return postdata;
    }



}
