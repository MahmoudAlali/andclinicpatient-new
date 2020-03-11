package com.dcoret.beautyclient.Activities.SingleOffer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.OfferBookingResult;
import com.dcoret.beautyclient.Adapters.GroupEffectAdapter;
import com.dcoret.beautyclient.DataModel.ClientEffectModel;
import com.dcoret.beautyclient.DataModel.ClientEffectRequestModel;
import com.dcoret.beautyclient.DataModel.DataOffer;
import com.dcoret.beautyclient.Fragments.OffersForRequest;
import com.dcoret.beautyclient.Fragments.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceFragment;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Fragments.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Service.NotificationsBeauty;

import java.util.ArrayList;

public class SingleOfferEffect extends AppCompatActivity {

    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;
    public static LinearLayout root;

    static Context context;
    static  int position=0;
    String postdata,offerType,offerplace;
    public static String bdb_pack_code;
    static ArrayList<DataOffer.SupIdClass> supIdClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        root=findViewById(R.id.root);
        recyclerView=findViewById(R.id.recycleview);
       postdata= getIntent().getStringExtra("filter");
        offerType=getIntent().getStringExtra("offertype");
        offerplace=getIntent().getStringExtra("place");



         position=getIntent().getIntExtra("position",0);

        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            supIdClasses = OffersForRequest.arrayList.get(position).getSersup_ids();
            bdb_pack_code = OffersForRequest.arrayList.get(position).getBdb_pack_code();
        }
        else
        {
            supIdClasses =TabTwo.arrayList.get(position).getSersup_ids();
            bdb_pack_code = TabTwo.arrayList.get(position).getBdb_pack_code();
        }

        if(!BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            //region CHECK_NOTIFICATION
            String notification = "";
            try {
                notification=getIntent().getStringExtra("notification");

            }
            catch (Exception e){}
            if(!notification.equals(""))

            {
                bdb_pack_code = getIntent().getStringExtra("bdb_pack_id");
                supIdClasses = NotificationsBeauty.supIdClasses;
            }
        {
            bdb_pack_code = getIntent().getStringExtra("bdb_pack_id");
//            supIdClasses = NotificationsBeauty.supIdClasses;
            supIdClasses =TabTwo.arrayList.get(position).getSersup_ids();

            Log.e("SupClassesSize","Size2:"+supIdClasses.size()+"");

        }

            //endregion

        }


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
                "                {\n" ;


        for (int i = 0; i< supIdClasses.size(); i++){
            if (i==0){
                filter+="\"ser_id\": "+ supIdClasses.get(i).getBdb_ser_id()+"\n" ;
            }else {
                filter+=",\"ser_id\": "+ supIdClasses.get(i).getBdb_ser_id()+"\n" ;
            }
        }

        filter+=    "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
        Log.e("Effectfilter",filter);


        APICall.getMyEffectsSingleDateOffer(context,filter,effectAdapter);


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
            Log.e("SIZE",""+supIdClasses.size());

            for (int j = 0; j < supIdClasses.size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                if (j == 0) {
                    clients = clients + "{\"ser_id\":" +supIdClasses.get(j).getBdb_ser_id() +"}\n";
                } else {
                    clients = clients + ",{\"ser_id\":" + supIdClasses.get(j).getBdb_ser_id()+ "}\n";
                }
                Log.e("Ser_Id",supIdClasses.get(j).getBdb_ser_id());
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
            cat_name.setText(clientEffectModel.getClient_name() + ":"+clientEffectModel.getClientEffectModels().get(position).getCat_name());
        } else
            cat_name.setText(clientEffectModel.getClient_name() + ": " + clientEffectModel.getClientEffectModels().get(position).getCat_name());

        Log.e("Effects_cat",clientEffectModel.getClientEffectModels().get(position).getCat_name());

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
       // String bdb_pack_code=TabTwo.arrayList.get(position).getBdb_pack_code();
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
