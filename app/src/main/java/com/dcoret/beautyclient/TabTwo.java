package com.dcoret.beautyclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.Location_Beauty;
import com.dcoret.beautyclient.MainActivity;
import com.dcoret.beautyclient.MyReservations;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TabTwo extends Fragment {

    public TabTwo() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
    String[] prices={"100","500","450","123","345","411","800","900","600","300"};
    String[] rank={"4.1","3.2","3.5","4.7","4.4","3.0","3.0","2.5","2.0","1.5"};
    String[] city={"الرياض","الدمام","مكة","الرياض","جدة","الدمام","مكة","مكة","الطائف","مكة"};
    Location_Beauty[] locations={
            new Location_Beauty(32.7792842,35.8816735),
            new Location_Beauty(31.964383, 35.918756),
            new Location_Beauty(32.709566, 36.137142),
            new Location_Beauty(32.777491, 35.935935),
            new Location_Beauty(32.755262, 35.986746),
            new Location_Beauty(32.725373, 35.944346),
            new Location_Beauty(32.688479, 35.992233),
            new Location_Beauty(32.670663, 36.052908),
            new Location_Beauty(33.506590, 36.299474),
            new Location_Beauty(33.546086, 36.200597),
    };

    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_google_maps, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {


                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(MyReservations.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyReservations.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney;
                Geocoder geo;
                 sydney = new LatLng( MainActivity.latitud,  MainActivity.longitud);
                geo = new Geocoder(MyReservations.context, Locale.getDefault());
                List<Address> addresses =new ArrayList<>();
                try {
                   addresses = geo.getFromLocation(MainActivity.latitud, MainActivity.longitud, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                googleMap.addMarker(new MarkerOptions().position(sydney).title(addresses.get(0).getFeatureName()).snippet("Test From Beauty Client Google Maps"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10F));


                for (int i=1;i<items.length-2;i++){
                    sydney = new LatLng( locations[i].getLatitude(),  locations[i].getLongtude());
                    geo = new Geocoder(MyReservations.context, Locale.getDefault());
                    try {
                        addresses = geo.getFromLocation(locations[i].getLatitude(), locations[i].getLongtude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    googleMap.addMarker(new MarkerOptions().position(sydney).title(items[i]).snippet("Test From Beauty Client Google Maps"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10F));

                }


                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}
