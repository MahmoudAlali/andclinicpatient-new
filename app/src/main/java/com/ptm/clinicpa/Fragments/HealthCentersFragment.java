package com.ptm.clinicpa.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.Adapters.HealthCentersAdapter;
import com.ptm.clinicpa.Adapters.RequestProvidersAdapter;
import com.ptm.clinicpa.DataModel.RequestProviderItem;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

import static com.ptm.clinicpa.Activities.TabOne.compareModels;

public class HealthCentersFragment extends Fragment implements LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static ArrayList<RequestProviderItem> providerItems=new ArrayList<>();
    public static HealthCentersAdapter providersAdapter;
    public static RecyclerView recyclerView;
    public static SwipeRefreshLayout pullToRefresh;
    static String lati,lngi;
    static double lat,lng;
    Location mLastLocation;
    public static int ACCESS_FINE_LOCATION = 90;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        providerItems.clear();
        View view= inflater.inflate(R.layout.request_centers_fragment,container,false);
        // filter =view.findViewById(R.id.filter);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
//        providersAdapter.notifyDataSetChanged();
        //---------------------call API for Services and get items-------------
        APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context, Offers.Lat,Offers.Long);
        if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!enabled)
        {
            showLocationServiceMsg(BeautyMainPage.context);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        /*final LocationManager locationManager = (LocationManager)
                ( BeautyMainPage.context).getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!enabled)
        {
            showLocationServiceMsg(BeautyMainPage.context);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat=location.getLatitude();
                    lng=location.getLongitude();
                    Log.e("LATLANG",lat+":"+lng);
                    //APICall.setlocation(lat,lng);
                    lati=lat+"";
                    lngi=lng+"";
                    APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context,lati,lngi);

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
        }*/
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                providerItems.clear();
                providersAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
                APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context, Offers.Lat,Offers.Long);
                /*LocationManager locationManager = (LocationManager)
                        ( BeautyMainPage.context).getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                boolean enabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if(!enabled)
                {
                    showLocationServiceMsg(BeautyMainPage.context);
                }
                else
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            lat=location.getLatitude();
                            lng=location.getLongitude();
                            Log.e("LATLANG",lat+":"+lng);
                            //APICall.setlocation(lat,lng);
                            lati=lat+"";
                            lngi=lng+"";
                            providersAdapter.notifyDataSetChanged();
                            //---------------------call API for Services and get items-------------
                            APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context,lati,lngi);
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
                }*/


            }
        });




        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        providersAdapter=new HealthCentersAdapter(BeautyMainPage.context,  providerItems,R.layout.centers_layout_adapter_last,false);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(providersAdapter);

        return view;
    }
    public static void refreshRV(){
        providersAdapter.notifyDataSetChanged();
//        recyclerView.invalidate();
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


    @Override
    public void onLocationChanged(Location location) {

        /*//longitudeField.setText(String.valueOf(lng));
        Log.e("longitudeField", String.valueOf(lng));
        Log.e("KKKKKKK","onLocationChanged");
        lati=location.getLatitude()+"";
        lngi=location.getLongitude()+"";
        providersAdapter.notifyDataSetChanged();
        //---------------------call API for Services and get items-------------
        APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context,lati,lngi);*/
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
                    Log.e("KKKKKKK","onLocationChanged");
                    lati=location.getLatitude()+"";
                    lngi=location.getLongitude()+"";
                    providersAdapter.notifyDataSetChanged();
                    //---------------------call API for Services and get items-------------
                    APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context, Offers.Lat,Offers.Long);

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
                Log.e("KKKKKKK","onLocationChanged");
                lati=mLastLocation.getLatitude()+"";
                lngi=mLastLocation.getLongitude()+"";
                providersAdapter.notifyDataSetChanged();
                //---------------------call API for Services and get items-------------
                APICall.automatedCentersBrowse( 1+"", BeautyMainPage.context, Offers.Lat,Offers.Long);
            }
        } else {
            //requestLocationPermission();
        }


    }
    @Override
    public void onConnectionSuspended ( int i){

    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult){
        APICall.showSweetDialog(getActivity(), getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.plsActivLoc));

    }

}
