package com.dcoret.beautyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("");
    }

    public void editprofile(View view) {
        Intent intent=new Intent(this, EditProfile.class);
        startActivity(intent);

    }
}
