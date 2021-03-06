package com.ptm.clinicpa.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.EffectAdapter;
import com.ptm.clinicpa.Activities.TabOne;
import com.ptm.clinicpa.Fragments.MyEffects.MyEffectsActivity;
import com.ptm.clinicpa.R;

public class MyIndEffectsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EffectAdapter effectAdapter;
    Button update;

    public static Context context;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects_v1);
        context=this;
        EffectAdapter.checkClick=false;

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        recyclerView=findViewById(R.id.recycleview);
        effectAdapter=new EffectAdapter(BeautyMainPage.context, APICall.clientEffectModels,false);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(effectAdapter);

        String filter= "  { \"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \""+ BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                {\n" +
                "                    \"ser_id\": "+ TabOne.ser_id+"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";

        APICall.getMyEffectsClient(context,filter,effectAdapter);





        String effectFilter="";
//                "{\"effect_id\": 3,\"effect_value\": 1}";

        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEffectFilter();
                Log.e("effectsCheck","is"+EffectAdapter.checkClick);
                if (EffectAdapter.checkClick){
                    Intent intent = new Intent(context, BookingIndvidualActivity.class);
                    APICall.showUpdateEffectsDialog(context,intent,MyEffectsActivity.getEffectFilter());

//                    startActivity(intent);
                }else {
                    Intent intent = new Intent(context, BookingIndvidualActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    static String effectFilter="";
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
}
