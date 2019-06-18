package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
    Button login;
    TextView forgetpass, register;
    static double latit;
    static double longit;
   public static boolean logout=false;
    LinearLayout linearLayout;
    ProgressDialog pd;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));


        String name=null,pass=null;
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
        linearLayout=findViewById(R.id.layout_login );
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
        Intent intent=new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }



    //---------------- not used----------
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
    String data="";
    private void getdata(final String user, final String pass, final Boolean loginflag) {

//        final String emailtext=username.getText().toString();
//        final String passwordtext=password.getText().toString();

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://clientapp.dcoret.com/api/auth/user/login";

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("bdb_mobile",user);
            jsonBody.put("password", pass);
            pd= new ProgressDialog(Login.this);
            pd.setMessage("جار التحقق");
            pd.show();
            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Token",response.toString());
                    data=response.toString();
                    pd.dismiss();
                    parsedata(data,loginflag);
//                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error :",error.toString());
                    data=error.toString();
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Response:  " + data, Toast.LENGTH_SHORT).show();

//                    onBackPressed();

                }
            }) {

                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    SharedPreferences pref = getSharedPreferences("LOGIN", MODE_PRIVATE);

//                    headers.put("Authorization",pref.getString("token",null) );
                    headers.put("Content-Type", "application/json");
                    headers.put("X-Requested-With", "XMLHttpRequest");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("bdb_mobile", user);
                    params.put("password", pass);
                    return params;
                }


            };
            requestQueue.add(jsonOblect);

        } catch (Exception e) {
            e.printStackTrace();
            pd.dismiss();

        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();


    }
    String token="";
    void parsedata(String loginData,Boolean loginflag){
        String success=""
                ,message="";
        try {

            JSONObject data = new JSONObject(loginData);
            success = data.getString("success");
            message = data.getString("message");
        if (loginflag) {

                JSONObject datatoken=data.getJSONObject("data");
                token=datatoken.getString("bdb_token");


            if (success.equals("true")) {
//            Intent intent=new Intent(getApplicationContext(),MainPage.class);
                SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                editor.putString("name", username.getText().toString());
                editor.putString("pass", password.getText().toString());
                editor.putString("token", token);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), BeautyMainPage.class);
                finish();
                startActivity(intent);


            } else {
                if(message.equals("This user is not active")){
                    APICall.showSweetDialog(this,"لطفاً","هذا الرقم مسجل مسبقاً, يرجى تفعيله بإدخال رمز التحقق","",true,username.getText().toString());
                }else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                }

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //------------------------------------

}
