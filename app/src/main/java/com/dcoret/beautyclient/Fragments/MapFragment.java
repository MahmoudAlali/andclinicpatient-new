package com.dcoret.beautyclient.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.MapfragmentAddSite;

import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView map;
    FloatingActionButton filter;
    TextView search_map;
    Button searchmap_btn;
    Geocoder geocoder;
    double insLat, insLong;
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_map_filtering, container, false);

        BeautyMainPage.FRAGMENT_NAME =  "MAPFRAGMENT";

        map = view.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
       map.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



        Log.d("FRAGMENTNAME", BeautyMainPage.FRAGMENT_NAME);

        geocoder = new Geocoder(getActivity().getApplicationContext());
        search_map = view.findViewById(R.id.search_map);
        searchmap_btn = view.findViewById(R.id.search_map_btn);


        map.getMapAsync(this);


        searchmap_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                try {
                    String areaname = search_map.getText().toString();
                    Log.d("location", areaname);

                    list=getLocationFromAddress(areaname);
//                    LatLng latLng = list;
                    Log.d("Locationlist", list.toString());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .title(areaname)
                            .snippet("SNIPPET")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                            .position(list));
                    // Zoom in the Google Map
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list, 15));
//                        Toast.makeText(BeautyMainPage.context, statename + latitude + latLng, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    });


        return view;
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
        mMap.addMarker(new MarkerOptions()
//                .title(areaname)
                .snippet("SNIPPET")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(-34,57)));
        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34,57), 15));

    }
    LatLng list;
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(BeautyMainPage.context);
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
