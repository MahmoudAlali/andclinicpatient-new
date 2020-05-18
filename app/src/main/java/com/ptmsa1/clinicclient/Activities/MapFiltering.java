package com.ptmsa1.clinicclient.Activities;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptmsa1.clinicclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapFiltering extends AppCompatActivity implements OnMapReadyCallback {

    MapView map;
    Context context;
    Button ok;
    GoogleMap mMap;
    Boolean select_loc=false;
    Geocoder geocoder=new Geocoder(this,new Locale("en"));
    Geocoder geocoderAr=new Geocoder(this,new Locale("ar"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        map=findViewById(R.id.map);
        ok=findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------- for site in map from anther or current----------------
                Register.iscurrent_location=false;
                if (select_loc){
                    onBackPressed();
                    Register.googleMap.clear();
                    Register.googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Register.lat,Register.lang))
                            .title(Register.my_description));
                    Register.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Register.lat,Register.lang), 10));
                    Register.IsSelectedLocation=true;
                }
                Log.e("iscurrent",Register.iscurrent_location+"");
            }
        });
        context=this;
        map.onCreate(savedInstanceState);
        map.onResume(); // needed to get the map to display immediately
        try {
            //-------------- init map-----------
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("FRAGMENTNAME", BeautyMainPage.FRAGMENT_NAME);
        map.getMapAsync(this);
    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                mMap.clear();
                final Dialog d=new Dialog(context);
                d.setContentView(R.layout.enter_text_dialog);
                TextView title=d.findViewById(R.id.title);
                final EditText addr_title=d.findViewById(R.id.message);
                TextView ok=d.findViewById(R.id.confirm);
                title.setText("Please enter Address Details");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Register.my_description=addr_title.getText().toString();
                        try {
                            List<Address> addresses=geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            List<Address> addressesAr=geocoderAr.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            Register.description=addresses.get(0).getAddressLine(0);
                            mMap.addMarker(new MarkerOptions()
                                    .title(Register.my_description)
                                    .position(latLng)
                            );
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                            Register.lat=latLng.latitude;
                            Register.lang=latLng.longitude;
                            Register.admin=addresses.get(0).getAdminArea();
                            Register.adminAr=addressesAr.get(0).getAdminArea();
                            Register.locality=addresses.get(0).getLocality();
                            Register.localityAr=addressesAr.get(0).getLocality();
                            Register.sublocality=addresses.get(0).getSubLocality();
                            Register.sublocalityAr=addressesAr.get(0).getSubLocality();
                            Register.thoroughfare=addresses.get(0).getThoroughfare();
                            Register.thoroughfareAr=addressesAr.get(0).getThoroughfare();
                            select_loc=true;

                            d.cancel();
                        }catch (Exception e){
                            e.printStackTrace();
                            d.cancel();
                        }
                    }
                });
                d.show();

            }
        });

    }
}