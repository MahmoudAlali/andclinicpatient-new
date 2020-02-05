package com.dcoret.beautyclient.Activities.GroupOffer;

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
import com.dcoret.beautyclient.Activities.SingleOffer.SingleDateOfferBooking;
import com.dcoret.beautyclient.Adapters.GroupEffectAdapter;
import com.dcoret.beautyclient.Adapters.OfferBookingMultiClientsAdapter;
import com.dcoret.beautyclient.Fragments.GroupBooking.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupBooking.GroupReservationResultActivity;
import com.dcoret.beautyclient.Fragments.GroupBooking.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabTwo;
import com.dcoret.beautyclient.Fragments.SingleMultiBooking.MultiIndividualBookingReservationFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class MultiClientOfferEffect extends AppCompatActivity {
    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;
    static Context context;
    static int position;
    String filter,place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

        position=getIntent().getIntExtra("position",0);
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
                "                \n" ;


        for (int i = 0; i< TabTwo.arrayList.get(position).getSersup_ids().size(); i++){
            if (i==0){
                filter+="{\"ser_id\": "+ TabTwo.arrayList.get(position).getSersup_ids().get(i).getBdb_ser_id()+"}\n" ;
            }else {
                filter+=",{\"ser_id\": "+ TabTwo.arrayList.get(position).getSersup_ids().get(i).getBdb_ser_id()+"}\n" ;
            }
        }

        filter+=    "                \n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        Log.e("EffectfilterOffer",getEffectClients());


        APICall.getMyEffectsClientGroup(context,getEffectClients(),effectAdapter);


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEffects();
                Log.e("Effectfilter",getClients(effectsArr));

                Intent intent=new Intent(context, OfferBookingResult.class);
                intent.putExtra("filter",getClients(effectsArr));
//                intent.putExtra("filter", postdata);
                intent.putExtra("place", getIntent().getStringExtra("place"));
                intent.putExtra("position", position);
                intent.putExtra("offertype",getIntent().getStringExtra("offertype")  );
                startActivity(intent);
            }
        });
    }


    static String effectFilter="";
    public static String getEffectClients(){
        Log.e("SizeEffectC",OfferBookingMultiClientsAdapter.selectDateOfferModels.size()+"");
        String clients = "{\"clients\":[";
        try {

            for (int i = 0; i< OfferBookingMultiClientsAdapter.selectDateOfferModels.size(); i++) {
                String cname = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString();
                String phone = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString();

                String current_user="0";
                if (cname.equals(BeautyMainPage.client_name)){
                   current_user="1";
                }
                if (i == 0) {
                    clients += "\t{\"client_name\":\"" +
                            cname + "\",\"client_phone\":\""+
                           phone+"\",\"is_current_user\":"+current_user+
                            ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + cname
                            + "\",\"client_phone\":\""+phone+"\",\"is_current_user\":"+current_user
                            +",\"services\":[\n";

                }
//                Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j<SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().size() ; j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    String ser_id="";
                    for (int k=0;k<TabTwo.arrayList.get(position).getSersup_ids().size();k++){
                        if (SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id().equals(TabTwo.arrayList.get(position).getSersup_ids().get(k).getBdb_ser_sup_id())){
                            ser_id= TabTwo.arrayList.get(position).getSersup_ids().get(k).getBdb_ser_id();
                        }
                    }
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +ser_id +"}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + ser_id+ "}\n";
                    }
//                    Log.e("Ser_Id",GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
                }
                if (OfferBookingMultiClientsAdapter.selectDateOfferModels.size()==1){
                    clients=clients+"]}";
                }else if (i==OfferBookingMultiClientsAdapter.selectDateOfferModels.size()-1){
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
    static String place_num,price_num;
    public static String getClients(ArrayList effectsArr){
        switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()){
            case 1:
                place_num="9";
                price_num="32";
                break;
            case 2:
                place_num="8";
                price_num="1";
                break;
            case 3:
                place_num="10";
                price_num="30";
                break;
            case 4:
                place_num="11";
                price_num="31";
                break;

        }

        String bdb_pack_code = TabTwo.arrayList.get(position).getBdb_pack_code();
            String postdata =
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
                            "            \"num\": "+ price_num+",\n" +
                            "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                            "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"num\": "+place_num+",\n" +
                            "            \"value1\": 1,\n" +
                            "            \"value2\": 0\n" +
                            "        }\n" +
                            "    ],\n" +
                    "\n" +
                    "\"date\":\"" + SingleDateMultiClientOfferBooking.add_date.getText().toString() + "\"," +
                    "\t\"bdb_pack_code\":" + bdb_pack_code + ",\n" +
                    "\t\"clients\": [ \n ";
//                offerClientsModels
            Log.e("offerClientsModels", SingleDateMultiClientOfferBooking.offerClientsModels.size() + "");
            Log.e("selectDateOfferModels", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
            String client = "";

            for (int i = 0; i < SingleDateMultiClientOfferBooking.offerClientsModels.size(); i++) {

                    String cname = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString();
                    String phone = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString();
                    if (i == 0) {
                        client = client + "{\"client_name\": \"" + cname + "\",\"client_phone\": \"" + phone + "\",\"is_adult\":1,\"date\":\""+APICall.arabicToDecimal(SingleDateMultiClientOfferBooking.add_date.getText().toString())+"\",\"is_current_user\": 0,\"services\": [ \n";
                        String services = "";
                        for (int j = 0; j < SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().size(); j++) {
                            if (j == 0) {
                                services = "{ \"bdb_ser_sup_id\": " + SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                            } else {
                                services += ",{ \"bdb_ser_sup_id\": " + SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                            }
                        }
                        client = client + services + "],\"effect\":["+effectsArr.get(i)+"]}";
                        Log.e("selectDateOff.size()", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
                    } else {
                        client = client + ",{\"client_name\": \"" + cname + "\",\"client_phone\": \"" + phone + "\",\"is_adult\":1,\"date\":\""+APICall.arabicToDecimal(SingleDateMultiClientOfferBooking.add_date.getText().toString())+"\",\"is_current_user\": 0,\"services\": [ \n";
                        String services = "";
                        for (int j = 0; j < SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().size(); j++) {
                            if (j == 0) {
                                services = "{ \"bdb_ser_sup_id\": " + SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                            } else {
                                services += "\n,{ \"bdb_ser_sup_id\": " + SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                            }
                        }
                        client = client + services + "],\"effect\":["+effectsArr.get(i)+"]}";
                        Log.e("selectDateOff.size()", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
                    }
//                        View view = recyclerView.getLayoutManager().findViewByPosition(1);
//                            String bdb_ser_sup_id = API.dofs.get(i).getSersup_ids().get(i).getBdb_ser_sup_id();
//                        String ser_time=API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();



            }
            postdata = postdata + client + "],\"offer_type\":" + ((AppCompatActivity)context).getIntent().getStringExtra("offertype") + "\n" +
                    "\t\t   }\n" +
                    "\t\t";
        return postdata;
    }


}
