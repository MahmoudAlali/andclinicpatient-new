package com.ptmsa1.clinicclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.clinicclient.API.Constants;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.DataModel.ClientEffectModel;
import com.ptmsa1.clinicclient.DataModel.ClientEffectRequestModel;
import com.ptmsa1.clinicclient.DataModel.DataOffer;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.ListHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
    public static Boolean checkClick=false;

    ArrayList<ClientEffectModel> clientEffectModels;
    ArrayList<ClientEffectRequestModel> clientEffectRequestModels;
    static  LinearLayout postionSelected,tmpSelected;
    Boolean isreq;


    public EffectAdapter(Context context, ArrayList<ClientEffectModel> clientEffectModels,Boolean isreq){
        this.context=context;
        this.isreq=isreq;
        this.clientEffectModels=clientEffectModels;
    }
 public EffectAdapter(Context context, ArrayList<ClientEffectRequestModel> clientEffectRequestModels){
        this.context=context;
        this.isreq=isreq;
        this.clientEffectModels=clientEffectModels;
    }



    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {





            LayoutInflater inflater = LayoutInflater.from(context);
            View row;
//        effects_layout
            row = inflater.inflate(R.layout.effects_layout_main, parent, false);
            EffectAdapter.Item item = new EffectAdapter.Item(row);
        View convertview = inflater.inflate(R.layout.effects_layout_main, parent, false);

        ListHolder holder = new ListHolder(convertview);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final ListHolder holder, final int position) {
//        if (isreq){
//            (holder).client_name.setVisibility(View.VISIBLE);
//        }else {
//            (holder).client_name.setVisibility(View.GONE);
//        }

        if (BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar")){
            (holder).cat_name.setText(clientEffectModels.get(position).getCat_name_ar());
        }else
        (holder).cat_name.setText(clientEffectModels.get(position).getCat_name());

        for (int i=0;i<clientEffectModels.get(position).getEffects().size();i++){
            addlayout((holder).myroot,clientEffectModels.get(position).getEffects().get(i));
        }
    }
    private HashMap<Integer, Boolean> isChecked = new HashMap<>();
    public class ListHolder extends RecyclerView.ViewHolder {
        //        CheckBox cb_product;
        TextView cat_name,client_name;
        LinearLayout myroot;
        public ListHolder(View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.cat_name);
            client_name = itemView.findViewById(R.id.client_name);
            myroot = itemView.findViewById(R.id.myroot);

        }

    }


    @Override
    public int getItemCount() {
        try {
            return clientEffectModels.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {
        //-------------- variables-----------
        TextView cat_name,client_name;
        LinearLayout myroot;

        public Item(View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.cat_name);
            client_name = itemView.findViewById(R.id.client_name);
            myroot = itemView.findViewById(R.id.myroot);
//            calLayout = itemView.findViewById(R.id.calLayout);
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
        }



//        dzero.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                effects.setBdb_value(Constants.effectValues[0]);
////                effects.setBdb_effect_client_id("0");
//                dzero.setBackgroundResource(R.color.colorAccent);
//                done.setBackgroundResource(android.R.color.transparent);
//                dtwo.setBackgroundResource(android.R.color.transparent);
//                dthree.setBackgroundResource(android.R.color.transparent);
//                dfour.setBackgroundResource(android.R.color.transparent);
//                dfive.setBackgroundResource(android.R.color.transparent);
//            }
//        });



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


}
