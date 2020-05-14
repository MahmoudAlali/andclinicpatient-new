package com.ptmsa1.vizage.Activities.SingleOffer;

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
import com.ptmsa1.vizage.Activities.OfferBookingResult;
import com.ptmsa1.vizage.Activities.Offers;
import com.ptmsa1.vizage.Adapters.GroupEffectAdapter;
import com.ptmsa1.vizage.DataModel.ClientEffectModel;
import com.ptmsa1.vizage.DataModel.ClientEffectRequestModel;
import com.ptmsa1.vizage.DataModel.DataOffer;
import com.ptmsa1.vizage.Fragments.MyEffects.MyEffectsActivity;
import com.ptmsa1.vizage.Fragments.OffersForRequest;
import com.ptmsa1.vizage.Fragments.PlaceServiceFragment;
import com.ptmsa1.vizage.Activities.TabTwo;
import com.ptmsa1.vizage.Fragments.freeBookingFragment;
import com.ptmsa1.vizage.R;
import com.ptmsa1.vizage.Service.NotificationsBeauty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleOfferEffect extends AppCompatActivity {

    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button update;
    public static LinearLayout root;
    public static boolean checkClick=false;

    static Context context;
    static  int position=0;
    public  static String postdata,offerType,offerplace;
    public static String bdb_pack_code;
    static ArrayList<DataOffer.SupIdClass> supIdClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;
        Log.e("OFFER_CLASS_NAME3","is"+APICall.OFFER_CLASS_NAME);
//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        root=findViewById(R.id.root);
        recyclerView=findViewById(R.id.recycleview);
       postdata= getIntent().getStringExtra("filter");
        offerType=getIntent().getStringExtra("offertype");
        offerplace=getIntent().getStringExtra("place");



         position=getIntent().getIntExtra("position",0);

         try {
             if (BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment")) {
                 supIdClasses = OffersForRequest.arrayList.get(position).getSersup_ids();
                 bdb_pack_code = OffersForRequest.arrayList.get(position).getBdb_pack_code();
                 Log.e("free", supIdClasses.size() + "");
             } else if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")) {
                 supIdClasses = TabTwo.arrayList.get(0).getSersup_ids();
                 bdb_pack_code = TabTwo.arrayList.get(0).getBdb_pack_code();
                 Log.e("else", supIdClasses.size() + "Offers");
                 Log.e("else", TabTwo.arrayList.get(0).getSersup_ids().size() + "tabtwoOffers");
             } else {
                 supIdClasses = TabTwo.arrayList.get(position).getSersup_ids();
                 bdb_pack_code = TabTwo.arrayList.get(position).getBdb_pack_code();
                 Log.e("else", supIdClasses.size() + "");

             }
         }
         catch (Exception e)
         {
             Log.e("ERROR","SingleOfferEffect : "+e.getMessage());

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
                Log.e("notif1232",supIdClasses.get(0).getBdb_name()+"");
//                supIdClasses = NotificationsBeauty.supIdClasses;

            }
        }
        catch (Exception e){
            Log.e("notifERR ",e.getMessage()+"");
        }

            //endregion

       /* Log.e("SERVICES",supIdClasses.get(0).getBdb_ser_id());
        Log.e("SERVICEcS",supIdClasses.size()+"");
*/
        effectAdapter=new GroupEffectAdapter(BeautyMainPage.context, APICall.clientEffectRequestModels);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

        Log.e("else1", supIdClasses.size() + "Offers");
        Log.e("else1", TabTwo.arrayList.get(0).getSersup_ids().size() + "tabtwoOffers");
        String filter="{\"bdb_pack_code\": \""+bdb_pack_code+"\",\n" + "\"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \""+ BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                {\n" ;


        for (int i = 0; i< supIdClasses.size(); i++){

            if (i==0){
                filter+="\"ser_sup_id\": "+ supIdClasses.get(i).getBdb_ser_sup_id() ;
            }else {
                filter+=",\"ser_sup_id\": "+ supIdClasses.get(i).getBdb_ser_sup_id() ;
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
        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            update.setText(R.string.createRequest);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getEffectFilter();
                    Log.e("effects",effectFilter);

                    APICall.addBookingRequest(freeBookingFragment.lat+"",freeBookingFragment.lng+"", "",freeBookingFragment.Place,bdb_pack_code,"25",getClientsJ(effectFilter),context);

                }
            });
        }
        else
        {
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
                    if (checkClick){
                        APICall.showUpdateEffectsDialog(context,intent, MyEffectsActivity.getEffectFilter());
                    }else
                    startActivity(intent);
                }
            });
        }

    }


    public  static String effectFilter="";
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
        TextView effect_name,txtOne,txtTwo,txtThree,txtFour,txtFive;
        ImageView one,two,three,four,five;        final LinearLayout dzero,done,dtwo,dthree,dfour,dfive;
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

//        if (effects.getBdb_value().equals(Constants.effectValues[0])) {
//            dzero.setBackgroundResource(R.color.colorAccent);
//        }else
        if (effects.getBdb_value().equals(Constants.effectValues[0])){
            done.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[1])){
            dtwo.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[2])){
            dthree.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[3])){
            dfour.setBackgroundResource(R.color.colorAccent);
        }else if (effects.getBdb_value().equals(Constants.effectValues[4])){
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
                if (!effects.getBdb_value().equals(Constants.effectValues[0])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[0]);

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
                if (!effects.getBdb_value().equals(Constants.effectValues[1])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[1]);

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
                if (!effects.getBdb_value().equals(Constants.effectValues[2])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[2]);

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
                if (!effects.getBdb_value().equals(Constants.effectValues[3])){
                    checkClick=true;
//                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[3]);

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
                if (!effects.getBdb_value().equals(Constants.effectValues[4])){
                    checkClick=true;
//                    update.setEnabled(true);
                }
                effects.setBdb_value(Constants.effectValues[4]);

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


    public static String getFilter(String effect){
       // String bdb_pack_code=TabTwo.arrayList.get(position).getBdb_pack_code();
        String date=SingleDateOfferBooking.showDate.getText().toString();
        String cname= BeautyMainPage.client_name;
        String cphone=BeautyMainPage.client_number;
        String prices="";
        if (PlaceServiceFragment.maxprice.equals("") || PlaceServiceFragment.maxprice.equals("")){
            prices= " {\n" +
                    "            \"num\": "+SingleDateOfferBooking.price_num+",\n" +
                    "            \"value1\": 0,\n" +
                    "            \"value2\": 10000\n" +
                    "        },\n" ;
        }else {
            prices="{\n" +
                    "            \"num\": "+SingleDateOfferBooking.price_num+",\n" +
                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                    "        },\n";
        }
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
                        "     " +prices+
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
    public static String getFilter(String effect,String offerType){
       // String bdb_pack_code=TabTwo.arrayList.get(position).getBdb_pack_code();
        String date=SingleDateOfferBooking.showDate.getText().toString();
        String cname= BeautyMainPage.client_name;
        String cphone=BeautyMainPage.client_number;
        String prices="";
        if (PlaceServiceFragment.maxprice.equals("") || PlaceServiceFragment.maxprice.equals("")){
            prices= " {\n" +
                    "            \"num\": "+SingleDateOfferBooking.price_num+",\n" +
                    "            \"value1\": 0,\n" +
                    "            \"value2\": 10000\n" +
                    "        },\n" ;
        }else {
            prices="{\n" +
                    "            \"num\": "+SingleDateOfferBooking.price_num+",\n" +
                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                    "        },\n";
        }
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
                        "     " +prices+
                        "        {\n" +
                        "            \"num\": "+SingleDateOfferBooking.place_num+",\n" +
                        "            \"value1\": 1,\n" +
                        "            \"value2\": 0\n" +
                        "        }\n" +
                        "    ],\n" +

                        "\"bdb_pack_code\":"+SingleDateOfferBooking.bdb_pack_id+",\"date\":\""+date+"\",\"clients\": [        \n" +
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
    public static JSONArray getClientsJ(String effectFilter){
        JSONArray clients =new JSONArray();
            JSONObject clientJ = new JSONObject();
        Log.e("effectFilter",effectFilter);
        String ef ="["+effectFilter+"]";

            {
                String cname= BeautyMainPage.client_name;
                String cphone=BeautyMainPage.client_number;
                try {
                    clientJ.put("client_name",cname);
                    Log.e("ERR1","1");
                    clientJ.put("client_phone",cphone);
                    Log.e("ERR2","1");

                    clientJ.put("start_date",APICall.arabicToDecimal(SingleDateOfferBooking.showDate.getText().toString()));
                    Log.e("ERR3","1");

                    clientJ.put("is_current_user","1");
                    Log.e("ERR4","1");

                    clientJ.put("old","1");
                    Log.e("ERR5","1");

                    JSONArray services=new JSONArray() ;
                    JSONArray effects=new JSONArray(ef) ;
                    Log.e("ERR6","1");

                    clientJ.put("effect",effects);
                    Log.e("ERR7","1");


                    for (int j = 0; j < SingleDateOfferBooking.offerClientsModels.get(0).getServiceDetails().size(); j++) {
                        JSONObject servic = new JSONObject();
                        Log.e("ERR8 "+j,"1");

                        servic.put("bdb_ser_sup_id",SingleDateOfferBooking.offerClientsModels.get(0).getServiceDetails().get(j).getBdb_ser_sup_id());
                        Log.e("ERR9 "+j,"1");

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

        return clients;
    }




}
