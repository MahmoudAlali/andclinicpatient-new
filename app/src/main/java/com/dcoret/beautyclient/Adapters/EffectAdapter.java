package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataModel.ClientEffectModel;
import com.dcoret.beautyclient.DataModel.ClientEffectRequestModel;
import com.dcoret.beautyclient.DataModel.DataOffer;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.ListHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
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
        TextView effect_name;
        final LinearLayout dzero,done,dtwo,dthree,dfour,dfive;
        dzero=layout2.findViewById(R.id.dzereo);
        done=layout2.findViewById(R.id.d_one);
        dtwo=layout2.findViewById(R.id.dtwo);
        dthree=layout2.findViewById(R.id.dthree);
        dfour=layout2.findViewById(R.id.dfour);
        dfive=layout2.findViewById(R.id.dfive);


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
