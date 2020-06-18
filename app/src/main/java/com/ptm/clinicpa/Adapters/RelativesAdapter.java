package com.ptm.clinicpa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.DataModel.PatientDataModel;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

public class RelativesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static ArrayList<PatientDataModel> patientDataModels;
    public static Context context;
    public RelativesAdapter(Context context, ArrayList<PatientDataModel> bookingAutomatedBrowseData, boolean isNew){
        this.context=context;
        this.patientDataModels=bookingAutomatedBrowseData;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.relative_layout_ext,viewGroup,false);
        Item item=new Item(row);
        return item;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        try {

            ((Item)viewHolder).client_name.setText(patientDataModels.get(position).getBdb_user_name());
            String age;
            if(patientDataModels.get(position).getBdb_old().equals("0"))
                age=context.getString(R.string.lessThanYear);
            else if(patientDataModels.get(position).getBdb_old().equals("1"))
                age=context.getString(R.string.oneYear);
            else if(patientDataModels.get(position).getBdb_old().equals("2"))
                age=context.getString(R.string.twoYears);
            else
            {
                age=context.getString(R.string.age2)+" "+patientDataModels.get(position).getBdb_old()+" "+context.getString(R.string.years);
            }

            ((Item)viewHolder).age.setText(age);

            String gender =context.getString(R.string.gender);
            if(patientDataModels.get(position).getBdb_gender().equals("0"))
                gender+=context.getString(R.string.male);
            else if(patientDataModels.get(position).getBdb_gender().equals("1"))
                gender+=context.getString(R.string.female);

            ((Item)viewHolder).gender.setText(gender);

            String relation=context.getString(R.string.relationship);
            if(patientDataModels.get(position).getBdb_relation().equals("1"))
                relation+=context.getString(R.string.father);
            else if(patientDataModels.get(position).getBdb_relation().equals("2"))
                relation+=context.getString(R.string.mother);
            else if(patientDataModels.get(position).getBdb_relation().equals("3"))
                relation+=context.getString(R.string.son);
            else if(patientDataModels.get(position).getBdb_relation().equals("4"))
                relation+=context.getString(R.string.husband);
            else if(patientDataModels.get(position).getBdb_relation().equals("5"))
                relation+=context.getString(R.string.daughter);
            else if(patientDataModels.get(position).getBdb_relation().equals("0"))
                relation=context.getString(R.string.me);
            ((Item)viewHolder).Relationship.setText(relation);

            for (int i=0;i<patientDataModels.get(position).getRecords().size();i++)
            {
                addLayout(((Item)viewHolder).myroot,patientDataModels.get(position).getRecords().get(i).getHealth_center_en(),patientDataModels.get(position).getRecords().get(i).getHealth_center_ar(),patientDataModels.get(position).getRecords().get(i).getHealth_record());
            }

            ((Item)viewHolder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=patientDataModels.get(position).getBdb_id();
                    APICall.DeleteFollower(context,id);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void addLayout(final LinearLayout myroot, String serviceName,String serviceName_ar,String number){
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.record_layout, myroot, false);
        TextView emp_name,healthNum;
        emp_name=layout2.findViewById(R.id.healthCntr);
        healthNum=layout2.findViewById(R.id.healthNum);
        healthNum.setText(number);
        if(context.getString(R.string.locale).equals("en"))
            emp_name.setText(serviceName);
        else
            emp_name.setText(serviceName_ar);

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientDataModels.size();
    }
    public class Item extends RecyclerView.ViewHolder {
        TextView client_name, gender, Relationship, delete, edit;
        Space space;

        TextView  age;
        LinearLayout myroot, actions;

        public Item(View itemView) {
            super(itemView);
            myroot = itemView.findViewById(R.id.myroot);
            space = itemView.findViewById(R.id.space);
            gender = itemView.findViewById(R.id.total_price);
            client_name = itemView.findViewById(R.id.client_name);
            Relationship = itemView.findViewById(R.id.booking_place);
            delete = itemView.findViewById(R.id.refuse);
            edit = itemView.findViewById(R.id.time);
            age = itemView.findViewById(R.id.patientName);
            actions = itemView.findViewById(R.id.actions);

        }
    }
}
