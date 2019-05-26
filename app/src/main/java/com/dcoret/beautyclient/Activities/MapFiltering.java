package com.dcoret.beautyclient.Activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.R;

public class MapFiltering extends AppCompatActivity {

    LinearLayout map;
    FloatingActionButton filter;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_filtering);
        map=findViewById(R.id.map);
        Fragment fragment = new Mapfragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.map,fragment).commit();
        context=this;

//        filter=findViewById(R.id.filter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog  dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.filter_dialog);
                dialog.setTitle("Filtering");

                dialog.show();
            }
        });


    }
}
