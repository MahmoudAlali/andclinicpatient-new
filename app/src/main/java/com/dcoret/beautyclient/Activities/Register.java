package com.dcoret.beautyclient.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dcoret.beautyclient.R;

public class Register extends AppCompatActivity {
Spinner gender_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("انشاء حساب");

        gender_spinner=findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(adapter);
    }
}
