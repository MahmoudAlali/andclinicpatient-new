package com.ptm.clinicpa.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.Fragments.AccountFragment;
import com.ptm.clinicpa.Fragments.ServiceFragment;
import com.ptm.clinicpa.R;
import com.google.firebase.FirebaseApp;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login,new_user,viewApp;
    TextView forgetpass
//            register
                    ;
    LocationManager lm ;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    static double latit;
    static double longit;
   public static boolean logout=false;
    LinearLayout linearLayout;
    ProgressDialog pd;
    Context context;
    String key="",value="";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        APICall.FRAGMENT_NAME="activity_login";

        key=getIntent().getStringExtra("key");
        value=getIntent().getStringExtra("value");
        context=this;
        lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);


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
        viewApp = findViewById(R.id.viewApp);
        linearLayout=findViewById(R.id.layout_login );


        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch(Exception ex) {}

                try {
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch(Exception ex) {}

                if(!gps_enabled) {
                    // notify user
                    final Dialog dialog=new Dialog(context);
                    dialog.setContentView(R.layout.lcation_service_turnon_msg);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       /* dialog.getWindow()
                .setBackgroundResource(android.R.color.transparent);*/
                    TextView cancel=dialog.findViewById(R.id.cancel);
                    TextView whatsSupport=dialog.findViewById(R.id.whatsapp_support);
                    TextView webSupport=dialog.findViewById(R.id.website_support);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    webSupport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Uri uri = Uri.parse("http://vizagep.ptm.com.sa/contact.php");
                            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(myAppLinkToMarket);
                        }
                    });
                    whatsSupport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            SupportActivity.openWhatsappChat(context);
                        }
                    });

                    dialog.show();

                }
                else {
                    Intent intent = new Intent(context, Register.class);
                    intent.putExtra("key",key);
                    intent.putExtra("value",value);
                    startActivity(intent);
                }
            }
        });

    }

    public void login(View view) {
        FirebaseApp.initializeApp(this);

        if (username.getText().toString().isEmpty()){
            APICall.showSweetDialog(Login.this,R.string.nice,R.string.EntermobnumberAlert);
        }else if (password.getText().toString().isEmpty()){
            APICall.showSweetDialog(Login.this,R.string.nice,R.string.EnterPassAlert);
        }else if (checkPhoneNumber(username.getText().toString()))
        {
            APICall.login(username.getText().toString(), password.getText().toString(), APICall.API_PREFIX_NAME+"/api/user/login", Login.this,key,value);
        }
        }
    public void forgetpass(View view) {
        APICall.reset_pass(APICall.API_PREFIX_NAME+"/api/user/password/reset", Login.this);
    }


    public void register(View view) {
    }

    public void enterguest(View view) {
        SharedPreferences.Editor prefs = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
        prefs.putString("isGuest", "1");
        prefs.apply();
        prefs.commit();
        BeautyMainPage.bdb_is_guest="1";
        APICall.getGuestToken(context,FirebaseInstanceId.getInstance().getToken());

    }
    private boolean checkPhoneNumber(String phoneNumber)
    {
        Boolean check=false;
        if(phoneNumber.length()==0 ||  phoneNumber.length()==1){
            check=false;
            APICall.showSweetDialog(context,  ((AppCompatActivity)context).getResources().getString(R.string.EntermobnumberAlert), false);

        }else {
            String prefix = phoneNumber.substring(0, 2);
            //        String prefix=text.substring(0,1);
            if (prefix.matches("05")) {
                if (phoneNumber.matches(".*[A-Z].*") || phoneNumber.matches(".*[a-z].*")) {
                    check = false;
                    APICall.showSweetDialog(context,  context.getResources().getString(R.string.insert_char_alert), false);
                } else {
                    if (phoneNumber.matches(".*[0-9].*") && phoneNumber.length() == 10) {
                        check = true;
                    } else {
                        APICall.showSweetDialog(context, context.getResources().getString(R.string.enter_ten_numbers) , false);
                        check = false;
                    }
                }
            } else {
                APICall.showSweetDialog(context,  context.getResources().getString(R.string.prefix_alert), false);
                check = false;
            }
            Log.e("Prefix", prefix);
            Log.e("Prefix", phoneNumber.matches(".*[A-Z].*") + "");
            Log.e("Prefixnum", phoneNumber.matches(".*[A-Z].*") + "");
            Log.e("Prefix", prefix);
            Log.e("CheckNumber", check + "");
        }
        return check;    }
}
