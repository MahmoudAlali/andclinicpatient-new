package com.ptm.clinicpa.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.R;

import java.util.Arrays;

public class AddRelativeActivity extends AppCompatActivity {
    public static Spinner relativeSpinner,genderSpinner,ageSpinner;
    String ages[] =new String[102];
    public static EditText ClientName;
    public static String oldName="",oldAge="",oldGender="",oldRelation="";

    public static Context context;
    TextView add_text;
    boolean isEditRelative;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relative);
        context=this;
        genderSpinner = findViewById(R.id.gender);
        ageSpinner = findViewById(R.id.age_range);
        relativeSpinner = findViewById(R.id.relative);
        ClientName = findViewById(R.id.client_name);
        add_text = findViewById(R.id.add_text);

        next = findViewById(R.id.search);
        isEditRelative= getIntent().getBooleanExtra("isEditRelative",false);

        ArrayAdapter genderAdapter = new HintArrayAdapter(this, 0);
        genderAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.gender)));
        genderAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        genderSpinner.setAdapter(genderAdapter);

        ArrayAdapter relativeAdapter = new HintArrayAdapter(this, 0);
        relativeAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.relativesType)));
        relativeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        relativeSpinner.setAdapter(relativeAdapter);
        ages[0]=getString(R.string.lessThanYear);
        for (int i=1;i<=101;i++)
        {
            ages[i]=i+"";
        }

        ArrayAdapter ageAdapter = new HintArrayAdapter(this, 0);
        ageAdapter.addAll(Arrays.asList(ages));
        ageAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        ageSpinner.setAdapter(ageAdapter);

        if(isEditRelative)
        {
            ClientName.setText(getIntent().getStringExtra("name"));
            oldName=getIntent().getStringExtra("name");

            int age=Integer.parseInt(getIntent().getStringExtra("old"));
            ageSpinner.setSelection(age);
            oldAge=age+"";

            int relation=Integer.parseInt(getIntent().getStringExtra("relation"));
            relativeSpinner.setSelection(relation);
            oldRelation=relation+"";

            int gender=Integer.parseInt(getIntent().getStringExtra("gender"));
            genderSpinner.setSelection(gender+1);
            oldGender=gender+"";

            next.setText(R.string.update);
            add_text.setText(R.string.update_r);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ClientName.getText().toString().length() == 0) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_r_name), false);

                    }/* else if (ageSpinner.getSelectedItemPosition() == 0){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_r_range), false);

                    }*/
                    else if (relativeSpinner.getSelectedItemPosition() == 0){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_relation), false);

                    }

                    else if (genderSpinner.getSelectedItemPosition() == 0) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.enter_r_gender), false);
                    }
                    else
                    {
                        APICall.updateFollower(getIntent().getStringExtra("id"),ageSpinner.getSelectedItemPosition()+"",ClientName.getText().toString(),genderSpinner.getSelectedItemPosition()-1+"",relativeSpinner.getSelectedItemPosition()+"",context);
                    }
                }
            });

        }
        else
        {
            next.setText(R.string.add_relative);
            add_text.setText(R.string.add_relative_r);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (ClientName.getText().toString().length() == 0) {
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_r_name), false);

                    }/* else if (ageSpinner.getSelectedItemPosition() == 0){
                        APICall.showSweetDialog(context,getResources().getString(R.string.enter_age_r_range), false);

                    }*/
                   else if (relativeSpinner.getSelectedItemPosition() == 0){
                       APICall.showSweetDialog(context,getResources().getString(R.string.enter_relation), false);

                   }

                    else if (genderSpinner.getSelectedItemPosition() == 0) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.enter_r_gender), false);
                    }
                    else
                   {
                       APICall.addFollower(ageSpinner.getSelectedItemPosition()+"",ClientName.getText().toString(),genderSpinner.getSelectedItemPosition()-1+"",relativeSpinner.getSelectedItemPosition()+"",context);
                   }
                }
            });

        }

    }
}
