package com.dcoret.beautyclient.Fragments.OtherGroupBooking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dcoret.beautyclient.DataModel.ClientsViewData;
import com.dcoret.beautyclient.DataModel.SerchGroupBookingData;
import com.dcoret.beautyclient.DataModel.ServiceItems;
import com.dcoret.beautyclient.DataModel.ServicesIDS;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class GroupReservationOthersFragment extends Fragment {
//    LinearLayout services_tabs;
    Button add_client;
    LinearLayout clients,bookme;
    static int items=0;
    static int viewcount=0;
    String myid="";
    View mylayout;
    Button next;
    public static ArrayList<ServiceItems> servicesList=new ArrayList<>();
    public static ArrayList<String> serviceNameList=new ArrayList<>();
    public static ArrayAdapter adapter;
    boolean bridecheck=false;





    public static ArrayList<SerchGroupBookingData> serchGroupBookingData = new ArrayList<>();
    public static ArrayList<SerchGroupBookingData.SolutionsCount> solutionsCounts = new ArrayList<>();

    public static ArrayList<String> salons=new ArrayList<>();


    //------------ save view client--------
    public static ArrayList<ClientsViewData> clientsViewData=new ArrayList<>();
    public static ArrayList<Integer> ishairService=new ArrayList();
//    public static ArrayList<Integer> postions=new ArrayList();

    public static String is_group_booking="";

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.activity_group_reservation_others_frag, container, false);


//        BeautyMainPage.FRAGMENT_NAME="GroupReservationFragment";

        add_client=view.findViewById(R.id.add_client);
//        add_me=view.findViewById(R.id.add_me);
//        choose_occision=view.findViewById(R.id.choose_occision);
        clients=view.findViewById(R.id.clients);
        bookme=view.findViewById(R.id.bookme);

        servicesList.clear();
        serviceNameList.clear();
        serviceNameList.add("Choose Service");
        Log.e("FragmentName",BeautyMainPage.FRAGMENT_NAME );

        if (BeautyMainPage.FRAGMENT_NAME .equals("PLACESERVICEFRAGMENTBRIDEOTHER")) {
            APICall.getServices("2", BeautyMainPage.context);
            is_group_booking="12";
        }else if (BeautyMainPage.FRAGMENT_NAME .equals("PLACESERVICEFRAGMENTOTHER")) {
            APICall.getServices("0", BeautyMainPage.context);
            is_group_booking="2";

        }
//        choose_occision.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v);
//                popupMenu.getMenu().add("Wedding");
//                popupMenu.getMenu().add("Normal");
//                popupMenu.show();
//            }
//        });
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

                ImageView delete=layout2.findViewById(R.id.delete);
                EditText client_name=layout2.findViewById(R.id.client_name);
                EditText phone_number=layout2.findViewById(R.id.phone_number);
                final AppCompatSpinner add_service=layout2.findViewById(R.id.add_service);
                final LinearLayout adding_name_service=layout2.findViewById(R.id.adding_service_layout);
//                 adapter=new CustomListAdapterWithoutImage(getActivity(), serviceNameList);

                adapter=new ArrayAdapter(BeautyMainPage.context,R.layout.simple_spinner_dropdown_item_v1 ,serviceNameList);
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                add_service.setAdapter(adapter);

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

                         if (servicesList.get(position-1).getBdb_is_bride_service().equals("1") && BeautyMainPage.FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTBRIDEOTHER")) {
                             bridecheck=true;
                         }
                         Log.e("brideCheck",bridecheck+"");

                         servicesForClientGroups.add(new ServicesIDS(servicesList.get(position-1).getBdb_ser_id(),add_service.getSelectedItem().toString(),vc));



                     }
//                     if (position!=0){
//                         postions.add(position-1);
//                     }
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
                Spinner age_range=layout2.findViewById(R.id.age_range);
                ArrayAdapter adapter_age_range=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.age_range,R.layout.simple_spinner_dropdown_item_v1 );
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                age_range.setAdapter(adapter_age_range);

//                Spinner client_status=layout2.findViewById(R.id.client_status);
//                ArrayAdapter adapter_client_status=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.client_status,android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                client_status.setAdapter(adapter_client_status);


                clientsViewData.add(new ClientsViewData(client_name,phone_number,add_service,age_range,null,servicesForClientGroups,"0",ic));

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













        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int alert=0;


                if (BeautyMainPage.FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTBRIDEOTHER") && bridecheck==false){
                    alert=3;
                }

                for (int i=0;i<clientsViewData.size();i++){
                    if (clientsViewData.get(i).getClient_name().getText().toString().isEmpty()||clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
                        || clientsViewData.get(i).getAdd_service().getSelectedItemPosition()==0)
                    {
                      alert=1;
                    }
                }

                if (alert==1){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.complete_all_data));
                }else if (alert==3){
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.ser_have_one_bride_ser));

                }else {

                    if ( clientsViewData.size()==1){
                        APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.add_one_client));

                    }else {

                    // =------------is hair service go to anthor fragment----------

//                    if (ishairService.size() > 0) {
//                        fragment = new HairSpecificationsFragment();
//                        fm = getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
//                    } else {
//                        fragment = new GroupReservationOtherResultFragment();
//                        fm = getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
                        Intent intent=new Intent(BeautyMainPage.context,MyOtherEffectActivity.class);
                        startActivity(intent);
//                    }
                    }
                }

//            APICall.getlatlng();
//            APICall.getPrice();
//            APICall.getDate();
//            APICall.getClients();
            APICall.GroupFilterBookingforOther();

            }
        });



        return view;
    }
}
