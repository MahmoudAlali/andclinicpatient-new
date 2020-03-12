package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.R;

public class EditReservationFragment extends Fragment {
    Spinner servicePlace;
    View child;
    LinearLayout myRoot;
    Spinner spinner1,spinner2;
    Button add_person;
    ImageView delete_person;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_edit_reservation, container, false);

        add_person=view.findViewById(R.id.add_person);
        servicePlace=view.findViewById(R.id.servicePlace);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.service_place,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        servicePlace.setAdapter(adapter);
        myRoot = (LinearLayout) view.findViewById(R.id.myroot);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        add_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.layout_name_reservation_and_age_and_employee_editable, myRoot,false);

                spinner1=child.findViewById(R.id.agespinner);
                ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.age,android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);
                spinner2=child.findViewById(R.id.employeespinner);
                ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.employee,android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);
                delete_person=child.findViewById(R.id.delete_person);

                final LinearLayout a = new LinearLayout(BeautyMainPage.context);
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


        return view;
    }
}
