package com.ptmsa1.vizage.Activities;

import android.Manifest;
import android.app.AlertDialog;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class Register extends AppCompatActivity implements OnMapReadyCallback {


    EditText name, phone, email, password, confirm_password;
    CheckBox privacy_policy;
    public static Context context;
    public static int ACCESS_FINE_LOCATION = 90;
    MapView mMapView;
    public static GoogleMap googleMap;
    //    public static final String TAG = Login.class.getSimpleName();
    private SMSReceiver smsReceiver;
    CheckBox show_map;
    SupportMapFragment mapFragment;
    String activation_number = "";
    Button current_location,another_location;
    public static double lat,lang;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    public static Boolean IsSelectedLocation=false;
    public static String my_description,description;
    Geocoder geocoder;
    public static boolean iscurrent_location=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        APICall.FRAGMENT_NAME="activity_register";

        context = this;
        name = findViewById(R.id.name);
        current_location = findViewById(R.id.cur_location);
        another_location = findViewById(R.id.anthor_location);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        privacy_policy = findViewById(R.id.privacy_policy);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
         geocoder=new Geocoder(context);

        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestLocationPermission();
        }

        //----------------- go to anther location interface-------------------------
        another_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MapFiltering.class);
                startActivity(intent);
            }
        });


//        cities_spinner = findViewById(R.id.gender_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        cities_spinner.setAdapter(adapter);
    }

    //--- when confirm register and click ok-----
    public void registerAction(View view) {
        if (phone.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            APICall.showSweetDialog(Register.this, R.string.ExuseMeAlert, R.string.EnterMobAndPass);
        } else if (!password.getText().toString().equals(confirm_password.getText().toString()) && !password.getText().toString().isEmpty()) {
            APICall.showSweetDialog(Register.this, R.string.ExuseMeAlert, R.string.PasswordNotMatchAlert);
        } else if (password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty()) {
            APICall.showSweetDialog(Register.this, R.string.ExuseMeAlert, R.string.EnterMobAndPass);
        } else if (!privacy_policy.isChecked()) {
            APICall.showSweetDialog(Register.this, R.string.ExuseMeAlert, R.string.ApplicationPolicyAlert);
            privacy_policy.setChecked(true);
        } else if (!IsSelectedLocation){
            APICall.showSweetDialog(Register.this,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.cant_reg_without_loc) );
        }else {
//            Log.e("lat_Lang",lat+","+lang);
            APICall.new_user(phone.getText().toString(),email.getText().toString(),"1",password.getText().toString()
            ,confirm_password.getText().toString(),lang+"",lat+"",description,my_description,getFilterLocationDetails()+"",getFilterLocationDetailsAr()+"",APICall.API_PREFIX_NAME+"/api/auth/user/register/new_user_v2",Register.this);
        }
    }

    //----------------- show map ----------------------
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
        //-----------------  select current location -------------
        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iscurrent_location=true;
                final Dialog d=new Dialog(context);
                d.setContentView(R.layout.enter_text_dialog);
                TextView title=d.findViewById(R.id.title);
                final EditText addr_title=d.findViewById(R.id.message);
                TextView ok=d.findViewById(R.id.confirm);
                title.setText("Please enter Address Details");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        my_description=addr_title.getText().toString();
                        try {
                            d.cancel();
                            Toast.makeText(Register.this,"Please Wait while Processing",Toast.LENGTH_SHORT).show();
                            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                                @Override
                                public void onMyLocationChange(Location arg0) {
                                    Log.e("iscurrent",Register.iscurrent_location+"");

                                    if (iscurrent_location){

                                        // TODO Auto-generated method stub
                                        lat = arg0.getLatitude();
                                    lang = arg0.getLongitude();
                                    googleMap.clear();
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title(my_description));

                                    LatLng myLatLng = new LatLng(arg0.getLatitude(),
                                            arg0.getLongitude());
                                    CameraPosition myPosition = new CameraPosition.Builder()
                                            .target(myLatLng).zoom(10f).bearing(90).tilt(30).build();
                                    List<Address> addresses = null;
                                    try {
                                        addresses = geocoder.getFromLocation(lat, lang, 1);
                                        description = addresses.get(0).getAdminArea();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("Desc", description + ":" + my_description);
                                    googleMap.animateCamera(
                                            CameraUpdateFactory.newCameraPosition(myPosition));
                                }
                            }
                            });

                            IsSelectedLocation=true;
                        }catch (Exception e){
                            e.printStackTrace();
                            String text=getResources().getString(R.string.err);
                            Toast.makeText(Register.context,"",Toast.LENGTH_SHORT).show();
                        d.cancel();
                        }
                        }
                });
                d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                d.show();
            }

            });


    }

    //-------------------- request permission-------------------
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                &&
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
        ){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission Needed because of This and That")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Register.this,new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.ACCESS_COARSE_LOCATION
                            },ACCESS_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton(R.string.CancelAlert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_PHONE_STATE
                    ,Manifest.permission.ACCESS_NETWORK_STATE
            },ACCESS_FINE_LOCATION);
        }
    }
    public static String admin="",adminAr="",locality="",localityAr="",sublocality="",sublocalityAr="",thoroughfare="",thoroughfareAr="";

    public static String getFilterLocationDetails(){
        String filetr="";
        if (locality.equals("") || sublocality.equals("")) {
        }else {
             filetr =
                    "{" +
                            "\"lang\":\"en\"," +
                            "\"admin\":\"" + admin + "\"," +
                            "\"locality\":\"" + locality + "\"," +
                            "\"sub_locality\":\"" + sublocality + "\"," +
                            "\"thoroughfare\":\"" + thoroughfare + "\"" +
                            "}";
        }
        return filetr;

    }
    public static String getFilterLocationDetailsAr(){

        String filetr="";
        if (locality.equals("") || sublocality.equals("")) {
        }else {
             filetr =
                    "{" +
                            "\"lang\":\"ar\"," +
                            "\"admin\":\"" + adminAr + "\"," +
                            "\"locality\":\"" + localityAr + "\"," +
                            "\"sub_locality\":\"" + sublocalityAr + "\"," +
                            "\"thoroughfare\":\"" + thoroughfareAr + "\"" +
                            "}";
        }
        return filetr;

    }
}
