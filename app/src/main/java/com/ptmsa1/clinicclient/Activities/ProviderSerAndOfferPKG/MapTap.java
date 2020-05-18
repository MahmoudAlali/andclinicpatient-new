package com.ptmsa1.clinicclient.Activities.ProviderSerAndOfferPKG;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.R;
import com.ptmsa1.clinicclient.test.MapWrapperLayout;

import java.text.DecimalFormat;

public class MapTap extends Fragment implements OnMapReadyCallback {

    View view;
    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    LocationListener locationListener;
    MapWrapperLayout mapWrapperLayout;
    LayoutInflater layoutInflater;
    TextView service_sw,offer_sw;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState)
    {
        Toast.makeText(BeautyMainPage.context,"CREATEd",Toast.LENGTH_LONG);
        view = inflater.inflate(R.layout.provider_location_map_layout, container, false);

        mapWrapperLayout = view.findViewById(R.id.map_relative_layout);

        // Fixing Later Map loading Delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mMapView = new MapView(BeautyMainPage.context);
                    mapWrapperLayout.addView(mMapView);
                    ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
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
                            mMapView.getMapAsync(MapTap.this);
                            mMapView.onResume(); // needed to get the map to display immediately
                        }
                    });
                }catch (Exception ignored){
                    ignored.printStackTrace();
                }
            }
        }).start();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        return view;
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(final GoogleMap mMap)
    {
        googleMap = mMap;

        //------------------------ info title ---------------------
        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(googleMap, getPixelsFromDp(BeautyMainPage.context, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        Log.e("Lat","lat"+MainProviderActivity.lat1);
        Log.e("Lang","lang"+MainProviderActivity.lang1);
        googleMap.setMyLocationEnabled(true);
        if (MainProviderActivity.lat1!=null
            &&  MainProviderActivity.lang1!=null
        ) {
            LatLng sydney = new LatLng(MainProviderActivity.lat1, MainProviderActivity.lang1);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Provider place"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            // for enter add + button and reserve service
        }


        if ( MainProviderActivity.arrayList.size()>0) {
            Double longtude=Double.parseDouble(MainProviderActivity.arrayList.get(0).getLongitude());
            Double latitude=Double.parseDouble(MainProviderActivity.arrayList.get(0).getLatitude());

            LatLng sydney = new LatLng(longtude, latitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Provider place"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));


        }
    }

}
