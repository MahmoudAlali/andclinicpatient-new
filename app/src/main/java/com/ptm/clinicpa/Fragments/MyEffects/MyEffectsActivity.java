package com.ptm.clinicpa.Fragments.MyEffects;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Constants;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Adapters.EffectAdapter;
import com.ptm.clinicpa.DataModel.ClientEffectModel;
import com.ptm.clinicpa.R;

public class MyEffectsActivity extends AppCompatActivity {
    EffectAdapter effectAdapter;
    public static Button update;
    public static LinearLayout root;

    static Context context;

    public static Boolean checkClick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;
        APICall.clientEffectModels.clear();

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        root=findViewById(R.id.root);
//        recyclerView=findViewById(R.id.recycleview);

        update.setEnabled(false);

//        effectAdapter=new EffectAdapter(BeautyMainPage.context, APICall.clientEffectModels,false);
//        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(effectAdapter);

        APICall.getEffectsClient(context,effectAdapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkClick) {
                    APICall.updateEffectsClient(context, getEffectFilter());
                }
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
//        else if (effects.getBdb_value().equals(Constants.effectValues[5])){
//            dfive.setBackgroundResource(R.color.colorAccent);
//        }




//        dzero.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                effects.setBdb_value(Constants.effectValues[0]);
////                effects.setBdb_effect_client_id("0");
//                if (!effects.getBdb_value().equals(Constants.effectValues[0])){
//                    checkClick=true;
//                }
//                dzero.setBackgroundResource(R.color.colorAccent);
//                done.setBackgroundResource(android.R.color.transparent);
//                dtwo.setBackgroundResource(android.R.color.transparent);
//                dthree.setBackgroundResource(android.R.color.transparent);
//                dfour.setBackgroundResource(android.R.color.transparent);
//                dfive.setBackgroundResource(android.R.color.transparent);
//
//            }
//        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!effects.getBdb_value().equals(Constants.effectValues[0])){
                    checkClick=true;
                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[0]);
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
                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[1]);
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
                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[2]);
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
                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[3]);
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
                    update.setEnabled(true);

                }
                effects.setBdb_value(Constants.effectValues[4]);
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





    public static String getEffectFilter(){
        String filter="{\"client_effects\":[";

        for (int i=0;i<APICall.clientEffectModels.size();i++){

            for (int j=0;j<APICall.clientEffectModels.get(i).getEffects().size();j++) {
                Log.e(APICall.clientEffectModels.get(i).getCat_name_ar(),"is"+APICall.clientEffectModels.get(i).getEffects().size());

                if (filter.equals("{\"client_effects\":[")) {
                    filter += "{" +
                            "\"bdb_effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+",\n" +
                            "                        \"bdb_client_id\": "+BeautyMainPage.bdb_id+",\n" +
                            "                        \"bdb_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+",\n" +
                            "                        \"bdb_effect_client_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_client_id()+"" +
                            "                        }";
                } else {
                    filter += "\n,{" +
                            "\"bdb_effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+",\n" +
                            "                        \"bdb_client_id\": "+BeautyMainPage.bdb_id+",\n" +
                            "                        \"bdb_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+",\n" +
                            "                        \"bdb_effect_client_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_client_id()+"" +
                            "                        }";
                }
            }
            Log.e("EffectFilter"+i,filter);

        }

        filter=filter+"]}";
        Log.e("EffectFilter11",filter);
        return filter;
    }

}
