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
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.DataModel.ServiceFilter;
import com.ptm.clinicpa.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Arrays;

public class freeBookingFragment extends Fragment implements LocationListener ,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LinearLayout  service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static ArrayList<String> mylocation = new ArrayList();
    public static double lat,lng;
    Button ok;
    public static Spinner  placeSpinner,typeSpinner,genderSpinner,ageSpinner;
    public static int citiyitemSelected;
    public static int placeId = 0;
    ArrayAdapter locatioAdapter;
    public static String maxValDistance,mylocationId="";
    static TextView distance,mylocationbtn,specialityType, clinicName, doctorName;
    static  boolean fregmentIsFirstOpen=false;
    public static String distanceOffer="",locOfferlat="",locOfferlong="",place_service="",priceOffer="",rateOffer="",supRate="";
    public static String priceServiceValue="",minprice="",maxprice="";
    //public static TextView date;
    public static String offerPlace="";
    public static String Place="";
    public static HintArrayAdapter adapter;
    public static String clinName="",salonId="",docName="";
    public static String Name="";
    private static ArrayList<String> servicesList=new ArrayList<>();
    ArrayList<String> specialitiesList=new ArrayList<>();

    public static String filterSupplierName="",filterSupplierId="",filterDistance="",filterType="",filterProviderRate="",filterAgeRange="",filterGender="",filterServicePlace="",filterMyLocationLat="",filterMyLocationLng="",filterOfferPrice="",filterSpeciality="",filterDoctorName="";


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_free_booking_filters, container, false);
        //service_hair = view.findViewById(R.id.service_hair);

        //---------------------init my location spinner-----------
        BeautyMainPage.FRAGMENT_NAME = "freeBookingFragment";

        placeSpinner = view.findViewById(R.id.service_place);
        genderSpinner = view.findViewById(R.id.gender);
        ageSpinner = view.findViewById(R.id.age_range);
        typeSpinner = view.findViewById(R.id.requestType);
        mylocationbtn = view.findViewById(R.id.my_location);
        clinicName = view.findViewById(R.id.providerName);
        doctorName = view.findViewById(R.id.service_rate);
        distance = view.findViewById(R.id.distance);
        ok = view.findViewById(R.id.ok);
        APICall.getdetailsUser(BeautyMainPage.context);
        //APICall.getAllSuppliers(BeautyMainPage.context);
        APICall.getAllSpecialities(BeautyMainPage.context);

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

        if (BeautyMainPage.client_name.equals(""))
            APICall.details_user(APICall.API_PREFIX_NAME+"/api/user/detailsUser",BeautyMainPage.context);

        //region Type Spinner

        final HintArrayAdapter TypeAdapter = new HintArrayAdapter(BeautyMainPage.context, 0);
        TypeAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.requestType)));
        TypeAdapter.setDropDownViewResource(R.layout.spinner_center_item);
        typeSpinner.setAdapter(TypeAdapter);

/*
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
*/

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    switch (position) {
                        case 1:
                            filterType="0";
                            break;
                        case 2:
                            filterType="1";
                            break;
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");

            }
        });


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
                APICall.getClinics(BeautyMainPage.context,filterMyLocationLat,filterMyLocationLng,filterDistance,filterSpeciality);

            }
        });

        //endregion

        //region Doctor Name

        doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genderFilter=Filters.getString(Filters.PATIENT_GENDER,APICall.getGender(BeautyMainPage.context));
                if(mylocationbtn.getText().toString().equals(getResources().getString(R.string.MyLocation))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.location_first));
                } else if(distance.getText().toString().equals(getResources().getString(R.string.distance))) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.distance_first));
                }
                else
                APICall.getDoctors(false,BeautyMainPage.context,filterMyLocationLat,filterMyLocationLng,filterDistance,filterSpeciality,filterSupplierName,genderFilter);

            }
        });


        //endregion

        //region Service Place

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
/*
                    switch (position) {
                        case 1:
                            placeId = 32;
                            filterServicePlace="0";
                            break;
                        case 2:
                            placeId = 1;
                            filterServicePlace="1";
                            break;
                        case 3:
                            placeId = 30;
                            filterServicePlace="2";
                            break;
                        case 4:
                            placeId = 31;
                            filterServicePlace="3";
                            break;
                    }
*/
                if (position==1) {
                    filterServicePlace = ",{\"num\":9,\"value1\":1}";
                    Place="center";
                }else if (position==2){
                    filterServicePlace = ",{\"num\":8,\"value1\":1}";
                    Place="home";

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");
                filterServicePlace = "";

            }
        });


        //endregion

        //region Age range

        final HintArrayAdapter ageAdapter = new HintArrayAdapter(BeautyMainPage.context, 0);
        ageAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.age)));
        ageAdapter.setDropDownViewResource(R.layout.spinner_center_item);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/*
                    switch (position) {
                        case 1:
                            placeId = 32;
                            filterServicePlace="0";
                            break;
                        case 2:
                            placeId = 1;
                            filterServicePlace="1";
                            break;
                        case 3:
                            placeId = 30;
                            filterServicePlace="2";
                            break;
                        case 4:
                            placeId = 31;
                            filterServicePlace="3";
                            break;
                    }
*/
                if (position==1) {
                    filterAgeRange = ",{\"num\":9,\"value1\":1}";
                }else if (position==2){
                    filterAgeRange = ",{\"num\":8,\"value1\":1}";

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");
                filterServicePlace = "";

            }
        });

        //endregion

        //region Gender

        final HintArrayAdapter genderAdapter = new HintArrayAdapter(BeautyMainPage.context, 0);
        genderAdapter.addAll(Arrays.asList(getResources().getStringArray(R.array.gender)));
        genderAdapter.setDropDownViewResource(R.layout.spinner_center_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/*
                    switch (position) {
                        case 1:
                            placeId = 32;
                            filterServicePlace="0";
                            break;
                        case 2:
                            placeId = 1;
                            filterServicePlace="1";
                            break;
                        case 3:
                            placeId = 30;
                            filterServicePlace="2";
                            break;
                        case 4:
                            placeId = 31;
                            filterServicePlace="3";
                            break;
                    }
*/
                if (position==1) {
                    filterGender = Filters.getString(Filters.PATIENT_GENDER,0+"");
                }else if (position==2){
                    filterGender =Filters.getString(Filters.PATIENT_GENDER,1+"");

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");
                filterGender = "";

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
                                Criteria crit = new Criteria();
                                crit.setAccuracy(Criteria.ACCURACY_FINE);
                                //locationManager.requestLocationUpdates( locationListener);
                                LocationManager locationManager2 = (LocationManager)BeautyMainPage.context.getSystemService(Context.LOCATION_SERVICE);
                                locationManager2.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        Log.e("LATLANG",lat+":"+lng +"FIRST");
                                        lat=location.getLatitude();
                                        lng=location.getLongitude();
                                        Log.e("LATLANG",lat+":"+lng);
                                        //APICall.setlocation(lat,lng);
                                        filterMyLocationLat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                        filterMyLocationLng="{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
                                        clinicName.setText(getResources().getText(R.string.providerName));
                                        filterSupplierName="";
                                        doctorName.setText(getResources().getText(R.string.doctorName));
                                        filterDoctorName="";

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
                                    filterMyLocationLat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    filterMyLocationLng="{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
                                    clinicName.setText(getResources().getText(R.string.providerName));
                                    filterSupplierName="";
                                    doctorName.setText(getResources().getText(R.string.doctorName));
                                    filterDoctorName="";
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
                        filterDistance="{\"num\":2,\"value1\":"+Min.getText()+",\"value2\":"+Max.getText()+"}";
                        Log.e("DistanceOffer",distanceOffer);
                        clinicName.setText(getResources().getText(R.string.providerName));
                        filterSupplierName="";
                        doctorName.setText(getResources().getText(R.string.doctorName));
                        filterDoctorName="";
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

        //region Speciality

        specialityType=view.findViewById(R.id.offerPrice);
        specialityType.setOnClickListener(new View.OnClickListener() {
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
                           /* filterSupplierName=salonName;
                            filterSupplierId=salonId;*/
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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mylocationbtn.getText().toString().equals(getResources().getString(R.string.MyLocation))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.location_proceed));
                } else  if (placeSpinner.getSelectedItemPosition() == 0) {
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.place_proceed));
                } else if(distance.getText().toString().equals(getResources().getString(R.string.distance))){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.distance_proceed));
                } else if(typeSpinner.getSelectedItemPosition()==0){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.type_proceed));
                /*} else if(typeSpinner.getSelectedItemPosition()==1&& filterSpeciality.equals("")){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.speciality_proceed));*/
                } else {
//                    APICall.setCityId(placeSpinner.getSelectedItemPosition());
                    citiyitemSelected = placeSpinner.getSelectedItemPosition();
                    if(typeSpinner.getSelectedItemPosition()==2)
                        fragment = new RequestProvidersFragment();
                    else
                        fragment= new OffersForRequest();
                    Bundle b= new Bundle();
                    b.putBoolean("isGroup",false);
                    fm = getActivity().getFragmentManager();
                    fragment.setArguments(b);
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

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
                    filterSupplierName=clinName;
                    filterSupplierId=salonId;
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
//                    ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));

                    doctorName.setText(context.getResources().getText(R.string.doctorName));
                    filterDoctorName="";
                    // bdb_name="\"SupplierId\":"+idsup+",";
                }else {
                    namesalonDialog.cancel();
                    clinicName.setText(context.getResources().getText(R.string.providerName));
                    filterSupplierName="";
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
    public static void showDoctorsNamesFilterDialog(final Context context)
    {
        servicesList.clear();
        for (int i=0;i<APICall.allDoctors.size();i++){
            if(context.getResources().getString(R.string.locale).equals("en"))
                servicesList.add(APICall.allDoctors.get(i).getBdb_name());
            else
                servicesList.add(APICall.allDoctors.get(i).getBdb_name_ar());
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
        String s = context.getResources().getString(R.string.doctors)+" :";
        add_service.setTitle(s);
        add_service.setAdapter(adapter);
        // set listener
        add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
              //  salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

                docName=Filters.getString(Filters.CLINIC_ID,APICall.allDoctors.get(position).getBdb_ser_id());
                if(context.getResources().getString(R.string.locale).equals("en"))
                    Name=APICall.allDoctors.get(position).getBdb_name();
                else
                    Name=APICall.allDoctors.get(position).getBdb_name_ar();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                docName="";
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
                    doctorName.setText( Name);
                    // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                    filterDoctorName=docName;
                    // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                  //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, clinicName.getText().toString()));

                    // bdb_name="\"SupplierId\":"+idsup+",";
                }else {
                    namesalonDialog.cancel();
                    doctorName.setText(context.getResources().getText(R.string.doctorName));
                    filterDoctorName="";
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
                doctorName.setText(context.getResources().getText(R.string.doctorName));
                filterDoctorName="";
                APICall.filterSortAlgorithm("3", "", "");
                //  ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, clinicName.getText().toString()));
                // idsup="";
            }
        });
        namesalonDialog.show();
    }
    private final FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    GoogleApiClient mGoogleApiClient;
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
                    Log.e("LocationChangedFree","ok" +fusedLocationProviderApi.getLastLocation(mGoogleApiClient).getLatitude()+" "+fusedLocationProviderApi.getLastLocation(mGoogleApiClient).getLongitude());
                   /* Lat=String.valueOf(fusedLocationProviderApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Long = String.valueOf(fusedLocationProviderApi.getLastLocation(mGoogleApiClient).getLongitude());
                    */

                }
            });
        } else {
           // requestLocationPermission();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
