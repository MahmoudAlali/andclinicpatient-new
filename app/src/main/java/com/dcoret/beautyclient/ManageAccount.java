package com.dcoret.beautyclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class ManageAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        setTitle("ادارة حسابي");
    }

    public void myprofile(View view) {
        Intent intent=new Intent(this,MyProfile.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu2, menu);
        return true;
    }
    public void mypoint(View view) {
        Intent intent=new Intent(this, Point.class);
        startActivity(intent);
    }
}
