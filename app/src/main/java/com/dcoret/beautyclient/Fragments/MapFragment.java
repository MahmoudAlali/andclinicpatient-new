package com.dcoret.beautyclient.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.MapfragmentAddSite;

import com.dcoret.beautyclient.DataClass.LocationTitles;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public  static ArrayList<LocationTitles> locationTitles=new ArrayList<>();
    ArrayList<String> arrayList=new ArrayList<>();

    MapView map;
    FloatingActionButton filter;
    TextView search_map;
    Button searchmap_btn;
    Geocoder geocoder;
    double insLat, insLong;
    Button add_loc,del_loc,edit_loc;
    GoogleMap mMap;
    NestedScrollView nscrolview;
    int i;
    Spinner location_titles;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_map_filtering, container, false);

        BeautyMainPage.FRAGMENT_NAME =  "MAPFRAGMENT";

        map = view.findViewById(R.id.map);
        add_loc = view.findViewById(R.id.add_loc);
        edit_loc = view.findViewById(R.id.edit_loc);
        del_loc = view.findViewById(R.id.del_loc);
        location_titles=view.findViewById(R.id.location_title);
//        nscrolview = view.findViewById(R.id.nscrolview);




        ArrayAdapter<String> karant_adapter = new ArrayAdapter<>(BeautyMainPage.context, android.R.layout.simple_spinner_item,arrayList);
        karant_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_titles.setAdapter(karant_adapter);

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
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list, 8f));
//                        Toast.makeText(BeautyMainPage.context, statename + latitude + latLng, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    });
        edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (edit_loc.getText().toString().equals("تعديل موقع")){
                    add_loc.setEnabled(false);
                    del_loc.setEnabled(false);
                    flag_add_delete_location = 2;
                    edit_loc.setText("انتهيت من التعديل");
                    APICall.showSweetDialog(BeautyMainPage.context,"لطفاً","من فضلك اضغطي على المواقع المحددة لتعديلها");

                    Log.e("edit", flag_add_delete_location + "");
                }else {
                    flag_add_delete_location = 0;
                    edit_loc.setText("تعديل موقع");
                    Log.e("edit", flag_add_delete_location + "");
//                    onMapReady(mMap);
                    add_loc.setEnabled(true);
                    del_loc.setEnabled(true);
                    Toast.makeText(BeautyMainPage.context,"تم تعديل المواقع ",Toast.LENGTH_LONG).show();
                    mMap.clear();
                    for (int i = 0; i < locationTitles.size(); i++) {
                        mMap.addMarker(new MarkerOptions()
                                .position(locationTitles.get(i).getLatLng())
                                .title(locationTitles.get(i).getTitle())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                        );


                    }

                }
                onMapReady(mMap);

            }
        });
        add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                if (add_loc.getText().equals("اضافة")) {
//                    flag_add_delete_location = 0;

//                    add_loc.setText("اضافة عنوان");
//
//                }else {
                    APICall.titlemapdialog(BeautyMainPage.context, "لطفاً", "من فضلك ضع اسم لموقعك!", latLngtmp, mMap, marker,flag_add_delete_location);
//                    add_loc.setText("اضافة");
//                }
                 Log.d("Remove", "false");


            }
        });
        del_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(del_loc.getText().toString().equals("حذف موقع")) {
                    add_loc.setEnabled(false);
                    edit_loc.setEnabled(false);
                    flag_add_delete_location = 1;
                    del_loc.setText("انتهيت من الحذف.");
                    APICall.showSweetDialog(BeautyMainPage.context,"لطفاً","من فضلك اضغطي على المواقع المحددة لحذفها");

                }else {
                    add_loc.setEnabled(true);
                    edit_loc.setEnabled(true);
                    flag_add_delete_location = 0;
                    del_loc.setText("حذف موقع");
                    Toast.makeText(BeautyMainPage.context,"تم الحذف ",Toast.LENGTH_LONG).show();

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
     LatLng latLngtmp;
//    MarkerOptions markerOptionstmp;
    int flag_add_delete_location=0;
    Marker marker;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i=0;i<locationTitles.size();i++){
            mMap.addMarker(new MarkerOptions()
                    .position(locationTitles.get(i).getLatLng())
                    .title(locationTitles.get(i).getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))

            );
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationTitles.get(i).getLatLng(), 10));

            // Setting the position for the marker
        }




        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (flag_add_delete_location == 0) {
                    Log.e("flag", flag_add_delete_location + "");
                    latLngtmp = latLng;
                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(latLng);

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                    // Clears the previously touched position
                    mMap.clear();
                    for (int i = 0; i < locationTitles.size(); i++) {
                        mMap.addMarker(new MarkerOptions()
                                .position(locationTitles.get(i).getLatLng())
                                .title(locationTitles.get(i).getTitle())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                        );

                        // Setting the position for the marker


                    }
                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Placing a marker on the touched position
                    marker = mMap.addMarker(markerOptions);
//                markerOptionstmp=markerOptions;

                }
            }
        });
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (flag_add_delete_location == 1) {
                        Log.e("flag", flag_add_delete_location + "");
//                        onMapClick(null);
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(final Marker marker) {
                                if (flag_add_delete_location == 1) {
                                    Log.e("Delete","ok");

                                    Log.e("marker", marker.getPosition().toString());
                                    Log.e("title", marker.getTitle());

                                    for ( i = 0; i < locationTitles.size(); i++) {
                                        if (marker.getTitle().equals(locationTitles.get(i).getTitle())) {
                                                            locationTitles.remove(i);
                                                            marker.remove();
                                                        }


                                        }
                                    mMap.clear();
                                    for (int i = 0; i < locationTitles.size(); i++) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(locationTitles.get(i).getLatLng())
                                                .title(locationTitles.get(i).getTitle())
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                                        );


                                    }



                                } else {
                                    marker.showInfoWindow();
                                }
                                return true;
                            }
                        });
                    } else if (flag_add_delete_location==2){
                        LatLng latLngtmp=null;
                        for ( i = 0; i < locationTitles.size(); i++) {
                            if (marker.getTitle().equals(locationTitles.get(i).getTitle())) {
                                         latLngtmp=locationTitles.get(i).getLatLng();
                                                locationTitles.remove(i);
//                                                marker.remove();
                                APICall.titlemapdialog(BeautyMainPage.context, "لطفاً", "من فضلك ضع اسم لموقعك!", latLngtmp, mMap, marker,flag_add_delete_location);

                            }
                        }
                    }else {
                        marker.showInfoWindow();
                    }
                    return false;
                }
            });


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
