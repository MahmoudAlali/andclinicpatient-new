package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.API.HintArrayAdapter;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.ClientsRelationsViewClass;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;

public class ClientRelationsFragment extends Fragment {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;

    LinearLayout myroot;
    Button done;
    public static  String multi_salon_clients_rel="1";
    public static  String multi_salon_client="1";
    public static ArrayList<String> relations=new ArrayList<>();
    public static ArrayList<ClientsRelationsViewClass> clientRelationView=new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.client_relation_layout, container, false);
                    myroot=view.findViewById(R.id.myroot);
                    done=view.findViewById(R.id.done);
                    ArrayList<String> arrayList=new ArrayList<>();
                    Log.e("clientSize", GroupReservationFragment.clientsViewData.size()+"");
                    for (int j=0;j<GroupReservationFragment.clientsViewData.size();j++){
                        arrayList.add(GroupReservationFragment.clientsViewData.get(j).getClient_name().getText().toString());
                    }
                    for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++){
                        Log.e("clientSize",i+"");
//                        ArrayList<String> arrayList1=new ArrayList<>();
//                        for (int j=0;j<GroupReservationFragment.clientsViewData.size();j++) {
//                            if (GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString().
//                                    equals(GroupReservationFragment.clientsViewData.get(j).getClient_name().getText().toString())) {
////                                arrayList1 = new ArrayList<>();
//                                arrayList1.add(GroupReservationFragment.clientsViewData.get(j).getClient_name().getText().toString());
//                            }
//                        }
                            addLayout(GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString(),
                                arrayList,BeautyMainPage.context,myroot);

                    }



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


                            int alert=0;

                            for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++) {
                                if (GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText().toString().length()!=0)
                                    if (!APICall.checkNumber(GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText().toString(), BeautyMainPage.context)) {
                                        alert=2;
                                        break;
                                    }
                            }

                            for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++) {
//                    Log.e("ClientName", clientsViewData.get(i).getClient_name().getText().toString());

                                if (GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString().isEmpty() || GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
                                        || GroupReservationFragment.clientsViewData.get(i).getAdd_service().getSelectedItemPosition() == 0) {
                                    alert = 1;
                                }
                            }

                            if (alert == 1) {
                                String m=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.complete_all_data);
                                APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), m);
                            }else if(alert==2){

                            }else {


                                    // =------------is hair service go to anthor fragment----------
                                    if (GroupReservationFragment.ishairService.size() > 0) {
                                        fragment = new HairSpecificationsFragment();
                                        fm = getFragmentManager();
                                        fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment, fragment);
                                        fragmentTransaction.commit();
                                    } else {
                                        fragment = new GroupReservationResultFragment();
                                        fm = getFragmentManager();
                                        fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment, fragment);
                                        fragmentTransaction.commit();
                                    }

                            }
                            //----- call group filter for booking -------------
                            APICall.GroupFilterBooking();

                        }
                    });

        return view;
    }



    public static void addLayout(String client, ArrayList<String> namelist, Context context,LinearLayout myroot) {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.client_layout_root, myroot, false);
        TextView client_name =  layout2.findViewById(R.id.client_name);
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
        clientRelationView.add(new ClientsRelationsViewClass(client_name,clientlist));
        myroot.addView(layout2);
    }

}
