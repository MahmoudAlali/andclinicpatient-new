package com.dcoret.beautyclient.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Mapfragment;
import com.dcoret.beautyclient.Activities.MyProfile;
import com.dcoret.beautyclient.Activities.Point;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.GoogleMapBeauty;
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

public class AccountFragment extends Fragment implements BeautyMainPage.OnBackPressedListener {
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
    String[] array = new String[] {"بروفايلي", "نقاطي", "ايقاف الحساب","حذف الحساب"};
    ListView listView;
    Button add_del_site;


    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_manage_accounts_frag, container, false);



        BeautyMainPage.FRAGMENT_NAME="";
        BeautyMainPage.FRAGMENT_NAME="ACCOUNTFRAGMENT";
        add_del_site=view.findViewById(R.id.add_del_site);
        add_del_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new MapFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
//                Intent intent=new Intent(BeautyMainPage.context, GoogleMapBeauty.class);
//                startActivity(intent);
            }
        });
//        mMapView = (MapView) view.findViewById(R.id.map);
//
        Log.d("fargmentname",BeautyMainPage.FRAGMENT_NAME);
//        mMapView.onCreate(savedInstanceState);
//        getlocation();
//        mMapView.onResume(); // needed to get the map to display immediately
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    MapsInitializer.initialize(getActivity().getApplicationContext());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

//        addFragment(new MapFragment(),false,"map");
        mMapView = view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, array);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    Intent intent = new Intent(getActivity().getApplicationContext(), MyProfile.class);
//                    startActivity(intent);
////                    Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                } else if (position == 1) {
//                    Intent intent = new Intent(getActivity().getApplicationContext(), Point.class);
//                    startActivity(intent);
////                    Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });
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
//                sydney = new LatLng( locations[0].getLatitude(), locations[0].getLongtude());
//                geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
//                List<Address> addresses =new ArrayList<>();
//                try {
//                    addresses = geo.getFromLocation(latitud, longitud, 1);
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
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//        });

        ((BeautyMainPage) getActivity()).setOnBackPressedListener(this);
        return view;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }

    @Override
    public void onPause() {
        super.onPause();
//        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mMapView.onLowMemory();
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
//    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
//        FragmentManager manager = getActivity().getFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//
//        if (addToBackStack) {
//            ft.addToBackStack(tag);
//        }
//        ft.replace(R.id.map, fragment, tag);
//        ft.commitAllowingStateLoss();
//    }

    @Override
    public int doBack() {
        Log.d("doback",1+"");
        return 1;
    }
}
