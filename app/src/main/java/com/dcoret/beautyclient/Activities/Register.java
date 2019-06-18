package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Register extends AppCompatActivity implements OnMapReadyCallback {
    Spinner gender_spinner;
    EditText name, phone, email, password, confirm_password;
    CheckBox privacy_policy;
    public static Context context;
    MapView mMapView;
    private GoogleMap googleMap;
//    public static final String TAG = Login.class.getSimpleName();
    private SMSReceiver smsReceiver;
    CheckBox show_map;
    SupportMapFragment mapFragment;
    String  activation_number="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        privacy_policy = findViewById(R.id.privacy_policy);
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
            }
        });
        gender_spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(adapter);
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
        } else {
     APICall.new_user(email.getText().toString(), name.getText().toString(), phone.getText().toString(), password.getText().toString()
                    , confirm_password.getText().toString(), "154", "-34", "1", "1","http://clientapp.dcoret.com/api/auth/user/register/new_user", context);
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

}
