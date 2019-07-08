package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.dcoret.beautyclient.AddReservation;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
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
    ProgressDialog pd;
//    MapView mMapView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_google_maps, container, false);

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
                                        Thread.sleep(1000);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mMapView.onCreate(savedInstanceState);
                                mMapView.getMapAsync(TabThree.this);
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

    static double latitud, longitud;
// --------- get my location by location Manager----------
    @SuppressLint("MissingPermission")
    void configure() {
//        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getlocation() {
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
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
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_PHONE_STATE
                    ,Manifest.permission.ACCESS_NETWORK_STATE
            },BeautyMainPage.ACCESS_FINE_LOCATION);
            return;
        }

        configure();
    }

    LatLng sydney;
    Geocoder geo;
    List<Address> addresses;



    private ViewGroup infoWindow;
    private RatingBar service_rate;
    private TextView infoTitle,infoPrice;
//    private TextView infoSnippet;
    private ImageButton infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;

        //------------------------ info title ---------------------
        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(googleMap, getPixelsFromDp(BeautyMainPage.context, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance

        infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.title_map_layout, null);
        infoTitle = infoWindow.findViewById(R.id.title);
        infoPrice = infoWindow.findViewById(R.id.price);
        service_rate=infoWindow.findViewById(R.id.service_rate);
        infoButton = infoWindow.findViewById(R.id.book);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
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
        infoButton.setOnTouchListener(infoButtonListener);



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


        //---------------show custom info title-------------------
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
              for (int i=0;i< TabOne.arrayList.size();i++){
               if (marker.getPosition().longitude==Double.parseDouble(TabOne.arrayList.get(i).getLongitude())
                       && marker.getPosition().latitude==Double.parseDouble(TabOne.arrayList.get(i).getLatitude())){
                    infoTitle.setText(TabOne.arrayList.get(i).getBdb_sup_name());
                    infoPrice.setText(TabOne.arrayList.get(i).getPriceByFilter());
                    service_rate.setEnabled(false);
                    service_rate.setRating(Float.parseFloat(TabOne.arrayList.get(i).getBdb_sup_rating()));
               }
              }
                marker.showInfoWindow();

                return false;
            }
        });
        googleMap.setMyLocationEnabled(true);


        // For dropping a marker at a point on the Map

//        sydney = new LatLng(locations[0].getLatitude(), locations[0].getLongtude());
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
//            googleMap.addMarker(new MarkerOptions().position(sydney).title(addresses.get(0).getFeatureName()).snippet("Test From Beauty Client Google Maps"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F));
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }


//        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                for (int i = 1; i < TabOne.arrayList.size() ; i++) {
//                Log.e("TabOne.arrayList.size",TabOne.arrayList.size()+"");
//                for (int i = 0; i < TabOne.arrayList.size() ; i++) {
//                    sydney = new LatLng(Double.parseDouble( TabOne.arrayList.get(i).getLatitude()),Double.parseDouble(TabOne.arrayList.get(i).getLongitude()));
//                    geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
//
//                    try {
//                        googleMap.addMarker(new MarkerOptions().position(sydney).title(TabOne.arrayList.get(i).getBdb_sup_name()).snippet("Test From Beauty Client Google Maps"));
//                    } catch (Exception e) {
//
//                    }
//                }
//            }
//        });


        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(locations[0].getLatitude(), locations[0].getLongtude())).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        // for enter add + button and reserve service
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                infoButtonListener.setMarker(marker);
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