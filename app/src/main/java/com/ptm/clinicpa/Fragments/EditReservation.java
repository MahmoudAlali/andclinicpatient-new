package com.ptm.clinicpa.Fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.R;

import java.util.Arrays;

public class EditReservation extends AppCompatActivity {

    Spinner servicePlace;
    View child;
    LinearLayout myRoot;
    Spinner spinner1,spinner2;
    Button add_person;
    ImageView delete_person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);

        add_person=findViewById(R.id.add_person);
        servicePlace=findViewById(R.id.servicePlace);
        HintArrayAdapter adapter=new HintArrayAdapter(this,0);
        adapter.addAll(Arrays.asList(getResources().getStringArray(R.array.service_place)));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        servicePlace.setAdapter(adapter);
        myRoot = (LinearLayout) findViewById(R.id.myroot);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = LayoutInflater.from(EditReservation.this).inflate(R.layout.layout_name_reservation_and_age_and_employee_editable, myRoot,false);

                spinner1=child.findViewById(R.id.agespinner);
                HintArrayAdapter adapter1=new HintArrayAdapter(getApplicationContext(),0);
                adapter1.addAll(Arrays.asList(getResources().getStringArray(R.array.age)));
                adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);
//                myRoot.addView(child);
                spinner2=child.findViewById(R.id.employeespinner);
                ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getApplicationContext(),R.array.employee,android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
                delete_person=child.findViewById(R.id.delete_person);

                final LinearLayout a = new LinearLayout(getApplicationContext());
                a.setOrientation(LinearLayout.VERTICAL);
                a.addView(child);

                delete_person.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRoot.removeView(a);
                    }
                });


                myRoot.addView(a);
            }
        });


    }
}
