package com.dcoret.beautyclient.Activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFiltering extends AppCompatActivity implements OnMapReadyCallback {

    MapView map;
    FloatingActionButton filter;
    Context context;
    Button ok;
    GoogleMap mMap;
    Boolean select_loc=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        map=findViewById(R.id.map);
        ok=findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_loc){
                    onBackPressed();
                    Register.googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Register.lat,Register.lang))
                            .title("It's Me!"));
                    Register.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Register.lat,Register.lang), 10));
                    Register.IsSelectedLocation=true;
                }
            }
        });
//        Fragment fragment = new Mapfragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.map,fragment).commit();
        context=this;

        map.onCreate(savedInstanceState);
        map.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("FRAGMENTNAME", BeautyMainPage.FRAGMENT_NAME);

//        geocoder = new Geocoder(getActivity().getApplicationContext());
//        search_map = view.findViewById(R.id.search_map);
//        searchmap_btn = view.findViewById(R.id.search_map_btn);
        map.getMapAsync(this);


//        filter=findViewById(R.id.filter);

//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog  dialog = new Dialog(context);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                dialog.setContentView(R.layout.filter_dialog);
//                dialog.setTitle("Filtering");
//
//                dialog.show();
//            }
//        });


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                .title("it's Me")
                        .position(latLng)
//                                .position(new LatLng(123.23,12333.23))
                );

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

//                map.
                Register.lat=latLng.latitude;
                Register.lang=latLng.longitude;
                select_loc=true;
            }
        });

    }
}
