package com.ptmsa1.vizage.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.API.HintArrayAdapter;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.ClientsRelationsViewClass;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class OtherRelationActivity extends AppCompatActivity {

    LinearLayout myroot;
    Button done;
    Context context;
    CheckBox multi_salon_client_check,multi_salon_client_rel_check;
    public static  String multi_salon_clients_rel="1";
    public static  String multi_salon_client="1";
    public static ArrayList<String> relations=new ArrayList<>();
    public static ArrayList<ClientsRelationsViewClass> clientRelationView=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_relation_layout);
        myroot=findViewById(R.id.myroot);
        done=findViewById(R.id.done);
        done=findViewById(R.id.done);
        multi_salon_client_check=findViewById(R.id.multi_salon_client);
        multi_salon_client_rel_check=findViewById(R.id.multi_salon_rel);
        context=this;
        ArrayList<String> arrayList=new ArrayList<>();
        Log.e("clientSize", GroupReservationOthersFragment.clientsViewData.size()+"");
        for (int j=0;j<GroupReservationOthersFragment.clientsViewData.size();j++){
            arrayList.add(GroupReservationOthersFragment.clientsViewData.get(j).getClient_name().getText().toString());
        }
        for (int i=0;i<GroupReservationOthersFragment.clientsViewData.size();i++){
            Log.e("clientSize",i+"");
//                        ArrayList<String> arrayList1=new ArrayList<>();
//                        for (int j=0;j<GroupReservationOthersFragment.clientsViewData.size();j++) {
//                            if (GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString().
//                                    equals(GroupReservationOthersFragment.clientsViewData.get(j).getClient_name().getText().toString())) {
////                                arrayList1 = new ArrayList<>();
//                                arrayList1.add(GroupReservationOthersFragment.clientsViewData.get(j).getClient_name().getText().toString());
//                            }
//                        }
            addLayout(GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString(),
                    arrayList, BeautyMainPage.context,myroot,i);

        }

        multi_salon_client_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    multi_salon_client="0";
                }else {
                    multi_salon_client="1";
                }
            }
        });

        multi_salon_client_rel_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!multi_salon_client_check.isChecked()){
                        multi_salon_clients_rel="1";
                    }else {
                        multi_salon_clients_rel="0";
                    }
                }
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relations.clear();
                multi_salon_clients_rel="1";
                for (int i=0;i<clientRelationView.size();i++){

                    //                                relations.add()

                    if (clientRelationView.get(i).getClient_list().getSelectedItemPosition()!=0){
                        relations.add("1");
                    }else {
                        relations.add("0");
                    }

                }

                Boolean check=true;

                for (int i=0;i<clientRelationView.size();i++){
                    if (clientRelationView.get(i).getClient_list().getSelectedItemPosition()==0){
                        check=false;
                    }
                }
                int alert=0;

                for (int i=0;i<GroupReservationOthersFragment.clientsViewData.size();i++) {
                    if (GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText().toString().length()!=0)
                        if (!APICall.checkNumber(GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText().toString(), BeautyMainPage.context)) {
                            alert=2;
                            break;
                        }
                }

                for (int i=0;i<GroupReservationOthersFragment.clientsViewData.size();i++) {
//                    Log.e("ClientName", clientsViewData.get(i).getClient_name().getText().toString());

                    if (GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString().isEmpty() || GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
                            || GroupReservationOthersFragment.clientsViewData.get(i).getAdd_service().getSelectedItemPosition() == 0) {
                        alert = 1;
                    }
                }

                if (alert == 1) {
                    String m=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.complete_all_data);
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), m);
                }else if(alert==2){

                }else {


                    // =------------is hair service go to anthor fragment----------
//                    if (GroupReservationOthersFragment.ishairService.size() > 0) {
//                        fragment = new HairSpecificationsFragment();
//                        fm = getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
//                    } else {
//                        fragment = new GroupReservationResultFragment();
//                        fm = getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
//                    }
                    if (multi_salon_clients_rel.equals("0")) {

                        APICall.showSweetDialog(context,"","PLease select the relations between clients");
                    }else {
                        if (check) {
                            Intent intent = new Intent(context, AlterGroupOtherReservationResult.class);
                            startActivity(intent);
                        }else {
                                APICall.showSweetDialog(context,"","Select Relation Between Clients");
                            }
                    }
                }
                //----- call group filter for booking -------------
                APICall.GroupFilterBookingforOther();

            }
        });

    }
    public static void addLayout(String client, ArrayList<String> namelist, Context context, LinearLayout myroot,final int pos) {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.client_layout_root, myroot, false);
        final TextView client_name =  layout2.findViewById(R.id.client_name);
        Spinner clientlist = layout2.findViewById(R.id.clientlist);
        ArrayList<String> arrayList=new ArrayList<>();
        client_name.setText(client);
        arrayList.add("Select Relation");
        for (int i=0;i<namelist.size();i++){
            if (!client_name.getText().toString().equals(namelist.get(i).toString())){
                Log.e("ObjectArray1",client_name.getText()+"");
                Log.e("ObjectArray2",namelist.get(i)+"");
                arrayList.add(namelist.get(i));
//                adapter.remove(adapter.getItem(i));
            }
        }
        HintArrayAdapter adapter=new HintArrayAdapter(context,0);
        adapter.addAll(arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        for (int i=0;i<adapter.getCount();i++){
//            if (client_name.getText().toString().equals(adapter.getItem(i).toString())){
//                Log.e("Object",adapter.getItem(i)+"");
//                adapter.remove(adapter.getItem(i));
//            }
//        }
        clientlist.setAdapter(adapter);
        clientlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    int cposition = 0;
                    for (int i=0;i<GroupReservationOthersFragment.clientsViewData.size();i++){
                        if (GroupReservationOthersFragment.clientsViewData.get(i).equals(client_name.getText().toString())){
                            cposition=i;
                        }
                    }
                    try {
                        GroupReservationOthersFragment.clientsViewData.get(pos).setRel(GroupReservationOthersFragment.clientsViewData.get(cposition).getPhone_number().getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        clientRelationView.add(new ClientsRelationsViewClass(client_name,clientlist));
        myroot.addView(layout2);
    }

}
