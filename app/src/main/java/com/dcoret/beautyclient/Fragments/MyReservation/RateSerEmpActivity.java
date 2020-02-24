package com.dcoret.beautyclient.Fragments.MyReservation;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.R;

public class RateSerEmpActivity extends AppCompatActivity {

    static Context context;
    static LinearLayout myroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_ser_emp);
       int l= R.layout.emp_rating_layout;
       context=this;
       myroot=findViewById(R.id.myroot);

       addLayout("Hala","تسريحة");
       addLayout("Rana","قص شعر");


    }
    public static void addLayout(String emp_name,String serviceName)
    {
        final View layout2;
        layout2 = LayoutInflater.from(context).inflate(R.layout.emp_rating_layout, myroot, false);
        TextView employee_name,ser_name;

        employee_name=layout2.findViewById(R.id.employee_name);
        ser_name=layout2.findViewById(R.id.service_name);

        employee_name.setText(emp_name);
        ser_name.setText(serviceName);

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myroot.addView(layout2);
            }
        });


    }

}
