package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.GroupReservationsAdapter;
import com.dcoret.beautyclient.DataClass.ReservationClients;
import com.dcoret.beautyclient.DataClass.ReservationClientsEmployee;
import com.dcoret.beautyclient.DataClass.ServiceItems;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class GroupReservationResultFragment extends Fragment {
//    LinearLayout services_tabs;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    RecyclerView recyclerView;
    ArrayList<ReservationClientsEmployee> reservationClientsEmployees1=new ArrayList<>();
    ArrayList<ReservationClientsEmployee> reservationClientsEmployees2=new ArrayList<>();
    ArrayList<ReservationClientsEmployee> reservationClientsEmployees3=new ArrayList<>();
    ArrayList<ReservationClients> reservationClients=new ArrayList<>();



    String items[]={"1","2","3","4","5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_group_reservation_result_frag, container, false);


        BeautyMainPage.FRAGMENT_NAME="GroupReservationResultFragment";



//        ArrayList<ArrayList<ReservationClientsEmployee>> nn=new ArrayList<>();
//        nn.add(reservationClientsEmployees1);
//        nn.add(reservationClientsEmployees2);
//        nn.add(reservationClientsEmployees3);
//        reservationClientsEmployees1.add(new ReservationClientsEmployee("service1","employee1","saturday","12:00"));
//        reservationClientsEmployees1.add(new ReservationClientsEmployee("service2","employee2","sunday","12:00"));
//
//        reservationClientsEmployees2.add(new ReservationClientsEmployee("service5","employee5","monday","12:00"));
//        reservationClientsEmployees2.add(new ReservationClientsEmployee("service9","employee8","sunday","12:00"));
//
//        reservationClientsEmployees3.add(new ReservationClientsEmployee("service9","employee8","sunday","12:00"));
//        reservationClientsEmployees3.add(new ReservationClientsEmployee("service9","employee8","sunday","12:00"));
//        reservationClientsEmployees3.add(new ReservationClientsEmployee("service9","employee8","sunday","12:00"));

        for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++){
                ArrayList<ReservationClientsEmployee> reservationClientsEmployees=new ArrayList<>();
                for (int j=0;j<GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size();j++)
                reservationClientsEmployees.add(new ReservationClientsEmployee(GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).toString(),"emp "+j,"Saturday","12:00"));
                    reservationClients.add(new ReservationClients(GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString(),"salon1",reservationClientsEmployees));

        }
//        reservationClients.add(new ReservationClients("client2","salon2",reservationClientsEmployees2));
//        reservationClients.add(new ReservationClients("client3","salon3",reservationClientsEmployees3));

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        GroupReservationsAdapter adapter=new GroupReservationsAdapter(BeautyMainPage.context,reservationClients);

        recyclerView.setAdapter(adapter);

        return view;
    }
}
