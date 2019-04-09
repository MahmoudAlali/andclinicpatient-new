package com.dcoret.beautyclient;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateOffers extends AppCompatActivity {

    TextView numserv;
    Button ok;
    Spinner dateoffer;
    NestedScrollView nestedScrollView;
    LinearLayout cancel_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offers);
        ok=findViewById(R.id.ok);
        numserv=findViewById(R.id.numserv);
        dateoffer=findViewById(R.id.limitoffer);
        nestedScrollView=findViewById(R.id.scrollview);
        cancel_add=findViewById(R.id.cancel_layout);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.days,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateoffer.setAdapter(adapter);

    }
    Button cancel,save;
    LinearLayout main;
    int num;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createoffer(View view) {
         main= new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setWeightSum(3);
        try {
            nestedScrollView.addView(main);
            main.setVisibility(View.VISIBLE);

        }catch (Exception e){
            main.setVisibility(View.VISIBLE);
        }
          num=Integer.parseInt(numserv.getText().toString());
        for (int i=0; i<num;i++){
//         TextView text =  new TextView(this);
//         text.setText("ok"+i);
            View child = getLayoutInflater().inflate(R.layout.create_offer_layout, null);
             main.addView(child);


        }

        ok.setVisibility(View.INVISIBLE);
        numserv.setVisibility(View.INVISIBLE);
        dateoffer.setVisibility(View.INVISIBLE);
        cancel=new Button(this);
        save=new Button(this);
        cancel_add.setGravity(View.FOCUS_RIGHT);
        cancel.setText("Cancel");
        save.setText("Save");
        cancel.setBackgroundColor(Color.TRANSPARENT);
        save.setBackgroundColor(Color.TRANSPARENT);

//        cancel_add.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        cancel_add.addView(cancel);
        cancel_add.addView(save);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numserv.setVisibility(View.VISIBLE);
                dateoffer.setVisibility(View.VISIBLE);
                ok.setVisibility(View.VISIBLE);


                nestedScrollView.removeView(main);
                cancel.setVisibility(View.GONE);
                save.setVisibility(View.GONE);
            }

        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Offer Activated",Toast.LENGTH_LONG).show();
            }
        });


    }
}
