package com.ptm.clinicpa.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Constants;
import com.ptm.clinicpa.Adapters.GroupEffectAdapter;
import com.ptm.clinicpa.DataModel.ClientEffectModel;
import com.ptm.clinicpa.DataModel.ClientEffectRequestModel;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddEffectsToRequestActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GroupEffectAdapter effectAdapter;
    Button Ok;

    public  static Context context;
    public  static LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
        Ok=findViewById(R.id.update);
        root=findViewById(R.id.root);
//        recyclerView=findViewById(R.id.recycleview);
//        effectAdapter=new GroupEffectAdapter(BeautyMainPage.context, APICall.clientEffectRequestModels);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

/*        String filter= "  { \"clients\": [\n" +
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
                "}\n";*/
        Log.e("Effectfilter",getEffectClients());


        APICall.getClientsEffects(context,getEffectClients());


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
        Ok.setText(R.string.next);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEffects();
                Log.e("Effectfilter",getEffects().toString());
                Log.e("getClients123",getClients(1)+"tostring");
                APICall.addBookingRequest(freeBookingFragment.lat+"",freeBookingFragment.lng+"", CreateRequestActivity.sup_id+"",freeBookingFragment.Place+"","",CreateRequestActivity.is_group_booking,getClients(1),context);
                //Intent intent=new Intent(context, GroupReservationResultActivity.class);
//                intent.putExtra("filter",getfilter(f));
             //   startActivity(intent);
            }
        });
    }


    static String effectFilter="";
    public static String getEffectClients(){
        String clients = "{\"clients\":[";
        try {

            for (int i = 0; i< CreateRequestActivity.clientsArrayList.size(); i++) {
                if (i == 0) {
                    clients += "{\"client_name\":\"" +
                            CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString() + "\",\"client_phone\":\""+
                            CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText()+"\",\"is_current_user\":"+
                            "1"+
                            ",\"date\": \""+ CreateRequestActivity.add_date.getText()+"\""+
//                            ",\"rel\":\"0\",\"is_adult\":1 "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"{\"client_name\":\"" + CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString() + "\",\"client_phone\":\""+CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText()+"\",\"is_current_user\":"+"1"
//                            +",\"rel\":\"0\",\"is_adult\":1"+
                            +",\"date\": \""+CreateRequestActivity.add_date.getText()+"\""
                            +",\"services\":[\n";

                }
                Log.e("SIZE",""+CreateRequestActivity.clientsArrayList.get(i).getServicesModels().size());

                for (int j = 0; j < CreateRequestActivity.clientsArrayList.get(i).getServicesModels().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id() +"}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id()+ "}\n";
                    }
                    Log.e("Ser_Id",CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
                }
                if (CreateRequestActivity.clientsArrayList.size()==1){
                    clients=clients+"]}";
                }else if (i==CreateRequestActivity.clientsArrayList.size()-1){
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


    ArrayList<String> getEffects(){
        Log.e("SizeClientReq","Size:"+APICall.clientEffectRequestModels.size());
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
        Log.e("effArrSize","Size:"+effectsArr.size());


        return effectsArr;
    }

    public static String getClients(){
        String clients = "[";
        try {

            for (int i=0;i<CreateRequestActivity.clientsArrayList.size();i++) {
                if (i == 0) {
                    clients+= "{\"client_name\":\"" +
                            CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString() + "\",\"client_phone\":\""+
                            CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText()+"\",\"is_current_user\":"+
                            "1"+
                            ",\"start_date\": \""+CreateRequestActivity.add_date.getText()+"\""+
                            //",\"old\": "+(CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()-1)+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                            ",\"services\":[";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"{\"client_name\":\"" + CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString() + "\",\"client_phone\":\""+CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText()+"\",\"is_current_user\":"+"1"
                    +",\"start_date\": \""+CreateRequestActivity.add_date.getText()+"\"";
                       //     ",\"old\": "+CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()
                         //   +",\"services\":[";

                }
              //  Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < CreateRequestActivity.clientsArrayList.get(i).getServicesModels().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"bdb_ser_sup_id\":" +CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id() + "}";
                    } else {
                        clients = clients + ",{\"bdb_ser_sup_id\":" + CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id()+ "}";
                    }
                    Log.e("Ser_Id",CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
                }
                String eff="";
                Log.e("index-i"+i,"index-i"+i);
                try {
                    eff=effectsArr.get(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (CreateRequestActivity.clientsArrayList.size()==1){

                    clients=clients+"],\"effect\":["+eff+"]}";
                }else if (i==CreateRequestActivity.clientsArrayList.size()-1){
                    clients=clients+"],\"effect\":["+eff+"]}";

                }else {
                    clients=clients+"],\"effect\":["+eff+"]},";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]";

            Log.e("clients",clients);
        return clients;
    }
    public static JSONArray getClients(int c)
    {
        JSONArray clients =new JSONArray();
        try {

            for (int i=0;i<CreateRequestActivity.clientsArrayList.size();i++) {

                JSONObject client = new JSONObject();

                client.put("client_name",CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString());
                client.put("client_phone",CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText().toString());
                client.put("start_date",CreateRequestActivity.add_date.getText());
                client.put("is_current_user","1");
               // client.put("old",(CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()-1));
                JSONArray services=new JSONArray() ;
                JSONObject effects=new JSONObject(effectsArr.get(i)) ;

                client.put("effect",effects);

                //  Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < CreateRequestActivity.clientsArrayList.get(i).getServicesModels().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    JSONObject servic = new JSONObject();

                    servic.put("ser_id",CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
                    services.put(servic);
                }
                client.put("services",services);

                String eff="";
                Log.e("index-i"+i,"index-i"+i);
                try {
                    eff=effectsArr.get(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
               /* if (CreateRequestActivity.clientsArrayList.size()==1){

                    clients=clients+"],\"effect\":["+eff+"]}";
                }else if (i==CreateRequestActivity.clientsArrayList.size()-1){
                    clients=clients+"],\"effect\":["+eff+"]}";

                }else {
                    clients=clients+"],\"effect\":["+eff+"]},";

                }*/

               clients.put(client);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //clients=clients+"]";

            Log.e("clients",clients.toString());
        return clients;
    }

   /* public static JSONArray getClients(Context c)
    {
        JSONArray result=new JSONArray() ;



        for (int i=0;i<CreateRequestActivity.clientsArrayList.size();i++) {
            JSONObject client = new JSONObject();

          //  if (i == 0) {
                client.put("client_name",CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString());
                client.put("client_phone",CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText().toString());
                client.put("is_current_user","1");
                client.put("old",(CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()-1));
                JSONArray services=new JSONArray() ;
                JSONArray effects=new JSONArray() ;

              *//*  clients+= "{\"client_name\":\"" +
                        CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString() + "\",\"client_phone\":\""+
                        CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText()+"\",\"is_current_user\":"+
                        "1"+
                        ",\"start_date\": \""+CreateRequestActivity.add_date.getText()+"\""+
                        ",\"old\": "+(CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()-1)+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                        ",\"services\":[";
            } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                clients = clients+"{\"client_name\":\"" + CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString() + "\",\"client_phone\":\""+CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText()+"\",\"is_current_user\":"+"1"
                        +",\"start_date\": \""+CreateRequestActivity.add_date.getText()+"\""+
                        ",\"old\": "+CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()
                        +",\"services\":[";

            }
            //  Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
*//*
            for (int j = 0; j < CreateRequestActivity.clientsArrayList.get(i).getServicesModels().size(); j++) {
                JSONObject servic = new JSONObject();

                servic.put("bdb_ser_sup_id",CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
                services.put(servic);
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
               *//* if (j == 0) {
                    clients = clients + "{\"bdb_ser_sup_id\":" +CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id() + "}";
                } else {
                    clients = clients + ",{\"bdb_ser_sup_id\":" + CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id()+ "}";
                }
                Log.e("Ser_Id",CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
         *//*   }
            String eff="";
            Log.e("index-i"+i,"index-i"+i);
            try {
                eff=effectsArr.get(i);
            }catch (Exception e){
                e.printStackTrace();
            }
            if (CreateRequestActivity.clientsArrayList.size()==1){

                clients=clients+"],\"effect\":["+eff+"]}";
            }else if (i==CreateRequestActivity.clientsArrayList.size()-1){
                clients=clients+"],\"effect\":["+eff+"]}";

            }else {
                clients=clients+"],\"effect\":["+eff+"]},";

            }

        }


        return result;
    }
*/
}
