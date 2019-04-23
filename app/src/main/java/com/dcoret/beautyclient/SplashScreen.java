package com.dcoret.beautyclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context=this;
        final Thread splash=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    String name=null,pass=null;
                    SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);

                        name = prefs.getString("name", null);
                        pass = prefs.getString("pass", null);
                            if (name.equals("admin") && pass.equals("admin")) {
//                    Toast.makeText(getApplicationContext(),"main",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(context, BeautyMainPage_2.class);
                                finish();
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(context, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    Intent intent = new Intent(context, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        };
        splash.start();
    }
}
