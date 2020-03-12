package com.ptmsa1.vizage.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.Notification;
import com.ptmsa1.vizage.Activities.ShoppingCart;
import com.ptmsa1.vizage.Activities.TabOne;
import com.ptmsa1.vizage.Activities.TabTwo;
import com.ptmsa1.vizage.Activities.TabThree;
import com.ptmsa1.vizage.Adapters.ServicesAdapter;
import com.ptmsa1.vizage.DataModel.ServiceFilter;
import com.ptmsa1.vizage.DataModel.ServiceItems;
import com.ptmsa1.vizage.DataModel.SupInfoClass;
import com.ptmsa1.vizage.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
//import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;

import java.util.ArrayList;

public class ServicesTabsFragment extends Fragment implements View.OnClickListener {

    public static CheckBox price,price_offer,distance,rateService,rateProvider,servicePlace,nameSalonOrProvider,discountVal,activeDate;
    Fragment fragment;
    public static SearchableSpinner ser_name;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    public static TextView servicetab,offertab,maptab;
    public static ImageButton filter,compare,sort;
    public static Boolean gridlistcheck=false;
    static boolean Isservice=false;
    LinearLayout myLocationbtn,distancebtn;
    TextView myLocationbtnTxt,distancebtnTxt;
    LinearLayout layout_bar;
    public static ServicesAdapter servicesAdapter;
    public static  String bdb_name="",ServiceId="";
    Toolbar toolbar;
    String service_place_name="";
    LinearLayout pages;
    int TABFLAG=1;
    String idsup="";
    public static String p_offer="",priceOffer="";
    public static int ItemPageNum=4;
    ArrayList<String> citiyname=new ArrayList<>();
    public static  ArrayList<SupInfoClass> supInfoList=new ArrayList<>();

    public static Boolean updateServ=true;
    public static Boolean updateoffr=true;

    TextView pagenum,sortUsed;
    LinearLayout pageNext,pagePrev;
    public static ArrayList<ServiceItems> servicesList=new ArrayList<>();
    public static ArrayList<String> serviceNameList=new ArrayList<>();


    //-------- for check if get services------
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_reservations2, container, false);

        //-------------- init service filter array-----------
        // 1 city name, 2 price, 3 rate service ,4 rate provider, 5 distance , 6 saloon or pro name, 7 service place
        myLocationbtn=view.findViewById(R.id.my_location);
        distancebtn=view.findViewById(R.id.distance);
        sortUsed=view.findViewById(R.id.sort_used);
        pagenum=view.findViewById(R.id.pagenum);
        pageNext=view.findViewById(R.id.pageNext);
        pagePrev=view.findViewById(R.id.pagePrev);
        price_offer=view.findViewById(R.id.price_offer);
        pagenum.setText("Page"+TabOne.pagenum);
        sort=view.findViewById(R.id.sort);
        myLocationbtnTxt=view.findViewById(R.id.myLocationbtnTxt);
        distancebtnTxt=view.findViewById(R.id.distancebtnTxt);
        BeautyMainPage.FRAGMENT_NAME="SERVICETABFRAGMENT";
//        Log.d("doback",BeautyMainPage.FRAGMENT_NAME);

        //--------------init supInfoList---------------
        supInfoList.clear();
        supInfoList.add(new SupInfoClass("Provider Name","",""));


        ServiceId="\"ServiceId\":["+APICall.idSerForOffer+"],";

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortDialog();

            }
        });


        myLocationbtnTxt.setText(PlaceServiceFragment.mylocationId);
        myLocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(BeautyMainPage.context,v);
                for (int i=1;i<PlaceServiceFragment.mylocation.size();i++) {
                    popupMenu.getMenu().add(PlaceServiceFragment.mylocation.get(i));
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String i = item.getTitle().toString();
                            if (i.equals(getResources().getString(R.string.current_location))) {
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
                                        PlaceServiceFragment.lat = location.getLatitude();
                                        PlaceServiceFragment.lng = location.getLongitude();
                                        Log.e("LATLANG", PlaceServiceFragment.lat + ":" + PlaceServiceFragment.lng);
                                        APICall.setlocation(PlaceServiceFragment.lat, PlaceServiceFragment.lng);
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
                            if (i.equals(getResources().getString(R.string.new_location))){
                                fragment = new MapFragment();
                                fm = getActivity().getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commit();
                                BeautyMainPage.FRAGMENT_NAME="Tabs";
                            }
                        return true;
                        }


                    });
                }
                popupMenu.show();
                }
        });
        for (int i = 0; i < ServiceFragment.serviceFilters.size(); i++) {
            if (i == 5) {
                if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {

                    distancebtnTxt.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                    distancebtnTxt.setText("<="+PlaceServiceFragment.maxValDistance);

//  distance.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                }

            }
        }

        distancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
                rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
                rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                // get seekbar from view
                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
                rangeSeekbar.setMaxValue(10000);
                // get min and max text view
                TextView title = rangeDistanceDialog.findViewById(R.id.title);
                title.setText(getResources().getText(R.string.Distance_Range));
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

                        PlaceServiceFragment.maxValDistance=tvMax.getText().toString();
                        distancebtnTxt.setText("<="+PlaceServiceFragment.maxValDistance);

//                        distancebtn.setText(R.string.distance+":"+Min.getText().toString()+"-" + Max.getText().toString());
                        APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(true, distancebtnTxt.getText().toString()));
                        PlaceServiceFragment.distanceOffer=",{\"num\":2,\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";

                        if (TABFLAG==1){
                            APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
//                            APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

                        }else {
                            APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
//                            APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                        }



                    }
                });

                rangeDistanceDialog.show();
            }
        });

//        if (updateServ) {
            APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
//            updateServ=false;
//        }
//        APICall.automatedBrowse()

        pageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabOne.pagenum += 1;
                if (TABFLAG==1) {
                    Log.e("serviceCheck",TabOne.pagenum+"");
                    APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", (TabOne.pagenum) + "", BeautyMainPage.context);
                }else if (TABFLAG==2){
                    TabTwo.refreshRV();
                    Log.e("OfferCheck",TabOne.pagenum+"");
                    APICall.automatedBrowseOffers("8", TabOne.pagenum+"", BeautyMainPage.context);

                }
            }
        });
        pagePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TabOne.pagenum-1!=0) {
                    TabOne.pagenum -= 1;
                    if (TABFLAG==1) {
                        Log.e("serviceCheck",TabOne.pagenum+"");
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", (TabOne.pagenum) + "", BeautyMainPage.context);
                }else if (TABFLAG==2){
                        Log.e("OfferCheck",TabOne.pagenum+"");
                        TabTwo.refreshRV();
                        APICall.automatedBrowseOffers("8",TabOne.pagenum+"", BeautyMainPage.context);

                }
                }else {
//                    APICall.showSweetDialog(BeautyMainPage.context,"","");
                }


            }
        });

         toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        citiesSpinner =view.findViewById(R.id.citiesSpinner);
        filter =view.findViewById(R.id.filter);
        compare =view.findViewById(R.id.compare);
//        gridlist =view.findViewById(R.id.gridlist);
        layout_bar =view.findViewById(R.id.layout_bar);
        servicetab=view.findViewById(R.id.servicetabclick);
        offertab=view.findViewById(R.id.offertabclick);
        maptab=view.findViewById(R.id.maptabclick);
        pages=view.findViewById(R.id.pages);




        Log.e("ServiceFilterSize",ServiceFragment.serviceFilters.size()+"");
        toolbar.setNavigationOnClickListener(this);

        //-------------------- Grid List---------------
//        gridlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gridlist();
//            }
//        });

        //-------------------- compare services----------
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TabOne.compareModels.size()>=2) {
                    Log.d("Compare", ServicesAdapter.comparenum+"");
//                    fragment = new CompareFragment();
//                    fm = getActivity().getFragmentManager();
//                    fragmentTransaction = fm.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment, fragment);
//                    fragmentTransaction.commit();

                    Intent intent=new Intent(BeautyMainPage.context,CompareServiceActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(BeautyMainPage.context,"Compare 2 items or more",Toast.LENGTH_LONG).show();

                }
            }
        });




        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (citiesSpinner.getSelectedItemPosition()==0){
//                    ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.select_city_alert);
//                        }
//                    });
//                }else
//                if (TABFLAG == 1) {





                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    dialog.setContentView(R.layout.filter_dialog_layout);
                    //----------place service--------------
                Spinner place_service=dialog.findViewById(R.id.place_service);
                ArrayAdapter adapter1=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.service_place,R.layout.simple_spinner_dropdown_item_v1);
                adapter1.setDropDownViewResource(R.layout.spinner_center_item);
                place_service.setSelection(0);
                place_service.setAdapter(adapter1);
                place_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //---------- service names---------------
                ser_name = dialog.findViewById(R.id.ser_name);
//                ArrayList<String> serviceNameList=new ArrayList<>();
               ArrayAdapter adapter=new ArrayAdapter(BeautyMainPage.context,R.layout.simple_spinner_dropdown_item_v1,serviceNameList);
                adapter.setDropDownViewResource(R.layout.spinner_center_item);
                ser_name.setTitle(getResources().getString(R.string.Service_Name));
                ser_name.setSelection(0);
                ser_name.setAdapter(adapter);
                APICall.getServicesForFilter("0",adapter,serviceNameList,BeautyMainPage.context);

                ser_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position!=0) {
                            APICall.filterSortAlgorithm("33", servicesList.get(position - 1).getBdb_ser_id(), "0");
                            Log.e("NameList-1",servicesList.get(position - 1).getBdb_name_ar());
                            ListServicesFragment.bdb_ser_id=servicesList.get(position - 1).getBdb_ser_id();
                            APICall.idSerForOffer=servicesList.get(position - 1).getBdb_ser_id();
                            ServiceId="\"ServiceId\":["+APICall.idSerForOffer+"],";

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //-------------- range price filter--------------------
                    price = dialog.findViewById(R.id.price);
                price_offer = dialog.findViewById(R.id.price_offer);
                    Button clean = dialog.findViewById(R.id.clean);
                    clean.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                    PlaceServiceFragment.priceOffer="";
                                    PlaceServiceFragment.rateOffer="";
                                    PlaceServiceFragment.supRate="";
                                  PlaceServiceFragment.priceServiceValue="";

                            //------------------------------------edit names
                            ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, "price"));
                            ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, "Service Rate"));
                            ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, "Provider Rate"));
                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, "Provider Name"));




                            try {
                                        price.setChecked(false);
//                                        distance.setChecked(false);
                                        rateService.setChecked(false);
                                        rateProvider.setChecked(false);
//                                        servicePlace.setChecked(false);
                                        nameSalonOrProvider.setChecked(false);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                        }
                    });



                    if (TABFLAG==2){
                        price.setEnabled(false);
                        price.setVisibility(View.GONE);

                        price_offer.setEnabled(true);
                        price_offer.setVisibility(View.VISIBLE);



                        if (!p_offer.equals("")){
                            price_offer.setText(p_offer);
                            price_offer.setChecked(true);

                        }





                    }else {
                        price.setEnabled(true);
                        price.setVisibility(View.VISIBLE);

                        price_offer.setEnabled(false);
                        price_offer.setVisibility(View.INVISIBLE);




                    }
                    price.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (price.isChecked()) {
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
                                        price.setText(getResources().getText(R.string.Price) + Min.getText().toString() + "-" + Max.getText().toString());
                                        APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", Min.getText().toString(), Max.getText().toString());
                                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, price.getText().toString()));

                                        PlaceServiceFragment.maxprice=tvMax.getText().toString();
                                        PlaceServiceFragment.minprice=tvMin.getText().toString();


                                    }
                                });

                                rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        price.setChecked(false);
                                        price.setText(getResources().getText(R.string.Price));
                                        APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", "", "");
                                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price.getText().toString()));

                                    }
                                });

                                rangePriceDialog.show();


                            } else {
                                price.setText(getResources().getText(R.string.Price));
                                APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", "", "");
                                ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price.getText().toString()));

                            }
                        }


                    });


                price_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (price_offer.isChecked()) {
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
                                    price_offer.setText(getResources().getText(R.string.Price) + Min.getText().toString() + "-" + Max.getText().toString());
//                                    APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", Min.getText().toString(), Max.getText().toString());
//                                    ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, price.getText().toString()));
                                    PlaceServiceFragment.priceOffer=",{\"num\":"+PlaceServiceFragment.placeId+",\"value1\":"+tvMin.getText()+",\"value2\":"+tvMax.getText()+"}";
//                                    PlaceServiceFragment.maxprice=tvMax.getText().toString();
//                                    PlaceServiceFragment.minprice=tvMin.getText().toString();
                                    p_offer=price_offer.getText().toString();



                                }
                            });

                            rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    price_offer.setChecked(false);
                                    price_offer.setText(getResources().getText(R.string.Price));
//                                    APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", "", "");
//                                    ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price_offer.getText().toString()));
                                     p_offer="سعر العرض";
                                    PlaceServiceFragment.priceOffer="";
                                }
                            });

                            rangePriceDialog.show();


                        } else {
                            price_offer.setText(getResources().getText(R.string.Price));
//                            APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", "", "");
//                            ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price_offer.getText().toString()));
                            p_offer="سعر العرض";
                            PlaceServiceFragment.priceOffer="";

                        }
                    }


                });


                //------------------- rate service filte-------------
                    rateService = dialog.findViewById(R.id.rate_service);
                if (TABFLAG==2){
                    rateService.setEnabled(false);
                }
                    rateService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (rateService.isChecked()) {
                                final Dialog rateServiceDialog = new Dialog(BeautyMainPage.context);
                                rateServiceDialog.setContentView(R.layout.rating_dialog);
                                Button ok = rateServiceDialog.findViewById(R.id.ok);
                                Button cancel = rateServiceDialog.findViewById(R.id.cancel);
                                final RatingBar ratingBar = rateServiceDialog.findViewById(R.id.ratingBar);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateServiceDialog.dismiss();
                                        rateService.setText(getResources().getText(R.string.Service_Eval)+"" + (int) ratingBar.getRating());
                                        APICall.filterSortAlgorithm("5", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(true, rateService.getText().toString()));
                                        PlaceServiceFragment.rateOffer=",{\"num\":5,\"value1\":"+ratingBar.getRating()+",\"value2\":"+ratingBar.getRating()+"}";
                                    }
                                });


                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateService.setChecked(false);
                                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                                        rateServiceDialog.dismiss();
                                        PlaceServiceFragment.rateOffer="";
                                    }
                                });
                                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        rateService.setChecked(false);
                                        rateService.setText(getResources().getText(R.string.Service_Eval));
                                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));

                                        APICall.filterSortAlgorithm("5", "", "");
                                        PlaceServiceFragment.rateOffer="";


                                    }
                                });

                                rateServiceDialog.show();
                            } else {
                                rateService.setText(getResources().getText(R.string.Service_Eval));
                                APICall.filterSortAlgorithm("5", "", "");
                                ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                                    PlaceServiceFragment.rateOffer="";

                            }
                        }
                    });


                    //------------------- rate provider filte------------------
                    rateProvider = dialog.findViewById(R.id.rate_provoder);
                    rateProvider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                  if (rateProvider.isChecked()) {
                                final Dialog rateProviderDialog = new Dialog(BeautyMainPage.context);
                                rateProviderDialog.setContentView(R.layout.rating_dialog);
                                Button ok = rateProviderDialog.findViewById(R.id.ok);
                                Button cancel = rateProviderDialog.findViewById(R.id.cancel);
                                final RatingBar ratingBar = rateProviderDialog.findViewById(R.id.ratingBar);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateProviderDialog.dismiss();
                                        rateProvider.setText(getResources().getText(R.string.Provider_Eval)+"" + (int) ratingBar.getRating());
                                        APICall.filterSortAlgorithm("28", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(true, rateProvider.getText().toString()));
                                        PlaceServiceFragment.supRate=",{\"num\":28,\"value1\":"+ratingBar.getRating()+",\"value2\":"+ratingBar.getRating()+"}";
                                    }
                                });


                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateProvider.setChecked(false);
                                        rateProviderDialog.cancel();
                                        PlaceServiceFragment.supRate="";
                                    }
                                });
                                rateProviderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                rateProviderDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        rateProvider.setChecked(false);
                                        rateProvider.setText(getResources().getText(R.string.Provider_Eval));
                                        APICall.filterSortAlgorithm("28", "", "");
                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
                                    }
                                });
                                rateProviderDialog.show();
                            } else {
                                rateProvider.setText(getResources().getText(R.string.Provider_Eval)+"");
                                APICall.filterSortAlgorithm("28", "", "");
                                ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
                                      PlaceServiceFragment.supRate="";
                            }
                        }
                    });
                    //-------------- range distance filter--------------------
//                    distance = dialog.findViewById(R.id.far);
//                    distance.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                                if (distance.isChecked()) {
//                                final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
//                                rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
//                                rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                // get seekbar from view
//                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
////                            rangeSeekbar.setMaxValue(100);
//                                // get min and max text view
//                                TextView title = rangeDistanceDialog.findViewById(R.id.title);
//                                title.setText("Distance Range");
//                                final TextView tvMin = rangeDistanceDialog.findViewById(R.id.textMin1);
//                                final TextView tvMax = rangeDistanceDialog.findViewById(R.id.textMax1);
//                                final EditText Min = rangeDistanceDialog.findViewById(R.id.minval);
//                                final EditText Max = rangeDistanceDialog.findViewById(R.id.maxval);
//                                Button search = rangeDistanceDialog.findViewById(R.id.search);
//                                // set listener
//                                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//                                    @Override
//                                    public void valueChanged(Number minValue, Number maxValue) {
//                                        tvMin.setText(String.valueOf(minValue));
//                                        Min.setText(String.valueOf(minValue));
//                                        Max.setText(String.valueOf(maxValue));
//                                        tvMax.setText(String.valueOf(maxValue));
//                                    }
//                                });
//
//                                // set final value listener
//                                rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
//                                    @Override
//                                    public void finalValue(Number minValue, Number maxValue) {
//                                        Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//                                    }
//                                });
//
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        rangeDistanceDialog.dismiss();
//                                        distance.setText("البعد " + Min.getText().toString() + "-" + Max.getText().toString());
//                                        APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
//                                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(true, distance.getText().toString()));
//
//                                    }
//                                });
//
//
//                                rangeDistanceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        distance.setChecked(false);
//                                        distance.setText("البعد");
//                                        APICall.filterSortAlgorithm("2", "", "");
//                                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));
//
//                                    }
//                                });
//                                rangeDistanceDialog.show();
//
//                            } else {
//                                distance.setText("البعد");
//                                APICall.filterSortAlgorithm("2", "", "");
//                                ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));
//
//                            }
//                        }
//
//
//                    });




                    Button filterBtnDialog = dialog.findViewById(R.id.filter);
                    filterBtnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
//                        TabOne.browseService();

                            if (TABFLAG==1) {
                                TabOne.arrayList.clear();
                                TabOne.servicesAdapter.notifyDataSetChanged();
                                APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                                updateServ=false;
                                updateoffr=true;
                            }else if (TABFLAG==2){
                                TabTwo.arrayList.clear();
                                TabTwo.offersAdapterTab.notifyDataSetChanged();
                                APICall.automatedBrowseOffers("8", "1", BeautyMainPage.context);
                                updateServ=true;
                                updateoffr=false;
                            }
                            //                            APICall.clearFilterList();
//                            APICall.filterSortAlgorithm("6",  citiesSpinner.getSelectedItemPosition()+"", "0");



                        }
                    });

                    nameSalonOrProvider=dialog.findViewById(R.id.salon_name_provider);
                nameSalonOrProvider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nameSalonOrProvider.isChecked()) {
                                final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
                                namesalonDialog.setContentView(R.layout.name_saloon_or_provider);
                                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    final Spinner name=namesalonDialog.findViewById(R.id.name);
                                    ArrayList<String> namesList=new ArrayList<>();

                                       for (int i = 0; i < supInfoList.size(); i++) {
                                           namesList.add(supInfoList.get(i).getName() + "," + supInfoList.get(i).getAddress());
                                       }

                                    ArrayAdapter adapter=new ArrayAdapter(BeautyMainPage.context,
                                            android.R.layout.simple_spinner_item, namesList);
                                    name.setAdapter(adapter);

                                    Button search = namesalonDialog.findViewById(R.id.search);
                                name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                });


                                search.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!name.getSelectedItem().toString().isEmpty()){
                                            namesalonDialog.dismiss();
                                            nameSalonOrProvider.setText(getResources().getText(R.string.salon_provider_name)+":" +name.getSelectedItem().toString());
                                            APICall.filterSortAlgorithm("3","\""+name.getSelectedItem().toString()+"\"" , null);
                                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, nameSalonOrProvider.getText().toString()));

                                             bdb_name="\"SupplierId\":"+idsup+",";
                                        }else {
                                            namesalonDialog.cancel();
                                            nameSalonOrProvider.setText(getResources().getText(R.string.salon_provider_name));
                                            APICall.filterSortAlgorithm("3", "", "");
                                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));

                                            bdb_name="";
                                        }




                                    }
                                });
                                namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        nameSalonOrProvider.setChecked(false);
                                        nameSalonOrProvider.setText(getResources().getText(R.string.salon_provider_name));
                                        APICall.filterSortAlgorithm("3", "", "");
                                        ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));
                                        idsup="";
                                    }
                                });
                                namesalonDialog.show();

                            }else {
//                                nameSalonOrProvider.setChecked(false);
                                nameSalonOrProvider.setText(getResources().getText(R.string.salon_provider_name));
                                APICall.filterSortAlgorithm("3", "", "");
                                ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));
                                idsup="";

                            }
                            }
                    });




//
//                    servicePlace=dialog.findViewById(R.id.service_place);
//                    servicePlace.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (servicePlace.isChecked()) {
//                                final Dialog servicePlaceDialog = new Dialog(BeautyMainPage.context);
//                                servicePlaceDialog.setContentView(R.layout.service_place);
//                                servicePlaceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                final RadioGroup service_radio=servicePlaceDialog.findViewById(R.id.service_radio);
//                                final CheckBox salon=servicePlaceDialog.findViewById(R.id.salon);
//                                final CheckBox home=servicePlaceDialog.findViewById(R.id.home);
//                                final CheckBox hall=servicePlaceDialog.findViewById(R.id.hall);
//                                final CheckBox hotel=servicePlaceDialog.findViewById(R.id.hotel);
//                                Button search = servicePlaceDialog.findViewById(R.id.search);
//
//
//                                service_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                        switch (checkedId){
//                                            case R.id.salon:
//                                                service_place_name="salon";
//                                                break;
//                                            case R.id.home:
//                                                service_place_name="home";
//                                                break;
//                                            case R.id.hall:
//                                                service_place_name="hall";
//                                                break;
//                                            case R.id.hotel:
//                                                service_place_name="hotel";
//                                                break;
//                                        }
//                                    }
//                                });
//
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                            servicePlaceDialog.dismiss();
//                                            servicePlace.setText("مكان الخدمة: "+service_place_name );
//                                            APICall.filterSortAlgorithm("3","\""+service_place_name+ "\"", null);
//                                            ServiceFragment.serviceFilters.set(7, new ServiceFilter(true, servicePlace.getText().toString()));
//
//                                    }
//                                });
//                                servicePlaceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        servicePlace.setChecked(false);
//                                        servicePlace.setText("مكان الخدمة");
//                                        APICall.filterSortAlgorithm("3", "", "");
//                                        ServiceFragment.serviceFilters.set(7, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));
//
//                                    }
//                                });
//                                servicePlaceDialog.show();
//
//                            }else {
//                                servicePlace.setChecked(false);
//                                servicePlace.setText("مكان الخدمة");
//                                APICall.filterSortAlgorithm("3", "", "");
//                                ServiceFragment.serviceFilters.set(7, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));
//
//                            }
//                        }
//                    });



                    for (int i = 0; i < ServiceFragment.serviceFilters.size(); i++) {
                        if (i == 2) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                price.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());

                            }
                        } else if (i == 3) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                rateService.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                rateService.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 4) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                rateProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                            rateProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 5) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                distance.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                distance.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//                            }
                        } else if (i == 6) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                nameSalonOrProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                nameSalonOrProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 7) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                servicePlace.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                servicePlace.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 1) {
//                                    price.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                    price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                        }

                    }


                    dialog.show();
//                }

                //--------------------- for offers in the future--------------
//                else if (TABFLAG == 2) {
//                    final Dialog dialog = new Dialog(BeautyMainPage.context);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                    dialog.setContentView(R.layout.filter_dialog_offer_layout);
//
//                    //-------------- range price filter--------------------
//                    final CheckBox price = dialog.findViewById(R.id.price);
//                    price.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (price.isChecked()) {
//                                final Dialog rangePriceDialog = new Dialog(BeautyMainPage.context);
//                                rangePriceDialog.setContentView(R.layout.price_range_dialog);
//                                rangePriceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                // get seekbar from view
//                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangePriceDialog.findViewById(R.id.rangeSeekbar5);
//
//                                // get min and max text view
//                                final TextView tvMin = rangePriceDialog.findViewById(R.id.textMin1);
//                                final TextView tvMax = rangePriceDialog.findViewById(R.id.textMax1);
//                                final EditText Min = rangePriceDialog.findViewById(R.id.minval);
//                                final EditText Max = rangePriceDialog.findViewById(R.id.maxval);
//                                Button search = rangePriceDialog.findViewById(R.id.search);
//                                // set listener
//                                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//                                    @Override
//                                    public void valueChanged(Number minValue, Number maxValue) {
//                                        tvMin.setText(String.valueOf(minValue));
//                                        Min.setText(String.valueOf(minValue));
//                                        Max.setText(String.valueOf(maxValue));
//                                        tvMax.setText(String.valueOf(maxValue));
//                                    }
//                                });
//
//                                // set final value listener
//                                rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
//                                    @Override
//                                    public void finalValue(Number minValue, Number maxValue) {
//                                        Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//                                    }
//                                });
//
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        rangePriceDialog.dismiss();
//                                        price.setText("السعر " + Min.getText().toString() + "-" + Max.getText().toString());
//                                        APICall.filterSortAlgorithm("19", Max.getText().toString(), Min.getText().toString());
//                                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, price.getText().toString()));
//
//                                    }
//                                });
//
//                                rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        price.setChecked(false);
//                                        price.setText("السعر");
//                                        APICall.filterSortAlgorithm("19", "", "");
//                                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price.getText().toString()));
//
//
//                                    }
//                                });
//
//                                rangePriceDialog.show();
//
//
//                            } else {
//                                price.setText("السعر");
//                                APICall.filterSortAlgorithm("19", "", "");
//                                ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price.getText().toString()));
//
//                            }
//                        }
//                    });
//
//
//
//
//
//                    //------------------- rate service filte------------------
//                    final CheckBox rateService = dialog.findViewById(R.id.rate_service);
//                    //------------- this service not available now ------------
//                    rateService.setText("تقييم العرض");
//                    rateService.setEnabled(false);
//                    //-----------------------------------------------------
////                    rateService.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            if (rateService.isChecked()) {
////                                final Dialog rateServiceDialog = new Dialog(BeautyMainPage.context);
////                                rateServiceDialog.setContentView(R.layout.rating_dialog);
////                                Button ok = rateServiceDialog.findViewById(R.id.ok);
////                                Button cancel = rateServiceDialog.findViewById(R.id.cancel);
////                                final RatingBar ratingBar = rateServiceDialog.findViewById(R.id.ratingBar);
////
////                                ok.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        rateServiceDialog.dismiss();
////                                        rateService.setText("تقييم العرض " + (int) ratingBar.getRating());
////                                        APICall.filterSortAlgorithm("5", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
////                                    }
////                                });
////
////
////                                cancel.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        rateService.setChecked(false);
////                                        rateServiceDialog.dismiss();
////                                    }
////                                });
////                                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////
////                                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
////                                    @Override
////                                    public void onCancel(DialogInterface dialog) {
////                                        rateService.setChecked(false);
////                                        rateService.setText("تقييم العرض");
////                                        APICall.filterSortAlgorithm("22", "", "");
////
////                                    }
////                                });
////
////                                rateServiceDialog.show();
////                            } else {
////                                rateService.setText("تقييم العرض");
////                                APICall.filterSortAlgorithm("22", "", "");
////
////                            }
////                        }
////                    });
//
//
//
//
//
//                    //------------------- rate provider filter------------------
//                    final CheckBox rateProvider = dialog.findViewById(R.id.rate_provoder);
//                    rateProvider.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (rateProvider.isChecked()) {
//                                final Dialog rateProviderDialog = new Dialog(BeautyMainPage.context);
//                                rateProviderDialog.setContentView(R.layout.rating_dialog);
//                                Button ok = rateProviderDialog.findViewById(R.id.ok);
//                                Button cancel = rateProviderDialog.findViewById(R.id.cancel);
//                                final RatingBar ratingBar = rateProviderDialog.findViewById(R.id.ratingBar);
//
//
//
//                                ok.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        rateProviderDialog.dismiss();
//                                        rateProvider.setText("تقييم المزودة " + (int) ratingBar.getRating());
//                                        APICall.filterSortAlgorithm("28", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
//                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(true, rateProvider.getText().toString()));
//                                        Log.e("rateprovider","ok");
//                                    }
//                                });
//
//
//                                cancel.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        rateProvider.setChecked(false);
//                                        rateProviderDialog.cancel();
//                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
//                                        Log.e("rateprovider","cancell");
//
//                                    }
//                                });
//                                rateProviderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                rateProviderDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        rateProvider.setChecked(false);
//                                        rateProvider.setText("تقييم المزودة");
//                                        APICall.filterSortAlgorithm("28", "", "");
//                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
//                                        Log.e("rateprovider","cancel");
//
//                                    }
//                                });
//                                rateProviderDialog.show();
//                            } else {
//                                rateProvider.setText("تقييم المزودة");
//                                APICall.filterSortAlgorithm("28", "", "");
//                                ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
//                                Log.e("rateprovider","else");
//
//                            }
//                        }
//                    });
//
//
//                    //-------------- range distance filter--------------------
//                    final CheckBox distance = dialog.findViewById(R.id.far);
//                    distance.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (distance.isChecked()) {
//                                final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
//                                rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
//                                rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                // get seekbar from view
//                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
////                            rangeSeekbar.setMaxValue(100);
//                                // get min and max text view
//                                TextView title = rangeDistanceDialog.findViewById(R.id.title);
//                                title.setText("Distance Range");
//                                final TextView tvMin = rangeDistanceDialog.findViewById(R.id.textMin1);
//                                final TextView tvMax = rangeDistanceDialog.findViewById(R.id.textMax1);
//                                final EditText Min = rangeDistanceDialog.findViewById(R.id.minval);
//                                final EditText Max = rangeDistanceDialog.findViewById(R.id.maxval);
//                                Button search = rangeDistanceDialog.findViewById(R.id.search);
//                                // set listener
//                                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//                                    @Override
//                                    public void valueChanged(Number minValue, Number maxValue) {
//                                        tvMin.setText(String.valueOf(minValue));
//                                        Min.setText(String.valueOf(minValue));
//                                        Max.setText(String.valueOf(maxValue));
//                                        tvMax.setText(String.valueOf(maxValue));
//                                    }
//                                });
//
//                                // set final value listener
//                                rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
//                                    @Override
//                                    public void finalValue(Number minValue, Number maxValue) {
//                                        Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//                                    }
//                                });
//
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        rangeDistanceDialog.dismiss();
//                                        distance.setText("البعد " + Min.getText().toString() + "-" + Max.getText().toString());
//                                        APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
//                                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(true, activeDate.getText().toString()));
//
//                                    }
//                                });
//
//
//                                rangeDistanceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        distance.setChecked(false);
//                                        distance.setText("البعد");
//                                        APICall.filterSortAlgorithm("2", "", "");
//                                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));
//
//                                    }
//                                });
//                                rangeDistanceDialog.show();
//
//                            } else {
//                                distance.setText("البعد");
//                                APICall.filterSortAlgorithm("2", "", "");
//                                ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));
//                            }
//                        }
//                    });
//
//
//
//
//                    final Button filterBtnDialog = dialog.findViewById(R.id.filter);
//                    filterBtnDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.cancel();
////                        TabOne.browseService();
//
//                            TabTwo.arrayList.clear();
//                            TabTwo.offersAdapterTab.notifyDataSetChanged();
//                            Log.e("SizeTTAR",TabTwo.arrayList.size()+"");
//                            APICall.automatedBrowseOffers( "8", "1", BeautyMainPage.context);
////                            APICall.clearFilterList();
//
//                        }
//                    });
//
//                    //---------------- active Date--------------------
//                    activeDate=dialog.findViewById(R.id.activeDate);
//                    activeDate.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (activeDate.isChecked()) {
//                                final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
//                                namesalonDialog.setContentView(R.layout.active_date_dialog);
//                                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                                final DatePicker date=namesalonDialog.findViewById(R.id.date);
//                                Button search = namesalonDialog.findViewById(R.id.search);
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        if (date.getDayOfMonth()!=0){
//                                            namesalonDialog.dismiss();
//                                            String dateText=date.getMonth()+":"+date.getDayOfMonth();
//                                            activeDate.setText("التاريخ النشط: " +dateText);
//                                            APICall.filterSortAlgorithm("3","\""+dateText+"\"" , null);
//                                            ServiceFragment.serviceFilters.set(8, new ServiceFilter(true, activeDate.getText().toString()));
//
//                                        }else {
//                                            namesalonDialog.cancel();
//                                            activeDate.setText("التاريخ النشط");
////                                            APICall.filterSortAlgorithm("3", "", "");
//                                            ServiceFragment.serviceFilters.set(8, new ServiceFilter(false, activeDate.getText().toString()));
//
//                                        }
//
//
//
//
//                                    }
//                                });
//                                namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        activeDate.setChecked(false);
//                                        activeDate.setText("التاريخ النشط");
//                                        APICall.filterSortAlgorithm("3", "", "");
//                                        ServiceFragment.serviceFilters.set(8, new ServiceFilter(false, activeDate.getText().toString()));
//
//                                    }
//                                });
//                                namesalonDialog.show();
//
//                            }else {
////                                nameSalonOrProvider.setChecked(false);
//                                activeDate.setText("التاريخ النشط");
//                                APICall.filterSortAlgorithm("3", "", "");
//                                ServiceFragment.serviceFilters.set(8, new ServiceFilter(false, activeDate.getText().toString()));
//
//                            }
//                        }
//                    });
//
//                    //---------------- Discount val--------------------
//                    discountVal=dialog.findViewById(R.id.discountVal);
//                    discountVal.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (discountVal.isChecked()) {
//                                final Dialog discountDialog = new Dialog(BeautyMainPage.context);
//
//                                discountDialog.setContentView(R.layout.discount_value_percent_dialog);
//                                discountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                                // get seekbar from view
//                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) discountDialog.findViewById(R.id.rangeSeekbar5);
//                                rangeSeekbar.setMaxValue(100);
//                                // get min and max text discountDialog
//                                final TextView tvMin = discountDialog.findViewById(R.id.textMin1);
//                                final TextView tvMax = discountDialog.findViewById(R.id.textMax1);
//                                final EditText Min = discountDialog.findViewById(R.id.minval);
//                                final EditText Max = discountDialog.findViewById(R.id.maxval);
//                                Button search = discountDialog.findViewById(R.id.search);
//                                // set listener
//                                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//                                    @Override
//                                    public void valueChanged(Number minValue, Number maxValue) {
//                                        tvMin.setText(String.valueOf(minValue)+"%");
//                                        Min.setText(String.valueOf(minValue));
//                                        Max.setText(String.valueOf(maxValue));
//                                        tvMax.setText(String.valueOf(maxValue)+"%");
//                                    }
//                                });
//
//                                // set final value listener
//                                rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
//                                    @Override
//                                    public void finalValue(Number minValue, Number maxValue) {
//                                        Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//                                    }
//                                });
//
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        discountDialog.dismiss();
//                                        discountVal.setText("نسبة الخصم المئوية:  " + Min.getText().toString() + "% -" + Max.getText().toString()+"%");
//                                        APICall.filterSortAlgorithm("19", Max.getText().toString(), Min.getText().toString());
//                                        ServiceFragment.serviceFilters.set(9, new ServiceFilter(true, discountVal.getText().toString()));
//                                    }
//                                });
//
//                                discountDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        discountVal.setChecked(false);
//                                        discountVal.setText("نسبة الخصم المئوية");
//                                        APICall.filterSortAlgorithm("19", "", "");
//                                        ServiceFragment.serviceFilters.set(9, new ServiceFilter(false, discountVal.getText().toString()));
//
//
//                                    }
//                                });
//
//                                discountDialog.show();
//
//
//                            } else {
//                                discountVal.setText("نسبة الخصم المئوية");
//                                APICall.filterSortAlgorithm("19", "", "");
//                                ServiceFragment.serviceFilters.set(9, new ServiceFilter(false, discountVal.getText().toString()));
//                            }
//                        }
//                    });
//
//
////                    2 price, 3 rate service, 4 rate prov, 5 distance, 6 service place,7 name salon,8 active date, 9per%
//
//                    for (int i = 0; i < ServiceFragment.serviceFilters.size(); i++) {
//                              if (i == 2) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                price.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//                            }
//                        } else if (i == 3) {
////                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
////                                rateService.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
////                                rateService.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
////                            }
//                        } else if (i == 4) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                rateProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                rateProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//
//                            }
//                        } else if (i == 5) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                distance.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                distance.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//                            }
//                        } else if (i == 7) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                nameSalonOrProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                nameSalonOrProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//                            }
//                        } else if (i == 6) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                servicePlace.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                servicePlace.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//                            }
//                        } else if (i == 8) {
//                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                activeDate.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                activeDate.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//
//                        }
//                        }else if (i == 9) {
//                                  if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
//                                      discountVal.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                      discountVal.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
//
//                                  }
//                              }
//
//                    }
//
//
//                    dialog.show();
//                }
//

            }

                });


//        gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
        tabselected(servicetab,offertab,maptab);
         fragment = new TabOne();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        servicetab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pages.setVisibility(View.VISIBLE);
                TABFLAG=1;

//                gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
                tabselected(servicetab,offertab,maptab);
                 fragment = new TabOne();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });
        offertab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(offertab,servicetab,maptab);
                TABFLAG=2;
//                gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
                pages.setVisibility(View.VISIBLE);
                fragment = new TabTwo();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });
        maptab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(maptab,servicetab,offertab);
                TABFLAG=3;
//                gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
                fragment = new TabThree();
//                fragment = new MapFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
                pages.setVisibility(View.GONE);

            }
        });


        return view;
    }


    void tabselected(TextView t1,TextView t2,TextView t3){
        t1.setTextSize(20);
        t1.setBackgroundResource(R.drawable.selected_tab_shape);
        t1.setTextColor(Color.MAGENTA);
        t2.setTextSize(18);
        t2.setBackgroundResource(R.drawable.unselected_tab_shape);
        t2.setTextColor(Color.BLACK);
        t3.setTextSize(18);
        t3.setBackgroundResource(R.drawable.unselected_tab_shape);
        t3.setTextColor(Color.BLACK);

    }

//    public static void gridlist(){
//        TabOne.compareModels.clear();
//        if (!gridlistcheck){
//            ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    servicesAdapter=new ServicesAdapter(BeautyMainPage.context,TabOne.arrayList,R.layout.service_layout_adapter_grid_last);
//                    LinearLayoutManager manager = new GridLayoutManager(BeautyMainPage.context,2);
//                    TabOne.recyclerView.setLayoutManager(manager);
//                    TabOne.recyclerView.setAdapter(servicesAdapter);
//                    servicesAdapter.notifyDataSetChanged();
//                    gridlistcheck=true;
//                    gridlist.setImageResource(R.drawable.ic_grid_on_black_24dp);
//                }
//            });
//        }else {
//            servicesAdapter=new ServicesAdapter(BeautyMainPage.context,  TabOne.arrayList,R.layout.service_layout_adapter_last);
//            LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
//            TabOne.recyclerView.setLayoutManager(manager);
//            TabOne.recyclerView.setAdapter(servicesAdapter);
//            gridlistcheck=false;
//            gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
//
//        }
//
//
//    }

    //------------ not used---------------
    public static Menu menu;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.bar_menu_compare,menu);
//        this.menu=menu;
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.compare){
            if(ServicesAdapter.comparenum>=2) {
                Log.d("Compare", ServicesAdapter.comparenum+"");
                fragment = new CompareFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
//                Toast.makeText(BeautyMainPage.context,"There no Comparation enugh",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(BeautyMainPage.context,"Compare 2 items or more",Toast.LENGTH_LONG).show();

            }
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(BeautyMainPage.context,ShoppingCart.class);
            startActivity(intent);

        }else if(id==R.id.notify){
            Intent intent=new Intent(BeautyMainPage.context,Notification.class);
            startActivity(intent);
        }
//        else if(id==R.id.gridlist){
////            TabOne.gridlistitems();
//            if (ServicesAdapter.list)
//            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_grid_on_black_24dp));
//            else
//            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_view_list_black_24dp));
//        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
    }

    CheckBox nearest,highRate,priceLH,priceHL,common,compatible;
    public static String sortby="";
    static String value="",num="";
    static int checked=0;
    public void showSortDialog(){
        final Dialog dialog = new Dialog(BeautyMainPage.context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.sort_dialog_layout);
        nearest=dialog.findViewById(R.id.near);
        highRate=dialog.findViewById(R.id.highRate);
        priceLH=dialog.findViewById(R.id.price);
        priceHL=dialog.findViewById(R.id.price2);
        common=dialog.findViewById(R.id.common);
        compatible=dialog.findViewById(R.id.compatible);

        sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
        switch (checked){
            case 1:
                nearest.setChecked(true);
                break;
            case 2:
                highRate.setChecked(true);
                break;
            case 3:
                priceLH.setChecked(true);
                break;
            case 4:
                priceHL.setChecked(true);
                break;
            case 5:
                common.setChecked(true);
                break;
            case 6:
                compatible.setChecked(true);
                break;
            default:
                 sortby="";
                 break;


        }

        nearest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nearest.isChecked()){
                    checked=1;
                    sortUsed.setText(nearest.getText().toString());
                    highRate.setChecked(false);
                    priceLH.setChecked(false);
                    priceHL.setChecked(false);
                    common.setChecked(false);
                    compatible.setChecked(false);
                    num="23";
                    value="asc";
                    sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }else {
                    sortby="";
                    checked=0;
                    sortUsed.setText("");
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }
                Log.e("Sortby",sortby);
            }
        });


        highRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (highRate.isChecked()){
                    checked=2;
                    sortUsed.setText(highRate.getText().toString());
                    nearest.setChecked(false);
                    priceLH.setChecked(false);
                    priceHL.setChecked(false);
                    common.setChecked(false);
                    compatible.setChecked(false);
                    num="22";
                value="desc";
                sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

                    }
                }else {
                sortby="";
                checked=0;
                    sortUsed.setText("");
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

                    }
                }
                Log.e("Sortby",sortby);

            }
        });


        priceLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceLH.isChecked()){
                    sortUsed.setText(priceLH.getText().toString());
                    checked=3;
                    highRate.setChecked(false);
                    nearest.setChecked(false);
                    priceHL.setChecked(false);
                    common.setChecked(false);
                    compatible.setChecked(false);
                    num="19";
                    value="asc";
                    sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

                    }
                }else {
                    sortby="";
                    checked=0;
                    sortUsed.setText("");
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

                    }
                }
                Log.e("Sortby",sortby);

            }
        });


        priceHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceHL.isChecked()){
                    checked=4;
                    sortUsed.setText(priceHL.getText().toString());
                    highRate.setChecked(false);
                    priceLH.setChecked(false);
                    nearest.setChecked(false);
                    common.setChecked(false);
                    compatible.setChecked(false);
                    num="19";
                    value="desc";
                    sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }else {
                    sortby="";
                    checked=0;
                    sortUsed.setText("");
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }
                Log.e("Sortby",sortby);
            }
        });



        common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (common.isChecked()){
                    checked=5;
                    sortUsed.setText(common.getText().toString());
                    highRate.setChecked(false);
                    priceLH.setChecked(false);
                    priceHL.setChecked(false);
                    nearest.setChecked(false);
                    compatible.setChecked(false);
                    num="20";
                    value="desc";
                    sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }else {
                    sortby="";
                    checked=0;
                    sortUsed.setText("");
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }
                Log.e("Sortby",sortby);
            }
        });



        compatible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compatible.isChecked()){
                    checked=6;
                    sortUsed.setText(compatible.getText().toString());
                    highRate.setChecked(false);
                    priceLH.setChecked(false);
                    priceHL.setChecked(false);
                    common.setChecked(false);
                    nearest.setChecked(false);
                    num="21";
                    value="desc";
                    sortby=",\"sort\":{\"num\":"+num+",\"by\":\""+value+"\"}";
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);
                    }
                }else {
                    sortby="";
                    checked=0;
                    sortUsed.setText("");
                    dialog.cancel();
                    if (TABFLAG==1) {
                        APICall.automatedBrowse(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                    }else if (TABFLAG==2){
                        APICall.automatedBrowseOffers("8","1",BeautyMainPage.context);

                    }
                }
                Log.e("Sortby",sortby);
            }
        });

dialog.show();

    }
}
