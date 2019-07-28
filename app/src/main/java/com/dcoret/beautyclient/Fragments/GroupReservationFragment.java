package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.ClientsViewData;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.DataClass.ServiceItems;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class GroupReservationFragment extends Fragment {
//    LinearLayout services_tabs;
    Button add_client, choose_occision;
    LinearLayout clients;
    static int items=0;
    Button next;
    public static ArrayList<ServiceItems> servicesList=new ArrayList<>();
    public static ArrayList<String> serviceNameList=new ArrayList<>();
    public static ArrayAdapter adapter;



    //------------ save view client--------
    public static ArrayList<ClientsViewData> clientsViewData=new ArrayList<>();
    static int ishairService=0;


    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_frag, container, false);


        BeautyMainPage.FRAGMENT_NAME="GroupReservationFragment";

        add_client=view.findViewById(R.id.add_client);
        choose_occision=view.findViewById(R.id.choose_occision);
        clients=view.findViewById(R.id.clients);

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
        ishairService=0;
        clientsViewData.clear();
        add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items++;
                View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.client_layout, clients, false);

                EditText client_name=layout2.findViewById(R.id.client_name);
                EditText phone_number=layout2.findViewById(R.id.phone_number);
                final Spinner add_service=layout2.findViewById(R.id.add_service);
                final LinearLayout adding_name_service=layout2.findViewById(R.id.adding_service_layout);
                adapter=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_item,serviceNameList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                add_service.setAdapter(adapter);

                add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     if (position!=0) {
                         View view1 = inflater.inflate(R.layout.adding_name_service_layout, adding_name_service, false);
                         TextView textView = view1.findViewById(R.id.service_name);
                         textView.setText(add_service.getSelectedItem().toString());
                         adding_name_service.addView(view1);
                     }
                        if (add_service.getSelectedItem().toString().equals("Hair cut")){
                            ishairService++;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                Spinner age_range=layout2.findViewById(R.id.age_range);
                ArrayAdapter adapter_age_range=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.age_range,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                age_range.setAdapter(adapter_age_range);

                Spinner client_status=layout2.findViewById(R.id.client_status);
                ArrayAdapter adapter_client_status=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.client_status,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                client_status.setAdapter(adapter_client_status);


                clientsViewData.add(new ClientsViewData(client_name,phone_number,add_service,age_range,client_status));

                clients.addView(layout2);
            }
        });

        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int alert=0;
                for (int i=0;i<clientsViewData.size();i++){
                    if (clientsViewData.get(i).getClient_name().getText().toString().isEmpty()||clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
                        || clientsViewData.get(i).getAdd_service().getSelectedItemPosition()==0)
                    {
                      alert=1;
                    }
                }

                if (alert==1){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),"Please Complete All Data..");

                }else {
                    if (ishairService > 0) {
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
        });


        return view;
    }
}
