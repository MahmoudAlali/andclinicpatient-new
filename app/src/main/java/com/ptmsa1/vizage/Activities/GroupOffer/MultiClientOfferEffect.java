package com.ptmsa1.vizage.Activities.GroupOffer;

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
import com.ptmsa1.vizage.Activities.MultiDateOffer.MultiDateOfferBooking;
import com.ptmsa1.vizage.Activities.OfferBookingResult;
import com.ptmsa1.vizage.Adapters.GroupEffectAdapter;
import com.ptmsa1.vizage.Adapters.OfferBookingMultiClientsAdapter;
import com.ptmsa1.vizage.DataModel.ClientEffectModel;
import com.ptmsa1.vizage.DataModel.ClientEffectRequestModel;
import com.ptmsa1.vizage.DataModel.DataOffer;
import com.ptmsa1.vizage.Fragments.MyEffects.MyEffectsActivity;
import com.ptmsa1.vizage.Fragments.OffersForRequest;
import com.ptmsa1.vizage.Fragments.PlaceServiceFragment;
import com.ptmsa1.vizage.Activities.TabTwo;
import com.ptmsa1.vizage.Fragments.freeBookingFragment;
import com.ptmsa1.vizage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MultiClientOfferEffect extends AppCompatActivity {
    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;
    static Context context;
    public static LinearLayout root;
    public static int position;
    public static String bdb_pack_code;
    public static Boolean checkClick=false,checkClick2=false;
    static ArrayList<DataOffer.SupIdClass> supIdClasses;

    String filter,place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;
        checkClick=false;
        checkClick2=false;

        Log.e("OFFER_CLASS_NAME1","is"+APICall.OFFER_CLASS_NAME);

        position=getIntent().getIntExtra("position",0);
        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            supIdClasses = OffersForRequest.arrayList.get(position).getSersup_ids();
            bdb_pack_code = OffersForRequest.arrayList.get(position).getBdb_pack_code();
        }else if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")){
            supIdClasses =TabTwo.arrayList.get(0).getSersup_ids();
            bdb_pack_code = TabTwo.arrayList.get(0).getBdb_pack_code();
        }
        else
        {
            supIdClasses =TabTwo.arrayList.get(position).getSersup_ids();
            bdb_pack_code = TabTwo.arrayList.get(position).getBdb_pack_code();
        }




        //region CHECK_NOTIFICATION
        String notification = "";
        try {
            notification=getIntent().getStringExtra("notification");

        }
        catch (Exception e){}
        try {
            if(!notification.equals(""))

            {
                bdb_pack_code = getIntent().getStringExtra("bdb_pack_id");
//            supIdClasses = NotificationsBeauty.supIdClasses;
            }
        }
        catch (Exception e){}


        //endregion



//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        recyclerView=findViewById(R.id.recycleview);
        root=findViewById(R.id.root);
        effectAdapter=new GroupEffectAdapter(BeautyMainPage.context, APICall.clientEffectRequestModels);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

        String filter= "  { \"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \""+ BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                \n" ;


        for (int i = 0; i< supIdClasses.size(); i++){
            if (i==0){
                filter+="{\"ser_id\": "+ supIdClasses.get(i).getBdb_ser_id()+"}\n" ;
            }else {
                filter+=",{\"ser_id\": "+ supIdClasses.get(i).getBdb_ser_id()+"}\n" ;
            }
        }

        filter+=    "                \n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        Log.e("EffectfilterOffer",getEffectClients());


        APICall.getMyEffectsMultiClientOffer(context,getEffectClients(),effectAdapter);


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
        update.setText(R.string.next);

        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            update.setText(R.string.createRequest);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getEffects();
                    APICall.addBookingRequest(freeBookingFragment.lat+"",freeBookingFragment.lng+"", "",freeBookingFragment.Place,bdb_pack_code,"25",getClientsJ(effectsArr),context);

                }
            });
        }
        else
        {
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


                    if (checkClick && checkClick2){
                        APICall.showUpdateEffectsDialog(context,intent, getEffectFilter());
                    }else {
                        startActivity(intent);
                    }
                }
            });
        }

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
                    for (int k=0;k<supIdClasses.size();k++){
                        if (SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id().equals(supIdClasses.get(k).getBdb_ser_sup_id())){
                            ser_id= supIdClasses.get(k).getBdb_ser_id();
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
    public static ArrayList<String> effectsArr=new ArrayList<>();

    ArrayList<String> getEffects(){
        for (int i=0;i< APICall.clientEffectRequestModels.size();i++){
            if (APICall.clientEffectRequestModels.get(i).getClient_name().equals(BeautyMainPage.client_name)){
                checkClick2=true;
            }
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
        for (int i=0;i< APICall.clientEffectRequestModels.size();i++) {
            if (APICall.clientEffectRequestModels.get(i).getClient_name().equals(BeautyMainPage.client_name)) {
                checkClick2 = true;
                break;
            }
        }

        return effectsArr;
    }


    public static String getEffectFilter(){
        String filter="{\"client_effects\":[";

        for (int i=0;i<APICall.clientEffectRequestModels.size();i++){
            for (int j=0;j<APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().size();j++) {

                if (filter.equals("{\"client_effects\":[")) {
                    filter += "{" +
                            "\"bdb_effect_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().get(j).getBdb_effect_id()+",\n" +
                            "                        \"bdb_client_id\": "+BeautyMainPage.bdb_id+",\n" +
                            "                        \"bdb_value\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().get(j).getBdb_value()+",\n" +
                            "                        \"bdb_effect_client_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().get(j).getBdb_effect_client_id()+"" +
                            "                        }";
                } else {
                    filter += "\n,{" +
                            "\"bdb_effect_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().get(j).getBdb_effect_id()+",\n" +
                            "                        \"bdb_client_id\": "+BeautyMainPage.bdb_id+",\n" +
                            "                        \"bdb_value\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().get(j).getBdb_value()+",\n" +
                            "                        \"bdb_effect_client_id\": "+APICall.clientEffectRequestModels.get(i).getClientEffectModels().get(0).getEffects().get(j).getBdb_effect_client_id()+"" +
                            "                        }";
                }
            }
        }

        filter=filter+"]}";
        Log.e("EffectFilter",filter);
        return filter;
    }


    static String place_num,price_num;
    public static String getClients(ArrayList effectsArr){

        try {
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

        }catch (Exception e){
            e.printStackTrace();
            switch (Integer.parseInt(((AppCompatActivity)context).getIntent().getStringExtra("place"))){
                case 0:
                    place_num="9";
                    price_num="32";
                    break;
                case 1:
                    place_num="8";
                    price_num="1";
                    break;
                case 2:
                    place_num="10";
                    price_num="30";
                    break;
                case 3:
                    place_num="11";
                    price_num="31";
                    break;

            }

        }
        String prices="";
        if (PlaceServiceFragment.maxprice.equals("") || PlaceServiceFragment.maxprice.equals("")){
            prices= " {\n" +
                    "            \"num\": "+price_num+",\n" +
                    "            \"value1\": 0,\n" +
                    "            \"value2\": 10000\n" +
                    "        },\n" ;
        }else {
            prices="{\n" +
                    "            \"num\": "+price_num+",\n" +
                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                    "        },\n";
        }
       // String bdb_pack_code = TabTwo.arrayList.get(position).getBdb_pack_code();
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
                            "        " +prices+
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
    public static String getClients(ArrayList effectsArr,String offerType){

        try {
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

        }catch (Exception e){
            e.printStackTrace();
            switch (Integer.parseInt(((AppCompatActivity)context).getIntent().getStringExtra("place"))){
                case 0:
                    place_num="9";
                    price_num="32";
                    break;
                case 1:
                    place_num="8";
                    price_num="1";
                    break;
                case 2:
                    place_num="10";
                    price_num="30";
                    break;
                case 3:
                    place_num="11";
                    price_num="31";
                    break;

            }

        }
        String prices="";
        if (PlaceServiceFragment.maxprice.equals("") || PlaceServiceFragment.maxprice.equals("")){
            prices= " {\n" +
                    "            \"num\": "+SingleDateMultiClientOfferBooking.price_num+",\n" +
                    "            \"value1\": 0,\n" +
                    "            \"value2\": 10000\n" +
                    "        },\n" ;
        }else {
            prices="{\n" +
                    "            \"num\": "+SingleDateMultiClientOfferBooking.price_num+",\n" +
                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                    "        },\n";
        }
       // String bdb_pack_code = TabTwo.arrayList.get(position).getBdb_pack_code();
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
                            "        " +prices+
                            "        {\n" +
                            "            \"num\": "+SingleDateMultiClientOfferBooking.place_num+",\n" +
                            "            \"value1\": 1,\n" +
                            "            \"value2\": 0\n" +
                            "        }\n" +
                            "    ],\n" +
                    "\n" +
                    "\"date\":\"" + SingleDateMultiClientOfferBooking.add_date.getText().toString() + "\"," +
                    "\t\"bdb_pack_code\":" +SingleDateMultiClientOfferBooking.bdb_pack_id + ",\n" +
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
            postdata = postdata + client + "],\"offer_type\":" +offerType + "\n" +
                    "\t\t   }\n" +
                    "\t\t";
        return postdata;
    }
    public static JSONArray getClientsJ(ArrayList<String> effectsArr){
        JSONArray clients =new JSONArray();
        for (int i = 0; i < SingleDateMultiClientOfferBooking.offerClientsModels.size(); i++) {
            JSONObject clientJ = new JSONObject();

            {
                String cname = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString();
                String phone = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString();
                try {
                    clientJ.put("client_name",cname);
                    clientJ.put("client_phone",phone);
                    clientJ.put("start_date",APICall.arabicToDecimal(SingleDateMultiClientOfferBooking.add_date.getText().toString()));
                    if(phone.equals(BeautyMainPage.client_number))
                        clientJ.put("is_current_user","1");
                    else
                        clientJ.put("is_current_user","0");

                    clientJ.put("old","1");

                    JSONArray services=new JSONArray() ;
                    JSONObject effects=new JSONObject(effectsArr.get(i)) ;
                    clientJ.put("effect",effects);

                    for (int j = 0; j < SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().size(); j++) {
                        JSONObject servic = new JSONObject();
                        servic.put("bdb_ser_sup_id",SingleDateMultiClientOfferBooking.offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id());
                        services.put(servic);

                    }
                    clientJ.put("services",services);
                    clients.put(clientJ);



                }
                catch (Exception e)
                { Log.e("ERR",e.getMessage());}


//                        View view = recyclerView.getLayoutManager().findViewByPosition(1);
//                            String bdb_ser_sup_id = API.dofs.get(i).getSersup_ids().get(i).getBdb_ser_sup_id();
//                        String ser_time=API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();


            }
        }

        return clients;
    }
    public static void addCatLayout(final LinearLayout myroot, ClientEffectRequestModel clientEffectModel, int position){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout_main, myroot, false);
        TextView cat_name;
        LinearLayout myroot2;

        cat_name=layout2.findViewById(R.id.cat_name);
        myroot2=layout2.findViewById(R.id.myroot);

        try {

            if (context.getResources().getString(R.string.locale).equals("ar")) {
                cat_name.setText(clientEffectModel.getClient_name() + ":"+clientEffectModel.getClientEffectModels().get(0).getCat_name());
            } else
                cat_name.setText(clientEffectModel.getClient_name() + ": " + clientEffectModel.getClientEffectModels().get(0).getCat_name());

            Log.e("Effects_cat",clientEffectModel.getClientEffectModels().get(0).getCat_name());

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
                if (!effects.getBdb_value().equals(Constants.effectValues[1])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[1]);

                Log.e(" checkClick","is "+checkClick);
                Log.e(" checkClick","is "+effects.getBdb_value());

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
                if (!effects.getBdb_value().equals(Constants.effectValues[2])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[2]);

                Log.e(" checkClick","is "+checkClick);
                Log.e(" checkClick","is "+effects.getBdb_value());


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
                if (!effects.getBdb_value().equals(Constants.effectValues[3])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[3]);

                Log.e(" checkClick","is "+checkClick);
                Log.e(" checkClick","is "+effects.getBdb_value());


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
                if (!effects.getBdb_value().equals(Constants.effectValues[4])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[4]);

                Log.e(" checkClick","is "+checkClick);
                Log.e(" checkClick","is "+effects.getBdb_value());


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
                if (!effects.getBdb_value().equals(Constants.effectValues[5])){
                    checkClick=true;
//                    update.setEnabled(true);
                }
                effects.setBdb_value(Constants.effectValues[5]);

                Log.e(" checkClick","is "+checkClick);
                Log.e(" checkClick","is "+effects.getBdb_value());


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


}
