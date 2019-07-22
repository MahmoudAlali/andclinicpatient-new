package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login,new_user;
    TextView forgetpass, register;
    static double latit;
    static double longit;
   public static boolean logout=false;
    LinearLayout linearLayout;
    ProgressDialog pd;
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        context=this;

        String name,pass;
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        if(logout==false) {
            name = prefs.getString("name", null);
            pass = prefs.getString("pass", null);
            try {
                if (name.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(this, BeautyMainPage.class);
                    finish();
                    startActivity(intent);
                }
            }catch (NullPointerException e){
            }
        }
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forgetpass = findViewById(R.id.forgetpass);
        register = findViewById(R.id.register);
        new_user = findViewById(R.id.new_user);
        linearLayout=findViewById(R.id.layout_login );


        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Register.class);
                startActivity(intent);
            }
        });

    }

    public void login(View view) {
        if (username.getText().toString().isEmpty()){
            APICall.showSweetDialog(Login.this,R.string.nice,R.string.EntermobnumberAlert);
        }else if (password.getText().toString().isEmpty()){
            APICall.showSweetDialog(Login.this,R.string.nice,R.string.EnterPassAlert);
        }else {
            APICall.login(username.getText().toString(), password.getText().toString(), "http://clientapp.dcoret.com/api/auth/user/login", Login.this);
        }
        }
    public void forgetpass(View view) {
        APICall.reset_pass("http://clientapp.dcoret.com/api/password/user/reset", Login.this);
    }


    public void register(View view) {
    }
}
