package com.ptmsa1.vizage.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Fragments.AccountFragment;
import com.ptmsa1.vizage.Fragments.ServiceFragment;
import com.ptmsa1.vizage.R;
import com.google.firebase.FirebaseApp;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login,new_user;
    TextView forgetpass
//            register
                    ;
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
        APICall.FRAGMENT_NAME="activity_login";

        context=this;


        //---------- get guest token-----then-- get sys info-----
        APICall.getGuestTokenThenInfo(context, FirebaseInstanceId.getInstance().getToken());



        try{
            ServiceFragment.serviceFilters.clear();
            AccountFragment.locationTitles.clear();

        }catch (Exception e){
            e.printStackTrace();
        }
        String name,pass;
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        if(logout==false) {
            name = prefs.getString("name", null);
            pass = prefs.getString("pass", null);
            try {
//                if (name.equals("admin") && pass.equals("admin")) {
//                    Intent intent = new Intent(this, BeautyMainPage.class);
//                    finish();
//                    startActivity(intent);
//                }
            }catch (NullPointerException e){
            }
        }
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forgetpass = findViewById(R.id.forgetpass);
//        register = findViewById(R.id.register);
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
        FirebaseApp.initializeApp(this);

        if (username.getText().toString().isEmpty()){
            APICall.showSweetDialog(Login.this,R.string.nice,R.string.EntermobnumberAlert);
        }else if (password.getText().toString().isEmpty()){
            APICall.showSweetDialog(Login.this,R.string.nice,R.string.EnterPassAlert);
        }else {
            APICall.login(username.getText().toString(), password.getText().toString(), APICall.API_PREFIX_NAME+"/api/auth/user/login_v1", Login.this);
        }
        }
    public void forgetpass(View view) {
        APICall.reset_pass(APICall.API_PREFIX_NAME+"/api/password/user/reset", Login.this);
    }


    public void register(View view) {
    }
}
