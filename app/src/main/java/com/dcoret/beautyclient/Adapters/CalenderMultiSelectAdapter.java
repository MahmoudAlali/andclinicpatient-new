package com.dcoret.beautyclient.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DateClass;
import com.dcoret.beautyclient.DataClass.MultiDateOfferClass;
import com.dcoret.beautyclient.Fragments.MultiDateOfferFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class CalenderMultiSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    Boolean grid=false;
    String items[];
    DataOffer[] offers;
    static  LinearLayout postionSelected,tmpSelected;

    ArrayList<DateClass> dateClasses;
    public CalenderMultiSelectAdapter(Context context, ArrayList<DateClass> dateClasses){
        this.context=context;
        this.dateClasses=dateClasses;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row;

            row = inflater.inflate(R.layout.indvisual_booking_layout, parent, false);
            CalenderMultiSelectAdapter.Item item = new CalenderMultiSelectAdapter.Item(row);

        return item;
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((Item)holder).dayOfMonth.setText(dateClasses.get(position).getDayOfMonth()+"");
        ((Item)holder).dayOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tmpSelected.setBackgroundResource(R.drawable.border_gray);
                }catch (Exception e){
                    e.printStackTrace();
                }
                tmpSelected=((Item)holder).calLayout;
                postionSelected=((Item)holder).calLayout;
                ((Item)holder).calLayout.setBackgroundResource(R.drawable.border_selected);
                ArrayList<MultiDateOfferClass> multiDateOfferClasses=new ArrayList<>();

                ArrayList slList=new ArrayList();
                slList.add("service1");
                slList.add("service2");
//        slList.add("service3");
//        slList.add("service4");

                multiDateOfferClasses.add(new MultiDateOfferClass(slList,"","",""));
                multiDateOfferClasses.add(new MultiDateOfferClass(slList,"","",""));
                multiDateOfferClasses.add(new MultiDateOfferClass(slList,"","",""));
                multiDateOfferClasses.add(new MultiDateOfferClass(slList,"","",""));

                MultiOfferAdapter multiOfferAdapter=new MultiOfferAdapter(context,multiDateOfferClasses);
                LinearLayoutManager manager1 = new LinearLayoutManager(context.getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                MultiDateOfferFragment.review.setLayoutManager(manager1);
                MultiDateOfferFragment.review.setAdapter(multiOfferAdapter);
                multiOfferAdapter.notifyDataSetChanged();

            }
        });
    }



    @Override
    public int getItemCount() {
        try {
            return dateClasses.size();
        }catch (Exception e){
            e.getMessage();
            return 0;
        }

    }
    public static class Item extends RecyclerView.ViewHolder {
        //-------------- variables-----------
        TextView dayOfWeek,dayOfMonth,reserv_offer;
        LinearLayout calLayout;

        public Item(View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayofweek);
            dayOfMonth = itemView.findViewById(R.id.dayofmonth);
            calLayout = itemView.findViewById(R.id.calLayout);
        }
    }



}
