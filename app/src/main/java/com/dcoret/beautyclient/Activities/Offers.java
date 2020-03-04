package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataModel.BestOfferItem;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class Offers extends Fragment implements LocationListener {
    // public static Context context;
    RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static   ArrayList<BestOfferItem> bestOfferItems=new ArrayList<>();
    public static OffersAdapter bestOffer;
    public static  String name="offers";
    static Boolean isFirstOpen=true;
    public static String Lat="",Long="";
    public static int ACCESS_FINE_LOCATION = 90;
    private LocationManager locationManager;
    private String provider;
    Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.offers_layout, container, false);
        toolbar= view.findViewById(R.id.toolbarm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });
        pullToRefresh = view.findViewById(R.id.pullToRefresh);

        //context=this;
        //----------------init recycle view ----------------------------
//        if (bestOfferItems.size()>0){

        LocationManager service = (LocationManager) BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            APICall.showSweetDialog(getActivity(),getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.plsActivLoc));
           /* Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);*/
        }
      /*  if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestLocationPermission();
        }*/
        else
        {
            locationManager = (LocationManager) BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            try
            {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    System.out.println("Provider " + provider + " has been selected.");
                    onLocationChanged(location);

                } else {
                    Log.e("LOCATION","Location not available");
                    //latituteField.setText("Location not available");
                    // longitudeField.setText("Location not available");
                }
                bestOfferItems.clear();
                APICall.bestOffer(BeautyMainPage.context,Lat,Long);
            }
            catch (SecurityException e)
            {
                Log.e("LOCATION","11111111");

                if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestLocationPermission();
                }
            }
            catch (Exception e)
            {
                Log.e("LOCATION","222222");
                Log.e("LOCATION",e.getMessage());

            }
        }

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LocationManager service = (LocationManager) BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    APICall.showSweetDialog(getActivity(),getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.plsActivLoc));
                    pullToRefresh.setRefreshing(false);
           /* Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);*/
                }
      /*  if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestLocationPermission();
        }*/
                else
                {
                    locationManager = (LocationManager) BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    provider = locationManager.getBestProvider(criteria, false);
                    try
                    {
                        Location location = locationManager.getLastKnownLocation(provider);
                        if (location != null) {
                            System.out.println("Provider " + provider + " has been selected.");
                            onLocationChanged(location);

                        } else {
                            Log.e("LOCATION","Location not available");
                            pullToRefresh.setRefreshing(false);

                            //latituteField.setText("Location not available");
                            // longitudeField.setText("Location not available");
                        }
                        bestOfferItems.clear();
                        // APICall.detailsUser4(context);
                        APICall.bestOffer(BeautyMainPage.context,Lat,Long);

                    }
                    catch (SecurityException e)
                    {
                        pullToRefresh.setRefreshing(false);

                        if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            requestLocationPermission();
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
              /*  bestOfferItems.clear();
                APICall.bestOffer(BeautyMainPage.context,Lat,Long);*/
            }
        });
        if (isFirstOpen){

            isFirstOpen=false;
        }

//        }
        recyclerView=view.findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        bestOffer=new OffersAdapter(BeautyMainPage.context,bestOfferItems);
//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));

        recyclerView.setAdapter(bestOffer);
        //------------------------ call API bestOffers and get items-----------------
//        APICall.bestOffer(Offers.this);

        //-------------------------------call BagReservation after 5 minutes
        return view;
    }
    //-------------------- go to main page -----------------------
    /*public void servicesBeauty(View view) {
        Intent in=new Intent(this,BeautyMainPage.class);
        startActivity(in);
        finish();

    }*/
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)
                &&
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)
        ){

            new AlertDialog.Builder(BeautyMainPage.context)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission Needed because of This and That")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.ACCESS_COARSE_LOCATION
                            },ACCESS_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton(R.string.CancelAlert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_PHONE_STATE
                    ,Manifest.permission.ACCESS_NETWORK_STATE
            },ACCESS_FINE_LOCATION);
        }
    }
    public void getLocation()
    {

    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            // locationManager.requestLocationUpdates(provider, 400, 1,this);

        }
        catch (SecurityException e){}
    }
    @Override
    public void onPause() {
        super.onPause();
        // locationManager.removeUpdates(this);
    }
    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        //latituteField.setText(String.valueOf(lat));
        Log.e("latituteField",String.valueOf(lat));

        Lat=lat+"";
        Long=lng+"";
        //longitudeField.setText(String.valueOf(lng));
        Log.e("longitudeField",String.valueOf(lng));
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(BeautyMainPage.context, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(BeautyMainPage.context, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==ACCESS_FINE_LOCATION){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Permission Granted",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }

}
