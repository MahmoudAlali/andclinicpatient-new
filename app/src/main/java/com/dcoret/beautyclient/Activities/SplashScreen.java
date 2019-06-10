package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dcoret.beautyclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(SplashScreen.this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        context = this;
        final Thread splash = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    String name = null, pass = null;
                    SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);

                    name = prefs.getString("name", null);
                    pass = prefs.getString("pass", null);
//                    Log.d("name",name);
                    if (!name.isEmpty()) {
//                    Toast.makeText(getApplicationContext(),"main",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, Offers.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.d("name","null");
                    Intent intent = new Intent(context, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        };
        splash.start();


//        sendRequest();


        final String TAG = "FireBase";
        try {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                           token = task.getResult().getToken();

                            // Log and toast
//                        String msg = getString(1, token);
                            Log.d(TAG, token);
//                            Toast.makeText(SplashScreen.this, token, Toast.LENGTH_SHORT).show();
                            System.out.println(token);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    String token="cuFy-L6jZrU:APA91bGfYvXg99FhxscZUQVarBLyRhSA28y3EfaV1LUzL8FKQf0s9eM9SHkSYYzP3LuzVzD2ngUDfdrIb1wJBufqzm3LmLTJVtNcF5edrrECwx-nrDtNsfr5nuhjzplMGJJTglek2M0V";
    static String token;
    String api_key_header_value = "Key=AAAA6gZ1CO8:APA91bHEg19SqKpRdvifPk3-o-nWwDm350IZaNjqX0yy0eHkRUnv1hSBHN6zaQZR0ZvoINJUNX1zbRMDto0W4ePuFwckOOBabMECCscYuwyisY4YEGHhCr10kjEVPoifc9IOz_x7dP0q";



    private void sendRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url= "https://fcm.googleapis.com/fcm/send";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();//Here ServerError shows
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                String accessToken = token;
                params.put("to",accessToken);
                params.put("title", "This is string message");
                params.put("body", "This is string message");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                header.put("Authorization",api_key_header_value);
                header.put("Content-Type","application/json");
                return header;
            }
        }
                ;

        requestQueue.add(request);
    }
}
