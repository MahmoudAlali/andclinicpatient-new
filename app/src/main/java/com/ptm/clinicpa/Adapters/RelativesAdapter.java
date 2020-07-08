package com.ptm.clinicpa.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.AddRelativeActivity;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.CreateRequestActivity;
import com.ptm.clinicpa.Activities.RelativesActivity;
import com.ptm.clinicpa.DataModel.PatientDataModel;
import com.ptm.clinicpa.Dialog.Dialogs;
import com.ptm.clinicpa.Dialog.MyRunnable;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

import static com.ptm.clinicpa.Activities.support.SupportActivity.providerMail;

public class RelativesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static Dialogs getReasonDialog;

    public static ArrayList<PatientDataModel> patientDataModels;
    public static Context context;
    boolean isBooking,isOffer;
    public RelativesAdapter(Context context, ArrayList<PatientDataModel> bookingAutomatedBrowseData, boolean isNew,boolean isOffer){
        this.context=context;
        this.patientDataModels=bookingAutomatedBrowseData;
        isBooking=isNew;
        this.isOffer=isOffer;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.relative_layout_ext,viewGroup,false);
        Item item=new Item(row);
        return item;    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
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

            if(isBooking)
            {
                ((Item)viewHolder).actions.setVisibility(View.GONE);
                ((Item)viewHolder).item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent i = new Intent(context, CreateRequestActivity.class);
                         i.putExtra("sup_id",RelativesActivity.sup_id);
                         i.putExtra("age",patientDataModels.get(position).getBdb_old());
                         i.putExtra("relation",patientDataModels.get(position).getBdb_relation());
                         i.putExtra("gender",patientDataModels.get(position).getBdb_gender());
                         i.putExtra("client_name",patientDataModels.get(position).getBdb_user_name());
                         if(isOffer)
                         {
                             i.putExtra("is_offer",true);
                             i.putExtra("pack_code",RelativesActivity.pack_code);
                             i.putExtra("longNum",RelativesActivity.longNum);
                             i.putExtra("latNum",RelativesActivity.latNum);
                         }
                        for (int j=0;j<patientDataModels.get(position).getRecords().size();j++)
                        {
                            if(RelativesActivity.center_id.equals(patientDataModels.get(position).getRecords().get(j).getHealth_center_id()))
                                i.putExtra("health_record",patientDataModels.get(position).getBdb_health_record());

                        }

               // RequestProvidersFragment.bdb_booking_period=itemArrayList.get(position).getBdb_booking_period();
                context.startActivity(i);
                    }
                });
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Item)viewHolder).myroot.removeAllViews();
                    }
                });
                for (int i=0;i<patientDataModels.get(position).getRecords().size();i++)
                {
                    if(RelativesActivity.center_id.equals(patientDataModels.get(position).getRecords().get(i).getHealth_center_id()))
                         addLayout(((Item)viewHolder).myroot,patientDataModels.get(position).getRecords().get(i).getHealth_center_en(),patientDataModels.get(position).getRecords().get(i).getHealth_center_ar(),patientDataModels.get(position).getRecords().get(i).getHealth_record(),position,i);
                }
            }
            else
            {
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Item)viewHolder).myroot.removeAllViews();
                    }
                });
                for (int i=0;i<patientDataModels.get(position).getRecords().size();i++)
                {
                    addLayout(((Item)viewHolder).myroot,patientDataModels.get(position).getRecords().get(i).getHealth_center_en(),patientDataModels.get(position).getRecords().get(i).getHealth_center_ar(),patientDataModels.get(position).getRecords().get(i).getHealth_record(),position,i);
                }
            }
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


            ((Item)viewHolder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=patientDataModels.get(position).getBdb_id();
                    APICall.DeleteFollower(context,id);
                }
            });
            ((Item)viewHolder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=patientDataModels.get(position).getBdb_id();
                    String name=patientDataModels.get(position).getBdb_user_name();
                    String gender=patientDataModels.get(position).getBdb_gender();
                    String relation=patientDataModels.get(position).getBdb_relation();
                    String old=patientDataModels.get(position).getBdb_old();
                    Intent i=new Intent(context, AddRelativeActivity.class);
                    i.putExtra("isEditRelative",true);
                    i.putExtra("id",id);
                    i.putExtra("name",name);
                    i.putExtra("gender",gender);
                    i.putExtra("relation",relation);
                    i.putExtra("old",old);
                    context.startActivity(i);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void addLayout(final LinearLayout myroot, String serviceName,String serviceName_ar,String number,final int position,final int index){
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.record_layout, myroot, false);
        TextView emp_name,healthNum;
        ImageView delete =layout2.findViewById(R.id.delete);
        ImageView edit =layout2.findViewById(R.id.edit);
        emp_name=layout2.findViewById(R.id.healthCntr);
        healthNum=layout2.findViewById(R.id.healthNum);
        healthNum.setText(number);
        if(context.getString(R.string.locale).equals("en"))
            emp_name.setText(serviceName);
        else
            emp_name.setText(serviceName_ar);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.dltRelativeHlthRcrd)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String id=patientDataModels.get(position).getRecords().get(index).getId()+"";
                                APICall.DeleteFollowerHealthRecord(context,id);
                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        myroot.removeViewInLayout(layout2);
                                    }
                                });                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });
        final MyRunnable OnClickEditBtn =new MyRunnable()
        {
            @Override
            public void run() {

                String id=patientDataModels.get(position).getRecords().get(index).getId()+"";
                APICall.EditFollowerHealthRecord(context,id,getValue());
            }
        };
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getReasonDialog =new Dialogs(context, R.string.empty, R.string.med_id2, R.string.ok,OnClickEditBtn,true);
                getReasonDialog.show();
            }
        });
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
        LinearLayout myroot, actions,item;

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
            item = itemView.findViewById(R.id.item);

        }
    }
}
