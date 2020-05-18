package com.ptmsa1.clinicclient.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.API.HintArrayAdapter;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.DataModel.ClientsViewData;
import com.ptmsa1.clinicclient.DataModel.IDNameService;
import com.ptmsa1.clinicclient.DataModel.SerchGroupBookingData;
import com.ptmsa1.clinicclient.DataModel.ServiceItems;
import com.ptmsa1.clinicclient.DataModel.ServicesIDS;
import com.ptmsa1.clinicclient.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MultiIndividualBookingReservationFragment extends Fragment {

    public static Button add_client, add_me, choose_date;
    LinearLayout clients, bookme;
    static int items = 0;
    static int viewcount = 0;
    String myid = "";
    View mylayout;
    Button next;
    public static Spinner choose_occision;
    public static ArrayList<ServiceItems> servicesList = new ArrayList<>();
    public static ArrayList<String> serviceNameList = new ArrayList<>();
    public static ArrayAdapter adapter;
    public static  ArrayList<TextView> dates=new ArrayList<>();

    //-----------not used --------------
    public static ArrayList<SerchGroupBookingData> serchGroupBookingData = new ArrayList<>();
    public static ArrayList<SerchGroupBookingData.SolutionsCount> solutionsCounts = new ArrayList<>();

    //------------ save view client--------
    public static ArrayList<ClientsViewData> clientsViewData = new ArrayList<>();
    public static ArrayList<Integer> ishairService = new ArrayList();


    public static String is_group_booking = "";

//    Fragment fragment;
//    FragmentManager fm;
//    FragmentTransaction fragmentTransaction;
//    static ArrayList<TextView> dates=new ArrayList<>();

    boolean bridecheck = false;
    public static ArrayList<ServicesIDS> servicesForClientGroups = new ArrayList<>();


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_multi_ind_booking_reservation_frag, container, false);


        //----------- init----
        servicesForClientGroups.clear();
        dates.clear();

//        BeautyMainPage.FRAGMENT_NAME="MultiIndividualBookingReservationFragment";

        //---------- find views------------------
//        add_client=view.findViewById(R.id.add_client);
//        add_me=view.findViewById(R.id.add_me);
        choose_occision = view.findViewById(R.id.choose_occision);
        choose_date = view.findViewById(R.id.date_select);
        clients = view.findViewById(R.id.clients);
        bookme = view.findViewById(R.id.bookme);
        //----------------------------------------
        servicesList.clear();
        serviceNameList.clear();
        String choose = getResources().getString(R.string.choose_service);
        serviceNameList.add(choose);

        Log.e("FrgmenNAme", BeautyMainPage.FRAGMENT_NAME);
        if (BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking_bride")) {
            APICall.getServicesForMulti("2", BeautyMainPage.context);
            is_group_booking = "13";
//            APICall.getServices("1", BeautyMainPage.context);
        } else if (BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking")) {
//            APICall.getServices("0", BeautyMainPage.context);
            APICall.getServicesForMulti("0", BeautyMainPage.context);
            is_group_booking = "3";

        }
        HintArrayAdapter adapter1 = new HintArrayAdapter(this.getActivity(), 0);
        adapter1.addAll(Arrays.asList(getResources().getStringArray(R.array.service_type)));
        adapter1.setDropDownViewResource(R.layout.spinner_center_item);
        choose_occision.setAdapter(adapter1);


        choose_occision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (choose_occision.getSelectedItemPosition() == 1) {
                    choose_date.setVisibility(View.VISIBLE);
                } else if (choose_occision.getSelectedItemPosition() == 2) {
                    choose_date.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        choose_occision.setText("choose reservation type");
//            choose_occision.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String one=getResources().getString(R.string.one_date_ser);
//                String dif=getResources().getString(R.string.dif_date_ser);
//
//
//                final PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v);
//                popupMenu.getMenu().add(one);
//                popupMenu.getMenu().add(dif);
//
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//
//                        choose_occision.setText(item.getTitle());
//
//
//                        return false;
//                    }
//                });
//
//                popupMenu.show();
//
//
//
//            }
//        });
        items = 0;
        viewcount = 0;
        ishairService.clear();
        clientsViewData.clear();


        items++;
        //items++;
        final String ic;
        ic = "client" + items;
        final View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.my_booking, bookme, false);

        //------------ sp for delete-------
        mylayout = layout2;
        myid = ic;
        //------------------
        SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        try {
            sh.getString("bdb_name", null).equals("");
        } catch (NullPointerException npe) {
            APICall.detailsUser1(APICall.API_PREFIX_NAME + "/api/auth/user/detailsUser", BeautyMainPage.context);
//                    editor.putString("bdb_name","");
//                    editor.commit();
        }


        //--------- find views --------------------
        final EditText client_name = layout2.findViewById(R.id.client_name);
        final EditText phone_number = layout2.findViewById(R.id.phone_num);
        final SearchableSpinner add_service = layout2.findViewById(R.id.add_service);
        final LinearLayout adding_name_service = layout2.findViewById(R.id.adding_service_layout);

        //--------------------------------------
        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(BeautyMainPage.context);
                dialog.setContentView(R.layout.select_date);
                TextView ok = dialog.findViewById(R.id.confirm);
                TextView cancel = dialog.findViewById(R.id.cancel);
                final DatePicker datePicker = dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(System.currentTimeMillis());

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

//                                            Date date=sdf.parse(APICall.dofs.get(position).getBdb_offer_end());

//                                            datePicker.setMaxDate(date.getTime());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        int month = datePicker.getMonth() + 1;
                        choose_date.setText(datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth());

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
//                        ((Item)holder).select_time.setText(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
                    }
                });

                dialog.show();
            }
        });

        //------------------ adapter for add services----------------------
        adapter = new ArrayAdapter(BeautyMainPage.context, R.layout.spinner_center_item, serviceNameList);
//                adapter.addAll(serviceNameList);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        add_service.setTitle(getResources().getString(R.string.services));
        add_service.setAdapter(adapter);

        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //------------------------- check bride service at least one service -----------------
                if (position != 0) {
//                            if (choose_occision.getSelectedItemPosition()==0 ){
//                                APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.alert),getResources().getString(R.string.choose_type_res));
//                            } else {
                    //---------- check if service is there
                    boolean addlayoutchek = true;
                    for (int i = 0; i < servicesForClientGroups.size(); i++) {
                        if ((servicesForClientGroups.get(i).getName().equals(add_service.getSelectedItem().toString()))) {
                            addlayoutchek = false;
                            APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.alert), getResources().getString(R.string.ser_added_before));
                            add_service.setSelection(0);
                            break;
                        }
                    }


                    if (choose_occision.getSelectedItemPosition() == 0) {
                        APICall.showSweetDialog(BeautyMainPage.context, "", BeautyMainPage.context.getResources().getString(R.string.plsSelectBookingType));
                        add_service.setSelection(0);
                    } else if (addlayoutchek) {
                        viewcount++;
                        final String vc;
                        vc = "view" + viewcount;
                        final View view1;
                        Boolean multicheck;
                        if (choose_occision.getSelectedItemPosition() == 1) {
                            view1 = inflater.inflate(R.layout.adding_name_service_layout, adding_name_service, false);
                            multicheck = false;
                        } else {
                            view1 = inflater.inflate(R.layout.adding_name_service_layout_dates, adding_name_service, false);
                            multicheck = true;
                        }

                        TextView textView = view1.findViewById(R.id.service_name);
                        textView.setText(add_service.getSelectedItem().toString());
                        final LinearLayout selectdate = view1.findViewById(R.id.select_date);
                        final TextView select_time = view1.findViewById(R.id.select_time);
                        dates.add(select_time);

                        if (multicheck) {
                            selectdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                                    dialog.setContentView(R.layout.select_date);
                                    TextView ok = dialog.findViewById(R.id.confirm);
                                    TextView cancel = dialog.findViewById(R.id.cancel);
                                    final DatePicker datePicker = dialog.findViewById(R.id.date_picker);
                                    datePicker.setMinDate(System.currentTimeMillis());

                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

//                                            Date date=sdf.parse(APICall.dofs.get(position).getBdb_offer_end());

//                                            datePicker.setMaxDate(date.getTime());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.cancel();
                                            int month = datePicker.getMonth() + 1;

                                            select_time.setText(datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth());
//                                                dates.add(select_time);
                                        }
                                    });

                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.cancel();
//                        ((Item)holder).select_time.setText(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
                                        }
                                    });

                                    dialog.show();
                                }
                            });

                        }


                        adding_name_service.addView(view1);

                        ImageView delete = view1.findViewById(R.id.delete);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adding_name_service.removeView(view1);
                                for (int i = 0; i < servicesForClientGroups.size(); i++) {
                                    if (servicesForClientGroups.get(i).getViewnum().equals(vc)) {
                                        try {
                                            servicesForClientGroups.remove(i);
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    }
                                }
//
//                                 servicesForClientGroups.remove(vc-1);
                                Log.e("servicesForClientGroups", servicesForClientGroups.size() + "");

                            }
                        });
                        Log.e("brideservice", servicesList.get(position - 1).getBdb_is_bride_service());

                        if (servicesList.get(position - 1).getBdb_is_bride_service().equals("1") && BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking_bride")) {
                            bridecheck = true;
                        }

                        if (multicheck) {
                            servicesForClientGroups.add(new ServicesIDS(servicesList.get(position - 1).getBdb_ser_id(), add_service.getSelectedItem().toString(), select_time, vc));
                        } else {
                            servicesForClientGroups.add(new ServicesIDS(servicesList.get(position - 1).getBdb_ser_id(), add_service.getSelectedItem().toString(), vc));

                        }
                    }
                }

//                        if (position!=0) {
//                            Log.e("servicesForClientGroups", "sfcg" + servicesForClientGroups.get(servicesForClientGroups.size() - 1).getId());
//                            Log.e("servicesForClientGroups", "sfcg" + servicesForClientGroups.get(servicesForClientGroups.size() - 1).getName());
//                        }
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

        clientsViewData.add(new ClientsViewData(client_name, phone_number, add_service, null, null, servicesForClientGroups, "1", myid));
        bookme.addView(layout2);

        //------------------click next btn----------------------
        next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("fname", BeautyMainPage.FRAGMENT_NAME);
                Log.e("check", bridecheck + "");
                if (servicesForClientGroups.size() <= 1) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.select_two_ser_atleast));

                } else if (BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking_bride") && bridecheck == false) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.ser_have_one_bride_ser));
                } else {


                    clientf = "";
                    int alert = 0;
//                for (int i=0;i<clientsViewData.size();i++){
//                    if (clientsViewData.get(i).getClient_name().getText().toString().isEmpty()||clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
//                        || clientsViewData.get(i).getAdd_service().getSelectedItemPosition()==0    )
//                    {
//
//


//                      alert=1;
//                    }
//                }

                    for (int i = 0; i < clientsViewData.size(); i++) {
//                    if (clientsViewData.get(i).getClient_name().getText().toString().isEmpty()||clientsViewData.get(i).getPhone_number().getText().toString().isEmpty()
//                        || clientsViewData.get(i).getAdd_service().getSelectedItemPosition()==0    )
//                    {
                        if (choose_date.getVisibility() == View.GONE) {
//                        for (int j = 0; j < clientsViewData.get(i).getServicesSelected().size(); j++) {
//                            clientsViewData.get(i).getServicesSelected().get(j).getDate().equals("Date");
//                            alert = 1;
//                            break;
//                        }
                        }
//}
//                      alert=1;
//                    }
                    }


                    if (alert == 1) {
                        APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.complete_all_data));

//                    choose date for your services
                    } else if (choose_date.getText().toString().equals(getResources().getString(R.string.choose_date_for_your_services)) && choose_date.getVisibility() == View.VISIBLE) {
                        APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.choose_date_for_your_services));
                    } else {

                        // =------------is hair service go to anthor fragment----------
//                    if (ishairService.size() > 0) {
//                        fragment = new HairSpecificationsFragment();
//                        fm = getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
//                    } else {
                        SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);


                        String username = sh.getString("bdb_name", "");
                        String phone = sh.getString("bdb_mobile", "");

                        if (choose_date.getVisibility() == View.VISIBLE) {
                            String date = choose_date.getText().toString();

                            clientf = " \"clients\": [\n" +
                                    "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + date + "\",\"services\": [ ";
                            String services = "";
                            for (int i = 0; i < servicesForClientGroups.size(); i++) {

                                if (i == 0) {
                                    services = "{\"ser_id\": " + servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }";
                                } else {
                                    services = services + ",{\"ser_id\": " + servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }";
                                }

                            }

                            clientf = clientf + services + "" +
                                    "]" +
                                    ",\"effect\":[]" +
                                    "}\n]}";
                        } else {
                            clientf = " \"clients\": [\n";
                            for (int i = 0; i < servicesForClientGroups.size(); i++) {

                                if (i == 0) {
                                    clientf = clientf + "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                                    String services = "";
//                        for (int i = 0; i < servicesForClientGroups.size(); i++) {


                                    clientf += "{\"ser_id\": " + servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }]" +
                                            ",\"effect\":[]\n}";

                                } else {

                                    clientf = clientf + "\t\t,{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                                    String services = "";
//                        for (int i = 0; i < servicesForClientGroups.size(); i++) {

                                    clientf += "{\"ser_id\": " + servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }],\"effect\":[]\n}";
                                }

                            }
                            clientf = clientf + "]}";
                        }
//                        fragment = new MultiBookingIndividualResult();
//                        fm = getFragmentManager();
//                        fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment, fragment);
//                        fragmentTransaction.commit();
                        Intent intent = new Intent(BeautyMainPage.context, MySingleMultiEffectActivity.class);
                        startActivity(intent);
//                    }
                    }
                    //----- call group filter for booking -------------
                    String filter = APICall.GroupFilterBookingMulti();
                    Log.e("filter", filter);
//                    clientf=filter+clientf;
                    Log.e("clientf", clientf);
                }
            }
        });
        return view;
    }

    public static String clientf = "";

//    static String sortdates(ArrayList<TextView> dates, String cname, String phone, int postion) {
//        ArrayList<ArrayList<IDNameService>> arrayList = new ArrayList<>();
//        ArrayList<Integer> index = new ArrayList<>();
//
//        for (int i = 0; i < dates.size(); i++) {
//            String ser_sup_id = clientsViewData.get(postion).getServicesSelected().get(i).getId();
//
//            if (i == 0) {
//                ArrayList<IDNameService> list = new ArrayList();
//                list.add(new IDNameService(ser_sup_id, dates.get(i).getText().toString()));
//                arrayList.add(list);
//                index.add(i);
//            } else {
//                Boolean check = false;
//                for (int k = 0; k < arrayList.size(); k++) {
//                    for (int j = 0; j < arrayList.get(k).size(); j++) {
//                        if (dates.get(i).getText().toString().equals(arrayList.get(k).get(j).getName())) {
////                            ArrayList list = new ArrayList();
//                            arrayList.get(k).add(new IDNameService(ser_sup_id, dates.get(i).getText().toString()));
////                            .add(dates.get(i).getText().toString());
////                            arrayList.add(list);
//                            index.add(i);
//                            check = true;
//                            break;
//                        }
//
//                    }
//                }
//
//                if (!check) {
//                    ArrayList<IDNameService> list = new ArrayList();
//                    list.add(new IDNameService(ser_sup_id, dates.get(i).getText().toString()));
//                    arrayList.add(list);
//                    index.add(i);
//                }
//
//
//            }
//        }
////
////
////
////
////        String services="";
////
////
////
//////        String cname="c1";
//////        String phone_num="0500500011";
////
////
////
////
////        for (int k=0;k<arrayList.size();k++) {
////            String tmp="\t\t   {\"date\":\""+arrayList.get(k).get(0).getName()+"\",\n" +
////                    "\t\t   \"client_name\": \""+cname+"\",\n" +
////                    "\t\t   \"client_phone\": \""+phone+"\",\n" +
////                    "\t\t   \"is_current_user\":1 ,\n" +
////                    "\t\t   \"services\": [\n" ;
////            if (k==0) {
////                services = services + tmp;
////            }
////
//////            else {
//////                services=services+",[";
//////            }
////            else {
////                services = services + ","+tmp;
////            }
////            for (int j = 0; j < arrayList.get(k).size(); j++) {
////
////                Log.e("date"+k+""+j,arrayList.get(k).get(j).getName());
////
////
////                if (j==0) {
////                    services =services+ " \t\t\t{\n" +
////                            "    \t\t\t\t\"bdb_ser_sup_id\": "+arrayList.get(k).get(j).getId()+",\n" +
////                            "    \t\t\t\t\"ser_time\": 60\n" +
////                            "    \t\t\t\t\n" +
////                            "      }";
////                }else {
////                    services =services+ " ,\t\t\t{\n" +
////                            "    \t\t\t\t\"bdb_ser_sup_id\": "+arrayList.get(k).get(j).getId()+",\n" +
////                            "    \t\t\t\t\"ser_time\": 60\n" +
////                            "    \t\t\t\t\n" +
////                            "      }";
////                }
////
//////                if (j==arrayList.get(k).size()-1){
//////                    services=services+"]";
//////                }
////            }
////
////            services=services+"\n ]}";
////
////        }
////
//////        services=services+"}";
////        Log.e("ServicesDate",services);
////        clientf=" \"clients\": [\n"+services+"]";
////        return services;
////    }
//    }


    public static String sortdates(ArrayList<TextView> dates,String cname,String phone,String is_adult,String effects,int postion){
        ArrayList<ArrayList<IDNameService>> arrayList=new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();



        Log.e("datesSize",dates.size()+"");
        for (int i=0;i<dates.size();i++){

            try {
//                String ser_sup_id = API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();

                if (i == 0) {
                    ArrayList<IDNameService> list = new ArrayList();
                    list.add(new IDNameService(servicesForClientGroups.get(i).getId(), dates.get(i).getText().toString()));
                    arrayList.add(list);
                    index.add(i);
                } else {
                    Boolean check = false;
                    for (int k = 0; k < arrayList.size(); k++) {
                        for (int j = 0; j < arrayList.get(k).size(); j++) {
                            if (dates.get(i).getText().toString().equals(arrayList.get(k).get(j).getName())) {
//                            ArrayList list = new ArrayList();
                                arrayList.get(k).add(new IDNameService(servicesForClientGroups.get(i).getId(), dates.get(i).getText().toString()));
//                            .add(dates.get(i).getText().toString());
//                            arrayList.add(list);
                                index.add(i);
                                check = true;
                                break;
                            }

                        }
                    }

                    if (!check) {
                        ArrayList<IDNameService> list = new ArrayList();
                        list.add(new IDNameService(servicesForClientGroups.get(i).getId(), dates.get(i).getText().toString()));
                        arrayList.add(list);
                        index.add(i);
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }




        String services="";



//        String cname="c1";
//        String phone_num="0500500011";




        for (int k=0;k<arrayList.size();k++) {
            String tmp="\t\t   {\n"+
                    "\t\t   \"client_name\": \""+cname+"\",\n" +
                    "\t\t   \"client_phone\": \""+phone+"\",\n" +
                    "\t\t   \"is_current_user\":1,\n" +
                    "\t\t   \"date\":\""+ APICall.arabicToDecimal(arrayList.get(k).get(0).getName())+"\",\n" +
                    "\t\t   \"is_adult\":1," +
                    "\t\t   \"services\": [\n" ;
            if (k==0) {
                services = services + tmp;
            }

//            else {
//                services=services+",[";
//            }
            else {
                services = services + ","+tmp;
            }
            for (int j = 0; j < arrayList.get(k).size(); j++) {

                Log.e("date"+k+""+j,arrayList.get(k).get(j).getName());


                if (j==0) {
                    services =services+ " \t\t\t{\n" +
                            "    \t\t\t\t\"ser_id\": "+arrayList.get(k).get(j).getId()+"\n" +
                            "    \t\t\t\t\n" +

                            "      }";
                }else {
                    services =services+ " ,\t\t\t{\n" +
                            "    \t\t\t\t\"ser_id\": "+arrayList.get(k).get(j).getId()+"\n" +
                            "    \t\t\t\t\n" +

                            "      }";
                }

//                if (j==arrayList.get(k).size()-1){
//                    services=services+"]";
//                }
            }

            services=services+"\n ]" +
                    "" +",\"effect\":["+effects+"]" +
                    "" +
                    "}";

        }

//        services=services+"}";
        Log.e("ServicesDate","\"clients\":["+services+"]}");
        return  "\"clients\":["+services+"]}";
    }


}