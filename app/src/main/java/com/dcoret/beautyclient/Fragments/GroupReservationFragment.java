package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.ClientsViewData;
import com.dcoret.beautyclient.DataClass.CustomListAdapter;
import com.dcoret.beautyclient.DataClass.CustomListAdapterWithoutImage;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.SerchGroupBookingData;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.DataClass.ServiceItems;
import com.dcoret.beautyclient.DataClass.ServicesForClientGroup;
import com.dcoret.beautyclient.DataClass.ServicesIDS;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class GroupReservationFragment extends Fragment {

    Button add_client, add_me,choose_occision,relations;
    LinearLayout clients,bookme;
    static int items=0;
    static int viewcount=0;
    String myid="";
    View mylayout;
    Button next;
    public static ArrayList<ServiceItems> servicesList=new ArrayList<>();
    public static ArrayList<String> serviceNameList=new ArrayList<>();
    public static ArrayAdapter adapter;

    //-----------not used --------------
    public static ArrayList<SerchGroupBookingData> serchGroupBookingData = new ArrayList<>();
    public static ArrayList<SerchGroupBookingData.SolutionsCount> solutionsCounts = new ArrayList<>();

    //------------ save view client--------
    public static ArrayList<ClientsViewData> clientsViewData=new ArrayList<>();
    public static ArrayList<ClientsViewData> clientsViewData1=new ArrayList<>();
    public static ArrayList<Integer> ishairService=new ArrayList();


    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.activity_group_reservation_frag, container, false);


        BeautyMainPage.FRAGMENT_NAME="GroupReservationFragment";

        //---------- find views------------------
        add_client=view.findViewById(R.id.add_client);
        relations=view.findViewById(R.id.relations);
        add_me=view.findViewById(R.id.add_me);
        choose_occision=view.findViewById(R.id.choose_occision);
        clients=view.findViewById(R.id.clients);
        bookme=view.findViewById(R.id.bookme);
        //----------------------------------------
        servicesList.clear();
        serviceNameList.clear();
        serviceNameList.add("Choose Service");

        APICall.getServices("1",BeautyMainPage.context);
        choose_occision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v);
                popupMenu.getMenu().add("Wedding");
                popupMenu.getMenu().add("Normal");
                popupMenu.show();
            }
        });
        items=0;
        viewcount=0;
        ishairService.clear();
        clientsViewData.clear();
        add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                items++;
                final String ic;
                ic="client"+items;
                final View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.client_layout, clients, false);

                //---------- find views------------------
                ImageView delete=layout2.findViewById(R.id.delete);
                EditText client_name=layout2.findViewById(R.id.client_name);
                EditText phone_number=layout2.findViewById(R.id.phone_number);
                final AppCompatSpinner add_service=layout2.findViewById(R.id.add_service);
                final LinearLayout adding_name_service=layout2.findViewById(R.id.adding_service_layout);
                //------------------------------------------------
//                 adapter=new CustomListAdapterWithoutImage(getActivity(), serviceNameList);



                //----------- adapter add services----------
                adapter=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_item,serviceNameList){

                    public View getView(int position, View convertView,ViewGroup parent) {

                        View v = super.getView(position, convertView, parent);

                        ((TextView) v).setTextSize(16);
//                        ((TextView) v).setGravity(Gravity.CENTER);
                        ((TextView) v).setGravity(Gravity.RIGHT);

                        return v;

                    }

                    public View getDropDownView(int position, View convertView,ViewGroup parent) {

                        View v = super.getDropDownView(position, convertView,parent);

                        ((TextView) v).setGravity(Gravity.RIGHT);
                        return v;

                    }

                };
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                add_service.setAdapter(adapter);
                //--------------------------------------------



                //----------------------------- click add service0---------------------------
                final ArrayList<ServicesIDS> servicesForClientGroups=new ArrayList<>();
                add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     if (position!=0) {
                       viewcount++;
                          final String vc;
                         vc="view"+viewcount;
                         final View view1 = inflater.inflate(R.layout.adding_name_service_layout, adding_name_service, false);
                         TextView textView = view1.findViewById(R.id.service_name);
                         textView.setText(add_service.getSelectedItem().toString());
                         ImageView delete = view1.findViewById(R.id.delete);
                         delete.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 adding_name_service.removeView(view1);
                                 for (int i=0;i<servicesForClientGroups.size();i++){
                                     if (servicesForClientGroups.get(i).getViewnum().equals(vc)){
                                       try {
                                           servicesForClientGroups.remove(i);
                                       }catch (Exception ee)
                                       {
                                           ee.printStackTrace();
                                       }
                                       }
                                 }
//
//                                 servicesForClientGroups.remove(vc-1);
                                 Log.e("servicesForClientGroups",servicesForClientGroups.size()+"");

                             }
                         });

                         adding_name_service.addView(view1);

                         servicesForClientGroups.add(new ServicesIDS(servicesList.get(position-1).getBdb_ser_id(),add_service.getSelectedItem().toString(),vc));
                     }
                        if (position!=0 && servicesList.get(position-1).getBdb_is_fixed_price().equals("1")){
                            ishairService.add(items-1);
                            Log.e("PostionID",position+"");
                            Log.e("PostionID",servicesList.get(position-1).getBdb_name()+"");
                            Log.e("PostionID",servicesList.get(position-1).getBdb_is_fixed_price()+"");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                //------------------ adapter for spinner age_range------
                Spinner age_range=layout2.findViewById(R.id.age_range);
                ArrayAdapter adapter_age_range=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.age_range,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                age_range.setAdapter(adapter_age_range);

                //------------------ adapter for spinner client status---------
                Spinner client_status=layout2.findViewById(R.id.client_status);
                ArrayAdapter adapter_client_status=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.client_status,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                client_status.setAdapter(adapter_client_status);


                clientsViewData.add(new ClientsViewData(client_name,phone_number,add_service,age_range,client_status,servicesForClientGroups,"0",ic));
                clientsViewData1.add(new ClientsViewData(client_name,phone_number,add_service,age_range,client_status,servicesForClientGroups,"0",ic));

               //------------------ delete click btn----------------
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clients.removeView(layout2);
                        for (int i=0;i<clientsViewData.size();i++){
                            if (clientsViewData.get(i).getId().equals(ic)){
                                clientsViewData.remove(i);
                            }
                        }
//                        clientsViewData.remove(ic-1);
                        Log.e("clientsViewData",clientsViewData.size()+"");
                        Log.e("clientsViewData","ic:"+clientsViewData.size()+"");
                    }
                });

                clients.addView(layout2);
            }
        });





        //---------------- add me click btn-------------------
        add_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_me.getText().equals("Add Me")){
                    add_me.setText("Remove Me");
                    items++;
                    //items++;
                final String ic;
                ic="client"+items;
                final View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.my_booking, bookme, false);

                //------------ sp for delete-------
                mylayout=layout2;
                    myid=ic;
                    //------------------
                    SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                try {
                    sh.getString("bdb_name", null).equals("");
                } catch (NullPointerException npe) {
                    APICall.detailsUser1("http://clientapp.dcoret.com/api/auth/user/detailsUser", BeautyMainPage.context);
//                    editor.putString("bdb_name","");
//                    editor.commit();
                }


                //--------- find views --------------------
                EditText client_name = layout2.findViewById(R.id.client_name);
                EditText phone_number = layout2.findViewById(R.id.phone_num);
                final AppCompatSpinner add_service = layout2.findViewById(R.id.add_service);
                final LinearLayout adding_name_service = layout2.findViewById(R.id.adding_service_layout);

                //------------------ adapter for add services----------------------
                adapter = new ArrayAdapter(BeautyMainPage.context, android.R.layout.simple_spinner_item, serviceNameList) {

                    public View getView(int position, View convertView, ViewGroup parent) {

                        View v = super.getView(position, convertView, parent);

                        ((TextView) v).setTextSize(16);
//                        ((TextView) v).setGravity(Gravity.CENTER);
                        ((TextView) v).setGravity(Gravity.CENTER);

                        return v;

                    }

                    public View getDropDownView(int position, View convertView, ViewGroup parent) {

                        View v = super.getDropDownView(position, convertView, parent);

                        ((TextView) v).setGravity(Gravity.CENTER);
                        return v;

                    }

                };
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                add_service.setAdapter(adapter);

                final ArrayList<ServicesIDS> servicesForClientGroups = new ArrayList<>();
                add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            viewcount++;
                            final String vc;
                            vc="view"+viewcount;
                            final View view1 = inflater.inflate(R.layout.adding_name_service_layout, adding_name_service, false);
                            TextView textView = view1.findViewById(R.id.service_name);

                            textView.setText(add_service.getSelectedItem().toString());
                            adding_name_service.addView(view1);

                            ImageView delete = view1.findViewById(R.id.delete);
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    adding_name_service.removeView(view1);
                                    for (int i=0;i<servicesForClientGroups.size();i++){
                                        if (servicesForClientGroups.get(i).getViewnum().equals(vc)){
                                            try {
                                                servicesForClientGroups.remove(i);
                                            }catch (Exception ee)
                                            {
                                                ee.printStackTrace();
                                            }
                                        }
                                    }
//
//                                 servicesForClientGroups.remove(vc-1);
                                    Log.e("servicesForClientGroups",servicesForClientGroups.size()+"");

                                }
                            });
                            servicesForClientGroups.add(new ServicesIDS(servicesList.get(position - 1).getBdb_ser_id(), add_service.getSelectedItem().toString(),vc));


                        }
//                     if (position!=0){
//                         postions.add(position-1);
//                     }
                        if (position != 0 && servicesList.get(position - 1).getBdb_is_fixed_price().equals("1")) {
                            ishairService.add(items - 1);
                            Log.e("PostionID", position + "");
                            Log.e("PostionID", servicesList.get(position - 1).getBdb_name() + "");
                            Log.e("PostionID", servicesList.get(position - 1).getBdb_is_fixed_price() + "");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                client_name.setText(sh.getString("bdb_name", ""));
                phone_number.setText(sh.getString("bdb_mobile", ""));

                clientsViewData.add(new ClientsViewData(client_name, phone_number, add_service, null, null, servicesForClientGroups, "1",myid));
                bookme.addView(layout2);
            }else {

                    //----------------- remove add me -----------------
                    bookme.removeView(mylayout);
                    for (int i=0;i<clientsViewData.size();i++){
                        if (clientsViewData.get(i).getId().equals(myid)){
                            clientsViewData.remove(i);
                            add_me.setText("Add Me");
                        }
                    }
//                        clientsViewData.remove(ic-1);
//                    Log.e("clientsViewData",clientsViewData.size()+"");
//                    Log.e("clientsViewData","ic:"+clientsViewData.size()+"");
                }
            }
        });





        relations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int alert=0;
                Log.e("ClientSizeF",clientsViewData.size()+"");
                for (int i=0;i<clientsViewData.size();i++) {
//                    Log.e("ClientName", clientsViewData.get(i).getClient_name().getText().toString());

                    if (clientsViewData.get(i).getClient_name().getText().toString().isEmpty() || clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
                            || clientsViewData.get(i).getAdd_service().getSelectedItemPosition() == 0) {
                        alert = 1;
                    }
                }
                if (alert == 1) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), "Please Complete All Data..");
                } else {

                    if (add_me.getText().toString().equals("Add Me")) {
                        APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), "Please Add yourself to complete Process");

                    } else if (add_me.getText().toString().equals("Remove Me") && clientsViewData.size() == 1) {
                        APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), "Please Add another client with you");
                    }else {

                        fragment = new ClientRelationsFragment();
                        fm = getFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, fragment);
                        fragmentTransaction.commit();
                    }
                }
            }
        });


        //------------------click next btn----------------------
        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int alert=0;

                for (int i=0;i<clientsViewData.size();i++) {
                    if (clientsViewData.get(i).getPhone_number().getText().toString().length()!=0)
                    if (!APICall.checkNumber(clientsViewData.get(i).getPhone_number().getText().toString(), BeautyMainPage.context)) {
                      alert=2;
                        break;
                    }
                }

                for (int i=0;i<clientsViewData.size();i++) {
//                    Log.e("ClientName", clientsViewData.get(i).getClient_name().getText().toString());

                        if (clientsViewData.get(i).getClient_name().getText().toString().isEmpty() || clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
                                || clientsViewData.get(i).getAdd_service().getSelectedItemPosition() == 0) {
                            alert = 1;
                        }
                    }

                    if (alert == 1) {
                        APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), "Please Complete All Data..");
                    }else if(alert==2){

                    }else {

                        if (add_me.getText().toString().equals("Add Me")) {
                            APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), "Please Add yourself to complete Process");
                        } else if (add_me.getText().toString().equals("Remove Me") && clientsViewData.size() == 1) {
                            APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), "Please Add another client with you");
                        } else {
                            // =------------is hair service go to anthor fragment----------
                            if (ishairService.size() > 0) {
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
                    }
                    //----- call group filter for booking -------------
                    APICall.GroupFilterBooking();

            }
        });
        return view;
    }
}
