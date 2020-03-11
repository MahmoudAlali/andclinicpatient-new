package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.API.Constants;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataModel.ClientEffectModel;
import com.dcoret.beautyclient.DataModel.ClientEffectRequestModel;
import com.dcoret.beautyclient.DataModel.DataOffer;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class GroupEffectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
//    ArrayList<ClientEffectModel> clientEffectModels;
    ArrayList<ClientEffectRequestModel> clientEffectRequestModels;
    static  LinearLayout postionSelected,tmpSelected;
    Boolean isreq;


    public GroupEffectAdapter(Context context, ArrayList<ClientEffectModel> clientEffectModels, Boolean isreq){
        this.context=context;
        this.isreq=isreq;
//        this.clientEffectModels=clientEffectModels;
    }
 public GroupEffectAdapter(Context context, ArrayList<ClientEffectRequestModel> clientEffectRequestModels){
        this.context=context;
        this.isreq=isreq;
        this.clientEffectRequestModels=clientEffectRequestModels;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row;
//        effects_layout
            row = inflater.inflate(R.layout.effects_layout_main_v1, parent, false);
            GroupEffectAdapter.Item item = new GroupEffectAdapter.Item(row);

        return item;
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//        if (isreq){
//            ((Item)holder).client_name.setVisibility(View.VISIBLE);
//            ((Item)holder).client_name.setText(clientEffectRequestModels.get(position).getClient_name());
//        }else {
            ((Item)holder).client_name.setVisibility(View.GONE);
//        }

//        if (APICall.ln.equals("ar")){
//            ((Item)holder).cat_name.setText(clientEffectRequestModels.get(position).getCat_name_ar());
//        }else
//        ((Item)holder).cat_name.setText(clientEffectModels.get(position).getCat_name());

        for (int i=0;i<clientEffectRequestModels.get(position).getClientEffectModels().size();i++){
            addMainlayout(((Item)holder).myroot,clientEffectRequestModels.get(position).getClientEffectModels().get(i),clientEffectRequestModels.get(position).getClient_name());
        }
    }



    @Override
    public int getItemCount() {
        try {
            return clientEffectRequestModels.size();
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

    public static void addMainlayout(final LinearLayout myroot, final ClientEffectModel clientEffectModel,String cname){
        //------- add degrees
        final View layout2;
        layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.effects_layout_main, myroot, false);
        TextView cat_name;
        LinearLayout myroot1;
        cat_name=layout2.findViewById(R.id.cat_name);
        myroot1=layout2.findViewById(R.id.myroot);
        if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
            cat_name.setText(cname+" : "+clientEffectModel.getCat_name_ar());
        else
            cat_name.setText(cname+" : "+clientEffectModel.getCat_name());


        for (int i=0;i<clientEffectModel.getEffects().size();i++){
            addlayout(myroot1,clientEffectModel.getEffects().get(i));
        }




        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
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
                dfive.setBackgroundResource(R.color.colorAccent);
                done.setBackgroundResource(android.R.color.white);
                dtwo.setBackgroundResource(android.R.color.white);
                dthree.setBackgroundResource(android.R.color.white);
                dzero.setBackgroundResource(android.R.color.white);
                dfour.setBackgroundResource(android.R.color.white);
            }
        });

        effect_name=layout2.findViewById(R.id.effect_name);
        if (BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar")) {
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
