package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.R;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView forgetpass, register;
    static double latit;
    static double longit;
   public static boolean logout=false;
    LinearLayout linearLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));

//        setContentView(R.layout.activity_beauty_main_page_2);
        setTitle("تسجيل الدخول");


        String name=null,pass=null;
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        if(logout==false) {
            name = prefs.getString("name", null);
            pass = prefs.getString("pass", null);
            try {
                if (name.equals("admin") && pass.equals("admin")) {
//                    Toast.makeText(getApplicationContext(),"main",Toast.LENGTH_LONG).show();

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
        linearLayout=findViewById(R.id.layout_login );
//        forgetpass();

//        getlocation();
    }

    public void login(View view) {



        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {

            final Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.type_services_dialog);
            Button beauty=dialog.findViewById(R.id.beauty);
            Button fashion=dialog.findViewById(R.id.fashion);


            beauty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // shared preference
                    SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                    editor.putString("name",username.getText().toString());
                    editor.putString("pass", password.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), BeautyMainPage.class);
                    dialog.cancel();

//                    Toast.makeText(getApplicationContext(),"beauty",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(intent);
                }
            });

            fashion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                    editor.putString("name",username.getText().toString());
                    editor.putString("pass", password.getText().toString());
                    editor.apply();
//                    Toast.makeText(getApplicationContext(),"fashion",Toast.LENGTH_LONG).show();

                    dialog.cancel();
                    Intent intent = new Intent(getApplicationContext(), BeautyMainPage.class);
                    finish();
                    startActivity(intent);
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();





        } else {
            Toast.makeText(this, "اسم المستخدم او كلمة السر خطأ... ", Toast.LENGTH_LONG).show();
        }


    }


   static LocationManager locationManager;
   static LocationListener locationListener;
   static Location location1;
   static double latitud,longitud;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public   void getlocation() {
//        Intent intent=new Intent(getApplicationContext(),ForgetMyPass.class);
//        startActivity(intent);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud=location.getLatitude();
                longitud=location.getLongitude();
//                        Toast.makeText(getApplicationContext()
//                                ,"lat: "+location.getLatitude()+" long: "+location.getLongitude(),Toast.LENGTH_LONG).show();
//                    register.setText(location.getLatitude()+" : "+location.getLongitude());

               }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
            }
        };

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                },10);
//                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            configure();
        }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configure();
                return;
        }
    }

    @SuppressLint("MissingPermission")
    void configure(){
//        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    @SuppressLint("MissingPermission")
    public void register(View view) {
        Intent intent=new Intent(getApplicationContext(),Register.class);
        startActivity(intent);


//        LatLng latLngA = new LatLng(12.3456789,98.7654321);
//        LatLng latLngB = new LatLng(98.7654321,12.3456789);

//        Location locationA = new Location("point A");
//        locationA.setLatitude(32.7792842);
//        locationA.setLongitude(35.8816735);
//        Location locationB = new Location("point B");
//        locationB.setLatitude(32.626967);
//        locationB.setLongitude(36.103520);
//
//        double distance = locationA.distanceTo(locationB);
//        distance=distance/1000;
//        register.setText(distance+"");

    }


    public void forgetpass(View view) {
        Intent intent=new Intent(this,ForgetMyPass.class);
        startActivity(intent);


    }
}
