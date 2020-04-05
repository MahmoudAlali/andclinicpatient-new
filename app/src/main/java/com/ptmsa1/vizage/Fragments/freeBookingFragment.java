package com.ptmsa1.vizage.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
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
import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.ServiceFilter;
import com.ptmsa1.vizage.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class freeBookingFragment extends Fragment {
    LinearLayout  service_hair;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    public static ArrayList<String> mylocation = new ArrayList();
    public static double lat,lng;
    Button ok;
    public static Spinner  placeSpinner,typeSpinner;
    public static int citiyitemSelected;
    public static int placeId = 0;
    ArrayAdapter locatioAdapter;
    public static String maxValDistance,mylocationId="";
    TextView distance,mylocationbtn,offerPrice, providerName, rateProvider;
    static  boolean fregmentIsFirstOpen=false;
    public static String distanceOffer="",locOfferlat="",locOfferlong="",place_service="",priceOffer="",rateOffer="",supRate="";
    public static String priceServiceValue="",minprice="",maxprice="";
    //public static TextView date;
    public static String offerPlace="";
    public static String Place="";
    public static ArrayAdapter adapter;
    public static String salonName="",salonId="";
    public static String Name="";
    ArrayList<String> servicesList=new ArrayList<>();

    public static String filterSupplierName="",filterSupplierId="",filterDistance="",filterType="",filterProviderRate="",filterServicePlace="",filterMyLocationLat="",filterMyLocationLng="",filterOfferPrice="";


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_free_booking_filters, container, false);
        //service_hair = view.findViewById(R.id.service_hair);



        //---------------------init my location spinner-----------
        BeautyMainPage.FRAGMENT_NAME = "freeBookingFragment";

        placeSpinner = view.findViewById(R.id.service_place);
        typeSpinner = view.findViewById(R.id.requestType);
        mylocationbtn = view.findViewById(R.id.my_location);
        providerName = view.findViewById(R.id.providerName);
        rateProvider = view.findViewById(R.id.service_rate);
        distance = view.findViewById(R.id.distance);
        ok = view.findViewById(R.id.ok);
        APICall.getdetailsUser(BeautyMainPage.context);
        APICall.getAllSuppliers(BeautyMainPage.context);

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
            APICall.details_user(APICall.API_PREFIX_NAME+"/api/auth/user/detailsUser",BeautyMainPage.context);

        //region Type Spinner

        final ArrayAdapter TypeAdapter = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.requestType, R.layout.simple_spinner_dropdown_item_v1);
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

        //region Supplier Name

        providerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (servicesList.size()==0)
                    for (int i=0;i<APICall.allSuppliers.size();i++){
                        servicesList.add(APICall.allSuppliers.get(i).getName());
                    }

                final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
                namesalonDialog.setContentView(R.layout.provider_name_layout);
                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //final Spinner name=namesalonDialog.findViewById(R.id.name);
                final EditText name=namesalonDialog.findViewById(R.id.name);
                final SearchableSpinner add_service=namesalonDialog.findViewById(R.id.add_service);
                adapter=new ArrayAdapter(BeautyMainPage.context,R.layout.simple_spinner_dropdown_item_v1,servicesList);
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                add_service.setTitle(getResources().getString(R.string.suppliers));
                add_service.setAdapter(adapter);
                // set listener
                add_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        salonName="\"supplier_name\":" +"\""+ APICall.allSuppliers.get(position).getName()+"\",";
                        salonId="\"SupplierId\":" +"\""+ APICall.allSuppliers.get(position).getId()+"\",";

                        Name=APICall.allSuppliers.get(position).getName();
                       /* if (position!=0) {
                           // final View view1 = inflater.inflate(R.layout.adding_name_service_layout, adding_name_service, false);
                           // TextView textView = view1.findViewById(R.id.service_name);
                           // textView.setText(add_service.getSelectedItem().toString());
                         //   ImageView delete = view1.findViewById(R.id.delete);
                        *//*    delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    adding_name_service.removeView(view1);
                                    for (int i=0;i<servicesForClientGroups.size();i++){
                                        if (servicesForClientGroups.get(i).getViewnum().equals(vc)){
                                            try {
                                                servicesForClientGroups.remove(i);
                                            }catch (Exception ee)
                                            {
                                                ee.printStackTrace();
                                            }
                                        }
                                    }
//
//                                 servicesForClientGroups.remove(vc-1);
                                    Log.e("servicesForClientGroups",servicesForClientGroups.size()+"");

                                }
                            });


                            adding_name_service.addView(view1);*//*

                        }
                        if (position!=0 && servicesList.get(position-1).getBdb_is_fixed_price().equals("1")){
                            ishairService.add(items-1);
                            Log.e("PostionID",position+"");
                            Log.e("PostionID",servicesList.get(position-1).getBdb_name()+"");
                            Log.e("PostionID",servicesList.get(position-1).getBdb_is_fixed_price()+"");
                        }*/
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        salonName="";
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
                            providerName.setText( Name);
                           // filterSupplierName= "\"supplier_name\":" +"\""+ name.getText().toString()+"\",";
                            filterSupplierName=salonName;
                            filterSupplierId=salonId;
                            // APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                           // ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, providerName.getText().toString()));

                            // bdb_name="\"SupplierId\":"+idsup+",";
                        }else {
                            namesalonDialog.cancel();
                            providerName.setText(getResources().getText(R.string.providerName));
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
                        providerName.setText(getResources().getText(R.string.providerName));
                        filterSupplierName="";
                        APICall.filterSortAlgorithm("3", "", "");
                        ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, providerName.getText().toString()));
                        // idsup="";
                    }
                });
                namesalonDialog.show();
            }
        });

        //endregion

        //region Provider Rate

        rateProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog rateProviderDialog = new Dialog(BeautyMainPage.context);
                rateProviderDialog.setContentView(R.layout.rating_dialog);
                Button ok = rateProviderDialog.findViewById(R.id.ok);
                Button cancel = rateProviderDialog.findViewById(R.id.cancel);
                final RatingBar ratingBar = rateProviderDialog.findViewById(R.id.ratingBar);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateProviderDialog.dismiss();
                        rateProvider.setText(getResources().getText(R.string.Provider_Rate)+"" + (int) ratingBar.getRating());
                       // APICall.filterSortAlgorithm("28", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                       // ServiceFragment.serviceFilters.set(4, new ServiceFilter(true, rateProvider.getText().toString()));
                        filterProviderRate=",{\"num\":28,\"value1\":"+ratingBar.getRating()+",\"value2\":"+ratingBar.getRating()+"}";
                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  rateProvider.setChecked(false);
                        rateProviderDialog.cancel();
                        filterProviderRate="";
                    }
                });
                rateProviderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                rateProviderDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //   rateProvider.setChecked(false);
                        rateProvider.setText(getResources().getText(R.string.Provider_Rate));
                        filterProviderRate="";
                        //APICall.filterSortAlgorithm("28", "", "");
                      //  ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
                    }
                });
                rateProviderDialog.show();
            }
        });


        //endregion

        //region Service Place

        final ArrayAdapter adapter = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.service_place, R.layout.simple_spinner_dropdown_item_v1);
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
                    Place="salon";
                }else if (position==2){
                    filterServicePlace = ",{\"num\":8,\"value1\":1}";
                    Place="home";

                }else if (position==3){
                    filterServicePlace = ",{\"num\":10,\"value1\":1}";
                    Place="hall";

                }else if (position==4){
                    filterServicePlace = ",{\"num\":11,\"value1\":1}";
                    Place="hotel";

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("getCities", "http://clientapp.dcoret.com/api/auth/user/getCities");
                filterServicePlace = "";

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
                            mylocationId=item.getTitle().toString();
                            mylocationbtn.setText(mylocationId);
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
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    lat=location.getLatitude();
                                    lng=location.getLongitude();
                                    Log.e("LATLANG",lat+":"+lng);
                                    //APICall.setlocation(lat,lng);
                                    filterMyLocationLat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    filterMyLocationLng="," + "{\"num\":35,\"value1\":"+lng+",\"value2\":0}";

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
                                    filterMyLocationLng=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
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
                        filterDistance=",{\"num\":2,\"value1\":"+Min.getText()+",\"value2\":"+Max.getText()+"}";
                        Log.e("DistanceOffer",distanceOffer);
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

        //region Offer Price

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
                /*Max.setEnabled(false);
                Min.setEnabled(false);*/
                Button search = rangePriceDialog.findViewById(R.id.search);
                // set listener
                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                    @Override
                    public void valueChanged(Number minValue, Number maxValue) {
                        tvMin.setText(String.valueOf(minValue)+" "+getString(R.string.ryal));
                        Min.setText(String.valueOf(minValue));
                        Max.setText(String.valueOf(maxValue));
                        tvMax.setText(String.valueOf(maxValue)+" "+getString(R.string.ryal));
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
                        filterOfferPrice=",{\"num\":39,\"value1\":"+Min.getText()+",\"value2\":"+Max.getText()+"}";


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
                        filterOfferPrice="";
                    }
                });

                rangePriceDialog.show();

            }
        });

        //endregion

/*
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
                    distance.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
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
*/


        //date=view.findViewById(R.id.date);
      /*  if (ServiceFragment.date.equals("")) {
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



*/





//        if (fregmentIsFirstOpen==false){
//            fregmentIsFirstOpen=true;
//        }



      //  Log.e("Size",mylocation.size()+"");





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
                } else if(typeSpinner.getSelectedItemPosition()==1&& filterOfferPrice.equals("")){
                    APICall.showSweetDialog(BeautyMainPage.context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.offerPrice_proceed));
                } else {
//                    APICall.setCityId(placeSpinner.getSelectedItemPosition());
                    citiyitemSelected = placeSpinner.getSelectedItemPosition();
                    if(typeSpinner.getSelectedItemPosition()==2)
                        fragment = new RequestProvidersFragment();


                    else


                        fragment= new OffersForRequest();
                    fm = getActivity().getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                }
            }
        });


      /*  priceService.setOnClickListener(new View.OnClickListener() {
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
        });*/






        return view;
    }


}
