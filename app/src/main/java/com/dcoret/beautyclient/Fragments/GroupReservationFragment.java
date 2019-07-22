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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class GroupReservationFragment extends Fragment {
//    LinearLayout services_tabs;
    Button add_client, choose_occision;
    LinearLayout clients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_frag, container, false);

        add_client=view.findViewById(R.id.add_client);
        choose_occision=view.findViewById(R.id.choose_occision);
        clients=view.findViewById(R.id.clients);


        choose_occision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v);
                popupMenu.getMenu().add("Wedding");
                popupMenu.getMenu().add("Normal");
                popupMenu.show();
            }
        });
        add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.client_layout, clients, false);

//                TextView textView = (TextView) layout2.findViewById(R.id.button1);
//                Button button1 = (Button) layout2.findViewById(R.id.button2);
//                Button button2 = (Button) layout2.findViewById(R.id.button3);
//

                Spinner add_service=layout2.findViewById(R.id.add_service);
                ArrayAdapter adapter=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.add_service,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                add_service.setAdapter(adapter);


                Spinner age_range=layout2.findViewById(R.id.age_range);
                ArrayAdapter adapter_age_range=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.age_range,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                age_range.setAdapter(adapter_age_range);


                Spinner client_status=layout2.findViewById(R.id.client_status);
                ArrayAdapter adapter_client_status=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.client_status,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                client_status.setAdapter(adapter_client_status);


//                textView1.setText(textViewText);
//                button1.setText(buttonText1);
//                button2.setText(buttonText2);

                clients.addView(layout2);
            }
        });


        return view;
    }
}
