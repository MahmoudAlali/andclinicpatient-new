package com.ptmsa1.vizage.Activities;

import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmsa1.vizage.Adapters.ServicesAdapter;
import com.ptmsa1.vizage.DataModel.Location_Beauty;
import com.ptmsa1.vizage.Fragments.MyIndEffectsActivity;
import com.ptmsa1.vizage.R;
import com.ptmsa1.vizage.test.MapWrapperLayout;
import com.ptmsa1.vizage.test.OnInfoWindowElemTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.text.DecimalFormat;
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
    TextView service_sw,offer_sw;
    //--- 0 is service, 1 is offers---
    public static int showOnMap=0;
    ProgressDialog pd;
//    MapView mMapView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_google_maps, container, false);

          mapWrapperLayout = view.findViewById(R.id.map_relative_layout);
        service_sw = view.findViewById(R.id.service_Sw);
        offer_sw = view.findViewById(R.id.offer_sw);



        //---------- manual switch ------------
        offer_sw.setBackgroundResource(android.R.color.transparent);
        service_sw.setBackgroundResource(R.drawable.shadow_service_tab);
        showOnMap=0;

        service_sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_sw.setBackgroundResource(android.R.color.transparent);
                service_sw.setBackgroundResource(R.drawable.shadow_service_tab);
                showOnMap=0;
                googleMap.clear();
                for (int i = 0; i< TabOne.arrayList.size(); i++){
                    Double lat=Double.parseDouble(TabOne.arrayList.get(i).getLatitude());
                    Double lng=Double.parseDouble(TabOne.arrayList.get(i).getLongitude());
                    Log.e("lng_lat",lat+":"+lng);
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))

                    );
                }
            }
        });
        offer_sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service_sw.setBackgroundResource(android.R.color.transparent);
                offer_sw.setBackgroundResource(R.drawable.shadow_service_tab);
                showOnMap=1;
                googleMap.clear();
//                TabTwo.arrayList.size();
                for (int i = 0; i< TabTwo.arrayList.size(); i++){
                    Double lat=Double.parseDouble(TabTwo.arrayList.get(i).getLatitude());
                    Double lng=Double.parseDouble(TabTwo.arrayList.get(i).getLongitude());
                    Log.e("lng_lat_offer",lat+":"+lng);
                    Log.e("lng_lat_offer",TabTwo.arrayList.size()+"");
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))

                    );
                }
            }
        });

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

//    LatLng sydney;
    Geocoder geo;
    List<Address> addresses;



    private ViewGroup infoWindow;
    private RatingBar service_rate;
    private TextView infoTitle,infoPrice,infoldPrice;
    String bdb_ser_sup_id,bdb_booking_period;
//    private TextView infoSnippet;
    private ImageButton infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(final GoogleMap mMap) {
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
        infoldPrice = infoWindow.findViewById(R.id.old_price);
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

//                ServicesAdapter.ListOfDates(Integer.parseInt(bdb_booking_period));
//                Intent intent=new Intent(BeautyMainPage.context, IndividualBooking.class);
//                intent.putExtra("Provider Name",infoTitle.getText().toString());
//                intent.putExtra("bdb_ser_sup_id",bdb_ser_sup_id);
//                intent.putExtra("bdb_booking_period",bdb_booking_period);
//                intent.putExtra("Price", infoPrice.getText().toString());
//                startActivity(intent);


//                ser_sup_id=itemArrayList.get(position).getBdb_ser_sup_id();
                Log.e("SERSUPID",bdb_ser_sup_id);
                ServicesAdapter.ListOfDates(Integer.parseInt(bdb_booking_period));

//                ListOfDates(Integer.parseInt(itemArrayList.get(position).getBdb_booking_period()));
                Intent intent = new Intent(BeautyMainPage.context, MyIndEffectsActivity.class);
                //                        intent.putExtra("Service Name","")
             int position=0;
                for (int i = 0; i< TabOne.arrayList.size(); i++) {
                    Log.e("infoTitle",infoTitle.getText().toString());
                    Log.e("TabOneArray",TabOne.arrayList.get(i).getBdb_sup_name());
                    if (infoTitle.getText().toString().equals(TabOne.arrayList.get(i).getBdb_sup_name())) {
                        Log.e("POSTIONARRAYINMAP",i+"");
                        position=i;
                        break;
                    }
                }
                    TabOne.bdb_sup_id=TabOne.arrayList.get(position).getSup_id();
                TabOne.ser_id=TabOne.arrayList.get(position).getSer_id();
                TabOne.ser_sup_id=TabOne.arrayList.get(position).getBdb_ser_sup_id();
                TabOne.ser_sup_id=TabOne.arrayList.get(position).getBdb_ser_sup_id();
                TabOne.bdb_time=TabOne.arrayList.get(position).getBdb_time();
                TabOne.bdb_is_bride=TabOne.arrayList.get(position).getBdb_isbride_ser();
//
                startActivity(intent);
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

                for (int i=0;i< TabOne.arrayList.size();i++){

                    Double lat=Double.parseDouble(TabOne.arrayList.get(i).getLatitude());
                    Double lng=Double.parseDouble(TabOne.arrayList.get(i).getLongitude());
                    Log.e("lng_lat",lat+":"+lng);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));
        }

        final DecimalFormat df = new DecimalFormat("0.0");

        //---------------show custom info title-------------------
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (showOnMap==0) {
                    for (int i = 0; i < TabOne.arrayList.size(); i++) {
                        if (marker.getPosition().longitude == Double.parseDouble(TabOne.arrayList.get(i).getLongitude())
                                && marker.getPosition().latitude == Double.parseDouble(TabOne.arrayList.get(i).getLatitude())) {
                            infoTitle.setText(TabOne.arrayList.get(i).getBdb_sup_name());
                            infoPrice.setText(TabOne.arrayList.get(i).getPriceByFilter());
                            bdb_ser_sup_id=TabOne.arrayList.get(i).getBdb_ser_sup_id();
                            bdb_booking_period=TabOne.arrayList.get(i).getBdb_booking_period();
                                    infoldPrice.setText("");
                            service_rate.setVisibility(View.VISIBLE);
                            service_rate.setEnabled(false);
                            service_rate.setRating(Float.parseFloat(TabOne.arrayList.get(i).getBdb_sup_rating()));
                        }
                    }
                }else{
                    for (int i = 0; i < TabTwo.arrayList.size(); i++) {
                        if (marker.getPosition().longitude == Double.parseDouble(TabTwo.arrayList.get(i).getLongitude())
                                && marker.getPosition().latitude == Double.parseDouble(TabTwo.arrayList.get(i).getLatitude())) {
                            infoTitle.setText(TabTwo.arrayList.get(i).getBdb_sup_name());
                            infoPrice.setText(TabTwo.arrayList.get(i).getNewPrice());
                            float old_prc=Float.parseFloat(Double.parseDouble(TabTwo.arrayList.get(i).getOldPrice())+"");
                            old_prc = Float.parseFloat(df.format(old_prc));
                            infoldPrice.setText(old_prc+"");
                            infoldPrice.setPaintFlags(infoldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                            service_rate.setEnabled(false);
                            service_rate.setVisibility(View.GONE);
//                            service_rate.setRating(Float.parseFloat(TabTwo.arrayList.get(i).getBdb_sup_rating()));
                        }
                    }
                }
                marker.showInfoWindow();

                return false;
            }
        });
        googleMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map

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
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }


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
//-----------    for Title info dialog
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}