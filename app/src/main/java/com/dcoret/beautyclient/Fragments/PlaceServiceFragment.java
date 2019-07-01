package com.dcoret.beautyclient.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Services;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PlaceServiceFragment extends Fragment {
    //    LinearLayout services_tabs;
    LinearLayout bride_service, service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static ArrayList citiyname = new ArrayList();
    public static ArrayList placeServiceList = new ArrayList();
    public static ArrayList mylocation = new ArrayList();
//    public static LatLng latLng;
    public static double lat,lng;
    ArrayList price = new ArrayList();
    ArrayList rate = new ArrayList();
    Button ok, priceService, rateService;
    Spinner mylocationSpinner, placeSpinner;
    public static int citiyitemSelected;
    public static int placeId = 0;
    ArrayAdapter locatioAdapter;
    public static int mylocationId=0;
//    public static ArrayList<FilterAndSortModel> filterList=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_place_service_frag, container, false);
        service_hair = view.findViewById(R.id.service_hair);


        //---------------------init my location spinner-----------
        mylocation.clear();
        mylocation.add("موقعي");
        mylocation.add("current location");
        for (int i = 0; i < MapFragment.arrayList.size(); i++)
            mylocation.add(MapFragment.arrayList.get(i));
        mylocation.add("new Location");


        BeautyMainPage.FRAGMENT_NAME = "PLACESERVICEFRAGMENT";
        Resources res = getResources();

        placeSpinner = view.findViewById(R.id.service_place);
        mylocationSpinner = view.findViewById(R.id.my_location);
        priceService = view.findViewById(R.id.service_price);
        rateService = view.findViewById(R.id.service_rate);
        ok = view.findViewById(R.id.ok);


        final ArrayAdapter adapter = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.service_place, android.R.layout.simple_spinner_item);
        //new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_dropdown_item,placeServiceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(adapter);

        switch (placeId) {
            case 32:
                placeSpinner.setSelection(1);
                break;
            case 1:
                placeSpinner.setSelection(2);
                break;
            case 30:
                placeSpinner.setSelection(3);
                break;
            case 31:
                placeSpinner.setSelection(4);
                break;
        }

        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    switch (position) {
                        case 1:
                            placeId = 32;
                            break;
                        case 2:
                            placeId = 1;
                            break;
                        case 3:
                            placeId = 30;
                            break;
                        case 4:
                            placeId = 31;
                            break;
                    }

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");

            }
        });

//

//        price.add(res.getResourceEntryName(R.string.ServicePrice));
//        final ArrayAdapter mylocationSpinner= ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.service_price,android.R.layout.simple_spinner_item);
         locatioAdapter = new ArrayAdapter(BeautyMainPage.context, android.R.layout.simple_spinner_dropdown_item, mylocation);
        locatioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mylocationSpinner.setAdapter(locatioAdapter);
        mylocationSpinner.setSelection(mylocationId);

        mylocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mylocationId=position;
                    LocationManager locationManager = (LocationManager)
                            ((AppCompatActivity) BeautyMainPage.context).getSystemService(Context.LOCATION_SERVICE);
//                    LocationListener locationListener = new MyLocation();
                    if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
//                    Log.e("EEEEEEEEEEEEEEEEEEE","ok");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            lat=location.getLatitude();
                            lng=location.getLongitude();
                            Log.e("LATLANG",lat+":"+lng);
                            APICall.setlocation(lat,lng);
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
                }else if (position==locatioAdapter.getCount()-1){
                    fragment = new MapFragment();
                    fm = getActivity().getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
//                    fragmentTransaction.detach(fragment);
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                    BeautyMainPage.FRAGMENT_NAME="SPINNER";

                }else {
                    for (int i=0;i<MapFragment.locationTitles.size();i++){
                        if (mylocationSpinner.getSelectedItem().toString().equals(MapFragment.locationTitles.get(i).getTitle())){
                            Log.e("Spinner",mylocationSpinner.getSelectedItem().toString());
                            Log.e("Loction_title",MapFragment.locationTitles.get(i).getTitle()+":"+MapFragment.locationTitles.get(i).getLatLng());
                            lat=MapFragment.locationTitles.get(i).getLatLng().latitude;
                            lng=MapFragment.locationTitles.get(i).getLatLng().longitude;
//                            lng=location.getLongitude();
                            Log.e("LATLANG",lat+":"+lng);
                            APICall.setlocation(lat,lng);

                        }
                    }
                    mylocationId=position;

//                    MapFragment.locationTitles

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





//        rate.add(res.getString(R.string.ServiceRate));
//        final ArrayAdapter adapterrate= ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.service_rate,android.R.layout.simple_spinner_item);
////        ArrayAdapter adapterrate=new ArrayAdapter(BeautyMainPage.context,android.R.layout.simple_spinner_dropdown_item,rate);
//        adapterrate.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
//        rateSpinner.setAdapter(adapterrate);

        toolbar= view.findViewById(R.id.toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // If the navigation drawer is not open then open it, if its already open then close it.
//                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
//                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);

                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mylocationSpinner.getSelectedItemPosition()==0){

                    APICall.showSweetDialog(BeautyMainPage.context,"","you have to choose a location to proceed");

                } else  if (placeSpinner.getSelectedItemPosition() == 0) {

                    APICall.showSweetDialog(BeautyMainPage.context,"","you have to choose a service place to proceed");

//                    Toast.makeText(BeautyMainPage.context,"Please Select an Place You want To Receive Service!",Toast.LENGTH_LONG).show();

                }  else {
//                    APICall.setCityId(placeSpinner.getSelectedItemPosition());
                    citiyitemSelected = placeSpinner.getSelectedItemPosition();
                    fragment = new ServicesTabsFragment();
                    fm = getActivity().getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
//                citiyname.remove(1);

                }
            }
        });


        priceService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //-------------- range price filter--------------------

                            final Dialog rangePriceDialog = new Dialog(BeautyMainPage.context);
                            rangePriceDialog.setContentView(R.layout.price_range_dialog);
                            rangePriceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            // get seekbar from view
                            final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangePriceDialog.findViewById(R.id.rangeSeekbar5);

                            // get min and max text view
                            final TextView tvMin = (TextView) rangePriceDialog.findViewById(R.id.textMin1);
                            final TextView tvMax = (TextView) rangePriceDialog.findViewById(R.id.textMax1);
                            final EditText Min = rangePriceDialog.findViewById(R.id.minval);
                            final EditText Max = rangePriceDialog.findViewById(R.id.maxval);
                            Button search = rangePriceDialog.findViewById(R.id.search);
                            // set listener
                            rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    tvMin.setText(String.valueOf(minValue));
                                    Min.setText(String.valueOf(minValue));
                                    Max.setText(String.valueOf(maxValue));
                                    tvMax.setText(String.valueOf(maxValue));
                                }
                            });

                            // set final value listener
                            rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                                @Override
                                public void finalValue(Number minValue, Number maxValue) {
                                    Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                                }
                            });

                            search.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rangePriceDialog.dismiss();
                                    priceService.setText("Price:" + Min.getText().toString() + "-" + Max.getText().toString());
                                    APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", Min.getText().toString(), Max.getText().toString());
                                    ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, priceService.getText().toString()));

                                }
                            });

                            rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
//                                    price.setChecked(false);
                                    priceService.setText(R.string.ServicePrice);
//                                    Log.e("Cancel","ok");
                                    APICall.filterSortAlgorithm(placeId+"", "", "");
                                    ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, priceService.getText().toString()));
                                }
                            });

                            rangePriceDialog.show();


            }
        });

        rateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog rateServiceDialog = new Dialog(BeautyMainPage.context);
                rateServiceDialog.setContentView(R.layout.rating_dialog);
                Button ok = rateServiceDialog.findViewById(R.id.ok);
                Button cancel = rateServiceDialog.findViewById(R.id.cancel);
                final RatingBar ratingBar = rateServiceDialog.findViewById(R.id.ratingBar);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateServiceDialog.dismiss();
                        rateService.setText("Service Evaluation: " + (int) ratingBar.getRating());
                        APICall.filterSortAlgorithm("5", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(true, rateService.getText().toString()));


                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        rateService.setChecked(false);
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                        rateServiceDialog.dismiss();
                    }
                });
                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                        rateService.setChecked(false);
                        rateService.setText("Service Evaluation");
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                        Log.e("Cancel","ok");
                        APICall.filterSortAlgorithm("5", "", "");

                    }
                });

                rateServiceDialog.show();
//            } else {
//                rateService.setText("تقييم الخدمة");
//                APICall.filterSortAlgorithm("22", "", "");
//                serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
//
//
//            }
            }
        });

        return view;
    }

    class MyLocation implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {


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
    }

}
