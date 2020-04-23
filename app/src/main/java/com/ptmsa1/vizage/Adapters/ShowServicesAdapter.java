package com.ptmsa1.vizage.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.DataModel.OfferClientsModel;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class ShowServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    String items[];
    ArrayList<OfferClientsModel> strings;
    Boolean layout;
    ArrayList<String> services;
    public  static ArrayList<OfferClientsModel> offerClientsModels=new ArrayList<>();

//    public ShowServicesAdapter(Context context,ArrayList<String> services){
//        this.context=context;
//        this.services=services;
//    }

    public ShowServicesAdapter(Context context, ArrayList<OfferClientsModel> offerClientsModels){
        this.context=context;
        this.offerClientsModels=offerClientsModels;
    }

    public ShowServicesAdapter(Context context, String items[], Boolean layout){
        this.context=context;
        this.items=items;
        this.layout=layout;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row;
        row = inflater.inflate(R.layout.show_emp_layout_v2, parent, false);
        ShowServicesAdapter.Item item=new ShowServicesAdapter.Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

//        selectDateOfferModels.add(new SelectDateOfferModel,((Item)holder).phone_number,position));
//        for (int i=0;i<offerClientsModels.get(position).getServiceDetails().size();i++){
//            addLayout2(offerClientsModels.get(position).getServiceDetails().get(i).getBdb_name_ar(),context,((Item)holder).show_services);
       if (context.getResources().getString(R.string.locale).equals("ar")) {
           ((Item) holder).name.setText(offerClientsModels.get(0).getServiceDetails().get(position).getBdb_name_ar());
       }else {
           ((Item) holder).name.setText(offerClientsModels.get(0).getServiceDetails().get(position).getBdb_ser_name_en());
       }
//        }

    }

    private static void addLayout2(String s,Context context, final LinearLayout layout) {

        final View layout2;

//        ArrayList<GroupBookingModel.ServicesModel> servicesModels1=new ArrayList<>();
        layout2 = LayoutInflater.from(context).inflate(R.layout.show_emp_layout_v2, layout, false);

        Button emp_name =  layout2.findViewById(R.id.emp_name);
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
        Log.e("StringSize",offerClientsModels.size()+"");
        try {
            return offerClientsModels.get(0).getServiceDetails().size();
        }catch (Exception e){
            return 0;
        }
    }
    public class Item extends RecyclerView.ViewHolder {

        TextView name,phone_number;
        LinearLayout show_services;
        public Item(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.emp_name);
//            phone_number=itemView.findViewById(R.id.phone_number);
//
//            show_services=itemView.findViewById(R.id.show_service);




        }
    }

}