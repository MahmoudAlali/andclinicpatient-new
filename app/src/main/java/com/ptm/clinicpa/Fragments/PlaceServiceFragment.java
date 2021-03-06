package com.ptm.clinicpa.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.DataModel.ServiceFilter;
import com.ptm.clinicpa.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaceServiceFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LinearLayout  service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static ArrayList<String> mylocation = new ArrayList();
    public static double lat,lng;
    Button ok;
    public static Spinner  placeSpinner;
    public static int citiyitemSelected;
    public static int placeId = 0;
    ArrayAdapter locatioAdapter;
    public static String maxValDistance,mylocationId="";
    TextView distance,mylocationbtn,offerPrice, priceService, rateService;
    static  boolean fregmentIsFirstOpen=false;
    public static String distanceOffer="",locOfferlat="",locOfferlong="",place_service="",priceOffer="",rateOffer="",supRate="";
    public static String priceServiceValue="",minprice="",maxprice="";
    public static TextView date;
    public static String offerPlace="";
    public static String dateFilter="",dateFilterOffer="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_place_service_frag, container, false);
        //service_hair = view.findViewById(R.id.service_hair);



        //---------------------init my location spinner-----------
        APICall.isBride="0";
        BeautyMainPage.FRAGMENT_NAME = "PLACESERVICEFRAGMENT";

        placeSpinner = view.findViewById(R.id.service_place);
        mylocationbtn = view.findViewById(R.id.my_location);
        priceService = view.findViewById(R.id.service_price);
        rateService = view.findViewById(R.id.service_rate);
        distance = view.findViewById(R.id.distance);
        ok = view.findViewById(R.id.ok);


        Log.e("GUESTIS","isss"+APICall.isGuest(BeautyMainPage.context).equals("1"));


        if (BeautyMainPage.client_name.equals(""))
            APICall.details_user(APICall.API_PREFIX_NAME+"/api/auth/user/detailsUser",BeautyMainPage.context);



        for (int i = 0; i < ServiceFragment.serviceFilters.size(); i++) {
            if (i == 2) {
                if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                    priceService.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                    price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());

                }
            } else if (i == 3) {
                if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                    rateService.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                    rateService.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                }
            } else if (i == 4) {
                if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                    rateProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                    rateProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                }
            } else if (i == 5) {
                if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                    distance.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                    distance.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                }
            } else if (i == 7) {
                if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                    priceService.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                    servicePlace.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                }
            } else if (i == 1) {
//                                    price.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                    price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
            }

        }


        date=view.findViewById(R.id.date);
        if (ServiceFragment.date.equals("")) {
            date.setText(R.string.date);
        }else {
            date.setText(ServiceFragment.date);
        }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(BeautyMainPage.context);
                dialog.setContentView(R.layout.active_date_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                Button search=dialog.findViewById(R.id.search);
                final DatePicker datePicker=dialog.findViewById(R.id.date);
                datePicker.setMinDate(System.currentTimeMillis() - 1000);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        int month=datePicker.getMonth()+1;
                        date.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                        APICall.DATE_FOR_SER_OFR=date.getText().toString();
//                        dateFilter=date.getText().toString();
//                        ServiceFragment.date=dateFilter;
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        date.setText(R.string.date);
                    }
                });
                dialog.show();
            }

        });





        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-------------- range distance filter--------------------
//                        if (distance.isChecked()) {
                            final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
                            rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
                            rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            // get seekbar from view
                            final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
                            rangeSeekbar.setMaxValue(10000);
                            // get min and max text view
                            TextView title = rangeDistanceDialog.findViewById(R.id.title);
                            title.setText(R.string.distance);
                            final TextView tvMin = rangeDistanceDialog.findViewById(R.id.textMin1);
                            final TextView tvMax = rangeDistanceDialog.findViewById(R.id.textMax1);
                            final EditText Min = rangeDistanceDialog.findViewById(R.id.minval);
                            final EditText Max = rangeDistanceDialog.findViewById(R.id.maxval);
                            Button search = rangeDistanceDialog.findViewById(R.id.search);
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
                                    rangeDistanceDialog.dismiss();
                                     maxValDistance=tvMax.getText().toString();
                                     String s=getResources().getString(R.string.distance);
                                    distance.setText(s+": " +APICall.convertToArabic( Min.getText().toString() + "-" + Max.getText().toString()));
                                    APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
                                    ServiceFragment.serviceFilters.set(5, new ServiceFilter(true, distance.getText().toString()));
//                               ------------For Offer Filter-------------------------------
                                distanceOffer=",{\"num\":2,\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";
                                    Log.e("DistanceOffer",distanceOffer);
                                }
                            });


                            rangeDistanceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    distance.setText(R.string.distance);
                                    APICall.filterSortAlgorithm("2", "", "");
                                    ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));

                                }
                            });
                            rangeDistanceDialog.show();

            }
        });


        final HintArrayAdapter adapter = new HintArrayAdapter(BeautyMainPage.context, 0);
        adapter.addAll(Arrays.asList(getResources().getStringArray(R.array.service_place)));
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
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
                            offerPlace="0";
                            break;
                        case 2:
                            placeId = 1;
                            offerPlace="1";
                            break;
                        case 3:
                            placeId = 30;
                            offerPlace="2";
                            break;
                        case 4:
                            placeId = 31;
                            offerPlace="3";
                            break;
                    }
                    if (position!=0){
                        if (position==1) {
                            PlaceServiceFragment.place_service = ",{\"num\":9,\"value1\":1}";
                        }else if (position==2){
                            PlaceServiceFragment.place_service = ",{\"num\":8,\"value1\":1}";

                        }else if (position==3){
                            PlaceServiceFragment.place_service = ",{\"num\":10,\"value1\":1}";

                        }else if (position==4){
                            PlaceServiceFragment.place_service = ",{\"num\":11,\"value1\":1}";

                        }
                    }

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");

            }
        });

//        if (fregmentIsFirstOpen==false){
            APICall.getdetailsUser(BeautyMainPage.context);
//            fregmentIsFirstOpen=true;
//        }



        Log.e("Size",mylocation.size()+"");
        if (mylocationId.equals("")){
            mylocationId=getResources().getString(R.string.MyLocation);
        }
        mylocationbtn.setText(mylocationId);
        mylocationbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                    PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v,Gravity.CENTER);
                    mylocation.clear();
                    mylocation.add(getResources().getString(R.string.MyLocation));
                    mylocation.add(getResources().getString(R.string.current_location));
                for (int i = 0; i < AccountFragment.locationTitles.size(); i++)
                    mylocation.add(AccountFragment.locationTitles.get(i).getBdb_my_descr());
                    mylocation.add(getResources().getString(R.string.new_location));
                for (int i=0;i<mylocation.size();i++) {
                    popupMenu.getMenu().add(mylocation.get(i));
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(getResources().getString(R.string.current_location))) {

                            LocationManager locationManager = (LocationManager)
                                    ((AppCompatActivity) BeautyMainPage.context).getSystemService(Context.LOCATION_SERVICE);

                            if (ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                            }
                            if (mGoogleApiClient == null) {
                                mGoogleApiClient = new GoogleApiClient.Builder(BeautyMainPage.context)
                                        .addConnectionCallbacks(PlaceServiceFragment.this)
                                        .addOnConnectionFailedListener( PlaceServiceFragment.this)
                                        .addApi(LocationServices.API)
                                        .build();
                            }
                            if (mGoogleApiClient != null) {
                                mGoogleApiClient.connect();
                            }
                            boolean enabled = locationManager
                                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
                            if(!enabled)
                            {
                                showLocationServiceMsg(BeautyMainPage.context);
                            }
                            else
                            {
                                mylocationId=item.getTitle().toString();
                                mylocationbtn.setText(mylocationId);
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        lat=location.getLatitude();
                                        lng=location.getLongitude();
                                        Log.e("LATLANG",lat+":"+lng);
                                        APICall.setlocation(lat,lng);
                                        locOfferlat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                        locOfferlong="," +
                                                "{\"num\":35,\"value1\":"+lng+",\"value2\":0}";

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


                        }else if (item.getTitle().equals(getResources().getString(R.string.new_location))){
                            fragment = new MapFragment();
                            fm = getActivity().getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            BeautyMainPage.FRAGMENT_NAME="SPINNER";

                        }else {
                            for (int i=0;i<AccountFragment.locationTitles.size();i++){
                                if (item.getTitle().equals(AccountFragment.locationTitles.get(i).getBdb_my_descr())){
                                    Log.e("Spinner",item.getTitle().toString());
                                    Log.e("Loction_title",AccountFragment.locationTitles.get(i).getBdb_my_descr()+":"+AccountFragment.locationTitles.get(i).getLatLng());
                                    lat=AccountFragment.locationTitles.get(i).getLatLng().latitude;
                                    lng=AccountFragment.locationTitles.get(i).getLatLng().longitude;
                                    Log.e("LATLANG",lat+":"+lng);
                                    APICall.setlocation(lat,lng);
                                    locOfferlat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    locOfferlong=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
                                }
                            }
                            mylocationId=item.getTitle().toString();
                            mylocationbtn.setText(mylocationId);
                        }


                        return false;
                    }
                });


                popupMenu.show();

            }
        });


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
                if(mylocationbtn.getText().toString().equals(getResources().getString(R.string.MyLocation))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.location_proceed));
                } else  if (placeSpinner.getSelectedItemPosition() == 0) {
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.place_proceed));
                } else if(distance.getText().toString().equals(getResources().getString(R.string.distance))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.distance_proceed));
                } else if(date.getText().toString().equals(getResources().getString(R.string.date))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.choose_date));
                } else {
//                    APICall.setCityId(placeSpinner.getSelectedItemPosition());
                    dateFilter =  ",{\"num\":44,\"value1\":\"" + date.getText().toString() + "\"}";

                    dateFilterOffer = "  ,{\"num\":13,\"value1\":\"" + date.getText().toString() + "\"}\n" +
                            "     ,{\"num\":44,\"value1\":\"" + date.getText().toString() + "\"}";

                    citiyitemSelected = placeSpinner.getSelectedItemPosition();
                    fragment = new ServicesTabsFragment();
                    fm = getActivity().getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
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
                                    priceService.setText(BeautyMainPage.context.getResources().getString(R.string.price)+": " + Min.getText().toString() + "-" + Max.getText().toString());
                                    APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", Min.getText().toString(), Max.getText().toString());
                                    ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, priceService.getText().toString()));
//                                    priceOffer=",{\"num\":32,\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";

                                    minprice=tvMin.getText().toString();
                                    maxprice=tvMax.getText().toString();

//                                  priceServiceValue=",{\"num\":"+placeId+",\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";

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
                                    priceOffer="";
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
                        rateService.setText(BeautyMainPage.context.getResources().getString(R.string.srvcEvaluation)+": " + (int) ratingBar.getRating());
                        APICall.filterSortAlgorithm("5", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(true, rateService.getText().toString()));
                        rateOffer=",{\"num\":5,\"value1\":"+ratingBar.getRating()+",\"value2\":"+ratingBar.getRating()+"}";

                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        rateService.setChecked(false);
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                        rateServiceDialog.dismiss();
                        rateOffer="";
                    }
                });
                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                        rateService.setChecked(false);
                        rateService.setText(R.string.srvcEvaluation);
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                        Log.e("Cancel","ok");
                        APICall.filterSortAlgorithm("5", "", "");
                        rateOffer="";

                    }
                });
                rateServiceDialog.show();
            }
        });


        offerPrice=view.findViewById(R.id.offerPrice);
        offerPrice.setOnClickListener(new View.OnClickListener() {
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
                        offerPrice.setText(BeautyMainPage.context.getResources().getString(R.string.price)+": " + Min.getText().toString() + "-" + Max.getText().toString());
//                        APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", Min.getText().toString(), Max.getText().toString());
//                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, priceService.getText().toString()));
                        priceOffer=",{\"num\":39,\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";


                    }
                });

                rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                                    price.setChecked(false);
                        offerPrice.setText(R.string.ServicePrice);
//                                    Log.e("Cancel","ok");
//                        APICall.filterSortAlgorithm(placeId+"", "", "");
//                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, priceService.getText().toString()));
                        priceOffer="";
                    }
                });

                rangePriceDialog.show();

            }
        });
        return view;
    }

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(BeautyMainPage.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            APICall.setlocation(lat,lng);
            Log.e("LATLANG111",lat+":"+lng);

            locOfferlat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
            locOfferlong="," +
                    "{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
            // APICall.detailsUser4(context);
//            APICall.bestOffer(BeautyMainPage.context, Lat, Long);
        }

    } else {
    }

}

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
}
