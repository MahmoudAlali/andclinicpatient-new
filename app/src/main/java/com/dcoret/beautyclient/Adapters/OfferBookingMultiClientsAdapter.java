package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.DataModel.OfferClientsModel;
import com.dcoret.beautyclient.DataModel.SelectDateOfferModel;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class OfferBookingMultiClientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    String items[];
    ArrayList<OfferClientsModel> strings;
    Boolean layout;
    public  static ArrayList<SelectDateOfferModel> selectDateOfferModels=new ArrayList<SelectDateOfferModel>();

    public OfferBookingMultiClientsAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }

    public OfferBookingMultiClientsAdapter(Context context, ArrayList<OfferClientsModel> strings){
        this.context=context;
        this.strings=strings;
    }

    public OfferBookingMultiClientsAdapter(Context context, String items[], Boolean layout){
        this.context=context;
        this.items=items;
        this.layout=layout;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.offer_booking_multi_adapters, parent, false);
        OfferBookingMultiClientsAdapter.Item item=new OfferBookingMultiClientsAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


            ((Item)holder).checkme.setVisibility(View.VISIBLE);




        ((Item)holder).in_grp_offer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    String name = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("bdb_name", null);
//                    String mobile = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("bdb_mobile", null);


                    for (int i=0;i<selectDateOfferModels.size();i++){
                        selectDateOfferModels.get(i).getCheckme().setChecked(false);
                            selectDateOfferModels.get(i).getPhone_number().setText("");
                            selectDateOfferModels.get(i).getCname().setText("");
                            selectDateOfferModels.get(i).getPhone_number().setEnabled(true);
                            selectDateOfferModels.get(i).getCname().setEnabled(true);

                    }
                    ((Item) holder).in_grp_offer.setChecked(true);
                    ((Item)holder).name.setEnabled(false);
                    ((Item)holder).phone_number.setEnabled(false);
//                    ((Item)holder).phone_number.setText(mobile);
                    APICall.detailsUser2(context,((Item)holder).name,((Item)holder).phone_number);
                }else {
                    ((Item)holder).name.setEnabled(true);
                    ((Item)holder).phone_number.setEnabled(true);
                    ((Item)holder).name.setText("");
                    ((Item)holder).phone_number.setText("");
                }
            }
        });


        selectDateOfferModels.add(new SelectDateOfferModel(((Item)holder).name,((Item)holder).phone_number,((Item) holder).in_grp_offer,position));

        for (int i=0;i<strings.get(position).getServiceDetails().size();i++){
            addLayout2(strings.get(position).getServiceDetails().get(i).getBdb_name_ar(),context,((Item)holder).show_services);
        }


    }

    private static void addLayout2(String s,Context context, final LinearLayout layout) {

        final View layout2;

//        ArrayList<GroupBookingModel.ServicesModel> servicesModels1=new ArrayList<>();
        layout2 = LayoutInflater.from(context).inflate(R.layout.show_emp_layout_v3, layout, false);

        TextView emp_name =  layout2.findViewById(R.id.emp_name);
//        ImageView delete =  layout2.findViewById(R.id.delete);

        emp_name.setText(s);

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                layout.removeView(layout2);
//            }
//        });
//        API.groupBookingModels.add(new GroupBookingModel(client_name,client_no,age,))
//        servicesModels.add(new GroupBookingModel.ServicesModel(API.supplierSerSups.get(addService.getSelectedItemPosition()-1).getBdb_ser_id(),API.supplierSerSups.get(addService.getSelectedItemPosition()-1).getBdb_time()));

        layout.addView(layout2);

    }


    @Override
    public int getItemCount() {
        Log.e("StringSize",strings.size()+"");
        return strings.size();
    }
    public class Item extends RecyclerView.ViewHolder {

        EditText name,phone_number;
        LinearLayout show_services;
        CoordinatorLayout checkme;
        CheckBox in_grp_offer;
        public Item(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.client_name);
            phone_number=itemView.findViewById(R.id.phone_number);
            show_services=itemView.findViewById(R.id.show_service);
            checkme=itemView.findViewById(R.id.checkme);

            in_grp_offer=itemView.findViewById(R.id.in_grp_offer);




        }
    }

}