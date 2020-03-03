package com.dcoret.beautyclient.Fragments.MyEffects;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.EffectAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataModel.ClientEffectModel;
import com.dcoret.beautyclient.R;

public class MyEffectsActivity extends AppCompatActivity {
    Spinner select_cat,add_effect;
    RecyclerView recyclerView;
    EffectAdapter effectAdapter;
    Button update;
    public static LinearLayout root;

    static Context context;
    static int [][] effectsImgs =
            {
                    {R.drawable.e1_1,R.drawable.e1_2,R.drawable.e1_3,R.drawable.e1_4,R.drawable.e1_5},
                    {R.drawable.e1_1,R.drawable.e1_2,R.drawable.e1_3,R.drawable.e1_4,R.drawable.e1_5},
                    {R.drawable.e2_1,R.drawable.e2_2,R.drawable.e2_3,R.drawable.e2_4,R.drawable.e2_5},
                    {R.drawable.e3_1,R.drawable.e3_2,R.drawable.e3_3,R.drawable.e3_4,R.drawable.e3_5},
                    {R.drawable.e4_1,R.drawable.e4_2,R.drawable.e4_3,R.drawable.e4_4,R.drawable.e4_5},
                    {R.drawable.e5_1,R.drawable.e5_2,R.drawable.e5_3,R.drawable.e5_4,R.drawable.e5_5},
                    {R.drawable.e6_1,R.drawable.e6_2,R.drawable.e6_3,R.drawable.e6_4,R.drawable.e6_5},
                    {R.drawable.e18_1,R.drawable.e18_2,R.drawable.e18_3,R.drawable.e18_4,R.drawable.e18_5},
                    {R.drawable.e8_1,R.drawable.e8_2,R.drawable.e8_3,R.drawable.e8_4,R.drawable.e8_5},
                    {R.drawable.e8_1,R.drawable.e8_2,R.drawable.e8_3,R.drawable.e8_4,R.drawable.e8_5},
                    {R.drawable.e8_1,R.drawable.e8_2,R.drawable.e8_3,R.drawable.e8_4,R.drawable.e8_5},
                    {R.drawable.e11_1,R.drawable.e11_2,R.drawable.e11_3,R.drawable.e11_4,R.drawable.e11_5},
                    {R.drawable.e12_1,R.drawable.e12_2,R.drawable.e12_3,R.drawable.e12_4,R.drawable.e12_5},
                    {R.drawable.e13_1,R.drawable.e13_2,R.drawable.e13_3,R.drawable.e13_4,R.drawable.e13_5},
                    {R.drawable.e14_1,R.drawable.e14_2,R.drawable.e14_3,R.drawable.e14_4,R.drawable.e14_5},
                    {R.drawable.e15_1,R.drawable.e15_2,R.drawable.e15_3,R.drawable.e15_4,R.drawable.e15_5},
                    {R.drawable.e8_1,R.drawable.e8_2,R.drawable.e8_3,R.drawable.e8_4,R.drawable.e8_5},
                    {R.drawable.e17_1,R.drawable.e17_2,R.drawable.e17_3,R.drawable.e17_4,R.drawable.e17_5},
                    {R.drawable.e18_1,R.drawable.e18_2,R.drawable.e18_3,R.drawable.e18_4,R.drawable.e18_5},
                    {R.drawable.e8_1,R.drawable.e8_2,R.drawable.e8_3,R.drawable.e8_4,R.drawable.e8_5},
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        root=findViewById(R.id.root);
//        recyclerView=findViewById(R.id.recycleview);


//        effectAdapter=new EffectAdapter(BeautyMainPage.context, APICall.clientEffectModels,false);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

        APICall.getEffectsClient(context,effectAdapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APICall.updateEffectsClient(context, getEffectFilter());
            }
        });


    }
    public static void addCatLayout(final LinearLayout myroot,ClientEffectModel clientEffectModel){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout_main, myroot, false);
        TextView cat_name;
        LinearLayout myroot2;

        cat_name=layout2.findViewById(R.id.cat_name);
        myroot2=layout2.findViewById(R.id.myroot);

        if (context.getResources().getString(R.string.locale).equals("ar")){
            cat_name.setText(clientEffectModel.getCat_name_ar());
        }else
            cat_name.setText(clientEffectModel.getCat_name());

        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
        Log.e("Effects_cat",clientEffectModel.getCat_name());
        for (int i=0;i<clientEffectModel.getEffects().size();i++) {
            Log.e("Effects_name",clientEffectModel.getEffects().get(i).getBdb_effect_name_ar());
            addlayout(myroot2, clientEffectModel.getEffects().get(i));
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
            one.setImageResource(effectsImgs[id][0]);
            two.setImageResource(effectsImgs[id][1]);
            three.setImageResource(effectsImgs[id][2]);
            four.setImageResource(effectsImgs[id][3]);
            five.setImageResource(effectsImgs[id][4]);
        }


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
