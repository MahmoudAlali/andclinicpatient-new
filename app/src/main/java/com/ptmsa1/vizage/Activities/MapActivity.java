package com.ptmsa1.vizage.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ptmsa1.vizage.Activities.ProviderSerAndOfferPKG.MapTap;
import com.ptmsa1.vizage.R;
import com.ptmsa1.vizage.test.MapWrapperLayout;

import java.text.DecimalFormat;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    LocationListener locationListener;
//    MapWrapperLayout mapWrapperLayout;
    LayoutInflater layoutInflater;
    TextView service_sw,offer_sw;
    Double lat,lang;

    Context context;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_location_map_layout);
//        mapWrapperLayout = findViewById(R.id.map_relative_layout);

        context=this;
        lat=getIntent().getDoubleExtra("lat",0.0d);
        lang=getIntent().getDoubleExtra("lang",0.0d);

        // Fixing Later Map loading Delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mMapView = new MapView(context);
//                    mapWrapperLayout.addView(mMapView);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                if (mMapView!=null) {
                                    Thread.sleep(500);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mMapView.onCreate(savedInstanceState);
                            mMapView.getMapAsync(MapActivity.this);
                            mMapView.onResume(); // needed to get the map to display immediately
                        }
                    });
                }catch (Exception ignored){
                    ignored.printStackTrace();
                }
            }
        }).start();

        MapsInitializer.initialize(context);
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(final GoogleMap mMap)
    {
//        googleMap = mMap;

        //------------------------ info title ---------------------
        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
//        mapWrapperLayout.init(googleMap, getPixelsFromDp(BeautyMainPage.context, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle thecase where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        final DecimalFormat df = new DecimalFormat("0.0");

        //---------------show custom info title-------------------
        googleMap.setMyLocationEnabled(true);

        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Booking Place"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // for enter add + button and reserve service
    }

}
