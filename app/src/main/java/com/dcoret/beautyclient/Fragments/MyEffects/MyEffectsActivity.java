package com.dcoret.beautyclient.Fragments.MyEffects;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
//        add_effect=findViewById(R.id.add_effect);
        recyclerView=findViewById(R.id.recycleview);


        effectAdapter=new EffectAdapter(BeautyMainPage.context, APICall.clientEffectModels);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(effectAdapter);

        APICall.getEffectsClient(context,effectAdapter);
    }

    public static String getEffectFilter(){
        String filter="\"client_effects\":[";

        for (int i=0;i<APICall.clientEffectModels.size();i++){
           if (i==0) {
               filter = "{" +
                       "\"bdb_effect_id\": 1,\n" +
                       "                        \"bdb_client_id\": 264,\n" +

                       "                        \"bdb_value\": -1,\n" +
                       "                        \"bdb_effect_client_id\": -1"+
                       "                        }";
           }else {

           }
        }

        filter=filter+"]";
        return filter;
    }

}
