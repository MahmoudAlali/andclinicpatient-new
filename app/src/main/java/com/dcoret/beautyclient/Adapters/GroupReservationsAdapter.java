package com.dcoret.beautyclient.Adapters;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.ReservationDetails;
import com.dcoret.beautyclient.DataClass.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.DataClass.DataReservation;
import com.dcoret.beautyclient.DataClass.ReservationClients;
import com.dcoret.beautyclient.DataClass.SerchGroupBookingData;
import com.dcoret.beautyclient.Fragments.EditReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.InvoioceFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupReservationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String items[];
    RecyclerView.ViewHolder holder;
//    ArrayList <DataService> services;
    ArrayList <BookingAutomatedBrowseData> bookingAutomatedBrowseData;
    ArrayList<DataReservation> reservations;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    ArrayList<ReservationClients> reservationClients;
    ArrayList<SerchGroupBookingData> serchGroupBookingData;
    public GroupReservationsAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public GroupReservationsAdapter(Context context, ArrayList<SerchGroupBookingData> serchGroupBookingData){
        this.context=context;
        this.serchGroupBookingData=serchGroupBookingData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.result_group_reservation_layout,parent,false);
        Item item=new Item(row, new MyClickListener() {
            @Override
            public void resrve(int p) {
                Toast.makeText(context,"ok",Toast.LENGTH_LONG).show();

            }

            @Override
            public void more(int p) {
                Toast.makeText(context,"ok",Toast.LENGTH_LONG).show();

            }
        });
        return item;


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        Log.e("bookingAutomated",serchGroupBookingData.size()+"");
        return serchGroupBookingData.size();
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyClickListener listener;

            TextView client_name,salon_name;
            LinearLayout service_layout;

        public Item(View itemView, MyClickListener listener) {
            super(itemView);
            client_name=itemView.findViewById(R.id.client_name);
            salon_name=itemView.findViewById(R.id.salon_name);
            service_layout=itemView.findViewById(R.id.service_layout);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.reserv_btn:
                    listener.resrve(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }
    public interface MyClickListener {
        void resrve(int p);
        void more(int p);
    }
}
