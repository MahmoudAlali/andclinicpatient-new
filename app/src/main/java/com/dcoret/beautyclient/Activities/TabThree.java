package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.dcoret.beautyclient.AddReservation;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.ResevationDate;
import com.dcoret.beautyclient.test.MapWrapperLayout;
import com.dcoret.beautyclient.test.OnInfoWindowElemTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TabThree extends Fragment implements OnMapReadyCallback {

    public TabThree() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    String[] items = {"Service1", "Service2", "Service3", "Service4", "Service5", "Service6", "Service7", "Service8", "Service9", "Service10"};
    String[] prices = {"100", "500", "450", "123", "345", "411", "800", "900", "600", "300"};
    String[] rank = {"4.1", "3.2", "3.5", "4.7", "4.4", "3.0", "3.0", "2.5", "2.0", "1.5"};
    String[] city = {"الرياض", "الدمام", "مكة", "الرياض", "جدة", "الدمام", "مكة", "مكة", "الطائف", "مكة"};
    Location_Beauty[] locations = {
            new Location_Beauty(32.7792842, 35.8816735),
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

    View view;
    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    LocationListener locationListener;
    MapWrapperLayout mapWrapperLayout;
    LayoutInflater layoutInflater;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_google_maps, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

          mapWrapperLayout = view.findViewById(R.id.map_relative_layout);



//        getlocation();

        mMapView.onResume(); // needed to get the map to display immediately
        MapsInitializer.initialize(getActivity().getApplicationContext());

        mMapView.getMapAsync(this);


        return view;
    }

    static double latitud, longitud;

    @SuppressLint("MissingPermission")
    void configure() {
//        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getlocation() {
//        Intent intent=new Intent(getApplicationContext(),ForgetMyPass.class);
//        startActivity(intent);
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
//                        Toast.makeText(getApplicationContext()
//                                ,"lat: "+location.getLatitude()+" long: "+location.getLongitude(),Toast.LENGTH_LONG).show();
//                    register.setText(location.getLatitude()+" : "+location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET

            }, 10);
            return;
        }

        configure();
    }

    LatLng sydney;
    Geocoder geo;
    List<Address> addresses;



    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private ImageButton infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;
    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(googleMap, getPixelsFromDp(BeautyMainPage.context, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.title_map_layout, null);
//        this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
//        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
        this.infoButton = (ImageButton)infoWindow.findViewById(R.id.book);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
               null,
                null)
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
//                Toast.makeText(BeautyMainPage.context, "تم حج", Toast.LENGTH_SHORT).show();

                Intent i=new Intent(BeautyMainPage.context, AddReservation.class);
                startActivity(i);
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);



//        final InfoWindow infoWindow = new InfoWindow();
        // For showing a move to my location button
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
        googleMap.setMyLocationEnabled(true);

        // For dropping a marker at a point on the Map

        sydney = new LatLng(locations[0].getLatitude(), locations[0].getLongtude());
        geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        addresses = new ArrayList<>();
        try {
            addresses = geo.getFromLocation(latitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                addresses = geo.getFromLocation(locations[0].getLatitude(), locations[0].getLongtude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            googleMap.addMarker(new MarkerOptions().position(sydney).title(addresses.get(0).getFeatureName()).snippet("Test From Beauty Client Google Maps"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F));
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < items.length - 2; i++) {
                    sydney = new LatLng(locations[i].getLatitude(), locations[i].getLongtude());
                    geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                    try {
                        addresses = geo.getFromLocation(locations[i].getLatitude(), locations[i].getLongtude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        googleMap.addMarker(new MarkerOptions().position(sydney).title(items[i]).snippet("Test From Beauty Client Google Maps"));
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F));
                    } catch (Exception e) {

                    }
                }
            }
        });

//            }
//        }).start();


        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                // Setting up the infoWindow with current's marker info
//                infoTitle.setText("Helllooo");
//                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);
//
//                 We must call this to set the current marker and infoWindow references
//                 to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;

            }

            @Override
            public View getInfoContents(Marker marker) {
           return null;
            }
        });
    }
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}