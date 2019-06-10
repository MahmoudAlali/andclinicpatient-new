package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Register extends AppCompatActivity implements OnMapReadyCallback {
    Spinner gender_spinner;
    EditText name, phone, email, password, confirm_password;
    CheckBox privacy_policy;
    public static Context context;
    MapView mMapView;

    private GoogleMap googleMap;


    public static final String TAG = MainActivity.class.getSimpleName();

    private SMSReceiver smsReceiver;
    CheckBox show_map;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("انشاء حساب");
        context = this;
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        privacy_policy = findViewById(R.id.privacy_policy);
//        mMapView=findViewById(R.id.map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setVisibility(View.INVISIBLE);

        show_map = findViewById(R.id.show_map);
        show_map.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mapFragment.getView().setVisibility(View.VISIBLE);
                } else {

                    mapFragment.getView().setVisibility(View.INVISIBLE);

                }

//                test--------------
//            APICall.rateApp("5","http://clientapp.dcoret.com/api/rating/rateApp",Register.this);
//            APICall.detailsUser("http://clientapp.dcoret.com/api/auth/user/detailsUser",Register.this);
            }
        });


        gender_spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(adapter);


//        mMapView.onResume(); // needed to get the map to display immediately


    }




    //--- when confirm register and click ok-----
    public void registerAction(View view) {
        if (phone.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
            showSweetDialog(Register.this, "لطفاً!", "من فضلك ادخل رقم الهاتف و كلمة السر.", false);
        } else if (!password.getText().toString().equals(confirm_password.getText().toString()) && !password.getText().toString().isEmpty()) {
            showSweetDialog(Register.this, "لطفاً!", "كلمة السر غير متطابقة.", false);
        } else if (password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty()) {
            showSweetDialog(Register.this, "لطفاً!", "من فضلك ادخل رقم الهاتف و كلمة السر.", false);
        } else if (!privacy_policy.isChecked()) {
            showSweetDialog(Register.this, "لطفاً!", "يجب الموافقة على سياسة التطبيق.", false);
        } else {

//            getdata();
//        makePost();
//            APICall.new_user("jkl@gamil.com","test","0512345698","test","test","","","","","http://clientapp.dcoret.com/api/auth/user/register/new_user",Register.this);

     APICall.new_user(email.getText().toString(), name.getText().toString(), phone.getText().toString(), password.getText().toString()
                    , confirm_password.getText().toString(), "154", "-34", "1", "1","http://clientapp.dcoret.com/api/auth/user/register/new_user", context);
//        try {
//            JSONObject jsonObject=new JSONObject(newuser_response);
//            String success=jsonObject.getString("success");
//            JSONObject data=jsonObject.getJSONObject("data");
//            if (success.equals("true")){
//                SharedPreferences.Editor editor=getSharedPreferences("LOGIN",MODE_PRIVATE).edit();
//                editor.putString("name",phone.getText().toString());
//                editor.putString("pass",password.getText().toString());
//                editor.putString("token",data.getString("token"));
//                editor.putString("activation_number",jsonObject.getString("activation number :"));
//
//                //----------- Enter validation code---------
//                showSweetDialog(context,"لطفاً","ادخل رمز التحقق:",true);
//
//            }else {
//
//            }
//        }catch (JSONException e){
//            Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_LONG).show();
//        }
        }


    }

    void showDialog(String title, String message, Boolean editText) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        if (editText) {
            final EditText input = new EditText(Register.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);
        }
//        builder.setIcon(R.drawable.key);
        // A null listener allows the button to dismiss the dialog and take no further action.
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
        builder.show();
    }


    void showSweetDialog(final Context context, String texttitle, String textmessage, Boolean iscode) {


        if (iscode) {
            final Dialog dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.confirm_code_layout);
            TextView message = dialog.findViewById(R.id.message);
            TextView title = dialog.findViewById(R.id.title);
            title.setText(texttitle);
            final EditText code = dialog.findViewById(R.id.code);
            TextView confirm = dialog.findViewById(R.id.confirm);
            TextView resend_code = dialog.findViewById(R.id.resend_code);
            message.setText(textmessage);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (code.getText().toString().equals(activation_number)) {
                        dialog.cancel();
                        //----------toast for congratulations---------
//                        active_account(context,activation_number);
                        APICall.activeAccount(  "http://clientapp.dcoret.com/api/auth/user/register/activate",
                                getSharedPreferences("LOGIN",MODE_PRIVATE).getString(activation_number,null),
                                Register.this);

//                        http://clientapp.dcoret.com/api/auth/user/register/activate/6552
                        Toast.makeText(context, "تم انضمامك لعائلة .... بنجاح,يمكنك الآن التمتع بالخدمات الرائعة", Toast.LENGTH_LONG).show();
                        //---------- go to offers ----------
                        finish();
                        Intent intent = new Intent(Register.this, Offers.class);
                        startActivity(intent);
                    } else {
                        dialog.cancel();
                        showSweetDialog(Register.this, "لطفاً!", "الرمز المدخل خاطئ, الرجاء إعادة المحاولة", true);
                    }
                }
            });
            dialog.show();
        } else {
            final Dialog dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.sweet_dialog_layout);
            TextView message = dialog.findViewById(R.id.message);
            TextView title = dialog.findViewById(R.id.title);
            TextView confirm = dialog.findViewById(R.id.confirm);
//                TextView resend_code = dialog.findViewById(R.id.resend_code);
            title.setText(texttitle);
            message.setText(textmessage);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.googleMap.setMyLocationEnabled(true);

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub

                googleMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));

                LatLng myLatLng = new LatLng(arg0.getLatitude(),
                        arg0.getLongitude());
                CameraPosition myPosition = new CameraPosition.Builder()
                        .target(myLatLng).zoom(10f).bearing(90).tilt(30).build();

                googleMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(myPosition));
            }
        });

    }

    ProgressDialog pd;



    private final OkHttpClient client = new OkHttpClient();

    private void makePost() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("bdb_email", email.getText().toString())
                .addFormDataPart("bdb_name", name.getText().toString())
                .addFormDataPart("bdb_mobile", phone.getText().toString())
                .addFormDataPart("password", password.getText().toString())
                .addFormDataPart("c_password", confirm_password.getText().toString())
                .addFormDataPart("bdb_loc_long", "154")
                .addFormDataPart("bdb_loc_lat", "-34")
                .addFormDataPart("bdb_city", "1")
                .addFormDataPart("bdb_gender", "1")
                .build();
        String URL = "http://clientapp.dcoret.com/api/auth/user/register/new_user";
        final okhttp3.Request requestb = new okhttp3.Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();
        Log.d("request", requestb.toString());
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                try (okhttp3.Response response = client.newCall(requestb).execute()) {
                    Log.d("Response", response.toString());
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        a.start();
    }


    //    private static final OkHttpClient client = new OkHttpClient();
    //    static ProgressDialog pd;
//     OkHttpClient client = new OkHttpClient();
//    static ProgressDialog pd;
    static String error = "";
    String token="";
    String success="";
    String  activation_number="";
    public void register(final String email, final String name, final String phone, final String password, final String confirm_password, String loc_long
            , String loc_lat, String city, String gender, final Context context) {
        pd=new ProgressDialog(context);
               pd.setMessage("جار التحقق...");
        pd.show();
        Thread a = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("bdb_email", email)
                        .addFormDataPart("bdb_name", name)
                        .addFormDataPart("bdb_mobile", phone)
                        .addFormDataPart("password", password)
                        .addFormDataPart("c_password", confirm_password)
                        .addFormDataPart("bdb_loc_long", "154")
                        .addFormDataPart("bdb_loc_lat", "-34")
                        .addFormDataPart("bdb_city", "1")
                        .addFormDataPart("bdb_gender", "1")
                        .build();
                String URL = "http://clientapp.dcoret.com/api/auth/user/register/new_user";
                final okhttp3.Request requestb = new okhttp3.Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();
                Log.d("request", requestb.toString());


                try (okhttp3.Response response = client.newCall(requestb).execute()) {
                    Log.d("Response", response.toString());



                       pd.dismiss();
                    if (!response.isSuccessful()) {
                        error = response.toString();
                       pd.dismiss();
                    }

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    String body=response.body().string();
                    Log.d("body",body);

                        JSONObject object=new JSONObject(body);
                                success=object.getString("success");
                               JSONObject data=object.getJSONObject("data");
                               token=data.getString("token");
                                activation_number=object.getString("activation number :");

                    SharedPreferences.Editor prefs = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                    prefs.putString("name",name);
                    prefs.putString("pass",password);
                    prefs.putString("token",token);
                    prefs.apply();
                    prefs.commit();


                    runOnUiThread(new Runnable() {
                        public void run() {
//                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            showSweetDialog(context,"لطفاً","ادخل رمز التحقق:",true);

                        }
                    });

                } catch (final IOException e) {
//                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final JSONException e) {
//                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        a.start();
//           Toast.makeText(Register.context,error,Toast.LENGTH_LONG).show();
    }

//    private static final OkHttpClient client = new OkHttpClient();

    public  void active_account(Context context, final String active_number){
//        pd=new ProgressDialog(context);
        Thread a=new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
//       pd.setMessage("جار التحقق...");
//        pd.show();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("token",active_number)
                        .build();
                String URL = "http://clientapp.dcoret.com/api/auth/user/register/activate";
                final okhttp3.Request requestb = new okhttp3.Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();
                Log.d("request",requestb.toString());


                try (okhttp3.Response response = client.newCall(requestb).execute()) {
                    Log.d("Response",response.toString());
//                       pd.dismiss();
                    if (!response.isSuccessful()){
                        error =response.toString();
//                       pd.dismiss();
                    }

//                    Headers responseHeaders = response.headers();
//                    for (int i = 0; i < responseHeaders.size(); i++) {
//                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                    }

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        a.start();
//           Toast.makeText(Register.context,error,Toast.LENGTH_LONG).show();
    }
}
