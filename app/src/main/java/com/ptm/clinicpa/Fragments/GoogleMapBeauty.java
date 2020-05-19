package com.ptm.clinicpa.Fragments;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ptm.clinicpa.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapBeauty extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    EditText search_map;
    Button search_map_btn;
    GoogleMap googleMap;
    Double insLat,insLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        search_map=findViewById(R.id.search_map);
        search_map_btn=findViewById(R.id.search_map_btn);

        search_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String areaname = search_map.getText().toString();
                    Log.d("location",areaname);
                    getLocationFromAddress(areaname);

//                    if (list.size() > 0)
                        LatLng latLng = list;
                        Log.d("Locationlist",list.toString());
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions()
                                .title(areaname)
                                .snippet("SNIPPET")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                .position(latLng));
                        // Zoom in the Google Map
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
    LatLng list;
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            Log.d("address",lat+" , "+lng);
//            Map<String,String> map=new HashMap();
            list=new LatLng(lat,lng);

            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
