package com.dcoret.beautyclient.Fragments;

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

import com.dcoret.beautyclient.R;

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
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.service_place,android.R.layout.simple_spinner_item);
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
                ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getApplicationContext(),R.array.age,android.R.layout.simple_spinner_item);
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
