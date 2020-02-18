package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataModel.BestOfferItem;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class Offers extends AppCompatActivity implements LocationListener {
    public static Context context;
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);

        context=this;
        //----------------init recycle view ----------------------------
//        if (bestOfferItems.size()>0){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestLocationPermission();
        }
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.e("LOCATION","Location not available");
            //latituteField.setText("Location not available");
           // longitudeField.setText("Location not available");
        }
// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bestOfferItems.clear();
                APICall.bestOffer(Offers.this,Lat,Long);
            }
        });
        if (isFirstOpen){
            bestOfferItems.clear();
           // APICall.detailsUser4(context);
            APICall.bestOffer(Offers.this,Lat,Long);
            isFirstOpen=false;
        }

//        }
        recyclerView=findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        bestOffer=new OffersAdapter(this,bestOfferItems);
//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(bestOffer);
        //------------------------ call API bestOffers and get items-----------------
//        APICall.bestOffer(Offers.this);

        //-------------------------------call BagReservation after 5 minutes
    }
    //-------------------- go to main page -----------------------
    public void servicesBeauty(View view) {
        Intent in=new Intent(this,BeautyMainPage.class);
        startActivity(in);
        finish();

    }
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                &&
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
        ){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission Needed because of This and That")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Offers.this,new String[]{
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
            ActivityCompat.requestPermissions(this,new String[]{
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
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(provider, 400, 1,this);

        }
        catch (SecurityException e){}
    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
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
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

}
