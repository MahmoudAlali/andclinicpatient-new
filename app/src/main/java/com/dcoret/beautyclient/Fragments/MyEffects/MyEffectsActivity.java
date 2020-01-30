package com.dcoret.beautyclient.Fragments.MyEffects;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.EffectAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabOne;
import com.dcoret.beautyclient.R;

public class MyEffectsActivity extends AppCompatActivity {
    Spinner select_cat,add_effect;
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

        APICall.getEffectsClient(context,effectAdapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APICall.updateEffectsClient(context, getEffectFilter());
            }
        });


    }

    public static String getEffectFilter(){
        String filter="{\"client_effects\":[";

        for (int i=0;i<APICall.clientEffectModels.size();i++){
        for (int j=0;j<APICall.clientEffectModels.get(i).getEffects().size();j++) {
            if (filter.equals("{\"client_effects\":[")) {
                filter += "{" +
                        "\"bdb_effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+",\n" +
                        "                        \"bdb_client_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_client_id()+",\n" +
                        "                        \"bdb_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+",\n" +
                        "                        \"bdb_effect_client_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_client_id()+"" +
                        "                        }";
            } else {
                filter += "\n,{" +
                        "\"bdb_effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+",\n" +
                        "                        \"bdb_client_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_client_id()+",\n" +
                        "                        \"bdb_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+",\n" +
                        "                        \"bdb_effect_client_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_client_id()+"" +
                        "                        }";
            }
        }
        }

        filter=filter+"]}";
        Log.e("EffectFilter",filter);
        return filter;
    }

}
