package com.dcoret.beautyclient.Fragments;


import android.app.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;

import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
//    public  static ArrayList<LocationTitles> locationTitles=new ArrayList<>();
//    public  static ArrayList<String> arrayList=new ArrayList<>();

    LinearLayout my_loc_layout;
    MapView map;
    TextView search_map;
    Button searchmap_btn;
    Geocoder geocoder;
    Button add_loc,del_loc,edit_loc;
   public static GoogleMap mMap;
    int i;
     ArrayList<String> listItems=new ArrayList<String>();

    Spinner location_titles;
    Button my_location_btn;
    static int del_Flag=0;
    static int edit_Flag=0;
    ArrayAdapter<String> adapter;
    public static ArrayList<String> my_loc=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_filtering, container, false);
        if (BeautyMainPage.FRAGMENT_NAME.equals("SPINNER")){
         BeautyMainPage.FRAGMENT_NAME="MAPFRAGMENTSPINNER";
        }else if (BeautyMainPage.FRAGMENT_NAME.equals("Tabs")){
            BeautyMainPage.FRAGMENT_NAME="TABS";
        }else {
            BeautyMainPage.FRAGMENT_NAME = "MAPFRAGMENT";
        }
        map = view.findViewById(R.id.map);
        my_location_btn = view.findViewById(R.id.my_location_btn);
        add_loc = view.findViewById(R.id.add_loc);
        edit_loc = view.findViewById(R.id.edit_loc);
        del_loc = view.findViewById(R.id.del_loc);
        my_loc_layout = view.findViewById(R.id.my_loc_layout);

        my_loc_layout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(BeautyMainPage.context, my_location_btn);


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String name=item.getTitle().toString();
                        for(int i=0;i<AccountFragment.locationTitles.size();i++){
                            if (name.equals(AccountFragment.locationTitles.get(i).getBdb_my_descr())){
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AccountFragment.locationTitles.get(i).getLatLng(), 10));
                            }
                        }
                        return false;
                    }
                });
                for (int i=0;i<AccountFragment.locationTitles.size();i++){
                    String title=AccountFragment.locationTitles.get(i).getBdb_my_descr();
                    popup.getMenu().add(title);
                }
//                popup.inflate(R.menu.popup_menu);
                popup.show();
//                popup.showAsDropDown(v,-153,0);
            }
        });
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
                    Log.d("Locationlist", list.toString());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .title(areaname)
                            .snippet("SNIPPET")
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                            .position(list));
                    // Zoom in the Google Map
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list, 8f));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    });
        edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_Flag==0 ){
                    edit_Flag=1;
                    add_loc.setEnabled(false);
                    del_loc.setEnabled(false);
                    flag_add_delete_location = 2;
                    mMap.clear();
                    APICall.getdetailsUser(BeautyMainPage.context);
                    edit_loc.setText(R.string.finishedediting);
                    APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.clicksitestoedit);
                    Log.e("edit", flag_add_delete_location + "");
                }else {
                    edit_Flag=0;
                    flag_add_delete_location = 0;
                    edit_loc.setText(R.string.editlocation);
                    Log.e("edit", flag_add_delete_location + "");
                    add_loc.setEnabled(true);
                    del_loc.setEnabled(true);
                    Toast.makeText(BeautyMainPage.context,R.string.Sitesmodified,Toast.LENGTH_LONG).show();
                }
                onMapReady(mMap);
            }
        });
        add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    APICall.titlemapdialog(BeautyMainPage.context, R.string.ExuseMeAlert, R.string.putnamesite, latLngtmp, mMap, marker,flag_add_delete_location);
                 Log.d("Remove", "false");
            }
        });

        del_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(del_Flag==0) {
                    del_Flag=1;
                    add_loc.setEnabled(false);
                    edit_loc.setEnabled(false);
                    flag_add_delete_location = 1;
                    del_loc.setText(R.string.finisheddeleting);
                    APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.clicksitestodelete);
                }else {
                    del_Flag=0;
                    add_loc.setEnabled(true);
                    edit_loc.setEnabled(true);
                    flag_add_delete_location = 0;
                    del_loc.setText(R.string.deletelocation);
                    Toast.makeText(BeautyMainPage.context,R.string.finisheddeleting,Toast.LENGTH_LONG).show();
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
    int flag_add_delete_location=0;
    Marker marker;
    PopupWindow popupMenu;
    ListView listView;
    //----------not used------
    private void setPopUpWindow() {
        LayoutInflater inflater = (LayoutInflater)
                ((AppCompatActivity)BeautyMainPage.context).getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view = inflater.inflate(R.layout.popup_window, null);

        listView =  view.findViewById(R.id.listview);

          popupMenu = new PopupWindow(view, 300, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        APICall.getdetailsUser(BeautyMainPage.context);
        mMap = googleMap;

        for (int i=0;i<AccountFragment.locationTitles.size();i++){
            mMap.addMarker(new MarkerOptions()
                    .position(AccountFragment.locationTitles.get(i).getLatLng())
                    .title(AccountFragment.locationTitles.get(i).getBdb_my_descr())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
            );
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AccountFragment.locationTitles.get(i).getLatLng(), 10));

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
                    for (int i = 0; i < AccountFragment.locationTitles.size(); i++) {
                        mMap.addMarker(new MarkerOptions()
                                .position(AccountFragment.locationTitles.get(i).getLatLng())
                                .title(AccountFragment.locationTitles.get(i).getBdb_my_descr())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                        );
                        // Setting the position for the marker
                    }
                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    // Placing a marker on the touched position
                    marker = mMap.addMarker(markerOptions);

                }
            }
        });


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (flag_add_delete_location == 1) {
                        Log.e("flag", flag_add_delete_location + "");
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(final Marker marker) {

                                if (flag_add_delete_location == 1) {
                                    Log.e("Delete","ok");
                                    Log.e("marker", marker.getPosition().toString());
                                    Log.e("title", marker.getTitle());


                                    for ( i = 0; i < AccountFragment.locationTitles.size(); i++) {
                                        if (marker.getPosition().equals(AccountFragment.locationTitles.get(i).getLatLng())) {

                                            APICall.deleteAddress("http://clientapp.dcoret.com/api/auth/user/deleteAddress",AccountFragment.locationTitles.get(i).getId(),i,marker,BeautyMainPage.context);
                                        }
                                        }
                                    mMap.clear();
                                    AccountFragment. arrayList.clear();
                                    for (int i = 0; i < AccountFragment.locationTitles.size(); i++) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(AccountFragment.locationTitles.get(i).getLatLng())
                                                .title(AccountFragment.locationTitles.get(i).getBdb_my_descr())
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                                        );
                                        AccountFragment.arrayList.add(AccountFragment.locationTitles.get(i).getBdb_my_descr());

                                    }

                                    //------------- for spinner in place service /init spinner
                                    PlaceServiceFragment.mylocationId=getResources().getString(R.string.MyLocation);
                                } else {
                                    marker.showInfoWindow();
                                }
                                return true;
                            }
                        });
                    } else if (flag_add_delete_location==2){
                        LatLng latLngtmp=null;
                        for ( i = 0; i < AccountFragment.locationTitles.size(); i++) {
                            if (marker.getTitle().equals(AccountFragment.locationTitles.get(i).getBdb_my_descr())) {
                                         latLngtmp=AccountFragment.locationTitles.get(i).getLatLng();
                                        AccountFragment.locationTitles.remove(i);
                                                Log.e("LatLang",latLngtmp.latitude+","+latLngtmp.longitude);
                                APICall.titlemapdialog(BeautyMainPage.context, R.string.ExuseMeAlert, R.string.putnamesite, latLngtmp, mMap, marker,flag_add_delete_location);
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
            list=new LatLng(lat,lng);
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
