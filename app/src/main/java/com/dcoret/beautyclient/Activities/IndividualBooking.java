package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dcoret.beautyclient.Adapters.CalenderAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.TimeClass;
import com.dcoret.beautyclient.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class IndividualBooking extends AppCompatActivity {


    public static ExpandableListView listView;
    public static CustomExpandableListAdapter listAdapter;
    public static List<String> listDataHeader=new ArrayList<>();
    public static HashMap<String,List<String>> listHashMap=new HashMap<>();



    static Context context;
    RecyclerView recyclerView,recyclerViewtime;
    String [] item={"1","2","3","4","1","2","3","4","1","2","3","4"};
    public static String bdb_id;
    public static LinearLayout showEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_booking);
        Intent intent= getIntent();
        context=this;
         bdb_id=intent.getStringExtra("bdb_ser_sup_id");


        context=this;
//        splitTime("2019-07-15T09:00:00.000000Z","2019-07-15T12:00:00.000000Z");



        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        CalenderAdapter calenderAdapter =new CalenderAdapter(this, ServicesAdapter.dateClasses);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(calenderAdapter);


        showEmp=findViewById(R.id.show_emp);
//        showEmp.addView(R.layout.show_employee_layout);




        listView=findViewById(R.id.expandableListView);
//        initData();

        listAdapter=new CustomExpandableListAdapter(this,listDataHeader,listHashMap);
        listAdapter.notifyDataSetChanged();
//        listView.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader=new ArrayList<>();
        listHashMap=new HashMap<>();

        listDataHeader.add("emp1");
        listDataHeader.add("emp2");
        listDataHeader.add("emp3");
        listDataHeader.add("emp4");

        List<String> timeemp1=new ArrayList<>();
        timeemp1.add("09:00");
        timeemp1.add("09:15");
        timeemp1.add("09:30");
        timeemp1.add("09:45");
        timeemp1.add("10:00");
        List<String> timeemp2=new ArrayList<>();
        timeemp2.add("09:00");
        timeemp2.add("09:15");
        timeemp2.add("09:30");
        timeemp2.add("09:45");
        timeemp2.add("10:00");
        List<String> timeemp3=new ArrayList<>();
        timeemp3.add("09:00");
        timeemp3.add("09:15");
        timeemp3.add("09:30");
        timeemp3.add("09:45");
        timeemp3.add("10:00");
        List<String> timeemp4=new ArrayList<>();
        timeemp4.add("09:00");
        timeemp4.add("09:15");
        timeemp4.add("09:30");
        timeemp4.add("09:45");
        timeemp4.add("10:00");


        listHashMap.put(listDataHeader.get(0),timeemp1);
        listHashMap.put(listDataHeader.get(1),timeemp2);
        listHashMap.put(listDataHeader.get(2),timeemp3);
        listHashMap.put(listDataHeader.get(3),timeemp4);
    }


    public static void addLayout(String name, final ArrayList times) {
        final View layout2 = LayoutInflater.from(context).inflate(R.layout.show_employee_layout, showEmp, false);

        final TextView emp_name =  layout2.findViewById(R.id.emp_name);
        final Spinner avliable_time =  layout2.findViewById(R.id.avl_time);

        emp_name.setText(name);
        ArrayAdapter adapter = new ArrayAdapter(BeautyMainPage.context, android.R.layout.simple_spinner_item,times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        avliable_time.setAdapter(adapter);

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (times.size()==1 && times.get(0).equals("0")){
                    emp_name.setEnabled(false);
                    avliable_time.setEnabled(false);
                    times.set(0,"No Times Available for booking ");
                    layout2.setEnabled(false);
                }
                showEmp.addView(layout2);

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
                 sdf = new SimpleDateFormat("HH:mm:ss");
                 sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Log.e("endDate",d+"");
                Date enddate = sdf.parse(to);
                Date startDate = sdf.parse(from);
                Log.e("endDate",enddate+"");
                Log.e("StartDate",startDate+"");
                Log.e("Compare",enddate.compareTo(enddate)+"");

                while (startDate.compareTo(enddate)==-1) {
//                    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
//                    System.out.println(cal.getTime());
                    // Output "Wed Sep 26 14:23:28 EST 2012"

//                    System.out.println(formatted);
                    timeSplites.add(startDate.getHours()+":"+startDate.getMinutes());
                    cal.setTime(startDate); // sets calendar time/date
                    cal.add(Calendar.MINUTE, 15); // adds 15 min

                    startDate=cal.getTime(); // returns new date object, one hour in the future
                    Log.e("new StartDate",startDate+"");
            }
            }catch (Exception e){
                e.printStackTrace();
            }
            return timeSplites;
        }

    }


