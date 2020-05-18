package com.ptmsa1.clinicclient.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.API.HintArrayAdapter;
import com.ptmsa1.clinicclient.API.LayoutList;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Adapters.CalenderAdapter;
import com.ptmsa1.clinicclient.Adapters.CustomExpandableListAdapter;
import com.ptmsa1.clinicclient.Adapters.ServicesAdapter;
import com.ptmsa1.clinicclient.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class IndividualBooking extends AppCompatActivity {

   public static ArrayList alltimes=new ArrayList();
//   public static ArrayList times=new ArrayList();
//    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static List<String> listDataHeader=new ArrayList<>();
    public static HashMap<String,List<String>> listHashMap=new HashMap<>();
    //------------ for spinner array-------------------
//    public static ArrayList<String> freeTimes=new ArrayList<>();
    public static ArrayList<String> empid=new ArrayList();
    public static ArrayList<LayoutList> layoutLists=new ArrayList<>();
    public static int idforemp;
    public static String dateSelected;
    public  static String starttime;
    public  static String startdate;
    public  static String emp_id,emp_name;
    static String bdb_ser_salon,bdb_ser_home,bdb_ser_hall,bdb_ser_hotel;



    public static Spinner alltimesSpinner;

    static Context context;
    RecyclerView recyclerView,recyclerViewtime;
//    String [] item={"1","2","3","4","1","2","3","4","1","2","3","4"};
    public static String bdb_id,txtProviderName,txtPrice;
    public static LinearLayout showEmp;
    TextView providerName,price;
    Button addtocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_booking);
        Intent intent= getIntent();
        context=this;
        emp_id="";
         bdb_id=intent.getStringExtra("bdb_ser_sup_id");
        txtProviderName=intent.getStringExtra("Provider Name");
        txtPrice=intent.getStringExtra("Price");


         providerName=findViewById(R.id.provider_name);
         price=findViewById(R.id.price);

         providerName.setText(txtProviderName);
        price.setText(txtPrice);

        context=this;
//        splitTime("2019-07-15T09:00:00.000000Z","2019-07-15T12:00:00.000000Z");

        recyclerView=findViewById(R.id.recycleview);

        recyclerView.setHasFixedSize(true);
        CalenderAdapter calenderAdapter =new CalenderAdapter(this, ServicesAdapter.dateClasses);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(calenderAdapter);


        alltimesSpinner=findViewById(R.id.alltimesSpinner);
        alltimes.add("Booking Time");
        alltimes.add("Remove Time Selected");
        HintArrayAdapter adapter = new HintArrayAdapter(context,0);
        adapter.addAll(alltimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        alltimesSpinner.setAdapter(adapter);

        alltimesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layoutLists.clear();
                Log.e("LSSIZE",layoutLists.size()+"");
                if (position==0){

                }else if (position==1){

                    Log.e("TimeSpinner",alltimesSpinner.getSelectedItem().toString());
                    APICall.searchBooking(ServicesAdapter.ser_sup_id,dateSelected,context);

                }else{
                    idforemp=-1;
                    Log.e("TIMESESP",alltimesSpinner.getSelectedItem()+"");
                    Log.e("TIMESESP",alltimesSpinner.getSelectedItemPosition()+"");
                    Log.e("TIMESESP",alltimesSpinner.getSelectedItemId()+"");
                    showEmp.removeAllViews();
                    APICall.searchBooking1("15","",alltimesSpinner.getSelectedItem().toString(),context);
//                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showEmp=findViewById(R.id.show_emp);
//        showEmp.addView(R.layout.show_employee_layout);
//        listView=findViewById(R.id.expandableListView);
//        listAdapter=new CustomExpandableListAdapter(this,listDataHeader,listHashMap);
//        listAdapter.notifyDataSetChanged();
//        listView.setAdapter(listAdapter);

        addtocart=findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layouttype == 1){
                    if (idforemp==-1) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.alert),getResources().getString(R.string.select_an_emp));

//                        Toast.makeText(IndividualBooking.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        if (PlaceServiceFragment.placeId == 1) {
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            bdb_ser_salon = "0";
                        } else if (PlaceServiceFragment.placeId == 30) {
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            bdb_ser_salon = "0";
                        } else if (PlaceServiceFragment.placeId == 31) {
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            bdb_ser_salon = "0";
                        } else if (PlaceServiceFragment.placeId == 32) {
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            bdb_ser_salon = "1";
                        }

                        if (!layoutLists.get(idforemp).getAvlTime().getSelectedItem().toString().equals("Time")) {
                            starttime = layoutLists.get(idforemp).getAvlTime().getSelectedItem().toString();
                            emp_name = layoutLists.get(idforemp).getEmpName().getText().toString();
//                        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
//                        Date startdt=null;
//                       try {
//                           startdt =sdf.parse(startdate);
//                       }catch (Exception e){
//                           e.printStackTrace();
//                       }
                            Log.e("EMPID1",emp_id);
                            Log.e("EMPID1",emp_name);
                            APICall.getServiceTime(bdb_id,emp_name,ServicesAdapter.empid23,txtPrice, bdb_ser_salon, bdb_ser_home, bdb_ser_hall, bdb_ser_hotel, startdate , starttime,IndividualBooking.this);
                        } else {
                            APICall.showSweetDialog(context,getResources().getString(R.string.alert),getResources().getString(R.string.select_an_time));
//                            Toast.makeText(IndividualBooking.this, "Please, Select an Time!", Toast.LENGTH_SHORT).show();
                        }
                    }
            }else {
                    if (idforemp==-1) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.alert),getResources().getString(R.string.select_an_emp));

//                        Toast.makeText(IndividualBooking.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        if (PlaceServiceFragment.placeId == 1) {
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            bdb_ser_salon = "0";
                        } else if (PlaceServiceFragment.placeId == 30) {
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            bdb_ser_salon = "0";
                        } else if (PlaceServiceFragment.placeId == 31) {
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            bdb_ser_salon = "0";
                        } else if (PlaceServiceFragment.placeId == 32) {
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            bdb_ser_salon = "1";
                        }

                        if (alltimesSpinner.getSelectedItemPosition()!=0 || alltimesSpinner.getSelectedItemPosition()!=1) {
                            starttime = alltimesSpinner.getSelectedItem().toString();
                            emp_name = layoutLists.get(idforemp).getEmpName().getText().toString();


                            Log.e("EMPNAME",emp_name);
                            APICall.getServiceTime(bdb_id, emp_name,ServicesAdapter.empid23, txtPrice, bdb_ser_salon, bdb_ser_home, bdb_ser_hall, bdb_ser_hotel, startdate + "", starttime, IndividualBooking.this);

                        } else {
                            APICall.showSweetDialog(context,getResources().getString(R.string.alert),getResources().getString(R.string.select_an_time));

//                            Toast.makeText(IndividualBooking.this, "Please, Select an Time!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                Log.e("emp",idforemp+":"+emp_id+":"+starttime+":"+startdate);
            }
        });

    }


   static int layouttype;
    public static void addLayout(String name,String avaliablity) {
        layouttype=0;
        final View layout2;
             layout2 = LayoutInflater.from(context).inflate(R.layout.show_employee_layout, showEmp, false);
        final RadioButton emp_name =  layout2.findViewById(R.id.emp_name);
        emp_name.setText(name);

        layoutLists.add(new LayoutList(emp_name));

        if (avaliablity.equals("0")){
            emp_name.setEnabled(false);
            emp_name.setBackgroundResource(R.color.pf_red);
        }else if (avaliablity.equals("1")){
            emp_name.setEnabled(true);
            emp_name.setBackgroundResource(R.color.green);
        }else {
            emp_name.setEnabled(false);
            emp_name.setBackgroundResource(R.color.pf_light_yellow);
        }
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showEmp.addView(layout2);
            }
        });

        emp_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SizeLS",layoutLists.size()+"");

                for (int i = 0; i < layoutLists.size(); i++) {
//                    Log.e("empName",emp_name.getText()+"");
//                    Log.e("empNameList",layoutLists.get(i).getEmpName().getText()+"");
                    if (!emp_name.getText().toString().equals(layoutLists.get(i).getEmpName().getText())) {

                        layoutLists.get(i).getEmpName().setChecked(false);
                       try {
                           emp_id=empid.get(i).toString();
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                        starttime=alltimesSpinner.getSelectedItem().toString();
                        Log.e("EMPIDnmnm",emp_id);
                    }else {
                        idforemp=i;
                    }

                }

//
//                    if (!emp_name.getText().toString().equals(layoutLists.get(i).getEmpName().getText())) {
//                        layoutLists.get(i).getEmpName().setChecked(false);
//                        emp_id=empid.get(i).toString();
//                        starttime=alltimesSpinner.getSelectedItem().toString();
//
//                    }else {
//                        idforemp=i;
//                    }


            }
        });
    }


    public static void addLayout(String name,String avaliablity,ArrayList times) {
        layouttype=1;
        final View layout2;
        ArrayList timeslist=new ArrayList();
        timeslist.add("Time");
        timeslist.addAll(times);
            layout2 = LayoutInflater.from(context).inflate(R.layout.show_employee_with_time_layout, showEmp, false);
         final RadioButton emp_name =  layout2.findViewById(R.id.emp_name);
         Spinner avliable_time = layout2.findViewById(R.id.avl_time);
         LinearLayout colorsp = layout2.findViewById(R.id.colorsp);


        emp_name.setText(name);
        layoutLists.add(new LayoutList(emp_name,avliable_time));


        if (avaliablity.equals("0")){
            emp_name.setEnabled(false);
            avliable_time.setEnabled(false);
            emp_name.setBackgroundResource(R.color.pf_red);
            colorsp.setBackgroundResource(R.color.pf_red);
        }else if (avaliablity.equals("1")){
            emp_name.setEnabled(true);
            avliable_time.setEnabled(true);
            emp_name.setBackgroundResource(R.color.green);
            colorsp.setBackgroundResource(R.color.green);
        }else {
            emp_name.setEnabled(false);
            avliable_time.setEnabled(false);
            emp_name.setBackgroundResource(R.color.pf_light_yellow);
            colorsp.setBackgroundResource(R.color.pf_light_yellow);
        }

            ArrayAdapter adapter = new ArrayAdapter(BeautyMainPage.context, android.R.layout.simple_spinner_item, timeslist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            avliable_time.setAdapter(adapter);

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showEmp.addView(layout2);
            }
        });





        emp_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SizeLS",layoutLists.size()+"");
                for (int i = 0; i < layoutLists.size(); i++) {
//                    Log.e("empName",emp_name.getText()+"");
//                    Log.e("empNameList",layoutLists.get(i).getEmpName().getText()+"");
                    if (!emp_name.getText().toString().equals(layoutLists.get(i).getEmpName().getText())) {
                        layoutLists.get(i).getEmpName().setChecked(false);
                        emp_id=empid.get(i).toString();
                        Log.e("EMPID",emp_id);
//                        Log.e("startTime",starttime);
                    }else {
                        idforemp=i;
                    }

                }
            }
        });

    }

    public static ArrayList splitTime(String from,String to){
//            DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        String d="";
        SimpleDateFormat sdf,sdf1;
        ArrayList timeSplites=new ArrayList();
            try{
                 sdf = new SimpleDateFormat("HH:mm");
//                 sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                Log.e("endDate",d+"");
                Date enddate = sdf.parse(to);
                Date startDate = sdf.parse(from);
///                Log.e("endDate",enddate+"");

                while (startDate.compareTo(enddate)==-1) {

                    timeSplites.add(sdf.format(startDate));
                    cal.setTime(startDate); // sets calendar time/date
                    cal.add(Calendar.MINUTE, 15); // adds 15 min
                    startDate=cal.getTime(); // returns new date object, one hour in the future

//                    Log.e("new StartDate",startDate+"");
            }
            }catch (Exception e){
                e.printStackTrace();
            }
            return timeSplites;
        }

    }


