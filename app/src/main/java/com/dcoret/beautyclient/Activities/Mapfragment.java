package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class Mapfragment extends Fragment implements OnMapReadyCallback{

//    public Mapfragment() {
//        // Required empty public constructor
//    }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_google_maps, container, false);
//        mMapView = (MapView) view.findViewById(R.id.map);
        SupportMapFragment mapFragment=(SupportMapFragment)getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        if (mapFragment==null){
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment);

        }
        mapFragment.getMapAsync(this);
//        mMapView.onCreate(savedInstanceState);
//        getlocation();

      

//        mMapView.onResume(); // needed to get the map to display immediately
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        MapsInitializer.initialize(getActivity().getApplicationContext());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }).start();


//            googleMap.setOnMapClickListener(new GoogleMapBeauty.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    // Creating a marker
//                    MarkerOptions markerOptions = new MarkerOptions();
//
//                    // Setting the position for the marker
//                    markerOptions.position(latLng);
//
//                    // Setting the title for the marker.
//                    // This will be displayed on taping the marker
//                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                    // Clears the previously touched position
//                    googleMap.clear();
//
//                    // Animating to the touched position
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                    // Placing a marker on the touched position
//                    googleMap.addMarker(markerOptions);
//                }
//            });




//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMapBeauty mMap) {
//
//
//                googleMap = mMap;
//
//                // For showing a move to my location button
//                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                googleMap.setMyLocationEnabled(true);
//
//                // For dropping a marker at a point on the Map
//                LatLng sydney;
//                Geocoder geo;
//                 sydney = new LatLng( locations[0].getLatitude(), locations[0].getLongtude());
//                geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
//                List<Address> addresses =new ArrayList<>();
//                try {
//                   addresses = geo.getFromLocation(latitud, longitud, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    try {
//                        addresses = geo.getFromLocation(locations[0].getLatitude(), locations[0].getLongtude(), 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    googleMap.addMarker(new MarkerOptions().position(sydney).title(addresses.get(0).getFeatureName()).snippet("Test From Beauty Client Google Maps"));
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F));
//                }catch (Exception e){
//                    Toast.makeText(getActivity().getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
//                }
//
////                for (int i=1;i<items.length-2;i++){
////                    sydney = new LatLng( locations[i].getLatitude(),  locations[i].getLongtude());
////                    geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
////                    try {
////                        addresses = geo.getFromLocation(locations[i].getLatitude(), locations[i].getLongtude(), 1);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    try {
////                        googleMap.addMarker(new MarkerOptions().position(sydney).title(items[i]).snippet("Test From Beauty Client Google Maps"));
////                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F));
////                    }catch (Exception e){
////
////                    }
////                }
//
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//        });
//





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

    static double latit;
    static double longit;

    static Location location1;
    static double latitud,longitud;
    @SuppressLint("MissingPermission")
    void configure(){
//        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public     void getlocation() {
//        Intent intent=new Intent(getApplicationContext(),ForgetMyPass.class);
//        startActivity(intent);
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud=location.getLatitude();
                longitud=location.getLongitude();
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
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET

            },10);
//                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        configure();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {



    }
}
