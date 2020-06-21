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
import android.location.Criteria;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.DataModel.ServiceFilter;
import com.ptm.clinicpa.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class MyOffersFilters extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    public static String maxValDistance,mylocationId="";
    static TextView distance,mylocationbtn, clinicName, centerRate,offerDate,services,offerPrice;
    public static String filterSupplierName="",
            filterSupplierId="",
            filterServiceId="",
            filterDistance="",
            filterOfferPrice="",
            filterClinicName="",
            filterOfferDate="",
            filterMyLocationLat="",
            filterMyLocationLatNoComa="",
            filterMyLocationLng="",
            filterClinicRate="";
    public static ArrayList<String> mylocation = new ArrayList();
    public static double lat,lng;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    ArrayList<String> specialitiesList=new ArrayList<>();
    public static String clinName="",salonId="",docName="";
    public static String Name="";
    private static ArrayList<String> servicesList=new ArrayList<>();
    public static HintArrayAdapter adapter;
    Button ok;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_my_offers_filters, container, false);

        mylocationbtn = view.findViewById(R.id.my_location);
        clinicName = view.findViewById(R.id.providerName);
        distance = view.findViewById(R.id.distance);
        centerRate = view.findViewById(R.id.service_rate);
        offerDate = view.findViewById(R.id.service_exec_date);
        offerPrice = view.findViewById(R.id.service_price);
        services = view.findViewById(R.id.service_place);
        //nearestCenters = view.findViewById(R.id.nearestCenters);
        ok = view.findViewById(R.id.ok);

        APICall.getdetailsUser(BeautyMainPage.context);
        filterSupplierName="";
        filterSupplierId="";
        filterDistance="";
        filterClinicName="";
        filterOfferDate="";
        filterMyLocationLat="";
        filterMyLocationLng="";
        filterOfferPrice="";
        filterClinicRate="";
        if(!MyOffersFragment.salonFilterName.equals(""))
        {
            clinicName.setText(MyOffersFragment.salonFilterName);
            filterSupplierId=MyOffersFragment.salonFilterOld;
        }
        if(!MyOffersFragment.dateFilterName.equals(""))
        {
            offerDate.setText(MyOffersFragment.dateFilterName);
            filterOfferDate=MyOffersFragment.dateFilterOld;

        }
        if(!MyOffersFragment.distanceFilterName.equals(""))
        {
            distance.setText(MyOffersFragment.distanceFilterName);
            filterDistance=MyOffersFragment.distanceFilterOld;

        }
        if(!MyOffersFragment.locationFilterName.equals(""))
        {
            mylocationbtn.setText(MyOffersFragment.locationFilterName);
            filterMyLocationLat=MyOffersFragment.locationFilterOld;
            filterMyLocationLng=MyOffersFragment.locationFilterOld2;
        }
        if(!MyOffersFragment.clinicRateFilterName.equals(""))
        {
            centerRate.setText(MyOffersFragment.clinicRateFilterName);
            filterClinicRate=MyOffersFragment.clinicRateFilterOld;

        }
        if(!MyOffersFragment.serviceFilterName.equals(""))
        {
            services.setText(MyOffersFragment.specialityFilterName);
            filterServiceId=MyOffersFragment.serviceFilterOld;

        }
        if(!MyOffersFragment.priceFilterName.equals(""))
        {
            offerPrice.setText(MyOffersFragment.priceFilterName);
            filterOfferPrice=MyOffersFragment.priceFilterOld;
        }
        // region get Current Location
        {

            LocationManager locationManager = (LocationManager)
                    (BeautyMainPage.context).getSystemService(Context.LOCATION_SERVICE);
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
                // showLocationServiceMsg(BeautyMainPage.context);
            }
            else
            {
                Criteria crit = new Criteria();
                crit.setAccuracy(Criteria.ACCURACY_FINE);
                //locationManager.requestLocationUpdates( locationListener);
                LocationManager locationManager2 = (LocationManager)BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
                locationManager2.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //Log.e("LATLANG",lat+":"+lng +"FIRST");
                        lat=location.getLatitude();
                        lng=location.getLongitude();
                        Log.e("OffersFilters",lat+":"+lng);
                        //APICall.setlocation(lat,lng);
                        filterMyLocationLat=",{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                        filterMyLocationLatNoComa="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                        filterMyLocationLng=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
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

        }

        //endregion


        //region Clinic Name

        clinicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mylocationbtn.getText().toString().equals(getResources().getString(R.string.MyLocation))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.location_first));
                } else if(distance.getText().toString().equals(getResources().getString(R.string.distance))) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.distance_first));
                }
                else
                    APICall.getClinics(BeautyMainPage.context,filterMyLocationLat,filterMyLocationLng,filterDistance);

            }
        });

        //endregion


        //region Service

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mylocationbtn.getText().toString().equals(getResources().getString(R.string.MyLocation))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.location_first));
                } else if(distance.getText().toString().equals(getResources().getString(R.string.distance))) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.distance_first));
                }
                else
                    APICall.getAllServices(BeautyMainPage.context,filterMyLocationLatNoComa,filterMyLocationLng,filterDistance);

            }
        });

        //endregion

        //region My Location


        if (mylocationId.equals("")){
            mylocationId=getResources().getString(R.string.MyLocation);
        }
        mylocationbtn.setText(mylocationId);
        mylocationbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v, Gravity.CENTER);
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
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(getResources().getString(R.string.current_location))) {

                            LocationManager locationManager = (LocationManager)
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
                                mylocationId=item.getTitle().toString();
                                mylocationbtn.setText(mylocationId);
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        Log.e("LATLANG",lat+":"+lng +"FIRST");
                                        lat=location.getLatitude();
                                        lng=location.getLongitude();
                                        Log.e("LATLANG",lat+":"+lng);
                                        //APICall.setlocation(lat,lng);
                                        filterMyLocationLat=",{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                        filterMyLocationLatNoComa="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                        filterMyLocationLng=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";

                                        clinicName.setText(getResources().getText(R.string.providerName));
                                        filterSupplierName="";
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
                                    // APICall.setlocation(lat,lng);
                                    filterMyLocationLat=",{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    filterMyLocationLatNoComa="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    filterMyLocationLng=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
                                    clinicName.setText(getResources().getText(R.string.providerName));
                                    filterSupplierName="";
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


        //endregion

        //region Distance

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
                        tvMin.setText(String.valueOf(minValue)+" "+getString(R.string.KM));
                        Min.setText(String.valueOf(minValue));
                        Max.setText(String.valueOf(maxValue));
                        tvMax.setText(String.valueOf(maxValue)+" "+getString(R.string.KM));
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
                        maxValDistance=Max.getText().toString();
                        String s=getResources().getString(R.string.distance);
                        distance.setText(s+": " +APICall.convertToArabic( Min.getText().toString() + "-" + Max.getText().toString()));
                        //APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
                        //ServiceFragment.serviceFilters.set(5, new ServiceFilter(true, distance.getText().toString()));
//                               ------------For Offer Filter-------------------------------
                        filterDistance=","+"{\"num\":2,\"value1\":"+Min.getText()+",\"value2\":"+Max.getText()+"}";
                        // Log.e("DistanceOffer",distanceOffer);
                        clinicName.setText(getResources().getText(R.string.providerName));
                        filterSupplierName="";
                    }
                });


                rangeDistanceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        distance.setText(R.string.distance);
                        filterDistance="";
                        //APICall.filterSortAlgorithm("2", "", "");
                        // ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));

                    }
                });
                rangeDistanceDialog.show();

            }
        });

        //endregion
/*

        //region Speciality

        specialityType=view.findViewById(R.id.offerPrice);
        specialityType.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                //-------------- range price filter--------------------
                if (specialitiesList.size()==0)
                    for (int i=0;i<APICall.allSpecialities.size();i++){
                        if(getResources().getString(R.string.locale).equals("en"))
                            specialitiesList.add(APICall.allSpecialities.get(i).getBdb_name());
                        else
                            specialitiesList.add(APICall.allSpecialities.get(i).getBdb_name_ar());

                    }

                final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
                namesalonDialog.setContentView(R.layout.provider_name_layout);
                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //final Spinner name=namesalonDialog.findViewById(R.id.name);
                final EditText name=namesalonDialog.findViewById(R.id.name);
                final TextView title=namesalonDialog.findViewById(R.id.title);
                title.setText(R.string.speciality);
                final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
                HintArrayAdapter adapter=new HintArrayAdapter(BeautyMainPage.context,0);
                adapter.addAll(specialitiesList);
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                add_service.setTitle(getResources().getString(R.string.specialities));
                add_service.setAdapter(adapter);
                // set listener
                add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  salonName="\"supplier_name\":" +"\""+ APICall.allSpecialities.get(position).getBdb_name()+"\",";
                        //  salonId="\"SupplierId\":" +"\""+ APICall.allSpecialities.get(position).getBdb_ser_id()+"\",";

                        salonId= Filters.getString(Filters.SPECIALITY_ID,APICall.allSpecialities.get(position).getBdb_ser_id());


                        if(getResources().getString(R.string.locale).equals("en"))
                            Name=APICall.allSpecialities.get(position).getBdb_name();
                        else
                            Name=APICall.allSpecialities.get(position).getBdb_name_ar();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        salonId="";
                    }
                });

                Button search = namesalonDialog.findViewById(R.id.search);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if (!name.getSelectedItem().toString().isEmpty()){
                        if (!name.getText().equals("")){
                            namesalonDialog.dismiss();
                            specialityType.setText( Name);
                           */
/* filterSupplierName=salonName;
                            filterSupplierId=salonId;*/
/*

                            filterSpeciality=salonId;
                            clinicName.setText(getResources().getText(R.string.providerName));
                            filterSupplierName="";

                        }else {
                            namesalonDialog.cancel();
                            specialityType.setText(getResources().getText(R.string.speciality));
                            filterSpeciality="";
                        }




                    }
                });
                namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // nameSalonOrProvider.setChecked(false);
                        specialityType.setText(getResources().getText(R.string.speciality));
                        filterSpeciality="";
                        APICall.filterSortAlgorithm("3", "", "");
                        // ServiceFragment.serviceFilters.set(7, new ServiceFilter(false, providerName.getText().toString()));
                        // idsup="";
                    }
                });
                namesalonDialog.show();

            }
        });

        //endregion
*/

        //region Center Rate

        centerRate.setOnClickListener(new View.OnClickListener() {
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
                        centerRate.setText(BeautyMainPage.context.getResources().getString(R.string.srvcEvaluation)+": " + (int) ratingBar.getRating());
                        filterClinicRate=","+Filters.getString(Filters.CENTER_RATING,(int) ratingBar.getRating()+"",(int) ratingBar.getRating()+"");
                        // APICall.filterSortAlgorithm("50", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                        //ServiceFragment.serviceFilters.set(3, new ServiceFilter(true, rateService.getText().toString()));


                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        rateService.setChecked(false);
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, centerRate.getText().toString()));
                        rateServiceDialog.dismiss();
                    }
                });
                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                        rateService.setChecked(false);
                        centerRate.setText(R.string.srvcEvaluation);
                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, centerRate.getText().toString()));
                        Log.e("Cancel","ok");
                        APICall.filterSortAlgorithm("5", "", "");

                    }
                });
                rateServiceDialog.show();
            }
        });

        //endregion

        //region Offer Price
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
                        filterOfferPrice=",{\"num\":39,\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";


                    }
                });

                rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                                    price.setChecked(false);
                        offerPrice.setText(R.string.OfferPrice);
//                                    Log.e("Cancel","ok");
//                        APICall.filterSortAlgorithm(placeId+"", "", "");
//                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, priceService.getText().toString()));
                        filterOfferPrice="";
                    }
                });

                rangePriceDialog.show();

            }
        });

        //endregion

        //region offer Date

        offerDate.setOnClickListener(new View.OnClickListener() {
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
                        offerDate.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                        filterOfferDate = "  ,{\"num\":13,\"value1\":\"" + offerDate.getText().toString() + "\"}\n" +
                                "     ,{\"num\":44,\"value1\":\"" + offerDate.getText().toString() + "\"}";
                        //APICall.DATE_FOR_SER_OFR=date.getText().toString();
//                        dateFilter=date.getText().toString();
//                        ServiceFragment.date=dateFilter;
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        offerDate.setText(R.string.date);
                        filterOfferDate="";
                    }
                });
                dialog.show();
            }

        });

        //endregion

     /*   //region Nearest Centers

        nearestCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragment= new HealthCentersFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }

        });

        //endregion
*/
        //region Search

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOffersFragment.salonFilterOld=filterSupplierId;
                MyOffersFragment.dateFilterOld=filterOfferDate;
                MyOffersFragment.distanceFilterOld=filterDistance;
                MyOffersFragment.priceFilterOld=filterOfferPrice;
                MyOffersFragment.locationFilterOld=filterMyLocationLat;
                MyOffersFragment.locationFilterOld2=filterMyLocationLng;
                MyOffersFragment.serviceFilterOld=filterServiceId;
                MyOffersFragment.clinicRateFilterOld=filterClinicRate;

                MyOffersFragment.salonFilterName=clinicName.getText().toString();
                MyOffersFragment.dateFilterName=offerDate.getText().toString();
                MyOffersFragment.locationFilterName=mylocationbtn.getText().toString();
                MyOffersFragment.priceFilterName=offerPrice.getText().toString();
                MyOffersFragment.clinicRateFilterName=centerRate.getText().toString();
                MyOffersFragment.distanceFilterName=distance.getText().toString();
                MyOffersFragment.serviceFilterName=services.getText().toString();
                fragment= new MyOffersFragment();

                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        //endregion
        return view;
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

    public static void showClinicsNamesFilterDialog(final Context context)
    {
        servicesList.clear();
        for (int i=0;i<APICall.allClinics.size();i++){
            if(context.getResources().getString(R.string.locale).equals("en"))
                servicesList.add(APICall.allClinics.get(i).getBdb_name());
            else
                servicesList.add(APICall.allClinics.get(i).getBdb_name_ar());
        }

        final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
        namesalonDialog.setContentView(R.layout.provider_name_layout);
        namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //final Spinner name=namesalonDialog.findViewById(R.id.name);
        final EditText name=namesalonDialog.findViewById(R.id.name);
        final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
        adapter=new HintArrayAdapter(BeautyMainPage.context,0);
        adapter.addAll(servicesList);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        String s = context.getResources().getString(R.string.healthCenteres)+" :";
        add_service.setTitle(s);
        add_service.setAdapter(adapter);
        // set listener
        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
                //  salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

                clinName=Filters.getString(Filters.CLINIC_ID,APICall.allClinics.get(position).getBdb_ser_id());
                if(context.getResources().getString(R.string.locale).equals("en"))
                    Name=APICall.allClinics.get(position).getBdb_name();
                else
                    Name=APICall.allClinics.get(position).getBdb_name_ar();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                clinName="";
            }
        });

        //ArrayList<String> namesList=new ArrayList<>();

               /* for (int i = 0; i < supInfoList.size(); i++) {
                    namesList.add(supInfoList.get(i).getName() + "," + supInfoList.get(i).getAddress());
                }*/

                /*ArrayAdapter adapter=new ArrayAdapter(BeautyMainPage.context,
                        android.R.layout.simple_spinner_item, namesList);
                name.setAdapter(adapter);*/

        Button search = namesalonDialog.findViewById(R.id.search);
               /* name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position!=0){

                            idsup = supInfoList.get(position).getId();

                        }else {
                            idsup="";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!name.getSelectedItem().toString().isEmpty()){
                if (!name.getText().equals("")){
                    namesalonDialog.dismiss();
                    clinicName.setText( Name);
                    // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                    filterSupplierName=","+clinName;
                    filterSupplierId=","+salonId;
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                 //   ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));


                }else {
                    namesalonDialog.cancel();
                    clinicName.setText(context.getResources().getText(R.string.providerName));
                    filterSupplierName="";
                    filterSupplierId="";

                    //  APICall.filterSortAlgorithm("3", "", "");
                    //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, providerName.getText().toString()));

                    //  bdb_name="";
                }




            }
        });
        namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // nameSalonOrProvider.setChecked(false);
                clinicName.setText(context.getResources().getText(R.string.providerName));
                filterSupplierName="";
                APICall.filterSortAlgorithm("3", "", "");
                //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, clinicName.getText().toString()));
                // idsup="";
            }
        });
        namesalonDialog.show();
    }

    public static void showServicesNamesFilterDialog(final Context context)
    {
        servicesList.clear();
        for (int i=0;i<APICall.allServices.size();i++){
            if(context.getResources().getString(R.string.locale).equals("en"))
                servicesList.add(APICall.allServices.get(i).getBdb_name());
            else
                servicesList.add(APICall.allServices.get(i).getBdb_name_ar());
        }

        final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
        namesalonDialog.setContentView(R.layout.provider_name_layout);
        namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //final Spinner name=namesalonDialog.findViewById(R.id.name);
        final EditText name=namesalonDialog.findViewById(R.id.name);
        final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
        adapter=new HintArrayAdapter(BeautyMainPage.context,0);
        adapter.addAll(servicesList);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        String s = context.getResources().getString(R.string.service)+" :";
        add_service.setTitle(s);
        add_service.setAdapter(adapter);
        // set listener
        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
                //  salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

                clinName=Filters.getString(Filters.SERVICE_ID,APICall.allServices.get(position).getBdb_ser_id());
                if(context.getResources().getString(R.string.locale).equals("en"))
                    Name=APICall.allServices.get(position).getBdb_name();
                else
                    Name=APICall.allServices.get(position).getBdb_name_ar();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                clinName="";
            }
        });

        //ArrayList<String> namesList=new ArrayList<>();

               /* for (int i = 0; i < supInfoList.size(); i++) {
                    namesList.add(supInfoList.get(i).getName() + "," + supInfoList.get(i).getAddress());
                }*/

                /*ArrayAdapter adapter=new ArrayAdapter(BeautyMainPage.context,
                        android.R.layout.simple_spinner_item, namesList);
                name.setAdapter(adapter);*/

        Button search = namesalonDialog.findViewById(R.id.search);
               /* name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position!=0){

                            idsup = supInfoList.get(position).getId();

                        }else {
                            idsup="";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (!name.getSelectedItem().toString().isEmpty()){
                if (!name.getText().equals("")){
                    namesalonDialog.dismiss();
                    services.setText( Name);
                    // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                    filterServiceId=","+clinName;
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                    //   ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));


                }else {
                    namesalonDialog.cancel();
                    services.setText(context.getResources().getText(R.string.service));
                    filterServiceId="";

                    //  APICall.filterSortAlgorithm("3", "", "");
                    //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, providerName.getText().toString()));

                    //  bdb_name="";
                }




            }
        });
        namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // nameSalonOrProvider.setChecked(false);
                services.setText(context.getResources().getText(R.string.providerName));
                filterServiceId="";
             //   APICall.filterSortAlgorithm("3", "", "");
                //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, clinicName.getText().toString()));
                // idsup="";
            }
        });
        namesalonDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e("OffersFilters",lat+":"+lng +"location Changed");

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

