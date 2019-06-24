package com.dcoret.beautyclient;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.Activities.Register;
import com.dcoret.beautyclient.Fragments.ServiceFragment;


public class AddReservation extends AppCompatActivity
       {

    Fragment fragment;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Spinner servicePlace;
    LinearLayout myRoot;
    EditText numreservation;
    Spinner spinner1,spinner2;
    static Button dateandtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        servicePlace=findViewById(R.id.servicePlace);
        dateandtime=findViewById(R.id.dateandtime);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.service_place,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        servicePlace.setAdapter(adapter);
        myRoot = (LinearLayout) findViewById(R.id.myroot);
        numreservation = (EditText) findViewById(R.id.num_reservation);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });


    servicePlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position!=0) {
                Intent intent = new Intent(getApplicationContext(), AddLocation.class);
                startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    }
    public void selectDateAndTime(View view) {
        Intent intent=new Intent(this,ResevationDate.class);
        startActivity(intent);
    }
   @Override
   public void onBackPressed() {
       super.onBackPressed();
   }
   public void addreservation(View view) {
       myRoot.removeAllViews();
       View child = getLayoutInflater().inflate(R.layout.layout_name_reservation_and_age_and_employee, null);
       LinearLayout a = new LinearLayout(this);
       spinner1=child.findViewById(R.id.agespinner);
       ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_item);
       adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       spinner1.setAdapter(adapter1);

       spinner2=child.findViewById(R.id.employeespinner);
       ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.employee,android.R.layout.simple_spinner_item);
       adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       spinner2.setAdapter(adapter2);

       a.setOrientation(LinearLayout.VERTICAL);
       a.addView(child);
       myRoot.addView(a);
   }
   public void addMultiReservation(View view) {

       if (numreservation.getText().toString().isEmpty()){
          APICall.showSweetDialog(AddReservation.this,R.string.ExuseMeAlert,R.string.EnterNumberofCustomers);
       }else {
           myRoot.removeAllViews();
           for (int i = 0; i < Integer.parseInt(numreservation.getText().toString()); i++) {
               View child = LayoutInflater.from(this).inflate(R.layout.layout_name_reservation_and_age_and_employee,myRoot, false);
               spinner1=child.findViewById(R.id.agespinner);
               ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_item);
               adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
               spinner1.setAdapter(adapter1);

               spinner2=child.findViewById(R.id.employeespinner);
               ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.employee,android.R.layout.simple_spinner_item);
               adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
               spinner2.setAdapter(adapter2);

               LinearLayout a = new LinearLayout(this);
               a.setOrientation(LinearLayout.VERTICAL);
               a.addView(child);
               myRoot.addView(a);
           }
       }

}
   public void addtoshoppingcart(View view) {
            // We Received your request,Will reply within 30 minutes
//            APICall.showSweetDialog(AddReservation.this,R.string.Null,R.string.Recievedrequest);
       final Dialog dialog = new Dialog(AddReservation.this);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
       dialog.setContentView(R.layout.sweet_dialog_layout);
       TextView message = dialog.findViewById(R.id.message);
       TextView title = dialog.findViewById(R.id.title);
       TextView confirm = dialog.findViewById(R.id.confirm);
       //                TextView resend_code = dialog.findViewById(R.id.resend_code);
       title.setText("");
       message.setText(R.string.Recievedrequest);
       confirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.cancel();
               onBackPressed();
           }
       });
       dialog.show();

            }

           public static void setDateAndTime(String date,String time){


               dateandtime.setText(date+"--"+time);


           }
}
