package com.ptm.clinicpa.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Constants;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.Adapters.OffersAdapter;
import com.ptm.clinicpa.DataModel.BestOfferItem;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

public class Offers extends Fragment implements LocationListener ,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // public static Context context;
    RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    public static ArrayList<BestOfferItem> bestOfferItems = new ArrayList<>();
    public static OffersAdapter bestOffer;
    public static String name = "offers";
    static Boolean isFirstOpen = true;
    public static String Lat = "21.418923225457213", Long = "39.82685700058937";
    public static int ACCESS_FINE_LOCATION = 90;
    private LocationManager locationManager;
    private String provider;
    Toolbar toolbar;
    public static int bdb_booking_period;
    Location mLastLocation;
    public static ArrayList<DataOffer.SupIdClass> sersup_ids;
    static ImageView offerImage;



    public static Boolean check=false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_layout, container, false);
        toolbar = view.findViewById(R.id.toolbarm);
        BeautyMainPage.FRAGMENT_NAME = "Offers";
        offerImage= view.findViewById(R.id.offer_image);
        final Context context = getContext();
        check=false;

        try{
            bestOfferItems.clear();
            // APICall.detailsUser4(context);
            bestOffer.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });
        pullToRefresh = view.findViewById(R.id.pullToRefresh);

        //context=this;
        //----------------init recycle view ----------------------------
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }



        //-------------- get keys------------
        SharedPreferences prefs = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
      APICall.SERVER_KEY=  prefs.getString("SERVER_KEY","null");
        APICall.GOOGLE_KEY=  prefs.getString("GOOGLE_KEY","null");
        APICall.PROVIDER_SERVER_KEY=prefs.getString("PROVIDER_SERVER_KEY","null");
        if (APICall.SERVER_KEY.equals("null") || APICall.GOOGLE_KEY.equals("null") || APICall.PROVIDER_SERVER_KEY.equals("null") ){
           Log.e("GETKEYS","ok");
           if (prefs.getString("isGuest","").equals("0")){
               APICall.getSystemInfo(context);
           }else
            APICall.getGuestTokenThenInfo(context, FirebaseInstanceId.getInstance().getToken());
        }

//        if (bestOfferItems.size()>0){

        final LocationManager locationManager = (LocationManager)
                ((AppCompatActivity) BeautyMainPage.context).getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestLocationPermission();
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {



                    Lat=String.valueOf(location.getLatitude());
                    Long=String.valueOf(location.getLongitude());
                    Log.e("LATLANG",Lat+":"+Long);

                    if (bestOfferItems.size()==0)
                        if (!check) {
                            check = true;
                            bestOfferItems.clear();
                            bestOffer.notifyDataSetChanged();
                            // APICall.detailsUser4(context);
                            if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")) {
                                APICall.bestOffer(BeautyMainPage.context, Lat, Long);
                                Log.e("first","ok"+Lat+Long);

                            }else {
                                locationManager.removeUpdates(this);
                            }
                        }
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }

            });
        }

        LocationManager service = (LocationManager) BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.e("Enabled",service
                .isProviderEnabled(LocationManager.GPS_PROVIDER)+"is");
        if (!enabled) {
            showLocationServiceMsg(context);
           // APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.plsActivLoc));
            pullToRefresh.setRefreshing(false);
        }
        else {
            Toast.makeText(BeautyMainPage.context,R.string.please_wait_wihle_get_location_info,Toast.LENGTH_LONG).show();

            try{
                if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    requestLocationPermission();
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Lat=String.valueOf(location.getLatitude());
                        Long=String.valueOf(location.getLongitude());
                        Log.e("LATLANG",Lat+":"+Long);
                        if (bestOfferItems.size()==0)
                            if (!check) {
                                check = true;
                                bestOfferItems.clear();
                                // APICall.detailsUser4(context);
                                bestOffer.notifyDataSetChanged();
                                if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")) {
                                    APICall.bestOffer(BeautyMainPage.context, Lat, Long);
                                    Log.e("second","ok"+Lat+Long);
                                }else {
                                    locationManager.removeUpdates(this);
                                }
                            }

                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                    @Override
                    public void onProviderEnabled(String provider) {
                    }
                    @Override
                    public void onProviderDisabled(String provider) {
                    }

                });

            } catch (SecurityException e) {
                pullToRefresh.setRefreshing(false);

                if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestLocationPermission();
                }
            } catch (Exception e) {

            }
        }

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                check=false;
                try {
                    bestOfferItems.clear();
                    // APICall.detailsUser4(context);
                    bestOffer.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                LocationManager service = (LocationManager) BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                Log.e("Enabled",service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)+"is");
                if (!enabled) {
                    showLocationServiceMsg(context);
                    //APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.plsActivLoc));
                    pullToRefresh.setRefreshing(false);
                }
                else {
                    Toast.makeText(BeautyMainPage.context,R.string.please_wait_wihle_get_location_info,Toast.LENGTH_LONG).show();
                    try{
                    if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        requestLocationPermission();
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Lat=String.valueOf(location.getLatitude());
                            Long=String.valueOf(location.getLongitude());
                            Log.e("LATLANG",Lat+":"+Long);
                            if (bestOfferItems.size()==0)
                                if (!check) {
                                check = true;
                                bestOfferItems.clear();
                                // APICall.detailsUser4(context);
                                bestOffer.notifyDataSetChanged();
                                if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")) {
                                    APICall.bestOffer(BeautyMainPage.context, Lat, Long);
                                    Log.e("third","ok"+Lat+Long);
                                }else {
                                    locationManager.removeUpdates(this);
                                }
                            }

                        }
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }
                        @Override
                        public void onProviderEnabled(String provider) {
                        }
                        @Override
                        public void onProviderDisabled(String provider) {
                        }

                    });

                    } catch (SecurityException e) {
                        pullToRefresh.setRefreshing(false);

                        if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            requestLocationPermission();
                        }
                    } catch (Exception e) {

                    }
                }
              /*  bestOfferItems.clear();
                APICall.bestOffer(BeautyMainPage.context,Lat,Long);*/
            }
        });
        if (isFirstOpen) {

            isFirstOpen = false;
        }

//        }
        recyclerView = view.findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        bestOffer = new OffersAdapter(BeautyMainPage.context, bestOfferItems);
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                &&
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {

            new AlertDialog.Builder(BeautyMainPage.context)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission Needed because of This and That")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.ACCESS_COARSE_LOCATION
                            }, ACCESS_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton(R.string.CancelAlert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.ACCESS_NETWORK_STATE
            }, ACCESS_FINE_LOCATION);
        }
    }

    public void getLocation() {

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            // locationManager.requestLocationUpdates(provider, 400, 1,this);

        } catch (SecurityException e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat =  (location.getLatitude());
        Double lng =  (location.getLongitude());
        //latituteField.setText(String.valueOf(lat));
        Log.e("latituteField", String.valueOf(lat));

        Lat = lat + "";
        Long = lng + "";
        //longitudeField.setText(String.valueOf(lng));
        Log.e("longitudeField", String.valueOf(lng));
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
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private final FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    GoogleApiClient mGoogleApiClient;
//    private static boolean check=false;
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            final LocationRequest locationRequest = LocationRequest.create();
            locationRequest
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.e("LocationChanged","ok");
                    Lat=String.valueOf(fusedLocationProviderApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Long = String.valueOf(fusedLocationProviderApi.getLastLocation(mGoogleApiClient).getLongitude());
                    if (bestOfferItems.size()==0)
                    if (!check) {
                        check=false;
                        bestOfferItems.clear();
                       // APICall.bestOffer(BeautyMainPage.context, Lat, Long);
                        Log.e("forth","ok"+Lat+Long);
                    }

                }
            });
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
//            Lat = String.valueOf(mLastLocation.getLatitude());
//            Long = String.valueOf(mLastLocation.getLongitude());
//                bestOfferItems.clear();
                // APICall.detailsUser4(context);
//            APICall.bestOffer(BeautyMainPage.context, Lat, Long);
            }
        } else {
            requestLocationPermission();
        }


    }
        @Override
        public void onConnectionSuspended ( int i){

        }

        @Override
        public void onConnectionFailed (@NonNull ConnectionResult connectionResult){
            APICall.showSweetDialog(getActivity(), getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.plsActivLoc));

        }


        private void showLocationServiceMsg(final Context context)
        {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.lcation_service_turnon_msg);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       /* dialog.getWindow()
                .setBackgroundResource(android.R.color.transparent);*/
                TextView cancel=dialog.findViewById(R.id.cancel);
                TextView whatsSupport=dialog.findViewById(R.id.whatsapp_support);
                TextView webSupport=dialog.findViewById(R.id.website_support);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                webSupport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Uri uri = Uri.parse("http://vizagep.ptm.com.sa/contact.php");
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(myAppLinkToMarket);
                    }
                });
                whatsSupport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        SupportActivity.openWhatsappChat(context);
                    }
                });

                dialog.show();

            }

            public static void setOfferImage()
            {
                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        APICall.getSalonLogoDltWhenEmpty2(BeautyMainPage.context, Constants.offerImageId,offerImage);

                    }
                });
            }
}
