package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.Adapters.BrideServiecSelectAdapter;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class BrideServicesSelecting extends AppCompatActivity {
    RecyclerView recyclerView;
    Button save;
    Context context;
    int pos;
    double discount;
    String price_temp;
    public static ArrayList<String> names=new ArrayList<>();
    public static ArrayList<String> prices=new ArrayList<>();
    public static ArrayList<String> names_tmp=new ArrayList<>();
    public static ArrayList<String> prices_tmp=new ArrayList<>();
    public static ArrayList<String> names_tmp1=new ArrayList<>();
    public static ArrayList<String> prices_tmp1=new ArrayList<>();
    TextView add_dialog,price;
    EditText percent;

    Spinner name_spinner;
    CheckBox percent_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bride_services_selecting);
        names_tmp.add("تصفيف شعر");
        names_tmp.add("صبغة شعر");
        names_tmp.add(" منكير و بدكير");
        names_tmp.add(" مكياج عروسة");
        prices_tmp.add("100");
        prices_tmp.add("200");
        prices_tmp.add("300");
        context=this;
        prices_tmp.add("400");
        BrideServiecSelectAdapter  b=new BrideServiecSelectAdapter(context,names_tmp,prices_tmp);
        recyclerView=findViewById(R.id.service_select_re);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(b);


    }
}
