package com.dcoret.beautyclient.Fragments.SingleMultiBooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.dcoret.beautyclient.Fragments.IndividualBooking.BookingIndvidualActivity;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabOne;
import com.dcoret.beautyclient.R;

public class MySingleMultiEffectActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EffectAdapter effectAdapter;
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
        effectAdapter=new EffectAdapter(BeautyMainPage.context, APICall.clientEffectModels,false);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(effectAdapter);

        String filter=
                "       { \"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \""+ BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"rel\": \""+"0"+"\",\n" +
                "            \"multi_salon_client\": \"1\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                {\n" ;


                        for (int i=0;i<MultiIndividualBookingReservationFragment.servicesForClientGroups.size();i++){
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

        APICall.getMyEffectsClient(context,filter,effectAdapter);


        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
//                     if (position!=0){
//                         postions.add(position-1);
//                     }
        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f=getEffectFilter();
                Log.e("filter",getfilter(f));
                Log.e("Effectfilter",f);

                Intent intent=new Intent(context, MultiBookingIndividualResultActivity.class);
                intent.putExtra("filter",getfilter(f));
                startActivity(intent);
            }
        });
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

            clientf = " \"clients\": [\n" +
                    "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + date + "\",\"services\": [ ";
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
                    clientf = clientf + "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                    String services = "";
//                        for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {


                    clientf += "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }]" +
                            ",\"effect\":["+eff_filter+"]\n}";

                } else {

                    clientf = clientf + "\t\t,{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
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
}
